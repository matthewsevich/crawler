package by.matusevich;

import java.io.Serializable;
import java.util.List;

public class ResultsOfParsing implements Serializable {

    private String pageParsed;
    private String termsCount;

    private List<Integer> top10Values;
    private List<String> top10Names;

    public ResultsOfParsing(String pageParsed, String termsCount, List<Integer> top10Values, List<String> top10Names) {
        this.pageParsed = pageParsed;
        this.termsCount = termsCount;
        this.top10Values = top10Values;
        this.top10Names = top10Names;
    }

    public String getPageParsed() {
        return pageParsed;
    }

    public void setPageParsed(String pageParsed) {
        this.pageParsed = pageParsed;
    }

    public String getTermsCount() {
        return termsCount;
    }

    public void setTermsCount(String termsCount) {
        this.termsCount = termsCount;
    }

    public List<Integer> getTop10Values() {
        return top10Values;
    }

    public void setTop10Values(List<Integer> top10Values) {
        this.top10Values = top10Values;
    }

    public List<String> getTop10Names() {
        return top10Names;
    }

    public void setTop10Names(List<String> top10Names) {
        this.top10Names = top10Names;
    }


}

