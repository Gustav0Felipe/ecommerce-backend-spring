package com.app.ecommerce.model;

import java.io.Serializable;

import jakarta.persistence.Embeddable;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
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
	private Float val_prod;

	public PedidoProduto() {
		
	}
	
	public PedidoProduto(PedidosProdutosId pedidosProdutosId, Integer qtd_prod, Float val_prod) {
		this.pedidosProdutosId = pedidosProdutosId;
		this.qtd_prod = qtd_prod;
		this.val_prod = val_prod;
	}

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

	public Float getVal_prod() {
		return val_prod;
	}

	public void setVal_prod(Float val_prod) {
		this.val_prod = val_prod;
	}
	
	@Embeddable
	public
	static class PedidosProdutosId implements Serializable {
		private static final long serialVersionUID = 1L;
		
		@ManyToOne
		private Pedido pedido;
		
		@ManyToOne
		private Produto produto;

		public PedidosProdutosId() {
			
		}
		
		public PedidosProdutosId(Produto produto, Pedido pedido) {
			this.pedido = pedido;
			this.produto = produto;
		}

		public Produto getProduto() {
			return produto;
		}

		public void setProduto(Produto produto) {
			this.produto = produto;
		}

		public Pedido getPedido() {
			return pedido;
		}

		public void setPedido(Pedido pedido) {
			this.pedido = pedido;
		}
	}
	
}

