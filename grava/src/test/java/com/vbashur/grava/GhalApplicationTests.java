package com.vbashur.grava;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.vbashur.grava.GhalApplication;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = GhalApplication.class)
@WebAppConfiguration
public class GhalApplicationTests {

	@Test
	public void contextLoads() {
	}

}
