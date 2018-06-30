package me.bluesad.bluefreinds.bungeecord;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;

public class MessageListener implements PluginMessageListener {
    @Override
    public void onPluginMessageReceived(String channel, Player player, byte[] bytes) {
        if(channel.equalsIgnoreCase("BungeeCord")){
            ByteArrayDataInput in = ByteStreams.newDataInput(bytes);
            String subChannel = in.readUTF();
            if(subChannel.equalsIgnoreCase("BlueFriends")){
                int size = in.readInt();
                String[] args = new String[size];
                for(int i = 0;i < size;i++){
                    args[i] = in.readUTF();
                }
                PacketHandler.handle(args);
            }
        }
    }
}
