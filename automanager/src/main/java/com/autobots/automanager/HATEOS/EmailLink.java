package com.autobots.automanager.HATEOS;

import java.util.List;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import com.autobots.automanager.controles.EmailControle;
import com.autobots.automanager.entidades.Email;


@Component
public class EmailLink implements LinkAdder<Email> {

	@Override
	public void addLink(List<Email> lista) {
		for (Email email : lista) {
			long id = email.getId();
			Link linkProprio = WebMvcLinkBuilder
					.linkTo(WebMvcLinkBuilder
							.methodOn(EmailControle.class)
							.obterEmail(id))
					.withSelfRel();
			email.add(linkProprio);
		}
	}

	@Override
	public void addLink(Email objeto) {
		Link linkProprio = WebMvcLinkBuilder
				.linkTo(WebMvcLinkBuilder
						.methodOn(EmailControle.class)
						.obterEmails())
				.withRel("emails");
		objeto.add(linkProprio);
	}
}
