package by.matusevich;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Crawler {
    private static final int URL_LIMIT = 100;

    public static Queue<String> queue = new LinkedList<>();
    public static Map<String, Integer> marked = new HashMap<>();

    public static String regex = "\\b(https?|ftp)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";

    public static String[] terms = new String[]{"Java", "regex", "java", "Regex"};
    public static Map<String, Integer> termMap = new HashMap<>();
    static int i = 1;
    public static void crawl(String urlToCrawl) throws IOException {
        queue.add(urlToCrawl);
        marked.put(urlToCrawl, 1);
        BufferedReader br = null;

        while (!queue.isEmpty()) {
            i++;
            String crawledUrl = queue.poll();
            System.out.println("\n=== Site crawled : " + crawledUrl + " ===");

            if (i > URL_LIMIT)
                return;
            boolean ok = false;
            URL url = null;
            while (!ok) {
                try {
                    url = new URL(crawledUrl);
//                    url.openConnection().setConnectTimeout(3000);
                    br = new BufferedReader(new InputStreamReader(url.openStream()));
                    ok = true;
                } catch (SocketTimeoutException e) {
                    System.out.println("*** SocketTimeoutException URL : " + crawledUrl);
                    crawledUrl = queue.poll();
                    ok = false;
                } catch (MalformedURLException e) {
                    System.out.println("*** Malformed URL : " + crawledUrl);
                    crawledUrl = queue.poll();
                    ok = false;
                } catch (IOException e) {
                    System.out.println("*** IOException URL : " + crawledUrl);
                    crawledUrl = queue.poll();
                    ok = false;
                }
            }

            StringBuilder sb = new StringBuilder();
            String tmp = null;

            while ((tmp = br.readLine()) != null) {
                sb.append(tmp);
            }
            tmp = sb.toString();

            searchTerms(tmp, terms);
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(tmp);
            while (matcher.find()) {
                String urlToFind = matcher.group();
                if ((urlToFind.contains(".png")) || urlToFind.contains(".js") || urlToFind.contains(".jpeg") ||
                        urlToFind.contains(".css") || urlToFind.contains(".jpg") || urlToFind.contains(".xml") || urlToFind.contains(".dtd")) {
                    continue;
                }
                if (!marked.containsKey(urlToFind)) {
                    marked.put(urlToFind, marked.get(urlToCrawl) + 1);
                    System.out.println("site added :" + urlToFind);
                    queue.add(urlToFind);
                }
            }
        }
        if (br != null) {
            br.close();
        }
    }

    public static void showResults() {
        System.out.println("\n\nResults");
        System.out.println("web sites crawled: " + marked.size() + "\n");
        System.out.println("url visited " + i);
//        for (String s : marked) {
//            System.out.println("* " + s);
//        }
        for (Map.Entry<String, Integer> entry : termMap.entrySet()
        ) {
            System.out.println(entry.getKey() + "\t\t\t" + entry.getValue());
        }
    }

//    public static void searchUrls(String text, String regex) {
//
//    }

    public static void searchTerms(String text, String[] terms) {
        for (String term : terms
        ) {
            Pattern pattern = Pattern.compile(term);
            Matcher matcher = pattern.matcher(text);
            while (matcher.find()) {
                if (termMap.containsKey(term))
                    termMap.put(term, termMap.get(term) + 1);
                else
                    termMap.put(term, 1);
            }
        }
    }

    public static void main(String[] args) {
        try {
            crawl("https://www.javatpoint.com/java-regex");
//            crawl("https://en.wikipedia.org/wiki/Elon_Musk");
        } catch (IOException e) {
            e.printStackTrace();
        }
        showResults();
//        printCsv(termMap);
    }

}
