package com.app.ecommerce.dto;

import jakarta.validation.constraints.NotNull;

public record EmailSenha (
		Long id_user,
		
		@NotNull(message = "O Email não pode ser Nulo.")
		String email,
		
		@NotNull(message = "A Senha não pode ser Nula.")
		String senha
		){
}
