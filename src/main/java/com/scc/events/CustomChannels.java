package com.scc.events;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

public interface CustomChannels {
   @Input("inboundDogChanges")
   SubscribableChannel dog();

   @Input("inboundOwnerChanges")
   SubscribableChannel owner();

   @Input("inboundBreederChanges")
   SubscribableChannel breeder();

   @Input("inboundTitleChanges")
   SubscribableChannel title();

   @Input("inboundPedigreeChanges")
   SubscribableChannel pedigree();

   @Input("inboundParentChanges")
   SubscribableChannel parent();

}
