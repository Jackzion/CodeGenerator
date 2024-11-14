package com.ziio;

import com.ziio.generator.StaticGenerator;

import java.io.File;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {
        // 获取项目根路径
        String projectPath = System.getProperty("user.dir");
        File parentFile = new File(projectPath).getParentFile();
        // input path
        String inputPath = new File(parentFile, "code-producer-demo-projects/acm-template").getAbsolutePath();
        // output path
        String outputPath = projectPath;
        StaticGenerator.copyFilesByRecursive(inputPath,outputPath);

    }
}