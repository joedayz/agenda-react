package pe.joedayz.agenda.controller;

import static org.mockito.Mockito.*;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Arrays;
import java.util.List;

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

import com.fasterxml.jackson.databind.ObjectMapper;

import pe.joedayz.agenda.controller.EmpleadoController;
import pe.joedayz.agenda.domain.Empleado;
import pe.joedayz.agenda.service.EmpleadoService;

/**
 * Tests for EmpleadoController RESTful endpoints.
 * 
 * @author joe
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class EmpleadoControllerTest {

	private MockMvc mockMvc;
	
    @Mock
    private EmpleadoService empleadoService;
    
    @InjectMocks
    private EmpleadoController empleadoController;

    @Before
    public void setup() {
    	MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders
                .standaloneSetup(empleadoController)
                .build();
    }
    
    @Test
    public void test_get_all_success() throws Exception {
        List<Empleado> empleados = Arrays.asList(
        		new Empleado(1, "Jose", "Diaz", 12000.00, 1),
        		new Empleado(2, "Miryan", "Ramirez", 10000.00, 1));
        
        when(empleadoService.list()).thenReturn(empleados);
        
        mockMvc.perform(get("/empleados"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].nombre", is("Jose")))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].nombre", is("Miryan")));
        
        verify(empleadoService, times(1)).list();
        verifyNoMoreInteractions(empleadoService);
    }
    
    @Test
    public void test_get_by_id_success() throws Exception {
    	Empleado empleado = new Empleado(1, "Jose", "Diaz", 12000.00, 1);
        when(empleadoService.findById(1)).thenReturn(empleado);
        
        mockMvc.perform(get("/empleados/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.nombre", is("Jose")));
        
        verify(empleadoService, times(1)).findById(1);
        verifyNoMoreInteractions(empleadoService);
    }
    
    @Test
    public void test_get_by_id_fail_404_not_found() throws Exception {
        when(empleadoService.findById(1)).thenReturn(null);
        
        mockMvc.perform(get("/empleados/{id}", 1))
                .andExpect(status().isNotFound());
        
        verify(empleadoService, times(1)).findById(1);
        verifyNoMoreInteractions(empleadoService);
    }
    
    @Test
    public void test_create_success() throws Exception {
    	Empleado empleado = new Empleado(1, "Jose", "Diaz", 12000.00, 1);
        when(empleadoService.saveEmpleado(empleado)).thenReturn(Boolean.TRUE);
        
        mockMvc.perform(post("/empleados")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(asJsonString(empleado)))
            		.andExpect(status().isCreated());
        
        verify(empleadoService, times(1)).saveEmpleado(empleado);
        verifyNoMoreInteractions(empleadoService);
    }
    
    @Test
    public void test_update_success() throws Exception {
    	Empleado empleado = new Empleado(1, "Jose", "Diaz", 12000.00, 1);
        when(empleadoService.updateEmpleado(empleado)).thenReturn(Boolean.TRUE);
        
        mockMvc.perform(
                put("/empleados/{id}", empleado.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(asJsonString(empleado)))
                	.andExpect(status().isOk());
        
        verify(empleadoService, times(1)).updateEmpleado(empleado);
        verifyNoMoreInteractions(empleadoService);
    }
    
    @Test
    public void test_delete_success() throws Exception {
    	Empleado empleado = new Empleado(1, "Jose", "Diaz", 12000.00, 1);
        when(empleadoService.deleteById(empleado.getId())).thenReturn(Boolean.TRUE);
        
        mockMvc.perform(
                delete("/empleados/{id}", empleado.getId()))
                .andExpect(status().isNoContent());
        
        verify(empleadoService, times(1)).deleteById(empleado.getId());
        verifyNoMoreInteractions(empleadoService);
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
