package dev.bingulhan.diamondhunt.game.status.waiting;

import dev.bingulhan.diamondhunt.game.HuntData;
import dev.bingulhan.diamondhunt.game.HuntGame;
import online.bingulhan.minigameapi.game.GameVariant;
import online.bingulhan.minigameapi.game.objects.GameScoreboard;
import online.bingulhan.minigameapi.game.status.StatusVariant;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class WaitingStatus extends StatusVariant {

    public boolean isCountDownRun = false;

    public WaitingStatus(String name, GameVariant variant, boolean init) {
        super(name, variant, init);
    }

    public WaitingStatus(String name, GameVariant variant) {
        super(name, variant);
    }

    @Override
    protected void reset() {

    }

    @Override
    protected void onEnable() {

        getGameVariant().getPlugin().getLogger().info("Waiting status is ready");
        addListener(new WaitingListener(this, "main"));

        HuntData data = ((HuntGame) getGameVariant()).getData();

        FileConfiguration c = getGameVariant().getPlugin().getConfig();

        data.setMaxPlayer(c.getInt("game.max-player"));
        data.setMinPlayer(c.getInt("game.min-player"));
        data.setBorderSize(c.getInt("game.border-size"));
        data.setDelay(c.getInt("game.delay"));
        data.setGool(c.getInt("game.gool"));
        data.setGoolMaterial(Material.valueOf(c.getString("game.material")));
        data.setGameTimeStartMinute(c.getInt("game.game-time-minute"));
    }

    @Override
    protected void onDisable() {
        getGameVariant().nextState();
    }

    public void countdown() {

        if (isCountDownRun) return;

        isCountDownRun = true;

        getAlivePlayers().stream().forEach(p -> p.sendMessage(ChatColor.GREEN+"Oyun başlıyor son 45 saniye."));

        for (int index = 45; index >= 0; index--) {

            int finalIndex1 = index;
            addTimer(() -> {
                int finalIndex = finalIndex1;
                getAlivePlayers().stream().forEach(p -> p.setLevel(finalIndex));

                if (finalIndex1 == 30) {
                    getAlivePlayers().stream().forEach(p -> p.sendMessage(ChatColor.GREEN+"Oyun başlıyor son 30 saniye."));
                }else if (finalIndex1 == 15) {
                    getAlivePlayers().stream().forEach(p -> p.sendMessage(ChatColor.GREEN+"Oyun başlıyor son 15 saniye."));
                }else if (finalIndex1 <6) {
                    getAlivePlayers().stream().forEach(p -> p.sendMessage(ChatColor.GREEN+"Oyun başlıyor son "+finalIndex+" saniye"));
                }

                if (finalIndex1 < 1) {
                    stop();
                }
            }, Math.abs(1 * (index - 44)));

        }

    }

    @Override
    public GameScoreboard getScoreboard() {
        return new WaitingScoreboard(this);
    }

    @Override
    public StatusVariant clone(Boolean init) {
        return new WaitingStatus(name, getGameVariant(), init);
    }
}
