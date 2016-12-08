package com.ddf;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SenacAcEcommerceApplication {

	public final static String UPLOAD_PATH = "/home/fred/Downloads";

	public static void main(String[] args) {
		SpringApplication.run(SenacAcEcommerceApplication.class, args);
	}
}
