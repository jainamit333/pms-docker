package com.mmt.adminui.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
@Controller
public class AdminPanelPagesController {
	@RequestMapping(value = "/")
	public String index() {
		return "index";
	}
}
