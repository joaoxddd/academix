package com.comux.academix.repository;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.comux.academix.model.Usuario;
import com.comux.academix.repository.queries.UsuarioRepositoryQueries;

@Repository
public interface UsuarioRepository extends CustomJpaRepository<Usuario, Long>, UsuarioRepositoryQueries{
	Optional<Usuario> findByEmail(String email);
	Optional<Usuario> findByCodigo(Long codigo);

}
