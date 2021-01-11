package by.matusevich.model;

import java.io.Serializable;
import java.util.List;

public class ResultsOfParsingTop10 implements Serializable {

    private List<String> top10;

    public ResultsOfParsingTop10() {
    }

    public ResultsOfParsingTop10(List<String> top10) {
        this.top10 = top10;
    }
}
