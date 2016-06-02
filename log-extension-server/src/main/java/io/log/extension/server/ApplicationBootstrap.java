package io.log.extension.server;

import io.log.extension.server.service.KafkaConsumerService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class ApplicationBootstrap {

	public static void main(String[] args) {
		ApplicationContext ctx = SpringApplication.run(ApplicationBootstrap.class, args);
		
		KafkaConsumerService kafkaConsumerService = ctx.getBean(KafkaConsumerService.class);
		kafkaConsumerService.test();
//		System.out.println(kafkaConsumerService.getTopic());
		
	}

}
