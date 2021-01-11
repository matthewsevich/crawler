package by.matusevich.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;

public class ResultsOfParsing implements Serializable {

    private String pageParsed;
    private String termsCount;

    private Map<String, ArrayList<Integer>> allStatistics;

    public ResultsOfParsing() {
    }

    public ResultsOfParsing(String pageParsed, String termsCount, Map<String, ArrayList<Integer>> allStatistics) {
        this.pageParsed = pageParsed;
        this.termsCount = termsCount;
        this.allStatistics = allStatistics;
    }
}

