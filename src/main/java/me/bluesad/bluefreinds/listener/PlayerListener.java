package me.bluesad.bluefreinds.listener;

import com.mysql.fabric.xmlrpc.base.Array;
import me.bluesad.bluefreinds.Blue;
import me.bluesad.bluefreinds.Main;
import me.bluesad.bluefreinds.manager.Config;
import me.bluesad.bluefreinds.manager.Individual;
import me.bluesad.bluefreinds.manager.Message;
import me.bluesad.bluefreinds.util.Util;
import org.bluesad.tablet.util.item.ItemStackUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.scheduler.BukkitRunnable;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author bluesad
 * */
public class PlayerListener implements Listener{
    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        Player p = event.getPlayer();
        new BukkitRunnable(){
            @Override
            public void run() {
                Util.sendHub(p, Message.ON_JOIN.
                        replaceAll("%c%",String.valueOf(new Individual(p.getName()).getSystemMessageList().size())));
            }
        }.runTaskLater(Main.getInstance(),60L);
        if(Config.JOIN_MSG){
            for(String name : new Individual(p.getName()).getFriendList()){
                Individual friend = new Individual(name);
                if(friend.isOnline()){
                   Util.sendHub(friend.asPlayer(), Message.JOIN_MSG.replaceAll("%player%",p.getName()));
                };
            }
        }
    }

    @EventHandler
    public void onClose(InventoryCloseEvent event){
        Inventory inv = event.getInventory();
        Individual individual = new Individual(event.getPlayer().getName());
        if("添加附件".equals(event.getInventory().getTitle())){
            individual.getMailEditor().setItems(ItemStackUtil.toItemStackList(inv.getContents()));
            Bukkit.dispatchCommand((Player)event.getPlayer(),"tablet open maileditor.yml");
        }
    }
}
