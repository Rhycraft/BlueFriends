package me.bluesad.bluefreinds.bungeecord;

import me.bluesad.bluefreinds.manager.BungeeCord;

public class BCUtil {
    public static boolean isBungeeCord(){
        return BungeeCord.BUNGEE_CORD;
    }
    public static String getServerName(){
        return BungeeCord.SERVER_NAME;
    }
}
