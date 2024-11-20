package com.ziio.maker.template;

import com.ziio.maker.meta.Meta;
import com.ziio.maker.template.model.TemplateMakerFileConfig;
import com.ziio.maker.template.model.TemplateMakerModelConfig;
import org.junit.Test;

import java.io.File;
import java.util.Arrays;
import java.util.List;

public class TemplateMakerTest {

    @Test
    public void testMakeTemplateBug1() {
        Meta meta = new Meta();
        meta.setName("acm-template-generator");
        meta.setDescription("ACM 示例模板生成器");

        String projectPath = System.getProperty("user.dir");
        String originProjectPath = new File(projectPath).getParent() + File.separator + "code-producer-demo-projects/acm-template-pro";

        // 文件参数配置
        String inputFilePath1 = "src/com/ziio/acm/pack";
        TemplateMakerFileConfig templateMakerFileConfig = new TemplateMakerFileConfig();
        TemplateMakerFileConfig.FileInfoConfig fileInfoConfig1 = new TemplateMakerFileConfig.FileInfoConfig();
        fileInfoConfig1.setPath(inputFilePath1);
        templateMakerFileConfig.setFiles(Arrays.asList(fileInfoConfig1));

        // 模型参数配置
        TemplateMakerModelConfig templateMakerModelConfig = new TemplateMakerModelConfig();
        TemplateMakerModelConfig.ModelInfoConfig modelInfo = new TemplateMakerModelConfig.ModelInfoConfig();
        modelInfo.setFieldName("className");
        modelInfo.setType("String");
        String replacedText = "MainTemplate";
        modelInfo.setReplaceText(replacedText);
        List<TemplateMakerModelConfig.ModelInfoConfig> modelInfoConfigList = Arrays.asList(modelInfo);
        templateMakerModelConfig.setModels(modelInfoConfigList);

        long id = TemplateMaker.makeTemplate(meta, originProjectPath, templateMakerFileConfig, templateMakerModelConfig, 1858691755625439232L);
        System.out.println(id);
    }
}