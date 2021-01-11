package by.matusevich;

import by.matusevich.crawler.Crawler;
import by.matusevich.model.ResultsOfParsing;
import by.matusevich.model.ResultsOfParsingTop10;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class CrawlerTest {

    @Test
    public void crawl() {
    }

    @Test
    public void searchUrls() {
        Crawler crawler = new Crawler();
        String filename = "example.html";
        File file = new File(filename);
        Document site = null;
        try {
            site = Jsoup.parse(file, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        Elements links = site.select("a[href]");
        List<String> strings = crawler.searchUrls(links, 1);

        assertEquals(3L, links.size());
        assertEquals(2L, strings.size());

    }

    @Test
    public void getTop10Results() {
    }

    @Test
    public void sortArrayLists() {
        Crawler crawler = new Crawler();
        ArrayList<Integer> newValues = new ArrayList<>();
        ArrayList<String> newNames = new ArrayList<>();
        newValues.add(1);
        newValues.add(2);
        newValues.add(3);
        newValues.add(4);

        newNames.add("one");
        newNames.add("two");
        newNames.add("three");
        newNames.add("four");

        List<String> sortedArrayList = crawler.sortArrayLists(newValues, newNames);
        String four = sortedArrayList.get(0);
        assertEquals(four, "four");
        assertEquals(sortedArrayList.get(3), "one");
    }

    @Test
    public void getIndexOfMinValueFromTop10() {
        Crawler crawler = new Crawler();
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        list.add(5);

        int index = crawler.getIndexOfMinValueFromTop10(list);
        assertEquals(0L, index);
    }

    @Test
    public void getAllResults() {
        Crawler crawler = new Crawler();
        ResultsOfParsing allResults = crawler.getAllResults();

        assertNotNull(allResults);
    }

    @Test
    public void top10ResultsToString() {
        List<String> list = new ArrayList<>();
        list.add("Ultramarines");
        list.add("Dark Angels");
        list.add("Alpha Legion");
        list.add("Iron Warriors");
        list.add("Sons of Horus");
        ArrayList<Integer> arrayList = new ArrayList<>();
        arrayList.add(1);
        Map<String, ArrayList<Integer>> map = new HashMap<>();
        map.put("Ultramarines", arrayList);
        map.put("Dark Angels", arrayList);
        map.put("Alpha Legion", arrayList);
        map.put("Iron Warriors", arrayList);
        map.put("Sons of Horus", arrayList);

        Crawler crawler = new Crawler();
        ResultsOfParsingTop10 top10 = crawler.top10ResultsToString(list, map);
        assertNotNull(top10);
        assertEquals(1L, (long) map.get("Sons of Horus").get(0));
    }

    @Test
    public void searchOnlyOneTerm() {
        String text = "It was treachery at first. To turn against brothers, to kill for personal advancement and power. " +
                "But we have seen them, how their minds and bodies have been corrupted. Their very belief systems have been warped." +
                " This is no longer Horus's treachery. It is his heresy.";
        String term = "Horus";

        int oneTermCount = Crawler.searchOnlyOneTerm(text, term);
        assertEquals(1L, oneTermCount);
    }
}