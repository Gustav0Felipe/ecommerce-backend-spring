package com.app.ecommerce.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.app.ecommerce.model.Produto;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {

	public Produto findById(int id);
	
	public Produto findByNome(@Param("nome")String nome_produto);
	
	public List<Produto> findAll(); 
	
}
