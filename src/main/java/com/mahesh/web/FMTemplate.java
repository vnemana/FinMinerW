package com.mahesh.web;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;

class FMTemplate extends WebPage {
    static final String CONTENT_ID="contentComponent";

    FMTemplate() {
        add (new HeaderPanel("headerPanel"));
        add (new MenuPanel("menuPanel"));
        add (new FooterPanel("footerPanel"));
        add (new Label(CONTENT_ID, "Put your content here"));
    }
}
