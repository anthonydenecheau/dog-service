package com.scc.dog.services;

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
    
}
