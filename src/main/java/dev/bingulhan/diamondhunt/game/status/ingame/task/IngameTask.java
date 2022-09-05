package dev.bingulhan.diamondhunt.game.status.ingame.task;

import dev.bingulhan.diamondhunt.game.HuntGame;
import lombok.Getter;
import online.bingulhan.minigameapi.game.GameVariant;
import org.bukkit.scheduler.BukkitRunnable;

public class IngameTask extends BukkitRunnable {


    GameVariant game;

    @Getter
    int delay;

    public IngameTask(GameVariant game) {
        this.game= game;

        delay = ((HuntGame) game).getData().getGameTimeStartMinute()*60;
    }

    @Override
    public void run() {
        delay--;

        if (delay < 1) {
            game.getCurrentStatus().stop();
            cancel();
        }
    }
}
