package com.casadocodigo.greendogdelivery.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.casadocodigo.greendogdelivery.DTO.Notificacao;
import com.casadocodigo.greendogdelivery.domain.Cliente;
import com.casadocodigo.greendogdelivery.domain.Pedido;

@Component
public class EnviaNotificacao {

	private final Notificacao notificacao;

	Logger logger = LoggerFactory.getLogger(EnviaNotificacao.class.getSimpleName());

	public EnviaNotificacao(Notificacao notificacao) {
		this.notificacao = notificacao;
	}

	public void enviaEmail(Cliente cliente, Pedido pedido) {

		logger.info("Enviar notificacao para " + cliente.getNome() + " - pedido $" + pedido.getValorTotal());

		if (notificacao.envioAtivo()) {

			/*
			 * codigo de envio
			 */

			logger.info("Notificacao enviada!");

		} else {

			logger.info("Notificacao desligada!");

		}
	}
}
