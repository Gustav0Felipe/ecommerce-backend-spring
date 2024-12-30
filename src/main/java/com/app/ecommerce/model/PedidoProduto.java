package com.app.ecommerce.model;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name="pedidos_produtos")
public class PedidoProduto {
	
	@EmbeddedId
	private PedidosProdutosId pedidosProdutosId;
	
	@NotNull
	private Integer qtd_prod;
	
	@NotNull
	private Double val_prod;

	public PedidosProdutosId getPedidosProdutosId() {
		return pedidosProdutosId;
	}

	public void setPedidosProdutosId(PedidosProdutosId pedidosProdutosId) {
		this.pedidosProdutosId = pedidosProdutosId;
	}

	public Integer getQtd_prod() {
		return qtd_prod;
	}

	public void setQtd_prod(Integer qtd_prod) {
		this.qtd_prod = qtd_prod;
	}

	public Double getVal_prod() {
		return val_prod;
	}

	public void setVal_prod(Double val_prod) {
		this.val_prod = val_prod;
	}
	
	
}

