package com.thingtrack.countertrack.dao.api;

import com.thingtrack.konekti.dao.template.Dao;
import com.thingtrack.countertrack.domain.User;

public interface UserDao extends Dao<User, Integer> {
	public User getByUser(String username, String password) throws Exception;
	public User getUserRecovery(String username) throws Exception;
	public User getUserByRegistrationCode(String registrationCode) throws Exception;
}
