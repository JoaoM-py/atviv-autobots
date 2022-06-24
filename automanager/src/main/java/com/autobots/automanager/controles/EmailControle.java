package com.autobots.automanager.controles;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.autobots.automanager.HATEOS.EmailLink;
import com.autobots.automanager.entidades.Email;
import com.autobots.automanager.repositorios.RepositorioEmail;
import com.autobots.automanager.selecionadores.EmailSelecionador;


@RestController
public class EmailControle {
	
	@Autowired
	private RepositorioEmail repositorio;
	
	@Autowired
	private EmailLink linkAdd;
	
	@Autowired
	private  EmailSelecionador selecionador;
	
	
	@PreAuthorize("hasAnyRole('ADMIN')")
	@GetMapping("/email/{id}")
	public ResponseEntity<Email> obterEmail(@PathVariable long id) {
		List<Email> emails = repositorio.findAll();
		Email email = selecionador.selecionar(emails, id);
		if (email == null) {
			ResponseEntity<Email> resposta = new ResponseEntity<>(HttpStatus.NOT_FOUND);
			return resposta;
		} else {
			linkAdd.addLink(email);
			ResponseEntity<Email> resposta = new ResponseEntity<Email>(email, HttpStatus.FOUND);
			return resposta;
		}
	}
	
	
	
	@PreAuthorize("hasAnyRole('ADMIN')")
	@GetMapping("/emails")
	public ResponseEntity<List<Email>> obterEmails() {
		List<Email> emails = repositorio.findAll();
		if (emails.isEmpty()) {
			ResponseEntity<List<Email>> resposta = new ResponseEntity<>(HttpStatus.NOT_FOUND);
			return resposta;
		} else {
			linkAdd.addLink(emails);
			ResponseEntity<List<Email>> resposta = new ResponseEntity<>(emails, HttpStatus.FOUND);
			return resposta;
		}
	}
	
	@PreAuthorize("hasAnyRole('ADMIN')")
	@PostMapping("/email/cadastro")
	public ResponseEntity<?> cadastrarEmail(@RequestBody Email email) {
		HttpStatus status = HttpStatus.CONFLICT;
		if (email.getId() == null) {
			repositorio.save(email);
			status = HttpStatus.CREATED;
		}
		return new ResponseEntity<>(status);

	}
	
	@PreAuthorize("hasAnyRole('ADMIN')")
	@DeleteMapping("/email/excluir")
	public ResponseEntity<?> excluirEmail(@RequestBody Email exclusao) {
		HttpStatus status = HttpStatus.BAD_REQUEST;
		Email email = repositorio.getReferenceById(exclusao.getId());
		if (email != null) {
			repositorio.delete(email);
			status = HttpStatus.OK;
		}
		return new ResponseEntity<>(status);
	}		

}
