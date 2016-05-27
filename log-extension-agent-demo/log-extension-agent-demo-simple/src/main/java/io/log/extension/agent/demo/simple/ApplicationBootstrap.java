package io.log.extension.agent.demo.simple;

import org.apache.kafka.log4jappender.KafkaLog4jAppender;
import org.apache.log4j.Appender;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ApplicationBootstrap {

	public static void main(String[] args) {
		Appender a;
		KafkaLog4jAppender ap;
		SpringApplication.run(ApplicationBootstrap.class, args);
	}

}
