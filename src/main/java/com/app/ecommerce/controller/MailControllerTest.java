package com.app.ecommerce.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.ecommerce.service.EmailService;

@RestController
@RequestMapping("/mail")
public class MailControllerTest {

	@Autowired
	EmailService emailService;
	
	@GetMapping
	public String testeMandarEmail() {
		return emailService.enviarEmailTexto("gustavo.custodio55@hotmail.com", "Teste", "Testando o Email Sender.");
	}
}
