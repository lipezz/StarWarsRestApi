package com.swRestApi.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONArray;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class SwApiService {
		
	private Log logger = LogFactory.getLog(SwApiService.class);
			
	public List<String> searchFilmes(String nome) {
		
		List<String> filmes = new ArrayList<String>();
		RestTemplate restTemplate = new RestTemplate();
		System.setProperty("http.agent", "Chrome");		
		ObjectMapper objectMapper = new ObjectMapper();
		
		try {
			String response = restTemplate.getForObject("https://swapi.co/api/planets/?search="+nome, String.class);
			JsonNode jsonNode = objectMapper.readTree(response);
			if (jsonNode.get("count").asInt() == 1) {		
				String swApiRes = jsonNode.withArray("results").get(0).withArray("films").toString();
				JSONArray jsonArray = new JSONArray(swApiRes);
				for (int i=0; i<jsonArray.length(); i++) 
					filmes.add( jsonArray.getString(i) );	
			}  
        } catch (Exception e) {
        	logger.error(e);
        }
		return filmes;
	}
}