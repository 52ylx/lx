package com.gitee.ylx.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * 结果
 *
 * @author ylx
 * @date 2020-05-28
 */
@ApiModel(value = "响应实体类")
public class Result<T> implements Serializable {

	@ApiModelProperty(value = "返回状态码",required = true)
	//返回状态码
	private Integer code;

	@ApiModelProperty(value = "返回信息",required = false)
	//返回信息
	private String message;

	@ApiModelProperty(value = "返回数据",required = false)
	//返回数据
	private T data;


	//说明: 发生错误
	/**{ ylx } 2021/1/15 15:27 */
	public boolean isErr(){
		return StatusCode.OK != code;
	}

    /**
     * 返回调用成功
     *
     * @return {@link Result<R>}
     */
    public static <R>Result<R> ok(){
        return new Result(StatusCode.OK, "调用成功!");
    }
    /**
     * 成功 返回 Data
     *
     * @param r r
     * @return {@link Result<R>}
     */
    public static <R>Result<R> ok(R r){
	    return new Result(StatusCode.OK,r);
    }

    /**
     * 调用成功返回提示信息 和 Data
     *
     * @param message 消息
     * @param r       r
     * @return {@link Result<R>}
     */
    public static <R>Result<R> ok(String message,R r){
		return new Result(StatusCode.OK,message, r);
	}

    /**
     * 返回错误
     *
     * @param msg
     * @return {@link Result}
     */
    public static Result err(String msg){
		return new Result(StatusCode.ERROR,msg);
	}

    /**
     * 返回指定错误码 和 错误信息
     *
     * @param code 代码
     * @param msg  味精
     * @return {@link Result}
     */
    public static Result err(Integer code,String msg){
		return new Result(code,msg);
	}

	/***
	 * Description 返回指定code码，错误信息，数据
	 * @author nq
	 * @CreateDate 2021/2/21 20:24
	 */
	public static Result err(Integer code,String msg,Object data){
		return new Result(code,msg,data);
	}
	public Result() {}

	public Result(Integer code, String message, T data) {
		this.code = code;
		this.message = message;
		this.data = data;
	}

	public Result(Integer code, T data) {
		this.code = code;
		this.data = data;
	}

	public Result(Integer code, String message) {
		this.code = code;
		this.message = message;
	}

	public Result(Integer code) {
		this.code = code;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}
}
