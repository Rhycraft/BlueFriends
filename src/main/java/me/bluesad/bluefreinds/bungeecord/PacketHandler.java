package me.bluesad.bluefreinds.bungeecord;

import me.bluesad.bluefreinds.util.Util;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import java.util.Arrays;

public class PacketHandler {
    static void handle(String[] args){
        if(args[0].equalsIgnoreCase(BCCommands.SEND_HUB)){
            OfflinePlayer off = Bukkit.getOfflinePlayer(args[1]);
            if(off.isOnline()){
                Player player = off.getPlayer();
                Util.sendHub(player, Arrays.copyOfRange(args,1,args.length));
            }
        }else if(args[0].equalsIgnoreCase(BCCommands.SEND_MESSAGE)){
            OfflinePlayer off = Bukkit.getOfflinePlayer(args[1]);
            if(off.isOnline()){
                Player player = off.getPlayer();
                player.sendMessage(Arrays.copyOfRange(args,1,args.length));
            }
        }
    }
}
