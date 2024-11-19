package com.flowpay;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableRabbit
@SpringBootApplication
public class FlowpayApplication {

	public static void main(String[] args) {
		SpringApplication.run(FlowpayApplication.class, args);
	}

}
