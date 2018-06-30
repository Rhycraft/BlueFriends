package me.bluesad.bluefreinds.manager;

import me.bluesad.bluefreinds.Blue;
import org.bukkit.configuration.Configuration;

public class BungeeCord {
    private static Configuration config = Blue.getBungeeCord();
    public static boolean BUNGEE_CORD = config.getBoolean("bungeecord",false);
    public static String SERVER_NAME = config.getString("server_name","Server");
}
