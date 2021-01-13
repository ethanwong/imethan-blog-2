package com.imethan.blog.service;

import com.imethan.blog.document.blog.Article;
import com.imethan.blog.document.blog.Channel;
import com.imethan.blog.common.Constant;
import com.imethan.blog.dto.ResultDto;
import com.imethan.blog.repository.ArticleRepository;
import com.imethan.blog.repository.ChannelRepository;
import com.imethan.blog.util.TimeUtils;
import com.imethan.blog.util.UuidUtils;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Name ChannelServiceImpl
 * @Description
 * @Author huangyingfeng
 * @Create 2020-12-28 11:48
 */
@Service
@Log4j2
public class ChannelServiceImpl implements ChannelService {

    @Autowired
    private ChannelRepository channelRepository;
    @Autowired
    private ArticleRepository articleRepository;

    @Override
    public ResultDto saveOrUpdate(Channel channel) {
        try {
            if (StringUtils.isBlank(channel.getId())) {
                channel.setId(UuidUtils.getUuid());
                channel.setCreateAt(TimeUtils.dateToString(new Date()));
                channel.setOrderNo(1);
            } else {
                Channel channelSource = this.getById(channel.getId());
                channel.setCreateAt(channelSource.getCreateAt());
                channel.setUpdateAt(TimeUtils.dateToString(new Date()));
            }

            if (channel.getName().equals(Constant.INNER_CHANNEL_NAME)) {
                channel.setType(1);
                channel.setShow(false);
            }

            Channel resultChannel = channelRepository.save(channel);
            Map<String, String> data = new HashMap<>();
            data.put("id", resultChannel.getId());

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
            //检查是否被关联
            List<Article> list = articleRepository.findByChannelId(id);
            if (list != null && list.size() > 0) {
                return ResultDto.ReturnFail("被关联的栏目不能删除");
            }
            channelRepository.deleteById(id);
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
            Channel channel = channelRepository.findById(id).get();
            channel.setStatus(Constant.ARTICLE_STATUS_RECYCLE);
            channelRepository.save(channel);
            return ResultDto.ReturnSuccess();
        } catch (Exception e) {
            String message = e.getMessage();
            log.error(message);
            return ResultDto.ReturnFail(message);
        }
    }

    @Override
    public ResultDto update(Channel channel) {
        return null;
    }

    @Override
    public Channel getById(String id) {
        return channelRepository.getById(id);
    }

    @Override
    public ResultDto page(Map<String, Object> parameters, Integer pageNo, Integer pageSize) {
        PageRequest pageable = PageRequest.of(pageNo, pageSize, Sort.by(Sort.Order.desc("orderNo")).and(Sort.by(Sort.Order.desc("createAt"))));
        Page<Channel> page = channelRepository.getPageByParameters(parameters, pageable);
        return ResultDto.ReturnSuccessData(page);
    }

    @Override
    public ResultDto list(Map<String, Object> parameters) {
        List<Channel> list = channelRepository.getListByParameters(parameters, Sort.by(Sort.Order.desc("orderNo")).and(Sort.by(Sort.Order.desc("createAt"))));
        return ResultDto.ReturnSuccessData(list);
    }

    @Override
    public void checkInnerChannel() {
        try {
            Channel channel = channelRepository.getByName(Constant.INNER_CHANNEL_NAME);
            if (channel == null) {
                channel = new Channel();
                channel.setId(UuidUtils.getUuid());
                channel.setCreateAt(TimeUtils.dateToString(new Date()));
                channel.setName(Constant.INNER_CHANNEL_NAME);

            }
            channel.setShow(false);
            channel.setOrderNo(10000);
            channel.setStatus(0);
            channel.setType(1);
            channelRepository.save(channel);
        } catch (Exception e) {
            e.printStackTrace();
            log.info("checkInnerChannel error msg = {}", e.getMessage());
        }
    }

    @Override
    public Channel findByName(String channel) {
        return channelRepository.getByName(channel);
    }

    @Override
    public ResultDto modifyByKeyValue(String id, Map<String, Object> parameter) {
        Channel channel = channelRepository.findAndModify("id", id, parameter);
        return ResultDto.ReturnSuccessData(channel);
    }
}
