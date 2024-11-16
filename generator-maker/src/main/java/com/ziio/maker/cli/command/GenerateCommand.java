package com.ziio.maker.cli.command;

import cn.hutool.core.bean.BeanUtil;
import com.ziio.maker.generator.file.MainGenerator;
import com.ziio.maker.medel.DataModel;
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
        DataModel model = new DataModel();
        BeanUtil.copyProperties(this,  model);
        System.out.println("配置信息 ：" + model);
        MainGenerator.doGenerate(model);
        return 0;
    }

    public static void main(String[] args) throws Exception {
        GenerateCommand generateCommand = new GenerateCommand();
        generateCommand.call();
    }
}
