package com.arno.blog.pojo;

import java.io.Serializable;

import com.arno.blog.framework.annotation.Column;
import com.arno.blog.framework.annotation.GeneratedValue;
import com.arno.blog.framework.annotation.GenerationType;
import com.arno.blog.framework.annotation.Id;
import com.arno.blog.framework.annotation.Table;

import lombok.Data;

/**
 * <p>
 * 介面訪問日誌表實體類
 * </p>
 *
 * @author Arno
 * @date 2020-02-07 14:04:12
 * @Version 1.0
 *
 */
@Data
@Table(name = "bl_log")
public class Log implements Serializable {

	private static final long serialVersionUID = 3344551717784614236L;

	/**
     * 日誌id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "log_id")
    private Integer logId;

    /**
     * 請求路徑
     */
    @Column(name = "log_url")
    private String logUrl;

    /**
     * 參數
     */
    @Column(name = "log_params")
    private String logParams;

    /**
     * 訪問狀態，1正常0異常
     */
    @Column(name = "log_status")
    private Integer logStatus;

    /**
     * 異常資訊
     */
    @Column(name = "log_message")
    private String logMessage;

    /**
     * 請求方式，get、post等
     */
    @Column(name = "log_method")
    private String logMethod;

    /**
     * 回應時間
     */
    @Column(name = "log_time")
    private Long logTime;

    /**
     * 返回值
     */
    @Column(name = "log_result")
    private String logResult;

    /**
     * 請求ip
     */
    @Column(name = "log_ip")
    private String logIp;

    /**
     * 創建時間
     */
    @Column(name = "created_time")
    private String createdTime;

    /**
     * 創建人
     */
    @Column(name = "created_by")
    private String createdBy;
}
