package com.miniprj.interviewcode;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import th.co.geniustree.springdata.jpa.repository.support.JpaSpecificationExecutorWithProjectionImpl;


@SpringBootApplication
@EnableJpaRepositories(repositoryBaseClass = JpaSpecificationExecutorWithProjectionImpl.class)
public class InterviewcodeApplication {

	public static void main(String[] args) {
		SpringApplication.run(InterviewcodeApplication.class, args);
	}

}
