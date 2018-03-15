package com.scc.dog.services;

import java.sql.Timestamp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.sleuth.Span;
import org.springframework.cloud.sleuth.Tracer;
import org.springframework.stereotype.Service;

import com.scc.dog.config.ServiceConfig;
import com.scc.dog.model.Parent;
import com.scc.dog.repository.ParentRepository;

@Service
public class ParentService {

    private static final Logger logger = LoggerFactory.getLogger(ParentService.class);

    @Autowired
    private Tracer tracer;

    @Autowired
    private ParentRepository parentRepository;
    
    @Autowired
    ServiceConfig config;

    public Parent getParentById(int id){
        Span newSpan = tracer.createSpan("getParentByIdDog");
        logger.debug("In the parentService.getParentByIdDog() call, trace id: {}", tracer.getCurrentSpan().traceIdString());
        try {
        	return parentRepository.findById(id);
        }
        finally{
          newSpan.tag("peer.service", "postgres");
          newSpan.logEvent(org.springframework.cloud.sleuth.Span.CLIENT_RECV);
          tracer.close(newSpan);
        }

    }
    
    public void save(Parent syncParent, Long timestamp){
     	 
    	try {
    		Parent parent = parentRepository.findById(syncParent.getId());
	    	if (parent == null) {
	    		logger.debug("Dog id {} not found", syncParent.getId());
	    		syncParent
	    			.withTimestamp(new Timestamp(timestamp))
	    		;	    		
	    		parentRepository.save(syncParent);
	    	} else {
	    		logger.debug("save dog id {}, {}, {}", parent.getId(), parent.getTimestamp().getTime(), timestamp);
	    		if (parent.getTimestamp().getTime() < timestamp) {
		    		logger.debug("check queue OK ; call saving changes ");
		    		parent
		    			.withId(syncParent.getId())
		    			.withName(syncParent.getName())
		    		    .withAffixe(syncParent.getAffixe())
		    		    .withOnSuffixe(syncParent.getOnSuffixe())
		    			.withTimestamp(new Timestamp(timestamp))
		    		;
		    		
		    		parentRepository.save(parent);
	    		} else
		    		logger.debug("check queue KO : no changes saved");

	    	}
    	} finally {
    		
    	}
    }
    
    public void deleteById(int idDog){
    	try {
    		parentRepository.deleteById(idDog);
    	} finally {
    		
    	}
    }    

}
