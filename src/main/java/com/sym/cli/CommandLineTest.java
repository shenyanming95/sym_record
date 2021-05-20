package com.sym.cli;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.junit.Test;

/**
 * @author shenyanming
 * Created on 2021/5/19 14:09
 */
public class CommandLineTest {

    @Test
    public void test01() {
        System.out.println(buildOptions().getOptions().size());
    }

    @Test
    public void test02() throws ParseException {
        String[] args = {"-h", "127.0.0.1", "-t", "game"};
        Options options = buildOptions();
        CommandLineParser parser = new DefaultParser();
        CommandLine commandLine = parser.parse(options, args);

        System.out.println(commandLine.getOptionValue("h"));
        HelpFormatter hf = new HelpFormatter();
        hf.printHelp("app", options, true);
    }

    private Options buildOptions() {
        Options options = new Options();

        Option opt = new Option("t", "topic", true, "topic name");
        options.addOption(opt);

        opt = new Option("h", "host", true, "host name");
        options.addOption(opt);

        return options;
    }
}
