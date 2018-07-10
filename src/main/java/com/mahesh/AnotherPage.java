package com.mahesh;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.request.mapper.parameter.PageParameters;

public class AnotherPage extends WebPage {
    private static final long serialVersionUID = 2L;
    public AnotherPage(final PageParameters pageParameters) {
        super (pageParameters);
        add(new Label("AnotherPageLabel", "This is the second page"));
    }
}
