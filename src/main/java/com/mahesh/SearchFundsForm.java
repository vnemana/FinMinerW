package com.mahesh;

import com.gargoylesoftware.htmlunit.ElementNotFoundException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.*;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.Model;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;

public class SearchFundsForm extends Form {
    private final static Logger logger = LoggerFactory.getLogger(SearchFundsForm.class);
    private static final String searchUrl = "https://www.sec.gov/cgi-bin/browse-edgar?company=";
    private static final String searchSite = "https://www.sec.gov";
    private static final String search13fParam = "&type=13F-HR&count=100";
    private TextField searchField;

    public SearchFundsForm(String id) {
        super(id);
        searchField =  new TextField("fundsearchentry", Model.of(""));
        add(searchField);
    }

    public final void onSubmit() {
        String fundString = (String) searchField.getDefaultModelObject();
        String urlString = searchUrl + fundString;

        try (final WebClient webClient = new WebClient()) {
            final HtmlPage page = webClient.getPage(urlString);

            HtmlElement body = page.getBody();
            HtmlElement series = body.getOneHtmlElementByAttribute("div", "id", "seriesDiv");
            HtmlTable table = series.getOneHtmlElementByAttribute("table", "summary", "Results");

            boolean firstRow = true;
            for (HtmlTableRow row : table.getRows()) {
                if (firstRow) {
                    firstRow = false;
                    continue;
                }
                try {
                    HtmlTableCell cell = row.getCell(0);
                    String anchorText = cell.asText();
                    HtmlAnchor ha = page.getAnchorByText(anchorText);
                    if (ha != null) {
                        String search13fSite = searchSite + ha.getHrefAttribute() + search13fParam;
                        logger.info("Search Site: " + search13fSite);
                        Search13FResultsPage s = new Search13FResultsPage(search13fSite);
                        ArrayList<FilingLinkInfo> filing13FLinks = s.get13FFilingLinks();
                        logger.info("Filing Link: " + filing13FLinks.size());
                        if (filing13FLinks.size() > 0) {
                            for (int ii=0; ii<filing13FLinks.size(); ii++)
                                logger.info(filing13FLinks.get(ii).toString());
//                            request.setAttribute("filingLinks", filing13FLinks);
//                            request.getRequestDispatcher("OptionalUpdates.jsp").forward(request, response);
                            return;
                        }
                    }
                } catch (ElementNotFoundException e) {
                    System.out.println("Element Not Found Exception");
                }
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}