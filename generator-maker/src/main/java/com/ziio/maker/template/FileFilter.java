package com.ziio.maker.template;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.io.FileUtil;
import com.ziio.maker.template.enums.FileFilterRangeEnum;
import com.ziio.maker.template.enums.FileFilterRuleEnum;
import com.ziio.maker.template.model.FileFilterConfig;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

public class FileFilter {

    /**
     * 单个文件过滤
     * @param fileFilterConfigs 过滤规则
     * @param file 单个文件
     * @return 是否保留
     */
    public static boolean doSingleFileFilter(List<FileFilterConfig> fileFilterConfigs , File file){
        String fileName = file.getName();
        String fileContent = FileUtil.readUtf8String(file);

        // default return
        boolean result = true;
        if(CollectionUtil.isEmpty(fileFilterConfigs)){
            return true;
        }

        for(FileFilterConfig fileFilterConfig : fileFilterConfigs){
            String range = fileFilterConfig.getRange();
            String rule = fileFilterConfig.getRule();
            String value = fileFilterConfig.getValue();

            FileFilterRangeEnum fileFilterRangeEnum = FileFilterRangeEnum.getEnumByValue(range);
            if(fileFilterRangeEnum == null){
                continue;
            }

            // 要过滤的原内容
            String content = fileName;
            switch (fileFilterRangeEnum){
                case FILE_NAME:
                    content = fileName;
                    break;
                case FILE_CONTENT:
                    content = fileContent;
                    break;
                default:
            }

            // 按规则对内容匹配
            FileFilterRuleEnum ruleEnum = FileFilterRuleEnum.getEnumByValue(rule);
            if(ruleEnum == null){
                continue;
            }
            switch (ruleEnum){
                case CONTAINS:
                    result = content.contains(value);
                    break;
                case STARTS_WITH:
                    result = content.startsWith(value);
                    break;
                case REGEX:
                    result = content.matches(value);
                    break;
                case ENDS_WITH:
                    result = content.endsWith(value);
                    break;
                case EQUALS:
                    result = content.equals(value);
                    break;
                default:
            }
            // 有一个不满足，直接返回
            if(!result){
                return false;
            }
        }
        // 都满足
        return true;
    }

    public static List<File> doFileFilter(String filePath , List<FileFilterConfig> fileFilterConfigList){
        // 根据路径获取所有文件
        List<File> files = FileUtil.loopFiles(filePath);
        // 过滤
        return files.stream()
                .filter(file -> doSingleFileFilter( fileFilterConfigList , file))
                .collect(Collectors.toList());
    }

}
