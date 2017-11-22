package com.example.demo;

import com.example.demo.util.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoShiroApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoShiroApplication.class, args);
	}
}
