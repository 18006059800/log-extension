package io.log.extension.agent.demo.simple.controller;

import io.log.extension.agent.demo.simple.service.TrainingService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TrainingController {
	@Autowired
	private TrainingService trainingService;

	@RequestMapping("/test")
	public String test() {
		return "hello-world";
	}
}
