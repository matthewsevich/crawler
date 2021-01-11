package by.matusevich.csv;

import by.matusevich.model.ResultsOfParsing;
import by.matusevich.model.ResultsOfParsingTop10;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class CsvWriter{

    public static void saveResults(ResultsOfParsing results, String filename) {
        FileOutputStream fos = null;

        try {
            fos = new FileOutputStream(filename);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        try (ObjectOutputStream oos = new ObjectOutputStream(fos)) {

            oos.writeObject(results);
            oos.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void saveResults(ResultsOfParsingTop10 results, String filename) {
        FileOutputStream fos = null;

        try {
            fos = new FileOutputStream(filename);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        try (ObjectOutputStream oos = new ObjectOutputStream(fos)) {

            oos.writeObject(results);
            oos.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
