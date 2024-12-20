package com.ziio.maker.meta;

import cn.hutool.core.io.resource.ResourceUtil;
import cn.hutool.json.JSONUtil;

public class MetaManager {
    private static volatile Meta meta;

    private MetaManager(){
        // 私有构造函数 ， 防外部调用
    }

    public static Meta getSingleInstance(){
        if(meta == null){
            synchronized (MetaManager.class){
                if(meta == null){
                    meta = initMeta();
                }
            }
        }
        return meta;
    }

    private static Meta initMeta(){
        String metaJson = ResourceUtil.readUtf8Str("springboot-init-meta.json");
        Meta newMeta = JSONUtil.toBean(metaJson, Meta.class);
        Meta.FileConfig fileConfig = newMeta.getFileConfig();
        MetaValidator.doValidate(newMeta);
        return newMeta;
    }

    public static void main(String[] args) {
        Meta singleInstance = MetaManager.getSingleInstance();
        System.out.println(singleInstance);
    }
}
