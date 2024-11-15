package com.ziio.cli.command;

import cn.hutool.core.bean.BeanUtil;
import com.ziio.generator.MainGenerator;
import com.ziio.medel.MainTemplateModel;
import lombok.Data;
import picocli.CommandLine;

import java.util.concurrent.Callable;

/**
 * 生成代码 command
 */
@CommandLine.Command(name = "generate" , description = "生成代码", mixinStandardHelpOptions = true)
@Data
public class GenerateCommand implements Callable<Integer> {

    @CommandLine.Option(names = {"-l" , "--loop"} , arity = "0..1" , description = "is it loop?" , interactive = true , echo = true)
    private boolean loop;

    @CommandLine.Option(names = {"-a" , "--author"} , arity = "0..1" , description = "who is author" , interactive = true , echo = true)
    private String author = "zeenoh";

    @CommandLine.Option(names = {"-o" , "--outPutText"} , arity = "0..1" , description = "the result text output" , interactive = true , echo = true)
    private String outputText = "sun = ";

    @Override
    public Integer call() throws Exception {
        MainTemplateModel model = new MainTemplateModel();
        model.setOutputText(this.outputText);
        model.setAuthor(this.author);
        model.setLoop(this.loop);
        System.out.println("配置信息 ：" + model);
        MainGenerator.doGenerate(model);
        return 0;
    }
}
