package com.comux.academix.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.comux.academix.exception.EmailUsuarioJaCadastradoException;
import com.comux.academix.exception.ImpossivelExcluirEntidadeException;
import com.comux.academix.exception.SenhaObrigatoriaUsuarioException;
import com.comux.academix.filter.UsuarioFilter;
import com.comux.academix.model.Grupo;
import com.comux.academix.model.Usuario;
import com.comux.academix.repository.GrupoRepository;
import com.comux.academix.repository.UsuarioRepository;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/usuarios")
public class UsuarioController {

	@Autowired
	private GrupoRepository grupos;

	@Autowired
	private UsuarioRepository usuarios;

	@RequestMapping("/novo")
	public ModelAndView novo(Usuario usuario) {
		ModelAndView mv = new ModelAndView("usuario/CadastroUsuario");
		Usuario novoUsuario = new Usuario();
		mv.addObject("usuario", novoUsuario);
		mv.addObject("grupos", grupos.findAll());
		return mv;
	}

	@PostMapping({ "/novo", "{\\d+}" })
	public ModelAndView salvar(@Valid Usuario usuario, BindingResult result, RedirectAttributes attributes,
			@RequestParam("grupos") List<Grupo> grupos) {
		if (result.hasErrors()) {
			return novo(usuario);
		}
		usuario.setGrupos(grupos);
		Optional<Usuario> usuarioExistente = usuarios.findByEmail(usuario.getEmail());
		try {

			if (usuarioExistente.isPresent()) {
				System.out.println("edit 2");
				Usuario existingUser = usuarioExistente.get();
				existingUser.setNome(usuario.getNome());
				existingUser.setEmail(usuario.getEmail());
				existingUser.setGrupos(usuario.getGrupos());
				usuarios.save(existingUser);
				attributes.addFlashAttribute("mensagem", "Usuário editado com sucesso!");
			} else {

				usuarios.save(usuario);
				attributes.addFlashAttribute("mensagem", "Usuário salvo com sucesso!");
			}

		} catch (EmailUsuarioJaCadastradoException e) {
			result.rejectValue("email", e.getMessage(), e.getMessage());
			return novo(usuario);
		} catch (SenhaObrigatoriaUsuarioException e) {
			result.rejectValue("senha", e.getMessage(), e.getMessage());
			return novo(usuario);
		}
		return new ModelAndView("redirect:/usuarios/novo");
	}

	@GetMapping("/{codigo}")
	public ModelAndView editar(@PathVariable Long codigo) {
		Optional<Usuario> usuario = usuarios.findByCodigo(codigo);
		if (usuario.isPresent()) {
			ModelAndView mv = novo(usuario.get());
			mv.addObject(usuario.get());
			return mv;
		} else {
			ModelAndView mv = new ModelAndView("usuario/PesquisaUsuario");
			return mv;
		}

	}

	@DeleteMapping("/{codigo}")
	@Transactional
	public ModelAndView excluir(@PathVariable Long codigo) {
		ModelAndView mv = new ModelAndView("usuario/PesquisaUsuario");
		try {
			usuarios.deleteById(codigo);
		} catch (ImpossivelExcluirEntidadeException e) {
			return mv;
		}

		return mv;
	}

	@GetMapping
	public ModelAndView pesquisar(UsuarioFilter usuarioFilter) {
		ModelAndView mv = new ModelAndView("usuario/PesquisaUsuario");

		List<Usuario> lista = usuarios.findAll();
		System.out.println(lista);
		mv.addObject("grupos", grupos.findAll());
		mv.addObject("lista", lista);
		return mv;
	}
}
