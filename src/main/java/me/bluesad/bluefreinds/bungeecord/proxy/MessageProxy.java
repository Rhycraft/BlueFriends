package me.bluesad.bluefreinds.bungeecord.proxy;

import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PluginMessageEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class MessageProxy implements Listener {
    @EventHandler
    public void onMessage(PluginMessageEvent event){
        String channel = event.getTag();
        System.out.println("Get:"+channel);
        if(channel.equalsIgnoreCase(BCConfig.CHANNEL)){
            for (ProxiedPlayer player : BungeeCord.getInstance().getPlayers()) {
                player.sendData(BCConfig.CHANNEL,event.getData());
            }
        }
    }
}
