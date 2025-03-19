package com.app.ecommerce.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.app.ecommerce.model.PedidoProduto;

@Repository
public interface PedidoProdutoRepository extends JpaRepository<PedidoProduto, Long>{

	List<PedidoProduto> findAll();

	@Query("select p from PedidoProduto p where p.pedidosProdutosId.pedido.num_ped=:pedido_id")
	List<PedidoProduto> findAllByPedido(@Param("pedido_id") Long pedido_id);
}
