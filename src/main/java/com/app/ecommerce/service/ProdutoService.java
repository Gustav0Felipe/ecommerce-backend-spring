package com.app.ecommerce.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import com.app.ecommerce.model.Produto;
import com.app.ecommerce.repository.ProdutoRepository;

import jakarta.validation.Valid;

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

	public Produto criarProduto(Produto produto) {
		produto.setEnabled(true);
		return produtoRepository.save(produto);
	}

	public ResponseEntity<String> deletarProduto(Long id) {
	     try {
	            Optional<Produto> produto = produtoRepository.findById(id);
	            if (produto.isEmpty()) {
	                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	            }
	            produto.get().setEnabled(false);
	            produtoRepository.save(produto.get());
	            return ResponseEntity.ok().build();
	        } catch (Exception e) {
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	        }
	}

	public ResponseEntity<String> reativarProduto(Long id) {
		 try {
	            Optional<Produto> produto = produtoRepository.findById(id);
	            if (produto.isEmpty()) {
	                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	            }
	            produto.get().setEnabled(true);
	            produtoRepository.save(produto.get());
	            return ResponseEntity.ok().build();
	        } catch (Exception e) {
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	        }
	}

	public ResponseEntity<String> editarProduto(@Valid Produto produto) {
		  try {
	            if(!produtoRepository.existsById(produto.getId())) {
	                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	            }
	            produtoRepository.save(produto);
	            return ResponseEntity.ok().build();
	        } catch (Exception e) {
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	        }
	}
}
