package Evolutionary;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Logger {
    String file = "pop2.tsv";
    FileWriter fileWriter;
    PrintWriter printWriter;

    public Logger() throws IOException {
        fileWriter = new FileWriter(file);
        printWriter = new PrintWriter(fileWriter, true);
    }
}
