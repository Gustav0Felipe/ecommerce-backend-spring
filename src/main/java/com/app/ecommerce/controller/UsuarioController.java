package com.app.ecommerce.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.ecommerce.dto.AtualizarUsuario;
import com.app.ecommerce.dto.EmailSenha;
import com.app.ecommerce.dto.LoginUsuario;
import com.app.ecommerce.model.Usuario;
import com.app.ecommerce.service.UsuarioService;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/usuarios")
@CrossOrigin(origins = "*", allowedHeaders = "*")
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
		return Optional.ofNullable(usuarioService.cadastrarCliente(usuario))
			.map(usuarioCadastrado -> ResponseEntity.ok(usuarioCadastrado))
			.orElse(ResponseEntity.badRequest().build());
	}
	
	@GetMapping("/verificar/{code}")
	public Boolean verificarUsuario(@PathVariable String code) {
		if(usuarioService.verify(code)) {
			return true;
		} else {
			return false;
		}
	}
	
	@PostMapping("/logar")
	public ResponseEntity<LoginUsuario> login(@RequestBody EmailSenha userPass){
		return Optional.ofNullable(usuarioService.validarLogin(userPass))
				.map(login -> ResponseEntity.ok(login))
				.orElse(ResponseEntity.notFound().build());
	}

	@Transactional
	@PutMapping("/atualizar")
	public ResponseEntity<Usuario> atualizarDados(@RequestBody @Valid AtualizarUsuario dados ) {	
		Usuario usuario = usuarioService.atualizarCliente(dados);
		if(usuario == null)
			return ResponseEntity.notFound().build();
		else
			return ResponseEntity.ok(usuario);
		
	} 
	
	@PostMapping("/email-alterar-senha")
	public String emailAlterarSenha(@RequestBody Usuario usuario) {
		String token = usuarioService.emailAlterarSenha(usuario);
		if(token != null) {
			return token;
		}else {
			return null;
		}
	}
	
	@PutMapping("/editar-senha")
	public ResponseEntity<Usuario> editarSenha(@RequestBody Usuario usuario) {
		return usuarioService.editarSenha(usuario);
	}
	
	@DeleteMapping("/desativar/{id}")
	public ResponseEntity<String> desativarUsuario(@PathVariable Long id) {
		return usuarioService.desativarUsuario(id);
	}
}
