package io.log.extension.server;

import io.log.extension.server.service.KafkaConsumerService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class ApplicationBootstrap {

    public static void main(String[] args) {
        ApplicationContext applicationContext = SpringApplication.run(ApplicationBootstrap.class, args);
        KafkaConsumerService kafkaConsumerService = applicationContext.getBean(KafkaConsumerService.class);
        kafkaConsumerService.test();

    }

}
