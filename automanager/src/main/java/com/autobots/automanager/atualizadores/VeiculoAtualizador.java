package com.autobots.automanager.atualizadores;

import java.util.Set;

import com.autobots.automanager.entidades.Veiculo;

public class VeiculoAtualizador {
	private StringVerificador verificador = new StringVerificador();

	public void atualizar(Veiculo veiculo, Veiculo atualizacao) {
		if (atualizacao != null) {

			if (!(atualizacao.getTipo() == null)) {
				veiculo.setTipo(atualizacao.getTipo());
			}
			
			if (!verificador.verificar(atualizacao.getModelo())) {
				veiculo.setModelo(atualizacao.getModelo());
			}
			
			if (!verificador.verificar(atualizacao.getPlaca())) {
				veiculo.setPlaca(atualizacao.getPlaca());
			}
			
			if (!(atualizacao.getProprietario() == null)) {
				veiculo.setProprietario(atualizacao.getProprietario());
			}
		}
	}

	public void atualizar(Set<Veiculo> veiculos, Set<Veiculo> atualizacoes) {
		for (Veiculo atualizacao : atualizacoes) {
			for (Veiculo veiculo : veiculos) {
				if (atualizacao.getId() != null) {
					if (atualizacao.getId() == veiculo.getId()) {
						atualizar(veiculo, atualizacao);
					}
				}
			}
		}
		

	}
}
