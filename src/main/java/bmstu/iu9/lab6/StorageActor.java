package bmstu.iu9.lab6;

import akka.actor.AbstractActor;
import akka.japi.pf.ReceiveBuilder;
import bmstu.iu9.lab6.messages.ServerMessage;

public class StorageActor extends AbstractActor {

    @Override
    public Receive createReceive() {
        return ReceiveBuilder.create().match(ServerMessage.class, message -> {

        })
    }

}
