package com.imethan.blog.rest;

import com.imethan.blog.document.blog.Article;
import com.imethan.blog.document.blog.Constant;
import com.imethan.blog.dto.FileDto;
import com.imethan.blog.dto.ImageDto;
import com.imethan.blog.dto.ResultDto;
import com.imethan.blog.service.ArticleService;
import com.imethan.blog.service.GridFsService;
import com.imethan.blog.service.TagService;
import com.imethan.blog.util.FileUtils;
import com.mongodb.client.gridfs.model.GridFSFile;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
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
    private TagService tagService;
    @Autowired
    GridFsService gridFsService;

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
        parameters.put("EQ_status", Constant.ARTICLE_STATUS_NORMAL);
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
