package com.mahesh;

import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.WebPage;

public class HomePage extends WebPage {
	private static final long serialVersionUID = 1L;

	public HomePage(final PageParameters parameters) {
		super(parameters);

		add(new Label("version", getApplication().getFrameworkSettings().getVersion()));

		// TODO Add your page's components here
		add(new Label("helloMessage", "Hello Mahesh Wicket! Achcha Ji mein Haari"));
        add(new Link<Void>("linkId") {
            @Override
            public void onClick() {
                AnotherPage anotherPage = new AnotherPage(parameters);
                HomePage.this.setResponsePage(anotherPage);
            }
        });
	}
}
