package io.log.extension.server.core;

//import io.log.extension.server.service.KafkaConsumerService;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TrainingValueTest {
//	@Autowired
//	private KafkaConsumerService kafkaConsumerService;
	@Value("${kafka.topics}")
	private String server;
	@Test
	public void test() {
//		System.out.println("==============================" + (kafkaConsumerService == null) + server);
	}

	@Test
	public void testJson() {
		String str = "{\"dkfj\"}";

		if (str.startsWith("{") && str.endsWith("}")) {
			System.out.println("=================json");
		}

	}
}
