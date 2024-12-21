package com.app.ecommerce.model;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "tb_produtos")
public class Produto {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY )
	private int id_prod;
	
	@NotBlank(message = "O Nome não pode estar Vazio.")
	@Size(max = 75)
	private String nome_prod;

	@NotBlank(message = "A Descrição não pode estar Vazia.")
	@Size(max = 255)
	private String desc_prod;

	@NotNull(message = "O Custo não pode estar Vazio")
	private Double custo_prod;

	@NotNull(message = "O Valor não pode ser Nulo.")
	private Double val_prod;

	@NotNull(message = "O Estoque não pode ser Nulo.")
	private int qtd_estq;
	
	private String cod_cat;
	
	@NotBlank(message = "O URL da imagem não pode ser Nulo.")
	private String image;
	
	@NotNull(message = "O Peso não pode ser Nulo.")
    private Double peso_kg;

	@NotNull(message = "O Comprimento não pode ser Nulo. ")
    private int comprimento_cm;

	@NotNull(message = "A Altura não pode ser Nula.")
	private int altura_cm;
	
	@NotNull(message = "A Largura não pode ser Nula.")
	private int largura_cm;

	
	public Produto() {
		
	}
	
	public Produto(int id_prod, String nome_prod, String desc_prod, Double custo_prod, Double val_prod, 
			int qtd_estq, String cod_cat, String image, Double peso_kg, int comprimento_cm, int altura_cm, int largura_cm) {
		this.id_prod = id_prod;
		this.nome_prod = nome_prod;
		this.desc_prod = desc_prod;
		this.custo_prod = custo_prod;
		this.val_prod = val_prod;
		this.qtd_estq = qtd_estq;
		this.cod_cat = cod_cat;
		this.image = image;
		this.peso_kg = peso_kg;
		this.comprimento_cm = comprimento_cm;
		this.altura_cm = altura_cm;
		this.largura_cm = largura_cm;
	}

	public int getId_prod() {
		return id_prod;
	}

	public void setId_prod(int id_prod) {
		this.id_prod = id_prod;
	}

	public String getNome_prod() {
		return nome_prod;
	}

	public void setNome_prod(String nome_prod) {
		this.nome_prod = nome_prod;
	}

	public String getDesc_prod() {
		return desc_prod;
	}

	public void setDesc_prod(String desc_prod) {
		this.desc_prod = desc_prod;
	}

	public Double getCusto_prod() {
		return custo_prod;
	}

	public void setCusto_prod(Double custo_prod) {
		this.custo_prod = custo_prod;
	}

	public Double getVal_prod() {
		return val_prod;
	}

	public void setVal_prod(Double val_prod) {
		this.val_prod = val_prod;
	}

	public int getQtd_estq() {
		return qtd_estq;
	}

	public void setQtd_estq(int qtd_estq) {
		this.qtd_estq = qtd_estq;
	}

	public String getCod_cat() {
		return cod_cat;
	}

	public void setCod_cat(String cod_cat) {
		this.cod_cat = cod_cat;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public Double getPeso_kg() {
		return peso_kg;
	}

	public void setPeso_kg(Double peso_kg) {
		this.peso_kg = peso_kg;
	}

	public int getComprimento_cm() {
		return comprimento_cm;
	}

	public void setComprimento_cm(int comprimento_cm) {
		this.comprimento_cm = comprimento_cm;
	}

	public int getAltura_cm() {
		return altura_cm;
	}

	public void setAltura_cm(int altura_cm) {
		this.altura_cm = altura_cm;
	}

	public int getLargura_cm() {
		return largura_cm;
	}

	public void setLargura_cm(int largura_cm) {
		this.largura_cm = largura_cm;
	}
	
	
}
