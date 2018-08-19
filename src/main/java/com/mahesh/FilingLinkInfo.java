package com.mahesh;

public class FilingLinkInfo {
    private String link;
    private String filingDate;

    public void setLink(String link) {
        this.link = link;
    }

    public void setFilingDate(String filingDate) {
        this.filingDate = filingDate;
    }

    public String getLink() {
        return link;
    }

    public String getFilingDate() {
        return filingDate;
    }

    public String toString() {
        return "{" + link + "\n" + filingDate +"}\n";
    }
}
