package me.bluesad.bluefreinds.util.yaml;

import me.bluesad.bluefreinds.util.Util;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;
import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;

/**
 * @author bluesad
 * */
public class YamlSaver {
    public YamlSaver(File target){
        this.target = target;
        this.f = YamlConfiguration.loadConfiguration(target);
    }

    private File target;
    private FileConfiguration f;

    /**
     * 获取一个类型的泛型类
     * */
    private Type[] getActualTypeArguments(Type type){
        if(type instanceof ParameterizedType){
            ParameterizedType pt = (ParameterizedType) type;
            return pt.getActualTypeArguments();
        }
        return new Type[0];
    }
    /**
     * @return 这种类型的对象是否可以直接被Bukkit存入和读取
     * */
    private boolean isBukkitSerializable(Class<?> clz){
        if(clz.isAssignableFrom(Collection.class)){
            return isBukkitSerializable((Class<?>)getActualTypeArguments(clz.getGenericSuperclass())[0]);
        }
        return clz.isPrimitive() || clz.equals(Integer.class) || clz.equals(Double.class) || clz.equals(Float.class)
                || clz.equals(Long.class) || clz.equals(Short.class) || clz.equals(Boolean.class) || clz.equals(String.class)
                || clz.equals(String.class) || clz.equals(ItemStack.class) || clz.equals(Location.class);
    }
    /**
     * @return 这个字段是否为临时字段
     * */
    private boolean isTemporary(Field field) {
        return !(field.getAnnotation(TemporaryField.class) == null);
    }
    /**
     * @return 一个对象
     * */
    private <T> T getInstance(Class<T> clz){
        try{
            Method method = clz.getDeclaredMethod("newInstance");
            method.setAccessible(true);
            return (T)method.invoke(null);
        }catch (NoSuchMethodException e){
            System.err.println("Error:Static Method 'newInstance' Required");
            e.printStackTrace();
        }catch (Exception e){
            System.err.println("在获取对象实例时出现了未知的错误");
            e.printStackTrace();
        }
        return null;
    }
    /**
     * 将一个Collection对象写进yml文件中
     * */
    private <T> void writeCollection(Collection<T> collection,String path){
        List<T> list = new ArrayList<>(collection);
        list = Util.removeNull(list);
        int size = list.size();
        if(size == 0){
            f.set(path, Collections.emptyList());
        }
        for(int i = 0;i<list.size();i++){
            T obj = list.get(i);
            saveObject(obj,path+"."+i);
        }
    }
    /**
     * 将一个Map对象写进yml文件中
     * */
    private <K,V> void writeMap(Map<K,V> map,String path){
        writeCollection(map.keySet(),path+".keys");
        writeCollection(map.values(),path+".values");
    }
    /**
     * 从yml文件中读取一个List实例
     * */
    private <T> List<T> readList(Class<T> clz,String path){
        try{
            List<T> list = new ArrayList<>();
            int i = 0;
            while(f.getKeys(true).contains(path+"."+i)){
                list.add(loadObject(clz,path+"."+i));
                i++;
            }
            return list;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
    /**
     * 从yml文件中读取一个Set实例
     * */
    private <T> Set<T> readSet(Class clz,String path){
        return new HashSet<>(readList(clz,path));
    }
    private Class<?> toClass(Type type){
        try{
            return Class.forName(type.getTypeName());
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
    /**
     * 从yml文件中读取一个Map实例
     * */
    public <T1,T2> Map<T1,T2> readMap(Class clz1,Class clz2,String path){
        List<T1> keys = readList(clz1, path+".keys");
        List<T2> values = readList(clz2, path+".values");
        int size = f.getInt(path+".keys.size");
        Map<T1,T2> map = new HashMap<>();
        for(int i = 0;i<size;i++) {
            map.put(keys.get(i), values.get(i));
        }
        return map;
    }
    /**
     * 保存一个对象到yml文件中
     * @param obj 要保存的对象
     * @param path 保存到文件中的路径
     * */
    public <T> void saveObject(T obj,String path) {
        try{
            //判断该类型是否可以直接存到yml中
            if(isBukkitSerializable(obj.getClass())){
                f.set(path,obj);
            }else{
                Class<?> objClass = obj.getClass();
                Class<?> nowClass = objClass;
                List<Field> fieldList = new ArrayList<>(Arrays.asList(objClass.getDeclaredFields()));
                //获取这个类和所有父类的所有成员变量
                while (!nowClass.getSuperclass().equals(Object.class)){
                    Class<?> superClass = nowClass.getSuperclass();
                    nowClass = superClass;
                    fieldList.addAll(Arrays.asList(superClass.getDeclaredFields()));
                }
                f.set(path+".class",objClass.getName());
                for(Field field : fieldList){
                    if(!isTemporary(field)){
                        field.setAccessible(true);
                        Object fieldObject = field.get(obj);
                        String newPath = path+"."+field.getName();
                        if(fieldObject != null){
                            //判断该成员变量是否为集合
                            if(fieldObject instanceof Collection){
                                writeCollection((Collection<?>)fieldObject,newPath);
                            }else if(fieldObject instanceof Map){
                                writeMap((Map<?,?>)fieldObject,newPath);
                            }else if(fieldObject instanceof UUID){
                                f.set(newPath,fieldObject.toString());
                            }else if(fieldObject instanceof OfflinePlayer){
                                f.set(newPath,((OfflinePlayer) fieldObject).getName());
                            }else{
                                saveObject(fieldObject,newPath);
                            }
                        }
                    }
                }
                f.save(target);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    /**
     * 从一个yml文件中读取对象
     * @param clz1 要保存的对象的Class
     * @param path 保存到文件中的路径
     * */
    public <T> T loadObject(Class<? extends T> clz1,String path){
        try{
            Class<?> clz = clz1;
            if(f.getKeys(true).contains(path+".class")){
                clz = Class.forName(f.getString(path+".class"));
            }
            //判断该类型是否可以直接从yml中读取
            if(isBukkitSerializable(clz)){
                return (T)f.get(path);
            }else{
                T result = (T)getInstance(clz);
                Class<?> nowClass = clz;
                List<Field> fieldList = new ArrayList<>(Arrays.asList(clz.getDeclaredFields()));
                //获取这个类和所有父类的所有成员变量
                while (!nowClass.getSuperclass().equals(Object.class)){
                    Class<?> superClass = nowClass.getSuperclass();
                    nowClass = superClass;
                    fieldList.addAll(Arrays.asList(superClass.getDeclaredFields()));
                }
                for(Field field : fieldList){
                    field.setAccessible(true);
                    Type[] args = getActualTypeArguments(field.getGenericType());
                    if(!isTemporary(field)){
                        Class<?> fieldClass = field.getType();
                        String newPath = path+"."+field.getName();
                        if(f.getKeys(true).contains(newPath)){
                            if(isBukkitSerializable(fieldClass)){
                                field.set(result,f.get(newPath));
                            }else if(fieldClass.isAssignableFrom(List.class)){
                                field.set(result,readList(toClass(args[0]),newPath));
                            }else if(fieldClass.isAssignableFrom(Set.class)){
                                field.set(result,readSet(toClass(args[0]),newPath));
                            }else if(fieldClass.isAssignableFrom(Map.class)){
                                field.set(result,readMap(toClass(args[0]),toClass(args[1]),newPath));
                            }else if(fieldClass.equals(UUID.class)){
                                field.set(result,UUID.fromString(f.getString(newPath)));
                            } else if (fieldClass.isAssignableFrom(OfflinePlayer.class)) {
                                field.set(result,Bukkit.getOfflinePlayer(f.getString(newPath)));
                            } else {
                                field.set(result,loadObject(fieldClass, newPath));
                            }
                        }
                    }
                }
                return result;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

}
