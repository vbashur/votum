package com.vbashur.sample.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class SimpleController {

	@RequestMapping("/hi")
	public String sayHi() {
		return "myview";
	}
}
