package dev.bingulhan.diamondhunt.game;

import lombok.Getter;
import lombok.Setter;
import online.bingulhan.minigameapi.game.GameVariant;
import online.bingulhan.minigameapi.game.objects.GamePlayer;

public class HuntPlayer extends GamePlayer {

    @Getter
    @Setter
    private int score;

    public HuntPlayer(String name, GameVariant game) {
        super(name, game);
    }


}
