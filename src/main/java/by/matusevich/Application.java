package by.matusevich;

import by.matusevich.csv.CsvWriter;
import by.matusevich.model.ResultsOfParsing;
import by.matusevich.model.ResultsOfParsingTop10;
import by.matusevich.utility.CrawlerConsoleReader;

public class Application {

    private static String url = CrawlerConsoleReader.readUrl();
    private static String[] terms = CrawlerConsoleReader.readTerms();

    public static void main(String[] args) {

        long startTime = System.currentTimeMillis();
        Crawler crawler = new Crawler();
        crawler.crawl(url, terms);
        ResultsOfParsing results = crawler.getAllResults();
        ResultsOfParsingTop10 top10 = crawler.top10ResultsToString();
        long finalTime = System.currentTimeMillis() - startTime;
        CsvWriter.saveResults(results, "result.csv");
        CsvWriter.saveResults(top10, "top10.csv");
        System.out.println("completed " + finalTime);
    }
}
