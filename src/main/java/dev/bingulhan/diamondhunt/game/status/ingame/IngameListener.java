package dev.bingulhan.diamondhunt.game.status.ingame;

import dev.bingulhan.diamondhunt.game.HuntData;
import dev.bingulhan.diamondhunt.game.HuntPlayer;
import online.bingulhan.minigameapi.game.status.StatusListener;
import online.bingulhan.minigameapi.game.status.StatusVariant;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerLoginEvent;

public class IngameListener extends StatusListener {

    public IngameListener(StatusVariant state, String name) {
        super(state, name);
    }

    @EventHandler
    public void onDamage(EntityDamageEvent e) {
        if (!isGamePlayer(e.getEntity().getName())) return;

        e.setCancelled(true);
    }

    @EventHandler
    public void onLogin(PlayerLoginEvent e) {
        e.disallow(PlayerLoginEvent.Result.KICK_WHITELIST, "Oyun Basladi!");
    }

    @EventHandler
    public void onBreak(BlockBreakEvent e) {
        if (!isGamePlayer(e.getPlayer().getName())) return;

        HuntData data = ((IngameStatus) getStatus()).getData();
        if (e.getBlock().getType().equals(data.getGoolMaterial())) {
            e.setCancelled(true);
            e.getBlock().setType(Material.AIR);
            HuntPlayer player = (HuntPlayer) getPlayerData(e.getPlayer().getName()).get();

            int remaingGool = data.getGool() - player.getScore();


            player.setScore(player.getScore()+1);


            if (remaingGool != 0) {
                getStatus().sendMessageAll("&f"+player.getName()+"&e Bir skor daha aldı, kazanması için &a"+remaingGool+"&e kaldı!");
            }else {
                getStatus().sendMessageAll("&f"+player.getName()+"&a Oyunu kazandı!");
                e.getPlayer().setGameMode(GameMode.SPECTATOR);
                ((IngameStatus) getStatus()).getWinners().add(e.getPlayer().getUniqueId());

            }

        }

    }
}
