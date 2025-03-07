package io.github.chubbyhippo.learning;

import org.springframework.boot.SpringApplication;

public class TestLearningApplication {

	public static void main(String[] args) {
		SpringApplication.from(LearningApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
