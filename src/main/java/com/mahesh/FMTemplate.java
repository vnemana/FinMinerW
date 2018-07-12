package com.mahesh;

import org.apache.wicket.Component;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;

public class FMTemplate extends WebPage {
    public static final String CONTENT_ID="contentComponent";
    private Component headerPanel;
    private Component menuPanel;
    private Component footerPanel;

    public FMTemplate () {
        add (headerPanel = new HeaderPanel("headerPanel"));
        add (menuPanel = new MenuPanel("menuPanel"));
        add (footerPanel = new FooterPanel("footerPanel"));
        add (new Label(CONTENT_ID, "Put your content here"));
    }
}
