package com.app.ecommerce.model;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "tb_usuarios")
public class Usuario {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY )
	private Long id_user;
	
	@NotBlank(message = "O Nome do Usuário não pode estar Vazio.")
	@NotNull(message = "O Nome não pode estar Nulo.")
	@Size(max = 255)
	private String nome_user;
	
	@NotBlank(message = "O Telefone do Usuário não pode estar Vazio.")
	@NotNull(message = "O Telefone não pode estar Nulo.")
	@Size(max = 20)
	private String telefone;

	@Email 
	@Size(max = 255)
	private String email;
	
	@Size(min = 8, max = 100)
	private String senha;
	
	@NotBlank(message = "O CPF não pode estar Vazio.")
	@NotNull(message = "O CPF não pode estar Nulo.")
	public String cpf;
	
	@NotNull(message = "A Permissão do Usuario não pode ser Nula.")
	private List<String> role;
	
	@NotBlank(message = "O Endereço não pode estar Vazio.")
	@NotNull(message = "o Endereço do Usuario não pode ser Nulo.")
	private String endereco;

	private String verificationCode;
	
	private boolean enabled;

	public Usuario() {
		
	}

	public Usuario(String nome_user, String telefone,
			String email, String senha,
			String cpf, List<String> role, String endereco, String verification_code, boolean enabled) {
		this.nome_user = nome_user;
		this.telefone = telefone;
		this.email = email;
		this.senha = senha;
		this.cpf = cpf;
		this.role = role;
		this.endereco = endereco;
		this.verificationCode = verification_code;
		this.enabled = enabled;
	}

	public Long getId_user() {
		return id_user;
	}

	public void setId_user(Long id_user) {
		this.id_user = id_user;
	}

	public String getNome_user() {
		return nome_user;
	}

	public void setNome_user(String nome_user) {
		this.nome_user = nome_user;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public List<String> getRole() {
		return role;
	}

	public void setRole(List<String> role) {
		this.role = role;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public String getVerificationCode() {
		return verificationCode;
	}

	public void setVerificationCode(String verificationCode) {
		this.verificationCode = verificationCode;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
}
