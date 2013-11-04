package com.thingtrack.konekti.countertrack.workbench.service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;

public class MetadataBaseServiceImpl implements MetadataBaseService {	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Autowired
	private BasicDataSource basicDataSource;
	
	@Override
	public String getUrl() {
		String[] chuncks = basicDataSource.getUrl().split(":");
				
		return chuncks[2];
		
	}
}
