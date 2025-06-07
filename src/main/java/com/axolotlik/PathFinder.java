package com.axolotlik;

import com.axolotlik.exceptions.InvalidInputException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class PathFinder {
    private final FlagsExecutor flags;

    public PathFinder(FlagsExecutor flags) {
        this.flags = flags;
    }

    public Set<Path> findAllPaths() throws IOException, InvalidInputException {
        Set<Path> paths = new HashSet<>();

        Set<String> extensions;
        Set<String> files;
        Set<String> includeDirectories;
        Set<String> excludeDirectories;
        if (!flags.getTypes().isEmpty()) extensions = flags.getTypes();
        else {
            extensions = null;
        }
        if (!flags.getFiles().isEmpty()) files = flags.getFiles();
        else {
            files = null;
        }
        if (!flags.getIncludeDir().isEmpty()) includeDirectories = flags.getIncludeDir();
        else {
            includeDirectories = null;
        }
        if (!flags.getExcludeDir().isEmpty()) excludeDirectories = flags.getExcludeDir();
        else {
            excludeDirectories = null;
        }

        if (extensions == null && files == null && includeDirectories == null) {
            throw new InvalidInputException("You must specify either --types, --files, or --dir");
        }

        if (files != null) {
            try (var walker = Files.walk(Path.of("."))) {
                walker.filter(Files::isRegularFile)
                        .filter(path -> files.contains(path.getFileName().toString()))
                        .forEach(paths::add);
            }
        }

        if (extensions != null || includeDirectories != null || excludeDirectories != null) {
            try (var walker = Files.walk(Path.of("."))) {
                walker.filter(Files::isRegularFile)
                        .filter(path -> matchesTypes(path, extensions))
                        .filter(path -> isInIncludeDirs(path, includeDirectories))
                        .filter(path -> isInExcludeDirs(path, excludeDirectories))
                        .forEach(paths::add);
            }
        }

        return paths;
    }

    private boolean matchesTypes(Path path, Set<String> types) {
        if (types == null) return true;
        for (String t : types) {
            if (path.getFileName().toString().endsWith(t)) return true;
        }
        return false;
    }

    private boolean isInIncludeDirs(Path file, Set<String> directories) {
        if (directories == null) return true;
        for (Path part : file.toAbsolutePath()) {
            if (directories.contains(part.getFileName().toString())) {
                return true;
            }
        }
        return false;
    }

    private boolean isInExcludeDirs(Path file, Set<String> directories) {
        if (directories == null) return true;
        for (Path part : file.toAbsolutePath()) {
            if (directories.contains(part.getFileName().toString())) {
                return false;
            }
        }
        return true;
    }
}
