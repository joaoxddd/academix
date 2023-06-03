package com.comux.academix.config.data;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.comux.academix.model.Grupo;
import com.comux.academix.model.Permissao;
import com.comux.academix.model.Usuario;
import com.comux.academix.repository.UsuarioRepository;


@Component
public class DataLoader implements CommandLineRunner {
	
    private final UsuarioRepository usuarioRepository;

    public DataLoader(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }
    
    @Override
    public void run(String... args) throws Exception {

        boolean cadastros = usuarioRepository.existsById((long) 1);
        
        if(!cadastros) {
            Usuario usuario = new Usuario();
            usuario.setNome("admin");
            usuario.setEmail("admin@gmail.com");
            usuario.setSenha("Abcd1234");
            
            Permissao permissao1 = new Permissao();
            permissao1.setNome("Alterar");
            Permissao permissao2 = new Permissao();
            permissao2.setNome("Excluir");
            
            
            Grupo grupo = new Grupo();
            grupo.setNome("Professor");
            

            usuarioRepository.save(usuario);
            System.out.println(usuario);
        }
        
    }
	

}

