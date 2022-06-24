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

import com.autobots.automanager.HATEOS.DocumentoLink;
import com.autobots.automanager.entidades.Documento;
import com.autobots.automanager.repositorios.RepositorioDocumento;
import com.autobots.automanager.selecionadores.DocumentoSelecionador;


@RestController
public class DocumentoControle {
	
	@Autowired
	private RepositorioDocumento repositorio;
	
	@Autowired
	private DocumentoLink linkAdd;
	
	@Autowired
	private  DocumentoSelecionador selecionador;
	
	
	@PreAuthorize("hasAnyRole('ADMIN')")
	@GetMapping("/documento/{id}")
	public ResponseEntity<Documento> obterDocumento(@PathVariable long id) {
		List<Documento> documentos = repositorio.findAll();
		Documento documento = selecionador.selecionar(documentos, id);
		if (documento == null) {
			ResponseEntity<Documento> resposta = new ResponseEntity<>(HttpStatus.NOT_FOUND);
			return resposta;
		} else {
			linkAdd.addLink(documento);
			ResponseEntity<Documento> resposta = new ResponseEntity<Documento>(documento, HttpStatus.FOUND);
			return resposta;
		}
	}
	
	
	
	@PreAuthorize("hasAnyRole('ADMIN')")
	@GetMapping("/documentos")
	public ResponseEntity<List<Documento>> obterDocumentos() {
		List<Documento> documentos = repositorio.findAll();
		if (documentos.isEmpty()) {
			ResponseEntity<List<Documento>> resposta = new ResponseEntity<>(HttpStatus.NOT_FOUND);
			return resposta;
		} else {
			linkAdd.addLink(documentos);
			ResponseEntity<List<Documento>> resposta = new ResponseEntity<>(documentos, HttpStatus.FOUND);
			return resposta;
		}
	}
	
	@PreAuthorize("hasAnyRole('ADMIN')")
	@PostMapping("/documento/cadastro")
	public ResponseEntity<?> cadastrarDocumento(@RequestBody Documento documento) {
		HttpStatus status = HttpStatus.CONFLICT;
		if (documento.getId() == null) {
			repositorio.save(documento);
			status = HttpStatus.CREATED;
		}
		return new ResponseEntity<>(status);

	}
	
	@PreAuthorize("hasAnyRole('ADMIN')")
	@DeleteMapping("/documento/excluir")
	public ResponseEntity<?> excluirDocumento(@RequestBody Documento exclusao) {
		HttpStatus status = HttpStatus.BAD_REQUEST;
		Documento documento = repositorio.getReferenceById(exclusao.getId());
		if (documento != null) {
			repositorio.delete(documento);
			status = HttpStatus.OK;
		}
		return new ResponseEntity<>(status);
	}		

}