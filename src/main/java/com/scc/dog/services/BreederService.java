package com.scc.dog.services;

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
    
}
