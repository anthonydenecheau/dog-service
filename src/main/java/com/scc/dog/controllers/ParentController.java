package com.scc.dog.controllers;

import java.time.Instant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.scc.dog.model.Parent;
import com.scc.dog.services.ParentService;

@RestController
public class ParentController {

	@Autowired
    private ParentService parentService;
	
	
    @RequestMapping(value="/v2/parents/{id}",method = RequestMethod.PUT)
    public void updateParent( @PathVariable("id") int id, @RequestBody Parent parent) {
    	Instant instant = Instant.now();
    	parentService.save(parent, instant.toEpochMilli());
    }

    @RequestMapping(value="/v2/parents/",method = RequestMethod.POST)
    public void saveParent(@RequestBody Parent parent) {
    	Instant instant = Instant.now();
    	parentService.save(parent, instant.toEpochMilli());
    }

    @RequestMapping(value="/v2/parents/{id}",method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteParent( @PathVariable("id") int id) {
    	parentService.deleteById(id);
    }
    
}
