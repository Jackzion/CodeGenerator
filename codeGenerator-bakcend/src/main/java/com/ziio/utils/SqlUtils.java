package com.ziio.utils;

import org.apache.commons.lang3.StringUtils;

public class SqlUtils {

    /**
     * 校验排序字段是否合法 (防止 sql 注入)
     * @param sortFields
     * @return
     */
    public static boolean validSortField(String sortFields){
        if(StringUtils.isBlank(sortFields)){
            return false;
        }
        return !StringUtils.containsAny(sortFields,"=","(",")","");
    }

}
