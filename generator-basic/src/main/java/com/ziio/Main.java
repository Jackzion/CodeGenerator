package com.ziio;

import com.ziio.generator.DynamicGenerator;
import com.ziio.generator.StaticGenerator;
import com.ziio.medel.MainTemplateModel;
import freemarker.template.TemplateException;

import java.io.File;
import java.io.IOException;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) throws TemplateException, IOException {
        String projectPath = System.getProperty("user.dir");
        String inputPath = projectPath + File.separator + "src/main/resources/templates/acm.java.ftl";
        String outputPath = projectPath + File.separator + "acm.java";
        MainTemplateModel mainTemplateConfig = new MainTemplateModel();
        mainTemplateConfig.setAuthor("yupi");
        mainTemplateConfig.setLoop(false);
        mainTemplateConfig.setOutputText("求和结果：");
        DynamicGenerator.doGenerate(inputPath, outputPath, mainTemplateConfig);

    }
}