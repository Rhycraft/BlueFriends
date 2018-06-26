package me.bluesad.bluefreinds;

import me.bluesad.bluefreinds.command.BFCommand;
import me.bluesad.bluefreinds.database.DBManager;
import me.bluesad.bluefreinds.gui.FriendList;
import me.bluesad.bluefreinds.gui.MailList;
import me.bluesad.bluefreinds.gui.MessageList;
import me.bluesad.bluefreinds.gui.RequestList;
import me.bluesad.bluefreinds.listener.PlayerListener;
import me.bluesad.bluefreinds.manager.*;
import me.bluesad.bluefreinds.util.PapiUtil;
import me.bluesad.bluefreinds.util.YMLConfiguration;
import org.bluesad.tablet.Tablet;
import org.bluesad.tablet.hook.TabletHook;
import org.bluesad.tablet.manager.TabletManager;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

/**
 * @author bluesad
* */
public final class Main extends JavaPlugin {

    private static Main instance;
    static YMLConfiguration config;
    static YMLConfiguration message;
    static DBManager dbManager;
    static TabletManager manager;

    @Override
    public void onLoad() {
        instance = this;
        saveResource("config.yml",false);
        saveResource("message.yml",false);
        new File(getDataFolder(),"/gui").mkdirs();
        saveResource("gui/main.yml",false);
        saveResource("gui/query.yml",false);
        saveResource("gui/mail.yml",false);
        saveResource("gui/idcard.yml",false);
        saveResource("gui/friendsystem.yml",false);
        saveResource("gui/mailsystem.yml",false);
        saveResource("gui/maileditor.yml",false);
        saveResource("gui/friendlist.yml",false);
        saveResource("gui/maillist.yml",false);
        saveResource("gui/messagelist.yml",false);
        saveResource("gui/editor_title.yml",false);
        saveResource("gui/editor_title_op.yml",false);
        saveResource("gui/editor_to.yml",false);
        saveResource("gui/requestlist.yml",false);
        saveResource("gui/editor_content.yml",false);
        saveResource("gui/editor_content_op.yml",false);
        saveResource("gui/editor_head_url.yml",false);
        saveResource("gui/idcard_me.yml",false);
        saveResource("gui/idcardeditor.yml",false);
        saveResource("gui/editor_address.yml",false);
        saveResource("gui/editor_birthday.yml",false);
        saveResource("gui/editor_email.yml",false);
        saveResource("gui/editor_qq.yml",false);
        saveResource("gui/editor_realname.yml",false);
        saveResource("gui/editor_sex.yml",false);
        saveResource("gui/editor_signature.yml",false);
        saveResource("gui/maileditor_op.yml",false);
        saveResource("lang/zh-CN.yml",false);
    }

    @Override
    public void onEnable() {
        log("┍ BlueFriends正在加载...");
        config = new YMLConfiguration(new File(getDataFolder(),"config.yml"));
        message = new YMLConfiguration(new File(getDataFolder(),"message.yml"));
        dbManager = new DBManager();
        manager = Tablet.getTabletManager();
        log("┠ 正在检测语言文本...");
        if(!new File(getDataFolder(),"lang/"+ Config.LANGUAGE+".yml").exists()){
            log("┠ 无法检测到语言文本:"+Config.LANGUAGE+",即将替换为:zh_CN");
            Config.LANGUAGE = "zh_CN";
        }else{
            log("┠ 使用语言文本:"+Config.LANGUAGE);
        }
        log("┝ 正在注册PlaceHolderAPI变量...");
        PapiUtil.registerHook();
        Bukkit.getPluginCommand("bf").setExecutor(new BFCommand());
        log("┝ 正在注册TabletAPI变量...");
        manager.registerTabletHook(new TabletHook() {
            @Override
            public String onTabletRequested(OfflinePlayer offlinePlayer, String s) {
                return new Individual(offlinePlayer).replace(s);
            }
        });
        log("┝ 注册插件指令成功!");
        Bukkit.getPluginManager().registerEvents(new PlayerListener(),this);
        log("┝ 注册插件监听器成功!");
        log("┝ 正在注册GUI界面...");
        manager.registerComponent("friend_list",FriendList.class);
        manager.registerComponent("mail_list",MailList.class);
        manager.registerComponent("request_list",RequestList.class);
        manager.registerComponent("message_list",MessageList.class);
        for(File file : new File(getDataFolder(),"gui").listFiles()){
            manager.registerTabletGui(file.getName(),"BlueFriends/gui/"+file.getName());
        }
        log("┝ 读取插件数据成功!");
        log("┕ BlueFriends已全面装载!");
    }

    @Override
    public void onDisable() {
        log("┍ BlueFriends正在卸载...");
        log("┝ 正在关闭数据库连接...");
        dbManager.close();
        log("┕ BlueFriends已卸载!");
    }

    @Override
    public void saveResource(String resourcePath, boolean replace) {
        if(!getDataFolder().exists()){
            getDataFolder().mkdirs();
        }
        if(replace){
            super.saveResource(resourcePath,true);
        }else{
            if(!new File(getDataFolder(),resourcePath).exists()){
                super.saveResource(resourcePath,true);
            }
        }
    }

    public static void log(String msg){
        instance.getLogger().info(msg);
    }

    public static Main getInstance() {
        return instance;
    }

    /**
     * 重载插件
     * */
    public static void reload(){
        instance.onLoad();
        config = new YMLConfiguration(new File(Main.getInstance().getDataFolder(),"config.yml"));
        message = new YMLConfiguration(new File(Main.getInstance().getDataFolder(),"message.yml"));
        Config.reload();
        Message.reload();
        Lang.reload();
        dbManager = dbManager.reload();
    }
}
