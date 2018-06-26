package me.bluesad.bluefreinds.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBUtil {

    /**
     * 创建数据库
     * @param schemaName 数据库名
     * */
    synchronized public static boolean createSchema(Connection connection, String schemaName){
        if(!existsSchema(connection,schemaName)){
            try{
                PreparedStatement statement = connection.prepareStatement("CREATE DATABASE "+schemaName);
                boolean s = statement.execute();
                statement.close();
                return !connection.isClosed() && !existsSchema(connection,schemaName) && s;
            }catch (SQLException e){
                return false;
            }
        }
        return false;
    }

    /**
     * 创建表
     * @param tableName 表名
     * @param values 表中的列
     * */
    synchronized public static boolean createTable(Connection connection,String tableName,String... values){
        try{
            if(connection.isClosed() || existsTable(connection,tableName)){
                return false;
            }else{
                StringBuilder sqlBuilder = new StringBuilder();
                sqlBuilder.append("CREATE TABLE ")
                        .append(tableName)
                        .append("(")
                        .append("id INT UNSIGNED AUTO_INCREMENT,");
                for(String value: values){
                    sqlBuilder.append("`");
                    sqlBuilder.append(value);
                    sqlBuilder.append("`");
                    sqlBuilder.append(" TEXT(10000)");
                    if(!value.equals(values[values.length-1])){
                        sqlBuilder.append(",");
                    }else{
                        sqlBuilder.append(", PRIMARY KEY ( ");
                        sqlBuilder.append("id");
                        sqlBuilder.append(" ))");
                    }
                }
                PreparedStatement statement = connection.prepareStatement(sqlBuilder.toString());
                boolean s = statement.execute();
                statement.close();
                return s;
            }
        }catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }

    /**
     * @return 是否存在这个数据库
     * */
    private static boolean existsSchema(Connection connection,String schema){
        try{
            int i = 0;
            PreparedStatement sql = connection.prepareStatement("SHOW SCHEMAS LIKE '" + schema + "'");
            ResultSet result = sql.executeQuery();
            sql.close();
            while (result.next()) {
                i += 1;
            }
            return i > 0;
        }catch (SQLException e){
            return false;
        }
    }

    /**
     * @return 是否存在这个表
     * */
    private static boolean existsTable(Connection connection,String table){
        try(PreparedStatement statement = connection.prepareStatement("SELECT * FROM "+table)){
            return statement.execute();
        }catch (SQLException e){
            return false;
        }
    }
}
