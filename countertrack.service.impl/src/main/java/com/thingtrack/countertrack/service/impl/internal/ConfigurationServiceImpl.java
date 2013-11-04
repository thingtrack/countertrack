package com.thingtrack.countertrack.service.impl.internal;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.thingtrack.countertrack.dao.api.ConfigurationDao;
import com.thingtrack.countertrack.domain.Configuration;
import com.thingtrack.countertrack.service.api.ConfigurationService;

public class ConfigurationServiceImpl implements ConfigurationService {
	@Autowired
	private ConfigurationDao configurationDao;

	@Override
	public List<Configuration> getAll() throws Exception {
		return configurationDao.getAll();
		
	}

	@Override
	public Configuration get(Integer configurationId) throws Exception {
		return configurationDao.get(configurationId);
		
	}

	@Override
	public Configuration save(Configuration configuration) throws Exception {
		return configurationDao.save(configuration);
		
	}

	@Override
	public void delete(Configuration configuration) throws Exception {
		configurationDao.delete(configuration);
		
	}

	@Override
	public Configuration getByKey(String key) throws Exception {
		return configurationDao.getByKey(key);
		
	}
	
	@Override
	public List<Configuration> getAllReplication() throws Exception {
		return configurationDao.getAllReplication();
		
	}
}
