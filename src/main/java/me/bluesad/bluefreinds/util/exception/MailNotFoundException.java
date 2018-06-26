package me.bluesad.bluefreinds.util.exception;

/**
 * @author bluesad
 * */
public class MailNotFoundException extends IllegalCommandException{
    public MailNotFoundException(String msg){
        super(msg);
    }
}
