package com.imethan.blog.service;

import com.imethan.blog.document.blog.Article;
import com.imethan.blog.document.blog.Tag;
import com.imethan.blog.dto.ResultDto;
import com.imethan.blog.repository.TagRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Name TagServiceImpl
 * @Description
 * @Author huangyingfeng
 * @Create 2020-12-17 15:10
 */
@Service
@Log4j2
public class TagServiceImpl implements TagService {

    @Autowired
    private TagRepository tagRepository;
    @Autowired
    private ArticleService articleService;

    @Override
    public ResultDto saveOrUpdate(Tag tag) {
        try {
            tagRepository.save(tag);
            return ResultDto.ReturnSuccess();
        } catch (Exception e) {
            return ResultDto.ReturnFail(e.getMessage());
        }

    }

    @Override
    public ResultDto delete(String id) {
        return null;
    }

    @Override
    public ResultDto recycle(String id) {
        return null;
    }

    @Override
    public ResultDto update(Tag tag) {
        return null;
    }

    @Override
    public Tag getById(String id) {
        return null;
    }

    @Override
    public ResultDto findAll() {
        return ResultDto.ReturnSuccessData(tagRepository.findAll());
    }

    @Override
    public ResultDto clearTag() {
        try {
            List<Tag> tagList = tagRepository.findAll();
            for (Tag tag : tagList) {
                List<Article> artciles = articleService.searchArticleByTag(tag);
                if (artciles == null || artciles.isEmpty()) {
                    tagRepository.delete(tag);
                    log.info("remove tag {}", tag);
                }
            }

            return ResultDto.ReturnSuccess();
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResultDto.ReturnFail(e.getMessage());
        }

    }
}
