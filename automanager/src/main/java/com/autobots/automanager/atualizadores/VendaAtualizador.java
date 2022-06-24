package com.autobots.automanager.atualizadores;


import java.util.Set;

import com.autobots.automanager.entidades.Venda;


public class VendaAtualizador {
	private StringVerificador verificador = new StringVerificador();
	private ServicoAtualizador servicoAtualizador = new ServicoAtualizador();
	private VeiculoAtualizador veiculoAtualizador = new VeiculoAtualizador();
	private UsuarioAtualizador usuarioAtualizador = new UsuarioAtualizador();





	private void atualizarDados(Venda venda, Venda atualizacao) {
		if (!verificador.verificar(atualizacao.getIdentificacao())) {
			venda.setIdentificacao(atualizacao.getIdentificacao());
		}
		if (!(atualizacao.getCadastro() == null)) {
			venda.setCadastro(atualizacao.getCadastro());
		}
	}

	public void atualizar(Venda venda, Venda atualizacao) {
		atualizarDados(venda, atualizacao);
		servicoAtualizador.atualizar(venda.getServicos(), atualizacao.getServicos());
		veiculoAtualizador.atualizar(venda.getVeiculo(), atualizacao.getVeiculo());
		usuarioAtualizador.atualizar(venda.getFuncionario(), atualizacao.getFuncionario());
		usuarioAtualizador.atualizar(venda.getCliente(), atualizacao.getCliente());
	}
	
	public void atualizar(Set<Venda> vendas, Set<Venda> atualizacoes) {
		for (Venda atualizacao : atualizacoes) {
			for (Venda venda : vendas) {
				if (atualizacao.getId() != null) {
					if (atualizacao.getId() == venda.getId()) {
						atualizar(venda, atualizacao);
					}
				}
			}
		}
		

	}
}