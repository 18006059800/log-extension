package io.log.extension.agent.demo.service;

import io.log.extension.agent.demo.repo.TrainingRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TrainingService {
	@Autowired
	private TrainingRepo trainingRepo;
	
	public void test() {
		System.out.println("trainingService.test");
		trainingRepo.test();
	}

}
