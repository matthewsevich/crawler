package by.matusevich.csv;

import by.matusevich.model.ResultsOfParsing;
import by.matusevich.model.ResultsOfParsingTop10;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.*;

public class CsvWriterTest {

    @Test
    public void saveResults() {
        CsvWriter.saveResults(new ResultsOfParsing(), "testResults.csv");
        File file = new File("testResults.csv");
        assertNotNull(file);
        assertTrue(file.delete());
    }

    @Test
    public void testSaveResults() {
        CsvWriter.saveResults(new ResultsOfParsingTop10(), "testResultsTop10.csv");
        File file = new File("testResultsTop10.csv");
        assertNotNull(file);
        assertTrue(file.delete());
    }
}