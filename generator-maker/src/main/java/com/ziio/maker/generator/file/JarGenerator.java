package com.ziio.maker.generator.file;

import jdk.nashorn.internal.ir.WhileNode;

import java.io.*;
import java.util.Map;

public class JarGenerator {

    public static void doGenerate(String projectDir) throws IOException, InterruptedException {

        // 清理之前的构建并打包
        String winMavenCommand = "mvn.cmd clean package -DskipTests=true";

        ProcessBuilder processBuilder = new ProcessBuilder(winMavenCommand.split(" "));
        processBuilder.directory(new File(projectDir));

        Process process = processBuilder.start();

        // 读取命令输出
        InputStream inputStream = process.getInputStream();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String line;
        while ((line = bufferedReader.readLine())!= null){
            System.out.println(line);
        }

        // 等待命令完成
        int exitCode = process.waitFor();
        System.out.println("命令执行结束 ，退出码 ：" + exitCode);
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        doGenerate("D:\\Test");
    }

}
