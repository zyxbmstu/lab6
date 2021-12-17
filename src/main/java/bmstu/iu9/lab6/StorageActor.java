package bmstu.iu9.lab6;

import akka.actor.AbstractActor;
import akka.japi.pf.ReceiveBuilder;
import bmstu.iu9.lab6.messages.RandomServer;
import bmstu.iu9.lab6.messages.ServerMessage;

import java.util.Random;

public class StorageActor extends AbstractActor {

    private String[] servers;

    private String getRandomServer() {
        String url = servers[new Random().nextInt(servers.length)];
        System.out.println("REQUEST REDIRECTED: " + url);
        return url;
    }

    @Override
    public Receive createReceive() {
        return ReceiveBuilder.create().match(ServerMessage.class, message -> {
            this.servers = message.getServers();
        }).match(RandomServer.class, message ->
                sender().tell(getRandomServer(), self())
        ).build();
    }

}
