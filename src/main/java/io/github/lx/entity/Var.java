package io.github.lx.entity;

import io.github.lx.util.LX;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 游林夕 on 2019/8/23.
 */
public class Var extends HashMap {
    public Var(){}
    public Var(Map map){this.putAll(map);}
    public Var(Object k,Object v){this.put(k,v); }
    /** 获取字符串类型*/
    public String getStr(Object key){return LX.isEmpty(get(key))?"":get(key).toString();}
    public List<Var> getList(Object key){return LX.toList(get(key));}
    public <T>List<T> getList(Class<T> t,Object key){
        return LX.toList(t, key);
    }
    public Var getVar(Object key){return LX.toMap(get(key));}
    public int toInt(Object key){return (int)toDouble(key);}
    public double toDouble(Object key){return Double.parseDouble(getStr(key));}

    public boolean eq1(Object key){
        return "1".equals(getStr(key));
    }
    public boolean eq0(Object key){
        return "0".equals(getStr(key));
    }
    /** 获取值(可能强转不了)*/
    public <T> T getObj(Object key){return (T) get(key);}
    /** 获取指定类型值*/
    public <T> T getObj(Class<T> t,Object key){return LX.toObj(t,get(key));}

    @Override
    public Var put(Object key, Object value) {
        super.put(key, value);
        return this;
    }

    public String toJsonString() {
        return LX.toJSONString(this);
    }
}
