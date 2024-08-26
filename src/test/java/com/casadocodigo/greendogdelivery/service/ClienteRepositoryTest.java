package com.casadocodigo.greendogdelivery.service;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.casadocodigo.greendogdelivery.domain.Cliente;
import com.casadocodigo.greendogdelivery.repository.ClienteRepository;

@SpringBootTest
class ClienteRepositoryTest {
	
	@Autowired
	ClienteRepository repository;

	@Test
	public void buscaClientesCadastrados() {

		Page<Cliente> clientes = this.repository.findAll(PageRequest.of(0, 10));
		assertThat(clientes.getTotalElements()).isGreaterThan(1L);

	}

	@Test
	public void buscaClienteFernando() {
		
		var clienteNaoEncontrado = this.repository.findByNome("Fernando");
		assertThat(clienteNaoEncontrado).isNull();

		var cliente = this.repository.findByNome("Fernando Boaglio");
		assertThat(cliente).isNotNull();
		assertThat(cliente.getNome()).isEqualTo("Fernando Boaglio");
		assertThat(cliente.getEndereco()).isEqualTo("Sampa");
		
	}
 
}