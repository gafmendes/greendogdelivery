package com.casadocodigo.greendogdelivery.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.casadocodigo.greendogdelivery.repository.PedidoRepository;

@Controller
@RequestMapping("/pedidos")
public class PedidoController {
	
	private final PedidoRepository pedidoRepository;
	private final String PEDIDO_URI = "/pedidos";
	
	

}
