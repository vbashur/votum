package com.vbashur.votum;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({ "com.vbashur.votum.config" })
public class VotumApplication 
{
	public static void main(String[] args) {
		SpringApplication.run(VotumApplication.class, args);
	}	

}
