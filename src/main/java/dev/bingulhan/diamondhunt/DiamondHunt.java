package dev.bingulhan.diamondhunt;

import dev.bingulhan.diamondhunt.game.HuntGame;
import dev.bingulhan.diamondhunt.game.cmd.HuntCMD;
import dev.bingulhan.diamondhunt.listener.GlobalListener;
import lombok.Getter;
import online.bingulhan.minigameapi.game.GameVariant;
import org.bukkit.plugin.java.JavaPlugin;

public final class DiamondHunt extends JavaPlugin {

    @Getter
    private GameVariant gameVariant;

    @Getter
    private static DiamondHunt instance;

    @Override
    public void onEnable() {

        instance=this;


        getCommand("hunt").setExecutor(new HuntCMD());

        getConfig().options().copyDefaults(true);
        saveConfig();

       gameVariant = new HuntGame("Hunt", this, true);

       getServer().getPluginManager().registerEvents(new GlobalListener(), this);
       getLogger().info("Plugin is ready.");

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
