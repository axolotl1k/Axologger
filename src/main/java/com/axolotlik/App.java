package com.axolotlik;

import com.axolotlik.exceptions.InvalidInputException;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

public class App {
    public static void main(String[] args) {
        List<String> arguments = List.of(args);
        try {
            ArgsReader.validateArgs(arguments);
            if (arguments.contains("--version")) {
                System.out.println("Axologger v1.0");
                return;
            }
            if (arguments.contains("--help")) {
                String lang = "en";
                if (arguments.indexOf("--help") + 1 < arguments.size()
                && !arguments.get(arguments.indexOf("--help") + 1).startsWith("-")
                ) {
                    lang = arguments.get(arguments.indexOf("--help") + 1);
                }
                try (InputStream helpFile = App.class.getResourceAsStream("/help_" + lang + ".txt")) {
                    new BufferedReader(new InputStreamReader(helpFile))
                            .lines()
                            .forEach(System.out::println);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
                return;
            }
            FlagsExecutor flagsExecutor = arguments.contains("--prop-file")
                    ? ArgsReader.read(arguments.get(arguments.indexOf("--prop-file") + 1))
                    : ArgsReader.read(arguments);
            flagsExecutor.executeFlags();
        } catch (InvalidInputException e) {
            System.err.println(e.getMessage());
            System.exit(1);
        } catch (Exception e) {
            System.err.println("Unexpected error: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
    }
}
