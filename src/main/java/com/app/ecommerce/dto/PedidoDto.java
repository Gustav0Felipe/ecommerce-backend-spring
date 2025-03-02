package com.app.ecommerce.dto;

import java.util.List;

public record PedidoDto(String clienteId, String nome, String cpf, List<CartItemDto> produtos, String cep) {}