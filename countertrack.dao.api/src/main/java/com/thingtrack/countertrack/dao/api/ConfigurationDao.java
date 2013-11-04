package com.thingtrack.countertrack.dao.api;

import java.util.List;

import com.thingtrack.konekti.dao.template.Dao;
import com.thingtrack.countertrack.domain.Configuration;

public interface ConfigurationDao extends Dao<Configuration, Integer> {
	public Configuration getByKey(String key) throws Exception;
	public List<Configuration> getAllReplication() throws Exception;
	
}
