package com.comux.academix.controller;

import java.util.ArrayList;
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

import com.comux.academix.exception.ImpossivelExcluirEntidadeException;
import com.comux.academix.filter.TurmaFilter;
import com.comux.academix.model.Disciplina;
import com.comux.academix.model.Turma;
import com.comux.academix.model.Usuario;
import com.comux.academix.repository.DisciplinaRepository;
import com.comux.academix.repository.TurmaRepository;
import com.comux.academix.repository.UsuarioRepository;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/turmas")
public class TurmaController {

	@Autowired
	private TurmaRepository turmas;
	
	@Autowired
	private UsuarioRepository usuarios;
	
	@Autowired
	private DisciplinaRepository disciplinas;

	@RequestMapping("/novo")
	public ModelAndView novo(Turma turma) {
		ModelAndView mv = new ModelAndView("turma/CadastroTurma");
		Turma novoTurma = new Turma();
		mv.addObject("turma", novoTurma);
		mv.addObject("ministradores", usuarios.findAll());
		mv.addObject("disciplinas", disciplinas.findAll());
		return mv;
	}

	@PostMapping({ "/novo", "{\\d+}" })
	public ModelAndView salvar(@Valid Turma turma, BindingResult result, RedirectAttributes attributes, @RequestParam("ministrador") List<Usuario> ministradores, 
			@RequestParam("disciplina") List<Disciplina> disciplinas) {
		if (result.hasErrors()) {
			return novo(turma);
		}
		System.out.println("aqui");
		turma.setDisciplina(disciplinas.get(0));
		turma.setMinistrador(ministradores.get(0));
		System.out.println(turma.getDisciplina().getNome());
		System.out.println(turma.getMinistrador().getNome());
		Optional<Turma> turmaExistente = turmas.findByNome(turma.getNome());
		try {

			if (turmaExistente.isPresent()) {
				Turma existente = turmaExistente.get();
				existente.setNome(turma.getNome());
				turmas.save(existente);
				attributes.addFlashAttribute("mensagem", "Turma editada com sucesso!");
			} else {
				turmas.save(turma);
				attributes.addFlashAttribute("mensagem", "Turma salva com sucesso!");
			}

		} catch (Exception e) {
			return novo(turma);
		} 
		return new ModelAndView("redirect:/turmas/novo");
	}
	
	@GetMapping("/{codigoTurma}/{codigoAluno}")
	public ModelAndView matricularTurma(@PathVariable("codigoTurma") Long codigoTurma, @PathVariable("codigoAluno") Long codigoAluno, RedirectAttributes attributes) {
	    Optional<Turma> turmaExistente = turmas.findByCodigo(codigoTurma);
	    Optional<Usuario> usuarioExistente = usuarios.findByCodigo(codigoAluno);

	    if (!turmaExistente.isPresent() || !usuarioExistente.isPresent()) {
	        attributes.addFlashAttribute("mensagem", "Turma ou usuário não encontrado!");
	        return new ModelAndView("redirect:/turmas");
	    }

	    Turma turma = turmaExistente.get();
	    Usuario usuario = usuarioExistente.get();

	    if (turma.getMinistrados() == null) {

	        List<Usuario> ministrados = new ArrayList<>();
	        ministrados.add(usuario);
	        turma.setMinistrados(ministrados);
	    } else {
	    	List<Usuario> ministrados = turma.getMinistrados();
	    	ministrados.add(usuario);
	        turma.setMinistrados(ministrados);
	        	    }
	    
	    try {
	        turmas.save(turma);
	        attributes.addFlashAttribute("mensagem", "Usuário matriculado com sucesso!");
	    } catch (Exception e) {
	        attributes.addFlashAttribute("mensagem", "Erro ao matricular usuário!");
	    }

	    return new ModelAndView("redirect:/turmas");
	}

	@GetMapping("/{codigo}")
	public ModelAndView editar(@PathVariable Long codigo) {
		Optional<Turma> turma = turmas.findByCodigo(codigo);
		if (turma.isPresent()) {
			ModelAndView mv = novo(turma.get());
			mv.addObject(turma.get());
			return mv;
		} else {
			ModelAndView mv = new ModelAndView("turma/PesquisaTurma");
			return mv;
		}

	}

	@DeleteMapping("/{codigo}")
	@Transactional
	public ModelAndView excluir(@PathVariable Long codigo) {
		ModelAndView mv = new ModelAndView("turma/PesquisaTurma");
		try {
			turmas.deleteById(codigo);
		} catch (ImpossivelExcluirEntidadeException e) {
			return mv;
		}

		return mv;
	}

	@GetMapping
	public ModelAndView pesquisar(TurmaFilter turmaFilter) {
		ModelAndView mv = new ModelAndView("turma/PesquisaTurma");

		List<Turma> lista = turmas.findAll();
		System.out.println(lista);
		mv.addObject("lista", lista);
		return mv;
	}
}

