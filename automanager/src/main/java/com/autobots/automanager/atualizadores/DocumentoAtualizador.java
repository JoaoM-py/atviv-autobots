package com.autobots.automanager.atualizadores;


import java.util.Set;

import com.autobots.automanager.entidades.Documento;



public class DocumentoAtualizador {
	private StringVerificador verificador = new StringVerificador();

	public void atualizar(Documento documento, Documento atualizacao) {
		if (atualizacao != null) {

			if (!verificador.verificar(atualizacao.getNumero())) {
				documento.setNumero(atualizacao.getNumero());
			}
			
			if (!(atualizacao.getTipo() == null)) {
				documento.setTipo(atualizacao.getTipo());
			}
			
			if (!(atualizacao.getDataEmissao() == null)) {
				documento.setDataEmissao(atualizacao.getDataEmissao());
			}
			
			
		}
	}

	public void atualizar(Set<Documento> documentos, Set<Documento> atualizacoes) {
		for (Documento atualizacao : atualizacoes) {
			for (Documento documento : documentos) {
				if (atualizacao.getId() != null) {
					if (atualizacao.getId() == documento.getId()) {
						atualizar(documento, atualizacao);
					}
				}
			}
		}
	}
}
