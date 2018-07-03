package me.bluesad.bluefreinds.manager;

import me.bluesad.bluefreinds.Main;
import me.bluesad.bluefreinds.util.YMLConfiguration;
import org.bukkit.configuration.Configuration;

import java.io.File;

public class Lang {
    private static Configuration lang = new YMLConfiguration(new File(Main.getInstance().getDataFolder(),"lang/"+Config.LANGUAGE+".yml"));
    public static String[] HELP_MESSAGE = lang.getStringList("help_message").toArray(new String[0]);
    public static String[] OP_HELP_MESSAGE = lang.getStringList("op_help_message").toArray(new String[0]);
    public static String WARN_ARGUMENTS_NUMBER_WRONG = lang.getString("warn_arguments_number_wrong","§6§l[BlueFriends]§c您输入的指令参数有误,请检查后重新输入.");
    public static String WARN_PLAYER_NOT_FOUND = lang.getString("warn_player_not_found","§6§l[BlueFriends]§c该玩家不存在!");
    public static String WARN_NO_PLAYER_SENDER = lang.getString("warn_no_player_sender","§6§l[BlueFriends]§c该指令的发送者必须为玩家!");
    public static String WARN_ILLEGAL_COMMAND = lang.getString("warn_illegal_command","§6§l[BlueFriends]§c您输入了一条非法指令,请重新输入!");
    public static String WARN_MAIL_NOT_FOUND = lang.getString("warn_mail_not_found","§6§l[BlueFriends]§c该邮件不存在!");
    public static String RELOAD_COMPLETED = lang.getString("reload_completed","§6[BlueFriends]§a插件重载完毕!");
    public static String MAIL_GROUP_SEND_COMPLETED = lang.getString("mail_group_send_completed","§6[BlueFriends]§a群发邮件成功!");
    public static String WARN_NO_PERMISSION = lang.getString("warn_no_permission","§6[BlueFriends]§c你没有权限执行这个指令,需要:§e%permission%!");
    public static String ILLEGAL_ENCODING = lang.getString("illegal_encoding","%encoding%不是一个合法的字符集.");
    public static String ERROR_LOAD_PACKET_FROM_JSON = lang.getString("error_load_packet_from_json","错误:无法从%json%中读取数据包.");

    public static void reload(){
        lang = new YMLConfiguration(new File(Main.getInstance().getDataFolder(),"lang/"+Config.LANGUAGE+".yml"));
        HELP_MESSAGE = lang.getStringList("help_message").toArray(new String[0]);
        OP_HELP_MESSAGE = lang.getStringList("op_help_message").toArray(new String[0]);
        WARN_ARGUMENTS_NUMBER_WRONG = lang.getString("warn_arguments_number_wrong","§6§l[BlueFriends]§c您输入的指令参数有误,请检查后重新输入.");
        WARN_PLAYER_NOT_FOUND = lang.getString("warn_player_not_found","§6§l[BlueFriends]§c该玩家不存在!");
        WARN_NO_PLAYER_SENDER = lang.getString("warn_no_player_sender","§6§l[BlueFriends]§c该指令的发送者必须为玩家!");
        WARN_ILLEGAL_COMMAND = lang.getString("warn_illegal_command","§6§l[BlueFriends]§c您输入了一条非法指令,请重新输入!");
        WARN_MAIL_NOT_FOUND = lang.getString("warn_mail_not_found","§6§l[BlueFriends]§c该邮件不存在!");
        RELOAD_COMPLETED = lang.getString("reload_completed","§6[BlueFriends]§a插件重载完毕!");
        MAIL_GROUP_SEND_COMPLETED = lang.getString("mail_group_send_completed","§6[BlueFriends]§a群发邮件成功!");
        WARN_NO_PERMISSION = lang.getString("warn_no_permission","§6[BlueFriends]§c你没有权限执行这个指令!");
        ILLEGAL_ENCODING = lang.getString("illegal_encoding","%encoding%不是一个合法的字符集.");
        ERROR_LOAD_PACKET_FROM_JSON = lang.getString("error_load_packet_from_json","错误:无法从%json%中读取数据包.");
    }
}
