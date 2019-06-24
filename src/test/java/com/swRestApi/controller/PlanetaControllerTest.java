package com.swRestApi.controller;

import static org.hamcrest.core.Is.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import com.swRestApi.SwRestApiApplication;
import com.swRestApi.model.Planeta;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = SwRestApiApplication.class)
@AutoConfigureMockMvc
public class PlanetaControllerTest extends AbstractTest {
	
	@Autowired
    private MockMvc mockMvc;

    @MockBean
    private PlanetaControllerTest planetaController;       
   
    //recuperar
                   
    @Test
    public void get_valid_planeta_id() throws Exception {
    	mockMvc.perform(get("/planeta/id/2")
    		.contentType(MediaType.APPLICATION_JSON)    
        	.accept(MediaType.APPLICATION_JSON))           
	        .andExpect(status().isOk())
	        .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
	        .andExpect(jsonPath("$.nome", is("Alderaan")))
	        .andExpect(jsonPath("$.swApiId", is("2")));
    }
    
    @Test
    public void get_valid_planeta_nome() throws Exception {
    	mockMvc.perform(get("/planeta/nome/Geonosis")
    		.contentType(MediaType.APPLICATION_JSON)    
        	.accept(MediaType.APPLICATION_JSON))           
	        .andExpect(status().isOk())
	        .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
	        .andExpect(jsonPath("$.nome", is("Geonosis")))
	        .andExpect(jsonPath("$.swApiId", is("11")));
    }
    
    @Test
    public void get_planeta_list() throws Exception {    
        mockMvc.perform(get("/planeta/listar")
        	.contentType(MediaType.APPLICATION_JSON)    
        	.accept(MediaType.APPLICATION_JSON))
        	.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
        	.andExpect(status().isOk());
    }
    
    @Test
    public void not_get_unknown_planeta() throws Exception {
        mockMvc.perform(get("/planeta/id/000-1234")
        	.contentType(MediaType.APPLICATION_JSON)    
        	.accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
            .andDo(MockMvcResultHandlers.print());
    }
    
	//criar
    
    @Test
    public void create_valid_planeta() throws Exception {            
        List<String> filmes = Arrays.asList("https://swapi.co/api/films/z/","https://swapi.co/api/films/zz/");    	
    	Planeta planeta = new Planeta("PlanetaTeste1","temperate","grassy hills, swamps, forests, mountains","384",filmes);
    	
    	mockMvc.perform(post("/planeta/criar")
    		.content(mapToJson(planeta))
    		.contentType(MediaType.APPLICATION_JSON)    
        	.accept(MediaType.APPLICATION_JSON))  		      
    		.andExpect(status().isCreated())
    		.andDo(MockMvcResultHandlers.print());
    }
  
    @Test
    public void not_create_invalid_planeta() throws Exception {    	        	
        mockMvc.perform(post("/planeta/criar")
    		.content(mapToJson(null))
    		.contentType(MediaType.APPLICATION_JSON)    
        	.accept(MediaType.APPLICATION_JSON))
        	.andExpect(status().isBadRequest())
        	.andDo(MockMvcResultHandlers.print());
    }
   
    @Test
    public void not_create_existing_planeta() throws Exception {
       	Planeta planeta = new Planeta("Kamino","temperate","ocean","10");
	    mockMvc.perform(post("/planeta/criar")
	    	.content(mapToJson(planeta))
	    	.contentType(MediaType.APPLICATION_JSON)    
        	.accept(MediaType.APPLICATION_JSON))
	        .andExpect(status().isConflict())
	        .andDo(MockMvcResultHandlers.print());
    } 
    
    //remover
    
    @Test
    public void remove_existing_planeta() throws Exception {
	    mockMvc.perform(delete("/planeta/PlanetaTesteDeletar")
	    	.contentType(MediaType.APPLICATION_JSON))
	        .andExpect(status().isOk())
	        .andDo(MockMvcResultHandlers.print());
    } 
    
    @Test
    public void not_remove_invalid_planeta() throws Exception {
    	 mockMvc.perform(delete("/planeta/Invalid-Planet")
	    	.contentType(MediaType.APPLICATION_JSON))
	        .andExpect(status().isNotFound())
	        .andDo(MockMvcResultHandlers.print());
    }     
}