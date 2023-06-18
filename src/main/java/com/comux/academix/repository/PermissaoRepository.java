package com.comux.academix.repository;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.comux.academix.model.Permissao;

@Repository
public interface PermissaoRepository extends CustomJpaRepository<Permissao, Long>{
	Optional<Permissao> findByNome(String nome);

}
