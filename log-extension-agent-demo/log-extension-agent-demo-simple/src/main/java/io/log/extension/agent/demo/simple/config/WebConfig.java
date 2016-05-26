package io.log.extension.agent.demo.simple.config;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@SpringBootApplication
@ComponentScan(basePackages = "io.log.extension.agent.demo")
public class WebConfig extends WebMvcConfigurerAdapter {

}
