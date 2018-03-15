package com.scc.dog.services.v1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.sleuth.Span;
import org.springframework.cloud.sleuth.Tracer;
import org.springframework.stereotype.Service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.scc.dog.model.Dog;
import com.scc.dog.model.Pedigree;
import com.scc.dog.repository.DogRepository;
import com.scc.dog.services.PedigreeService;
import com.scc.dog.template.v1.DogObject;
import com.scc.dog.template.ResponseObjectList;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class DogServiceV1 {

    private static final Logger logger = LoggerFactory.getLogger(DogServiceV1.class);

    @Autowired
    private Tracer tracer;

    @Autowired
    private PedigreeService pedigreeService;

    @Autowired
    private DogRepository dogRepository;

    public DogObject getDogById(int dogId){
        Span newSpan = tracer.createSpan("getDogById");
        logger.debug("In the dogService.getDogById() call, trace id: {}", tracer.getCurrentSpan().traceIdString());
        try {
        	Dog dog = new Dog();
        	dog = dogRepository.findById(dogId);
        	
        	if (dog == null ) {
        		return (DogObject) new DogObject()
                        .withId(0)
                ;
        	} else {
	        	return (DogObject) new DogObject()
	    			.withId(dog.getId())
	    			.withNom(dog.getNom())
	    			.withSexe(dog.getSexe())
	    			.withDateNaissance(formatDtNaissance(dog.getDateNaissance()))
	    			.withLof(searchFrenchPedigree(dog.getId()))
	    			.withTatouage(dog.getTatouage())
	    			.withTranspondeur(dog.getTranspondeur())
	    			.withRace(dog.getRace())
	    			.withVariete(buildVariete(dog.getCodeFci(),dog.getVariete()))
	    			.withCouleur(dog.getCouleurAbr())
	    		;
        	}
        }
        finally{
          newSpan.tag("peer.service", "postgres");
          newSpan.logEvent(org.springframework.cloud.sleuth.Span.CLIENT_RECV);
          tracer.close(newSpan);
        }

    }

    @HystrixCommand(fallbackMethod = "buildFallbackDogList",
            threadPoolKey = "dogByTokenThreadPool",
            threadPoolProperties =
                    {@HystrixProperty(name = "coreSize",value="30"),
                     @HystrixProperty(name="maxQueueSize", value="10")},
            commandProperties={
                     @HystrixProperty(name="circuitBreaker.requestVolumeThreshold", value="10"),
                     @HystrixProperty(name="circuitBreaker.errorThresholdPercentage", value="75"),
                     @HystrixProperty(name="circuitBreaker.sleepWindowInMilliseconds", value="7000"),
                     @HystrixProperty(name="metrics.rollingStats.timeInMilliseconds", value="15000"),
                     @HystrixProperty(name="metrics.rollingStats.numBuckets", value="5")}
    )
    public ResponseObjectList<DogObject> getDogByToken(String token){

        Span newSpan = tracer.createSpan("getDogByToken");
        logger.debug("In the dogService.getDogByToken() call, trace id: {}", tracer.getCurrentSpan().traceIdString());
        try {
	    	/*
	        * norme ISO (FDXB) = 15 chiffres
	        */
	    	String regex = "^[0-9]{15}$";
	    	List<Dog> list = new ArrayList<Dog>(); 
	    	if (!token.matches(regex))
	    		list = dogRepository.findByTatouage(token);
	    	else
	    		list = dogRepository.findByTranspondeur(token);
	    
	    	// Pour conserver le timestamp lors de la maj via message (@JsonIgnore impossible s/ timestamp sinon timestamp == null)
	    	// nous sommes obligés de passer par une classe intermédiaire DogObject
	    	List<DogObject> dogs = new ArrayList<DogObject>(); 
	    	for (Dog dog : list) {
	    		dogs.add(
	    		  (DogObject) new DogObject()
	    			.withId(dog.getId())
	    			.withNom(dog.getNom())
	    			.withSexe(dog.getSexe())
	    			.withDateNaissance(formatDtNaissance(dog.getDateNaissance()))
	    			.withLof(searchFrenchPedigree(dog.getId()))
	    			.withTatouage(dog.getTatouage())
	    			.withTranspondeur(dog.getTranspondeur())
	    			.withRace(dog.getRace())
	    			.withVariete(buildVariete(dog.getCodeFci(),dog.getVariete()))
	    			.withCouleur(dog.getCouleurAbr())
	    		);
	    	}

	    	list.clear();
	    	
	    	return new ResponseObjectList<DogObject>(dogs.size(), dogs);
        }
	    finally{
	    	
	    	newSpan.tag("peer.service", "postgres");
	        newSpan.logEvent(org.springframework.cloud.sleuth.Span.CLIENT_RECV);
	        tracer.close(newSpan);
	    }
    }

    private ResponseObjectList<DogObject> buildFallbackDogList(String token){
    	
    	List<DogObject> list = new ArrayList<DogObject>(); 
    	list.add((DogObject) new DogObject()
                .withId(0))
    	;
        return new ResponseObjectList<DogObject>(list.size(), list);
    }
    
    private String searchFrenchPedigree(int _id) {
    	
    	String _numLof=null;
    	
    	try {
    		for(Pedigree _pedigree : pedigreeService.getPedigreesByIdDog( _id )) {
    			if ("LOF".equals(_pedigree.getType())) {
    				// le pedigree est contruit par la concaténation du n° de lof et de confirmation
    				if (_pedigree.getNumber()!= null && !"".equals(_pedigree.getNumber()))
    					_numLof = _pedigree.getNumber().split("/")[0];
    				
    				break;
    			}
    		}
    		
    	} catch (Exception e) {
    		
    	}
    	return _numLof;
    	
    }
    
    private String buildVariete(String codeFci, String variete) {
    	String libelle = null;
    	if (codeFci!=null && !"".equals(codeFci)) {
        	if (variete!=null && !"".equals(variete)) {
        		libelle = codeFci+"-"+variete;
        	} else {
        		libelle = codeFci+"-";
        	}
    	}
    	return libelle;
    }

    private String formatDtNaissance(String dateNaissance) {
    	return dateNaissance.split("-")[2]+"/"+dateNaissance.split("-")[1]+"/"+dateNaissance.split("-")[0];  
    }

}
