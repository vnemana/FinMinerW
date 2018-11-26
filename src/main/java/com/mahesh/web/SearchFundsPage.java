package com.mahesh.web;

public class SearchFundsPage extends FMTemplate {
    public SearchFundsPage() {
        super();
        replace (new SearchFundsPanel(CONTENT_ID));
    }
}
