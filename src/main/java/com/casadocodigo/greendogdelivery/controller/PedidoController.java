package com.casadocodigo.greendogdelivery.controller;

import java.util.HashMap;
import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.casadocodigo.greendogdelivery.domain.Cliente;
import com.casadocodigo.greendogdelivery.domain.Item;
import com.casadocodigo.greendogdelivery.domain.Pedido;
import com.casadocodigo.greendogdelivery.repository.ClienteRepository;
import com.casadocodigo.greendogdelivery.repository.ItemRepository;
import com.casadocodigo.greendogdelivery.repository.PedidoRepository;
import com.casadocodigo.greendogdelivery.service.AtualizaEstoque;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/pedidos")
public class PedidoController {

	private final PedidoRepository pedidoRepository;
	private final ItemRepository itemRepository;
	private final ClienteRepository clienteRepository;

	private final AtualizaEstoque atualizaEstoque;
	private final String ITEM_URI = "pedidos/";

	public PedidoController(PedidoRepository pedidoRepository, ItemRepository itemRepository, ClienteRepository clienteRepository, AtualizaEstoque atualizaEstoque) {
		this.pedidoRepository = pedidoRepository;
		this.itemRepository = itemRepository;
		this.clienteRepository = clienteRepository;
        this.atualizaEstoque = atualizaEstoque;
    }

	@GetMapping("/")
	public ModelAndView list() {
		var pedidos = this.pedidoRepository.findAll();
		return new ModelAndView(ITEM_URI + "list","pedidos",pedidos);
	}

	@GetMapping("{id}")
	public ModelAndView view(@PathVariable("id") Pedido pedido) {
		return new ModelAndView(ITEM_URI + "view","pedido",pedido);
	}

	@GetMapping("/novo")
	public ModelAndView createForm(@ModelAttribute Pedido pedido) {

		var model = new HashMap<String,Object>();
		model.put("todosItens",itemRepository.findAll());
		model.put("todosClientes",clienteRepository.findAll());
		return new ModelAndView(ITEM_URI + "form",model);

	}

	@PostMapping(value = "/", params = "form")
	public ModelAndView create(@Valid Pedido pedido,BindingResult result,RedirectAttributes redirect) {
		if (result.hasErrors()) { return new ModelAndView(ITEM_URI + "form","formErrors",result.getAllErrors()); }

		if (pedido.getId() != null) {
			Optional<Pedido> pedidoParaAlterarOpt = pedidoRepository.findById(pedido.getId());
			if (pedidoParaAlterarOpt.isPresent()) {
				var pedidoParaAlterar = pedidoParaAlterarOpt.get();

				Optional<Cliente> clienteOpt = clienteRepository.findById(pedidoParaAlterar.getCliente().getId());
				if (clienteOpt.isPresent()) {
					var c = clienteOpt.get();
					pedidoParaAlterar.setItens(pedido.getItens());
					double valorTotal = 0;
					for (Item i : pedido.getItens()) {
						valorTotal += i.getPreco();
					}
					pedidoParaAlterar.setData(pedido.getData());
					pedidoParaAlterar.setValorTotal(valorTotal);
					c.getPedidos().remove(pedidoParaAlterar);
					c.getPedidos().add(pedidoParaAlterar);
					this.clienteRepository.save(c);
				}
			}
		} else {
			Optional<Cliente> clienteOpt = clienteRepository.findById(pedido.getCliente().getId());
			if (clienteOpt.isPresent()) {
				var c = clienteOpt.get();
				double valorTotal = 0;
				for (Item i : pedido.getItens()) {
					valorTotal += i.getPreco();
				}
				pedido.setValorTotal(valorTotal);
				pedido = this.pedidoRepository.save(pedido);
				c.getPedidos().add(pedido);
				this.clienteRepository.save(c);
				// atualiza estoque
				atualizaEstoque.processar(pedido);
			}
		}
		redirect.addFlashAttribute("globalMessage","Pedido gravado com sucesso");
		return new ModelAndView("redirect:/" + ITEM_URI + "{pedido.id}","pedido.id",pedido.getId());
	}

	@GetMapping(value = "remover/{id}")
	public ModelAndView remover(@PathVariable("id") Long id,RedirectAttributes redirect) {

		Optional<Pedido> pedidoParaRemoverOpt = pedidoRepository.findById(id);
		if (pedidoParaRemoverOpt.isPresent()) {
			var pedidoParaRemover = pedidoParaRemoverOpt.get();
			Optional<Cliente> clienteOpt = clienteRepository.findById(pedidoParaRemover.getCliente().getId());
			if (clienteOpt.isPresent()) {
				var c = clienteOpt.get();
				c.getPedidos().remove(pedidoParaRemover);
				this.clienteRepository.save(c);
			}
		}
		this.pedidoRepository.deleteById(id);

		var pedidos = this.pedidoRepository.findAll();

		var mv = new ModelAndView(ITEM_URI + "list","pedidos",pedidos);
		mv.addObject("globalMessage","Pedido removido com sucesso");

		return mv;
	}

	@GetMapping(value = "alterar/{id}")
	public ModelAndView alterarForm(@PathVariable("id") Pedido pedido) {

		var model = new HashMap<String,Object>();
		model.put("todosItens",itemRepository.findAll());
		model.put("todosClientes",clienteRepository.findAll());
		model.put("pedido",pedido);

		return new ModelAndView(ITEM_URI + "form",model);
	}

}