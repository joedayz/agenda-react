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
import pe.joedayz.agenda.domain.Departamento;
import pe.joedayz.agenda.service.DepartamentoService;


/**
 * Department RESTful controller for CRUD operations.
 * 
 * @author joe
 *
 */
@RestController
public class DepartamentoController {
	
	@Autowired
    private DepartamentoService service;

	@GetMapping("/departamentos")
    public ResponseEntity<List<Departamento>> getDepartamentos() {
		List<Departamento> departamentos = this.service.list();
    	return new ResponseEntity<List<Departamento>>(departamentos, HttpStatus.OK);
    }
    
    @GetMapping(value = "/departamentos/{id}")
    public ResponseEntity<Departamento> getDepartamentoById(@PathVariable("id") Integer id) {
    	Departamento departamento = this.service.findById(id);
    	
    	if(departamento == null){
    		return new ResponseEntity<Departamento>(HttpStatus.NOT_FOUND);
    	}else{
    		return new ResponseEntity<Departamento>(departamento, HttpStatus.OK);
    	}    	
    }
    
    @PostMapping(value = "/departamentos")
	public ResponseEntity<Departamento> addDepartamento(@RequestBody Departamento departamento) {
    	this.service.saveDepartamento(departamento);
		return new ResponseEntity<Departamento>(departamento, HttpStatus.CREATED);
	}
    
    @PutMapping(value = "/departamentos/{id}")
	public ResponseEntity<Departamento> updateDepartamento(@PathVariable Long id, @RequestBody Departamento departamento) {
    	this.service.updateDepartamento(departamento);
		return new ResponseEntity<Departamento>(departamento, HttpStatus.OK);
	}
	
	@DeleteMapping(value = "/departamentos/{id}")
	public ResponseEntity<Void> deleteDepartamento(@PathVariable("id") Integer id) {
		this.service.deleteById(id);
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}    
}