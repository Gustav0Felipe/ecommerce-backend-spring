package com.app.ecommerce.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.ecommerce.model.Pedido;
import com.app.ecommerce.repository.PedidoRepository;

@Service
public class PedidoService {

	@Autowired
	private PedidoRepository pedidoRepository;
	
	public List<Pedido> listarTodos() {
		return pedidoRepository.findAll();
	}
	
	public Optional<Pedido> buscarPorId(Long id){
		return pedidoRepository.findById(id);
	}

}
