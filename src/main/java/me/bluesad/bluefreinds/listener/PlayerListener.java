package me.bluesad.bluefreinds.listener;

import me.bluesad.bluefreinds.Main;
import me.bluesad.bluefreinds.bungeecord.BungeeCord;
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
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * @author bluesad
 * */
public class PlayerListener implements Listener{
    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        Player p = event.getPlayer();
        Individual individual = new Individual(p);
        individual.setServerName(BungeeCord.SERVER_NAME);
        new BukkitRunnable(){
            @Override
            public void run() {
                individual.setBCOnline(true);
                Util.sendHub(p, Message.ON_JOIN.
                        replaceAll("%c%",String.valueOf(new Individual(p.getName()).getSystemMessageList().size())));
            }
        }.runTaskLater(Main.getInstance(),60L);
        if(Config.JOIN_MSG){
            for(String name : new Individual(p.getName()).getFriendList()){
                Individual friend = new Individual(name);
                if(friend.isBCOnline()){
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

    @EventHandler
    public void onQuit(PlayerQuitEvent event){
        new Individual(event.getPlayer()).setBCOnline(false);
    }
}
