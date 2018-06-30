package me.bluesad.bluefreinds.manager;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import me.bluesad.bluefreinds.Blue;
import me.bluesad.bluefreinds.Main;
import me.bluesad.bluefreinds.bungeecord.BCCommands;
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
        if(individual.isOnline()){
            Util.sendHub(individual.asPlayer(),msg);
        }else{
            ByteArrayDataOutput out = ByteStreams.newDataOutput();
            for (String arg : msg){
                out.writeUTF(" "+arg);
            }
            out.writeUTF(BCCommands.SEND_HUB);
            Bukkit.getServer().sendPluginMessage(Main.getInstance(),"BungeeCord",out.toByteArray());
        }
        List<String> list = individual.getSystemMessageList();
        list.add(msg[0]);
        Blue.getDBManager().getIndividualTable().set(name+".systemmessagelist",list);
    }
}
