package com.thingtrack.countertrack.domain;

import java.util.ArrayList;
import java.util.List;

public class Context {
	private String deviceName;
	private String integrationTopic;
	private List<String> replicationTopics = new ArrayList<String>();
	
	/**
	 * @return the deviceName
	 */
	public String getDeviceName() {
		return deviceName;
	}
	/**
	 * @param deviceName the serviceName to set
	 */
	public void setDeviceName(String serviceName) {
		this.deviceName = serviceName;
	}
	/**
	 * @return the integrationTopic
	 */
	public String getIntegrationTopic() {
		return integrationTopic;
	}
	/**
	 * @param integrationTopic the integrationTopic to set
	 */
	public void setIntegrationTopic(String integrationTopic) {
		this.integrationTopic = integrationTopic;
	}
	/**
	 * @return the replicationTopics
	 */
	public List<String> getReplicationTopics() {
		return replicationTopics;
	}
	/**
	 * @param replicationTopics the replicationTopics to set
	 */
	public void setReplicationTopics(List<String> replicationTopics) {
		this.replicationTopics = replicationTopics;
	}
	
}
