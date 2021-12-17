package bmstu.iu9.lab6;

import java.io.IOException;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;

public class Anonymazer {

    private static final String ACTOR_SYSTEM_NAME = "anonymizer";

    public static void main(String[] args) throws IOException {
        int port = Integer.parseInt(args[0]);
        ActorSystem system = ActorSystem.create(ACTOR_SYSTEM_NAME);
        ActorRef
    }

}
