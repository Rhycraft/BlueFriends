package me.bluesad.bluefreinds.util;

import me.bluesad.bluefreinds.Blue;
import me.bluesad.bluefreinds.Main;
import me.bluesad.bluefreinds.manager.Config;
import me.bluesad.bluefreinds.manager.Individual;
import me.bluesad.bluefreinds.manager.MailEditor;
import me.clip.placeholderapi.PlaceholderAPI;
import me.clip.placeholderapi.PlaceholderHook;
import org.bukkit.entity.Player;

/**
 * @author bluesad
 * */
public class PapiUtil {

    public static void registerHook(){
        PlaceholderAPI.registerPlaceholderHook("bf", new PlaceholderHook() {
                @Override
                public String onPlaceholderRequest(Player player, String s) {
                    Individual individual = new Individual(player);
                    MailEditor editor = individual.getMailEditor();
                    if(s.equals("online")){
                        return individual.isOnline() ? Config.ONLINE_FORMAT : Config.OFFLINE_FORMAT;
                    }else if(s.equals("name")){
                        return individual.getName();
                    }else if(s.equals("realname")){
                        return individual.getRealName();
                    }else if(s.equals("uuid")){
                        return individual.getUniqueId().toString();
                    }else if(s.equals("sex")){
                        return individual.getSex();
                    }else if(s.equals("qq")){
                        return individual.getQq();
                    }else if(s.equals("email")){
                        return individual.getEmail();
                    }else if(s.equals("birthday")){
                        return individual.getBirthday();
                    }else if(s.equals("address")){
                        return individual.getAddress();
                    }else if(s.equals("head_url")){
                        return individual.getHeadUrl();
                    }else if(s.equals("headborder_url")){
                        return individual.getHeadBorderUrl();
                    }else if(s.equals("signature")){
                        return individual.getSignature();
                    }else if(s.equals("maileditor_to")){
                        return editor.getTo();
                    }else if(s.equals("maileditor_content")){
                        return editor.getContent();
                    }else if(s.equals("maileditor_title")){
                        return editor.getTitle();
                    }else if(s.equals("maileditor_items")){
                        return String.valueOf(editor.getItems().size());
                    }else if(s.equals("friendlist")){
                        return String.valueOf(individual.getFriendList().size());
                    }else if(s.equals("messagelist")){
                        return String.valueOf(individual.getSystemMessageList().size());
                    }
                    return s;
                }
            });
    }
}
