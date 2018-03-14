package com.scc.dog.services;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.sleuth.Span;
import org.springframework.cloud.sleuth.Tracer;
import org.springframework.stereotype.Service;

import com.scc.dog.config.ServiceConfig;
import com.scc.dog.model.Pedigree;
import com.scc.dog.repository.PedigreeRepository;

@Service
public class PedigreeService {

    private static final Logger logger = LoggerFactory.getLogger(PedigreeService.class);

    @Autowired
    private Tracer tracer;

    @Autowired
    private PedigreeRepository pedigreeRepository;
    
    @Autowired
    ServiceConfig config;

    public List<Pedigree> getPedigreesByIdDog(int dogId){
        Span newSpan = tracer.createSpan("getPedigreesByIdDog");
        logger.debug("In the breederService.getPedigreesByIdDog call, trace id: {}", tracer.getCurrentSpan().traceIdString());
        try {
        	return pedigreeRepository.findByIdDog(dogId);
        }
        finally{
          newSpan.tag("peer.service", "postgres");
          newSpan.logEvent(org.springframework.cloud.sleuth.Span.CLIENT_RECV);
          tracer.close(newSpan);
        }

    }
    
}
