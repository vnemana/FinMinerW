package com.mahesh.utilities;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomNode;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.mahesh.database.dao.CusipDao;
import com.mahesh.database.dto.Cusip;

import java.io.IOException;
import java.net.URL;
import java.util.List;

//TODO: Ideally HoldingRecord and Holding should be the same thing.
public class HoldingRecord implements Comparable{
    private final String issuerName;
    private final String cusip;
    private String stock;
    private int numberOfShares;
    private long position;
    private final String urlString;

    public String getIssuerName() {
        return issuerName;
    }

    public String getCusip() {
        return cusip;
    }

    public int getNumberOfShares() {
        return numberOfShares;
    }

    public long getPosition() { return position; }

    public String getStock() { return stock; }

    public void setStock(String stock) {
        this.stock = stock;
    }

    public void setStockFromCusip () {
        try {
            if (!cusip.isEmpty()) {
                String stock;
                String companyName;
                final WebClient webClient = new WebClient();
                CusipDao cusipDao = new CusipDao();
                Cusip cusipFromDb = cusipDao.getCusipData(cusip);

                if (cusipFromDb == null) {

                    URL stockLookupURL = new URL(urlString);
                    stock = parseStockFromURL(webClient, stockLookupURL);
                    companyName = parseCompanyNameFromURL (webClient,
                            stockLookupURL);
                    Cusip cusipDto = new Cusip (cusip, stock, companyName);
                    cusipDao.insertStockCusip(cusipDto);
                }
                else {
                    System.out.println("Retrieved from db " +
                            cusipFromDb.getStock() + ", " + cusipFromDb
                            .getCompanyName());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setNumberOfShares(int numberOfShares) {
        this.numberOfShares = numberOfShares;
    }

    public void setPosition(long position) {
        this.position = position;
    }

    HoldingRecord(String issuerName, String cusip, int numberOfShares, long
            position) {
        this.issuerName = issuerName;
        this.cusip = cusip;
        this.numberOfShares = numberOfShares;
        this.position = position;
        this.urlString =
                "https://quotes.fidelity.com/mmnet/SymLookup.phtml?reqforlookup=" +
                        "REQUESTFORLOOKUP&productid=mmnet&isLoggedIn=mmnet" +
                        "&rows=50&for=stock&" +
                        "by=cusip&criteria="+ cusip + "&submit=Search";
    }

    @Override
    public int compareTo(@SuppressWarnings("NullableProblems") Object o) {
        if (this.cusip.equals(((HoldingRecord)o).cusip))
            return 0;
        else return 1;
    }

    @Override
    public String toString() {
        return this.getCusip() + ": " + this.getIssuerName() + ", " + this
                .getNumberOfShares() + ", " + this.getPosition() + "\n";
    }

    private String parseStockFromURL(WebClient webClient, URL stockLookupURL)
            throws IOException {
        String stock = "";
        final HtmlPage stockLookupPage = webClient.getPage
                (stockLookupURL);
        String stockSource =
                "/html/body/table[1]/tbody/tr/td[2]/table[2]/tbody/tr[1" +
                        "]/td[2]/table/tbody/tr[3]/td[2]/font/a";

        List<DomNode> domNodesList = stockLookupPage
                .getPage().getByXPath(stockSource);

        if (domNodesList.size() > 0) {
            stock = domNodesList.get(0).getTextContent();
            System.out.println("Stock: " + stock);
        }
        return stock;
    }

    private String parseCompanyNameFromURL(WebClient webClient, URL
            stockLookupURL) throws IOException {
        String companyName = "";
        final HtmlPage stockLookupPage = webClient.getPage
                (stockLookupURL);
        String companyNameSource =
                "/html/body/table[1]/tbody/tr/td[2]/table[2" +
                        "]/tbody/tr[1]" +
                        "/td[2]/table/tbody/tr[3]/td[1]/font";
        List<DomNode> domNodesList = stockLookupPage
                .getPage().getByXPath(companyNameSource);

        if (domNodesList.size() > 0) {
            companyName = domNodesList.get(0).getTextContent();
            System.out.println("Company Name: " + companyName);
        }
        return companyName;
    }
}
