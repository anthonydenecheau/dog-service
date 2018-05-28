package com.scc.dog.services;

import java.sql.Timestamp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.sleuth.Span;
import org.springframework.cloud.sleuth.Tracer;
import org.springframework.stereotype.Service;

import com.scc.dog.config.ServiceConfig;
import com.scc.dog.model.Breeder;
import com.scc.dog.repository.BreederRepository;

@Service
public class BreederService {

    private static final Logger logger = LoggerFactory.getLogger(BreederService.class);

    @Autowired
    private Tracer tracer;

    @Autowired
    private BreederRepository breederRepository;
    
    @Autowired
    ServiceConfig config;

    public Breeder getBreederByIdDog(int dogId){
        Span newSpan = tracer.createSpan("getBreederByIdDog");
        logger.debug("In the breederService.getBreederByIdDog() call, trace id: {}", tracer.getCurrentSpan().traceIdString());
        try {
        	return breederRepository.findByIdDog(dogId);
        }
        finally{
          newSpan.tag("peer.service", "postgres");
          newSpan.logEvent(org.springframework.cloud.sleuth.Span.CLIENT_RECV);
          tracer.close(newSpan);
        }

    }
    
    public void save(Breeder syncBreeder, Long timestamp){
    	 
    	try {
	    	Breeder breeder = breederRepository.findByIdDog(syncBreeder.getIdDog());
	    	if (breeder == null) {
	    		logger.debug("Dog id {} not found", syncBreeder.getId());
	    		syncBreeder
	    			.withTimestamp(new Timestamp(timestamp))
	    		;	    		
	    		breederRepository.save(syncBreeder);
	    	} else {
	    		logger.debug("save dog id {}, {}, {}", breeder.getId(), breeder.getTimestamp().getTime(), timestamp);
	    		if (breeder.getTimestamp().getTime() < timestamp) {
		    		logger.debug("check queue OK ; call saving changes ");
		    		breeder
		    			.withId(syncBreeder.getId())
		    			.withCivility(syncBreeder.getCivility())
		    			.withLastName(syncBreeder.getLastName())
		    			.withFirstName(syncBreeder.getFirstName())
		    		    .withTypeProfil(syncBreeder.getTypeProfil())
		    		    .withProfessionnelActif(syncBreeder.getProfessionnelActif())
		    			.withRaisonSociale(syncBreeder.getRaisonSociale())
		    			.withOnSuffixe(syncBreeder.getOnSuffixe())
		    			.withPays(syncBreeder.getPays())
		    			.withIdDog(syncBreeder.getIdDog())
		    			.withTimestamp(new Timestamp(timestamp))
		    		;
		    		
		    		breederRepository.save(breeder);
	    		} else
		    		logger.debug("check queue KO : no changes saved");

	    	}
    	} finally {
    		
    	}
    }    
    
    public void deleteByIdDog(int idDog){
    	try {
    		breederRepository.deleteByIdDog(idDog);
    	} finally {
    		
    	}
    }
    
    
}
