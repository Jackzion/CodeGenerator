package com.ziio.maker.generator.file;

import cn.hutool.core.io.resource.ClassPathResource;
import cn.hutool.core.util.StrUtil;
import com.sun.xml.internal.ws.util.StringUtils;
import com.ziio.maker.medel.DataModel;
import com.ziio.maker.meta.Meta;
import com.ziio.maker.meta.MetaManager;
import freemarker.template.TemplateException;
import freemarker.template.utility.StringUtil;

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
        StaticGenerator.copyFilesByHutool(inputPath,outputPath);
        // 生成动态文件
        String inputDynamicFilePath = projectPath + File.separator + "src/main/resources/templates/acm.java.ftl";
        String outputDynamicFilePath = projectPath + File.separator + "acm-template/src/com/ziio/acm/AcmTemplate.java";
        DynamicFileGenerator.doGenerate(inputDynamicFilePath,outputDynamicFilePath,model);
    }

    public static void main(String[] args) throws TemplateException, IOException {
        Meta meta = MetaManager.getSingleInstance();
        System.out.println(meta);

        // 输出根路径
        String projectPath = System.getProperty("user.dir");
        String outputPath = projectPath + File.separator + "generater" + File.separator + meta.getName();

        // 读取 resources 目录
        ClassPathResource classPathResource = new ClassPathResource("");
        String inputResourcePath = classPathResource.getAbsolutePath();

        // Java 包基础路径
        String outputBasePackage = meta.getBasePackage();
        String outputBasePackagePath = StrUtil.join("/", StrUtil.split(outputBasePackage, "."));
        String outputBaseJavaPackagePath = outputPath + File.separator + "src/main/java" + outputBasePackagePath;

        String inputFilePath;
        String outputFilePath;

        // datamodel
        inputFilePath = inputResourcePath + File.separator + "templates/java/model/DataModel.java.ftl";
        outputFilePath = outputBaseJavaPackagePath + "/model/DataModel.java";

        DynamicFileGenerator.doGenerate(inputFilePath,outputFilePath,meta);
    }

}
