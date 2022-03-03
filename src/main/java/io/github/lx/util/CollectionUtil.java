package io.github.lx.util;//说明:


import io.github.lx.entity.Var;
import io.github.lx.gson.Gson;
import io.github.lx.gson.GsonBuilder;
import io.github.lx.gson.JsonElement;
import io.github.lx.gson.JsonParser;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 创建人:游林夕/2019/3/27 15 01
 */
class CollectionUtil {
//    public static void main(String [] args) throws Exception {
////        List<PageData> s = LX.toList(PageData[].class,LX.format("[{a={0},b='{1}'},{a={0},b='{1}'}]","{a='{0}',b='{1}'}","苏打水"));
////        HashMap pd = LX.toMap(HashMap.class,"{a='阿迪达斯',b={0}}","{a={a=0},b='{1}'}");
////        System.out.println(((HashMap)pd.get("b")).get("a"));
//        Var var = LX.toMap("{\"msg\":\"\",\"success\":1,\"count\":7,\"rows\":[{\"service_name\":\"system-server\",\"service_memo\":\"系统服\",\"service_id\":\"0e069943-9121-4861-a066-3c8397e0239a\"},{\"service_name\":\"system-server\",\"service_memo\":\"系统服务\",\"service_id\":\"3e5da9bc-9b8e-4a14-b6c1-89b90930fbbe\"},{\"service_name\":\"system-server\",\"service_memo\":\"系统服\",\"service_id\":\"4489ac23-43ce-4844-9b12-0684dacc2700\"},{\"service_name\":\"system-server\",\"service_memo\":\"系统服\",\"service_id\":\"4c9cf1a5-5d61-4aa4-891f-8ef6ae70186b\"},{\"service_name\":\"system-server\",\"service_memo\":\"系统服\",\"service_id\":\"6ae5ed8c-4a2e-4932-875e-76e9a750c86e\"},{\"service_name\":\"system-server\",\"service_memo\":\"系统服\",\"service_id\":\"85f1f463-23c1-4bb4-a8b1-96f2abcc6441\"},{\"service_name\":\"system-server\",\"service_memo\":\"系统服\",\"service_id\":\"8d755650-6d77-4c09-b919-135475940b49\"}],\"entity\":{}}");
//        System.out.println(var.getList("rows").get(0).getStr("service_memo"));
//    }
    /**
     * 克隆Map
     */
    static <T extends Map>T cloneMap(T obj, String str) {
        if (LX.isEmpty(obj)){
            return null;
        }
        if (LX.isNotEmpty(str)){
            Map map = new HashMap();
            for (String key : str.split(",")){
                map.put(key,obj.get(key));
            }
            return (T) LX.toMap(obj.getClass(),deepClone(map));
        }else {
            return deepClone(obj);
        }
    }
    /**
     * 将对象Clone
     */
    static <T> T  deepClone(T obj){
        try{
            // 将对象写到流里
            ByteArrayOutputStream bo = new ByteArrayOutputStream();
            ObjectOutputStream oo = new ObjectOutputStream(bo);
            oo.writeObject(obj);
            // 从流里读出来
            ByteArrayInputStream bi = new ByteArrayInputStream(bo.toByteArray());
            ObjectInputStream oi = new ObjectInputStream(bi);
            return (T) oi.readObject();
        }catch (Exception e){
            return LX.exMsg(e);
        }
    }
    /**将对象转为对象*/
    static <T> T toObj(Class<T> t,Object obj){
        if (LX.isEmpty(obj)){
            return null;
        }
        if (t.isAssignableFrom(obj.getClass())){
            return (T) obj;
        }
        return g.fromJson(obj instanceof String?(String)obj:g.toJson(obj),t);
    }
    /**
     * 将对象转为Map
     *  @author
     *  创建时间：2018年4月26日 下午5:35:55
     */
    static <T extends Map>T toMap(Class<T> t,Object json) {
        if (json == null){
            return null;
        }
        if (t.isAssignableFrom(json.getClass())){
            return (T) json;
        }
        Map pd = g.fromJson(json instanceof String?(String)json:g.toJson(json),Map.class);
        T pp = null;
        try {
            pp = t.newInstance();
            for (Object key : pd.keySet()) {
                if(LX.isEmpty(key)){
                    continue;
                }
                Object obj = pd.get(key);
                if(LX.isNotEmpty(obj)){
                    if(obj instanceof Map){
                        pp.put(key, toMap(t,obj));
                    }else if(obj instanceof List){
                        pp.put(key, toList(obj));
                    }else {
                        pp.put(key,obj);
                    }
                }else{
                    pp.put(key, obj);
                }
            }
        } catch (Exception e) {
            LX.exMsg(e);
        }
        return pp;
    }
    /**将对象转为List*/
    static  List toList(Object obj){
        if (obj == null){
            return null;
        }
        List list = null;
        if (List.class.isAssignableFrom(obj.getClass())){
            list = (List) obj;
        }else{
            list =  g.fromJson(obj instanceof String?(String)obj:g.toJson(obj),List.class);
        }
        List ls = new ArrayList();
        for (Object o : list){
            if(o!=null){
                if(o instanceof Map){
                    ls.add(toMap(Var.class,o));
                }else if(o instanceof List){
                    ls.add(toList(o));
                }else {
                    ls.add(o);
                }
            }else{
                ls.add(o);
            }
        }
        return ls;
    }

    private static Gson g = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").disableHtmlEscaping().create();

    static String toJSONString(Object obj) {
        if (obj == null){
            return null;
        }
        return obj instanceof String?(String)obj:g.toJson(obj);
    }
    /**将map中的指定字段为null设置为数字*/
    static void nullToNum(Map pd , String str){
        if (LX.isEmpty(pd)|| LX.isEmpty(str)){
            return;
        }
        for (String s : str.split(",")){
            s = s.trim();
            if (LX.isEmpty(pd.get(s))){
                pd.put(s,0);
            }
        }
    }
    //说明:格式化输出json字符串
    /**{ ylx } 2019/8/12 10:04 */
    static String toFormatJson(Object json) {
        JsonElement jsonElement = null;
        if (json instanceof List || json.toString().trim().startsWith("[")){
            jsonElement = new JsonParser().parse(toJSONString(json)).getAsJsonArray();
        }else{
            jsonElement = new JsonParser().parse(toJSONString(json)).getAsJsonObject();
        }
        Gson gson = new GsonBuilder().disableHtmlEscaping().setPrettyPrinting().create();
        return gson.toJson(jsonElement);
    }
    /**将map中的指定字段为null设置为字符串*/
    static void nullToString(Map pd , String str){
        if (LX.isEmpty(pd)|| LX.isEmpty(str)){
            return;
        }
        for (String s : str.split(",")){
            s = s.trim();
            if (LX.isEmpty(pd.get(s))){
                pd.put(s,"");
            }
        }
    }
}


