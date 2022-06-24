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

import com.autobots.automanager.HATEOS.MercadoriaLink;
import com.autobots.automanager.entidades.Mercadoria;
import com.autobots.automanager.repositorios.RepositorioMercadoria;
import com.autobots.automanager.selecionadores.MercadoriaSelecionador;


@RestController
public class MercadoriaControle {
	
	@Autowired
	private RepositorioMercadoria repositorio;
	
	@Autowired
	private MercadoriaLink linkAdd;
	
	@Autowired
	private  MercadoriaSelecionador selecionador;
	
	
	@PreAuthorize("hasAnyRole('ADMIN','GERENTE', 'VENDEDOR')")
	@GetMapping("/mercadoria/{id}")
	public ResponseEntity<Mercadoria> obterMercadoria(@PathVariable long id) {
		List<Mercadoria> mercadorias = repositorio.findAll();
		Mercadoria mercadoria = selecionador.selecionar(mercadorias, id);
		if (mercadoria == null) {
			ResponseEntity<Mercadoria> resposta = new ResponseEntity<>(HttpStatus.NOT_FOUND);
			return resposta;
		} else {
			linkAdd.addLink(mercadoria);
			ResponseEntity<Mercadoria> resposta = new ResponseEntity<Mercadoria>(mercadoria, HttpStatus.FOUND);
			return resposta;
		}
	}
	
	
	
	@PreAuthorize("hasAnyRole('ADMIN','GERENTE','VENDEDOR')")
	@GetMapping("/mercadorias")
	public ResponseEntity<List<Mercadoria>> obterMercadorias() {
		List<Mercadoria> mercadorias = repositorio.findAll();
		if (mercadorias.isEmpty()) {
			ResponseEntity<List<Mercadoria>> resposta = new ResponseEntity<>(HttpStatus.NOT_FOUND);
			return resposta;
		} else {
			linkAdd.addLink(mercadorias);
			ResponseEntity<List<Mercadoria>> resposta = new ResponseEntity<>(mercadorias, HttpStatus.FOUND);
			return resposta;
		}
	}
	
	@PreAuthorize("hasAnyRole('ADMIN','GERENTE')")
	@PostMapping("/mercadoria/cadastro")
	public ResponseEntity<?> cadastrarMercadoria(@RequestBody Mercadoria mercadoria) {
		HttpStatus status = HttpStatus.CONFLICT;
		if (mercadoria.getId() == null) {
			repositorio.save(mercadoria);
			status = HttpStatus.CREATED;
		}
		return new ResponseEntity<>(status);

	}
	
	@PreAuthorize("hasAnyRole('ADMIN','GERENTE')")
	@DeleteMapping("/mercadoria/excluir")
	public ResponseEntity<?> excluirCliente(@RequestBody Mercadoria exclusao) {
		HttpStatus status = HttpStatus.BAD_REQUEST;
		Mercadoria mercadoria = repositorio.getReferenceById(exclusao.getId());
		if (mercadoria != null) {
			repositorio.delete(mercadoria);
			status = HttpStatus.OK;
		}
		return new ResponseEntity<>(status);
	}
	
	

}
