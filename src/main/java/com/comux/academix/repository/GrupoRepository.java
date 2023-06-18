package com.comux.academix.repository;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.comux.academix.model.Grupo;

@Repository
public interface GrupoRepository extends CustomJpaRepository<Grupo, Long>{
	Optional<Grupo> findByNome(String nome);

}
