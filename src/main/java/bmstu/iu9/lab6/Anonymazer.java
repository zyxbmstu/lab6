package bmstu.iu9.lab6;

import java.io.IOException;
import java.util.concurrent.CompletionStage;

import akka.NotUsed;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.http.javadsl.ConnectHttp;
import akka.http.javadsl.Http;
import akka.http.javadsl.ServerBinding;
import akka.http.javadsl.model.HttpRequest;
import akka.http.javadsl.model.HttpResponse;
import akka.stream.ActorMaterializer;
import akka.stream.javadsl.Flow;

public class Anonymazer {

    private static final String ACTOR_SYSTEM_NAME = "anonymizer";
    private static final String HOST = "localhost";

    public static void main(String[] args) throws IOException {
        int port = Integer.parseInt(args[0]);
        ActorSystem system = ActorSystem.create(ACTOR_SYSTEM_NAME);
        ActorRef storageActor = system.actorOf(Props.create(StorageActor.class));

        final Http http = Http.get(system);
        final ActorMaterializer materializer = ActorMaterializer.create(system);

        Server server = new Server(http, port, storageActor);

        final Flow<HttpRequest, HttpResponse, NotUsed> flow = server.createRoute().flow(system, materializer);
        final CompletionStage<ServerBinding> binding = http.bindAndHandle(
                flow,
                ConnectHttp.toHost(HOST, port),
                materializer
        );

        System.out.println("Server started: " + HOST + ":" + port);
        System.in.read();
        binding
                .thenCompose(ServerBinding::unbind)
                .thenAccept(unbind -> system.terminate());
    }

}
