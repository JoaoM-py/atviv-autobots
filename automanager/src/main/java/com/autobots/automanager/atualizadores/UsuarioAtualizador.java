package com.autobots.automanager.atualizadores;

import com.autobots.automanager.entidades.Usuario;

public class UsuarioAtualizador {
	private StringVerificador verificador = new StringVerificador();
	private EnderecoAtualizador enderecoAtualizador = new EnderecoAtualizador();
	private DocumentoAtualizador documentoAtualizador = new DocumentoAtualizador();
	private TelefoneAtualizador telefoneAtualizador = new TelefoneAtualizador();
	private EmailAtualizador emailAtualizador = new EmailAtualizador();
	private VeiculoAtualizador veiculoAtualizador = new VeiculoAtualizador();
	private MercadoriaAtualizador mercadoriaAtualizador = new MercadoriaAtualizador();
	private VendaAtualizador vendaAtualizador = new VendaAtualizador();




	private void atualizarDados(Usuario usuario, Usuario atualizacao) {
		if (!verificador.verificar(atualizacao.getNome())) {
			usuario.setNome(atualizacao.getNome());
		}
		if (!verificador.verificar(atualizacao.getNomeSocial())) {
			usuario.setNomeSocial(atualizacao.getNomeSocial());
		}
		if (!(atualizacao.getPerfis() == null)) {
			usuario.setPerfis(atualizacao.getPerfis());
		}
		
		if (!(atualizacao.getVendas() == null)) {
			usuario.setVendas(atualizacao.getVendas());
		}
	}

	public void atualizar(Usuario usuario, Usuario atualizacao) {
		atualizarDados(usuario, atualizacao);
		enderecoAtualizador.atualizar(usuario.getEndereco(), atualizacao.getEndereco());
		documentoAtualizador.atualizar(usuario.getDocumentos(), atualizacao.getDocumentos());
		telefoneAtualizador.atualizar(usuario.getTelefones(), atualizacao.getTelefones());
		emailAtualizador.atualizar(usuario.getEmails(), atualizacao.getEmails());
		veiculoAtualizador.atualizar(usuario.getVeiculos(), atualizacao.getVeiculos());
		vendaAtualizador.atualizar(usuario.getVendas(), atualizacao.getVendas());
		mercadoriaAtualizador.atualizar(usuario.getMercadorias(), atualizacao.getMercadorias());
	}
}