package com.swRestApi.model;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "planeta")
public class Planeta {
		
	@Id
    private String id;	//do mongo
	private String nome;
	private String clima;
	private String terreno; 
	private String swApiId;
	private List<String> filmes;
	
	public Planeta() {
		super();
	}
	
	public Planeta(String nome, String clima, String terreno, String swApiId) {
		super();
		this.nome = nome;
		this.clima = clima;
		this.terreno = terreno;
		this.swApiId = swApiId;
	}
	
	public Planeta(String nome, String clima, String terreno, String swApiId, List<String> filmes) {
		super();
		this.nome = nome;
		this.clima = clima;
		this.terreno = terreno;
		this.swApiId = swApiId;
		this.filmes = filmes;
	}

	public String getId() {
		return id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getClima() {
		return clima;
	}

	public void setClima(String clima) {
		this.clima = clima;
	}

	public String getTerreno() {
		return terreno;
	}

	public void setTerreno(String terreno) {
		this.terreno = terreno;
	}

	public String getSwApiId() {
		return swApiId;
	}

	public void setSwApiId(String swApiId) {
		this.swApiId = swApiId;
	}

	public List<String> getFilmes() {
		return filmes;
	}

	public void setFilmes(List<String> filmes) {
		this.filmes = filmes;
	}	
	
	
}