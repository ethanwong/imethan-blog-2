package com.imethan.blog.web;

import com.imethan.blog.service.StatisticsGuavaCacheService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @Name HomeController
 * @Description ${DESCRIPTION}
 * @Author huangyingfeng
 * @Create 2018-09-27 20:50
 */
@Controller
@Log4j2
public class HomeController {

    @Autowired
    StatisticsGuavaCacheService statisticsGuavaCacheService;

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


}
