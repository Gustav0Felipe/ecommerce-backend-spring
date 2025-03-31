package com.app.ecommerce.dto;

public record LoginUsuario(
	Long id_user,
	
	String nome_user,
	
	String telefone,
	
	String email,

	String cpf, 
	
	String role,
	
	String endereco, 
	
	String foto,

	String token,
	
	Boolean enabled,
	
	String tokenExpireDate
	) {
}
