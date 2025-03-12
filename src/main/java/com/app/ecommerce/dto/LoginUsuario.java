package com.app.ecommerce.dto;

import java.util.List;

public record LoginUsuario(
	Long id,
	
	String nome,
	
	String telefone,
	
	String email,

	String senha,

	String cpf, 
	
	List<String> role,
	
	String endereco, 
	
	String foto,

	String token
	) {
}
