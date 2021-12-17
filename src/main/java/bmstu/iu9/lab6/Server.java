package bmstu.iu9.lab6;

import akka.actor.ActorRef;
import akka.http.javadsl.Http;
import akka.http.javadsl.model.HttpRequest;
import akka.http.javadsl.model.HttpResponse;
import akka.http.javadsl.model.Query;
import akka.http.javadsl.model.Uri;
import akka.http.javadsl.server.Route;
import akka.japi.Pair;
import akka.pattern.Patterns;
import bmstu.iu9.lab6.messages.RandomServer;
import org.apache.zookeeper.KeeperException;

import java.io.IOException;
import java.util.concurrent.CompletionStage;

public class Server {

    private Http http;
    private ActorRef storageActor;

    private static final String LOCAL_URL = "http://localhost";
    private static final String URL_PARAM = "url";
    private static final String COUNT_PARAM = "count";


    public Server(Http http, int port, ActorRef storageActor) throws IOException {
        this.http = http;
        this.storageActor = storageActor;
    }

    private String getUrl(int port) {
        return LOCAL_URL + port;
    }

    private void initZookeeper(int port) throws IOException, InterruptedException, KeeperException {
        ZookeeperServices zookeeperServices = new ZookeeperServices(storageActor);
        zookeeperServices.initServer(getUrl(port));
    }

    public Route createRoute() {
        return get(() ->
                parameter(URL_PARAM, url ->
                        parameter(COUNT_PARAM, countParam -> {
                            int count = Integer.parseInt(countParam);
                            return count == 0
                                    ? completeWithFuture(fetch(url))
                                    : completeWithFuture(redirect(url, count));
                        })
                )
        );
    }

    private String createRedirectLink(String url, String anonymazerUrl, int count) {
        return Uri.create(url)
                .query(Query.create(
                        Pair.create(URL_PARAM, anonymazerUrl),
                        Pair.create(COUNT_PARAM, Integer.toString(count - 1))
                ))
                .toString();
    }

    private CompletionStage<HttpResponse> fetch(String url) {
        return http.singleRequest(HttpRequest.create(url));
    }

    private CompletionStage<HttpResponse> redirect(String url, int count) {
        return Patterns.ask(storageActor, new RandomServer(), 70)
                .thenCompose(url -> fetch())
    }

}
