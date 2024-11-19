package com.ziio.maker.template;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.ziio.maker.meta.Meta;
import com.ziio.maker.meta.enums.FileGenerateTypeEnum;
import com.ziio.maker.meta.enums.FileTypeEnum;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class TemplateMaker {
    public static void main(String[] args) {
        // Basic Info
        String name = "acm-template-generator";
        String description = "ACM 示例模板生成器";

        // File config
        String projectPath = System.getProperty("user.dir");
        String sourceRootPath = new File(projectPath).getParent() + File.separator + "code-producer-demo-projects/acm-template-pro";
        // win system path transfer
        sourceRootPath = sourceRootPath.replaceAll("\\\\" , "/");

        String fileInputPath = "src/com/ziio/acm/MainTemplate.java";
        String fileOutputPath = fileInputPath + ".ftl";

        // input model args
        Meta.ModelConfig.ModelInfo modelInfo = new Meta.ModelConfig.ModelInfo();
        modelInfo.setFieldName("outputText");
        modelInfo.setType("string");
        modelInfo.setDefaultValue("sum = ");

        // 使用字符串替换 .java , 生成 .ftl
        String fileInputAbsolutePath = sourceRootPath + File.separator + fileInputPath;
        String fileContent = FileUtil.readUtf8String(fileInputAbsolutePath);
        String replacedWord = String.format("${%s}", modelInfo.getFieldName());
        String ftlContent = StrUtil.replace(fileContent , "Sum:" , replacedWord );

        // output .ftl file
        String fileOutputAbsolutePath = sourceRootPath + File.separator + fileOutputPath;
        FileUtil.writeUtf8String(ftlContent,fileOutputAbsolutePath);

        // 生成配置文件 , modelInfo --> meta.json
        String metaOutputPath = sourceRootPath + File.separator + "meta.json";
        // 1. 构造配置参数
        Meta meta = new Meta();
        meta.setName(name);
        meta.setDescription(description);

        // file config
        Meta.FileConfig fileConfig = new Meta.FileConfig();
        meta.setFileConfig(fileConfig);
        fileConfig.setSourceRootPath(sourceRootPath);
        List<Meta.FileConfig.FileInfo> fileInfoList = new ArrayList<>();
        fileConfig.setFiles(fileInfoList);
        Meta.FileConfig.FileInfo fileInfo = new Meta.FileConfig.FileInfo();
        fileInfo.setInputPath(fileInputPath);
        fileInfo.setOutputPath(fileOutputPath);
        fileInfo.setType(FileTypeEnum.FILE.getValue());
        fileInfo.setGenerateType(FileGenerateTypeEnum.DYNAMIC.getValue());
        fileInfoList.add(fileInfo);

        // model config
        Meta.ModelConfig modelConfig = new Meta.ModelConfig();
        meta.setModelConfig(modelConfig);
        List<Meta.ModelConfig.ModelInfo> modelInfoList = new ArrayList<>();
        modelConfig.setModels(modelInfoList);
        modelInfoList.add(modelInfo);

        // output meta.json
        FileUtil.writeUtf8String(JSONUtil.toJsonPrettyStr(meta) , metaOutputPath);
    }

}
