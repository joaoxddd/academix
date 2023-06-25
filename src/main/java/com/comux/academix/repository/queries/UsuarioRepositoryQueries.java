package com.comux.academix.repository.queries;

import java.util.List;
import java.util.Optional;

import com.comux.academix.model.Usuario;
//import com.comux.academix.repository.filter.UsuarioFilter;

public interface UsuarioRepositoryQueries {
	
	public Optional<Usuario> porEmail(String email);
	
	public List<String> permissoes(Usuario usuario);
	
	public Usuario buscarComGrupos(Long codigo);

	List<Usuario> buscarPorNome(String nome);
	
	//List<Usuario> consultarUsuario(String nome);
	
	//Page<Usuario> consultarUsuarioFiltro(UsuarioFilter filtro, Pageable pageable);
	
}