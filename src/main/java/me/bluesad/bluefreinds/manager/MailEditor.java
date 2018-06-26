package me.bluesad.bluefreinds.manager;

import me.bluesad.bluefreinds.Blue;
import me.bluesad.bluefreinds.database.Table;
import org.bukkit.OfflinePlayer;
import org.bukkit.inventory.ItemStack;
import java.util.ArrayList;
import java.util.List;

/**
 * @author bluesad
 * */
public class MailEditor {
    public MailEditor(OfflinePlayer player){
        from = player.getName();
    }

    private static Table table = Blue.getDBManager().getIndividualTable();
    private String from;

    public MailEditor setTo(String to) {
        table.set(from+".editor_to",to);
        return this;
    }

    public MailEditor setTitle(String title) {
        table.set(from+".editor_title",title);
        return this;
    }

    public MailEditor setItems(List<ItemStack> items) {
        table.set(from+".editor_items",items);
        return this;
    }

    public MailEditor setContent(String content) {
        table.set(from+".editor_content",content);
        return this;
    }

    public String getTo() {
        return table.getString(from+".editor_to");
    }

    public List<ItemStack> getItems() {
        return table.getItemStackList(from+".editor_items");
    }

    public String getContent() {
        return table.getString(from+".editor_content");
    }

    public String getTitle() {
        return table.getString(from+".editor_title");
    }

    public boolean isSystemMail() {
        return new Individual(from).isOp();
    }

    /**
     * @return 构建出的Mail
     * */
    public Mail build(){
        Mail mail = new Mail(from,getTo(),getTitle(),getContent(),getItems());
        clear();
        return mail;
    }
    public Mail buildModel(){
        Mail mail = new Mail(from,"empty",getTitle(),getContent(),getItems());
        clear();
        return mail;
    }

    public void clear(){
         setTo("");
         setTitle("");
         setContent("");
         setItems(new ArrayList<>());
    }

}
