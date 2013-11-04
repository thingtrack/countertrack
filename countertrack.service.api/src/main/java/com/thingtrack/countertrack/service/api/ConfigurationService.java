package com.thingtrack.countertrack.service.api;

import java.util.List;

import com.thingtrack.countertrack.domain.Configuration;

public interface ConfigurationService {
	public List<Configuration> getAll() throws Exception;
	public Configuration get( Integer configurationId ) throws Exception;
	public Configuration save(Configuration configuration) throws Exception;
	public void delete(Configuration configuration) throws Exception;
	
	public Configuration getByKey(String key) throws Exception;
	public List<Configuration> getAllReplication() throws Exception;
}
