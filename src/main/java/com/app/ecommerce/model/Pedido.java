package com.app.ecommerce.model;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;


@Table(name = "pedidos")
@Entity
public class Pedido {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY )
	private Long num_ped;
	
	@ManyToOne
	@JsonIgnoreProperties("pedidos")
	private Usuario usuario;
	
	@Column
	@NotBlank
	@DateTimeFormat
	private String data_inicial;
	
	@Column
	@DateTimeFormat
	private String data_final;		
	
	@Column
	@NotBlank
	@Size(max = 30)
	private String status_ped;

	@Column
	@NotNull
	private double valor_total;
	
	
	public Pedido() {
	}

	public Pedido(Usuario usuario, String dataInicial, String dataFinal,
			String status) {
		this.usuario = usuario;
		this.data_inicial = dataInicial;
		this.data_final = dataFinal;
		this.status_ped = status;
	}

	public Long getNum_ped() {
		return num_ped;
	}

	public void setNum_ped(Long num_ped) {
		this.num_ped = num_ped;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public String getData_inicial() {
		return data_inicial;
	}

	public void setData_inicial(String data_inicial) {
		this.data_inicial = data_inicial;
	}

	public String getData_final() {
		return data_final;
	}

	public void setData_final(String data_final) {
		this.data_final = data_final;
	}

	public String getStatus_ped() {
		return status_ped;
	}

	public void setStatus_ped(String status_ped) {
		this.status_ped = status_ped;
	}

	public double getValor_total() {
		return valor_total;
	}

	public void setValor_total(double valor_total) {
		this.valor_total = valor_total;
	}
}
