package org.cauli.db;


import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.cauli.db.annotation.Mapper;
import org.springframework.jdbc.core.RowMapper;


import java.util.List;

/**
 * Created by tianqing.wang on 14-3-21
 */
public abstract class EntityService<T> {
    private RowMapper<T> rowMapper;
    private DBCore dbCore;
    public EntityService(){
        if(!this.getClass().isAnnotationPresent(Mapper.class)){
                    throw new RuntimeException("数据库的Service层必须设置映射类...，通过注解@RowMapper类实现");
        }else{
            Mapper mapper=this.getClass().getAnnotation(Mapper.class);
            try {
                this.rowMapper= (RowMapper<T>) mapper.value().newInstance();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    public abstract List<T> findAll();

    public List<T> query(String sql){
        return this.dbCore.query(sql,rowMapper);
    }

    public List<Map<String,Object>> queryForList(String sql){
        return this.dbCore.queryForList(sql);
    }

    public List<Map<String,Object>> queryForList(String sql,Object[] objects){
        return this.dbCore.queryForList(sql,objects);
    }

    public List<Map<String,Object>> queryForList(String sql,Object[] objects,int[] types){
        return this.dbCore.queryForList(sql,objects,types);
    }

    public int update(String sql){
        if(!StringUtils.startsWith(sql.toLowerCase(),"update")){
            throw new RuntimeException("错误的数据库操作，该操作应该为更新操作...update...");
        }else{
            return this.dbCore.update(sql);
        }
    }

    public List<T> query(String sql,Object[] objects){
        return this.dbCore.query(sql,objects,this.rowMapper);
    }

    public List<T> query(String sql,Object[] objects,int[] types){
        return this.dbCore.query(sql,objects,types,this.rowMapper);
    }

    public int update(String sql,Object object){
        if(!StringUtils.startsWith(sql.toLowerCase(),"update")){
            throw new RuntimeException("错误的数据库操作，该操作应该为更新操作...update...");
        }else{
            return this.dbCore.update(sql,object);
        }
    }

    public int update(String sql,Object object,int[] typs){
        if(!StringUtils.startsWith(sql.toLowerCase(),"update")){
            throw new RuntimeException("错误的数据库操作，该操作应该为更新操作...update...");
        }else{
            return this.dbCore.update(sql,object,typs);
        }
    }

    public int delete(String sql){
        if(!StringUtils.startsWith(sql.toLowerCase(),"delete")){
            throw new RuntimeException("错误的数据库操作，该操作应该为删除操作...delete...");
        }else{
            return this.dbCore.update(sql);
        }
    }

    public int delete(String sql,Object[] objects){
        if(!StringUtils.startsWith(sql.toLowerCase(),"delete")){
            throw new RuntimeException("错误的数据库操作，该操作应该为删除操作...delete...");
        }else{
            return this.dbCore.update(sql,objects);
        }
    }

    public int delete(String sql,Object[] objects,int[] types){
        if(!StringUtils.startsWith(sql.toLowerCase(),"delete")){
            throw new RuntimeException("错误的数据库操作，该操作应该为删除操作...delete...");
        }else{
            return this.dbCore.update(sql,objects,types);
        }
    }

    public int insert(String sql){
        if(!StringUtils.startsWith(sql.toLowerCase(),"insert")){
            throw new RuntimeException("错误的数据库操作，该操作应该为插入操作...insert...");
        }else{
            return this.dbCore.update(sql);
        }
    }
    public int insert(String sql,Object[] objects){
        if(!StringUtils.startsWith(sql.toLowerCase(),"insert")){
            throw new RuntimeException("错误的数据库操作，该操作应该为插入操作...insert...");
        }else{
            return this.dbCore.update(sql,objects);
        }
    }
    public int insert(String sql,Object[] objects,int[] types){
        if(!StringUtils.startsWith(sql.toLowerCase(),"insert")){
            throw new RuntimeException("错误的数据库操作，该操作应该为插入操作...insert...");
        }else{
            return this.dbCore.update(sql,objects,types);
        }
    }



}
