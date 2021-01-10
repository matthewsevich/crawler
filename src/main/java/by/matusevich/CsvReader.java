package by.matusevich;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class CsvReader {
    public static ResultsOfParsing readResultsCsv(String filename) {

        ResultsOfParsing results = null;
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(filename))) {
            results = (ResultsOfParsing) inputStream.readObject();
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }
        return results;
    }
}

