package com.scc.dog.services;

import java.sql.Timestamp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.sleuth.Span;
import org.springframework.cloud.sleuth.Tracer;
import org.springframework.stereotype.Service;

import com.scc.dog.config.ServiceConfig;
import com.scc.dog.model.Owner;
import com.scc.dog.repository.OwnerRepository;

@Service
public class OwnerService {

    private static final Logger logger = LoggerFactory.getLogger(OwnerService.class);

    @Autowired
    private Tracer tracer;

    @Autowired
    private OwnerRepository ownerRepository;
    
    @Autowired
    ServiceConfig config;

    public Owner getOwnerByIdDog(int dogId){
        Span newSpan = tracer.createSpan("getOwnerByIdDog");
        logger.debug("In the breederService.getOwnerByIdDog() call, trace id: {}", tracer.getCurrentSpan().traceIdString());
        try {
        	return ownerRepository.findByIdDog(dogId);
        }
        finally{
          newSpan.tag("peer.service", "postgres");
          newSpan.logEvent(org.springframework.cloud.sleuth.Span.CLIENT_RECV);
          tracer.close(newSpan);
        }

    }
    
    public void save(Owner syncOwner, Long timestamp){
   	 
    	try {
	    	Owner owner = ownerRepository.findByIdDog(syncOwner.getIdDog());
	    	if (owner == null) {
	    		logger.debug("Dog id {} not found", syncOwner.getId());
	    		syncOwner
	    			.withTimestamp(new Timestamp(timestamp))
	    		;	    		
	    		ownerRepository.save(syncOwner);
	    	} else {
	    		logger.debug("save dog id {}, {}, {}", owner.getId(), owner.getTimestamp().getTime(), timestamp);
	    		if (owner.getTimestamp().getTime() < timestamp) {
		    		logger.debug("check queue OK ; call saving changes ");
		    		owner
		    			.withId(syncOwner.getId())
		    			.withFirstName(syncOwner.getFirstName())
		    			.withLastName(syncOwner.getLastName())
		    		    .withAddress(syncOwner.getAddress())
		    		    .withZipCode(syncOwner.getZipCode())
		    			.withTown(syncOwner.getTown())
		    			.withCountry(syncOwner.getCountry())
		    			.withIdDog(syncOwner.getIdDog())
		    			.withTimestamp(new Timestamp(timestamp))
		    		;
		    		
		    		ownerRepository.save(owner);
	    		} else
		    		logger.debug("check queue KO : no changes saved");

	    	}
    	} finally {
    		
    	}
    }
    
    public void deleteByIdDog(int idDog){
    	try {
    		ownerRepository.deleteByIdDog(idDog);
    	} finally {
    		
    	}
    }
    
}
