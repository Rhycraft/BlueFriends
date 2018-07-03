package me.bluesad.bluefreinds.bungeecord;

import me.bluesad.bluefreinds.manager.Lang;
import me.bluesad.bluefreinds.util.Util;

import java.io.UnsupportedEncodingException;

public class BungeeCordPacket {
    private String label;
    private String[] arguments;
    private String secretKey;
    private String target;

    public BungeeCordPacket(){}

    public BungeeCordPacket(String secretKey,String target,String label,String... args){
        this.secretKey = secretKey;
        this.label = label;
        this.target = target;
        this.arguments = args;
    }

    public String getLabel() {
        return label;
    }

    public String[] getArguments() {
        return arguments;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public String getTarget() {
        return target;
    }

    public byte[] toBytes(){
        try {
            return Util.defaultGson().toJson(this).getBytes(BungeeCord.ENCODING);
        }catch (UnsupportedEncodingException e){
            throw new IllegalArgumentException(Lang.ILLEGAL_ENCODING.replaceAll("%encoding%",BungeeCord.ENCODING));
        }
    }

    public static BungeeCordPacket fromBytes(byte[] bytes){
        String json;
        try {
            json = new String(bytes, BungeeCord.ENCODING);
        }catch (UnsupportedEncodingException e){
            throw new IllegalArgumentException(Lang.ILLEGAL_ENCODING.replaceAll("%encoding%",BungeeCord.ENCODING));
        }
        try {
            return Util.defaultGson().fromJson(json, BungeeCordPacket.class);
        }catch (Exception e){
            e.printStackTrace();
            throw new IllegalArgumentException(Lang.ERROR_LOAD_PACKET_FROM_JSON.replaceAll("%json%",json));
        }
    }
}
