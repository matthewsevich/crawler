package by.matusevich;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Crawler {
    private static final int URL_LIMIT = 100;
    private static final int DEPTH_LIMIT = 3;

    private Queue<String> queue = new LinkedList<>();
    private Map<String, Integer> marked = new HashMap<>();

    private static String[] terms = new String[]{"Java", "regex"};
//    private static String[] terms = new String[]{"Tesla", "Musk", "Gigafactory", "Elon Mask"};

    private static Map<String, Integer> termMap = new HashMap<>();
    private Map<Integer, String> amountOfTermPerUrl = new TreeMap<>();

    private Map<String, ArrayList<Integer>> arrayOfTermsPerUrl = new HashMap<>();
    private int[] top10 = new int[10];

    static int crawledCount = 0;

    public void crawl(String initialSeedUrl) {
        queue.add(initialSeedUrl);
        marked.put(initialSeedUrl, 1);
        arrayOfTermsPerUrl.put(initialSeedUrl, new ArrayList<>(terms.length));

        while (!queue.isEmpty() && crawledCount <= URL_LIMIT) {
            crawledCount++;
            String crawledUrl = queue.poll();
            System.out.println("\n=== Site crawled on level: " + marked.get(crawledUrl) + " " + crawledUrl + " ===");

            Document site = null;
            try {
                site = Jsoup.connect(crawledUrl).get();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Elements links = site.select("a[href]");
            String siteText = site.wholeText();

            ArrayList<Integer> integers = new ArrayList<>();
            for (String term : terms
            ) {
                integers.add(searchOnlyOneTerm(siteText, term));
            }
            int sum = 0;
            for (Integer i : integers
            ) {
                sum += i;
            }

            arrayOfTermsPerUrl.put(crawledUrl, integers);

            if (marked.get(crawledUrl) == DEPTH_LIMIT) {
                continue;
            }
            for (Element link : links) {
                String urlToFind = link.attr("abs:href");
                if ((urlToFind.contains(".png")) || urlToFind.contains(".js") || urlToFind.contains(".jpeg") || urlToFind.contains(".css")
                        || urlToFind.contains(".jpg") || urlToFind.contains(".xml") || urlToFind.contains(".dtd")) {
                    continue;
                }
                if (!marked.containsKey(urlToFind)) {
                    int level = marked.get(crawledUrl) + 1;
                    marked.put(urlToFind, level);
                    queue.add(urlToFind);
                }
            }
        }
    }

    public void showResults() {
        String sb = "\n\nResults" +
                "\n\tweb sites crawled: " + marked.size() + "\n" +
                "\turl visited " + crawledCount +
                "\n\tamount " + amountOfTermPerUrl.size();
        System.out.println(sb);
        for (Map.Entry<String, Integer> entry : termMap.entrySet()
        ) {
            System.out.println(entry.getKey() + "\t\t\t" + entry.getValue());
        }

        for (Map.Entry<String, ArrayList<Integer>> entry : arrayOfTermsPerUrl.entrySet()
        ) {
            System.out.print(entry.getKey());
            entry.getValue().forEach(i -> System.out.print("\t" + i));
            System.out.println();

        }
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
        crawler.crawl("https://www.javatpoint.com/java-regex");
//            crawl("https://en.wikipedia.org/wiki/Elon_Musk");
        crawler.showResults();
        long stopTime = System.currentTimeMillis();
        System.out.println(stopTime - startTime);
    }
}