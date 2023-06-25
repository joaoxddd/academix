package com.comux.academix.repository;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.comux.academix.model.Disciplina;

@Repository
public interface DisciplinaRepository extends CustomJpaRepository<Disciplina, Long>{
	Optional<Disciplina> findByNome(String nome);

}
