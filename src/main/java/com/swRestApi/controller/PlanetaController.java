package com.swRestApi.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.swRestApi.model.Planeta;
import com.swRestApi.repository.PlanetaRepository;
import com.swRestApi.service.SwApiService;

@RestController
@RequestMapping("/planeta")
public class PlanetaController {
    		
	@Autowired
	private PlanetaRepository planetaRepository;
	
	@Autowired
	private SwApiService swApiService;
		
	//Adicionar um planeta (com nome, clima e terreno)
	@PostMapping("/criar")
	public ResponseEntity<Object> criar(@RequestBody Planeta planeta) {
		
		if (!isPlanetaObjValid(planeta))
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Favor informar o nome, clima, terreno e id");		
					
		Optional<Planeta> planetaOpt = planetaRepository.findByNome(planeta.getNome());
						
		if (planetaOpt.isPresent())
			return ResponseEntity.status(HttpStatus.CONFLICT).body("Planeta já existe: " + planeta.getNome());
				
		planetaRepository.save(planeta);	
		
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("planeta/id/{id}"+ planeta.getSwApiId())
				.buildAndExpand(planeta.getSwApiId()).toUri();
		 
		return ResponseEntity.status(HttpStatus.CREATED).body("Planeta criado");
	}	
	
	//Adicionar varios planetas 
	@PostMapping("/criarLista")
	public ResponseEntity<Object> criarLista(@RequestBody List<Planeta> planetas) {	
				
		if (!Optional.ofNullable(planetas).isPresent())
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Lista vazia");	
		
		for (Planeta planeta : planetas) {			
			Optional<Planeta> planetaOpt = planetaRepository.findByNome(planeta.getNome());
		
			if (!planetaOpt.isPresent())
				planetaRepository.save(planeta);
		}
		return ResponseEntity.ok(planetas);
	}
	
	//Listar planetas
	@GetMapping("/listar") 
	public ResponseEntity<List<Planeta>> getAllPlanetas() {
		
		List<Planeta> all = planetaRepository.findAll();
						
		for (Planeta planeta : all) 
			planeta.setFilmes(getFilmes(planeta.getNome()));
		
		return ResponseEntity.ok(all);		 
	}
			
	//Buscar por nome
	@GetMapping("/byName/{nome}")
	public ResponseEntity<Object> nome(@PathVariable String nome) {
						
		if (StringUtils.isEmpty(nome))
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Favor informar Nome");
		
		Optional<Planeta> planeta = planetaRepository.findByNome(nome);
		
		if (!planetaRepository.findByNome(nome).isPresent())
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Planeta não encontrado: "+nome);
				
		planeta.get().setFilmes(getFilmes(planeta.get().getNome()));
						
		return ResponseEntity.ok(planeta);
	}
	
	//Buscar por ID
	@GetMapping("/byId/{swApiId}")
	@ResponseBody
	public ResponseEntity<Object> planetaId(@PathVariable String swApiId) {
						
		if (StringUtils.isEmpty(swApiId))
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Id não fornecido.");
		
		Optional<Planeta> planeta = planetaRepository.findBySwApiId(swApiId);
		
		if (!planeta.isPresent()) 
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Id não encontrado: "+swApiId);
		
		planeta.get().setFilmes(getFilmes(planeta.get().getNome()));
		
		return ResponseEntity.ok(planeta);
	}
	    				
	//Remover 
	@DeleteMapping("/{nome}")
	public ResponseEntity<Object> removerPlaneta(@PathVariable String nome) {				
		
		if (StringUtils.isEmpty(nome))
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Favor informar Nome");
		
		Optional<Planeta> planetaOpt = planetaRepository.findByNome(nome);
		
		if (!planetaOpt.isPresent())
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Planeta não encontrado: "+nome);
		
		planetaRepository.deleteByNome(nome);
		
		return ResponseEntity.ok().build();
	}
			
	//Busca os filmes do planeta
	@GetMapping("/filmes/{planeta}")
    public List<String> getFilmes(@PathVariable String planeta) {    	       	
    	return swApiService.searchFilmes(planeta);
    }
	
	private boolean isPlanetaObjValid(Planeta planeta) {		
		return !StringUtils.isEmpty(planeta.getNome()) && 
			   !StringUtils.isEmpty(planeta.getClima()) &&
			   !StringUtils.isEmpty(planeta.getTerreno()) &&
			   !StringUtils.isEmpty(planeta.getSwApiId());		
	}		
}