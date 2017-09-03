package pe.joedayz.agenda.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import pe.joedayz.agenda.domain.Departamento;
import pe.joedayz.agenda.service.DepartamentoService;


import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Tests for DepartamentoController RESTful endpoints.
 * 
 * @author joe
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class DepartamentoControllerTest {

	private MockMvc mockMvc;
	
    @Mock
    private DepartamentoService departamentoService;
    
    @InjectMocks
    private DepartamentoController departamentoController;

    @Before
    public void setup() {
    	MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders
                .standaloneSetup(departamentoController)
                .build();
    }
    
    @Test
    public void test_get_all_success() throws Exception {
        List<Departamento> departamentos = Arrays.asList(
        		new Departamento(1, "Ingenieria", "Departamento de Ingenieria"),
        		new Departamento(2, "Diseno", "Departamento de Diseno"));
        
        when(departamentoService.list()).thenReturn(departamentos);
        
        mockMvc.perform(get("/departamentos"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].nombre", is("Ingenieria")))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].nombre", is("Diseno")));
        
        verify(departamentoService, times(1)).list();
        verifyNoMoreInteractions(departamentoService);
    }
    
    @Test
    public void test_get_by_id_success() throws Exception {
    	Departamento departamento = new Departamento(1, "Diseno", "Departamento de Diseno");
        when(departamentoService.findById(1)).thenReturn(departamento);
        
        mockMvc.perform(get("/departamentos/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.nombre", is("Diseno")));
        
        verify(departamentoService, times(1)).findById(1);
        verifyNoMoreInteractions(departamentoService);
    }
    
    @Test
    public void test_get_by_id_fail_404_not_found() throws Exception {
        when(departamentoService.findById(1)).thenReturn(null);
        
        mockMvc.perform(get("/departamentos/{id}", 1))
                .andExpect(status().isNotFound());
        
        verify(departamentoService, times(1)).findById(1);
        verifyNoMoreInteractions(departamentoService);
    }
    
    @Test
    public void test_create_success() throws Exception {
    	Departamento departamento = new Departamento(2, "Diseno", "Departamento de Diseno");
        when(departamentoService.saveDepartamento(departamento)).thenReturn(Boolean.TRUE);
        
        mockMvc.perform(post("/departamentos")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(asJsonString(departamento)))
            		.andExpect(status().isCreated());
        
        verify(departamentoService, times(1)).saveDepartamento(departamento);
        verifyNoMoreInteractions(departamentoService);
    }
    
    @Test
    public void test_update_success() throws Exception {
    	Departamento departamento = new Departamento(2, "Diseno", "Departamento de Diseno");
        when(departamentoService.updateDepartamento(departamento)).thenReturn(Boolean.TRUE);
        
        mockMvc.perform(
                put("/departamentos/{id}", departamento.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(asJsonString(departamento)))
                	.andExpect(status().isOk());
        
        verify(departamentoService, times(1)).updateDepartamento(departamento);
        verifyNoMoreInteractions(departamentoService);
    }
    
    @Test
    public void test_delete_success() throws Exception {
    	Departamento departamento = new Departamento(1, "Diseno", "Departamento de Diseno");
        when(departamentoService.deleteById(departamento.getId())).thenReturn(Boolean.TRUE);
        
        mockMvc.perform(
                delete("/departamentos/{id}", departamento.getId()))
                .andExpect(status().isNoContent());
        
        verify(departamentoService, times(1)).deleteById(departamento.getId());
        verifyNoMoreInteractions(departamentoService);
    }    
    
    public static String asJsonString(final Object obj) {
        try {
            final ObjectMapper mapper = new ObjectMapper();
            return mapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }    
}
