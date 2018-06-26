package me.bluesad.bluefreinds;

import me.bluesad.bluefreinds.database.DBManager;
import me.bluesad.bluefreinds.manager.*;
import me.bluesad.bluefreinds.util.YMLConfiguration;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.Map;

/**
 * @author bluesad
 * */
public class Blue {
    /**
     * 获取配置文件的Config实例
     * */
    public static YMLConfiguration getConfig(){
        return Main.config;
    }
    /**
     * 获取语言文件的Config实例
     * */
    public static YMLConfiguration getMessage(){
        return Main.message;
    }
    public static DBManager getDBManager(){
        return Main.dbManager;
    }
}
