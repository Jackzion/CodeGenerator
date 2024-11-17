# ${name}

> ${description}
>
> author：${author}
>
> based on zeenoh [Code Generator] , thank for your using

Dynamically generate the code you need for you project through interactive command line input

## Instruction

Execute window-bat in the root directory
---
generator generate <#list modelConfig.models as modelInfo>-${modelInfo.abbr} </#list>
---

### parameter description
<#list modelConfig.models as modelInfo>
    ${modelInfo?index + 1}）${modelInfo.fieldName}

    类型：${modelInfo.type}

    描述：${modelInfo.description}

    默认值：${modelInfo.defaultValue?c}

    缩写： -${modelInfo.abbr}


</#list>