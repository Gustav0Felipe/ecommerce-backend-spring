package com.app.ecommerce.dto;

import jakarta.validation.constraints.NotNull;

public record AtualizarUsuario (
		@NotNull(message = "ID não do Usuário pode ser Nulo.")
		Long id_user,
		@NotNull(message = "O Nome do Usuário não pode estar Nulo.")
		String nome_user,
		@NotNull(message = "Telefone do Usuário não pode ser Nulo.")
		String telefone,
		@NotNull(message = "Endereço do Usuário não pode ser Nulo.")
		String endereco,
		
		String foto)
{

}
