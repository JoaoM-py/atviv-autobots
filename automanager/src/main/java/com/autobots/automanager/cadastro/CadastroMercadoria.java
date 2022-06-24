package com.autobots.automanager.cadastro;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;

import com.autobots.automanager.entidades.Mercadoria;
import com.autobots.automanager.entidades.Usuario;
import com.autobots.automanager.repositorios.RepositorioMercadoria;
import com.autobots.automanager.repositorios.RepositorioUsuario;



public class CadastroMercadoria {
	
	@Autowired
	private RepositorioMercadoria repositorio;
	@Autowired
	private RepositorioUsuario repositorioUsuario;
	
	
	
	  public Usuario cadastroMercadoria(Usuario usuario, Mercadoria mercadoria) {

		    Set<Mercadoria> listaMercadorias = usuario.getMercadorias();
		    listaMercadorias.add(mercadoria);
		    usuario.setMercadorias(listaMercadorias);
		    repositorio.save(mercadoria);
		    return repositorioUsuario.save(usuario);
		  }

}
