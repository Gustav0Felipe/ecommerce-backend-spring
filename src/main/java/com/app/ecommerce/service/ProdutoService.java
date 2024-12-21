package com.app.ecommerce.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import com.app.ecommerce.model.Produto;
import com.app.ecommerce.repository.ProdutoRepository;

@Service
public class ProdutoService {

	@Autowired
	private ProdutoRepository produtoRepository;
	
	public List<Produto> listarTodos() {
		return produtoRepository.findAll();
	}

	public Optional<Produto> buscarProduto(@PathVariable Long id ) {
		return produtoRepository.findById(id);
	}
}
