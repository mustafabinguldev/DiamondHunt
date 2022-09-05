package dev.bingulhan.diamondhunt.game.status.waiting;

import dev.bingulhan.diamondhunt.game.HuntGame;
import dev.bingulhan.diamondhunt.game.HuntPlayer;
import online.bingulhan.minigameapi.game.status.StatusListener;
import online.bingulhan.minigameapi.game.status.StatusVariant;
import online.bingulhan.minigameapi.game.util.PlayerUtil;
import org.bukkit.GameMode;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class WaitingListener extends StatusListener {

    public WaitingListener(StatusVariant state, String name) {
        super(state, name);
    }

    @EventHandler
    public void onLogin(PlayerLoginEvent e) {
        getStatus().getGameVariant().getPlugin().getLogger().info("Status: "+isGamePlayer(e.getPlayer().getName()));

        int max = ((HuntGame) getStatus().getGameVariant()).getData().getMinPlayer();
        if (getStatus().getGameVariant().getPlayers().size()<max) {
            if (isGamePlayer(e.getPlayer().getName())==false) {
                getStatus().getGameVariant().addPlayer(e.getPlayer(), HuntPlayer.class);
            }
        }else {
            e.disallow(PlayerLoginEvent.Result.KICK_WHITELIST, "Oyun Dolu");
        }
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        if (!isGamePlayer(e.getPlayer().getName())) return;

        getStatus().injectScoreboard(true);
        e.getPlayer().setGameMode(GameMode.SURVIVAL);
        PlayerUtil.reload(e.getPlayer());

        int min = ((HuntGame) getStatus().getGameVariant()).getData().getMinPlayer();


        if (getStatus().getGameVariant().getPlayers().size()>=min) {
            ((WaitingStatus)getStatus()).countdown();
        }

    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        if (!isGamePlayer(e.getPlayer().getName())) return;
        getStatus().getGameVariant().removePlayer(e.getPlayer());
    }

    @EventHandler
    public void onBreak(BlockBreakEvent e) {
        if (isGamePlayer(e.getPlayer().getName())) e.setCancelled(true);
     }

    @EventHandler
    public void onPlace(BlockPlaceEvent e) {
        if (isGamePlayer(e.getPlayer().getName())) e.setCancelled(true);
    }

    @EventHandler
    public void onDamage(EntityDamageEvent e) {
        if (isGamePlayer(e.getEntity().getName())) e.setCancelled(true);
    }





}
