package com.app.ecommerce.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
	private String desc;

	
	public Categoria() {
	}

	public Categoria(Long cod_cat, String nome_cat) {
		this.cod_cat = cod_cat;
		this.nome_cat = nome_cat;
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

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
}
