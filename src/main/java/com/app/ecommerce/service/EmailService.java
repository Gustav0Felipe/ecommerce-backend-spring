package com.app.ecommerce.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {
	
	@Autowired
	private JavaMailSender mailSender;
	
	@Value("${spring.mail.username}")
	private String remetente;
	
	public String enviarEmailTexto(String destinatario, String assunto, String mensagem) {
		
		try {
			MimeMessage message = mailSender.createMimeMessage();

            message.setSubject(assunto);
            MimeMessageHelper helper;
            helper = new MimeMessageHelper(message, true);
            helper.setFrom(remetente);
            helper.setTo(destinatario);
            helper.setText(mensagem, true);
            mailSender.send(message);
			return "Email Enviado";
		}catch(Exception e) {
			return "Erro ao tentar Enviar Email " + e.getLocalizedMessage();
		}
		
	}
}
