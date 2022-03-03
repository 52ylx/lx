package io.github.lx.util;//说明:


import io.github.lx.entity.Var;
import io.github.lx.util.exception.ResultServiceException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 创建人:游林夕/2019/3/27 14 53
 */
public final class LX {
    private LX(){}
    /**为空则返回true，不否则返回false*/
    public static boolean isEmpty(Object s){
        if (s instanceof Map){
            return  ((Map)s).size()==0;
        }else if(s instanceof List){
            return  ((List)s).size()==0;
        }else if(s instanceof Set){
            return  ((Set)s).size()==0;
        }else if(s instanceof Object []){
            return ((Object[])s).length==0;
        }
        return s==null || "".equals(s) || "null".equals(s);
    }
    /**对象不为null*/
    public static boolean isNotEmpty(Object s){return !isEmpty(s);}
    public static boolean isNotEmptyMap(Map map,String keys){
        if (isNotEmpty(map) && isNotEmpty(keys)){
            for (String object : keys.split(",")) {
                if(isEmpty(map.get(object.trim()))){
                    return false;
                }
            }
            return true;
        }
        return false;
    }
    public static <T>T exMsg(Throwable e){throw new RuntimeException(e);}
    /**抛异常*/
    public static void exResultMsg(String msg){
        ResultServiceException.exMsg(msg);
    }
    public static <T>T exMsg(String msg){throw new ResultServiceException(msg);}
    public static <T>T exMsg(boolean boo,String msg){
        if (boo){
            throw new ResultServiceException(msg);
        }
        return null;
    }
    /**抛异常*/
    public static <T>T exMsg(String msg,Object...obj){return exMsg(format(msg,obj));}
    /**判断对象为空时抛异常*/
    public static void exObj(Object s){exObj(s,"值不能为空!");}
    /** 判断对象为空时抛异常*/
    public static void exObj(Object s,String exMsg){
        if(isEmpty(s)){
            exMsg(exMsg);
        }
    }
    /**判断map中key对应的value值是否为空*/
    public static void exMap(Map s , String keys){
        exObj(s);
        exObj(keys);
        StringBuilder sb = new StringBuilder();
        for (String object : keys.split(",")) {
            if(isEmpty(s.get(object.trim()))){
                sb.append("没有获取到:"+object);
            }
        }
        if (sb.length()>0){
            exMsg(sb.toString());
        }
    }
    /** 判断实体里的字段是否为空 */
    public static void exEntity(Object obj,String keys){
        exObj(obj,"实体不能为空");
        exMap(LX.toMap(obj),keys);
    }
    public static <T> T emptyGet(T obj, T t){return isEmpty(obj)? t:obj;}
    public static <T> T emptyGet(T obj, Func<T> f){return isEmpty(obj)? f.apply():obj;}
    public static <R> R notEmptyExec(Object t,Exec<R> e){return isEmpty(t)?null:e.exec(t);}
    public static void notEmptyApply(Object t,Apply e){
        if(isNotEmpty(t)){
            e.exec(t);
        }
    }

    public interface Func<T>{T apply();}
    public interface Exec<R>{R exec(Object t);}
    public interface Apply{void exec(Object t);}

    //判断字符串是否为数字
    public static boolean isNum(String str){return str.matches("(-?\\d+)(\\.\\d+)?");}
    /**计算*/
    public static BigDecimal eval(String str){return MathUtil.eval(str);}
    /**获取精确的数字*/
    public static BigDecimal getBigDecimal(Object obj){return new BigDecimal(emptyGet(obj,0).toString());}
    /**获取精确的数字*/
    public static BigDecimal  getBigDecimal(Map map , Object obj){return isEmpty(map) || isEmpty(obj)?new BigDecimal("0"):getBigDecimal(map.get(obj));}
    /**比较两个数字的大小*/
    public static boolean compareTo(Object obj1 , Object obj2, MathUtil.Type c){return MathUtil.compareTo(obj1,obj2,c);}

    /**获取日期和时间(当前时间)*/
    public static String getTime(){return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());}
    /**获取日期(当前时间)*/
    public static String getDay(){return new SimpleDateFormat("yyyy-MM-dd").format(new Date());}
    /**获取指定格式的日期字符串(当前时间)*/
    public static String getDate(String sdf){return new SimpleDateFormat(sdf).format(new Date());}
    /**获取指定跟他说日期字符串(指定时间)*/
    public static String getDate(String sdf,long t){return new SimpleDateFormat(sdf).format(new Date(t));}
    /**获取随机UUID*/
    public static String uuid(){return UUID.randomUUID().toString();}
    public static String uuid(int i){return uuid().substring(0, i<1||i>36 ? 36 : i);}
    /**获取随机UUID*/
    public static String uuid32(){return UUID.randomUUID().toString().replace("-","");}
    public static String uuid32(int i){return uuid32().substring(0, i<1||i>32 ? 32 : i);}



    /** 上次生成ID的时间截 */
    private static long LAST_TIME_STAMP = -1L;
    /** 上一次的毫秒内序列值 */
    private static long LAST_SEQ = 0L;
    private static Random random = new Random();
    //说明: 雪花ID
    /**{ ylx } 2021/10/27 14:39 */
    public synchronized static long primaryid(){
        long now = System.currentTimeMillis();
        LX.exMsg(now < LAST_TIME_STAMP, "系统时间错误! 拒绝生成雪花ID");
        if (now == LAST_TIME_STAMP) {
            LAST_SEQ = (LAST_SEQ + 1) & 4194303;
            if (LAST_SEQ == 0){
                while (now <= LAST_TIME_STAMP) {
                    now = System.currentTimeMillis();
                }
            }
        } else {
            LAST_SEQ = random.nextInt(2000000);
        }
        //上次生成ID的时间截
        LAST_TIME_STAMP = now;
        return (now*1000000L)+(LAST_SEQ % 1000000L);
    }
    /**获取首字母大写*/
    public static String initialLower(String str){return str == null ? null : str.substring(0,1).toLowerCase()+str.substring(1);}
    /**获取首字母大写*/
    public static String initialUp(String str){return str == null ? null : str.substring(0,1).toUpperCase()+str.substring(1);}
    /** 值是否为 1 */
    public static boolean notEqs0(Map pd , Object key){return !eqs0(pd,key);}
    /** 值是否为 1 */
    public static boolean notEqs1(Map pd , Object key){return !eqs1(pd,key);}
    /** 值是否为 1 */
    public static boolean eqs1(Map pd , Object key){return isNotEmpty(pd) && "1".equals(pd.get(key));}
    /** 值是否为 1 */
    public static boolean eqs0(Map pd , Object key){return isNotEmpty(pd) && "0".equals(pd.get(key));}
    /**将对象转为字符串 为null时显示""*/
    public static String str(Object obj){return isEmpty(obj)?"":obj.toString();}
    /**将字符串转为指定格式*/
    public static String format(String str , Object...args){
        int i = 0;
        for (Object s : args){
            str = str.replace("{"+(i++)+"}",s==null?"":s.toString());
        }
        return str;
    }


    /**克隆Map里的指定keys*/
    public static <T extends Map> T  deepClone(T obj,String str){return CollectionUtil.cloneMap(obj,str);}
    /**克隆对象*/
    public static <T> T  deepClone(T obj){return CollectionUtil.deepClone(obj);}

    /**将对象转为Map*/
    public static <T extends Map>T toMap(Class<T> t,String str , Object...args) {return toMap(t,format(str,args));}
    public static <T extends Map>T toMap(Class<T> t,Object obj) {return CollectionUtil.toMap(t,obj);}
    public static Var toMap(Object obj) {return CollectionUtil.toMap(Var.class,obj);}
    public static <T> T toObj(Class<T> t,Object obj){return CollectionUtil.toObj(t,obj);}
    /**将对象转为List*/
    public static List toList(Object obj) { return CollectionUtil.toList(obj);}
    public static <T>List<T> toList(Class<T> t,Object obj){
        List list = CollectionUtil.toList(obj);
        List<T> ls = new ArrayList<>();
        if (LX.isNotEmpty(list)){
            list.forEach(v->{
                if (t.isAssignableFrom(v.getClass())){
                    ls.add((T)v);
                }else{
                    ls.add(LX.toObj(t,v));
                }
            });
        }
        return ls;
    }
    /**将对象转为json串*/
    public static String toJSONString(Object obj) { return CollectionUtil.toJSONString(obj);}
    public static String toFormatJson(Object obj){return CollectionUtil.toFormatJson(obj);}

    /**调用Post请求*/
    public static String doPost(String url,Map<String,Object>parm){return doPost(url,parm,5000);}
    public static String doPost(String url, Map<String,Object> param,int timeout) {return LXHttp.doPost(url,param,timeout);}
    public static String doPost(String url,String str){return LXHttp.doPost(url,str);}
    public static String doPost(String url,String str,Map<String,String> property){return LXHttp.doPost(url,str,property);}
    public static String doGet(String url) {return LXHttp.doGet(url,null);}
    public static String doGet(String url,Map<String,String> property) {return LXHttp.doGet(url,property);}
    public static InputStream doGetStream(String url) {return LXHttp.doGetStream(url,null);}
    public static InputStream doPostInputStream(String url, String str, Map<String, String> property){return LXHttp.doPostInputStream(url,str,property);}



    /**记录日志*/
    private static LogUtil logUtil=new LogUtil();
    public static void before(String msg){
        logUtil.printP("==>start");
        logUtil.printP(msg);
    }
    public static void after(){logUtil.printP("<==end");}
    public static void info(String msg){logUtil.printP(msg);}
    public static void error(Exception e){logUtil.printE(e);}
    /**左对齐*/
    public static String left(String str,int i){return str.length()>=i?str.substring(0,i):(str+new String(new byte[i])).substring(0,i);};
    /**右对齐*/
    public static String right(String str,int i){return str.length()>=i?str.substring(str.length()-i):(new String(new byte[i])+str).substring(str.length());};

    /**左对齐*/
    public static String left(String str,int i,char c){
        if(str.length()>=i){
            return str.substring(0,i);
        }
        StringBuilder sb = new StringBuilder(str);
        for (int j=0;j<i-str.length();j++){
            sb.append(c);
        }
        return sb.toString();

    };
    /**右对齐*/
    public static String right(String str,int i,char c){
        if(str.length()>=i){
            return str.substring(str.length()-i);
        }
        StringBuilder sb = new StringBuilder();
        for (int j=0;j<i-str.length();j++){
            sb.append(c);
        }
        return sb.append(str).toString();
    };


    /**获取resource指定配置文件值*/
    public static String loadProperties(String fileName , String key) {
        return loadProperties(fileName).get(key);
    }
    /**获取resource指定配置文件*/
    public static Map<String,String> loadProperties(String fileName){
        ResourceBundle prb = ResourceBundle.getBundle(fileName);
        Map<String,String> pp = new HashMap<>();
        for (String key : prb.keySet()){
            pp.put(key , prb.getString(key));
        }
        return pp;
    }
    /**获取网络上的properties文件*/
    public static Map<String,String> getNetProperties(String urlPath){
        Properties pro = new Properties();
        try {
            pro.load(new URL(urlPath).openConnection().getInputStream());
        } catch (Exception e) {
            LX.exMsg(e);
        }
        return toMap(pro);
    }
    /*睡眠*/
    public static void sleep(long time){
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            exMsg(e.getMessage());
            Thread.currentThread().interrupt();
        }
    }
    /**编码*/
    public static String encode(String str){
        try {
            LX.exObj(str,"需要编码的字符串不能为空!");
            return io.github.lx.util.Base64.encode(str.getBytes("utf-8"));
        } catch (UnsupportedEncodingException e) {
            LX.exMsg(e);
        }
        return null;
    }
    /** 解码*/
    public static String decode(String str){
        try {
            LX.exObj(str,"需要解码的字符串不能为空!");
            byte[] decode = Base64.decode(str);
            LX.exObj(decode,"该编码不正确!");
            return new String(decode,"utf-8");
        } catch (UnsupportedEncodingException e) {
            LX.exMsg(e);
        }
        return null;
    }
    /** 对字符串编码*/
    public static String encodeV1(String str){
        for (int i=0;i<2;i++){
            str = LX.uuid32(i+1)+encode(str);
        }
        return str;
    }
    /** 对字符串解码*/
    public static String decodeV1(String str){
        for (int i=1;i>=0;i--){
            str = decode(str.substring(i+1));
        }
        return str;
    }
    /** 获取md5串*/
    public static String md5(String string){
        try {
            byte[] hash = MessageDigest.getInstance("MD5").digest(string.getBytes("UTF-8"));
            StringBuilder hex = new StringBuilder(hash.length * 2);
            for (byte b : hash) {
                if ((b & 0xFF) < 0x10){
                    hex.append("0");
                }
                hex.append(Integer.toHexString(b & 0xFF));
            }
            return hex.toString();
        }catch (Exception e){
            return LX.exMsg(e);
        }
    }
    //说明:RSA获取公私钥
    /**{ ylx } 2020/3/22 12:01 */
    public static void genKeyPair() throws NoSuchAlgorithmException {
        RSAEncrypt.genKeyPair();
    }
    //说明:RSA加密
    /**{ ylx } 2020/3/22 12:03 */
    public static String encrypt( String str, String publicKey ) throws Exception{
        return RSAEncrypt.encrypt(str,publicKey);
    }
    static final String PRIKEY = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBALP0eZKoOSPaHHn1YjRdnM82LPAqV/u3Y5N9YKpc2Z7fgLR2pYFhQDz5MQwxUcdAmkAjGJg2ey4WuXVsRR+6xEXveaWzvxXRmqnUVmwSsyZp35Fb9SyQ0vLN3Kcakeb5Y3Epux/5I/AtlTJpPbl/G2pSHANMJp3jVoqbliRcs825AgMBAAECgYAiwn9VL79RyIilmhttCAQJFmrZvSQfkA3g6F/qqGK0yHFA6oYPlqxk2rF5rHEibr0c1GqkxClI/sS2BMLZXScaFDt46Ssly5P42MsL89XlnjF9dDoUnAFbNQyJkoKHw+tG8YhAuaB90sYEC0XaVdMdSFpRzeJhoQHaGfZxbvMeHQJBANeDBRuyA/NvPivhJIS3v+Tn3PqlilG2WsrELttI/HeuFmTXOfWyL4q5RNEVo8SMQJoOTbxvJfdf6ul8UDXcoJ8CQQDVw1wWI4m71ggLofoakuFKPQ48UBT9ICDkTS2LGRA7t3C28+jjNHK7kxbiJ11icIX3PM4MCuCBipygJvxYvTqnAkEAnjpW5MelePTC68qIWlziqfjGWMxzqpy9OkQhtuR2YJRTYsRWjJq/pxVxfiVqMONchv9bea8Na/tjaPvZlEIhNwJAa2leBVXYKMV0h3vds6qof+hMFexbj5GsVWuefe2bJZaYkaXgGQLKwAw6DGuhVgyalFA+bPiuNQmUiDjuxhxXswJAQQeKew+igu7BLT9gdSO6KKCIpSEG7le08CJQU0ajCAmHPubIlgEU13i4EZrewSQe4Sfo9mI6yihMypWxz9XVHw==";
    //说明:RSA解密
    /**{ ylx } 2020/3/22 12:02 */
    public static String decrypt(String str, String privateKey){
        return RSAEncrypt.decrypt(str,privateKey);
    }


    //说明:查询两个日期之间的月份
    /**{ ylx } 2020/10/23 18:45 */
    public static List<String> getMonthBetween(Date minDate, Date maxDate){
        return LXDateUtil.getMonthBetween(minDate, maxDate);
    }

    /**  获取当前IP*/
    public final static String CURRENT_IP = getIp();
    public static String getIp(){
        String localHostAddress = "127.0.0.1";
        try {
            Enumeration<NetworkInterface> allNetInterfaces = NetworkInterface.getNetworkInterfaces();
            while(allNetInterfaces.hasMoreElements()){
                NetworkInterface networkInterface = allNetInterfaces.nextElement();
                Enumeration<InetAddress> address = networkInterface.getInetAddresses();
                while(address.hasMoreElements()){
                    InetAddress inetAddress = address.nextElement();
                    if(inetAddress != null
                            && inetAddress instanceof Inet4Address
                            && !"127.0.0.1".equals(inetAddress.getHostAddress())){
                        localHostAddress = inetAddress.getHostAddress();
                    }
                }
            }
        }catch (Exception e){
        }
        return localHostAddress;
    }


    //说明: InputStream -> byte[]
    /**{ ylx } 2021/6/16 21:30 */
    public static byte[] toByteArray(InputStream input){
        try {
            try(ByteArrayOutputStream output = new ByteArrayOutputStream()){
                byte[] buffer = new byte[4096];
                int n = -1;
                while (-1 != (n = input.read(buffer))) {
                    output.write(buffer, 0, n);
                }
                return output.toByteArray();
            }
        } catch (IOException e) {
            return exMsg(e.getMessage());
        }
    }
}









