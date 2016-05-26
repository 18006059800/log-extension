package io.log.extension.agent.demo.simple.service;

import io.log.extension.agent.demo.simple.repo.TrainingRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TrainingService {
	@Autowired
	private TrainingRepo trainingRepo;
	
	public void testService() {
		trainingRepo.test();
	}
}
