package com.fatec.scel.servico;

import java.util.List;

import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import com.fatec.scel.model.Livro;
import com.fatec.scel.model.LivroRepository;

@Service
public class LivroServicoI implements LivroServico {
	Logger logger = LogManager.getLogger(this.getClass());
	@Autowired
	LivroRepository repository;

	@Override
	public ResponseEntity<List<Livro>> consultaTodos() {
		List<Livro> livros = repository.findAll();
		return ResponseEntity.ok().body(livros);
	}

	@Override
	public ResponseEntity<Object> save(@Valid Livro livro, BindingResult result) {

		try {
			if (result.hasErrors()) {
				logger.info(">>>>>> 1. controller chamou servico save - erro detectado no bean");
				return new ResponseEntity<>(result.getFieldError().getDefaultMessage(), HttpStatus.BAD_REQUEST);
			} else {
				logger.info(">>>>>> 1. controller chamou servico save sem erro no bean validation");
				repository.save(livro);
				return new ResponseEntity<>("Cliente cadastrado", HttpStatus.CREATED);
			}
		} catch (DataIntegrityViolationException e) {
			logger.info(">>>>>> 1. controller chamou servico erro cliente ja cadastrado");
			return new ResponseEntity<>("Cliente já cadastrado", HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<>("Erro não esperado - cotacte o administrador", HttpStatus.BAD_REQUEST);
		}

	}

	@Override
	public ResponseEntity<Livro> consultaPorId(Long id) {
		logger.info(">>>>>> 2. servico consulta por id chamado");
		return repository.findById(id).map(record -> ResponseEntity.ok().body(record))
				.orElse(ResponseEntity.notFound().build());
	}

	@Override
	public ResponseEntity<Livro> consultaPorIsbn(String isbn) {
		ResponseEntity<Livro> response;
		logger.info(">>>>>> 2. servico consulta por id chamado");
		Livro livro = repository.findByIsbn(isbn);
		if (livro != null)
			response = ResponseEntity.ok().body(livro);
		else
			response = ResponseEntity.notFound().build();
		return response;
	}

	@Override
	public void delete(Long id) {
		logger.info(">>>>>> 2. servico delete por id chamado");
		repository.deleteById(id);
	}

}
