package com.thingtrack.konekti.countertrack.workbench.message;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.annotation.PostConstruct;

import javax.jms.BytesMessage;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.Session;

import org.fusesource.mqtt.client.BlockingConnection;
import org.fusesource.mqtt.client.MQTT;
import org.fusesource.mqtt.client.QoS;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

import com.thingtrack.countertrack.domain.Configuration;
import com.thingtrack.countertrack.domain.Counter;
import com.thingtrack.countertrack.service.api.ConfigurationService;
import com.thingtrack.countertrack.service.api.CounterService;

public class SensorMessageListener implements MessageListener, SensorMessage {
	@Autowired
    private ConfigurationService configurationService;
	
	@Autowired
	private CounterService counterService;
	    	
	@Autowired
    private JmsTemplate templateOut = null;
			
	private static final String DEVICE_NAME_KEY = "DEVICE_NAME";
	private static final String INTEGRATION_TOPIC_KEY = "INTEGRATION_TOPIC";
	
	private String deviceName = null;
	private String topicServer = null;
	private Integer topicPort = null;
	private String topicName = null;
	
	private List<String> replicationTopics = null;
	
	private byte[] bytes;
	
	@PostConstruct
	public void initialize() throws Exception {
  	    if (configurationService == null)
		  return;
	  
  	    // get device name configuration
  	    getDeviceName();
  	  
  	    // get integration topic
  	    getIntergrationTopic();
  	    
  	    // get replication topics
  	    getReplicationTopics();
	}
	
	@Override
	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
		
	}
	
	@Override
	public void setIntegrationTopic(String topic) {
		String[] uri = topic.split("/");
		
		topicName = uri[1];
		
		String[] uriTopic = uri[0].split(":");
		
		topicServer = uriTopic[0];
		topicPort = Integer.parseInt(uriTopic[1]);
		
	}
	
	@Override
	public void onMessage(final Message message) {
		if (counterService == null)
        	return;            
		
		// STEP01: parse count servlet payload and convert to bytes
		Counter counter = null;
		try {
			counter = parseCounter(message);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
			return;
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
			return;
		}
				
		// STEP02: save count
		/*try {
			counterService.save(counter);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
			return;
		}*/
		
		// STEP03: republish message to vaadin session topic
		templateOut.send(new MessageCreator() {
            public Message createMessage(Session session) throws JMSException {                
                return message;
                
            }
        });
							
		// STEP04: republish message to integration topic
		if (topicServer != null) {
			 try {
				 sendMessage(deviceName, topicServer, topicPort, topicName);
				 					
			 } catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			 } 
			 
		}
		
		// STEP05: replicate message to all sensor hubs
		if (replicationTopics != null) {
			for (String replicationTopic : replicationTopics) {
				String[] uri = replicationTopic.toString().split("/");
				
				String replicationTopicName = uri[1];
				
				String[] uriTopic = uri[0].split(":");
				
				String replicationTopicServer = uriTopic[0];
				Integer replicationTopicPort = Integer.parseInt(uriTopic[1]);
				
				try {
					sendMessage(deviceName, replicationTopicServer, replicationTopicPort, replicationTopicName);								
					 					
				 } catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				 } 
			}
		}
		
	}
	
	private void getDeviceName() throws Exception {
		Configuration deviceNameConfiguration = null;
		
		try {
			deviceNameConfiguration = configurationService.getByKey(DEVICE_NAME_KEY);
		}
		catch (Exception e) {
			// initialize the value
			deviceNameConfiguration = new Configuration();
			deviceNameConfiguration.setKey(DEVICE_NAME_KEY);
			
			// default random name
			Random randomName = new Random();			
			deviceNameConfiguration.setValue("DEV" + randomName.nextInt(100000));
			
			configurationService.save(deviceNameConfiguration);
		}
				 
		deviceName = deviceNameConfiguration.getValue();
		 
	}

	private void getIntergrationTopic() throws Exception {
		Configuration integrationTopicConfiguration = null;
		
		try {
			integrationTopicConfiguration = configurationService.getByKey(INTEGRATION_TOPIC_KEY);
		}
		catch (Exception e) {
			// initialize the value
			integrationTopicConfiguration = new Configuration();
			integrationTopicConfiguration.setKey(INTEGRATION_TOPIC_KEY);
			
			configurationService.save(integrationTopicConfiguration);
		}
		
		if (integrationTopicConfiguration.getValue() != null) {
			  String[] uri = integrationTopicConfiguration.getValue().toString().split("/");
						
			  topicName = uri[1];
			
			  String[] uriTopic = uri[0].split(":");
			
			  topicServer = uriTopic[0];
			  topicPort = Integer.parseInt(uriTopic[1]);
		}
		  		  
	}
	
	private void getReplicationTopics() throws Exception {
		replicationTopics = new ArrayList<String>();
		
		for (Configuration configuration : configurationService.getAllReplication())
			replicationTopics.add(configuration.getValue());
		
	}
	
	private Counter parseCounter(Message message) throws JMSException, UnsupportedEncodingException {
		Counter counter = null;
		
		if (message != null && message instanceof BytesMessage) {
			BytesMessage bytesMessage = (BytesMessage) message;
            String payload = null;
			
			bytes = new byte[(int) bytesMessage.getBodyLength()];
			bytesMessage.readBytes(bytes);
			payload = new String(bytes, "UTF-8");				

			String[] nodes = payload.split("-");
			
			counter = new Counter();
			
			counter.setDeviceName(nodes[0]);			
			counter.setWay(Integer.parseInt(nodes[1]));
			
			long dateLong = Long.parseLong(nodes[2]);
			dateLong *= 1000;			
			Date countDate = new Date(dateLong);
			
			counter.setCountDate(countDate);
        } 
				
		return counter;
	}
	
	private void sendMessage(String deviceHubName, String server, Integer port, String topicName) throws Exception {
		 MQTT publisher = new MQTT();
		 publisher.setClientId(deviceHubName);
		 publisher.setConnectAttemptsMax(1);
		 publisher.setHost(server, port);
		 
		 BlockingConnection pubConnection = publisher.blockingConnection();
		 pubConnection.connect();
	        
		 pubConnection.publish(topicName, bytes, QoS.AT_LEAST_ONCE, false);
		 
		 pubConnection.disconnect();
	}

}
