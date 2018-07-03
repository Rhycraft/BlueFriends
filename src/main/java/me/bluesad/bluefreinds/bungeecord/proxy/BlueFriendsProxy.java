package me.bluesad.bluefreinds.bungeecord.proxy;

import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.logging.Logger;

public class BlueFriendsProxy extends Plugin {
    public static Logger logger;
    public static Configuration bcconfig;

    @Override
    public void onEnable() {
        logger = getLogger();
        logger.info("》正在加载配置文件...");
        saveResource("bcconfig.yml");
        try {
            bcconfig = ConfigurationProvider.getProvider(YamlConfiguration.class).load(new File(getDataFolder(), "bcconfig.yml"));
        }catch (IOException e){
            e.printStackTrace();
        }
        logger.info("》正在注册通信频道...");
        BungeeCord.getInstance().registerChannel(BCConfig.CHANNEL);
        logger.info("》正在注册插件监听器...");
        BungeeCord.getInstance().getPluginManager().registerListener(this,new MessageProxy());
        logger.info("》BlueFriendsProxy已加载.");
    }

    @Override
    public void onDisable() {
        logger.info("BlueFriendsProxy已卸载.");
    }

    public void saveResource(String path){
        if (!getDataFolder().exists()) {
            getDataFolder().mkdirs();
        }
        File file = new File(getDataFolder(), path);
        if (!file.exists()) {
            try (InputStream in = getResourceAsStream(path)) {
                Files.copy(in, file.toPath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
