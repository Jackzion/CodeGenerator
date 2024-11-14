package com.ziio.generator;

import com.ziio.medel.MainTemplateModel;
import freemarker.template.TemplateException;

import java.io.File;
import java.io.IOException;

/**
 * 核心生成器 （Static + Dynamic）
 */
public class MainGenerator {
    public static void doGenerate(Object model) throws TemplateException, IOException {
        String projectPath = System.getProperty("user.dir");
        // 整个项目根路径
        File parentFile = new File(projectPath).getParentFile();
        // input Path and output Path
        String inputPath = new File(parentFile,"code-producer-demo-projects/acm-template").getAbsolutePath();
        String outputPath = projectPath;
        // 生成静态文件
        StaticGenerator.copyFilesByRecursive(inputPath,outputPath);
        // 生成动态文件
        String inputDynamicFilePath = projectPath + File.separator + "src/main/resources/templates/acm.java.ftl";
        String outputDynamicFilePath = projectPath + File.separator + "acm-template/src/com/ziio/acm/AcmTemplate.java";
        DynamicGenerator.doGenerate(inputDynamicFilePath,outputDynamicFilePath,model);
    }

    public static void main(String[] args) throws TemplateException, IOException {
        MainTemplateModel acmModel = new MainTemplateModel();
        acmModel.setOutputText("ziio , the sum is");
        acmModel.setLoop(false);
        acmModel.setAuthor("ziio");
        doGenerate(acmModel);
    }

}
