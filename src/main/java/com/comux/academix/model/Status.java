package com.comux.academix.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Status {
	
	Ativo("Ativo"), 
	Inativo("Inativo");

	private String descricao;
	
}
