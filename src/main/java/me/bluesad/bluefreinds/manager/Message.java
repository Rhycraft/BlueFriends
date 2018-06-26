package me.bluesad.bluefreinds.manager;

import me.bluesad.bluefreinds.Blue;
import org.bukkit.configuration.Configuration;

public class Message {
    private static Configuration message = Blue.getMessage();
    public static String ON_JOIN = message.getString("on_join");
    public static String DELETE_MAIL = message.getString("delete_mail");
    public static String SET_READ_MAIL = message.getString("setread_mail");
    public static String RECEIVE_ITEMS_MAIL = message.getString("receiveitems_mail");
    public static String FAIL_RECEIVE_ITEMS_MAIL = message.getString("fail_receiveitems_mail");
    public static String SEND_MAIL = message.getString("send_mail");
    public static String RECEIVE_MAIL = message.getString("receive_mail");
    public static String RECEIVE_SYSTEM_MAIL = message.getString("receive_system_mail");
    public static String FRIEND_REQUESTED = message.getString("friend_requested");
    public static String FRIEND_REQUEST = message.getString("friend_request");
    public static String ACCEPT_FRIEND_TO = message.getString("accept_friend_to");
    public static String ACCEPT_FRIEND_FROM = message.getString("accept_friend_from");
    public static String REJECT_FRIEND_TO = message.getString("reject_friend_to");
    public static String REJECT_FRIEND_FROM = message.getString("reject_friend_from");
    public static String REPEAT_FRIEND_REQUEST = message.getString("repeat_friend_request");
    public static String DELETE_FRIEND = message.getString("delete_friend");
    public static String SET_REAL_NAME = message.getString("set_realname");
    public static String SET_BIRTHDAY = message.getString("set_birthday");
    public static String SET_ADDRESS = message.getString("set_address");
    public static String SET_QQ = message.getString("set_qq");
    public static String SET_EMAIL = message.getString("set_email");
    public static String SET_SIGNATURE = message.getString("set_signature");
    public static String SET_SEX = message.getString("set_sex");
    public static String SET_URL = message.getString("set_url");
    public static String SET_EDITOR_TO = message.getString("set_editor_to");
    public static String SET_EDITOR_TITLE = message.getString("set_editor_title");
    public static String SET_EDITOR_CONTENT = message.getString("set_editor_content");
    public static String SET_EDITOR_ITEMS = message.getString("set_editor_items");
    public static String SET_HEADBORDER = message.getString("set_headborder");
    public static String JOIN_MSG = message.getString("join_msg");
    public static String UNEXISTS_GUI = message.getString("unexists_gui");
    public static String ILLEGAL_IMAGE_URL = message.getString("illegal_image_url","§6[BlueFriends]§c你输入了一个非法的图片地址§c(%url%)§c,请检查你的输入!");

    public static void reload(){
        message = Blue.getMessage();
        ON_JOIN = message.getString("on_join");
        DELETE_MAIL = message.getString("delete_mail");
        SET_READ_MAIL = message.getString("setread_mail");
        RECEIVE_ITEMS_MAIL = message.getString("receiveitems_mail");
        FAIL_RECEIVE_ITEMS_MAIL = message.getString("fail_receiveitems_mail");
        SEND_MAIL = message.getString("send_mail");
        RECEIVE_MAIL = message.getString("receive_mail");
        RECEIVE_SYSTEM_MAIL = message.getString("receive_system_mail");
        FRIEND_REQUESTED = message.getString("friend_requested");
        FRIEND_REQUEST = message.getString("friend_request");
        ACCEPT_FRIEND_TO = message.getString("accept_friend_to");
        ACCEPT_FRIEND_FROM = message.getString("accept_friend_from");
        REJECT_FRIEND_TO = message.getString("reject_friend_to");
        REJECT_FRIEND_FROM = message.getString("reject_friend_from");
        REPEAT_FRIEND_REQUEST = message.getString("repeat_friend_request");
        DELETE_FRIEND = message.getString("delete_friend");
        SET_REAL_NAME = message.getString("set_realname");
        SET_BIRTHDAY = message.getString("set_birthday");
        SET_ADDRESS = message.getString("set_address");
        SET_QQ = message.getString("set_qq");
        SET_EMAIL = message.getString("set_email");
        SET_SIGNATURE = message.getString("set_signature");
        SET_SEX = message.getString("set_sex");
        SET_URL = message.getString("set_url");
        SET_EDITOR_TO = message.getString("set_editor_to");
        SET_EDITOR_TITLE = message.getString("set_editor_title");
        SET_EDITOR_CONTENT = message.getString("set_editor_content");
        SET_EDITOR_ITEMS = message.getString("set_editor_items");
        SET_HEADBORDER = message.getString("set_headborder");
        JOIN_MSG = message.getString("join_msg");
        UNEXISTS_GUI = message.getString("unexists_gui");
        ILLEGAL_IMAGE_URL = message.getString("illegal_image_url","§6[BlueFriends]§c你输入了一个非法的图片地址§c(%url%)§c,请检查你的输入!");
    }
}
