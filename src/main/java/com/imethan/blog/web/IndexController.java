package com.imethan.blog.web;

import com.imethan.blog.document.blog.Article;
import com.imethan.blog.service.ArticleService;
import com.imethan.blog.service.StatisticsGuavaCacheService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * @Name IndexController
 * @Description ${DESCRIPTION}
 * @Author huangyingfeng
 * @Create 2018-09-27 20:50
 */
@Controller
@Log4j2
public class IndexController {

    @Autowired
    private StatisticsGuavaCacheService statisticsGuavaCacheService;
    @Autowired
    private ArticleService articleService;

    @GetMapping(value = "")
    public String index(Model model) {
        model.addAttribute("COUNT", statisticsGuavaCacheService.getHomeStatistics());
        return "home";
    }

    @GetMapping(value = "/home")
    public String home(Model model) {
        model.addAttribute("COUNT", statisticsGuavaCacheService.getHomeStatistics());
        return "home";
    }

    @GetMapping(value = "gitalk")
    public String gitalk(Model model, HttpServletRequest request) {

        return "gitalk";
    }

    @GetMapping(value = "/favorite")
    public String favorite(Model model) {
        Article article = articleService.findByTitle("Favorite");
        String id = "favorite";
        if(article != null){
            id = article.getId();
        }
        model.addAttribute("id", id);
        return "blog/blog-article-md";
    }

    @GetMapping(value = "about")
    public String about(Model model, HttpServletRequest request) {
        return "about";
    }


}
