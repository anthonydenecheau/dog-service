package com.scc.dog.services;

import java.sql.Timestamp;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.sleuth.Span;
import org.springframework.cloud.sleuth.Tracer;
import org.springframework.stereotype.Service;

import com.scc.dog.config.ServiceConfig;
import com.scc.dog.model.Title;
import com.scc.dog.repository.TitleRepository;

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
    
    public void save(Title syncTitle, Long timestamp){
      	 
    	try {
    		Title title = titleRepository.findById(syncTitle.getId());
	    	if (title == null) {
	    		logger.debug("Title id {} not found", syncTitle.getId());
	    		syncTitle
	    			.withTimestamp(new Timestamp(timestamp))
	    		;	    		
	    		titleRepository.save(syncTitle);
	    	} else {
	    		logger.debug("save dog id {}, {}, {}", title.getId(), title.getTimestamp().getTime(), timestamp);
	    		if (title.getTimestamp().getTime() < timestamp) {
		    		logger.debug("check queue OK ; call saving changes ");
		    		title
		    			.withId(syncTitle.getId())
		    			.withTitle(syncTitle.getTitle())
		    			.withName(syncTitle.getName())
		    		    .withType(syncTitle.getType())
		    		    .withCountry(syncTitle.getCountry())
		    			.withObtentionDate(syncTitle.getObtentionDate())
		    			.withIdDog(syncTitle.getIdDog())
		    			.withTimestamp(new Timestamp(timestamp))
		    		;
		    		
		    		titleRepository.save(title);
	    		} else
		    		logger.debug("check queue KO : no changes saved");

	    	}
    	} finally {
    		
    	}
    }
    
    public void deleteById(long id){
    	try {
    		titleRepository.deleteById(id);
    	} finally {
    		
    	}
    }    
}
