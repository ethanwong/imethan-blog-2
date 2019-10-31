package com.imethan.blog;

import com.alibaba.fastjson.JSONObject;
import com.imethan.blog.configuration.BlogApplication;
import lombok.extern.log4j.Log4j2;
import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;

import java.util.Map;
import java.util.Set;

/**
 * @Name BaseUnitTest
 * @Description 单元测试基础类
 * @Author huangyingfeng
 * @Create 2018-09-12 19:13
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = BlogApplication.class)
@Log4j2
public class BaseUnitTest {

    @Autowired
    private WebApplicationContext context;
    public MockMvc mvc;

    @Before
    public void setUp() throws Exception {
        mvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @After
    public void after() {

    }

    /**
     * POST测试
     *
     * @param uri        URI
     * @param jSONObject body内容
     * @return
     * @throws Exception
     */
    public MvcResult post(String uri, JSONObject jSONObject) throws Exception {
        return mvc.perform(MockMvcRequestBuilders.post(uri, "json").content(jSONObject.toJSONString()).accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print()).andReturn();
    }

    /**
     * POST测试
     *
     * @param uri    URI
     * @param params url参数
     * @return
     * @throws Exception
     */
    public MvcResult post(String uri, MultiValueMap<String, String> params) throws Exception {
        return mvc.perform(MockMvcRequestBuilders.post(uri, "json").params(params).accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print()).andReturn();
    }

    /**
     * POST测试
     *
     * @param uri        URI
     * @param jSONObject body内容
     * @param params     url参数
     * @return
     * @throws Exception
     */
    public MvcResult post(String uri, JSONObject jSONObject, MultiValueMap<String, String> params) throws Exception {
        return mvc.perform(MockMvcRequestBuilders.post(uri, "json").params(params).content(jSONObject.toJSONString()).accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print()).andReturn();
    }

    /**
     * PUT测试请求
     *
     * @param uri
     * @param jSONObject
     * @param params
     * @return
     * @throws Exception
     */
    public MvcResult put(String uri, JSONObject jSONObject, MultiValueMap<String, String> params) throws Exception {
        return mvc.perform(MockMvcRequestBuilders.put(uri, "json").params(params).content(jSONObject.toJSONString()).accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print()).andReturn();
    }

    /**
     * PUT测试请求
     *
     * @param uri
     * @param jSONObject
     * @return
     * @throws Exception
     */
    public MvcResult put(String uri, JSONObject jSONObject) throws Exception {
        return mvc.perform(MockMvcRequestBuilders.put(uri, "json").content(jSONObject.toJSONString()).accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print()).andReturn();
    }

    /**
     * DELETE测试请求
     *
     * @param uri
     * @param params
     * @return
     * @throws Exception
     */
    public MvcResult delete(String uri, Map params) throws Exception {
        MockHttpServletRequestBuilder mockMvcRequestBuilders = MockMvcRequestBuilders.delete(uri).accept(MediaType.APPLICATION_JSON);
        Set<Map.Entry> entrySet = params.entrySet();
        for (Map.Entry entry : entrySet) {
            mockMvcRequestBuilders.param((String) entry.getKey(), (String) entry.getValue());
        }
        return mvc.perform(mockMvcRequestBuilders)
                .andDo(MockMvcResultHandlers.print()).andReturn();
    }

    /**
     * GET测试请求
     *
     * @param uri
     * @param params
     * @return
     * @throws Exception
     */
    public MvcResult get(String uri, Map params) throws Exception {
        try {
            MockHttpServletRequestBuilder mockMvcRequestBuilders = MockMvcRequestBuilders.get(uri).accept(MediaType.APPLICATION_JSON);
            Set<Map.Entry> entrySet = params.entrySet();
            for (Map.Entry entry : entrySet) {
                mockMvcRequestBuilders.param((String) entry.getKey(), (String) entry.getValue());
            }
            return mvc.perform(mockMvcRequestBuilders)
                    .andDo(MockMvcResultHandlers.print()).andReturn();
        } catch (Exception e) {
            log.error(e);
            throw e;
        }

    }


}
