package me.bluesad.bluefreinds.bungeecord;

import me.bluesad.bluefreinds.util.Util;
import org.bukkit.entity.Player;

public class PacketHandler {
    static void handle(Player target,BungeeCordPacket packet){
        String label = packet.getLabel();
        String[] args = packet.getArguments();
        if(label.equalsIgnoreCase(BCCommands.SEND_HUB)){
            Util.sendHub(target, args);
        }else if(args[0].equalsIgnoreCase(BCCommands.SEND_MESSAGE)){
            target.sendMessage(args);
        }
    }
}
