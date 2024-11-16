package com.ziio.maker.cli.command;

import picocli.CommandLine;

@CommandLine.Command(name = "cbd" , mixinStandardHelpOptions = true)
public class CommandExecutor implements Runnable {

    private final CommandLine commandLine;

    {
        commandLine = new CommandLine(this)
                .addSubcommand(new GenerateCommand())
                .addSubcommand(new ConfigCommand())
                .addSubcommand(new ListCommand());
    }

    @Override
    public void run() {
        // 不输入子命令时候， 给友好提示
        System.out.println("please input specific command or input -- help to get the command list");
    }

    public Integer doExecute(String[] args){
        // 向下传递解析命令
        return commandLine.execute(args);
    }
}
