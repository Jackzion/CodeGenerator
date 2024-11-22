package com.ziio.model.enums;

import lombok.Getter;
import org.apache.commons.lang3.ObjectUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 文件上传业务类型 （区分 path）
 */
@Getter
public enum FileUploadEnum {
    USER_AVATAR("用户头像", "user_avatar"),
    GENERATOR_PICTURE("生成器图片", "generator_picture"),
    GENERATOR_DIST("生成器产物包", "generator_dist"),
    GENERATOR_MAKE_TEMPLATE("生成器制作模板文件", "generator_make_template");

    private final String text;

    private final String value;

    FileUploadEnum(String text, String value) {
        this.text = text;
        this.value = value;
    }

    /**
     * 获取值列表
     *
     * @return
     */
    public static List<String> getValues() {
        return Arrays.stream(values()).map(item -> item.value).collect(Collectors.toList());
    }

    /**
     * 根据 value 获取枚举
     *
     * @param value
     * @return
     */
    public static FileUploadEnum getEnumByValue(String value) {
        if (ObjectUtils.isEmpty(value)) {
            return null;
        }
        for (FileUploadEnum anEnum : FileUploadEnum.values()) {
            if (anEnum.value.equals(value)) {
                return anEnum;
            }
        }
        return null;
    }
}
