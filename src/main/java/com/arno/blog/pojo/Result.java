package com.arno.blog.pojo;

import java.io.Serializable;

import lombok.Data;

/**
 * 返回對象
 *
 * @author Arno
 */
@Data
public class Result<T> implements Serializable {

	private static final long serialVersionUID = 2548875595736527969L;

	/**
     * 回應業務狀態
     */
    private Integer code;

    /**
     * 回應訊息
     */
    private String msg;

    /**
     * 回應資料
     */
    private T data;

    /**
     * 是否加密。1加密0未加密。默認不加密
     */
    private Integer encrypt = 0;

    /**
     * 默認構造，默認為操作成功
     */
    public Result() {
        this.code = 20000;
        this.msg = "操作成功！";
    }

    /**
     * 全參數構造，自己設置code，msg，data
     *
     * @param code
     * @param msg
     * @param data
     */
    public Result(Integer code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    /**
     * 無data構造，一般用於操作失敗、刪除更新等操作
     *
     * @param code
     * @param msg
     */
    public Result(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    /**
     * 預設成功構造，自訂返回文本
     *
     * @param msg
     */
    public Result(String msg) {
        this.code = 20000;
        this.msg = msg;
    }

    /**
     * 自訂返回結果構造
     */
    public Result(Integer code) {
        this.code = code;
        this.msg = "操作失敗！";
    }

    /**
     * 預設成功構造，自訂返回資料
     *
     * @param data
     */
    public Result(T data) {
        this.code = 20000;
        this.msg = "操作成功！";
        this.data = data;
    }

    /**
     * 預設成功構造，自訂文本和data
     *
     * @param msg
     * @param data
     */
    public Result(String msg, T data) {
        this.code = 20000;
        this.msg = msg;
        this.data = data;
    }

    @Override
    public String toString() {
        return "Result{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                ", encrypt=" + encrypt +
                '}';
    }
}
