package com.logger.activatorlogger.config;

import com.logger.activatorlogger.entities.ActivityLogger;
import com.logger.activatorlogger.entities.Role;
import com.logger.activatorlogger.repositories.ActivityLoggerRepository;
import com.logger.activatorlogger.repositories.RoleRepository;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

/**
 * Created by Monjur-E-Morshed on 30-Jul-17.
 */
@Component
public class Receiver {

  private static final Logger logger = LoggerFactory.getLogger(Receiver.class);

  private CountDownLatch latch = new CountDownLatch(1);

  @Autowired
  private ActivityLoggerRepository activityLoggerRepository;

  @Autowired
  private RoleRepository roleRepository;

    private static final AtomicLong count = new AtomicLong(0);


    public CountDownLatch getLatch() {
    return latch;
  }

  @KafkaListener(topics = "ums_logger")
  public void receive(ConsumerRecord<?, ?> consumerRecord) throws Exception{
    //logger.info("received data = '{}'", consumerRecord.toString());
    logger.info(consumerRecord.value().toString());
    //ActivityLogger activityLogger =
    /*SparkConf conf = new SparkConf().setAppName("StreamApp").setMaster("master");
    JavaSparkContext sc = new JavaSparkContext(conf);
    List<String> logs = Arrays.asList(consumerRecord.value().toString().split(" "));
    ActivityLogger activityLogger = new ActivityLogger();*/
    ObjectMapper mapper = new ObjectMapper();
    ActivityLogger actualObject = mapper.readValue(consumerRecord.value().toString(), ActivityLogger.class);

    if(actualObject.getRoleId()!=null){
        predict(actualObject);
    }

    activityLoggerRepository.save(actualObject);
    latch.countDown();
  }

    private void predict(ActivityLogger actualObject) {
        List<ActivityLogger> activityLoggers = activityLoggerRepository.findAllWithoutNullRole();
        List<Role> roles = roleRepository.findAllRoles();

        Map<Long,Integer> classNumberMap = new HashMap<>();
        Map<Long, Double> classPriorProbabilityMap = new HashMap<>();

        calculateClassAndPriorProbability(activityLoggers, roles, classNumberMap, classPriorProbabilityMap);

        Map<Long, List<Double>> classConditionalProbilities = new HashMap<>();
        calculateConditinoalProbability(actualObject, activityLoggers, roles, classPriorProbabilityMap, classConditionalProbilities);

        Map<Double, Long> classBasedTotalProbabilities = new HashMap<>();
        Set<Double> probabilityResults = new HashSet<>();

        combileProbabilitiesAndExtractResultSet(roles, classPriorProbabilityMap, classConditionalProbilities, classBasedTotalProbabilities, probabilityResults);

        if(classBasedTotalProbabilities.get(Collections.max(probabilityResults)).equals(actualObject.getRoleId())){
            System.out.println("***********authenticated****************");
        }else{
            System.out.println("***********fraud***************");
        }
    }

    private void combileProbabilitiesAndExtractResultSet(List<Role> roles, Map<Long, Double> classPriorProbabilityMap, Map<Long, List<Double>> classConditionalProbilities, Map<Double, Long> classBasedTotalProbabilities, Set<Double> probabilityResults) {
        for(Role role: roles){
            Double totalProbability = classPriorProbabilityMap.get(role.getId())
                    * classConditionalProbilities.get(role.getId()).stream().reduce(1.0, (a,b)->a*b);
            classBasedTotalProbabilities.put(totalProbability, role.getId());
            probabilityResults.add(totalProbability);
        }
    }

    private void calculateConditinoalProbability(ActivityLogger actualObject, List<ActivityLogger> activityLoggers, List<Role> roles, Map<Long, Double> classPriorProbabilityMap, Map<Long, List<Double>> classConditionalProbilities) {
        for(Role role: roles){
            List<ActivityLogger> activityLoggerListConditional = activityLoggers.stream()
                    .filter(a->a.getRoleId().equals(role.getId()) && a.getClassName().equals(actualObject.getClassName()))
                    .collect(Collectors.toList());
            Double probability = (double)activityLoggerListConditional.size()/classPriorProbabilityMap.get(role.getId());
            if(classConditionalProbilities.get(role.getId())==null){
                List<Double> probabilites = new ArrayList<>();
                probabilites.add(probability);
                classConditionalProbilities.put(role.getId(), probabilites);
            }else{
                List<Double> probabilites = classConditionalProbilities.get(role.getId());
                probabilites.add(probability);
                classConditionalProbilities.put(role.getId(), probabilites);
            }
        }
    }

    private void calculateClassAndPriorProbability(List<ActivityLogger> activityLoggers, List<Role> roles, Map<Long, Integer> classNumberMap, Map<Long, Double> classPriorProbabilityMap) {
        for(Role role: roles){
            List<ActivityLogger> roleBasedActivityLogger = activityLoggers.stream()
                    .filter(a-> a.getRoleId().equals(role.getId()))
                    .collect(Collectors.toList());
            classNumberMap.put(role.getId(), roleBasedActivityLogger.size());
            classPriorProbabilityMap.put(role.getId(), ((double)roleBasedActivityLogger.size()/activityLoggers.size()));
        }
    }

}
