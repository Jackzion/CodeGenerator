package com.ziio.maker.meta;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 代码生成器元信息
 */
@NoArgsConstructor
@Data
public class Meta {

    private String name;
    private String description;
    private String basePackage;
    private String version;
    private String author;
    private String createTime;
    private FileConfig fileConfig;
    private ModelConfig modelConfig;

    // 模板文件设置
    @NoArgsConstructor
    @Data
    public static class FileConfig {
        private String inputRootPath;   // template.copy path
        private String outputRootPath;  // file output path
        private String sourceRootPath;  // template.source path
        private String type;
        private List<FileInfo> files;

        @NoArgsConstructor
        @Data
        public static class FileInfo {
            private String inputPath;
            private String outputPath;
            private String type;
            private String generateType;
            private String condition;
            private String groupKey;
            private String groupName;
            private List<FileInfo> files;
        }
    }

    // Command Model 填充设置
    @NoArgsConstructor
    @Data
    public static class ModelConfig {
        private List<ModelInfo> models;

        @NoArgsConstructor
        @Data
        public static class ModelInfo {
            private String fieldName;
            private String type;
            private String description;
            private Object defaultValue;
            private String abbr;
            private String groupKey;
            private String groupName;
            private List<ModelInfo> models;
            private String condition;   // trigger , input -loop => other models required input
            private String allArgsStr;  // all args commandLine execute next
        }
    }
}
