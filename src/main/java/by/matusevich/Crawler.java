package by.matusevich;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Crawler {
    public static Queue<String> queue = new LinkedList<>();
    public static Set<String> marked = new HashSet<>();
    //    public static String regex = "http[s]*://(\\w+\\.)*(\\w)";
    public static String regex = "\\b(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";
    public static String[] terms = new String[]{"java", "regex"};
    public static Map<String, Integer> termMap = new HashMap<>();

    public static void crawl(String root) throws IOException {
        queue.add(root);
        BufferedReader br = null;

        while (!queue.isEmpty()) {
            String crawledUrl = queue.poll();
            System.out.println("\n=== Site crawled : " + crawledUrl + " ===");

            if (marked.size() > 100)
                return;
            boolean ok = false;
            URL url = null;
            while (!ok) {
                try {
                    url = new URL(crawledUrl);
                    br = new BufferedReader(new InputStreamReader(url.openStream()));
                    ok = true;
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
            System.out.println("tmp" + tmp);
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(tmp);

            for (String term : terms
            ) {
                Pattern pattern1 = Pattern.compile(term);
                Matcher matcher1 = pattern1.matcher(tmp);
                while (matcher1.find()) {
                    if (termMap.containsKey(term)) {
                        termMap.put(term, termMap.get(term) + 1);
                    } else {
                        termMap.put(term, 1);
                    }
                }
            }
            //todo парсить страницу +
            //todo получить урлы +
            //todo парсить следующие урлы +
            //todo счетчик количества урлов +

            //todo счетчик глубины(структура данных)
            //todo поиск слов на странице и счетчик - вроде есть
            //todo таймаут при загрузке страниц
            while (matcher.find()) {
                String w = matcher.group();
                if ((w.contains(".png")) || w.contains(".js") ||
                        w.contains(".css") || w.contains(".jpg") || w.contains(".xml") || w.contains(".dtd")) {
                    continue;
                }
                if (!marked.contains(w)) {
                    marked.add(w);
                    System.out.println("site added :" + w);
                    queue.add(w);
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

        for (String s : marked) {
            System.out.println("* " + s);
        }
        for (Map.Entry<String, Integer> entry : termMap.entrySet()
        ) {
            System.out.println(entry.getKey() + "\t\t\t" + entry.getValue());

        }
    }

    public static void main(String[] args) {
        try {
            System.out.println(System.currentTimeMillis());

            crawl("https://www.javatpoint.com/java-regex");
//            crawl("http://www.quizful.net/");
        } catch (IOException e) {
            e.printStackTrace();
        }
        showResults();
        System.out.println(System.currentTimeMillis());
    }

}
