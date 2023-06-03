package com.comux.academix.repository.queries;

import java.util.List;
import java.util.Optional;

import com.comux.academix.model.Usuario;
//import com.comux.academix.repository.filter.UsuarioFilter;

public interface UsuarioRepositoryQueries {
	
	Optional<Usuario> porEmail(String email);
	
	List<String> permissoes(Usuario usuario);
	
	//List<Usuario> consultarUsuario(String nome);
	
	//Page<Usuario> consultarUsuarioFiltro(UsuarioFilter filtro, Pageable pageable);
	
}