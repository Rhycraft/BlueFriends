package me.bluesad.bluefreinds.manager;

import me.bluesad.bluefreinds.Blue;
import me.bluesad.bluefreinds.database.Table;
import me.bluesad.bluefreinds.util.Util;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.*;

/**
 * @author bluesad
 * 代表一封邮件
 * */
public class Mail {


    private static Table table = Blue.getDBManager().getMailTable();
    private final UUID UNIQUE_ID;

    public Mail(String from,String to,String title,String content,List<ItemStack> items){
        this.UNIQUE_ID = UUID.randomUUID();
        setFrom(from);
        setTo(to);
        setTitle(title);
        setContent(content);
        setItems(items);
        setRead(false);
        setDate(Util.getDate());
    }
    public Mail(UUID uuid){
        this.UNIQUE_ID = uuid;
    }
    public Mail(Mail another){
        this.UNIQUE_ID = UUID.randomUUID();
        setFrom(another.getFromName());
        setTitle(another.getTitle());
        setContent(another.getContent());
        setItems(another.getItems());
        setRead(false);
        setDate(another.getDate());
    }

    /**
     * 领取附件
     * */
    public boolean receiveItems(){
        Player p = getTo().asPlayer();
        List<ItemStack> items = getItems();
        int size = items.size();
        p.getInventory().addItem(items.toArray(new ItemStack[items.size()]));
        table.set(getUniqueId()+".items",new ArrayList<>());
        return size != 0;
    }
    /**
     * 获取该邮件的UUID
     * */
    public UUID getUniqueId(){
        return UNIQUE_ID;
    }
    /**
     * 获取该邮件的发送人
     * */
    public Individual getFrom() {
        return new Individual(getFromName());
    }
    /**
     * 获取该邮件的接收人
     * */
    public Individual getTo() {
        return new Individual(getToName());
    }
    /**
     * 获取该邮件的发送人的名称
     * */
    public String getFromName(){
        return table.getString(getUniqueId()+".from_");
    }
    /**
     * 获取该邮件的接受人的名称
     * */
    public String getToName(){
        return table.getString(getUniqueId()+".to_");
    }
    /**
    * 获取该邮件的主题
    * */
    public String getTitle() {
        return table.getString(getUniqueId()+".title");
    }
    /**
     * 获取该邮件的内容
     * */
    public String getContent() {
        return table.getString(getUniqueId()+".content");
    }
    /**
     * 获取该邮件的附件
     */
    public List<ItemStack> getItems() {
        return table.getItemStackList(getUniqueId()+".items");
    }
    /**
     * 设置该邮件的附件
     * */
    public void setItems(List<ItemStack> items) {
        table.set(getUniqueId()+".items",items);
    }
    /**
     * 设置邮件内容
     * */
    public void setContent(String content) {
        table.set(getUniqueId()+".content",content);
    }
    /**
     * 设置邮件发送日期
     * */
    public void setDate(String date) {
        table.set(getUniqueId()+".date",date);
    }
    /**
     * 设置邮件发件人
     * */
    public void setFrom(String from) {
        table.set(getUniqueId()+".from_",from);
    }
    /**
     * 设置邮件标题
     * */
    public void setTitle(String title) {
        table.set(getUniqueId()+".title",title);
    }
    /**
     * 设置邮件接收人
     * */
    public void setTo(String to) {
        table.set(getUniqueId()+".to_",to);
    }
    /**
     * 获取该邮件的发送日期
     * */
    public String getDate() {
        return table.getString(getUniqueId()+".date");
    }
    /**
     * @return 该邮件是否已读
     * */
    public boolean isRead() {
        return table.getBoolean(getUniqueId()+".read");
    }
    /**
     * 设置该邮件是否为已读
     * */
    public void setRead(boolean read) {
        table.set(getUniqueId()+".read",read);
    }
    /**
     * @return 该邮件是否为系统邮件
     * */
    public boolean isSystemMail(){
        return new Individual(getFromName()).isOp();
    }
    @Override
    public String toString() {
        return getTitle();
    }

    @Override
    public int hashCode() {
        return getUniqueId().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Mail) {
            Mail mail = (Mail) obj;
            return mail.getUniqueId().equals(getUniqueId());
        }else{
            return false;
        }
    }

    public String replace(String msg){
        return getTo().replace(msg.replaceAll("%uuid%",getUniqueId().toString())
                .replaceAll("%title%",getTitle())
                .replaceAll("%to%",getTo().isOp() ? Config.SYSTEM_FORMAT : getToName())
                .replaceAll("%from%",getFromName())
                .replaceAll("%date%",getDate()))
                .replaceAll("%read%",isRead() ? Config.READ_FORMAT : Config.UNREAD_FORMAT)
                .replaceAll("%content%",getContent());
    }
    /**
     * 删除该邮件
     * */
    public void delete(){
        Blue.getDBManager().getMailTable().delete(UNIQUE_ID.toString());
    }

}
