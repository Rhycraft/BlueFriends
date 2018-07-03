package me.bluesad.bluefreinds.manager;

import me.bluesad.bluefreinds.Blue;
import me.bluesad.bluefreinds.util.Util;
import org.bukkit.Bukkit;

import java.util.List;

/**
 * @author bluesad
 * 管理员
 * */
public class Administrator{

    public static void sendMessage(String name,String... msg){
        Individual individual = new Individual(name);
        if(individual.isBCOnline()){
            Util.sendHub(Bukkit.getOfflinePlayer(individual.getUniqueId()),msg);
        }
        List<String> list = individual.getSystemMessageList();
        list.add(msg[0]);
        Blue.getDBManager().getIndividualTable().set(name+".systemmessagelist",list);
    }
}
