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

import com.autobots.automanager.HATEOS.EmpresaLink;
import com.autobots.automanager.entidades.Empresa;
import com.autobots.automanager.repositorios.RepositorioEmpresa;
import com.autobots.automanager.selecionadores.EmpresaSelecionador;


@RestController
public class EmpresaControle {
	
	@Autowired
	private RepositorioEmpresa repositorio;
	
	@Autowired
	private EmpresaLink linkAdd;
	
	@Autowired
	private  EmpresaSelecionador selecionador;
	
	
	@PreAuthorize("hasAnyRole('ADMIN')")
	@GetMapping("/empresa/{id}")
	public ResponseEntity<Empresa> obterEmpresa(@PathVariable long id) {
		List<Empresa> empresas = repositorio.findAll();
		Empresa empresa = selecionador.selecionar(empresas, id);
		if (empresa == null) {
			ResponseEntity<Empresa> resposta = new ResponseEntity<>(HttpStatus.NOT_FOUND);
			return resposta;
		} else {
			linkAdd.addLink(empresa);
			ResponseEntity<Empresa> resposta = new ResponseEntity<Empresa>(empresa, HttpStatus.FOUND);
			return resposta;
		}
	}
	
	
	
	@PreAuthorize("hasAnyRole('ADMIN')")
	@GetMapping("/empresas")
	public ResponseEntity<List<Empresa>> obterEmpresas() {
		List<Empresa> empresas = repositorio.findAll();
		if (empresas.isEmpty()) {
			ResponseEntity<List<Empresa>> resposta = new ResponseEntity<>(HttpStatus.NOT_FOUND);
			return resposta;
		} else {
			linkAdd.addLink(empresas);
			ResponseEntity<List<Empresa>> resposta = new ResponseEntity<>(empresas, HttpStatus.FOUND);
			return resposta;
		}
	}
	
	@PreAuthorize("hasAnyRole('ADMIN')")
	@PostMapping("/empresa/cadastro")
	public ResponseEntity<?> cadastrarEmpresa(@RequestBody Empresa empresa) {
		HttpStatus status = HttpStatus.CONFLICT;
		if (empresa.getId() == null) {
			repositorio.save(empresa);
			status = HttpStatus.CREATED;
		}
		return new ResponseEntity<>(status);

	}
	
	@PreAuthorize("hasAnyRole('ADMIN')")
	@DeleteMapping("/empresa/excluir")
	public ResponseEntity<?> excluirEmpresa(@RequestBody Empresa exclusao) {
		HttpStatus status = HttpStatus.BAD_REQUEST;
		Empresa empresa = repositorio.getReferenceById(exclusao.getId());
		if (empresa != null) {
			repositorio.delete(empresa);
			status = HttpStatus.OK;
		}
		return new ResponseEntity<>(status);
	}		

}
