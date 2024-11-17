package com.ziio.generator;

import com.ziio.model.DataModel;
import freemarker.template.TemplateException;

import java.io.File;
import java.io.IOException;


/**
* 核心生成器
*/
public class MainGenerator {

/**
* 生成目标静态和动态文件
*
* @param model 数据模型
* @throws TemplateException
* @throws IOException
*/
public static void doGenerate(Object model) throws TemplateException, IOException {
        String inputRootPath = ".source/acm-template-pro";
        String outputRootPath = "generator";

        String inputPath;
        String outputPath;


        inputPath = new File(inputRootPath, "src/com/ziio/acm/MainTemplate.java.ftl").getAbsolutePath();
        outputPath = new File(outputRootPath, "src/com/ziio/acm/MainTemplate.java").getAbsolutePath();
        DynamicGenerator.doGenerate(inputPath, outputPath, model);

        inputPath = new File(inputRootPath, ".gitignore").getAbsolutePath();
        outputPath = new File(outputRootPath, ".gitignore").getAbsolutePath();
        StaticGenerator.copyFilesByHutool(inputPath, outputPath);

        inputPath = new File(inputRootPath, "README.md").getAbsolutePath();
        outputPath = new File(outputRootPath, "README.md").getAbsolutePath();
        StaticGenerator.copyFilesByHutool(inputPath, outputPath);
    }
}
