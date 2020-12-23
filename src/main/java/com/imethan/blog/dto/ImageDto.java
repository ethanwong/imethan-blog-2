package com.imethan.blog.dto;

import lombok.Data;

@Data
public class ImageDto {
    /**
     * 0 表示上传失败，1 表示上传成功
     */
    private Integer success;
    /**
     * 提示信息
     */
    private String message;
    /**
     * 上传成功时返回图片地址
     */
    private String url;

}