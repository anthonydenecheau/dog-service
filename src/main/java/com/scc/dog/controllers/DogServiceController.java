package com.scc.dog.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.scc.dog.services.DogService;
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
@RequestMapping(value="v1/dogs")
@Api(value="dog selection", description="Return a single dog data")
public class DogServiceController {
   
	@Autowired
    private DogService dogService;
      
    @ApiOperation(value = "View dog information by token",response = DogObject.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved dog"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })    
    @RequestMapping(value="/searchByToken/{token}",method = RequestMethod.GET)
    public ResponseObjectList<DogObject> getDogByToken( 
    		@ApiParam(value = "token (chip or tatoo)", required = true) @PathVariable("token") String token) {
        return dogService.getDogByToken(token);
    }    

    @ApiOperation(value = "Return a list of dog that became champions from a certain date till now",response = ChampionObject.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved dogs"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })    
    @RequestMapping(value="/getChampionsChanges/{referenceDate}",method = RequestMethod.GET)
    public ResponseObjectList<ChampionObject> getChampionsChanges( 
    		@ApiParam(value = "referenceDate (format: yyyy-mm-dd)", required = true) @PathVariable("referenceDate") String referenceDate) {
        return dogService.getChampionsChanges(referenceDate);
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
