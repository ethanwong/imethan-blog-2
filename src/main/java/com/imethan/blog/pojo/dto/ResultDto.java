package com.imethan.blog.pojo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Name ResultDto
 * @Description
 * @Author huangyingfeng
 * @Create 2020-12-11 14:54
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResultDto {

    public static final String SUCCESS_MESSAGE = "操作成功！";
    public static final String FAIL_MESSAGE = "操作失败！";

    /**
     * 是否成功
     */
    public boolean isSuccess;
    /**
     * 提示信息
     */
    public String message;
    /**
     * 数据域
     */
    public Object data;

    public ResultDto(boolean isSuccess) {
        this.isSuccess = isSuccess;
        if (isSuccess) {
            this.message = SUCCESS_MESSAGE;
        } else {
            this.message = FAIL_MESSAGE;
        }
    }

    public ResultDto(boolean isSuccess, Object data) {
        this.isSuccess = isSuccess;
        if (isSuccess) {
            this.message = SUCCESS_MESSAGE;
        } else {
            this.message = FAIL_MESSAGE;
        }
        this.data = data;
    }

    /**
     * 操作成功响应
     * @return
     */
    public static ResultDto ReturnSuccess() {
        return new ResultDto(true);
    }

    /**
     * 操作异常响应
     * @param message
     * @return
     */
    public static ResultDto ReturnFail(String message) {
        return new ResultDto(false, message, null);
    }

    /**
     * 操作成功响应
     * @param message
     * @return
     */
    public static ResultDto ReturnSuccess(String message) {
        return new ResultDto(true, message, null);
    }

    /**
     * 操作成功响应数据
     * @param data
     * @return
     */
    public static ResultDto ReturnSuccessData(Object data){
        return new ResultDto(true, data);
    }
}
