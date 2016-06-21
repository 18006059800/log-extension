package io.log.extension.server;

import io.log.extension.server.service.KafkaConsumerService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
@EnableAutoConfiguration
public class ApplicationBootstrap extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(ApplicationBootstrap.class);
    }

    public static void main(String[] args) {
        ApplicationContext applicationContext = SpringApplication.run(ApplicationBootstrap.class, args);
        KafkaConsumerService kafkaConsumerService = applicationContext.getBean(KafkaConsumerService.class);
        kafkaConsumerService.test();

    }

}
