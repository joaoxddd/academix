package com.comux.academix.repository;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.comux.academix.model.Turma;

@Repository
public interface TurmaRepository extends CustomJpaRepository<Turma, Long>{
	Optional<Turma> findByNome(String nome);
	Optional<Turma> findByCodigo(Long codigo);

}
