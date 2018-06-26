public class Test {
    public static void main(String[] args){
        System.out.println("condition='%sb%>10';GUGU".matches("condition='(%).*(%)(>|=|<)[0-9]*(';).*"));
    }
}
