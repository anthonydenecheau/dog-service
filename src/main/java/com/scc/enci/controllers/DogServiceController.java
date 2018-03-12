package com.scc.enci.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RestController;

import com.scc.enci.model.Dog;
import com.scc.enci.services.DogService;
import com.scc.enci.template.ResponseObject;
import com.scc.enci.template.ResponseObjectList;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;

@RestController
@RequestMapping(value="v1/dogs")
@Api(value="dog selection", description="Return a single dog data")
public class DogServiceController {
   
	@Autowired
    private DogService dogService;
      
    @ApiOperation(value = "View dog information by token",response = ResponseObjectList.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved dog"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })    
    @RequestMapping(value="/searchByToken/{token}",method = RequestMethod.GET)
    public ResponseObjectList<ResponseObject> getDogByToken( 
    		@ApiParam(value = "token (chip or tatoo)", required = true) @PathVariable("token") String token) {
        return dogService.getDogByToken(token);
    }    

//    @RequestMapping(value="/{dogId}",method = RequestMethod.GET)
//    public Dog getDog( @PathVariable("dogId") int dogId) {
//        return dogService.getDogById(dogId);
//    }
//
//    @RequestMapping(value="{dogId}",method = RequestMethod.PUT)
//    public String updateDog( @PathVariable("dogId") int dogId) {
//        return String.format("This is the put");
//    }
//
//    @RequestMapping(value="{dogId}",method = RequestMethod.POST)
//    public String saveDog( @PathVariable("dogId") int dogId) {
//        return String.format("This is the post");
//    }
//
//    @RequestMapping(value="{dogId}",method = RequestMethod.DELETE)
//    @ResponseStatus(HttpStatus.NO_CONTENT)
//    public String deleteDog( @PathVariable("dogId") int dogId) {
//        return String.format("This is the Delete");
//    }

}
