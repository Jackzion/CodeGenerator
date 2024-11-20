package com.ziio.maker.template.model;

import lombok.Data;

/**
 * 模板输出配置
 */
@Data
public class TemplateMakerOutputConfig {

    // 从未分组中移除组内的同名文件
    private boolean removeGroupFilesFromRoot = true;
}
