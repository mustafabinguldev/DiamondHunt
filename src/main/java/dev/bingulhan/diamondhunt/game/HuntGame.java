package dev.bingulhan.diamondhunt.game;

import dev.bingulhan.diamondhunt.game.status.ingame.IngameStatus;
import dev.bingulhan.diamondhunt.game.status.waiting.WaitingStatus;
import lombok.Getter;
import lombok.Setter;
import online.bingulhan.extentedbukkit.HanArenaAPI;
import online.bingulhan.extentedbukkit.arena.Arena;
import online.bingulhan.minigameapi.game.GameVariant;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;


public class HuntGame extends GameVariant {
    /**
     * Create a new Game
     *
     * @param gameName
     * @param plugin
     * @param init
     */
    public HuntGame(String gameName, JavaPlugin plugin, boolean init) {
        super(gameName, plugin, init);
    }

    @Getter
    @Setter
    private HuntData data;

    @Override
    protected void reset() {

    }

    @Override
    protected void onEnable() {
        data = new HuntData(this);

        getPlugin().getLogger().info("Game is ready");
        addStatus(new WaitingStatus("Bekleme", this));
        addStatus(new IngameStatus("Oyun", this));

        setStatus(0);
    }

    @Override
    protected void onDisable() {
        getPlayers().stream().forEach(p -> p.toPlayer().get().sendMessage(ChatColor.RED+"Oyun bitti arena sıfırlanıyor."));

        getPlugin().getServer().shutdown();
        
    }


}
