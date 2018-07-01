package me.bluesad.bluefreinds.manager;

import me.bluesad.bluefreinds.Blue;
import me.bluesad.bluefreinds.bungeecord.BCUtil;
import me.bluesad.bluefreinds.database.Table;
import me.bluesad.bluefreinds.util.Util;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import java.util.*;


/**
 * @author bluesad
 * */
public class Individual implements Comparable<Individual>{
    private static Table table = Blue.getDBManager().getIndividualTable();
    private OfflinePlayer player;

    public Individual(OfflinePlayer player){
        this.player = player;
        String name = player.getName();
        if(!table.existsKey(name)){
            table.set(name+".online",true);
            table.set(name+".servername",BCUtil.getServerName());
            table.set(name+".headurl",Config.ID_CARD_DEFAULT_HEAD_URL);
            table.set(name+".headborderurl",Config.ID_CARD_DEFAULT_HEAD_BORDER_URL);
            table.set(name+".signature",Config.ID_CARD_DEFAULT_SIGNATURE);
            table.set(name+".realname",Config.ID_CARD_DEFAULT_REAL_NAME);
            table.set(name+".birthday",Config.ID_CARD_DEFAULT_BIRTHDAY);
            table.set(name+".email",Config.ID_CARD_DEFAULT_EMAIL);
            table.set(name+".sex",Config.ID_CARD_DEFAULT_SEX);
            table.set(name+".address",Config.ID_CARD_DEFAULT_ADDRESS);
            table.set(name+".qq",Config.ID_CARD_DEFAULT_QQ);
            table.set(name+".friendlist",new ArrayList<String>());
            table.set(name+".maillist",new ArrayList<String>());
            table.set(name+".requestlist",new ArrayList<String>());
            table.set(name+".systemmessagelist",new ArrayList<String>());
            table.set(name+".editor_to","");
            table.set(name+".editor_content","");
            table.set(name+".editor_title","");
            table.set(name+".editor_items",new ArrayList<ItemStack>());
            table.set(name+".editor_systemmail",false);
        }
    }
    public Individual(String name){
        this(Util.getOfflinePlayer(name));
    }
    /**
     * @return 玩家是否在线
     * */
    public boolean isOnline(){
        return player.isOnline();
    }
    /**
     * @return 玩家是否为OP
     * */
    public boolean isOp(){
        return player.isOp();
    }
    /**
     * @return 玩家的UUID
     * */
    public UUID getUniqueId(){
        return player.getUniqueId();
    }
    /**
     * @return 玩家的名称
     * */
    public String getName(){
        return player.getName();
    }
    /**
     * @return 获取玩家的真实姓名
     * */
    public String getRealName() {
        return table.getString(getName()+".realname");
    }
    /**
     * 修改玩家的真实姓名
     * */
    public void setRealName(String realName) {
        table.set(getName()+".realname",realName);
    }

    public String getSex() {
        return table.getString(getName()+".sex");
    }

    public void setSex(String sex) {
        table.set(getName()+".sex",sex);
    }

    public String getQq() {
        return table.getString(getName()+".qq");
    }

    public void setBCOnline(boolean online) {
        table.set(getName()+".online",online);
    }

    public void setQq(String qq) {
        table.set(getName()+".qq",qq);
    }

    public String getEmail() {
        return table.getString(getName()+".email");
    }

    public void setEmail(String email) {
        table.set(getName()+".email",email);
    }

    public String getBirthday() {
        return table.getString(getName()+".birthday");
    }

    public void setServerName(String name) {
        table.set(getName()+".servername",name);
    }

    public void setBirthday(String birthday) {
        table.set(getName()+".birthday",birthday);
    }

    public String getAddress() {
        return table.getString(getName()+".address");
    }

    public void setAddress(String address) {
        table.set(getName()+".address",address);
    }

    public String getSignature() {
        return table.getString(getName()+".signature");
    }

    public void setSignature(String signature) {
        table.set(getName()+".signature",signature);
    }

    public String getHeadBorderUrl() {
        return table.getString(getName()+".headborderurl");
    }

    public void setHeadBorderUrl(String headBorderUrl) {
        table.set(getName()+".headborderurl",headBorderUrl);
    }

    public String getHeadUrl() {
        return table.getString(getName()+".headurl");
    }

    public void setHeadUrl(String headUrl) {
        table.set(getName()+".headurl",headUrl);
    }

    public MailEditor getMailEditor(){
        return new MailEditor(player);
    }

    public List<String> getFriendList(){
        return table.getStringList(getName()+".friendlist");
    }

    public List<Mail> getMailList(){
        List<String> list = table.getStringList(getName()+".maillist");
        List<Mail> mailList = new ArrayList<>();
        for(String uuid : list){
            mailList.add(new Mail(UUID.fromString(uuid)));
        }
        return mailList;
    }
    /**
     * 获取好友请求列表
     * */
    public List<String> getRequestList(){
        return table.getStringList(getName()+".requestlist");
    }
    public List<String> getSystemMessageList(){
        return table.getStringList(getName()+".systemmessagelist");
    }
    /**
     * 向一名玩家发送好友请求
     * */
    public boolean friendRequest(Individual another){
        if(getName().equals(another.getName())){
            return false;
        }
        List<String> friendList = getFriendList();
        if(friendList.contains(another.getName())){
            return false;
        }else{
            List<String> anotherRequestList = another.getRequestList();
            anotherRequestList.remove(getName());
            anotherRequestList.add(getName());
            table.set(another.getName()+".requestlist",anotherRequestList);
            return true;
        }
    }
    /**
     * 接受一名玩家的好友请求
     * */
    public boolean acceptFriendRequest(Individual another){
        List<String> friendList = getFriendList();
        List<String> requestList = getRequestList();
        List<String> anotherFriendList = another.getFriendList();
        if(requestList.contains(another.getName())){
            friendList.add(another.getName());
            anotherFriendList.add(getName());
            requestList.removeAll(Arrays.asList(another.getName()));
            table.set(getName()+".requestlist",requestList);
            table.set(getName()+".friendlist",friendList);
            table.set(another.getName()+".friendlist",anotherFriendList);
            return true;
        }
        return false;
    }
    /**
     * 拒绝一名玩家的好友请求
     * */
    public boolean rejectFriendRequest(Individual another){
        List<String> requestList = getRequestList();
        if(requestList.contains(another.getName())){
            requestList.removeAll(Arrays.asList(another.getName()));
            table.set(getName()+".requestlist",requestList);
            return true;
        }
        return false;
    }
    /**
     * 删除一名好友
     * */
    public boolean deleteFriend(Individual another){
        List<String> friendList = getFriendList();
        List<String> anotherList = another.getFriendList();
        if(friendList.remove(another.getName())){
            anotherList.remove(getName());
            table.set(another.getName()+".friendlist",friendList);
            table.set(getName()+".friendlist",friendList);
            return true;
        }
        return false;
    }
    /**
     * 发送邮件
     * */
    public void sendMail(Mail mail){
        List<String> list = table.getStringList(getName()+".maillist");
        list.add(0,mail.getUniqueId().toString());
        table.set(getName()+".maillist",list);
    }
    public void deleteMail(String uuid){
        List<String> list = table.getStringList(getName()+".maillist");
        list.remove(uuid);
        table.set(getName()+".maillist",list);
        new Mail(UUID.fromString(uuid)).delete();
    }
    public void deleteSystemMessage(int index){
        List<String> list = getSystemMessageList();
        list.remove(index);
        table.set(getName()+".systemmessagelist",list);
    }

    @Override
    public int compareTo(Individual o) {
        OfflinePlayer me = player;
        OfflinePlayer another = o.player;
        if(me.isOp()){
            if(another.isOp()){
                return 0;
            }
            return 1;
        }else{
            if(another.isOp()){
                return -1;
            }
        }
        return 0;
    }

    public List<Individual> getIndividualFriendList(){
        List<String> list = getFriendList();
        List<Individual> individualList = new ArrayList<>();
        for(String name : list){
            individualList.add(new Individual(name));
        }
        Collections.sort(individualList);
        return individualList;
    }

    public boolean isBCOnline(){
        return table.getBoolean(getName()+".online");
    }

    public String getServerName(){
        return table.getString(getName()+".servername");
    }

    public Player asPlayer(){
        if(player.isOnline()){
            return player.getPlayer();
        }
        throw new IllegalArgumentException();
    }

    @Override
    public int hashCode() {
        return getUniqueId().hashCode();
    }

    /**
     * 对一段字符串进行解析
     * */
    public String replace(String str){
        MailEditor editor = getMailEditor();
        return str.replaceAll("%bf_online%",isBCOnline() ? Config.ONLINE_FORMAT: Config.OFFLINE_FORMAT)
                .replaceAll("%bf_name%",getName())
                .replaceAll("%bf_realname%",getRealName())
                .replaceAll("%bf_uuid%",getUniqueId().toString())
                .replaceAll("%bf_sex%",getSex())
                .replaceAll("%bf_qq%",getQq())
                .replaceAll("%bf_email%", getEmail())
                .replaceAll("%bf_birthday%",getBirthday())
                .replaceAll("%bf_address%",getAddress())
                .replaceAll("%bf_head_url%",getHeadUrl())
                .replaceAll("%bf_headborder_url%",getHeadBorderUrl())
                .replaceAll("%bf_signature%",getSignature())
                .replaceAll("%bf_maileditor_to%",editor.getTo())
                .replaceAll("%bf_maileditor_content%",editor.getContent())
                .replaceAll("%bf_maileditor_title%",editor.getTitle())
                .replaceAll("%bf_maileditor_items%",String.valueOf(editor.getItems().size()))
                .replaceAll("%bf_friendlist%",String.valueOf(getFriendList().size()))
                .replaceAll("%bf_messagelist%",String.valueOf(getSystemMessageList().size()))
                .replaceAll("%bf_server_name%",getServerName());
    }
}

