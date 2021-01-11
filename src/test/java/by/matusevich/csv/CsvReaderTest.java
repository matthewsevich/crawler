package by.matusevich.csv;

import by.matusevich.model.ResultsOfParsing;
import by.matusevich.model.ResultsOfParsingTop10;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.*;

public class CsvReaderTest {

    @Test
    public void readResultsCsv() {
        String filename = "test.csv";
        CsvWriter.saveResults(new ResultsOfParsing(), filename);
        File file = new File(filename);
        assertNotNull(file);
        assertEquals(file.getName(),filename);
        ResultsOfParsing resultsOfParsing = CsvReader.readResultsCsv(filename);
        assertNotNull(resultsOfParsing);
        assertTrue(file.delete());
    }

    @Test
    public void readResultsTop10Csv() {
        String filename = "test.csv";
        CsvWriter.saveResults(new ResultsOfParsingTop10(), filename);
        File file = new File(filename);
        assertNotNull(file);
        assertEquals(file.getName(),filename);
        ResultsOfParsingTop10 resultsOfParsingTop10 = CsvReader.readResultsTop10Csv(filename);
        assertNotNull(resultsOfParsingTop10);
        assertTrue(file.delete());
    }
}