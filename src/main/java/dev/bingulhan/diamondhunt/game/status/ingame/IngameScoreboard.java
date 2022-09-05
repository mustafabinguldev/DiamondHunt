package dev.bingulhan.diamondhunt.game.status.ingame;

import online.bingulhan.minigameapi.game.objects.GameScoreboard;
import online.bingulhan.minigameapi.game.status.StatusVariant;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;

public class IngameScoreboard extends GameScoreboard {
    public IngameScoreboard(Player player, StatusVariant status) {
        super(player, status);
    }

    public IngameScoreboard(StatusVariant status) {
        super(status);
    }

    @Override
    public void onEnable() {
        setTitle("&b&lDIAMOND HUNT");
    }

    @Override
    public void onDisable() {

    }

    @Override
    public GameScoreboard clone(Player player) {
        return new IngameScoreboard(player, getStatus());
    }

    @Override
    public List<String> scores() {
        return Arrays.asList(" ", "&fKalan Süre: &e"+((IngameStatus) getStatus()).getTask().getDelay(), "  ");
    }
}
