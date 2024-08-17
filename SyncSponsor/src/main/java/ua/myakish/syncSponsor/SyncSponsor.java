package ua.myakish.syncSponsor;

import io.javalin.Javalin;
import io.servertap.api.v1.PluginApi;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.model.user.User;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;

public class MyPlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        LuckPerms luckPerms = LuckPermsProvider.get();

        Javalin app = PluginApi.getInstance().getJavalin();

        app.get("/api/sponsors", ctx -> {
            Map<String, Boolean> sponsors = new HashMap<>();

            for (Player player : Bukkit.getOnlinePlayers()) {
                User user = luckPerms.getUserManager().getUser(player.getUniqueId());
                if (user != null) {
                    // Перевіряємо, чи є у гравця одна з ролей wealth1, wealth2, wealth3, wealth4
                    boolean isSponsor = user.getPrimaryGroup().equalsIgnoreCase("wealth1") ||
                            user.getPrimaryGroup().equalsIgnoreCase("wealth2") ||
                            user.getPrimaryGroup().equalsIgnoreCase("wealth3") ||
                            user.getPrimaryGroup().equalsIgnoreCase("wealth4");
                    sponsors.put(player.getName(), isSponsor);
                }
            }

            ctx.json(sponsors);
        });
    }
}
