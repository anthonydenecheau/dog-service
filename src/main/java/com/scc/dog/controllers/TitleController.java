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

import com.scc.dog.model.Title;
import com.scc.dog.services.TitleService;

@RestController
public class TitleController {

   @Autowired
   private TitleService titleService;

   @RequestMapping(value = "/v2/titles/{id}", method = RequestMethod.PUT)
   public void updateTitle(@PathVariable("id") long id, @RequestBody Title title) {
      Instant instant = Instant.now();
      titleService.save(title, instant.toEpochMilli());
   }

   @RequestMapping(value = "/v2/titles/", method = RequestMethod.POST)
   public void saveTitle(@RequestBody Title title) {
      Instant instant = Instant.now();
      titleService.save(title, instant.toEpochMilli());
   }

   @RequestMapping(value = "/v2/titles/{id}", method = RequestMethod.DELETE)
   @ResponseStatus(HttpStatus.NO_CONTENT)
   public void deleteTitle(@PathVariable("id") long id) {
      titleService.deleteById(id);
   }

}
