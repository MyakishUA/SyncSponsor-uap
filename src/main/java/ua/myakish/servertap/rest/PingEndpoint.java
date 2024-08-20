package ua.myakish.servertap.rest;

import io.servertap.plugin.api.ServerTapWebserverService;

public class PingEndpoint {

    private final ServerTapWebserverService webserverService;

    public PingEndpoint(ServerTapWebserverService webserverService) {
        this.webserverService = webserverService;
        addPingEndpoint();
    }

    public void addPingEndpoint() {
        webserverService.get("/api/ping", ctx -> ctx.status(200).result("Pong!"));
    }
}
