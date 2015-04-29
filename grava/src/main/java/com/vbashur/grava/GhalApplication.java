package com.vbashur.grava;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;

@SpringBootApplication
public class GhalApplication implements ApplicationEventPublisherAware {

    public static void main(String[] args) {
        SpringApplication.run(GhalApplication.class, args);
    }

	@Override
	public void setApplicationEventPublisher(ApplicationEventPublisher arg0) {
		// TODO Auto-generated method stub
		
	}
}
