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

import com.scc.dog.model.Breeder;
import com.scc.dog.services.BreederService;

@RestController
public class BreederController {

	@Autowired
    private BreederService breederService;
	
	
    @RequestMapping(value="/v2/breeders/dog/{dogId}",method = RequestMethod.PUT)
    public void updateBreeder( @PathVariable("dogId") int id, @RequestBody Breeder breeder) {
    	Instant instant = Instant.now();
    	breederService.save(breeder, instant.toEpochMilli());
    }

    @RequestMapping(value="/v2/breeders/",method = RequestMethod.POST)
    public void saveBreeder(@RequestBody Breeder breeder) {
    	Instant instant = Instant.now();
    	breederService.save(breeder, instant.toEpochMilli());
    }

    @RequestMapping(value="/v2/breeders/dog/{dogId}",method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBreeder( @PathVariable("dogId") int dogId) {
    	breederService.deleteByIdDog(dogId);
    }
    
}
