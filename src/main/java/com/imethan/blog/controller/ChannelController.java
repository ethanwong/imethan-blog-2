package com.imethan.blog.controller;

import com.imethan.blog.dto.BootstrapTableResult;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Name ChannelController
 * @Description ${DESCRIPTION}
 * @Author huangyingfeng
 * @Create 2019-10-31 16:57
 */
@Controller
@RequestMapping(value = "/console/channel")
public class ChannelController {

    @GetMapping(value = "")
    public String console() {
        return "/console/channel";
    }
    @ResponseBody
    @GetMapping(value = "json")
    public BootstrapTableResult json() {
        Map map = new HashMap();
        map.put("name","ethan");
        map.put("id","1234567890");

        Map map1 = new HashMap();
        map1.put("name","wong");
        map1.put("id","1234567890");

        List list = new ArrayList<>();
        list.add(map);
        list.add(map1);
        list.add(map);
        list.add(map1);
        list.add(map1);
        list.add(map1);
        list.add(map1);
        list.add(map1);
        list.add(map1);
        list.add(map1);

//        list.add(map1);
//        list.add(map1);
//        list.add(map1);
//        list.add(map1);
//        list.add(map1);
//        list.add(map1);
//        list.add(map1);
//        list.add(map1);
//        list.add(map1);
//        list.add(map1);
//        list.add(map1);
//        list.add(map1);
//        list.add(map1);
//        list.add(map1);
//        list.add(map1);
//        list.add(map1);
//        list.add(map1);
//        list.add(map1);
//        list.add(map1);

        BootstrapTableResult result = new BootstrapTableResult();
        result.setRows(list);
        result.setTotal(list.size());
        return result;
    }
}
