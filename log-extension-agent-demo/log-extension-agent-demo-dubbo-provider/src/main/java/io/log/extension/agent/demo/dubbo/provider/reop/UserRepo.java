package io.log.extension.agent.demo.dubbo.provider.reop;

import org.springframework.stereotype.Repository;

import java.util.Random;

@Repository
public class UserRepo {
	public void testUserRepo() {
		//throw new RuntimeException("===============");
		Random random = new Random();
		Integer ri = random.nextInt(10);

//		if (ri > 5) {
			throw new RuntimeException("===============");
//		}
	}
}
