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

import com.autobots.automanager.HATEOS.VeiculoLink;
import com.autobots.automanager.entidades.Veiculo;
import com.autobots.automanager.repositorios.RepositorioVeiculo;
import com.autobots.automanager.selecionadores.VeiculoSelecionador;


@RestController
public class VeiculoControle {
	
	@Autowired
	private RepositorioVeiculo repositorio;
	
	@Autowired
	private VeiculoLink linkAdd;
	
	@Autowired
	private  VeiculoSelecionador selecionador;
	
	
	@PreAuthorize("hasAnyRole('ADMIN')")
	@GetMapping("/veiculo/{id}")
	public ResponseEntity<Veiculo> obterVeiculo(@PathVariable long id) {
		List<Veiculo> veiculos = repositorio.findAll();
		Veiculo veiculo = selecionador.selecionar(veiculos, id);
		if (veiculo == null) {
			ResponseEntity<Veiculo> resposta = new ResponseEntity<>(HttpStatus.NOT_FOUND);
			return resposta;
		} else {
			linkAdd.addLink(veiculo);
			ResponseEntity<Veiculo> resposta = new ResponseEntity<Veiculo>(veiculo, HttpStatus.FOUND);
			return resposta;
		}
	}
	
	
	
	@PreAuthorize("hasAnyRole('ADMIN')")
	@GetMapping("/veiculos")
	public ResponseEntity<List<Veiculo>> obterVeiculos() {
		List<Veiculo> veiculos = repositorio.findAll();
		if (veiculos.isEmpty()) {
			ResponseEntity<List<Veiculo>> resposta = new ResponseEntity<>(HttpStatus.NOT_FOUND);
			return resposta;
		} else {
			linkAdd.addLink(veiculos);
			ResponseEntity<List<Veiculo>> resposta = new ResponseEntity<>(veiculos, HttpStatus.FOUND);
			return resposta;
		}
	}
	
	@PreAuthorize("hasAnyRole('ADMIN')")
	@PostMapping("/veiculo/cadastro")
	public ResponseEntity<?> cadastrarVeiculo(@RequestBody Veiculo veiculo) {
		HttpStatus status = HttpStatus.CONFLICT;
		if (veiculo.getId() == null) {
			repositorio.save(veiculo);
			status = HttpStatus.CREATED;
		}
		return new ResponseEntity<>(status);

	}
	
	@PreAuthorize("hasAnyRole('ADMIN')")
	@DeleteMapping("/veiculo/excluir")
	public ResponseEntity<?> excluirCliente(@RequestBody Veiculo exclusao) {
		HttpStatus status = HttpStatus.BAD_REQUEST;
		Veiculo veiculo = repositorio.getReferenceById(exclusao.getId());
		if (veiculo != null) {
			repositorio.delete(veiculo);
			status = HttpStatus.OK;
		}
		return new ResponseEntity<>(status);
	}
	
	

}
