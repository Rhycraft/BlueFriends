package me.bluesad.bluefreinds.util.exception;

/**
 * @author bluesad
 */
public class RequestNotFoundException extends IllegalCommandException {
    public RequestNotFoundException(String msg){
        super(msg);
    }
}
