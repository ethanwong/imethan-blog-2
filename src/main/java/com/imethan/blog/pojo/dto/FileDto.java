package com.imethan.blog.pojo.dto;

import lombok.Data;

@Data
public class FileDto {

    /**
     * 0 表示上传失败，1 表示上传成功
     */
    private Integer success;
    /**
     * 提示信息
     */
    private String message;
    /**
     * 上传成功时返回文件地址
     */
    private String url;
    /**
     * 文件名
     */
    private String fileName;//

}