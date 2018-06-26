package me.bluesad.bluefreinds.command;

import me.bluesad.bluefreinds.Main;
import me.bluesad.bluefreinds.manager.Administrator;
import me.bluesad.bluefreinds.manager.Individual;
import me.bluesad.bluefreinds.manager.Mail;
import me.bluesad.bluefreinds.manager.Message;
import me.bluesad.bluefreinds.util.Util;
import org.bluesad.tablet.Tablet;
import org.bluesad.tablet.component.TabletGui;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import java.util.List;
import java.util.UUID;
import static me.bluesad.bluefreinds.manager.Lang.*;

public class BFCommand implements CommandExecutor {
    private static final String COMMAND_LABEL = "bf";


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player)){
            sender.sendMessage(WARN_NO_PLAYER_SENDER);
            return false;
        }
        int size = args.length;
        Individual me = new Individual(sender.getName());
        Player p = (Player)sender;
        if(label.equalsIgnoreCase(COMMAND_LABEL)){
            if(size == 0 || args[0].equalsIgnoreCase("help")){
                sender.sendMessage(HELP_MESSAGE);
                if(sender.isOp()){
                    sender.sendMessage(OP_HELP_MESSAGE);
                }
                return true;
            }else if(args[0].equalsIgnoreCase("friend")){
                if(size >= 2){
                    if(args[1].equalsIgnoreCase("accept")){
                        if(Util.existsPlayer(args[2])){
                            Individual another = new Individual(args[2]);
                            if(me.acceptFriendRequest(another)){
                                p.sendMessage(Message.ACCEPT_FRIEND_TO.replaceAll("%player%",args[2]));
                                Administrator.sendMessage(args[2],Message.ACCEPT_FRIEND_FROM.replaceAll("%player%",sender.getName()));
                            }else{
                                sender.sendMessage(WARN_ILLEGAL_COMMAND);
                            }
                        }else{
                            sender.sendMessage(WARN_PLAYER_NOT_FOUND);
                        }
                    }else if(args[1].equalsIgnoreCase("reject")){
                        if(Util.existsPlayer(args[2])){
                            Individual another = new Individual(args[2]);
                            if(me.rejectFriendRequest(another)){
                                p.sendMessage(Message.REJECT_FRIEND_TO.replaceAll("%player%", args[2]));
                                Administrator.sendMessage(args[2], Message.REJECT_FRIEND_FROM.replaceAll("%player%", sender.getName()));
                            }else{
                                sender.sendMessage(WARN_ILLEGAL_COMMAND);
                            }
                        }else{
                            sender.sendMessage(WARN_PLAYER_NOT_FOUND);
                        }
                    }else if(args[1].equalsIgnoreCase("delete")){
                        if(Util.existsPlayer(args[2])){
                            Individual another = new Individual(args[2]);
                            if(me.deleteFriend(another)){
                                p.sendMessage(Message.DELETE_FRIEND.replaceAll("%player%",args[2]));
                            }else{
                                sender.sendMessage(WARN_ILLEGAL_COMMAND);
                            }
                        }else{
                            sender.sendMessage(WARN_PLAYER_NOT_FOUND);
                        }
                    }else if(args[1].equals("request")){
                        Individual another = new Individual(args[2]);
                        if(me.friendRequest(another)){
                            Administrator.sendMessage(another.getName(),Message.FRIEND_REQUESTED.replaceAll("%player%",args[2]));
                            sender.sendMessage(Message.FRIEND_REQUEST.replaceAll("%player%",another.getName()));
                        }else{
                            sender.sendMessage(WARN_ILLEGAL_COMMAND);
                        }
                    }
                }else{
                    sender.sendMessage(WARN_ARGUMENTS_NUMBER_WRONG);
                    return true;
                }
            }else if(args[0].equalsIgnoreCase("mail")){
                if(size >= 2){
                    if(args[1].equalsIgnoreCase("show")){
                        if(size >=3) {
                            if(Util.existsMail(args[2])) {
                                Mail mail = new Mail(UUID.fromString(args[2]));
                                if(mail.getTo().getUniqueId().equals(p.getUniqueId())){
                                    TabletGui gui = Tablet.getTabletManager().getGui(p, "mail.yml");
                                    gui.setExtraFunction(str -> mail.replace(str));
                                    gui.show();
                                }else{
                                    sender.sendMessage(WARN_ILLEGAL_COMMAND);
                                }
                            }else{
                                sender.sendMessage(WARN_MAIL_NOT_FOUND);
                            }
                        }else{
                            sender.sendMessage(WARN_ARGUMENTS_NUMBER_WRONG);
                        }
                    }else if(args[1].equalsIgnoreCase("delete")){
                        if(size >=3) {
                            if(Util.existsMail(args[2])) {
                                Mail mail = new Mail(UUID.fromString(args[2]));
                                if(mail.getTo().getUniqueId().equals(p.getUniqueId())){
                                    me.deleteMail(args[2]);
                                }else{
                                    sender.sendMessage(WARN_ILLEGAL_COMMAND);
                                }
                            }else{
                                sender.sendMessage(WARN_MAIL_NOT_FOUND);
                            }
                        }else{
                            sender.sendMessage(WARN_ARGUMENTS_NUMBER_WRONG);
                        }
                    }else if(args[1].equalsIgnoreCase("send")){
                        String to = me.getMailEditor().getTo();
                        if(to != null && Util.existsPlayer(to)){
                            new Individual(to).sendMail(me.getMailEditor().build());
                            p.sendMessage(Message.SEND_MAIL.replaceAll("%to%",to));
                            Administrator.sendMessage(to, Message.RECEIVE_MAIL.replaceAll("%from%",sender.getName()));
                        }
                    }else if(args[1].equals("getitems")){
                        if(size >= 3) {
                            if (Util.existsMail(args[2])) {
                                Mail mail = new Mail(UUID.fromString(args[2]));
                                if (mail.getTo().getUniqueId().equals(p.getUniqueId())) {
                                    if (mail.receiveItems()) {
                                        p.sendMessage(mail.replace(Message.RECEIVE_ITEMS_MAIL));
                                    } else {
                                        p.sendMessage(mail.replace(Message.FAIL_RECEIVE_ITEMS_MAIL));
                                    }
                                } else {
                                    sender.sendMessage(WARN_ILLEGAL_COMMAND);
                                }
                            } else {
                                sender.sendMessage(WARN_MAIL_NOT_FOUND);
                            }
                        }else{
                            sender.sendMessage(WARN_ARGUMENTS_NUMBER_WRONG);
                        }
                    }
                }else{
                    sender.sendMessage(WARN_ARGUMENTS_NUMBER_WRONG);
                }
            }else if(args[0].equals("person")){
                if(size >= 3) {
                    if (args[1].equals("signature")) {
                        me.setSignature(args[2]);
                        p.sendMessage(Message.SET_SIGNATURE.replaceAll("%value%", args[2]));
                    } else if (args[1].equals("url")) {
                        if(Util.isImageExists(args[2])) {
                            me.setHeadUrl(args[2]);
                            p.sendMessage(Message.SET_URL.replaceAll("%value%", args[2]));
                        }else{
                            sender.sendMessage(Message.ILLEGAL_IMAGE_URL.replaceAll("%url%",args[2]));
                        }
                    } else if (args[1].equals("realname")) {
                        me.setRealName(args[2]);
                        p.sendMessage(Message.SET_REAL_NAME.replaceAll("%value%", args[2]));
                    } else if (args[1].equals("address")) {
                        me.setAddress(args[2]);
                        p.sendMessage(Message.SET_ADDRESS.replaceAll("%value%", args[2]));
                    } else if (args[1].equals("sex")) {
                        me.setSex(args[2]);
                        p.sendMessage(Message.SET_SEX.replaceAll("%value%", args[2]));
                    } else if (args[1].equals("birthday")) {
                        me.setBirthday(args[2]);
                        p.sendMessage(Message.SET_BIRTHDAY.replaceAll("%value%", args[2]));
                    } else if (args[1].equals("email")) {
                        me.setEmail(args[2]);
                        p.sendMessage(Message.SET_EMAIL.replaceAll("%value%", args[2]));
                    } else if (args[1].equals("qq")) {
                        me.setQq(args[2]);
                        p.sendMessage(Message.SET_QQ.replaceAll("%value%", args[2]));
                    } else if (args[1].equals("editor")) {
                        if (args[2].equals("to")) {
                            if(size >= 4) {
                                me.getMailEditor().setTo(args[3]);
                                p.sendMessage(Message.SET_EDITOR_TO.replaceAll("%value%", args[3]));
                            }else{
                                sender.sendMessage(WARN_ARGUMENTS_NUMBER_WRONG);
                            }
                        } else if (args[2].equals("title")) {
                            if(size >= 4) {
                                me.getMailEditor().setTitle(args[3]);
                                p.sendMessage(Message.SET_EDITOR_TITLE.replaceAll("%value%", args[3]));
                            }else{
                                sender.sendMessage(WARN_ARGUMENTS_NUMBER_WRONG);
                            }
                        } else if (args[2].equals("content")) {
                            if(size >= 4) {
                                me.getMailEditor().setContent(args[3]);
                                p.sendMessage(Message.SET_EDITOR_CONTENT.replaceAll("%value%", args[3]));
                            }else{
                                sender.sendMessage(WARN_ARGUMENTS_NUMBER_WRONG);
                            }
                        } else if (args[2].equals("items")) {
                            List<ItemStack> items = me.getMailEditor().getItems();
                            Inventory inv = Bukkit.createInventory(null, 54, "添加附件");
                            inv.setContents(items.toArray(new ItemStack[items.size()]));
                            p.openInventory(inv);
                        }
                    }
                }else{
                    sender.sendMessage(WARN_ARGUMENTS_NUMBER_WRONG);
                }
            }else if(args[0].equals("native")){
                if(size >= 2) {
                    if (args[1].equals("deletemsg")) {
                        try {
                            me.deleteSystemMessage(Integer.valueOf(args[2]));
                        }catch (NumberFormatException e){
                            sender.sendMessage(WARN_ILLEGAL_COMMAND);
                            return false;
                        }
                    }
                }else{
                    sender.sendMessage(WARN_ARGUMENTS_NUMBER_WRONG);
                }
            }else if(args[0].equals("idcard")) {
                if (size >= 2) {
                    if (args[1].equals("showme")) {
                        Bukkit.dispatchCommand(p, "tablet open idcard_me.yml");
                    } else if (args[1].equals("show")) {
                        if (size >= 3) {
                            if(Util.existsPlayer(args[2])) {
                                Individual another = new Individual(args[2]);
                                TabletGui gui = Tablet.getTabletManager().getGui(p, "idcard.yml");
                                gui.getComponents().forEach(tabletComponent -> tabletComponent.setAngle(Bukkit.getOfflinePlayer(another.getUniqueId())));
                                gui.show();
                            }else{
                                sender.sendMessage(WARN_PLAYER_NOT_FOUND);
                            }
                        }else{
                            sender.sendMessage(WARN_ARGUMENTS_NUMBER_WRONG);
                        }
                    }
                }else{
                    sender.sendMessage(WARN_ARGUMENTS_NUMBER_WRONG);
                }
            }else if(args[0].equals("admin")){
                if(sender.isOp()) {
                    if(size >= 2) {
                        if (args[1].equals("headborder")) {
                            if (size >= 4) {
                                if (Util.existsPlayer(args[2])) {
                                    Individual another = new Individual(args[2]);
                                    if(Util.isImageExists(args[3])) {
                                        another.setHeadBorderUrl(args[3]);
                                        p.sendMessage(Message.SET_HEADBORDER.replaceAll("%value%", args[2]).replaceAll("%player%", args[2]));
                                    }else{
                                        sender.sendMessage(Message.ILLEGAL_IMAGE_URL.replaceAll("%url%",args[3]));
                                    }
                                } else {
                                    sender.sendMessage(WARN_PLAYER_NOT_FOUND);
                                }
                            } else {
                                sender.sendMessage(WARN_ARGUMENTS_NUMBER_WRONG);
                            }
                        } else if (args[1].equals("reload")) {
                            Main.reload();
                            p.sendMessage(RELOAD_COMPLETED);
                        } else if (args[1].equals("sendmailg")) {
                            if(size >= 3) {
                                Mail mail = me.getMailEditor().buildModel();
                                String condition = args[2];
                                for (OfflinePlayer offlinePlayer : Tablet.getOfflinePlayers()) {
                                    String to = offlinePlayer.getName();
                                    if (to != null) {
                                        if (!to.equals(p.getName())) {
                                            Mail another = new Mail(mail);
                                            another.setTo(offlinePlayer.getName());
                                            new Individual(offlinePlayer).sendMail(another);
                                            Administrator.sendMessage(to, Message.RECEIVE_MAIL.replaceAll("%from%", sender.getName()));
                                        }
                                    }
                                }
                                sender.sendMessage(MAIL_GROUP_SEND_COMPLETED);
                                mail.delete();
                            }else{
                                sender.sendMessage(WARN_ARGUMENTS_NUMBER_WRONG);
                            }
                        }
                    }else{
                        sender.sendMessage(WARN_ARGUMENTS_NUMBER_WRONG);
                    }
                }else{
                    sender.sendMessage(WARN_ILLEGAL_COMMAND);
                }
            }else if(args[0].equals("open")){
                if(size >= 2) {
                    Bukkit.dispatchCommand(p, "tablet open " + args[1]);
                }else{
                    sender.sendMessage(WARN_ARGUMENTS_NUMBER_WRONG);
                }
            }else{
                sender.sendMessage(HELP_MESSAGE);
                if(sender.isOp()){
                    sender.sendMessage(OP_HELP_MESSAGE);
                }
                return true;
            }
        }
        return false;
    }
}
