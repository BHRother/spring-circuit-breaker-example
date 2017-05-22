package com.bhrother.github

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.retry.annotation.EnableRetry

@EnableRetry
@SpringBootApplication
class Application {

	static void main(String[] args) {
		SpringApplication.run Application, args
	}
}
