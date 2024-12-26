package com.app.ecommerce.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.app.ecommerce.model.Usuario;
import com.app.ecommerce.service.UsuarioService;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

	@Autowired
	UsuarioService usuarioService;
	
	@GetMapping
	public ResponseEntity<List<Usuario>> listarTodos(){
		return ResponseEntity.ok(usuarioService.listarTodos());
	}
	
	@Transactional
	@PostMapping
	public ResponseEntity<Usuario> cadastrarCliente(@RequestBody @Valid Usuario usuario) {
		Usuario clienteSalvo = usuarioService.cadastrarCliente(usuario);
		return ResponseEntity.ok().body(clienteSalvo);
	}
	
	@GetMapping("/verificar")
	public Boolean verificarUsuario(@RequestParam String code) {
		if(usuarioService.verify(code)) {
			return true;
		} else {
			return false;
		}
	}
}
