package com.fatec.scel;

import static org.assertj.core.api.Assertions.assertThat;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.fatec.scel.model.Livro;
import com.fatec.scel.model.LivroRepository;
@SpringBootTest
class REQ02ConsultarLivroTests {
	
	@Autowired
	LivroRepository repository;

	@Test
	void ct01_quando_consulta_isbn_retorna_dados_do_livro() {
		//Dado - que o livro esta cadastrado
		repository.deleteAll();
		Livro livro = new Livro("3333", "Teste de Software","Delamaro");
		repository.save(livro);
		//Quando - o usuario consultar o livro pelo isbn
		Livro ro = repository.findByIsbn("3333");
		//Então - o resultado obtido da consulta deve ser igual ao objeto cadastrado
		assertThat(livro).isEqualTo(ro);
	}
	@Test
	void ct02_quando_consulta_id_retorna_dados_do_livro() {
		//Dado - que o livro esta cadastrado
		repository.deleteAll();
		Livro livro = new Livro("3333", "Teste de Software","Delamaro");
		repository.save(livro);
		//Quando - o usuario consultar o livro pelo id
		Optional<Livro> ro = repository.findById(1L);
		//Então - o resultado obtido da consulta deve ser igual ao objeto cadastrado
		//livro.setTitulo("novo tiulo");
		assertThat(livro).isEqualTo(ro.get());
	}
	@Test
	void ct03_quando_consulta_id_retorna_dados_do_livro() {
		//Dado - que o id nao esta cadastrado
		//Quando - o usuario consultar o livro pelo id
		Optional<Livro> ro = repository.findById(11L);
		//Então - o resultado obtido da consulta deve ser igual ao objeto cadastrado
		//livro.setTitulo("novo tiulo");
		assertTrue(ro.isEmpty());
		
	}
	@Test
	void ct04_quando_consulta_titulo_parcial_retorna3() {
		//Dado - que existem 3 livros cadastrados com titulo teste e 1 cadastro com titulo engenharia
		repository.deleteAll();
		Livro livro = new Livro("3333", "Teste de Software","Delamaro");
		repository.save(livro);
		livro = new Livro("4444", "Teste de Software","Delamaro");
		repository.save(livro);
		livro = new Livro("5555", "Teste de Software","Delamaro");
		repository.save(livro);
		livro = new Livro("6666", "Engenharia de Software","Pressman");
		repository.save(livro);
		//Quando - o usuario consulta pelo titulo teste
		List<Livro> ro = repository.findAllByTituloIgnoreCaseContaining("Teste");
		//Então reetorna 3 livros
	    assertThat(ro.size()).isEqualTo(3);
	}
}
