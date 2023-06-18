package com.comux.academix.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class IndexController {

	@GetMapping("/home")
	public ModelAndView novo() {
		ModelAndView mv = new ModelAndView("home/home.html");
		return mv;
	}

}
