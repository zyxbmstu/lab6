package bmstu.iu9.lab6;

import akka.actor.AbstractActor;
import akka.japi.pf.ReceiveBuilder;

public class StorageActor extends AbstractActor {

    @Override
    public Receive createReceive() {
        return ReceiveBuilder.create().match()
    }

}
