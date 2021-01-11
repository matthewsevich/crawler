package by.matusevich.csv;

import by.matusevich.model.ResultsOfParsing;
import by.matusevich.model.ResultsOfParsingTop10;

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

    public static ResultsOfParsingTop10 readResultsTop10Csv(String filename) {

        ResultsOfParsingTop10 results = null;
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(filename))) {
            results = (ResultsOfParsingTop10) inputStream.readObject();
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }
        return results;
    }
}

