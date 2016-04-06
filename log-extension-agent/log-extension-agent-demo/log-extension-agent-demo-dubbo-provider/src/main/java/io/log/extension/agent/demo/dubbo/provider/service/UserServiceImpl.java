package io.log.extension.agent.demo.dubbo.provider.service;

import io.log.extension.agent.demo.api.User;
import io.log.extension.agent.demo.api.UserService;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.dubbo.config.annotation.Service;

@Service(protocol = { "dubbo" })
public class UserServiceImpl implements UserService {
	@Override
	public List<User> findAll() {
		List<User> users = new ArrayList<User>();
		return users;
	}

	@Override
	public String create(User user) {
		System.out.println(user.getName());
		return "ksadjfsdf";
	}

}
