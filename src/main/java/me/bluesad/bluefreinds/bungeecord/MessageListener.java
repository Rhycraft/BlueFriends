package me.bluesad.bluefreinds.bungeecord;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;

public class MessageListener implements PluginMessageListener {
    @Override
    public void onPluginMessageReceived(String channel, Player player, byte[] bytes) {
        System.out.println("接受");
        try {
            System.out.println(new String(bytes, "UTF-8"));
        }catch (Exception e){
            e.printStackTrace();
        }
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
