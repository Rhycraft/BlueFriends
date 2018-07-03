package me.bluesad.bluefreinds.bungeecord;

import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;

public class MessageListener implements PluginMessageListener {
    @Override
    public void onPluginMessageReceived(String channel, Player player, byte[] bytes) {
        System.out.println("Get:"+channel);
        try {
            System.out.println(new String(bytes, "UTF-8"));
        }catch (Exception e){e.printStackTrace();}
        if(channel.equalsIgnoreCase(BungeeCord.CHANNEL)){
            BungeeCordPacket packet = BungeeCordPacket.fromBytes(bytes);
            if(packet.getTarget().equalsIgnoreCase(player.getName())) {
                if (packet.getSecretKey().equals(BungeeCord.SECRET_KEY)) {
                    PacketHandler.handle(player,packet);
                }
            }
        }
    }
}
