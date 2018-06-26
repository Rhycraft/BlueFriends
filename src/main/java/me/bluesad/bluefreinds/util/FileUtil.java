package me.bluesad.bluefreinds.util;

import org.bukkit.configuration.file.FileConfiguration;

/**
 * @author bluesad
 * */
public class FileUtil {
    public static boolean containsPath(FileConfiguration f,String path){
        return f.getKeys(true).contains(path);
    }
}
