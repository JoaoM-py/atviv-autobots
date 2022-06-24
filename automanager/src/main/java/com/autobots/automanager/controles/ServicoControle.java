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

import com.autobots.automanager.HATEOS.ServicoLink;
import com.autobots.automanager.entidades.Servico;
import com.autobots.automanager.repositorios.RepositorioServico;
import com.autobots.automanager.selecionadores.ServicoSelecionador;


@RestController
public class ServicoControle {
	
	@Autowired
	private RepositorioServico repositorio;
	
	@Autowired
	private ServicoLink linkAdd;
	
	@Autowired
	private  ServicoSelecionador selecionador;
	
	
	@PreAuthorize("hasAnyRole('ADMIN','VENDEDOR')")
	@GetMapping("/servico/{id}")
	public ResponseEntity<Servico> obterServico(@PathVariable long id) {
		List<Servico> servicos = repositorio.findAll();
		Servico servico = selecionador.selecionar(servicos, id);
		if (servico == null) {
			ResponseEntity<Servico> resposta = new ResponseEntity<>(HttpStatus.NOT_FOUND);
			return resposta;
		} else {
			linkAdd.addLink(servico);
			ResponseEntity<Servico> resposta = new ResponseEntity<Servico>(servico, HttpStatus.FOUND);
			return resposta;
		}
	}
	
	
	
	@PreAuthorize("hasAnyRole('ADMIN','VENDEDOR')")
	@GetMapping("/servicos")
	public ResponseEntity<List<Servico>> obterServicos() {
		List<Servico> servicos = repositorio.findAll();
		if (servicos.isEmpty()) {
			ResponseEntity<List<Servico>> resposta = new ResponseEntity<>(HttpStatus.NOT_FOUND);
			return resposta;
		} else {
			linkAdd.addLink(servicos);
			ResponseEntity<List<Servico>> resposta = new ResponseEntity<>(servicos, HttpStatus.FOUND);
			return resposta;
		}
	}
	
	
	@PostMapping("/servico/cadastro")
	public ResponseEntity<?> cadastrarServico(@RequestBody Servico servico) {
		HttpStatus status = HttpStatus.CONFLICT;
		if (servico.getId() == null) {
			repositorio.save(servico);
			status = HttpStatus.CREATED;
		}
		return new ResponseEntity<>(status);

	}
	
	
	@DeleteMapping("/servico/excluir")
	public ResponseEntity<?> excluirServico(@RequestBody Servico exclusao) {
		HttpStatus status = HttpStatus.BAD_REQUEST;
		Servico servico = repositorio.getReferenceById(exclusao.getId());
		if (servico != null) {
			repositorio.delete(servico);
			status = HttpStatus.OK;
		}
		return new ResponseEntity<>(status);
	}		

}
