package com.krishnapotluri.kafka.kafkaproducer;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


@Component
@PropertySource("classpath:application.properties")
public class KafkaScheduledPush {

    private static final Logger log = LoggerFactory.getLogger(KafkaScheduledPush.class);

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    @Autowired
    private Environment env;
    
    @Autowired
    private Sender sender;
    
    @Scheduled(fixedRate = 1000)
    public void sendKafkaEverySec() throws JSONException {
    	String topicName = env.getProperty("kafka.topic");
    	
        log.info("The time is now {}", dateFormat.format(new Date()));
        
        log.info("topic name: ", topicName);
        
        String jsonData = env.getProperty("json.data");
        
        log.info("topic data: ", jsonData);

        for(int i=0;i<100;i++)
        {
        	sender.send(topicName, getRandomJSONData(jsonData));
        }    
    }
    
    
    private String getRandomJSONData(String jsonData) throws JSONException
    {
    	
    	JSONObject json = new JSONObject(jsonData);
    	
    	Random random = new Random();
    	json.put("cdcbatchid", random.nextLong());
		return json.toString();
    	
    }
    
}
