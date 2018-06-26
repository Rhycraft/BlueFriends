package me.bluesad.bluefreinds.util.exception;

import me.bluesad.bluefreinds.Blue;
import me.bluesad.bluefreinds.manager.Mail;
import me.bluesad.bluefreinds.util.Util;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.UUID;

/**
 * 用于检测指令中可能存在的错误
 * @author bluesad
 * */
public class CommandChecker {

    private static String prefix = "§c";

    /**
     * 检查一名玩家是否为OP
     * */
    public static void checkOP(Player p) throws IllegalCommandException{
        if(!p.isOp()) {
            throw new IllegalCommandException(prefix+"你没有权限执行这条指令,需要的权限:§eOP");
        }
    }
    /**
     * 检查一个指令的参数数量是否正确
     * */
    public static void checkSize(int rightsize,int size) throws IllegalCommandException{
        if(size < rightsize) {
            throw new IllegalCommandException(prefix+"参数错误,请检查你的输入!");
        }
    }
    /**
     * 检查一个玩家是否存在
     * */
    public static void checkPlayerExists(String name) throws IllegalCommandException{
        if(!Util.existsPlayer(name)){
            throw new IllegalCommandException(prefix+"玩家 §e"+name+" §c不存在!");
        }
    }
    /**
     * 检查一个玩家是否在线
     * */
    public static void checkPlayerOnline(String name)throws IllegalCommandException{
        if(Bukkit.getPlayer(name)==null) {
            throw new IllegalCommandException(prefix+"玩家 §e"+name+" §c不存在或不在线!");
        }
    }
    /**
     * 检查一个指令发送者是否为玩家
     * */
    public static Player checkSender(CommandSender sender)throws IllegalCommandException{
        if(sender instanceof Player){
            return (Player)sender;
        }else{
            throw new IllegalCommandException(prefix+"指令的发送者必须为玩家");
        }
    }
    /**
     * 检查指令发送者是否拥有某权限
     * */
    public static void checkPermission(CommandSender sender,String permission){
        if(!sender.hasPermission(permission)){
            throw new IllegalCommandException(prefix+"你没有权限执行这条指令,需要的权限:§e"+permission);
        }
    }
    /**
     * 检查邮件是否存在
     * */
    public static Mail checkMailExists(String uuid) throws MailNotFoundException{
        if(!Blue.getDBManager().getMailTable().existsKey(uuid)) {
            throw new MailNotFoundException(prefix+"邮件(§e"+uuid+")§c不存在!");
        }else{
            Mail mail = new Mail(UUID.fromString(uuid));
            return mail;
        }
    }
    /**
     * 设置提示信息的前缀
     * */
    public static void setPrefix(String prefix) {
        CommandChecker.prefix = prefix;
    }
}
