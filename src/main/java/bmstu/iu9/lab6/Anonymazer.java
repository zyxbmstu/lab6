package bmstu.iu9.lab6;

import java.io.IOException;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.http.javadsl.Http;
import akka.stream.ActorMaterializer;

public class Anonymazer {

    private static final String ACTOR_SYSTEM_NAME = "anonymizer";

    public static void main(String[] args) throws IOException {
        int port = Integer.parseInt(args[0]);
        ActorSystem system = ActorSystem.create(ACTOR_SYSTEM_NAME);
        ActorRef storageActor = system.actorOf(Props.create(StorageActor.class));

        final Http http = Http.get(system);
        final ActorMaterializer materializer = ActorMaterializer.create(system);


    }

}
