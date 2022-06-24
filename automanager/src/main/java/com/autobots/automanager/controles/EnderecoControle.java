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

import com.autobots.automanager.HATEOS.EnderecoLink;
import com.autobots.automanager.entidades.Endereco;
import com.autobots.automanager.repositorios.RepositorioEndereco;
import com.autobots.automanager.selecionadores.EnderecoSelecionador;


@RestController
public class EnderecoControle {
	
	@Autowired
	private RepositorioEndereco repositorio;
	
	@Autowired
	private EnderecoLink linkAdd;
	
	@Autowired
	private  EnderecoSelecionador selecionador;
	
	
	@PreAuthorize("hasAnyRole('ADMIN')")
	@GetMapping("/endereco/{id}")
	public ResponseEntity<Endereco> obterEndereco(@PathVariable long id) {
		List<Endereco> enderecos = repositorio.findAll();
		Endereco endereco = selecionador.selecionar(enderecos, id);
		if (endereco == null) {
			ResponseEntity<Endereco> resposta = new ResponseEntity<>(HttpStatus.NOT_FOUND);
			return resposta;
		} else {
			linkAdd.addLink(endereco);
			ResponseEntity<Endereco> resposta = new ResponseEntity<Endereco>(endereco, HttpStatus.FOUND);
			return resposta;
		}
	}
	
	
	
	@PreAuthorize("hasAnyRole('ADMIN')")
	@GetMapping("/enderecos")
	public ResponseEntity<List<Endereco>> obterEnderecos() {
		List<Endereco> enderecos = repositorio.findAll();
		if (enderecos.isEmpty()) {
			ResponseEntity<List<Endereco>> resposta = new ResponseEntity<>(HttpStatus.NOT_FOUND);
			return resposta;
		} else {
			linkAdd.addLink(enderecos);
			ResponseEntity<List<Endereco>> resposta = new ResponseEntity<>(enderecos, HttpStatus.FOUND);
			return resposta;
		}
	}
	
	@PreAuthorize("hasAnyRole('ADMIN')")
	@PostMapping("/endereco/cadastro")
	public ResponseEntity<?> cadastrarEndereco(@RequestBody Endereco endereco) {
		HttpStatus status = HttpStatus.CONFLICT;
		if (endereco.getId() == null) {
			repositorio.save(endereco);
			status = HttpStatus.CREATED;
		}
		return new ResponseEntity<>(status);

	}
	
	@PreAuthorize("hasAnyRole('ADMIN')")
	@DeleteMapping("/endereco/excluir")
	public ResponseEntity<?> excluirEndereco(@RequestBody Endereco exclusao) {
		HttpStatus status = HttpStatus.BAD_REQUEST;
		Endereco endereco = repositorio.getReferenceById(exclusao.getId());
		if (endereco != null) {
			repositorio.delete(endereco);
			status = HttpStatus.OK;
		}
		return new ResponseEntity<>(status);
	}		

}
