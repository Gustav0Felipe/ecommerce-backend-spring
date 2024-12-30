package com.app.ecommerce.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "tb_produtos")
public class Produto {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY )
	private Long id;
	
	@NotNull(message = "O Nome não pode estar Vazio.")
	@Size(max = 75)
	private String nome;

	@NotNull(message = "A Descrição não pode estar Vazia.")
	@Size(max = 255)
	private String descricao;

	@NotNull(message = "O Custo não pode estar Vazio")
	private Float custo;

	@NotNull(message = "O Valor não pode ser Nulo.")
	private Float valor;

	@NotNull(message = "O Estoque não pode ser Nulo.")
	private Integer estoque;

	@ManyToOne
	@JsonIgnoreProperties("produtos")
	@NotNull(message = "O Produto deve ter uma Categoria.")
	private Categoria categoria;
	
	@NotNull(message = "O URL da imagem não pode ser Nulo.")
	private String imagem;
	
	@NotNull(message = "O Peso não pode ser Nulo.")
    private Double peso_kg;

	@NotNull(message = "O Comprimento não pode ser Nulo. ")
    private Integer comprimento_cm;

	@NotNull(message = "A Altura não pode ser Nula.")
	private Integer altura_cm;
	
	@NotNull(message = "A Largura não pode ser Nula.")
	private Integer largura_cm;

	
	public Produto() {
		
	}
	
	public Produto(Long id, String nome, String descricao, Float custo, Float valor, 
			Integer estoque, Categoria categoria, String imagem, Double peso_kg, Integer comprimento_cm, Integer altura_cm, Integer largura_cm) {
		this.id = id;
		this.nome = nome;
		this.descricao = descricao;
		this.custo = custo;
		this.valor = valor;
		this.estoque = estoque;
		this.categoria = categoria;
		this.imagem = imagem;
		this.peso_kg = peso_kg;
		this.comprimento_cm = comprimento_cm;
		this.altura_cm = altura_cm;
		this.largura_cm = largura_cm;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Float getCusto() {
		return custo;
	}

	public void setCusto(Float custo) {
		this.custo = custo;
	}

	public Float getValor() {
		return valor;
	}

	public void setValor(Float valor) {
		this.valor = valor;
	}

	public Integer getEstoque() {
		return estoque;
	}

	public void setEstoque(Integer estoque) {
		this.estoque = estoque;
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	public String getImagem() {
		return imagem;
	}

	public void setImagem(String imagem) {
		this.imagem = imagem;
	}

	public Double getPeso_kg() {
		return peso_kg;
	}

	public void setPeso_kg(Double peso_kg) {
		this.peso_kg = peso_kg;
	}

	public Integer getComprimento_cm() {
		return comprimento_cm;
	}

	public void setComprimento_cm(Integer comprimento_cm) {
		this.comprimento_cm = comprimento_cm;
	}

	public Integer getAltura_cm() {
		return altura_cm;
	}

	public void setAltura_cm(Integer altura_cm) {
		this.altura_cm = altura_cm;
	}

	public Integer getLargura_cm() {
		return largura_cm;
	}

	public void setLargura_cm(Integer largura_cm) {
		this.largura_cm = largura_cm;
	}
	
}
