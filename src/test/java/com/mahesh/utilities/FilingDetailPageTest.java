package com.mahesh.utilities;

import org.junit.Test;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class FilingDetailPageTest {

    @Test
    public void getRawFiling() throws IOException, ParserConfigurationException, SAXException {
        URL filingURL = new URL(new URL("file:"), "form13fInfoTable.html");
        FilingDetailPage filingDetailPage = new FilingDetailPage(filingURL);
        HashMap<String, HoldingRecord> holdingRecordHashMap =
                filingDetailPage.parseRawFiling(filingURL);
        for (Object o : holdingRecordHashMap.entrySet()) {
            Map.Entry pair = (Map.Entry) o;
            System.out.println(pair.getValue());
        }
    }

    @Test
    public void getFilingDate() throws MalformedURLException {
        URL filingURL = new URL("https://www.sec" +
                ".gov/Archives/edgar/data/1067983/000095012319002221/0000950123-19-002221-index.htm");
        FilingDetailPage filingDetailPage = new FilingDetailPage(filingURL);
        Date filingDate = filingDetailPage.getFilingDate();
        System.out.println(filingDate);
    }

    @Test
    public void getReportDate() throws MalformedURLException {
        URL filingURL = new URL("https://www.sec" +
                ".gov/Archives/edgar/data/1067983/000095012319002221/0000950123-19-002221-index.htm");
        FilingDetailPage filingDetailPage = new FilingDetailPage(filingURL);
        Date reportDate = filingDetailPage.getReportDate();
        System.out.println(reportDate);
    }

    @Test
    public void getFundName() throws MalformedURLException {
        URL filingURL = new URL("https://www.sec" +
                ".gov/Archives/edgar/data/1067983/000095012319002221/0000950123-19-002221-index.htm");
        FilingDetailPage filingDetailPage = new FilingDetailPage(filingURL);
        String fundName = filingDetailPage.getFundName();
        System.out.println(fundName);
    }
}
