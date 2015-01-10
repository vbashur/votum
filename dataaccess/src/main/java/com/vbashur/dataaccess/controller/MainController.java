package com.vbashur.dataaccess.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class MainController {
	
	@RequestMapping("/")
	public ModelAndView entryPoint() {
		System.out.println("Hey");
		return new ModelAndView("main");
	}

}
