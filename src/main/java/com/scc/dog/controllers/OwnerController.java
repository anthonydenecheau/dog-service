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

import com.scc.dog.model.Owner;
import com.scc.dog.services.OwnerService;

@RestController
public class OwnerController {

	@Autowired
    private OwnerService ownerService;
	
	
    @RequestMapping(value="/v2/owners/dog/{dogId}",method = RequestMethod.PUT)
    public void updateOwner( @PathVariable("dogId") int id, @RequestBody Owner owner) {
    	Instant instant = Instant.now();
    	ownerService.save(owner, instant.toEpochMilli());
    }

    @RequestMapping(value="/v2/owners/",method = RequestMethod.POST)
    public void saveOwner(@RequestBody Owner owner) {
    	Instant instant = Instant.now();
    	ownerService.save(owner, instant.toEpochMilli());
    }

    @RequestMapping(value="/v2/owners/dog/{dogId}",method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteOwner( @PathVariable("dogId") int dogId) {
    	ownerService.deleteByIdDog(dogId);
    }
    
}
