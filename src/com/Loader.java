package com;

import lombok.NoArgsConstructor;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

@NoArgsConstructor
public class Loader {

    public ArrayList<Node> loadData(String fileName) throws FileNotFoundException {
        File file = new File(fileName);
        Scanner scanner = new Scanner(file);
        ArrayList<Node> data = new ArrayList<>();
        String nextValue = null;
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if ("NODE_COORD_SECTION".equals(line)) {
                while (scanner.hasNextLine()) {
                    nextValue = scanner.nextLine();
                    if (!Objects.equals(nextValue, "EOF") && !nextValue.isBlank()) {
                        String[] formattedLine = nextValue.trim().split("\\s+");
                        Node newNode = new Node(Integer.parseInt(formattedLine[0]),
                                Double.parseDouble(formattedLine[1]),
                                Double.parseDouble(formattedLine[2]));
                        data.add(newNode);
                    }
                }
            }
        }
        scanner.close();
        return data;
    }
}
