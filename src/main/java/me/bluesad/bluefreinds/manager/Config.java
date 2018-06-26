package me.bluesad.bluefreinds.manager;

import me.bluesad.bluefreinds.Main;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * @author bluesad
 * */
public class Config{
    private static Configuration configuration = Main.getInstance().getConfig();
    public static String DATE_FORMAT = configuration.getString("date_format","yyyy年MM月dd日 HH:mm:ss");
    public static String DATABASE_TYPE = configuration.getString("database.type","SQLite");
    public static String DATABASE_SCHEMA = configuration.getString("database.schema","bluefriends");
    public static String DATABASE_URL = configuration.getString("database.url","%local%data.db").replaceAll("%local%", Main.getInstance().getDataFolder().getAbsolutePath().replaceAll("\\\\","/")+"/");
    public static String DATABASE_USER = configuration.getString("database.user","root");
    public static String DATABASE_PASSWORD = configuration.getString("database.password","root");
    public static String DATABASE_INDIVIDUAL_TABLE = configuration.getString("database.individual_table","individual");
    public static String DATABASE_MAIL_TABLE = configuration.getString("database.mail_table","mail");
    public static String ID_CARD_DEFAULT_HEAD_URL = configuration.getString("idcard.default.headurl","[local]img#4.png");
    public static String ID_CARD_DEFAULT_HEAD_BORDER_URL = configuration.getString("idcard.default.headborderurl","[local]img#3.png");
    public static String ID_CARD_DEFAULT_REAL_NAME = configuration.getString("idcard.default.readname","保密");
    public static String ID_CARD_DEFAULT_BIRTHDAY = configuration.getString("idcard.default.birthday","保密");
    public static String ID_CARD_DEFAULT_SEX = configuration.getString("idcard.default.sex","保密");
    public static String ID_CARD_DEFAULT_ADDRESS = configuration.getString("idcard.default.address","保密");
    public static String ID_CARD_DEFAULT_QQ = configuration.getString("idcard.default.qq","保密");
    public static String ID_CARD_DEFAULT_EMAIL = configuration.getString("idcard.default.email","保密");
    public static String ID_CARD_DEFAULT_SIGNATURE = configuration.getString("idcard.default.signature","该玩家没有任何个性签名");
    public static String HUB_URL = configuration.getString("hub.url","[local]hub#1.png");
    public static int HUB_X = configuration.getInt("hub.x",0);
    public static int HUB_Y = configuration.getInt("hub.y",0);
    public static int HUB_W = configuration.getInt("hub.w",200);
    public static int HUB_H = configuration.getInt("hub.h",35);
    public static int HUB_CONTENTS_X = configuration.getInt("hub.contents_x",20);
    public static int HUB_CONTENTS_Y = configuration.getInt("hub.contents_y",10);
    public static int HUB_TIME = configuration.getInt("hub.time",8);
    public static boolean JOIN_MSG = configuration.getBoolean("join_msg",true);
    public static String ONLINE_FORMAT = configuration.getString("online_format","§a●在线");
    public static String OFFLINE_FORMAT = configuration.getString("offline_format","§8●离线");
    public static String SYSTEM_FORMAT = configuration.getString("system_format","§c系统");
    public static String READ_FORMAT = configuration.getString("read_format","§8已读");
    public static String UNREAD_FORMAT = configuration.getString("unread_format","§a未读");

    /**
     * 重载配置文件
     * */
    public static void reload(){
        configuration = Main.getInstance().getConfig();
        DATE_FORMAT = configuration.getString("date_format","yyyy年MM月dd日 HH:mm:ss");
        DATABASE_TYPE = configuration.getString("database.type","SQLite");
        DATABASE_SCHEMA = configuration.getString("database.schema","bluefriends");
        DATABASE_URL = configuration.getString("database.url","%local%data.db");
        DATABASE_USER = configuration.getString("database.user","root");
        DATABASE_PASSWORD = configuration.getString("database.password","root");
        DATABASE_INDIVIDUAL_TABLE = configuration.getString("database.individual_table","individual");
        DATABASE_MAIL_TABLE = configuration.getString("database.mail_table","mail");
        ID_CARD_DEFAULT_HEAD_URL = configuration.getString("idcard.default.headurl","[local]img#4.png");
        ID_CARD_DEFAULT_HEAD_BORDER_URL = configuration.getString("idcard.default.headborderurl","[local]img#3.png");
        ID_CARD_DEFAULT_REAL_NAME = configuration.getString("idcard.default.readname","保密");
        ID_CARD_DEFAULT_BIRTHDAY = configuration.getString("idcard.default.birthday","保密");
        ID_CARD_DEFAULT_SEX = configuration.getString("idcard.default.sex","保密");
        ID_CARD_DEFAULT_ADDRESS = configuration.getString("idcard.default.address","保密");
        ID_CARD_DEFAULT_QQ = configuration.getString("idcard.default.qq","保密");
        ID_CARD_DEFAULT_EMAIL = configuration.getString("idcard.default.email","保密");
        ID_CARD_DEFAULT_SIGNATURE = configuration.getString("idcard.default.signature","该玩家没有任何个性签名");
        HUB_URL = configuration.getString("hub.url","[local]hub#1.png");
        HUB_X = configuration.getInt("hub.x",0);
        HUB_Y = configuration.getInt("hub.y",0);
        HUB_W = configuration.getInt("hub.w",200);
        HUB_H = configuration.getInt("hub.h",35);
        HUB_CONTENTS_X = configuration.getInt("hub.contents_x",20);
        HUB_CONTENTS_Y = configuration.getInt("hub.contents_y",10);
        HUB_TIME = configuration.getInt("hub.time",8);
        JOIN_MSG = configuration.getBoolean("join_msg",true);
        ONLINE_FORMAT = configuration.getString("online_format","§a●在线");
        OFFLINE_FORMAT = configuration.getString("offline_format","§8●离线");
        SYSTEM_FORMAT = configuration.getString("system_format","§c系统");
        READ_FORMAT = configuration.getString("read_format","§8已读");
        UNREAD_FORMAT = configuration.getString("unread_format","§a未读");
    }
}
