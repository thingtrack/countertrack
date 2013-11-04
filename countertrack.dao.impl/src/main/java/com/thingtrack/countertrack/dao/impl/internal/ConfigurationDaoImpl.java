package com.thingtrack.countertrack.dao.impl.internal;

import java.util.List;

import javax.persistence.Query;

import com.thingtrack.konekti.dao.template.JpaDao;

import com.thingtrack.countertrack.dao.api.ConfigurationDao;
import com.thingtrack.countertrack.domain.Configuration;

public class ConfigurationDaoImpl extends JpaDao<Configuration, Integer> implements ConfigurationDao {
	@Override
	public Configuration getByKey(String key) throws Exception {
		return (Configuration)getEntityManager()
				.createQuery("SELECT p FROM " + getEntityName() + " p WHERE p.key = :key")
				.setParameter("key", key).getSingleResult();
	}
	
	@Override
	public List<Configuration> getAllReplication() throws Exception {
		StringBuffer queryString = new StringBuffer("SELECT p FROM " + getEntityName() + " p");
		queryString.append(" WHERE p.key = :key");
		
		Query query = (Query) getEntityManager().createQuery(queryString.toString());
		
		query.setParameter("key", "REPLICATION_TOPIC");
		
		return query.getResultList();
	}

}
