package by.matusevich;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Map;

public class CsvWriter {

    public static void saveResults(ResultsOfParsing results,String filename) {
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

    public static void saveStatistics(Map<String, ArrayList<Integer>> map,String filename) {
        FileOutputStream fos = null;

        try {
            fos = new FileOutputStream(filename);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        try (ObjectOutputStream oos = new ObjectOutputStream(fos)) {

            oos.writeObject(map);
            oos.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
