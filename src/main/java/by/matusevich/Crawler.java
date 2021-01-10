package by.matusevich;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Crawler {
    private static final int URL_LIMIT = 100;
    private static final int DEPTH_LIMIT = 5;
    private static final String INITIAL_SEED_URL = "https://www.javatpoint.com/java-regex";
//    private static final String INITIAL_SEED_URL= "https://en.wikipedia.org/wiki/Elon_Musk";

    private static final Logger logger = LoggerFactory.getLogger(Crawler.class);
    private Queue<String> queue = new LinkedList<>();
    private Map<String, Integer> marked = new HashMap<>();

    private static String url = CrawlerConsoleReader.readUrl();
    private String[] terms = CrawlerConsoleReader.readTerms();
//    private static String[] terms = new String[]{"Java", "regex", "java", "Regex"};
//    private static String[] terms = new String[]{"Tesla", "Musk", "Gigafactory", "Elon Mask"};

    private static Map<String, Integer> termMap = new HashMap<>();

    private static Map<String, ArrayList<Integer>> listOfTermsPerUrl = new HashMap<>();
    private List<Integer> top10values = new ArrayList<>();
    private List<String> top10names = new ArrayList<>();
//    private Map<String, Integer> top10AnimeBattles = new HashMap<>();

    int crawledCount = 0;

    public void crawl(String initialSeedUrl) {
        queue.offer(initialSeedUrl);
        marked.put(initialSeedUrl, 0);
        listOfTermsPerUrl.put(initialSeedUrl, new ArrayList<>(terms.length));
        logger.info("process started");

        while (!queue.isEmpty() && crawledCount < URL_LIMIT) {
            crawledCount++;
            String crawledUrl = queue.poll();
            int currentDepth = marked.get(crawledUrl);
//            logger.info(String.format("%n=== Site crawled on level: %d %s ===", currentDepth, crawledUrl));

            Document site = null;
            try {
                site = Jsoup.connect(crawledUrl).get();
            } catch (IOException e) {
                logger.error(e.getMessage());
            }
            if (site == null) {
                logger.error("site == null");
                continue;
            }
            Elements links = site.select("a[href]");

            String siteText = site.text();

            ArrayList<Integer> numbersOfTermsFound = new ArrayList<>();
            for (String term : terms
            ) {
                numbersOfTermsFound.add(searchOnlyOneTerm(siteText, term));
            }

            listOfTermsPerUrl.put(crawledUrl, numbersOfTermsFound);

            if (marked.get(crawledUrl) == DEPTH_LIMIT) {
                continue;
            }
            searchUrls(links, currentDepth);
        }
    }

    public void searchUrls(Elements links, int currentDepth) {
        for (Element link : links) {
            String urlToFind = link.attr("abs:href");

//                logger.info(getHostName(urlToFind));
            if ((urlToFind.contains(".png")) || urlToFind.contains(".js") || urlToFind.contains(".jpeg") || urlToFind.contains(".css")
                    || urlToFind.contains(".jpg") || urlToFind.contains(".xml") || urlToFind.contains(".dtd")) {
                continue;
            }
            Map<Integer, String> map = new TreeMap<>();
            map.keySet();
            if (!marked.containsKey(urlToFind)) {
                int level = currentDepth + 1;
                marked.put(urlToFind, level);
                queue.add(urlToFind);
            }
        }
    }

    public void getTop10Results(Map<String, ArrayList<Integer>> fullMap) {
        for (Map.Entry<String, ArrayList<Integer>> entry : fullMap.entrySet()
        ) {
            int sum = 0;
            String keyName = entry.getKey();
            for (Integer number : entry.getValue()
            ) {
                sum += number;
            }
            if (top10values.size() < 10) {
                top10values.add(sum);
                top10names.add(keyName);
            }
            int indexOfMinValue = getIndexOfMinValueFromTop10(top10values);
            if (sum > top10values.get(indexOfMinValue)) {
                top10values.set(indexOfMinValue, sum);
                top10names.set(indexOfMinValue, keyName);
            }
        }
    }

    private void sortArrayLists(List<Integer> values, List<String> names) {
        ArrayList<Integer> newValues = new ArrayList<>(values);
        ArrayList<String> newNames = new ArrayList<>(names);
        for (int i = 0; i <= 9; i++) {
            int indexOfMinValueFromTop10 = getIndexOfMinValueFromTop10(values);
            int minValue = values.get(indexOfMinValueFromTop10);
            String minName = names.get(indexOfMinValueFromTop10);
            newValues.set(9 - i, minValue);
            newNames.set(9 - i, minName);
            values.remove(indexOfMinValueFromTop10);
            names.remove(indexOfMinValueFromTop10);

        }
//        for (int i = 0; i < 9; i++) {
//            top10values.set(i, newValues.get(i));
//            top10names.set(i,newNames.get(i));
//        }
        top10names = newNames;
        top10values = newValues;
    }

    private int getIndexOfMinValueFromTop10(List<Integer> top10values) {
        int minValue = top10values.get(0);
        for (Integer i : top10values
        ) {
            if (minValue > i) {
                minValue = i;
            }
        }
        return top10values.indexOf(minValue);
    }

    public ResultsOfParsing getAllResults() {
        String result = "\n\nResults" +
                "\n\tweb sites crawled: " + marked.size() + "\n" +
                "\turl visited " + crawledCount;
        logger.info(result);

        String termCount = "";
        for (Map.Entry<String, Integer> entry : termMap.entrySet()) {
            termCount += (String.format("%s\t\t\t%d", entry.getKey(), entry.getValue()));
        }

        getTop10Results(listOfTermsPerUrl);
        sortArrayLists(top10values, top10names);
        for (int i = 0; i < 10; i++) {
            System.out.println(top10names.get(i) + " " + listOfTermsPerUrl.get(top10names.get(i)));
        }
        return new ResultsOfParsing(result, termCount, top10values, top10names);


//        for (Map.Entry<String, ArrayList<Integer>> entry : listOfTermsPerUrl.entrySet()) {
//            System.out.print(entry.getKey());
//            entry.getValue().forEach(i -> System.out.print("\t" + i));
//            System.out.println();
//
//        }
    }


    public static int searchOnlyOneTerm(String text, String term) {
        Pattern pattern = Pattern.compile(term);
        Matcher matcher = pattern.matcher(text);
        int countOfTerm = 0;
        while (matcher.find()) {
            countOfTerm++;
            if (termMap.containsKey(term))
                termMap.put(term, termMap.get(term) + 1);
            else
                termMap.put(term, 1);
        }
        return countOfTerm;
    }

    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();
        Crawler crawler = new Crawler();
        crawler.crawl(url);
        ResultsOfParsing results = crawler.getAllResults();
        long finalTime = System.currentTimeMillis() - startTime;
        System.out.println("completed " + finalTime);
        CsvWriter.saveResults(results, "result.csv");
        CsvWriter.saveStatistics(listOfTermsPerUrl, "statistics.csv");
    }
}