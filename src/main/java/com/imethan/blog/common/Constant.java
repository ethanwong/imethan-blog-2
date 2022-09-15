package com.imethan.blog.common;

/**
 * @Name Constant
 * @Description
 * @Author huangyingfeng
 * @Create 2020-12-17 15:01
 */
public class Constant {

    public static final String SITE_TITLE = "ImEthan - 一名软件工程师的技术自留地";

    /**
     * 0-正常发布文章
     */
    public static final Integer ARTICLE_STATUS_NORMAL = 0;
    /**
     * 1-文章草稿，未发布
     */
    public static final Integer ARTICLE_STATUS_DRAFT = 1;
    /**
     * 2-已经回收文章
     */
    public static final Integer ARTICLE_STATUS_RECYCLE = 2;

    /**
     * 3-内置不公开文章
     */
    public static final Integer ARTICLE_STATUS_INNER = 3;


    /**
     * 内置信息分类栏目名称
     */
    public static final String INNER_CHANNEL_NAME = "ImEthan基础信息";

    public static final String SETTING_MODULE_ABOUT = "about";
    public static final String SETTING_MODULE_EMAIL = "email";
    public static final String SETTING_MODULE_QINIU = "qiniu";



}
