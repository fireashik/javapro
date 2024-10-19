package com.proprojectstart.proproject.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.proprojectstart.proproject.model.FormModel;
import com.proprojectstart.proproject.service.FormService;

@Controller
public class FormController {
@Autowired
	private FormService service;
		@RequestMapping("showform")
		public String showForm(Model m) {
			m.addAttribute("form" ,new FormModel());
			return"register";
		}
		@PostMapping("save")
		public String saveForm(@ModelAttribute("form") FormModel model,@RequestParam("avatar") MultipartFile photo) throws IOException {
			if(!photo.isEmpty()) {
				byte [] photobytes=photo.getBytes();
				model.setAvatar(photobytes);	
			}
			service.saveRegForm(model,photo);
			return "register";
		}
}
