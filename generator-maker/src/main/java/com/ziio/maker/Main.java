package com.ziio.maker;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.resource.ClassPathResource;
import cn.hutool.core.util.StrUtil;
import com.ziio.maker.generator.file.DynamicFileGenerator;
import com.ziio.maker.generator.file.JarGenerator;
import com.ziio.maker.generator.file.ScriptGenerator;
import com.ziio.maker.generator.main.MainGenerator;
import com.ziio.maker.meta.Meta;
import com.ziio.maker.meta.MetaManager;
import freemarker.template.TemplateException;

import java.io.File;
import java.io.IOException;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {


    public static void main(String[] args) throws TemplateException, IOException, InterruptedException {
        MainGenerator mainGenerator = new MainGenerator();
        mainGenerator.doGenerate();
    }
}