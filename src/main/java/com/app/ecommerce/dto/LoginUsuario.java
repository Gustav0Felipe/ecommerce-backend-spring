package com.app.ecommerce.dto;

public record LoginUsuario(
	Long id,
	
	String nome,
	
	String telefone,
	
	String email,

	String senha,

	String cpf, 
	
	String role,
	
	String endereco, 
	
	String foto,

	String token,
	
	Boolean enabled
	) {
}
