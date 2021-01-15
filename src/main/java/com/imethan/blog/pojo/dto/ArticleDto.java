package com.imethan.blog.pojo.dto;

import lombok.Data;

/**
 * @Name ArticleDto
 * @Description
 * @Author huangyingfeng
 * @Create 2020-12-11 14:53
 */
@Data
public class ArticleDto {
    private String id;
    private String title;
    private String content;
    private String tag;
}
