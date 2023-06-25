package com.comux.academix.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import com.comux.academix.repository.UsuarioRepository;

@Controller
public class IndexController {
	
	@Autowired
	UsuarioRepository usuarios;
	

	@GetMapping("/")
	public ModelAndView novo() {
	    ModelAndView mv = new ModelAndView("home/home.html");
	    return mv;
	}

}
