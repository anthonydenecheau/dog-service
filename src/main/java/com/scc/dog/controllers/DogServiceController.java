package com.scc.dog.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.scc.dog.services.DogService;
import com.scc.dog.services.v1.DogServiceV1;
import com.scc.dog.template.ChampionObject;
import com.scc.dog.template.DogObject;
import com.scc.dog.template.ResponseObjectList;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@Api(value="dog selection", description="Return information about dog")
public class DogServiceController {
   
	@Autowired
    private DogService dogService;

	@Autowired
    private DogServiceV1 dogServiceV1;

    @ApiOperation(value = "View dog information by token",response = ResponseObjectList.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved dog"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })    
    @RequestMapping(value="/v2/dogs/token/{token}",method = RequestMethod.GET)
    public ResponseObjectList<DogObject> getDogByToken( 
    		@ApiParam(value = "token (chip or tatoo)", required = true) @PathVariable("token") String token) {
        return dogService.getDogByToken(token);
    }   
    
    @ApiOperation(value = "View dog information by id",response = DogObject.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved dog"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })    
    @RequestMapping(value="/v2/dogs/{id}",method = RequestMethod.GET)
    public DogObject getDogById( 
    		@ApiParam(value = "Dog id", required = true) @PathVariable("id") int id) {
        return dogService.getDogById(id);
    }  

    @ApiOperation(value = "Return a list of dog that became champions from a certain date till now",response = ResponseObjectList.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved dogs"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })    
    @RequestMapping(value="/v2/dogs/champions/{referenceDate}",method = RequestMethod.GET)
    public ResponseObjectList<ChampionObject> getChampionsChanges( 
    		@ApiParam(value = "referenceDate (format: yyyy-mm-dd)", required = true) @PathVariable("referenceDate") String referenceDate) {
        return dogService.getChampionsChanges(referenceDate);
    }    

    @ApiOperation(value = "View dog information by token",response = ResponseObjectList.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved dog"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })    
    @RequestMapping(value="/v1/dogs/token/{token}",method = RequestMethod.GET)
    public ResponseObjectList<com.scc.dog.template.v1.DogObject> getDogByTokenV1( 
    		@ApiParam(value = "token (chip or tatoo)", required = true) @PathVariable("token") String token) {
        return dogServiceV1.getDogByToken(token);
    }  
    
    @ApiOperation(value = "View dog information by id",response = com.scc.dog.template.v1.DogObject.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved dog"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })    
    @RequestMapping(value="/v1/dogs/{id}",method = RequestMethod.GET)
    public com.scc.dog.template.v1.DogObject getDogByIdV1( 
    		@ApiParam(value = "Dog id", required = true) @PathVariable("id") int id) {
        return dogServiceV1.getDogById(id);
    }      
 
}
