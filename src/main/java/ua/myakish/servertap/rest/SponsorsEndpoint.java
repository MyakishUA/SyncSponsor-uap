package ua.myakish.servertap.rest;

import io.servertap.plugin.api.ServerTapWebserverService;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.model.user.User;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

import java.util.HashMap;
import java.util.Map;

public class SponsorsEndpoint {

    private final LuckPerms luckPermsApi;
    private final ServerTapWebserverService webserverService;

    public SponsorsEndpoint(ServerTapWebserverService webserverService, LuckPerms luckPermsApi) {
        this.webserverService = webserverService;
        this.luckPermsApi = luckPermsApi;
        addGetSponsorsEndpoint();
    }

    public void addGetSponsorsEndpoint() {
        webserverService.get("/api/sponsors", ctx -> {
            Map<String, Boolean> sponsors = new HashMap<>();

            OfflinePlayer[] players = Bukkit.getOfflinePlayers();
            for (OfflinePlayer player : players) {
                User user = luckPermsApi.getUserManager().getUser(player.getUniqueId());
                if (user != null) {
                    String group = user.getPrimaryGroup();
                    sponsors.put(player.getName(), group.contains("wealth"));
                }
            }

            ctx.status(200).json(sponsors);
        });
    }
}
