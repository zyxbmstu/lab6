package bmstu.iu9.lab6;

import akka.actor.ActorRef;
import akka.http.scaladsl.Http;

import java.io.IOException;

public class Server {

    private Http http;
    private

    public Server(Http http, int port, ActorRef storageActor) throws IOException {
        this.http = http;
    }

}
