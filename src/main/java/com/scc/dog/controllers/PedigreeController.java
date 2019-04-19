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

import com.scc.dog.model.Pedigree;
import com.scc.dog.services.PedigreeService;

@RestController
public class PedigreeController {

   @Autowired
   private PedigreeService pedigreeService;

   @RequestMapping(value = "/v2/pedigrees/{id}", method = RequestMethod.PUT)
   public void updatePedigree(@PathVariable("id") long id, @RequestBody Pedigree pedigree) {
      Instant instant = Instant.now();
      pedigreeService.save(pedigree, instant.toEpochMilli());
   }

   @RequestMapping(value = "/v2/pedigrees/", method = RequestMethod.POST)
   public void savePedigree(@RequestBody Pedigree pedigree) {
      Instant instant = Instant.now();
      pedigreeService.save(pedigree, instant.toEpochMilli());
   }

   @RequestMapping(value = "/v2/pedigrees/{id}", method = RequestMethod.DELETE)
   @ResponseStatus(HttpStatus.NO_CONTENT)
   public void deletePedigree(@PathVariable("id") long id) {
      pedigreeService.deleteById(id);
   }

}
