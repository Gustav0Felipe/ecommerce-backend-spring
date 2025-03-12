package com.app.ecommerce.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.ecommerce.dto.CartItemDto;
import com.app.ecommerce.dto.PedidoDto;
import com.app.ecommerce.model.Pedido;
import com.app.ecommerce.model.PedidoProduto;
import com.app.ecommerce.model.PedidoProduto.PedidosProdutosId;
import com.app.ecommerce.model.Produto;
import com.app.ecommerce.repository.PedidoProdutoRepository;
import com.app.ecommerce.repository.PedidoRepository;
import com.app.ecommerce.repository.ProdutoRepository;

@Service
public class PedidoService {

	@Autowired
	private PedidoRepository pedidoRepository;
	
	@Autowired
	private ProdutoRepository produtoRepository;
	
	@Autowired
	private PedidoProdutoRepository pedidoProdutoRepository;
	
	public List<Pedido> listarTodos() {
		return pedidoRepository.findAll();
	}
	
	public Optional<Pedido> buscarPorId(Long id){
		return pedidoRepository.findById(id);
	}

	public Double calcularValorPedido(PedidoDto pedido) {
		Double valorTotal = 0d;
		for(CartItemDto produto : pedido.produtos()) {
			Optional<Produto> item = produtoRepository.findById(produto.id_prod());
			
			if(item.isPresent()) {
			valorTotal = valorTotal + (item.get().getValor() * produto.quantity());
			}
		}
		return valorTotal;
	}
	
	//Testar
	public void subirPedido(PedidoDto pedidoDto, Double frete) {
		
		Pedido pedido = new Pedido();
		
		pedido.setUsuario(pedidoDto.usuario());
		pedido.setData_inicial(LocalDate.now().toString());
		pedido.setStatus_ped("pendente");
		pedido.setFrete(frete);
		
		Pedido pedidoFeito = pedidoRepository.save(pedido);
		
		for(CartItemDto item : pedidoDto.produtos()) {
			PedidoProduto prod = new PedidoProduto();
			Optional<Produto> produto = produtoRepository.findById(item.id_prod());
			if(produto.isPresent()) {
				pedido.setValor_total(pedido.getValor_total() + (produto.get().getValor() * item.quantity()));
				
				prod.setPedidosProdutosId(new PedidosProdutosId(pedidoFeito.getNum_ped(), produto.get().getId()));
				prod.setQtd_prod(item.quantity());
				prod.setVal_prod(produto.get().getValor() * item.quantity());
				pedidoProdutoRepository.save(prod);
			}
		}
	};
}
