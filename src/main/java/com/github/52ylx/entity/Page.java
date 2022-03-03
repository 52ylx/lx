package com.gitee.ylx.entity;

import com.gitee.ylx.util.LX;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Page {
    private String msg = "";
    private int success = 1;
    private long count = 0;
    private List rows = new ArrayList();
    private Map entity = new HashMap();

    public Page(){}
    public Page(Map map){
        if (map == null) return;
        this.entity = map;
    }
    public Page(List rows){
        if (rows == null) return;
        this.rows = rows;
        this.count = rows.size();
    }
    public Page(List rows, long count){
        this.count = count;
        if (rows == null) return;
        this.rows = rows;
    }
    public Page(String msg){
        this.msg = msg;
        this.success = 0;
    }
    public Page(String msg,int success){
        this.msg = msg;
        this.success = success;
    }
    public static String toLogin(){
        return LX.toJSONString(new Page("请重新登陆",9));
    }
    public String getMsg() {
        return msg;
    }

    @Override
    public String toString() {
        return "Page{" +
                "msg='" + msg + '\'' +
                ", success=" + success +
                ", count=" + count +
                ", rows=" + rows +
                ", entity=" + entity +
                '}';
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
    public int getSuccess() {
        return success;
    }
    public void setSuccess(int success) {
        this.success = success;
    }
    public long getCount() {
        return count;
    }
    public void setCount(long count) {
        this.count = count;
    }
    public List getRows() {
        return rows;
    }
    public void setRows(List rows) {
        this.rows = rows;
    }
    public Map getEntity() {
        return entity;
    }
    public void setEntity(Map entity) {
        this.entity = entity;
    }
}
