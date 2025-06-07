package com.axolotlik;

import java.io.IOException;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;

public class FlagsExecutor {
    private Set<String> types;
    private Set<String> includeDir;
    private Set<String> excludeDir;
    private Set<String> files;
    private String out;
    private boolean append;
    private boolean dryRun;

    public FlagsExecutor() {
        types = new HashSet<>();
        includeDir = new HashSet<>();
        excludeDir = new HashSet<>();
        files = new HashSet<>();
        out = "log.md";
        append = false;
        dryRun = false;
    }

    public void executeFlags() throws IOException {
        PathFinder finder = new PathFinder(this);
        Set<Path> paths = finder.findAllPaths();
        if (dryRun) {
            System.out.println("Files to be included:");
            paths.forEach(p -> System.out.println(p.toString()));
            return;
        }
        FileWriter fileWriter = new FileWriter(paths, out, append);
        fileWriter.write();
    }

    public Set<String> getTypes() {
        return types;
    }

    public void setTypes(Set<String> types) {
        this.types = types;
    }

    public Set<String> getIncludeDir() {
        return includeDir;
    }

    public void setIncludeDir(Set<String> includeDir) {
        this.includeDir = includeDir;
    }

    public Set<String> getExcludeDir() {
        return excludeDir;
    }

    public void setExcludeDir(Set<String> excludeDir) {
        this.excludeDir = excludeDir;
    }

    public Set<String> getFiles() {
        return files;
    }

    public void setFiles(Set<String> files) {
        this.files = files;
    }

    public String getOut() {
        return out;
    }

    public void setOut(String out) {
        this.out = out;
    }

    public boolean isAppend() {
        return append;
    }

    public void setAppend(boolean append) {
        this.append = append;
    }

    public boolean isDryRun() {
        return dryRun;
    }

    public void setDryRun(boolean dryRun) {
        this.dryRun = dryRun;
    }
}
