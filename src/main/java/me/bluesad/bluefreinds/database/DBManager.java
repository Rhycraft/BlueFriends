package me.bluesad.bluefreinds.database;

import me.bluesad.bluefreinds.Blue;
import me.bluesad.bluefreinds.Main;
import me.bluesad.bluefreinds.manager.Config;
import me.bluesad.bluefreinds.util.DBUtil;
import java.sql.*;

/**
 * @author bluesad
 * */
public class DBManager {

    private static final String ERROR_MESSAGE = "§c在执行数据库操作时出现了异常.";
    private Table individualTable;
    private Table mailTable;
    private Connection connection;

    public DBManager(){
        try {
            String type = Config.DATABASE_TYPE;
            boolean mysql = type.equalsIgnoreCase("MYSQL");
            String url = "";
            String user = Config.DATABASE_USER;
            String password = Config.DATABASE_PASSWORD;
            String schema = Config.DATABASE_SCHEMA;;
            if(mysql){
                Class.forName("com.mysql.jdbc.Driver");
                url = "jdbc:mysql://"+Config.DATABASE_URL;
            }else {
                Class.forName("org.sqlite.JDBC");
                url = "jdbc:sqlite://"+Config.DATABASE_URL;
            }
            connection = DriverManager.getConnection(url, user, password);
            individualTable = new Table(connection,Config.DATABASE_INDIVIDUAL_TABLE,"name",mysql);
            mailTable = new Table(connection,Config.DATABASE_MAIL_TABLE,"uuid",mysql);
            if(mysql){
                DBUtil.createSchema(connection,schema);
                connection.setCatalog(schema);
            }
            DBUtil.createTable(connection,individualTable.getTableName(),"name","realname","sex","qq","email","birthday","address","signature","headborderurl","headurl","friendlist","maillist","requestlist","systemmessagelist","editor_to","editor_title","editor_content","editor_itemts","editor_systemmail","editor_items");
            DBUtil.createTable(connection,mailTable.getTableName(),"title","from_","to_","content","date","uuid","items","read");
        } catch (SQLException e){
            System.err.println(ERROR_MESSAGE);
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void close(){
        try{
            connection.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public Table getIndividualTable() {
        return individualTable;
    }

    public Table getMailTable() {
        return mailTable;
    }

    public DBManager reload(){
        close();
        return new DBManager();
    }

}
