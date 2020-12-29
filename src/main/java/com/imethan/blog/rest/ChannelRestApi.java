package com.imethan.blog.rest;

import com.imethan.blog.document.blog.Channel;
import com.imethan.blog.document.blog.Constant;
import com.imethan.blog.dto.BootstrapTableResult;
import com.imethan.blog.dto.ResultDto;
import com.imethan.blog.service.ChannelService;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @Name ChannelRestApi
 * @Description
 * @Author huangyingfeng
 * @Create 2020-12-28 11:55
 */
@RestController
@Log4j2
@RequestMapping(value = "/api/channel")
public class ChannelRestApi {

    @Autowired
    private ChannelService channelService;

    @PreAuthorize(value = "permitAll()")
    @ResponseBody
    @GetMapping(value = "page")
    public BootstrapTableResult page(@RequestParam("pageNumber") Integer page, @RequestParam("pageSize") Integer size
            , @RequestParam("searchText") String searchText, HttpServletRequest request) {
        log.info("get channel page page={},size={}", page, size);

        page = page - 1;

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("EQ_status", Constant.ARTICLE_STATUS_NORMAL);

        if (StringUtils.isNoneBlank(searchText)) {
            parameters.put("LIKE_name", searchText);
        }
        ResultDto resultDto = channelService.page(parameters, page, size);
        Page<Channel> pageData = (Page<Channel>) resultDto.getData();

        BootstrapTableResult result = new BootstrapTableResult();
        result.setRows(pageData.getContent());
        result.setTotal(pageData.getTotalElements());
        return result;
    }

    @PreAuthorize(value = "permitAll()")
    @ResponseBody
    @GetMapping(value = "list")
    public ResultDto list(){
        return channelService.list();
    }

    /**
     * 必须登录才能调用
     *
     * @param channel
     * @return
     */
    @PreAuthorize(value = "isAuthenticated()")
    @PostMapping("")
    public ResultDto add(@RequestBody Channel channel) {
        log.info("post channel {}", channel);
        return channelService.saveOrUpdate(channel);
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
        log.info("delete channel id {}", id);
        return channelService.recycle(id);
    }
}
