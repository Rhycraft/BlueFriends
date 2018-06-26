package me.bluesad.bluefreinds.command;

import me.bluesad.bluefreinds.Main;
import me.bluesad.bluefreinds.manager.*;
import me.bluesad.bluefreinds.util.exception.CommandChecker;
import org.bluesad.tablet.Tablet;
import org.bluesad.tablet.component.TabletGui;
import org.bluesad.tablet.util.Checker;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.Configuration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import java.util.List;

/**
 * @author bluesad
 * */
public class BaseCommands implements CommandExecutor{

    static {
        CommandChecker.setPrefix("§6[BlueFriends]§c");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        try{
            Player p = CommandChecker.checkSender(sender);
            Individual individual = new Individual(p.getName());
            String name = p.getName();
            int size = args.length;
            if(label.equalsIgnoreCase("bf")){
                if(size > 1){
                    if(args[0].equals("friend")){
                        if(args[1].equals("accept")){
                            //接受添加好友
                            CommandChecker.checkSize(3,size);
                            Individual another = new Individual(args[2]);
                            if(individual.acceptFriendRequest(another)){
                                p.sendMessage(Message.ACCEPT_FRIEND_TO.replaceAll("%player%",args[2]));
                                Administrator.sendMessage(args[2],Message.ACCEPT_FRIEND_FROM.replaceAll("%player%",name));
                            }
                        }else if(args[1].equals("reject")){
                            CommandChecker.checkSize(3,size);
                            Individual another = new Individual(args[2]);
                            if(individual.rejectFriendRequest(another)){
                                p.sendMessage(Message.REJECT_FRIEND_TO.replaceAll("%player%", args[2]));
                                Administrator.sendMessage(args[2], Message.REJECT_FRIEND_FROM.replaceAll("%player%", name));
                            }
                        }else if(args[1].equals("delete")){
                            //删除好友
                            CommandChecker.checkSize(3,size);
                            Individual another = new Individual(args[2]);
                            if(individual.deleteFriend(another)){
                                p.sendMessage(Message.DELETE_FRIEND.replaceAll("%player%",args[2]));
                            }
                        }else if(args[1].equals("request")){
                            CommandChecker.checkSize(3,size);
                            Individual another = new Individual(args[2]);
                            individual.friendRequest(another);
                        }
                    }else if(args[0].equals("mail")){
                        if(args[1].equals("show")){
                            Mail mail = CommandChecker.checkMailExists(args[2]);
                            TabletGui gui = Tablet.getTabletManager().getGui(p,"mail.yml");
                            gui.setExtraFunction(str->mail.replace(str));
                            gui.show();
                        }else if(args[1].equals("delete")){
                            individual.deleteMail(args[2]);
                        }else if(args[1].equals("send")){
                            String to = individual.getMailEditor().getTo();
                            CommandChecker.checkPlayerExists(to);
                            new Individual(to).sendMail(individual.getMailEditor().build());
                            p.sendMessage(Message.SEND_MAIL.replaceAll("%to%",to));
                            Administrator.sendMessage(to, Message.RECEIVE_MAIL.replaceAll("%from%",name));
                        }else if(args[1].equals("getitems")){
                            Mail mail = CommandChecker.checkMailExists(args[2]);
                            if(mail.receiveItems()){
                                p.sendMessage(mail.replace(Message.RECEIVE_ITEMS_MAIL));
                            }else{
                                p.sendMessage(mail.replace(Message.FAIL_RECEIVE_ITEMS_MAIL));
                            }
                        }
                    }else if(args[0].equals("person")){
                        CommandChecker.checkSize(3,size);
                        if(args[1].equals("signature")){
                            individual.setSignature(args[2]);
                            p.sendMessage(Message.SET_SIGNATURE.replaceAll("%value%",args[2]));
                        }else if(args[1].equals("url")){
                            individual.setHeadUrl(args[2]);
                            p.sendMessage(Message.SET_URL.replaceAll("%value%",args[2]));
                        }else if(args[1].equals("realname")){
                            individual.setRealName(args[2]);
                            p.sendMessage(Message.SET_REAL_NAME.replaceAll("%value%",args[2]));
                        }else if(args[1].equals("address")){
                            individual.setAddress(args[2]);
                            p.sendMessage(Message.SET_ADDRESS.replaceAll("%value%",args[2]));
                        }else if(args[1].equals("sex")){
                            individual.setSex(args[2]);
                            p.sendMessage(Message.SET_SEX.replaceAll("%value%",args[2]));
                        }else if(args[1].equals("birthday")){
                            individual.setBirthday(args[2]);
                            p.sendMessage(Message.SET_BIRTHDAY.replaceAll("%value%",args[2]));
                        }else if(args[1].equals("email")){
                            individual.setEmail(args[2]);
                            p.sendMessage(Message.SET_EMAIL.replaceAll("%value%",args[2]));
                        }else if(args[1].equals("qq")){
                            individual.setQq(args[2]);
                            p.sendMessage(Message.SET_QQ.replaceAll("%value%",args[2]));
                        }else if(args[1].equals("editor")){
                            if(args[2].equals("to")){
                                CommandChecker.checkSize(4,size);
                                individual.getMailEditor().setTo(args[3]);
                                p.sendMessage(Message.SET_EDITOR_TO.replaceAll("%value%",args[3]));
                            }else if(args[2].equals("title")){
                                CommandChecker.checkSize(4,size);
                                individual.getMailEditor().setTitle(args[3]);
                                p.sendMessage(Message.SET_EDITOR_TITLE.replaceAll("%value%",args[3]));
                            }else if(args[2].equals("content")){
                                CommandChecker.checkSize(4,size);
                                individual.getMailEditor().setContent(args[3]);
                                p.sendMessage(Message.SET_EDITOR_CONTENT.replaceAll("%value%",args[3]));
                            }else if(args[2].equals("items")){
                                List<ItemStack> items = individual.getMailEditor().getItems();
                                Inventory inv = Bukkit.createInventory(null,54,"添加附件");
                                inv.setContents(items.toArray(new ItemStack[items.size()]));
                                p.openInventory(inv);
                            }
                        }
                    }else if(args[0].equals("native")){
                        if(args[1].equals("deletemsg")){
                            individual.deleteSystemMessage(Integer.valueOf(args[2]));
                        }
                    }else if(args[0].equals("idcard")){
                        if(args[1].equals("showme")){
                            Bukkit.dispatchCommand(p,"tablet open idcard_me.yml");
                        }else if(args[1].equals("show")){
                            CommandChecker.checkSize(3,size);
                            CommandChecker.checkPlayerExists(args[2]);
                            Individual another = new Individual(args[2]);
                            Configuration configuration = Tablet.getTabletManager().getGuiMap().get(args[1]);
                            if(configuration == null){
                                sender.sendMessage("§6[TabletAPI]§a无法找到该GUI界面!");
                            }else{
                                TabletGui gui = Tablet.getTabletManager().getGui(p,"idcard.yml");
                                gui.getComponents().forEach(tabletComponent -> tabletComponent.setAngle(Bukkit.getOfflinePlayer(another.getUniqueId())));
                                gui.show();
                            }
                        }
                    }else if(args[0].equals("admin")){
                        CommandChecker.checkOP(p);
                        if(args[1].equals("headborder")){
                            CommandChecker.checkSize(4,size);
                            CommandChecker.checkPlayerExists(args[2]);
                            Individual another = new Individual(args[2]);
                            another.setHeadBorderUrl(args[3]);
                            p.sendMessage(Message.SET_HEADBORDER.replaceAll("%value%",args[2]).replaceAll("%player%",args[2]));
                        }else if(args[1].equals("reload")){
                            Main.reload();
                            p.sendMessage("§6[BlueFriends]§a插件重载完毕!");
                        }else if(args[1].equals("sendmailg")){
                            Mail mail = individual.getMailEditor().buildModel();
                            for(OfflinePlayer offlinePlayer : Tablet.getOfflinePlayers()){
                                String to = offlinePlayer.getName();
                                if(!to.equals(p.getName())){
                                    Mail another = new Mail(mail);
                                    another.setTo(offlinePlayer.getName());
                                    new Individual(offlinePlayer).sendMail(another);
                                    p.sendMessage(Message.SEND_MAIL.replaceAll("%to%",to));
                                    Administrator.sendMessage(to, Message.RECEIVE_MAIL.replaceAll("%from%",name));
                                }
                            }
                            mail.delete();
                         }
                     }else if(args[0].equals("open")){
                        CommandChecker.checkSize(2,size);
                        Bukkit.dispatchCommand(p,"tablet open "+args[1]);
                     }
                }
            }
        }catch (IllegalArgumentException e){
            sender.sendMessage(e.getMessage());
        }
        return false;
    }

}
