package com.comux.academix.repository.impl;

import java.util.List;
import java.util.Optional;

import com.comux.academix.model.Usuario;
import com.comux.academix.repository.queries.UsuarioRepositoryQueries;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

public class UsuarioRepositoryImpl implements UsuarioRepositoryQueries{
	
	@PersistenceContext
	private EntityManager manager;
	
	//@Autowired
	//private PaginacaoUtil paginacaoUtil;
	
	@Override
	public Optional<Usuario> porEmail(String email) {
		return manager.createQuery("from Usuario where lower(email) = lower(:email)", Usuario.class)
				.setParameter("email", email).getResultList().stream().findFirst();
	}
	
	@Override
	public List<String> permissoes(Usuario usuario) {
		return manager.createQuery(
				"select distinct p.nome from Usuario u inner join u.grupos g inner join g.permissoes p where u = :usuario", String.class)
				.setParameter("usuario", usuario)
				.getResultList();
	}
}
