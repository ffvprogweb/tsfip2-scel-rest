package com.fatec.scel.servico;

import java.util.List;
import java.util.Optional;

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
	public List<Livro> consultaTodos() {
		return repository.findAll();
		
	}

	@Override
	public CodigoDeRetorno save(@Valid Livro livro, BindingResult result) {
		try {
			if (result.hasErrors()) {
				logger.info(">>>>>> 2. controller chamou servico save - erro detectado no bean");
				return new CodigoDeRetorno(result.getFieldError().getDefaultMessage(), HttpStatus.BAD_REQUEST);
			} else {
				logger.info(">>>>>> 2. controller chamou servico save sem erro no bean validation");
				repository.save(livro);
				return new CodigoDeRetorno("Cliente cadastrado", HttpStatus.CREATED);
			}
		} catch (DataIntegrityViolationException e) {
			logger.info(">>>>>> 2. controller chamou servico erro cliente ja cadastrado");
			return new CodigoDeRetorno("Cliente já cadastrado", HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new CodigoDeRetorno("Erro não esperado", HttpStatus.BAD_REQUEST);
		}

	}

	@Override
	public Optional<Livro> consultaPorId(Long id) {
		logger.info(">>>>>> 2. servico consulta por id chamado");
		return repository.findById(id);
	}

	@Override
	public Livro consultaPorIsbn(String isbn) {
		logger.info(">>>>>> 2. servico consulta por id chamado");
		return repository.findByIsbn(isbn);
		
	}

	@Override
	public void delete(Long id) {
		logger.info(">>>>>> 2. servico delete por id chamado");
		repository.deleteById(id);
	}

}
