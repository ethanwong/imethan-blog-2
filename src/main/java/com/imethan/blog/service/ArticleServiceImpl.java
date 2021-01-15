package com.imethan.blog.service;

import com.imethan.blog.pojo.document.blog.Article;
import com.imethan.blog.pojo.document.blog.Channel;
import com.imethan.blog.common.Constant;
import com.imethan.blog.pojo.document.blog.Tag;
import com.imethan.blog.pojo.dto.ResultDto;
import com.imethan.blog.repository.ArticleRepository;
import com.imethan.blog.repository.ChannelRepository;
import com.imethan.blog.repository.TagRepository;
import com.imethan.blog.util.TimeUtils;
import com.imethan.blog.util.UuidUtils;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

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
    private ChannelRepository channelRepository;
    @Autowired
    private TagRepository tagRepository;


    @Override
    public ResultDto saveOrUpdate(Article article) {
        try {
            if (StringUtils.isBlank(article.getId())) {
                article.setId(UuidUtils.getUuid());
                if(StringUtils.isBlank(article.getCreateAt())){
                    article.setCreateAt(TimeUtils.dateToString(new Date()));
                }
            } else {
                Article articleSource = this.getById(article.getId());
                article.setCreateAt(articleSource.getCreateAt());
                article.setUpdateAt(TimeUtils.dateToString(new Date()));
            }

            //强制设定内置栏目归宿属文章为内置文章
            Channel channel = channelRepository.getById(article.getChannelId());
            if (channel.getName().equals(Constant.INNER_CHANNEL_NAME)) {
                article.setStatus(Constant.ARTICLE_STATUS_INNER);
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
            if (tagDb == null) {
                Tag tag = new Tag();
                tag.setName(name);
                tag.setCreateAt(TimeUtils.dateToString(new Date()));
                result.add(tag);
            }
        }
        try {
            tagRepository.saveAll(result);
        } catch (Exception e) {
            log.error("bach save tag error message=" + e.getMessage());
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
            String message = "操作成功，";
            Article article = articleRepository.findById(id).get();
            //回收状态下继续删除便是彻底删除
            if (article.getStatus().equals(Constant.ARTICLE_STATUS_RECYCLE)) {
                articleRepository.deleteById(id);
                message += "已经彻底删除！";
            } else {
                article.setStatus(Constant.ARTICLE_STATUS_RECYCLE);
                articleRepository.save(article);
                message += "进入回收站！";
            }
            return ResultDto.ReturnSuccess(message);
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
        Article article = articleRepository.getById(id);
        Channel channel = channelRepository.getById(article.getChannelId());
        if (channel != null) {
            article.setChannelName(channel.getName());
        }
        return article;
    }

    @Override
    public ResultDto page(Map<String, Object> parameters, Integer pageNo, Integer pageSize) {
        PageRequest pageable = PageRequest.of(pageNo, pageSize, Sort.by(Sort.Order.desc("createAt")));
        Page<Article> page = articleRepository.getPageByParameters(parameters, pageable);

        for (Article article : page.getContent()) {
            Channel channel = channelRepository.getById(article.getChannelId());
            if (channel != null) {
                article.setChannelName(channel.getName());
            }
        }

        return ResultDto.ReturnSuccessData(page);
    }

    @Override
    public List<Article> searchArticleByTag(Tag tag) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("LIKE_tag", tag.getName());
        return articleRepository.getListByParameters(parameters, null);
    }

    @Override
    public Article findByTitle(String title) {
        return articleRepository.findByTitle(title);
    }

}
