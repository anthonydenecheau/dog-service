package com.scc.enci.services;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.sleuth.Span;
import org.springframework.cloud.sleuth.Tracer;
import org.springframework.stereotype.Service;

import com.scc.enci.config.ServiceConfig;
import com.scc.enci.model.Title;
import com.scc.enci.repository.TitleRepository;

@Service
public class TitleService {

    private static final Logger logger = LoggerFactory.getLogger(TitleService.class);

    @Autowired
    private Tracer tracer;

    @Autowired
    private TitleRepository titleRepository;
    
    @Autowired
    ServiceConfig config;

    public List<Title> getTitlesByIdDog(int dogId){
        Span newSpan = tracer.createSpan("getTitlesByIdDog");
        logger.debug("In the titleService.getTitlesByIdDog call, trace id: {}", tracer.getCurrentSpan().traceIdString());
        try {
        	return titleRepository.findByIdDog(dogId);
        }
        finally{
          newSpan.tag("peer.service", "postgres");
          newSpan.logEvent(org.springframework.cloud.sleuth.Span.CLIENT_RECV);
          tracer.close(newSpan);
        }
    }
    
    public List<Title> findByObtentionDateGreaterThanEqual(String referenceDate) {
        Span newSpan = tracer.createSpan("findByObtentionDateAfter");
        logger.debug("In the titleService.findByObtentionDateAfter call, trace id: {}", tracer.getCurrentSpan().traceIdString());
        try {
        	return titleRepository.findByObtentionDateGreaterThanEqual(referenceDate);
        }
        finally{
          newSpan.tag("peer.service", "postgres");
          newSpan.logEvent(org.springframework.cloud.sleuth.Span.CLIENT_RECV);
          tracer.close(newSpan);
        }
    }
}
