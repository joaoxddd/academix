package com.comux.academix.config.data;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.comux.academix.model.Grupo;
import com.comux.academix.model.Permissao;
import com.comux.academix.model.Usuario;
import com.comux.academix.repository.GrupoRepository;
import com.comux.academix.repository.PermissaoRepository;
import com.comux.academix.repository.UsuarioRepository;


@Component
public class DataLoader implements CommandLineRunner {
	
    private final UsuarioRepository usuarioRepository;
    
    private final PermissaoRepository permissaoRepository;
    
    private final GrupoRepository grupoRepository;

    public DataLoader(UsuarioRepository usuarioRepository, PermissaoRepository permissaoRepository, GrupoRepository grupoRepository) {
        this.usuarioRepository = usuarioRepository;
        this.permissaoRepository = permissaoRepository;
        this.grupoRepository = grupoRepository;
    }
    
    @Override
    public void run(String... args) throws Exception {

        boolean cadastros = usuarioRepository.existsById((long) 1);
        
        if(!cadastros) {
            Usuario usuario1 = new Usuario();
            usuario1.setNome("admin");
            usuario1.setEmail("admin@gmail.com");
            usuario1.setSenha("Abcd1234");
            usuario1.setConfirmacaoSenha("Abcd1234");
            
            Permissao permissao1 = new Permissao();
            permissao1.setNome("Alterar");
            permissaoRepository.save(permissao1);
            
            Permissao permissao2 = new Permissao();
            permissao2.setNome("Excluir");
            permissaoRepository.save(permissao2);
            
            Permissao permissao3 = new Permissao();
            permissao3.setNome("Visualizar");
            permissaoRepository.save(permissao3);

            List<Permissao> permissoesAdmin = new ArrayList<>();
            permissoesAdmin.add(permissao1);
            permissoesAdmin.add(permissao2);
            permissoesAdmin.add(permissao3);
            Grupo grupo1 = new Grupo();
            grupo1.setNome("Administrador");
            grupo1.setPermissoes(permissoesAdmin);
            grupoRepository.save(grupo1);
            
            List<Permissao> permissoesAluno = new ArrayList<>();
            permissoesAluno.add(permissao3);
            Grupo grupo2 = new Grupo();
            grupo2.setNome("Aluno");
            grupo2.setPermissoes(permissoesAluno);
            grupoRepository.save(grupo2);
            
            List<Grupo> grupoUsuario1 = new ArrayList<>();
            grupoUsuario1.add(grupo1);
            usuario1.setGrupos(grupoUsuario1);
            usuarioRepository.save(usuario1);
            System.out.println(usuario1);
        }
        
    }
	

}

