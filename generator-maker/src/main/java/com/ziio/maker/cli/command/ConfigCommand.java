package com.ziio.maker.cli.command;

import cn.hutool.core.util.ReflectUtil;
import com.ziio.maker.medel.DataModel;
import picocli.CommandLine;

import java.lang.reflect.Field;

/**
 * 查看参数信息 command
 */
@CommandLine.Command(name = "config", description = "查看参数信息", mixinStandardHelpOptions = true)
public class ConfigCommand implements Runnable {

    @Override
    public void run() {
        // 通过反射获取
        System.out.println("查看参数信息");
        Field[] fields = ReflectUtil.getFields(DataModel.class);
        for(Field field : fields){
            System.out.println("字段名称: " + field.getName());
            System.out.println("字段类型: " + field.getType());
            System.out.println("---");
        }
    }
}
