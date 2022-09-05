package dev.bingulhan.diamondhunt.game.status.ingame;

import dev.bingulhan.diamondhunt.game.HuntData;
import dev.bingulhan.diamondhunt.game.HuntGame;
import dev.bingulhan.diamondhunt.game.status.ingame.task.IngameTask;
import lombok.Getter;
import online.bingulhan.minigameapi.game.GameVariant;
import online.bingulhan.minigameapi.game.objects.GameScoreboard;
import online.bingulhan.minigameapi.game.status.StatusVariant;
import online.bingulhan.minigameapi.game.util.ItemBuilder;
import online.bingulhan.minigameapi.game.util.PlayerUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import java.util.ArrayList;
import java.util.UUID;

public class IngameStatus extends StatusVariant {


    @Getter
    private IngameTask task;

    @Getter
    private HuntData data;

    public IngameStatus(String name, GameVariant variant, boolean init) {
        super(name, variant, init);
    }

    @Getter
    public ArrayList<UUID> winners = new ArrayList<UUID>();

    public IngameStatus(String name, GameVariant variant) {
        super(name, variant);
    }

    @Override
    protected void reset() {

    }

    @Override
    protected void onEnable() {

        data = ((HuntGame) getGameVariant()).getData();
        data.setGameWorld(getGameVariant().getPlugin().getServer().getWorlds().stream().filter(w -> w.getName().equals("world")).findAny().get());

        FileConfiguration c = getGameVariant().getPlugin().getConfig();

        Location location = new Location(Bukkit.getWorld(c.getString("game.spawn.world")), c.getInt("game.spawn.x"), c.getInt("game.spawn.y"), c.getInt("game.spawn.z"), c.getInt("game.spawn.yaw"), c.getInt("game.spawn.pitch"));
        data.setSpawn(location);

        data.setBorderRemoveAmount(c.getInt("game.borderamount"));
        task = new IngameTask(getGameVariant());
        task.runTaskTimer(getGameVariant().getPlugin(), 1L, 30L);

        getAlivePlayers().stream().forEach(player ->  {
            player.sendMessage(ChatColor.GREEN+"Oyun başladı bol şanslar!");
            PlayerUtil.reload(player);
            player.teleport(location);
            player.getInventory().addItem(new ItemBuilder(Material.DIAMOND_PICKAXE).addEnchant(Enchantment.DIG_SPEED, 5).build());
        });

        addListener(new IngameListener(this, "main"));

        data.getGameWorld().getWorldBorder().setCenter(location);
        data.getGameWorld().getWorldBorder().setSize(data.getBorderSize());

        borderTimer();
    }

    @Override
    protected void onDisable() {

        sendMessageAll("  ");
        if (winners.size()>0) {
            sendMessageAll("&a&lKazananlar: ");
            for (UUID uuid : winners) {
                sendMessageAll("&f- "+ Bukkit.getPlayer(uuid).getName());
            }
        }else{
            sendMessageAll(ChatColor.RED+"Kimse maalesef kazanamadı!");
        }
        sendMessageAll("  ");

        getGameVariant().nextState();
    }

    public void borderTimer() {
        addTimer(() -> {

            if (data.getGameWorld().getWorldBorder().getSize()>10) {
                data.getGameWorld().getWorldBorder().setSize(getData().getGameWorld().getWorldBorder().getSize()-data.getBorderRemoveAmount());
            }
            borderTimer();
        }, 1 * data.getDelay());
    }

    @Override
    public GameScoreboard getScoreboard() {
        return new IngameScoreboard(this);
    }

    @Override
    public StatusVariant clone(Boolean init) {
        return new IngameStatus("Oyun içi", getGameVariant(), init);
    }
}
