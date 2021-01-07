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
    private static final int URL_LIMIT = 10000;

    public static Queue<String> queue = new LinkedList<>();
    public static Set<String> marked = new HashSet<>();

    public static String regex = "\\b(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";

    public static String[] terms = new String[]{"Java", "regex", "java", "Regex"};
    public static Map<String, Integer> termMap = new HashMap<>();

    public static void crawl(String root) throws IOException {
        queue.add(root);
        BufferedReader br = null;

        int i = 1;
        while (!queue.isEmpty()) {
            String crawledUrl = queue.poll();
            System.out.println("\n=== Site crawled : " + marked.size() +" "+ crawledUrl  + " ===" );

            if (marked.size() > URL_LIMIT)
                return;
            boolean ok = false;
            URL url = null;
            while (!ok) {
                try {
                    url = new URL(crawledUrl);
                    url.openConnection().setConnectTimeout(3000);
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
            searchUrls(tmp, regex);

        }
        if (br != null) {
            br.close();
        }
    }

    public static void showResults() {
//        System.out.println("\n\nResults");
//        System.out.println("web sites crawled: " + marked.size() + "\n");

        for (String s : marked) {
            System.out.println("* " + s);
        }
        for (Map.Entry<String, Integer> entry : termMap.entrySet()
        ) {
            System.out.println(entry.getKey() + "\t\t\t" + entry.getValue());
        }
    }

    public static void searchUrls(String text, String regex) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(text);
        while (matcher.find()) {
            String w = matcher.group();
            if ((w.contains(".png")) || w.contains(".js") || w.contains(".jpeg") ||
                    w.contains(".css") || w.contains(".jpg") || w.contains(".xml") || w.contains(".dtd")) {
                continue;
            }
            if (!marked.contains(w)) {
                marked.add(w);
                //todo увеличение счетчика глубины
                System.out.println("site added :" + w);
                queue.add(w);
            }
        }
    }

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
