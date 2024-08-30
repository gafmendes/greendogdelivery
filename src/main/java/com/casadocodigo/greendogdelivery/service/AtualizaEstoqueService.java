package com.casadocodigo.greendogdelivery.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestClient;

import com.casadocodigo.greendogdelivery.domain.Item;
import com.casadocodigo.greendogdelivery.domain.Pedido;
import com.casadocodigo.greendogdelivery.estoque.domain.Estoque;

public class AtualizaEstoqueService {
	
	private final Logger logger = LoggerFactory.getLogger(AtualizaEstoqueService.class.getSimpleName());
	
	public void send(Pedido pedido) {
		
		var restClient = RestClient.create();
		var resourceUrl = "http://localhost:9000/api/atualiza";
		
		for(Item item : pedido.getItens()) {
			logger.info("Enviando requisicao - atualizando estoque - [ "+item.getNome()+" ] ...");
			var estoque = new Estoque(item.getId(),1l);
			var responseEstoque = restClient
					.post()
					.uri(resourceUrl)
					.body(estoque)
					.retrieve()
					.toEntity(String.class);
			
			logger.info("Resposta: "+responseEstoque.getBody());
		}
	}

}
