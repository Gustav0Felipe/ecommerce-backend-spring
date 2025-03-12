package com.app.ecommerce.model;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
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
		
		@Column(name = "id_prod")
		private Long id;
		
		@Column(name = "num_ped")
	    private Long num_ped;

		public PedidosProdutosId() {
			
		}
		
		public PedidosProdutosId(Long id, Long num_ped) {
			this.id = id;
			this.num_ped = num_ped;
		}

		public Long getIdProduto() {
			return id;
		}

		public void setIdProduto(Long id) {
			this.id = id;
		}

		public Long getIdPedido() {
			return num_ped;
		}

		public void setIdPedido(Long num_ped) {
			this.num_ped = num_ped;
		}
	}
	
}

