package com.app.ecommerce.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.app.ecommerce.model.Produto;

public interface ProdutoRepository extends JpaRepository<Produto, Integer> {

	Produto findById(int id);
	
	Produto findByNome(@Param("nome_prod")String nomeProduto);
	
	List<Produto> findAll(); 
	
}
