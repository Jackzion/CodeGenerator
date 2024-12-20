package com.ziio.maker.meta;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import com.ziio.maker.meta.enums.FileGenerateTypeEnum;
import com.ziio.maker.meta.enums.FileTypeEnum;
import com.ziio.maker.meta.enums.ModelTypeEnum;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 元信息校验
 */
public class MetaValidator {

    public static void doValidate(Meta meta){

        validAndFillFileConfig(meta);

        validAndFillMetaRoot(meta);

        validAndFillModelConfig(meta);
    }

    private static void validAndFillModelConfig(Meta meta) {
        // modelConfig
        Meta.ModelConfig modelConfig = meta.getModelConfig();
        if (modelConfig != null) {
            List<Meta.ModelConfig.ModelInfo> modelInfos = modelConfig.getModels();
            if (!CollectionUtil.isNotEmpty(modelInfos)) {
                return;
            }
            for(Meta.ModelConfig.ModelInfo modelInfo : modelInfos){
                // MODEL_GROUP skip validate
                String groupKey = modelInfo.getGroupKey();
                if (StrUtil.isNotEmpty(groupKey)) {
                    // get the Next allArgsStr if is groupKey
                    if(StrUtil.isNotEmpty(groupKey)){
                        List<Meta.ModelConfig.ModelInfo> submodelInfoList = modelInfo.getModels();
                        String allArgsStr = submodelInfoList.stream()
                                .map(submodelInfo -> String.format("\"--%s\"", submodelInfo.getFieldName()))
                                .collect(Collectors.joining(", "));
                        modelInfo.setAllArgsStr(allArgsStr);
                    }
                    continue;
                }
                // filedName required
                String fieldName = modelInfo.getFieldName();
                if (StrUtil.isBlank(fieldName)) {
                    throw new MetaException("未填写 fieldName");
                }
                // filedType default string
                String modelInfoType = modelInfo.getType();
                if (StrUtil.isEmpty(modelInfoType)) {
                    modelInfo.setType(ModelTypeEnum.STRING.getValue());
                }
            }
        }
    }
    private static void validAndFillMetaRoot(Meta meta) {
        // 基础信息校验和默认值
        String name = meta.getName();
        if (StrUtil.isBlank(name)) {
            name = "my-generator";
            meta.setName(name);
        }

        String description = meta.getDescription();
        if(StrUtil.isBlank(description)){
            description = "我的模板代码生成器";
            meta.setDescription(description);
        }

        String author = meta.getAuthor();
        if (StrUtil.isEmpty(author)) {
            author = "ziio";
            meta.setAuthor(author);
        }

        String basePackage = meta.getBasePackage();
        if (StrUtil.isBlank(basePackage)) {
            basePackage = "com.ziio";
            meta.setBasePackage(basePackage);
        }

        String version = meta.getVersion();
        if (StrUtil.isEmpty(version)) {
            version = "1.0";
            meta.setVersion(version);
        }

        String createTime = meta.getCreateTime();
        if (StrUtil.isEmpty(createTime)) {
            createTime = DateUtil.now();
            meta.setCreateTime(createTime);
        }
    }

    private static void validAndFillFileConfig(Meta meta) {
        // fileConfig
        Meta.FileConfig fileConfig = meta.getFileConfig();
        if (fileConfig == null) {
            return;
        }
        // sourceRootPath required
        String sourceRootPath = fileConfig.getSourceRootPath();
        if (StrUtil.isBlank(sourceRootPath)) {
            throw new MetaException("未填写 sourceRootPath");
        }
        // inputRootPath default .source + sourceRootPath.getLastPathEle
        String inputRootPath = fileConfig.getInputRootPath();
        String defaultInputRootPath = ".source" + "/" + FileUtil.getLastPathEle(Paths.get(sourceRootPath)).getFileName().toString();
        if (StrUtil.isEmpty(inputRootPath)) {
            fileConfig.setInputRootPath(defaultInputRootPath);
        }
        // outputPath default /generate
        String outputRootPath = fileConfig.getOutputRootPath();
        String defaultOutputRootPath = "generated";
        if (StrUtil.isEmpty(outputRootPath)) {
            fileConfig.setOutputRootPath(defaultOutputRootPath);
        }
        // type default dir
        String fileConfigType = fileConfig.getType();
        if (StrUtil.isEmpty(fileConfigType)) {
            fileConfig.setOutputRootPath(FileTypeEnum.DIR.getValue());
        }
        // fileInfo
        List<Meta.FileConfig.FileInfo> fileInfoList = fileConfig.getFiles();
        if (CollectionUtil.isNotEmpty(fileInfoList)) {
            for (Meta.FileConfig.FileInfo fileInfo : fileInfoList) {
                // FILE.GROUP skip validate
                if(fileInfo.getType().equals(FileTypeEnum.GROUP.getValue())){
                    continue;
                }
                // inputPath required
                String inputPath = fileInfo.getInputPath();
                if (StrUtil.isBlank(inputPath)) {
                    throw new MetaException("未填写 fileInfo-inputPath");
                }
                // outputPath default inputPath
                String outputPath = fileInfo.getOutputPath();
                if (StrUtil.isEmpty(outputPath)) {
                    fileInfo.setOutputPath(inputPath);
                }
                // type default file (.java) , or dir
                String fileInfoType = fileInfo.getType();
                if (StrUtil.isBlank(fileInfoType)) {
                    // no file suffix
                    if (StrUtil.isBlank(FileUtil.getSuffix(inputPath))) {
                        fileInfo.setType(FileTypeEnum.DIR.getValue());
                    } else {
                        fileInfo.setType(FileTypeEnum.FILE.getValue());
                    }
                }
                // generateType default static (suffix not ftl) , or dynamic
                String generateType = fileInfo.getType();
                if (StrUtil.isBlank(generateType)) {
                    // no file suffix
                    if (inputPath.endsWith(".ftl")) {
                        fileInfo.setGenerateType(FileGenerateTypeEnum.DYNAMIC.getValue());
                    } else {
                        fileInfo.setGenerateType(FileGenerateTypeEnum.STATIC.getValue());
                    }
                }
            }
        }
    }

}
