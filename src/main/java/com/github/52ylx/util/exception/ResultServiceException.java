package com.gitee.ylx.util.exception;
import com.gitee.ylx.entity.StatusCode;

/**
 * 指定返回code 的异常
 * @author 86158
 */
public class ResultServiceException extends ServiceException {

    /** 状态码*/
    private int statusCode;
    /** 给前台返回的数据*/
    private Object data;

    public static void exMsg(String msg){
        throw  new ResultServiceException(msg, StatusCode.RESULT_SERVICE_ERR);
    }

    //说明: 抛出异常
    /**{ ylx } 2020/9/21 17:11 */
    public static void exMsg(String msg, int statusCode){
        throw new ResultServiceException(msg, statusCode);
    }

    public ResultServiceException(Throwable e, int statusCode) {
        super(e);
        this.statusCode = statusCode;
    }

    public ResultServiceException(String msg) {
        super(msg);
        this.statusCode = StatusCode.RESULT_SERVICE_ERR;
    }
    public ResultServiceException(String msg, int statusCode) {
        super(msg);
        this.statusCode = statusCode;
    }

    public ResultServiceException(String msg, Object data, int statusCode) {
        super(msg);
        this.data = data;
        this.statusCode = statusCode;
    }

    //说明: 获取状态码
    /**{ ylx } 2020/9/21 17:09 */
    public int getStatusCode(){
        return statusCode;
    }

    //获取返回的数据
    public Object getData(){
        return data;
    }
}
