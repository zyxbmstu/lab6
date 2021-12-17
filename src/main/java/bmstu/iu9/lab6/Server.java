package bmstu.iu9.lab6;

import akka.actor.ActorRef;
import akka.http.scaladsl.Http;
import org.apache.zookeeper.server.ZooKeeperServer;

import java.io.IOException;

public class Server {

    private Http http;
    private ActorRef storageActor;

    private static final String LOCAL_URL = "http://localhost";

    public Server(Http http, int port, ActorRef storageActor) throws IOException {
        this.http = http;
        this.storageActor = storageActor;
    }

    private String getUrl(int port) {
        return LOCAL_URL + port;
    }

    private void initZookeeper(int port) throws IOException {
        ZookeeperServices zookeeperServices = new ZookeeperServices(storageActor);
        zookeeperServices.initServer();
    }

}
