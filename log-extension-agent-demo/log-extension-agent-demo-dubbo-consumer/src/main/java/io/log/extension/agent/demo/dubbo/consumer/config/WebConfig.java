package io.log.extension.agent.demo.dubbo.consumer.config;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@SpringBootApplication
@ComponentScan(basePackages = "io.log.extension.agent.demo.dubbo.consumer")
public class WebConfig extends WebMvcConfigurerAdapter {

}
