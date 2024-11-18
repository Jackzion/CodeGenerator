package ${basePackage}.cli.command;

import cn.hutool.core.bean.BeanUtil;
import ${basePackage}.generator.MainGenerator;
import ${basePackage}.model.DataModel;
import lombok.Data;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine;

import java.util.concurrent.Callable;

<#-- 生成选项 -->
<#macro generateOption indent modelInfo>
    ${indent}@Option(names = {<#if modelInfo.abbr??>"-${modelInfo.abbr}", </#if>"--${modelInfo.fieldName}"}, arity = "0..1", <#if modelInfo.description??>description = "${modelInfo.description}", </#if>interactive = true, echo = true)
    ${indent}private ${modelInfo.type} ${modelInfo.fieldName}<#if modelInfo.defaultValue??> = ${modelInfo.defaultValue?c}</#if>;
</#macro>

<#-- 生成下级命令调用 -->
<#macro generateCommand indent modelInfo>
    ${indent}System.out.println("输入${modelInfo.groupName}配置：");
    ${indent}CommandLine commandLine = new CommandLine(${modelInfo.type}Command.class);
    ${indent}commandLine.execute(${modelInfo.allArgsStr});
</#macro>

/**
* 生成代码 command
*/
@Command(name = "generate", description = "生成代码", mixinStandardHelpOptions = true)
@Data
public class GenerateCommand implements Callable<Integer> {
<#list modelConfig.models as modelInfo>

    <#-- 有分组 -->
    <#if modelInfo.groupKey??>
        /**
        * ${modelInfo.groupName}
        */
        static DataModel.${modelInfo.type} ${modelInfo.groupKey} = new DataModel.${modelInfo.type}();

    <#-- 根据分组生成下级内部命令类 -->
        @Command(name = "${modelInfo.groupKey}")
        @Data
        public static class ${modelInfo.type}Command implements Runnable {
        <#list modelInfo.models as subModelInfo>
            <@generateOption indent="        " modelInfo=subModelInfo />
        </#list>

        @Override
        public void run() {
        <#list modelInfo.models as subModelInfo>
            ${modelInfo.groupKey}.${subModelInfo.fieldName} = ${subModelInfo.fieldName};
        </#list>
        }
        }
    <#else>
    <#-- 无分组  ， 直接生成 option -->
        <@generateOption indent="    " modelInfo=modelInfo />
    </#if>
</#list>

    <#-- 生成调用方法 -->
    public Integer call() throws Exception {
    <#list modelConfig.models as modelInfo>
        <#if modelInfo.groupKey??>
        <#if modelInfo.condition??>
        <#-- 上级为 true  ，开始调用下级 -->
        if (${modelInfo.condition}) {
        <@generateCommand indent="            " modelInfo=modelInfo />
        }
        <#else>
        <#-- condition is null 默认调用下级 -->
        <@generateCommand indent="      " modelInfo=modelInfo />
        </#if>
        </#if>
        </#list>
        <#-- 填充数据模型对象 -->
        DataModel dataModel = new DataModel();
        BeanUtil.copyProperties(this, dataModel);
        <#list modelConfig.models as modelInfo>
        <#if modelInfo.groupKey??>
        dataModel.${modelInfo.groupKey} = ${modelInfo.groupKey};
        </#if>
        </#list>
        MainGenerator.doGenerate(dataModel);
        return 0;
    }
}