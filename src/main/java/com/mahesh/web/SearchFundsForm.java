package com.mahesh.web;

import com.gargoylesoftware.htmlunit.ElementNotFoundException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.*;
import com.mahesh.database.dto.Filing;
import com.mahesh.database.dto.Fund;
import com.mahesh.database.dto.Holding;
import com.mahesh.utilities.FilingDetailPage;
import com.mahesh.utilities.HoldingRecord;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.Model;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class SearchFundsForm extends Form {
    private final static Logger logger = LoggerFactory.getLogger(SearchFundsForm.class);
    private static final String searchUrl = "https://www.sec.gov/cgi-bin/browse-edgar?company=";
    private static final String searchSite = "https://www.sec.gov";
    private static final String search13fParam = "&type=13F-HR&count=100";
    private final TextField searchField;
    private final TextField cikSearchField;

    public SearchFundsForm(String id) {
        super(id);
        searchField =  new TextField("fundsearchentry", Model.of(""));
        add(searchField);
        cikSearchField = new TextField("ciksearchentry", Model.of(""));
        add(cikSearchField);

        Button searchFundButton = new Button("searchfund-button") {
            public void onSubmit() {
                onSearchFundSubmit();
            }
        };
        add(searchFundButton);

        Button ciksearchButton = new Button("ciksearch-button") {
            public void onSubmit(){
                System.out.println("cik search pressed");
                onCikSearchSubmit();
            }
        };
        add(ciksearchButton);
    }

    private void onCikSearchSubmit() {
        String cik = (String) cikSearchField.getModelObject();
        String cikSearchUrl = "https://www.sec.gov/cgi-bin/browse-edgar?action=getcompany&" +
                "CIK="+ cik +"&owner=include&count=40&hidefilings=0&type" +
                "=13F-HR&count=100";
        try {
            Search13FResultsPage s = new Search13FResultsPage(cikSearchUrl);
            ArrayList<FilingLinkInfo> filing13FLinks = s.get13FFilingLinks();
            logger.info("Filing Link: " + filing13FLinks.size());
            parseAndStoreFiling(filing13FLinks);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
    }

    public final void onSearchFundSubmit() {
        String fundString = (String) searchField.getDefaultModelObject();
        String urlString = searchUrl + fundString;

        try (final WebClient webClient = new WebClient()) {
            final HtmlPage page = webClient.getPage(urlString);

            HtmlElement body = page.getBody();
            HtmlElement series = body.getOneHtmlElementByAttribute("div",
                    "id", "seriesDiv");
            HtmlTable table = series.getOneHtmlElementByAttribute("table",
                    "summary", "Results");

            boolean firstRow = true;
            for (HtmlTableRow row : table.getRows()) {
                if (firstRow) {
                    firstRow = false;
                    continue;
                }
                try {
                    HtmlTableCell cell = row.getCell(0);
                    String anchorText = cell.asText();
                    System.out.println("Anchor: " + anchorText);
                    HtmlAnchor ha = page.getAnchorByText(anchorText);
                    if (ha != null) {
                        String search13fSite = searchSite + ha.getHrefAttribute() + search13fParam;
                        logger.info("Search Site: " + search13fSite);
                        Search13FResultsPage s = new Search13FResultsPage(search13fSite);
                        ArrayList<FilingLinkInfo> filing13FLinks = s.get13FFilingLinks();
                        logger.info("Filing Link: " + filing13FLinks.size());
                        parseAndStoreFiling(filing13FLinks);
                    }
                } catch (ElementNotFoundException e) {
                    System.out.println("Element Not Found Exception");
                    e.printStackTrace();
                } catch (SAXException | ParserConfigurationException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void parseAndStoreFiling(ArrayList<FilingLinkInfo> filing13FLinks) throws IOException, ParserConfigurationException, SAXException {
        System.out.println(filing13FLinks.size());
        if (filing13FLinks != null && !filing13FLinks.isEmpty()) {
            for (FilingLinkInfo f: filing13FLinks) {
                URL filingDetailURL = new URL(f.getLink());
                FilingDetailPage filingDetailPage = new
                        FilingDetailPage(filingDetailURL);
                String rawFilingURLString = filingDetailPage
                        .getRawFiling();
                logger.info("Raw Filing URL: " +
                        rawFilingURLString);
                if (rawFilingURLString != null) {
                    URL rawFilingURL = new URL(rawFilingURLString);

                    HashMap<String, HoldingRecord> holdingRecords
                            = filingDetailPage.parseRawFiling(rawFilingURL);
                    ArrayList<HoldingRecord>
                            holdingRecordArrayList = new
                            ArrayList<>();
                    Iterator it = holdingRecords.entrySet().iterator();
                    while (it.hasNext()) {
                        Map.Entry pair = (Map.Entry) it.next();
                        holdingRecordArrayList.add((HoldingRecord) pair
                                .getValue());
                    }

                    SearchResultsDisplayPage searchResultsDisplayPage =
                            new SearchResultsDisplayPage(holdingRecords);
                    setResponsePage(searchResultsDisplayPage);

                    Holding holding = new Holding();
                    Fund fund = new Fund(filingDetailPage
                            .getFundName());
                    Filing filing = new Filing();
                    filing.setFilingDate(filingDetailPage.getFilingDate());
                    filing.setReportDate(filingDetailPage.getReportDate());
                    filing.setFilingType(filingDetailPage.getFilingType());
                    holding.StoreFilingData(holdingRecordArrayList,
                            filing, fund);
                    System.out.println("Completed " + filing
                            .getFilingDate() + " " +
                            filing.getReportDate());
                }
            }
        }
    }
}
