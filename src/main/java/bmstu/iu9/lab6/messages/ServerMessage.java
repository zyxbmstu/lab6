package bmstu.iu9.lab6.messages;

public class ServerMessage {

    private String[] servers;

    public ServerMessage(String[] servers) {
        this.servers = servers;
    }

    public String[] getServers() {
        return servers;
    }

}
