package dev.bingulhan.diamondhunt.game;

import lombok.Getter;
import lombok.Setter;
import online.bingulhan.minigameapi.game.GameVariant;
import online.bingulhan.minigameapi.game.objects.GameData;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;

@Getter
@Setter
public class HuntData extends GameData {


    private int borderSize;
    private int delay;
    private int borderRemoveAmount = 1;
    private int gool;

    private int minPlayer = 2;
    private int maxPlayer = 12;

    private int gameTimeStartMinute=1;

    private Material goolMaterial;

    private World gameWorld;

    private Location spawn;


    public HuntData(GameVariant game) {
        super(game);
    }
}
