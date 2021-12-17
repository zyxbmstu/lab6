package bmstu.iu9.lab6;

import akka.actor.ActorRef;
import akka.http.javadsl.Http;
import akka.http.javadsl.server.Route;
import org.apache.zookeeper.KeeperException;

import java.io.IOException;

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
                            int count = Integer.parseInt(countParam)
                        })))
    }

}
