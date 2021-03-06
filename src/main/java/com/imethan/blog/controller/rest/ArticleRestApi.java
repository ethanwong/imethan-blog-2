package com.imethan.blog.controller.rest;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.imethan.blog.pojo.document.blog.Article;
import com.imethan.blog.pojo.document.blog.Channel;
import com.imethan.blog.common.Constant;
import com.imethan.blog.pojo.dto.ImageDto;
import com.imethan.blog.pojo.dto.ResultDto;
import com.imethan.blog.service.ArticleService;
import com.imethan.blog.service.ChannelService;
import com.imethan.blog.service.GridFsService;
import com.imethan.blog.service.TagService;
import com.imethan.blog.util.SecurityUtils;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

/**
 * @Name ArticleRestApi
 * @Description
 * @Author huangyingfeng
 * @Create 2020-12-11 14:49
 */
@RestController
@Log4j2
@RequestMapping(value = "/api/article")
public class ArticleRestApi {

    @Autowired
    private ArticleService articleService;
    @Autowired
    private ChannelService channelService;
    @Autowired
    private TagService tagService;
    @Autowired
    GridFsService gridFsService;

    @Value("classpath:static/imethan-blog-histoty.json")
    private Resource resource;

    @PreAuthorize(value = "isAuthenticated()")
    @GetMapping("import")
    public String bachImport() {
        try {
            String data = IOUtils.toString(resource.getInputStream(), Charset.forName("UTF-8"));
            JSONObject json = JSON.parseObject(data);

            JSONArray RECORDS = json.getJSONArray("RECORDS");

            for (int i = 0; i < RECORDS.size(); i++) {
                JSONObject jsonObject = RECORDS.getJSONObject(i);
                Article article = new Article();
                article.setTitle(jsonObject.getString("title"));
                article.setContent(jsonObject.getString("content"));
                article.setCreateAt(jsonObject.getString("createAt"));
                article.setUpdateAt(jsonObject.getString("updateAt"));

                Channel channelDb = channelService.findByName(jsonObject.getString("channel"));

                if (channelDb == null) {
                    Channel channel = new Channel();
                    channel.setShow(true);
                    channel.setType(0);
                    channel.setStatus(0);
                    channel.setName(jsonObject.getString("channel"));
                    ResultDto resultDto = channelService.saveOrUpdate(channel);
                    Map<String, String> resultDtoData = (Map<String, String>) resultDto.getData();
                    String channelId = resultDtoData.get("id");

                    article.setChannelId(channelId);
                } else {
                    article.setChannelId(channelDb.getId());
                }

                article.setTag(jsonObject.getString("tag"));
                if (jsonObject.getInteger("show").equals(1)) {
                    article.setStatus(0);
                } else {
                    article.setStatus(1);
                }
                Article articleDb = articleService.findByTitle(article.getTitle());
                if(articleDb == null){
                    articleService.saveOrUpdate(article);
                }

            }
            return "SUCCESS";
        } catch (Exception e) {
            log.error("ArticleRestApi bachImport error msg = {}", e.getMessage());
            return e.getMessage();
        }


    }

    /**
     * 必须登录才能调用
     *
     * @param article
     * @return
     */
    @PreAuthorize(value = "isAuthenticated()")
    @PostMapping("")
    public ResultDto add(@RequestBody Article article) {
        log.info("post article {}", article);
        return articleService.saveOrUpdate(article);
    }

    /**
     * 必须登录才能调用
     *
     * @param id
     * @return
     */
    @PreAuthorize(value = "isAuthenticated()")
    @DeleteMapping("/{id}")
    public ResultDto delete(@PathVariable String id) {
        log.info("delete article id {}", id);
        return articleService.recycle(id);
    }

    /**
     * 开放授权
     *
     * @param page
     * @param size
     * @param request
     * @return
     */
    @PreAuthorize(value = "permitAll()")
    @GetMapping("/page/{page}/{size}")
    public ResultDto page(@PathVariable Integer page, @PathVariable Integer size, HttpServletRequest request) {

        if (size > 100) {
            size = 100;
        }

        Map<String, Object> parameters = new HashMap<>();

        String status = request.getParameter("status");
        if (StringUtils.isNoneBlank(status) && SecurityUtils.isLogin()) {
            parameters.put("EQ_status", Integer.valueOf(status));//草稿箱和回收站,登录状态才允许访问
        } else {
            parameters.put("EQ_status", Constant.ARTICLE_STATUS_NORMAL);//
        }
        String title = request.getParameter("title");
        String tag = request.getParameter("tag");
        String channelId = request.getParameter("channelId");

        if (StringUtils.isNoneBlank(title)) {
            parameters.put("LIKE_title", title);
        }
        if (StringUtils.isNoneBlank(tag)) {
            parameters.put("LIKE_tag", tag);
        }
        if (StringUtils.isNoneBlank(channelId)) {
            parameters.put("EQ_channelId", channelId);
        }
        log.info("get article page page={},size={},parameters={}", page, size, parameters);

        return articleService.page(parameters, page, size);
    }

    /**
     * 开放授权
     *
     * @param id
     * @return
     */
    @PreAuthorize(value = "permitAll()")
    @GetMapping("/{id}")
    public ResultDto get(@PathVariable String id) {
        Article article = articleService.getById(id);
        return ResultDto.ReturnSuccessData(article);
    }

    /**
     * 开放授权
     *
     * @return
     */
    @PreAuthorize(value = "permitAll()")
    @GetMapping("/tag")
    public ResultDto getTag() {
        return tagService.findAll();
    }

    /**
     * 浏览图片
     *
     * @param id
     * @param response
     * @return
     */
    @GetMapping(value = "image/{id}", produces = {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE, MediaType.IMAGE_GIF_VALUE})
    public void image(@PathVariable String id, HttpServletResponse response, HttpServletRequest request) {
        try {
            if (request.getHeader("IF-Modified-Since") != null || request.getHeader("If-None-Match") != null) {
                log.info("使用缓存 IF-Modified-Since = {},If-None-Match = {} ", request.getHeader("IF-Modified-Since"), request.getHeader("If-None-Match"));
                response.setStatus(304);
                return;
            }

            log.info("下载图片:id={}", id);
            GridFsResource gridFsResource = gridFsService.getImage(id);
            if (gridFsResource != null) {
                response.addHeader("Accept-Ranges", "bytes");
                response.addHeader("Cache-Control", "max-age=2592000");
                response.addHeader("Last-Modified", gridFsResource.getGridFSFile().getUploadDate().toGMTString());
                response.addHeader("Etag", gridFsResource.getGridFSFile().getObjectId().toString());
                response.addHeader("Vary", "Origin");
                response.addHeader("Vary", "Access-Control-Request-Method");
                response.addHeader("Vary", "Access-Control-Request-Headers");
                String ext = gridFsResource.getGridFSFile().getMetadata().get("ext").toString();

                String contentType = MediaType.IMAGE_JPEG_VALUE;
                if (ext.equalsIgnoreCase("JPG")) {
                    contentType = MediaType.IMAGE_JPEG_VALUE;
                } else if (ext.equalsIgnoreCase("PNG")) {
                    contentType = MediaType.IMAGE_PNG_VALUE;
                } else if (ext.equalsIgnoreCase("GIF")) {
                    contentType = MediaType.IMAGE_GIF_VALUE;
                }
                response.setContentType(contentType);

                response.getOutputStream().write(IOUtils.toByteArray(gridFsResource.getInputStream()));
            }
        } catch (IOException e) {
            log.error("get image error msg=" + e.getMessage());
            e.printStackTrace();
        }

    }

    /**
     * 上传图片
     *
     * @param request
     * @param multipartFile
     * @param response
     * @return
     */
    @PreAuthorize(value = "isAuthenticated()")
    @PostMapping("/upload/image")
    public ImageDto uploadImage(HttpServletRequest request, @RequestParam("editormd-image-file") MultipartFile multipartFile, HttpServletResponse response) {
        String contentType = multipartFile.getContentType();
        String root_FileName = multipartFile.getOriginalFilename();
        log.info("上传图片:name={},type={}", root_FileName, contentType);
        return gridFsService.saveImage(multipartFile);
    }

}
