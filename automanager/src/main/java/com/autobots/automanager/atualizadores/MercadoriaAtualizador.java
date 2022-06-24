package com.autobots.automanager.atualizadores;


import java.util.Set;

import com.autobots.automanager.entidades.Mercadoria;

public class MercadoriaAtualizador {
	private StringVerificador verificador = new StringVerificador();

	public void atualizar(Mercadoria mercadoria, Mercadoria atualizacao) {
		if (atualizacao != null) {

			if (!(atualizacao.getValidade() == null)) {
				mercadoria.setValidade(atualizacao.getValidade());
			}
			
			if (!verificador.verificar(atualizacao.getNome())) {
				mercadoria.setNome(atualizacao.getNome());
			}
			
			if (!(atualizacao.getFabricao() == null)) {
				mercadoria.setFabricao(atualizacao.getFabricao());
			}
			
			if (!(atualizacao.getCadastro() == null)) {
				mercadoria.setCadastro(atualizacao.getCadastro());
			}
		}
	}

	public void atualizar(Set<Mercadoria> mercadorias, Set<Mercadoria> atualizacoes) {
		for (Mercadoria atualizacao : atualizacoes) {
			for (Mercadoria mercadoria : mercadorias) {
				if (atualizacao.getId() != null) {
					if (atualizacao.getId() == mercadoria.getId()) {
						atualizar(mercadoria, atualizacao);
					}
				}
			}
		}
		

	}
}
