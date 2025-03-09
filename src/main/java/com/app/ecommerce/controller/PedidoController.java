package com.app.ecommerce.controller;

import java.util.List;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.ecommerce.dto.PedidoDto;
import com.app.ecommerce.model.Pedido;
import com.app.ecommerce.service.PedidoService;
import com.app.ecommerce.service.PixService;

import jakarta.transaction.Transactional;

@RestController
@RequestMapping("/pedidos")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class PedidoController {
	
	@Autowired
	private PedidoService pedidoService;
	
	@Autowired
	private PixService pixService;
	
	@GetMapping
	public ResponseEntity<List<Pedido>> listarTodos(){
		return ResponseEntity.ok(pedidoService.listarTodos());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Pedido> buscarPorId(@PathVariable Long id){
		return pedidoService.buscarPorId(id)
				.map(pedido -> ResponseEntity.ok(pedido))
				.orElse(ResponseEntity.notFound().build())
				;
	}
	
	@Transactional
	@PostMapping
	public ResponseEntity<String> subirPedido(@RequestBody PedidoDto pedido){
		
		System.out.println("Cep: " + pedido.cep());
		Double valorTotal = pedidoService.calcularValorTotal(pedido);
		JSONObject response = pixService.pixCreateCharge(pedido , valorTotal);
		
		if(response == null) {
			response = new JSONObject();
			response.put("Mensagem", "Pix não foi gerado.");
		}else {
			pedidoService.subirPedido(pedido);
		}
		return ResponseEntity.ok(response.toString());
	}
}
