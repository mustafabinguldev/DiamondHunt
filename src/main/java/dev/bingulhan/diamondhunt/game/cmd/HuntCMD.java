package dev.bingulhan.diamondhunt.game.cmd;

import dev.bingulhan.diamondhunt.DiamondHunt;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class HuntCMD implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player)) return true;

        if (sender.hasPermission("hunt.admin") || sender.isOp()) {
            if (args.length>0) {
                if (args[0].equals("setspawn")) {
                    Player player = (Player) sender;

                    DiamondHunt.getInstance().getConfig().set("game.spawn.x", player.getLocation().getX());
                    DiamondHunt.getInstance().getConfig().set("game.spawn.y", player.getLocation().getY());
                    DiamondHunt.getInstance().getConfig().set("game.spawn.z", player.getLocation().getZ());
                    DiamondHunt.getInstance().getConfig().set("game.spawn.yaw", player.getLocation().getYaw());
                    DiamondHunt.getInstance().getConfig().set("game.spawn.pitch", player.getLocation().getPitch());
                    DiamondHunt.getInstance().getConfig().set("game.spawn.world", player.getLocation().getWorld().getName());

                    DiamondHunt.getInstance().saveConfig();
                    player.sendMessage(ChatColor.GREEN+"Spawn ayarlandı!");

                }
            }
        }else {
            sender.sendMessage(ChatColor.RED+"No permission.");
        }
        return true;
    }
}
