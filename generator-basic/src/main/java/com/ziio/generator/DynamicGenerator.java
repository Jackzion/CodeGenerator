package com.ziio.generator;

import com.ziio.medel.MainTemplateModel;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 动态文件生成器 (freemarker template)
 */
public class DynamicGenerator {

    /**
     * 生成文件
     *
     * @param inputPath 模板文件输入路径
     * @param outputPath 输出路径
     * @param model 数据模型
     * @throws IOException
     * @throws TemplateException
     */
    public static void doGenerate(String inputPath , String outputPath , Object model) throws IOException, TemplateException {
        Configuration configuration = new Configuration(Configuration.VERSION_2_3_30);
        // 指定模板所在路径
        File templateDir = new File(inputPath).getParentFile();
        configuration.setDirectoryForTemplateLoading(templateDir);
        // 设置模板文件使用字符
        configuration.setDefaultEncoding("utf-8");
        // 加载模板
        Template template = configuration.getTemplate(new File(inputPath).getName());
        // 指定生成路径
        FileWriter out = new FileWriter("acm.java");
        // 生成文件
        template.process(model,out);
        // 关闭输出流
        out.close();
    }
}
