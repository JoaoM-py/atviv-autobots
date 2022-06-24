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

import com.autobots.automanager.HATEOS.TelefoneLink;
import com.autobots.automanager.entidades.Telefone;
import com.autobots.automanager.repositorios.RepositorioTelefone;
import com.autobots.automanager.selecionadores.TelefoneSelecionador;


@RestController
public class TelefoneControle {
	
	@Autowired
	private RepositorioTelefone repositorio;
	
	@Autowired
	private TelefoneLink linkAdd;
	
	@Autowired
	private  TelefoneSelecionador selecionador;
	
	
	@PreAuthorize("hasAnyRole('ADMIN')")
	@GetMapping("/telefone/{id}")
	public ResponseEntity<Telefone> obterTelefone(@PathVariable long id) {
		List<Telefone> telefones = repositorio.findAll();
		Telefone telefone = selecionador.selecionar(telefones, id);
		if (telefone == null) {
			ResponseEntity<Telefone> resposta = new ResponseEntity<>(HttpStatus.NOT_FOUND);
			return resposta;
		} else {
			linkAdd.addLink(telefone);
			ResponseEntity<Telefone> resposta = new ResponseEntity<Telefone>(telefone, HttpStatus.FOUND);
			return resposta;
		}
	}
	
	
	
	@PreAuthorize("hasAnyRole('ADMIN')")
	@GetMapping("/telefones")
	public ResponseEntity<List<Telefone>> obterTelefones() {
		List<Telefone> telefones = repositorio.findAll();
		if (telefones.isEmpty()) {
			ResponseEntity<List<Telefone>> resposta = new ResponseEntity<>(HttpStatus.NOT_FOUND);
			return resposta;
		} else {
			linkAdd.addLink(telefones);
			ResponseEntity<List<Telefone>> resposta = new ResponseEntity<>(telefones, HttpStatus.FOUND);
			return resposta;
		}
	}
	
	@PreAuthorize("hasAnyRole('ADMIN')")
	@PostMapping("/telefone/cadastro")
	public ResponseEntity<?> cadastrarTelefone(@RequestBody Telefone telefone) {
		HttpStatus status = HttpStatus.CONFLICT;
		if (telefone.getId() == null) {
			repositorio.save(telefone);
			status = HttpStatus.CREATED;
		}
		return new ResponseEntity<>(status);

	}
	
	@PreAuthorize("hasAnyRole('ADMIN')")
	@DeleteMapping("/telefone/excluir")
	public ResponseEntity<?> excluirTelefone(@RequestBody Telefone exclusao) {
		HttpStatus status = HttpStatus.BAD_REQUEST;
		Telefone telefone = repositorio.getReferenceById(exclusao.getId());
		if (telefone != null) {
			repositorio.delete(telefone);
			status = HttpStatus.OK;
		}
		return new ResponseEntity<>(status);
	}		

}
