package me.bluesad.bluefreinds.bungeecord;

import me.bluesad.bluefreinds.Blue;
import org.bukkit.configuration.Configuration;

public class BungeeCord {
    private static Configuration config = Blue.getBungeeCord();
    public static boolean BUNGEE_CORD = config.getBoolean("bungeecord",false);
    public static String SERVER_NAME = config.getString("server_name","Server");
    public static String CHANNEL = config.getString("channel","BlueFriends");
    public static String SECRET_KEY = config.getString("secret_key","5cfbddc81084785cd45bc1a4b06d1843");
    public static String ENCODING = config.getString("encoding","UTF-8");
}
