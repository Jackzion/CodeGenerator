package com.ziio.maker.generator.file;

import cn.hutool.core.io.FileUtil;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class ScriptGenerator {

    public static void doGenerate(String outputPath , String jarPath) throws IOException, InterruptedException {

        // windows.bat
        StringBuilder sb = new StringBuilder();
        sb.append("@echo off").append("\n");
        sb.append(String.format("java -jar %s %%*" , jarPath)).append("\n");
        FileUtil.writeBytes(sb.toString().getBytes(StandardCharsets.UTF_8) , outputPath + ".bat");

    }

    public static void main(String[] args) throws IOException, InterruptedException {
        String outputPath = System.getProperty("user.dir") + File.separator + "generator";
        doGenerate(outputPath, "");
    }

}
