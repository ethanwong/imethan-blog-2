package com.imethan.blog.web;

import com.imethan.blog.dto.BootstrapTableResult;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
        return "console/channel";
    }

}
