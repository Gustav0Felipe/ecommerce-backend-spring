package com.app.ecommerce.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.ecommerce.model.Produto;
import com.app.ecommerce.service.ProdutoService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/produtos")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ProdutoController {

	@Autowired 
	private ProdutoService produtoService;
	
	@GetMapping
	public ResponseEntity<List<Produto>> listarTodos(){
		return ResponseEntity.ok(produtoService.listarTodos());
	}
	
	@GetMapping("/produto/{id}")
	public ResponseEntity<Produto> buscarProduto(@PathVariable Long id ) {
		return produtoService.buscarProduto(id)
				.map(produto -> ResponseEntity.ok(produto))
				.orElse(ResponseEntity.notFound().build()); 
	}
	
	@PostMapping
	public ResponseEntity<Produto> criarProduto(@Valid Produto produto){
		return ResponseEntity.ok(produtoService.criarProduto(produto));
	}
}
