package com.autobots.automanager.HATEOS;

import java.util.List;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import com.autobots.automanager.controles.ServicoControle;
import com.autobots.automanager.entidades.Servico;


@Component
public class ServicoLink implements LinkAdder<Servico> {

	@Override
	public void addLink(List<Servico> lista) {
		for (Servico servico : lista) {
			long id = servico.getId();
			Link linkProprio = WebMvcLinkBuilder
					.linkTo(WebMvcLinkBuilder
							.methodOn(ServicoControle.class)
							.obterServico(id))
					.withSelfRel();
			servico.add(linkProprio);
		}
	}

	@Override
	public void addLink(Servico objeto) {
		Link linkProprio = WebMvcLinkBuilder
				.linkTo(WebMvcLinkBuilder
						.methodOn(ServicoControle.class)
						.obterServicos())
				.withRel("servicos");
		objeto.add(linkProprio);
	}
}
