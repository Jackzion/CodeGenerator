package com.ziio.maker.cli.command;

import cn.hutool.core.io.FileUtil;
import picocli.CommandLine;

import java.io.File;
import java.util.List;

/**
 * 查看源文件列表 command
 */
@CommandLine.Command(name = "list" , description = "查看文件列表", mixinStandardHelpOptions = true)
public class ListCommand implements Runnable {

    @Override
    public void run() {
        String projectPath = System.getProperty("user.dir");
        // 整个项目根路径
        File parentFile = new File(projectPath).getParentFile();
        // 输入路径
        String inputPath = new File(parentFile, "code-producer-demo-projects/acm-template").getAbsolutePath();
        // hutool 遍历路径下文件
        List<File> files = FileUtil.loopFiles(inputPath);
        for(File file : files){
            System.out.println(file);
        }
    }
}
