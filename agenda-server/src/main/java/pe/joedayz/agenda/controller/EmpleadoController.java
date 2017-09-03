package pe.joedayz.agenda.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import pe.joedayz.agenda.domain.Empleado;
import pe.joedayz.agenda.service.EmpleadoService;

/**
 * Empleado RESTful controller for CRUD operations.
 * 
 * @author joe
 *
 */
@RestController
public class EmpleadoController {

	@Autowired
    private EmpleadoService service;

	@GetMapping("/empleados")
    public ResponseEntity<List<Empleado>> empleado() {
		List<Empleado> empleados = this.service.list();
    	return new ResponseEntity<List<Empleado>>(empleados, HttpStatus.OK);
    }
    
    @GetMapping(value = "/empleados/{id}")
    public ResponseEntity<Empleado> getEmpleadoById(@PathVariable("id") Integer id) {
    	Empleado empleado = this.service.findById(id);
    	if(empleado == null){
    		return new ResponseEntity<Empleado>(HttpStatus.NOT_FOUND);
    	}else{
    		return new ResponseEntity<Empleado>(empleado, HttpStatus.OK);
    	}
    }
    
    @PostMapping(value = "/empleados")
	public ResponseEntity<Empleado> addEmpleado(@RequestBody Empleado empleado) {
    	//TODO: check result
    	this.service.saveEmpleado(empleado);
		return new ResponseEntity<Empleado>(empleado, HttpStatus.CREATED);
	}
    
    @PutMapping(value = "/empleados/{id}")
	public ResponseEntity<Empleado> updateEmpleado(@PathVariable Integer id, @RequestBody Empleado empleado) {
    	//TODO: check result    	
    	this.service.updateEmpleado(empleado);
		return new ResponseEntity<Empleado>(empleado, HttpStatus.OK);
	}
	
	@DeleteMapping(value = "/empleados/{id}")
	public ResponseEntity<Void> deleteEmpleado(@PathVariable("id") Integer id) {
    	//TODO: check result		
		this.service.deleteById(id);
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}
}