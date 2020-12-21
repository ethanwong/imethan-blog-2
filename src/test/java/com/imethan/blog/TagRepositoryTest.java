package com.imethan.blog;

import com.imethan.blog.configuration.BlogApplication;
import com.imethan.blog.document.blog.Tag;
import com.imethan.blog.repository.TagRepository;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @Name TagRepositoryTest
 * @Description
 * @Author huangyingfeng
 * @Create 2020-12-17 15:52
 */
@SpringBootTest(classes = BlogApplication.class)
@Log4j2
public class TagRepositoryTest {
    @Autowired
    private TagRepository tagRepository;

//    @Test
    public void findByName(){
        Tag tag = new Tag();
        tag.setName("MQTT");
        tagRepository.save(tag);

        System.out.println(tagRepository.findByName("MQTT"));

    }
}
