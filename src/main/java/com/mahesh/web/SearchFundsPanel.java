package com.mahesh.web;

import org.apache.wicket.markup.html.panel.Panel;

public class SearchFundsPanel extends Panel {
    public SearchFundsPanel(String id) {
        super(id);
        add(new SearchFundsForm("searchfundform"));
    }
}
