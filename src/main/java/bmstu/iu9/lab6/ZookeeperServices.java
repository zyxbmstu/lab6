package bmstu.iu9.lab6;

import akka.actor.ActorRef;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.zookeeper.*;

public class ZookeeperServices {

    private ZooKeeper zookeeper;
    private ActorRef storageActor;

    private static final String ZOOKEEPER_SERVER = "127.0.0.1:2181";
    private static final String SERVERS_PATH = "/servers";
    private static final int TIMEOUT = 5000;
    private static final Watcher DEFAULT_WATCHER = null;

    private ZooKeeper create() throws IOException {
        return new ZooKeeper(ZOOKEEPER_SERVER, TIMEOUT, DEFAULT_WATCHER);
    }

    public ZookeeperServices(ActorRef storageActor) throws IOException {
        this.zookeeper = create();
        this.storageActor = storageActor;
    }

    private void watchServerList() {
        try {
            final List<String> servers = new ArrayList<>();
            final List<String> serverNames = zookeeper.getChildren(SERVERS_PATH, event -> {
                if (event.getType() == Watcher.Event.EventType.NodeChildrenChanged) {
                    watchServerList();
                }
            });

            for (String serverName : serverNames) {
                byte[] url = zookeeper.getData(SERVERS_PATH + "/" + serverName, DEFAULT_WATCHER, null);
                servers.add(new String(url));
            }
        } catch (KeeperException | InterruptedException e) {
            e.printStackTrace();
        }
    }

}
