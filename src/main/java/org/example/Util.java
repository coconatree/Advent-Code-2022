package org.example;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class Util {

    enum LogLevel {
        WARN,
        INFO,
        ERROR;

        public String value() {
            switch (this) {
                case INFO -> {
                    return "INFO";
                }
                case WARN -> {
                    return "WARN";
                }
                case ERROR -> {
                    return "ERROR";
                }
                default -> {
                    return "DEFAULT";
                }
            }
        }
    }

    public static void log(LogLevel level, String message) {
        System.out.printf("[%s] %s%n", level.value(), message);
    }

    public static void shutdownProgramme() {
        log(
                LogLevel.INFO,
                "Shutting down the programme"
        );
        System.exit(-1);
    }

    public static List<String> readFileToString(String filePath) {

        List<String> results = new LinkedList<>();
        BufferedReader bufferedReader = null;

        try {
            FileReader fileReader = new FileReader(filePath);
            bufferedReader = new BufferedReader(fileReader);

            String line = null;

            while ((line = bufferedReader.readLine()) != null) {
                results.add(line);
            }
        } catch (FileNotFoundException error) {
            log(
                    LogLevel.ERROR,
                    String.format("File on the given path %s is not found", filePath)
            );
            shutdownProgramme();
        } catch (IOException error) {
            log(
                    LogLevel.ERROR,
                    String.format("Error reading the line from the file %s with message %s", filePath, error.getMessage())
            );
            shutdownProgramme();
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException error) {
                    log(
                            LogLevel.ERROR,
                            String.format("An IO error occurred while closing the buffer reader %s", error.getMessage())
                    );
                    shutdownProgramme();
                }
            }
        }
        return results;
    }
}
