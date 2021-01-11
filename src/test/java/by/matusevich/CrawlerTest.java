package by.matusevich;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class CrawlerTest {

    @Test
    public void crawl() {
    }

    @Test
    public void searchUrls() {
    }

    @Test
    public void getTop10Results() {
    }

    @Test
    public void sortArrayLists() {
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
        assertEquals(0L,index);
    }

    @Test
    public void getAllResults() {
    }

    @Test
    public void top10ResultsToString() {
    }

    @Test
    public void searchOnlyOneTerm() {
        String text = "It was treachery at first. To turn against brothers, to kill for personal advancement and power. " +
                "But we have seen them, how their minds and bodies have been corrupted. Their very belief systems have been warped." +
                " This is no longer Horus's treachery. It is his heresy.";
        String term = "Horus";

        int oneTermCount = Crawler.searchOnlyOneTerm(text, term);
        assertEquals(1l,oneTermCount);
    }
}