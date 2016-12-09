package com.ddf;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

	public final static String UPLOAD_PATH = "/home/fred/Downloads/ac-ecommerce/";

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
