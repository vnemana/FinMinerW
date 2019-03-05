package com.mahesh.utilities;

import org.junit.Test;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.net.URL;
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
}
