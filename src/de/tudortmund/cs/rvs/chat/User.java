package de.tudortmund.cs.rvs.chat;


public class User {

    private String name;
    private String host;
    private String port;



    public User(String name, String host, String port) {
        this.name = name;
        this.host = host;
        this.port = port;
    }

    public String getName() {
        return name;
    }

    public String getHost() {
        return host;
    }

    public String getPort() {
        return port;
    }

    @Override
    public String toString() {
        return name;
    }
}
