package com.scc.events.handlers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;

import com.scc.events.CustomChannels;
import com.scc.events.models.BreederChangeModel;
import com.scc.events.models.DogChangeModel;
import com.scc.events.models.OwnerChangeModel;
import com.scc.events.models.ParentChangeModel;
import com.scc.events.models.PedigreeChangeModel;
import com.scc.events.models.TitleChangeModel;
import com.scc.dog.services.BreederService;
import com.scc.dog.services.DogService;
import com.scc.dog.services.OwnerService;
import com.scc.dog.services.ParentService;
import com.scc.dog.services.PedigreeService;
import com.scc.dog.services.TitleService;

@EnableBinding(CustomChannels.class)
public class DogChangeHandler {

   @Autowired
   private DogService dogService;

   @Autowired
   private BreederService breederService;

   @Autowired
   private OwnerService ownerService;

   @Autowired
   private TitleService titleService;

   @Autowired
   private PedigreeService pedigreeService;

   @Autowired
   private ParentService parentService;

   private static final Logger logger = LoggerFactory.getLogger(DogChangeHandler.class);

   @StreamListener("inboundDogChanges")
   public void loggerSinkDog(DogChangeModel dogChange) {
      logger.debug("Received a message of type {} traceId {} ", dogChange.getType(), dogChange.getTraceId());
      switch (dogChange.getAction()) {
      case "GET":
         logger.debug("Received a GET event from daemon dog for dog id {}", dogChange.getDog().getId());
         break;
      case "SAVE":
      case "UPDATE":
         logger.debug("Received a {} event from the daemon dog for dog id {}", dogChange.getAction(),
               dogChange.getDog().toString());
         dogService.save(dogChange.getDog(), dogChange.getTimestamp());
         break;
      case "DELETE":
         logger.debug("Received a DELETE event from the daemon dog for dog id {}", dogChange.getDog().getId());
         dogService.deleteById(dogChange.getDog().getId());
         break;
      default:
         logger.error("Received an UNKNOWN event from the daemon dog of type {}", dogChange.getDog().getId());
         break;
      }
   }

   @StreamListener("inboundBreederChanges")
   public void loggerSinkBreeder(BreederChangeModel breederChange) {
      logger.debug("Received a message of type {} traceId {} ", breederChange.getType(), breederChange.getTraceId());
      switch (breederChange.getAction()) {
      case "GET":
         logger.debug("Received a GET event from daemon dog for breeder id {}", breederChange.getBreeder().getId());
         break;
      case "SAVE":
      case "UPDATE":
         logger.debug("Received a {} event from the daemon dog for breeder id {}", breederChange.getAction(),
               breederChange.getBreeder().toString());
         breederService.save(breederChange.getBreeder(), breederChange.getTimestamp());
         break;
      case "DELETE":
         logger.debug("Received a DELETE event from the daemon dog for breeder id {}",
               breederChange.getBreeder().getId());
         breederService.deleteByIdDog(breederChange.getBreeder().getIdDog());
         break;
      default:
         logger.error("Received an UNKNOWN event from the daemon dog of type {}", breederChange.getBreeder().getId());
         break;
      }
   }

   @StreamListener("inboundOwnerChanges")
   public void loggerSinkOwner(OwnerChangeModel ownerChange) {
      logger.debug("Received a message of type {} traceId {} ", ownerChange.getType(), ownerChange.getTraceId());
      switch (ownerChange.getAction()) {
      case "GET":
         logger.debug("Received a GET event from daemon dog for owner id {}", ownerChange.getOwner().getId());
         break;
      case "SAVE":
      case "UPDATE":
         logger.debug("Received a {} event from the daemon dog for owner id {}", ownerChange.getAction(),
               ownerChange.getOwner().toString());
         ownerService.save(ownerChange.getOwner(), ownerChange.getTimestamp());
         break;
      case "DELETE":
         logger.debug("Received a DELETE event from the daemon dog for owner id {}", ownerChange.getOwner().getId());
         ownerService.deleteByIdDog(ownerChange.getOwner().getId());
         break;
      default:
         logger.error("Received an UNKNOWN event from the daemon dog of type {}", ownerChange.getOwner().getId());
         break;
      }
   }

   @StreamListener("inboundTitleChanges")
   public void loggerSinkTitle(TitleChangeModel titleChange) {
      logger.debug("Received a message of type {} traceId {} ", titleChange.getType(), titleChange.getTraceId());
      switch (titleChange.getAction()) {
      case "GET":
         logger.debug("Received a GET event from daemon dog for title id {}", titleChange.getTitle().getId());
         break;
      case "SAVE":
      case "UPDATE":
         logger.debug("Received a {} event from the daemon dog for title id {}", titleChange.getAction(),
               titleChange.getTitle().toString());
         titleService.save(titleChange.getTitle(), titleChange.getTimestamp());
         break;
      case "DELETE":
         logger.debug("Received a DELETE event from the daemon dog for title id {}", titleChange.getTitle().getId());
         titleService.deleteById(titleChange.getTitle().getId());
         break;
      default:
         logger.error("Received an UNKNOWN event from the daemon dog of type {}", titleChange.getTitle().getId());
         break;
      }
   }

   @StreamListener("inboundPedigreeChanges")
   public void loggerSinkPedigree(PedigreeChangeModel pedigreeChange) {
      logger.debug("Received a message of type {} traceId {} ", pedigreeChange.getType(), pedigreeChange.getTraceId());
      switch (pedigreeChange.getAction()) {
      case "GET":
         logger.debug("Received a GET event from daemon dog for pedigree id {}", pedigreeChange.getPedigree().getId());
         break;
      case "SAVE":
      case "UPDATE":
         logger.debug("Received a {} event from the daemon dog for pedigree id {}", pedigreeChange.getAction(),
               pedigreeChange.getPedigree().toString());
         pedigreeService.save(pedigreeChange.getPedigree(), pedigreeChange.getTimestamp());
         break;
      case "DELETE":
         logger.debug("Received a DELETE event from the daemon dog for pedigree id {}",
               pedigreeChange.getPedigree().getId());
         pedigreeService.deleteById(pedigreeChange.getPedigree().getId());
         break;
      default:
         logger.error("Received an UNKNOWN event from the daemon dog of type {}", pedigreeChange.getPedigree().getId());
         break;
      }
   }

   @StreamListener("inboundParentChanges")
   public void loggerSinkParent(ParentChangeModel parentChange) {
      logger.debug("Received a message of type {} traceId {} ", parentChange.getType(), parentChange.getTraceId());
      switch (parentChange.getAction()) {
      case "GET":
         logger.debug("Received a GET event from daemon dog for parent id {}", parentChange.getParent().getId());
         break;
      case "SAVE":
      case "UPDATE":
         logger.debug("Received a {} event from the daemon dog for parent id {}", parentChange.getAction(),
               parentChange.getParent().toString());
         parentService.save(parentChange.getParent(), parentChange.getTimestamp());
         break;
      case "DELETE":
         logger.debug("Received a DELETE event from the daemon dog for parent id {}", parentChange.getParent().getId());
         parentService.deleteById(parentChange.getParent().getId());
         break;
      default:
         logger.error("Received an UNKNOWN event from the daemon dog of type {}", parentChange.getParent().getId());
         break;
      }
   }

}
