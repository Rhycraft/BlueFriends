package me.bluesad.bluefreinds.util;

import lk.vexview.api.VexViewAPI;
import lk.vexview.hud.VexImageShow;
import lk.vexview.hud.VexTextShow;
import me.bluesad.bluefreinds.Blue;
import me.bluesad.bluefreinds.Main;
import me.bluesad.bluefreinds.manager.Config;
import me.bluesad.bluefreinds.manager.Individual;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author bluesad
 * 工具类
 * */
public class Util {

    private static final Date date = new Date();
    /**
     * 获取当前日期
     * */
    public static String getDate(){
        SimpleDateFormat sdf = new SimpleDateFormat(Config.DATE_FORMAT);
        return sdf.format(date);
    }
    /**
     * 获取一个数组的List实例
     * */
    public static <T> List<T> asList(T... arr){
        List<T> list = new ArrayList<>();
        if(arr != null){
            for(T t : arr){
                if(t != null){
                    if(t instanceof ItemStack){
                        list.add((T)t);
                    }else{
                        list.add(t);
                    }
                }
            }
        }
        return list;
    }
    /**
     * 移除一个List中所有的Null值
     * */
    public static <T> List<T> removeNull(List<T> list){
        list.removeIf((T t)->t == null);
        return list;
    }
    /**
     * 移除一个List中所有的Null值
     * */
    public static <T> Set<T> removeNull(Set<T> set){
        set.removeIf((T t)->t == null);
        return set;
    }

    public static void sendHub(Player p, String... message) {
        String url = Config.HUB_URL;
        int x = Config.HUB_X;
        int y = Config.HUB_Y;
        int w = Config.HUB_W;
        int h = Config.HUB_H;
        int cx = Config.HUB_CONTENTS_X;
        int cy = Config.HUB_CONTENTS_Y;
        int time = Config.HUB_TIME;
        VexTextShow textShow = new VexTextShow(0, cx, cy, asList(message), time);
        VexImageShow imageShow = new VexImageShow(0, url, x, y, w, h, w, h, time);
        VexViewAPI.sendHUD(p, textShow);
        VexViewAPI.sendHUD(p, imageShow);
    }
    /**
     * @name 玩家名
     * @return true 如果该玩家存在
     * */
    public static boolean existsPlayer(String name){
        OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(name);
        return offlinePlayer != null && offlinePlayer.getName() != null;
    }
    /**
     * @uuid 邮件UUID
     * @return true 如果玩邮件存在
     * */
    public static boolean existsMail(String uuid){
        return Blue.getDBManager().getMailTable().existsKey(uuid);
    }
    public static OfflinePlayer getOfflinePlayer(String name){
        if(existsPlayer(name)){
            return Bukkit.getOfflinePlayer(name);
        }
        throw new IllegalArgumentException("玩家"+name+"不存在.");
    }
    public static boolean workOut(String expression){
        if(expression.matches("[0-9]*(>|=|<)[0-9]*")){
            String[] arr = expression.split("(>|=|<)", 2);
            Double var1 = Double.valueOf(arr[0]);
            Double var2 = Double.valueOf(arr[1]);
            if (expression.matches("[0-9]*>[0-9]*")) {
                return var1 > var2;
            } else if (expression.matches("[0-9]*=[0-9]*")) {
                return var1.equals(var2);
            } else if (expression.matches("[0-9]*<[0-9]*")) {
                return var1 < var2;
            } else {
                throw new IllegalArgumentException(expression + " 不是一个合法的数学表达式");
            }
        }else{
            throw new IllegalArgumentException(expression + " 不是一个合法的数学表达式");
        }
    }
    /**
     * @param source 图片地址
     * @return true 如果该图片存在
     * */
    public static boolean isImageExists(String source) {
        if(source.startsWith("[local]")){
            return source.matches(".*[.](bmp|jpg|png|tiff|gif|pcx|tga|exif|fpx|svg|psd|cdr|pcd|dxf|ufo|eps|ai|raw|WMF|webp)");
        }
        try {
            URL url = new URL(source);
            URLConnection uc = url.openConnection();
            InputStream in = uc.getInputStream();
            if (source.equalsIgnoreCase(uc.getURL().toString()))
                in.close();
            return source.matches(".*[.](bmp|jpg|png|tiff|gif|pcx|tga|exif|fpx|svg|psd|cdr|pcd|dxf|ufo|eps|ai|raw|WMF|webp)");
        } catch (Exception e) {
            return false;
        }
    }

}
