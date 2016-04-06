package io.log.extension.agent.demo.api;

import java.util.List;

public interface UserService {
	/**
	 * 获取所有用户
	 * 
	 * @return所有用户
	 */
	List<User> findAll();

	/**
	 * 创建用户
	 * 
	 * @param user
	 * @return
	 */
	String create(User user);
}
