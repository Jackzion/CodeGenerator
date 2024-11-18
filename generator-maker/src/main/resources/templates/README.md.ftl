# ${name}

> ${description}
>
> author：${author}
>
> based on zeenoh [Code Generator] , thank for your using

Dynamically generate the code you need for you project through interactive command line input

## Instruction

Execute window-bat in the root directory

<#-- todo: README.ftl change -->
<#--
---
generator generate <#list modelConfig.models as modelInfo>-${modelInfo.abbr?c} </#list>
---

### parameter description
<#list modelConfig.models as modelInfo>
    ${modelInfo?index + 1}）${modelInfo.fieldName?c}

    类型：${modelInfo.type?c}

    描述：${modelInfo.description?c}

    默认值：${modelInfo.defaultValue?c}

    缩写： -${modelInfo.abbr?c}


</#list>
-!>