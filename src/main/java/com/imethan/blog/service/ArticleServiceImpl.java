package com.imethan.blog.service;

import com.imethan.blog.document.blog.Article;
import com.imethan.blog.document.blog.Constant;
import com.imethan.blog.document.blog.Tag;
import com.imethan.blog.dto.ResultDto;
import com.imethan.blog.repository.ArticleRepository;
import com.imethan.blog.repository.TagRepository;
import com.imethan.blog.util.TimeUtils;
import com.imethan.blog.util.UuidUtils;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.lang.reflect.Array;
import java.util.*;

/**
 * @Name ArticleService
 * @Description
 * @Author huangyingfeng
 * @Create 2020-12-11 15:09
 */
@Service
@Log4j2
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private ArticleRepository articleRepository;
    @Autowired
    private TagRepository tagRepository;


    @Override
    public ResultDto saveOrUpdate(Article article) {
        try {
            if (StringUtils.isBlank(article.getId())) {
                article.setId(UuidUtils.getUuid());
                article.setCreateAt(TimeUtils.dateToString(new Date()));
            } else {
                Article articleSource = this.getById(article.getId());
                article.setCreateAt(articleSource.getCreateAt());
                article.setUpdateAt(TimeUtils.dateToString(new Date()));
            }
            Article resultArticle = articleRepository.save(article);
            Map<String, String> data = new HashMap<>();
            data.put("id", resultArticle.getId());

            handleTag(article.getTag());


            return ResultDto.ReturnSuccessData(data);
        } catch (Exception e) {
            e.printStackTrace();
            String message = e.getMessage();
            log.error(message);
            return ResultDto.ReturnFail(message);
        }
    }

    private void handleTag(String tags) {
        String[] tagList = tags.split(",");
        List<Tag> result = new ArrayList<>();
        for (String name : tagList) {
            Tag tagDb = tagRepository.findByName(name);
            if(tagDb == null){
                Tag tag = new Tag();
                tag.setName(name);
                tag.setCreateAt(TimeUtils.dateToString(new Date()));
                result.add(tag);
            }
        }
        try{
            tagRepository.saveAll(result);
        }catch (Exception e){
            log.error("bach save tag error message="+e.getMessage());
        }

    }

    @Override
    public ResultDto delete(String id) {
        try {
            articleRepository.deleteById(id);
            return ResultDto.ReturnSuccess();
        } catch (Exception e) {
            String message = e.getMessage();
            log.error(message);
            return ResultDto.ReturnFail(message);
        }
    }

    @Override
    public ResultDto recycle(String id) {
        try {
            Article article = articleRepository.findById(id).get();
            article.setStatus(Constant.ARTICLE_STATUS_RECYCLE);
            articleRepository.save(article);
            return ResultDto.ReturnSuccess();
        } catch (Exception e) {
            String message = e.getMessage();
            log.error(message);
            return ResultDto.ReturnFail(message);
        }
    }

    @Override
    public ResultDto update(Article article) {
        return null;
    }

    @Override
    public Article getById(String id) {
        return articleRepository.getById(id);
    }

    @Override
    public ResultDto page(Map<String, Object> parameters, Integer pageNo, Integer pageSize) {
        PageRequest pageable = PageRequest.of(pageNo, pageSize, Sort.by(Sort.Order.desc("createAt")));
        Page<Article> page = articleRepository.getPageByParameters(parameters, pageable);
        return ResultDto.ReturnSuccessData(page);
    }

    @Override
    public List<Article> searchArticleByTag(Tag tag) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("LIKE_tag",tag.getName());
        return articleRepository.getListByParameters(parameters,null);
    }
}
