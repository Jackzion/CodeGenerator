package com.ziio.maker.template.model;

import lombok.Data;

import java.util.List;

@Data
public class TemplateMakerFileConfig {

    private List<FileInfoConfig> files;

    @Data
    public static class FileInfoConfig {

        private String path;

        private List<FileFilterConfig> fileFilterConfigs;

    }

}
