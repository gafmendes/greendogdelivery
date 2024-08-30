package com.casadocodigo.greendogdelivery.config;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.casadocodigo.greendogdelivery.DTO.Notificacao;

@Component
@Profile("prod")
public class ProdNotificacaoConfig implements Notificacao{
	
	@Override
	public boolean envioAtivo() {
		return true;
	}

}
