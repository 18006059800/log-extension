package io.log.extension.agent.demo.dubbo.provider.service;

import io.log.extension.agent.demo.api.User;
import io.log.extension.agent.demo.api.UserService;
import io.log.extension.agent.demo.dubbo.provider.reop.UserRepo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//import com.alibaba.dubbo.config.annotation.Service;

//@Service(protocol = { "dubbo" })
@Service("userServiceImpl")
public class UserServiceImpl implements UserService {
	@Autowired
	UserRepo userRepo;

	public UserRepo getUserRepo() {
		return userRepo;
	}

	public void setUserRepo(UserRepo userRepo) {
		this.userRepo = userRepo;
	}

	@Override
	public List<User> findAll() {
		List<User> users = new ArrayList<User>();
		userRepo.testUserRepo();
		return users;
	}

	@Override
	public String create(User user) {
		System.out.println(user.getName());
		return "ksadjfsdf";
	}

}
