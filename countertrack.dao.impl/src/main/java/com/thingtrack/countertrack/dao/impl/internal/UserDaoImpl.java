package com.thingtrack.countertrack.dao.impl.internal;

import com.thingtrack.konekti.dao.template.JpaDao;

import com.thingtrack.countertrack.dao.api.UserDao;
import com.thingtrack.countertrack.domain.User;

public class UserDaoImpl extends JpaDao<User, Integer> implements UserDao {
	@Override
	public User getByUser(String username, String password) throws Exception {
		return (User)getEntityManager()
				.createQuery("SELECT p FROM " + getEntityName() + " p WHERE p.email = :email AND p.password = :password")
				.setParameter("email", username)
				.setParameter("password", password).getSingleResult();
		
	}
	
	@Override
	public User getUserRecovery(String username) throws Exception {
		return (User)getEntityManager()
				.createQuery("SELECT p FROM " + getEntityName() + " p WHERE p.email = :email")
				.setParameter("email", username).getSingleResult();
		
	}
	
	@Override
	public User getUserByRegistrationCode(String registrationCode) throws Exception {
		return (User)getEntityManager()
				.createQuery("SELECT p FROM " + getEntityName() + " p WHERE p.registrationCode = :registrationCode")
				.setParameter("registrationCode", registrationCode).getSingleResult();
		
	}
}
