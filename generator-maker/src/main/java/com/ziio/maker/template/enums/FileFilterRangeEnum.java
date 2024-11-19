package com.ziio.maker.template.enums;

import cn.hutool.core.util.ObjectUtil;
import lombok.Getter;

@Getter
public enum FileFilterRangeEnum {

    FILE_NAME("文件名称", "fileName"),
    FILE_CONTENT("文件内容", "fileContent");

    private String text;

    private String value;

    FileFilterRangeEnum(String text, String value) {
        this.text = text;
        this.value = value;
    }

    // 根据 value 获取 enum
    public static FileFilterRangeEnum getEnumByValue(String value){
        if(ObjectUtil.isEmpty(value)){
            return null;
        }

        for(FileFilterRangeEnum anEnum : FileFilterRangeEnum.values()){
            if(anEnum.value.equals(value)){
                return anEnum;
            }
        }
        return null;
    }

}