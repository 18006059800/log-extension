package io.log.extension.agent.demo.dubbo.consumer.controller;

import io.log.extension.agent.demo.dubbo.consumer.service.ConsumerService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TrainingController {
	@Autowired
	private ConsumerService consumerService;
	
	@RequestMapping("/hello")
	public String hello(String name) {
		consumerService.testA();
		return "=====";
	}

}
