package dev.bingulhan.diamondhunt.game.status.waiting;

import online.bingulhan.minigameapi.game.objects.GameScoreboard;
import online.bingulhan.minigameapi.game.status.StatusVariant;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;

public class WaitingScoreboard extends GameScoreboard {

    public WaitingScoreboard(Player player, StatusVariant status) {
        super(player, status);
    }

    public WaitingScoreboard(StatusVariant status) {
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
        return new dev.bingulhan.diamondhunt.game.status.waiting.WaitingScoreboard(player, getStatus());
    }

    @Override
    public List<String> scores() {
        return Arrays.asList(" ", " &fOyuncular Bekleniyor", "   ");
    }
}
