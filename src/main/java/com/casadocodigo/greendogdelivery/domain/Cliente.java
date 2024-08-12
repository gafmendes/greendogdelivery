package com.casadocodigo.greendogdelivery.domain;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotNull;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.validator.constraints.Length;

@Entity
public class Cliente {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotNull
	@Length(min=2, max=30, message = "O tamanho do nome deve ser entre {min} e {max} caracteres")
	private String nome;
	
	@NotNull
	@Length(min=2, max=300, message = "O tamanho do endereco deve ser entre {min} e {max} caracteres")
	private String endereco;
	
	@OneToMany(mappedBy = "cliente", fetch = FetchType.EAGER)
	@Cascade(CascadeType.ALL)
	private List<Pedido> pedidos;

}
