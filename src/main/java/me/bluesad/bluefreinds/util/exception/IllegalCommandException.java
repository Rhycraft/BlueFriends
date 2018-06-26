package me.bluesad.bluefreinds.util.exception;

/**
 * 在玩家输入一个错误的指令时抛出的异常
 * @author bluesad
 * */
public class IllegalCommandException extends IllegalArgumentException{
    private static final long serialVersionUID = 1L;

    public IllegalCommandException(String reason) {
        super(reason);
    }

}
