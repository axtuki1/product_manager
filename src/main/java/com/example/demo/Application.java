package com.example.demo;

import java.io.File;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

	public static long serverWakeupDateTime;
	
	public static void main(String[] args) {
		serverWakeupDateTime = System.currentTimeMillis();
		SpringApplication.run(Application.class, args);
	}

	public static long getServerWakeupDateTime() {
		return serverWakeupDateTime;
	}
	
}
