package com.casadocodigo.greendogdelivery;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "Notificacao.class")
public class GreendogdeliveryApplication {

	public static void main(String[] args) {
		SpringApplication.run(GreendogdeliveryApplication.class, args);
	}

}
