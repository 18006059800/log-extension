package io.log.extension.agent.demo.controller;

import io.log.extension.agent.demo.service.TrainingService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TrainingController {
	
	@Autowired
	private TrainingService trainingService;
	
	@RequestMapping("/training")
	public String training() {
		trainingService.test();
		return "hello-world";
	}
	
}
