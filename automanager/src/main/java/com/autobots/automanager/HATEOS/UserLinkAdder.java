package com.autobots.automanager.HATEOS;

import java.util.List;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import com.autobots.automanager.controles.usuarioControle;
import com.autobots.automanager.entidades.Usuario;


@Component
public class UserLinkAdder implements LinkAdder<Usuario> {

	@Override
	public void addLink(List<Usuario> lista) {
		for (Usuario usuario : lista) {
			long id = usuario.getId();
			Link linkProprio = WebMvcLinkBuilder
					.linkTo(WebMvcLinkBuilder
							.methodOn(usuarioControle.class)
							.obterUsuario(id))
					.withSelfRel();
			usuario.add(linkProprio);
		}
	}

	@Override
	public void addLink(Usuario objeto) {
		Link linkProprio = WebMvcLinkBuilder
				.linkTo(WebMvcLinkBuilder
						.methodOn(usuarioControle.class)
						.obterUsuarios())
				.withRel("usuarios");
		objeto.add(linkProprio);
	}
}
