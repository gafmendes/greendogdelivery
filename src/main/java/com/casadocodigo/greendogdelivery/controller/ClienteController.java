package com.casadocodigo.greendogdelivery.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.casadocodigo.greendogdelivery.domain.Cliente;
import com.casadocodigo.greendogdelivery.repository.ClienteRepository;

@Controller
@RequestMapping("/clientes")
public class ClienteController {
	
	private final ClienteRepository clienteRepository;
	private final String CLIENTE_URI = "clientes/";

	public ClienteController(ClienteRepository clienteRepository) {
		this.clienteRepository = clienteRepository;
	}
	
	@GetMapping("/")
	public ModelAndView list() {
		Iterable<Cliente> clientes = this.clienteRepository.findAll();
		return new ModelAndView("clientes/list", "clientes", clientes);
				
	}
	
	@GetMapping("{id}")
	public ModelAndView view(@PathVariable("id") Cliente cliente) {
		return new ModelAndView("clientes/view", "cliente", cliente);
	}
	
	
	public String createForm(@ModelAttribute Cliente cliente) {
		return "cliente/form";
	}

}
