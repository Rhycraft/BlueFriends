package me.bluesad.bluefreinds.util;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class YMLConfiguration extends YamlConfiguration {
    public YMLConfiguration(File source){
        try {
            load(source);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<String> getStringList(String path) {
        List<String> result = new ArrayList<>();
        super.getStringList(path).forEach(str->result.add(str.replaceAll("&","§")));
        return result;
    }

    @Override
    public String getString(String path) {
        return super.getString(path,"§c<无法找到文本:"+path+">").replaceAll("&","§");
    }

    @Override
    public String getString(String path, String def) {
        return super.getString(path, def).replaceAll("&","§");
    }

}
