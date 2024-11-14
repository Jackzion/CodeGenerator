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
    public static void main(String[] args) throws IOException, TemplateException {
        Configuration configuration = new Configuration(Configuration.VERSION_2_3_30);
        // 指定模板所在路径
        configuration.setDirectoryForTemplateLoading(new File("src/main/resources/templates"));
        // 设置模板文件使用字符
        configuration.setDefaultEncoding("utf-8");
        // 加载模板
        Template template = configuration.getTemplate("acm.java.ftl");
        // 创建 data model
        MainTemplateModel mainTemplateModel = new MainTemplateModel();
        mainTemplateModel.setAuthor("ziio");
        mainTemplateModel.setLoop(true);
        mainTemplateModel.setOutputText("hello the sum is :");
        // 指定生成路径
        FileWriter out = new FileWriter("acm.java");
        // 生成文件
        template.process(mainTemplateModel,out);
        // 关闭输出流
        out.close();
    }
}
