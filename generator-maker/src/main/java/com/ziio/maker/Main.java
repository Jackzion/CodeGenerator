package com.ziio.maker;

import com.ziio.maker.generator.main.GenerateTemplate;
import com.ziio.maker.generator.main.ZipGenerator;
import freemarker.template.TemplateException;

import java.io.IOException;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {


    public static void main(String[] args) throws TemplateException, IOException, InterruptedException {
//        MainGenerator mainGenerator = new MainGenerator();
        GenerateTemplate mainGenerator = new ZipGenerator();
        mainGenerator.doGenerate();
    }
}