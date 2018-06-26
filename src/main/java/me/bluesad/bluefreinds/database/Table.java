package me.bluesad.bluefreinds.database;

import me.bluesad.bluefreinds.util.Util;
import org.bluesad.tablet.util.item.ItemSerializerUtil;
import org.bluesad.tablet.util.item.ItemStackUtil;
import org.bukkit.inventory.ItemStack;
import java.sql.*;
import java.util.*;

/**
 * @author bluesad
 * */
public class Table{

    private Connection connection;
    private String table;
    private String mainKey;


    /**
     * @param connection 数据库连接对象
     * @param table 表名
     * @param mainKey 主键(并非数据库里的主键)
     *                该主键将会用于查询
     * */
    public Table(Connection connection,String table,String mainKey,boolean mysql){
        this.connection = connection;
        this.table = table;
        this.mainKey = mainKey;
    }
    /**
     * @return 表名
     * */
    public String getTableName() {
        return table;
    }

    /**
     * @return 是否存在这个id
     * */
    public boolean existsKey(String key){
        try {
            return connection.prepareStatement("SELECT * FROM "+this.table+" WHERE "+mainKey+"='"+key+"'").executeQuery().next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    /**
     * 获取表中所有字段名称
     * @return 所有列的名称
     */
    public List<String> getColumnNames() {
        List<String> columnNames = new ArrayList<>();
        String tableSql = "SELECT * FROM " + table;
        try (PreparedStatement pStemt = connection.prepareStatement(tableSql);){
            ResultSetMetaData rsmd = pStemt.getMetaData();
            int size = rsmd.getColumnCount();
            for (int i = 0; i < size; i++) {
                columnNames.add(rsmd.getColumnName(i + 1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return columnNames;
    }
    /**
     * 以字符串的形式返回一列中的所有值
     * */
    public List<String> getColumn(String column){
        List<String> list = new ArrayList<>();
        String tableSql = "SELECT "+column+" FROM" +table;
        try (PreparedStatement pStemt = connection.prepareStatement(tableSql);){
            ResultSet resultSet = pStemt.executeQuery();
            while(resultSet.next()){
                String s = resultSet.getString(column);
                list.add(s);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    /**
     * 获取表中的每条记录
     * */
    public List<Map<String, Object>> getValues() {
        List<Map<String,Object>> list = new ArrayList<>();
        try(PreparedStatement statement = connection.prepareStatement("SELECT * FROM "+table)){
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()){
                Map<String,Object> map = new HashMap<>();
                for(String column : getColumnNames()){
                    map.put(column,get(resultSet.getString(mainKey)+"."+column));
                }
                list.add(map);
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return list;
    }

    public Object get(String s) {
        return get(s,Object.class);
    }

    synchronized public <T> T get(String s,Class<T> type) {
        String args[] = s.split("[.]");
        try(PreparedStatement statement = connection.prepareStatement("SELECT "+args[1]+" FROM "+table+" WHERE "+mainKey+"='"+args[0]+"'");){
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            return MyGSON.fromJson(resultSet.getString(args[1]),type);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    synchronized public void set(String s, Object o) {
        String args[] = s.split("[.]");
        String value = "";
        try{
            if(o instanceof List){
                List<?> list = (List)(o);
                if(!list.isEmpty() && list.get(0) instanceof ItemStack){
                    value = ItemSerializerUtil.toString(ItemStackUtil.toItemStackArray((List<ItemStack>) o));
                }else{
                    value = MyGSON.toJson(o);
                }
            }else{
                value = MyGSON.toJson(o);
            }
            if(!existsKey(args[0])){
                PreparedStatement statement = connection.prepareStatement("INSERT INTO "+table+"( "+mainKey+","+args[1]+" ) VALUES ( '"+args[0]+"' ,'"+value+"' )");
                statement.execute();
                statement.close();
            }else{
                PreparedStatement statement = connection.prepareStatement("UPDATE "+table+" SET "+args[1]+"='"+value+"' WHERE "+mainKey+"='"+args[0]+"'");
                statement.execute();
                statement.close();
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void delete(String s){
        try(PreparedStatement statement = connection.prepareStatement("DELETE FROM "+table+" WHERE "+mainKey+"='"+s+"'")){
            statement.execute();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public String getString(String s) {
        return get(s,String.class);
    }

    public int getInt(String s) {
        return get(s,Integer.class);
    }

    public boolean getBoolean(String s) {
        return get(s,Boolean.class);
    }

    public double getDouble(String s) {
        return get(s,Double.class);
    }

    public long getLong(String s) {
        return get(s,Long.class);
    }

    public List<String> getStringList(String s) {
        return Util.asList(get(s,String[].class));
    }

    public List<Integer> getIntegerList(String s) {
        return Util.asList(get(s,Integer[].class));
    }

    public List<Boolean> getBooleanList(String s) {
        return Util.asList(get(s,Boolean[].class));
    }

    public List<Double> getDoubleList(String s) {
        return Util.asList(get(s,Double[].class));
    }

    public List<Float> getFloatList(String s) {
        return Util.asList(get(s,Float[].class));
    }

    public List<Long> getLongList(String s) {
        return Util.asList(get(s,Long[].class));
    }

    public List<Byte> getByteList(String s) {
        return Util.asList(get(s,Byte[].class));
    }

    public List<ItemStack> getItemStackList(String s){
        String args[] = s.split("[.]");
        List<ItemStack> items = new ArrayList<>();
        try(PreparedStatement statement = connection.prepareStatement("SELECT "+args[1]+" FROM "+table+" WHERE "+mainKey+"='"+args[0]+"'");){
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            String str = resultSet.getString(args[1]);
            items = ItemSerializerUtil.fromString(str);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return items;
    }

}
