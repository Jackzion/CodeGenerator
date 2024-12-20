package com.ziio.model.dto.generator;

import com.ziio.common.PageRequest;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 用户创建请求
 */
@Data
public class GeneratorQueryRequest extends PageRequest implements Serializable {

    /**
     * id
     */
    private Long id;

    /**
     * 搜索词
     */
    private String searchText;

    /**
     * 标题
     */
    private String title;

    /**
     * 内容
     */
    private String content;

    /**
     * 标签列表
     */
    private List<String> tags;

    /**
     * 至少有一个标签
     */
    private List<String> orTags;

    /**
     * 创建用户 id
     */
    private Long userId;

    private static final long serialVersionUID = 1L;
}