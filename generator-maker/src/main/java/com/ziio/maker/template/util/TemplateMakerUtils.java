package com.ziio.maker.template.util;

import cn.hutool.core.util.StrUtil;
import com.ziio.maker.meta.Meta;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 模板制作工具类
 */
public class TemplateMakerUtils {

        public static List<Meta.FileConfig.FileInfo> removeGroupFilesFromRoot(List<Meta.FileConfig.FileInfo> fileInfoList){
            // 获取所有分组
            List<Meta.FileConfig.FileInfo> groupFileInfoList = fileInfoList.stream()
                    .filter(fileInfo -> StrUtil.isNotBlank(fileInfo.getGroupKey()))
                    .collect(Collectors.toList());

            // 获取所有分组内的文件列表
            List<Meta.FileConfig.FileInfo> allGroupInnerFilesList = groupFileInfoList.stream()
                    .flatMap(fileInfo -> fileInfo.getFiles().stream())
                    .collect(Collectors.toList());

            // 获取所有分组内文件输入路径集合
            Set<String> allInputPathsSet = allGroupInnerFilesList.stream()
                    .map(Meta.FileConfig.FileInfo::getInputPath)
                    .collect(Collectors.toSet());

            // 移除所有外层 set 中的文件
            List<Meta.FileConfig.FileInfo> newFileInfoList = fileInfoList.stream()
                    .filter(fileInfo -> !allInputPathsSet.contains(fileInfo.getInputPath()))
                    .collect(Collectors.toList());

            return newFileInfoList;
        }

}
