package com.autobots.automanager.cadastro;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.autobots.automanager.entidades.Veiculo;
import com.autobots.automanager.repositorios.RepositorioVeiculo;

@Service
public class VeiculoCadastro {
  @Autowired
  RepositorioVeiculo repositorioVeiculo;

  public Veiculo cadastrar(Veiculo veiculo) {
    Veiculo veiculoCriado = repositorioVeiculo.save(veiculo);
    return veiculoCriado;
  }
}
