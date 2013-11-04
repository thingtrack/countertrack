package com.thingtrack.countertrack.service.api;

import java.util.List;

import com.thingtrack.countertrack.domain.User;

public interface UserService {
	public List<User> getAll() throws Exception;
	public User get( Integer userId ) throws Exception;
	public User save(User User) throws Exception;
	public void delete(User User) throws Exception;
	public User getByUser(String username, String password) throws Exception;
	public User getUserRecovery(String username) throws Exception;
	public User getUserByRegistrationCode(String registrationCode) throws Exception;

}
