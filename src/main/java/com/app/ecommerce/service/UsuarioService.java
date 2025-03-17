package com.app.ecommerce.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.app.ecommerce.dto.AtualizarUsuario;
import com.app.ecommerce.dto.EmailSenha;
import com.app.ecommerce.dto.LoginUsuario;
import com.app.ecommerce.model.Usuario;
import com.app.ecommerce.repository.UsuarioRepository;
import com.app.ecommerce.security.JwtService;
import com.app.ecommerce.util.Utilitarios;

@Service
public class UsuarioService {
	@Autowired 
	public UsuarioRepository usuarioRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private EmailService emailService;
	
	@Autowired
	private JwtService jwtService;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	//Testar tudo Inclusive Criptografia e Emails.
	public Usuario cadastrarCliente(Usuario usuario) {
		
		if(usuarioRepository.existsByEmail(usuario.getEmail())){
			return null;
		} else {
			usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
			
			String randomCode = Utilitarios.gerarStringAlphanumerica(64);

			usuario.setVerificationCode(randomCode);
			usuario.setEnabled(false);
			usuario.setRole("USER");
			Usuario usuarioSalvo = usuarioRepository.save(usuario);
			
			String msg = ("<h1>Olá [[NOME]], aqui esta o link para confirmar seu cadastro: </h1>"
					+ String.format("<a href='%s/verificar/%s'>Clique Aqui</a>", 
							"http://localhost:8080/usuarios", usuario.getVerificationCode())
					).replace("[[NOME]]", usuario.getNome_user());
			emailService.enviarEmailTexto(usuario.getEmail(), "Confirme o Seu Cadastro.", msg);

			return usuarioSalvo;
		}
	}
	
	public boolean verify(String code) {
		Optional<Usuario> cliente = usuarioRepository.findByVerificationCode(code);
		
		if(cliente.isEmpty() || cliente.get().isEnabled()) {
			return false;
		} else {
			cliente.get().setVerificationCode(null);
			cliente.get().setEnabled(true);
			usuarioRepository.save(cliente.get());
			return true;
		}
	}

	public LoginUsuario validarLogin(EmailSenha userPass) {
		
		var credenciais = new UsernamePasswordAuthenticationToken(userPass.email(), userPass.senha());
		
		Authentication authentication = authenticationManager.authenticate(credenciais);
		
		//Checa se Existe e se esta Ativado.
		if(!authentication.isAuthenticated() | !usuarioRepository.findByEmail(userPass.email()).get().isEnabled()) {
			return null;
		}

		Usuario usuario = usuarioRepository.findByEmail(userPass.email()).get();
		
		var token = gerarToken(usuario.getEmail());
		return new LoginUsuario(usuario.getId_user(), usuario.getNome_user(), usuario.getTelefone(), usuario.getEmail(),
				usuario.getSenha() ,usuario.getCpf(), usuario.getRole(), usuario.getEndereco(), usuario.getFoto(), token);
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
	
	public boolean validarSenha(Usuario usuario) {
		Optional<Usuario> userDatabase = usuarioRepository.findById(usuario.getId_user());
		if(userDatabase.isPresent() && passwordEncoder.matches(usuario.getSenha(), userDatabase.get().getSenha())) 
			return true;
		else
			return false;
	}
	
	
	public String emailAlterarSenha(Usuario usuario) {
		if(validarSenha(usuario)) {
			String token = Utilitarios.gerarStringAlphanumerica(64);
			
			String htmlMsg = 
				("<h1>Aviso!: Tentaram alterar a senha de sua conta no site Ecommerce</h1>"
				+ "<p>Se for o dono da conta, [[NOME]] , e não for aquele que efetuou o pedido, entre em contato, caso tenha efetuado o pedido: </p>"
				+ "<a href='%s/perfil/editar-senha/%s'>Para prosseguir e alterar sua senha clique aqui.<a>"
				.formatted("http://localhost:8080/usuarios", token)
				).replace("[[NOME]]", usuario.getNome_user());
			
			emailService.enviarEmailTexto(usuario.getEmail(), "Tentativa de Alteração de Senha", htmlMsg);
			return token;
		}
		return null;
	}

	public ResponseEntity<Usuario> editarSenha(Usuario usuario) {
			
			if(usuarioRepository.existsById(usuario.getId_user())) {
				
				usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
				return ResponseEntity.ok(usuarioRepository.save(usuario));
			}else {
				return ResponseEntity.notFound().build();
			}
		
	}
	
	private String gerarToken(String usuario) {
        return "Bearer " + jwtService.generateToken(usuario);
    }

	public ResponseEntity<String> desativarUsuario(Long id) {
			String verificationCode = Utilitarios.gerarStringAlphanumerica(64);
			Optional<Usuario> usuario = usuarioRepository.findById(id);
			
			if(usuario.isPresent()) {
				usuario.get().setVerificationCode(verificationCode);
				usuario.get().setEnabled(false);
				usuarioRepository.save(usuario.get());	
				return ResponseEntity.notFound().build();
			}
			return ResponseEntity.ok(null);
		
	}
}
