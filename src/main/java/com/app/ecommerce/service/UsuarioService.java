package com.app.ecommerce.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.ecommerce.dto.AtualizarUsuario;
import com.app.ecommerce.model.Usuario;
import com.app.ecommerce.repository.UsuarioRepository;
import com.app.ecommerce.util.Utilitarios;

@Service
public class UsuarioService {
	@Autowired 
	public UsuarioRepository usuarioRepository;
	
	public Boolean clienteJaCadastrado(String email) {
		return usuarioRepository.existsByEmail(email);
	}
	
	public Usuario cadastrarCliente(Usuario usuario) {
		
		if(usuarioRepository.existsByEmail(usuario.getEmail())){
			return null;
		} else {
			usuario.setSenha(usuario.getSenha());
			
			String randomCode = Utilitarios.gerarStringAlphanumerica(64);

			usuario.setVerificationCode(randomCode);
			usuario.setEnabled(false);
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

	public List<Usuario> listarTodos() {
		return usuarioRepository.findAll();
	}

	public Usuario atualizarCliente(AtualizarUsuario usuario) {
		Optional<Usuario> user = usuarioRepository.findById(usuario.id_user());
	
		if(user.isPresent()) {
			user.get().setNome_user(usuario.nome_user());
			user.get().setEndereco(usuario.endereco());
			user.get().setTelefone(usuario.telefone());
			user.get().setFoto(usuario.foto());
			return usuarioRepository.save(user.get());
		}
		else {
			return null;
		}
		
	}
}
