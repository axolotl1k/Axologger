package com.axolotlik;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.charset.MalformedInputException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Set;

public class FileWriter {
    private Set<Path> files;
    private String out;
    private boolean append;

    public FileWriter(Set<Path> files, String out, boolean append) {
        this.files = files;
        this.out = out;
        this.append = append;
    }

    public void write() throws IOException {
        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(out), StandardOpenOption.CREATE,
                append ? StandardOpenOption.APPEND : StandardOpenOption.TRUNCATE_EXISTING)) {
            for (Path file : files) {
                StringBuilder buffer = new StringBuilder();
                try {
                    String ext = file.toString().substring(file.toString().lastIndexOf('.') + 1);
                    buffer.append("## ").append(file).append(System.lineSeparator());
                    buffer.append("```").append(ext).append(System.lineSeparator());

                    try (var reader = Files.newBufferedReader(file, StandardCharsets.UTF_8)) {
                        String line;
                        while ((line = reader.readLine()) != null) {
                            buffer.append(line).append(System.lineSeparator());
                        }
                    }

                    buffer.append("```").append(System.lineSeparator()).append(System.lineSeparator());

                    writer.write(buffer.toString());
                }
                catch (MalformedInputException e) {
                    System.err.println("Skipping unreadable file (probably binary): " + file.getFileName());
                }
            }
        }
    }
}
