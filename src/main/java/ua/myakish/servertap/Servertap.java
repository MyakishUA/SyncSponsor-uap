package ua.myakish.servertap;

import io.servertap.plugin.api.ServerTapWebserverService;
import net.luckperms.api.LuckPerms;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import ua.myakish.servertap.rest.PingEndpoint;
import ua.myakish.servertap.rest.SponsorsEndpoint;

public final class Servertap extends JavaPlugin {

    private LuckPerms luckPermsApi;
    private ServerTapWebserverService webserverService;

    @Override
    public void onEnable() {
        webserverService = this.getServer().getServicesManager().load(ServerTapWebserverService.class);
        if (webserverService == null) {
            getLogger().warning("ServerTap Webserver Service not found. Disabling plugin.");
            this.getServer().getPluginManager().disablePlugin(this);
            return;
        }

        RegisteredServiceProvider<LuckPerms> provider = Bukkit.getServicesManager().getRegistration(LuckPerms.class);
        if (provider != null) {
            luckPermsApi = provider.getProvider();
        }

        new PingEndpoint(webserverService);
        new SponsorsEndpoint(webserverService, luckPermsApi);
        getLogger().info("ServerTap plugin enabled.");
    }

    @Override
    public void onDisable() {
        getLogger().info("ServerTap plugin disabled.");
    }
}
