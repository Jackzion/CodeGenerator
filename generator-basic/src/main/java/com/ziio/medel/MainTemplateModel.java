package com.ziio.medel;

import lombok.Data;

/**
 * 动态模板 acm model
 */
@Data
public class MainTemplateModel {
    /**
     * 是否生成循环
     */
    private boolean loop;
    /**
     * 作者注释
     */
    private String author;
    /**
     * 输出信息
     */
    private String outputText;
}
