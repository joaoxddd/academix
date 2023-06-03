package com.comux.academix.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Categoria {
	
	Administrador("Administrador"), 
	Aluno("Aluno"),
	Professor("Professor");

	private String descricao;
	
}
