package com.app.ecommerce.service;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.app.ecommerce.model.Usuario;
import com.app.ecommerce.model.UsuarioRole;
import com.app.ecommerce.repository.UsuarioRepository;
import com.app.ecommerce.util.Utilitarios;

@Service
public class UsuarioService {
	@Autowired 
	public UsuarioRepository usuarioRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	public Boolean clienteJaCadastrado(String email) {
		return usuarioRepository.existsByUsuario(email);
	}
	
	public Usuario cadastrarCliente(Usuario usuario) {
		
		if(usuarioRepository.existsByUsuario(usuario.getEmail())){
			throw new RuntimeException("Esse email já existe.");
		} else {
			String encodedPassword = passwordEncoder.encode(usuario.getSenha());
			usuario.setSenha(encodedPassword);
			
			String randomCode = Utilitarios.gerarStringAlphanumerica(64);
			List<UsuarioRole> roles = new LinkedList<UsuarioRole>();
			roles.add(UsuarioRole.USER);
			
			usuario.setVerificationCode(randomCode);
			usuario.setEnabled(false);
			usuario.setRole(roles);
			Usuario usuarioSalvo = usuarioRepository.save(usuario);
			
			//Mandar Email de confirmação para o Cliente e criar endpoint para o Verification ser Verificado.
			
			return usuarioSalvo;
		}
	}
	
	public boolean verify(String code) {
		Usuario cliente = usuarioRepository.findByVerificationCode(code);
		
		if(cliente == null || cliente.isEnabled()) {
			return false;
		} else {
			cliente.setVerificationCode(null);
			cliente.setEnabled(true);
			usuarioRepository.save(cliente);
			
			return true;
		}
	}
}
