package com.imethan.blog.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Name BootstrapTableResult
 * @Description ${DESCRIPTION}
 * @Author huangyingfeng
 * @Create 2019-11-01 13:42
 */
@Data
public class BootstrapTableResult<T> implements Serializable {
    private long total;
    private List<T> rows = new ArrayList<T>();
}
