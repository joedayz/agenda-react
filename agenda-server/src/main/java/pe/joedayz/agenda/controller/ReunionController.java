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
import pe.joedayz.agenda.domain.Reunion;
import pe.joedayz.agenda.service.ReunionService;


/**
 * Meeting RESTful controller for CRUD operations.
 * 
 * @author joe
 *
 */
@RestController
public class ReunionController {
	
	@Autowired
    private ReunionService service;

	@GetMapping("/reuniones")
    public ResponseEntity<List<Reunion>> getReuniones() {
		List<Reunion> reuniones = this.service.list();
    	return new ResponseEntity<List<Reunion>>(reuniones, HttpStatus.OK);
    }
    
    @GetMapping(value = "/reuniones/{id}")
    public ResponseEntity<Reunion> getReunionById(@PathVariable("id") Integer id) {
		Reunion reunion = this.service.findById(id);
    	
    	if(reunion == null){
    		return new ResponseEntity<Reunion>(HttpStatus.NOT_FOUND);
    	}else{
    		return new ResponseEntity<Reunion>(reunion, HttpStatus.OK);
    	}      	
    }
    
    @PostMapping(value = "/reuniones")
	public ResponseEntity<Reunion> addReunion(@RequestBody Reunion reunion) {
    	this.service.saveReunion(reunion);
		return new ResponseEntity<Reunion>(reunion, HttpStatus.CREATED);
	}
    
    @PutMapping(value = "/reuniones/{id}")
	public ResponseEntity<Reunion> updateReunion(@PathVariable Long id,
												 @RequestBody Reunion reunion) {
    	this.service.updateReunion(reunion);
		return new ResponseEntity<Reunion>(reunion, HttpStatus.OK);
	}
	
	@DeleteMapping(value = "/reuniones/{id}")
	public ResponseEntity<Void> deleteMeeting(@PathVariable("id") Integer id) {
		this.service.deleteById(id);
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}    
}