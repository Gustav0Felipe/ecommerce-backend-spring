package com.app.ecommerce.dto;

import java.util.List;

import com.app.ecommerce.model.Usuario;

public record PedidoDto(Usuario usuario, List<CartItemDto> produtos, String cep, Integer selectedEnvio) {}