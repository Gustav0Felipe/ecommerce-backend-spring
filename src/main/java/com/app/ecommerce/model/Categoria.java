package com.app.ecommerce.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "tb_categorias")
public class Categoria {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY )
	private Long cod_cat;

	@Column
	@NotBlank(message = "O Nome da Categoria não pode estar Vazio.")
	@NotNull(message = "O Nome da Categoria não pode estar Nulo.")
	@Size(max = 75)
	private String nome_cat;

	@Column
	@NotBlank(message = "A Descrição da Categoria não pode estar Vazia.")
	@NotNull(message = "A Descrição da Categoria não pode estar Nula.")
	@Size(max = 75)
	private String descricao;

	@OneToMany(mappedBy = "categoria", cascade = CascadeType.REMOVE)
	@JsonIgnoreProperties("categoria")
	private List<Produto> produtos;
	
	public Categoria() {
		
	}

	public Categoria(Long cod_cat, String nome_cat, List<Produto> produtos) {
		this.cod_cat = cod_cat;
		this.nome_cat = nome_cat;
		this.produtos = produtos;
	}

	public Long getCod_cat() {
		return cod_cat;
	}

	public void setCod_cat(Long cod_cat) {
		this.cod_cat = cod_cat;
	}

	public String getNome_cat() {
		return nome_cat;
	}

	public void setNome_cat(String nome_cat) {
		this.nome_cat = nome_cat;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public List<Produto> getProdutos() {
		return produtos;
	}

	public void setProdutos(List<Produto> produtos) {
		this.produtos = produtos;
	}
}
