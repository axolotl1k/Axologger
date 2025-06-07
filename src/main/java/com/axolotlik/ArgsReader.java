package com.axolotlik;

import com.axolotlik.exceptions.InvalidInputException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ArgsReader {
    private static Set<String> allowedFlags = Set.of(
            "--types", "--files", "--dir", "--exclude-dir", "--out",
            "--append", "--dry-run", "--prop-file", "--help", "--version"
    );
    private static Set<String> allowedFlagsWithValues = Set.of(
            "--types", "--files", "--dir", "--exclude-dir", "--out",
            "--prop-file"
    );

    public static void validateArgs(List<String> arguments) throws InvalidInputException {
        if (arguments.isEmpty()) {
            throw new InvalidInputException("Arguments are empty");
        }
        for (int i = 0; i < arguments.size(); i++) {
            if (arguments.get(i).startsWith("-") && !allowedFlags.contains(arguments.get(i))) {
                throw new InvalidInputException("Invalid flag: " + arguments.get(i));
            }
            if (arguments.get(i).startsWith("--")) {
                if (allowedFlagsWithValues.contains(arguments.get(i))) {
                    if (i + 1 >= arguments.size() || arguments.get(i + 1).startsWith("--")) {
                        throw new InvalidInputException("Flag " + arguments.get(i) + " requires a value.");
                    }
                }
            }
        }
    }

    public static FlagsExecutor read(String filename) throws IOException {
        FlagsExecutor flagsExecutor = new FlagsExecutor();
        Path file = Paths.get(filename);
        if (Files.exists(file)) {
            try (var lines = Files.lines(file)) {
                lines.forEach(line -> {
                    String[] splitLine = line.split("=");
                    switch (splitLine[0]) {
                        case "--dry-run":
                            flagsExecutor.setDryRun(true);
                            break;
                        case "--append":
                            flagsExecutor.setAppend(true);
                            break;
                        case "--types":
                            flagsExecutor.setTypes(Set.of(splitLine[1].split(" ")));
                            break;
                        case "--dir":
                            flagsExecutor.setIncludeDir(Set.of(splitLine[1].split(" ")));
                            break;
                        case "--files":
                            flagsExecutor.setFiles(Set.of(splitLine[1].split(" ")));
                            break;
                        case "--exclude-dir":
                            flagsExecutor.setExcludeDir(Set.of(splitLine[1].split(" ")));
                            break;
                        case "--out":
                            flagsExecutor.setOut(splitLine[1]);
                            break;
                    }
                });
            }
        }
        return flagsExecutor;
    }

    public static FlagsExecutor read(List<String> arguments){
        FlagsExecutor flagsExecutor = new FlagsExecutor();
        for (int i = 0; i < arguments.size(); i++) {
            switch (arguments.get(i)) {
                case "--dry-run":
                    flagsExecutor.setDryRun(true);
                    break;
                case "--append":
                    flagsExecutor.setAppend(true);
                    break;
                case "--types":
                    flagsExecutor.setTypes(getArgs(i, arguments));
                    break;
                case "--dir":
                    flagsExecutor.setIncludeDir(getArgs(i, arguments));
                    break;
                case "--files":
                    flagsExecutor.setFiles(getArgs(i, arguments));
                    break;
                case "--exclude-dir":
                    flagsExecutor.setExcludeDir(getArgs(i, arguments));
                    break;
                case "--out":
                    flagsExecutor.setOut(arguments.get(i + 1));
                    break;
            }
        }
        return flagsExecutor;
    }

    private static Set<String> getArgs(int i, List<String> arguments) {
        Set<String> args = new HashSet<>();
        while (i + 1 < arguments.size() && !arguments.get(i + 1).equals("-")) {
            args.add(arguments.get(++i));
        }
        return args;
    }
}
