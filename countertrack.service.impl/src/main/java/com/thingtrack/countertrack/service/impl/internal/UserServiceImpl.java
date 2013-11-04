package com.thingtrack.countertrack.service.impl.internal;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.thingtrack.countertrack.dao.api.UserDao;
import com.thingtrack.countertrack.domain.User;
import com.thingtrack.countertrack.service.api.UserService;

public class UserServiceImpl implements UserService {
	@Autowired
	private UserDao userDao;
	
	@Override
	public List<User> getAll() throws Exception {
		return this.userDao.getAll();
	}

	@Override
	public User get(Integer userId) throws Exception {
		return this.userDao.get(userId);
	}

	@Override
	public User save(User user) throws Exception {
		return this.userDao.save(user);
	}

	@Override
	public void delete(User user) throws Exception {
		this.userDao.delete(user);
		
	}
	
	@Override
	public User getByUser(String username, String password) throws Exception {
		return this.userDao.getByUser(username, password);
		
	}
	
	@Override
	public User getUserRecovery(String username) throws Exception {
		return this.userDao.getUserRecovery(username);
		
	}
	
	@Override
	public User getUserByRegistrationCode(String registrationCode) throws Exception {
		return this.userDao.getUserByRegistrationCode(registrationCode);
		
	}
}
