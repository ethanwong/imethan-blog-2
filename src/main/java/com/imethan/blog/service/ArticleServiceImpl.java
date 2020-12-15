package com.imethan.blog.service;

import com.imethan.blog.document.blog.Article;
import com.imethan.blog.dto.ArticleDto;
import com.imethan.blog.dto.ResultDto;
import com.imethan.blog.repository.ArticleRepository;
import com.imethan.blog.util.TimeUtils;
import com.imethan.blog.util.UuidUtils;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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


    @Override
    public ResultDto saveOrUpdate(Article article) {
        try {
            if(StringUtils.isBlank(article.getId())){
                article.setId(UuidUtils.getUuid());
                article.setCreateAt(TimeUtils.dateToString(new Date()));
            }else{
                Article articleSource = this.getById(article.getId());
                article.setCreateAt(articleSource.getCreateAt());
                article.setUpdateAt(TimeUtils.dateToString(new Date()));
            }
            Article resultArticle = articleRepository.save(article);
            Map<String,String> data = new HashMap<>();
            data.put("id",resultArticle.getId());
            return ResultDto.ReturnSuccessData(data);
        } catch (Exception e) {
            e.printStackTrace();
            String message = e.getMessage();
            log.error(message);
            return ResultDto.ReturnFail(message);
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
    public ResultDto update(Article article) {
        return null;
    }

    @Override
    public Article getById(String id) {
        return articleRepository.findById(id).get();
    }

    @Override
    public ResultDto page(Integer pageNo, Integer pageSize) {
        Pageable Pageable =  PageRequest.of(pageNo,pageSize,Sort.by(Sort.Order.desc("createAt")));
        Page<Article> page = articleRepository.findAll(Pageable);
        return ResultDto.ReturnSuccessData(page);
    }
}
