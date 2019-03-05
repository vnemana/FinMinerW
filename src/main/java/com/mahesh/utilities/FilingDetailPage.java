package com.mahesh.utilities;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class FilingDetailPage {
    private String companyName;
    private HtmlPage filing13FPage;
    private static final String searchSite = "https://www.sec.gov";
    private final static Logger logger = LoggerFactory.getLogger(
            FilingDetailPage.class);
    public FilingDetailPage(URL url) {
        try (final WebClient webClient = new WebClient()) {
            filing13FPage = webClient.getPage(url);
            System.out.println(filing13FPage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getRawFiling() {
        HtmlTable resultsFiling13FTable = filing13FPage.getBody()
                .getOneHtmlElementByAttribute( "table",
                "summary", "Document Format Files");
        int numFormatFileRows = resultsFiling13FTable.getRowCount();
        //for each row
        //get "Document" cell text.
        //If it says "form13fInfoTable.html", get the link.
        String source = "/html/body/div[4]/div[2]/div[1]/table/tbody/tr[:?:]/td[3]/a/@href";
        String[] sourceArr = source.split(":");
        for (int ii = 0; ii < numFormatFileRows; ii++) {
            HtmlTableRow ii_row = resultsFiling13FTable.getRow(ii);
            HtmlTableCell ii_rowCell = ii_row.getCell(2);
            String docString = ii_rowCell.asText();
            if (docString.equals("form13fInfoTable.xml")) {
                String sourceData = sourceArr[0] + (ii+1) + sourceArr[2];
                List<DomNode> sourceNodes = filing13FPage.getByXPath(sourceData);
                if (sourceNodes.size() > 0)
                    return searchSite + sourceNodes.get(0).getTextContent();
            }
        }
        return null;
    }

    public HashMap<String, HoldingRecord> parseRawFiling(URL url)
            throws IOException, ParserConfigurationException, SAXException {
        URLConnection urlConnection = url.openConnection();
        urlConnection.connect();

        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();

        Document document = documentBuilder.parse(urlConnection.getInputStream());

        System.out.println("Root: " + document.getDocumentElement().getNodeName());

        NodeList nodeList = document.getElementsByTagName("infoTable");
        System.out.println("Number of Nodes: " + nodeList.getLength());
        HashMap<String, HoldingRecord> holdingRecords = new HashMap<>();

        for (int nodeCount=0; nodeCount<nodeList.getLength(); nodeCount++) {
            Node node = nodeList.item(nodeCount);
            Element element = (Element) node;
            String issuerName = element.getElementsByTagName("nameOfIssuer").item(0).getTextContent();
            String cusip = element.getElementsByTagName("cusip").item(0).getTextContent();
            int numberOfShares = Integer.valueOf(element.getElementsByTagName
                    ("sshPrnamt").item(0).getTextContent());
            long position = Long.valueOf(element.getElementsByTagName("value").item(0).getTextContent())*1000;

            HoldingRecord hr = new HoldingRecord(issuerName, cusip, numberOfShares, position);
            HoldingRecord existingHr = holdingRecords.get(cusip);
            if (existingHr != null) {
                existingHr.setNumberOfShares(existingHr.getNumberOfShares()
                        + numberOfShares);
                existingHr.setPosition(existingHr.getPosition() + position);
            }
            else {
                holdingRecords.put(cusip, hr);
            }
        }
        for (Object o : holdingRecords.entrySet()) {
            Map.Entry pair = (Map.Entry) o;
            logger.info(pair.getValue().toString());
        }
        return holdingRecords;
    }

    Date getFilingDate() {
        try {
            String source = "/html/body/div[4]/div[1]/div[2]/div[1]/div[2]";
            List<DomNode> domNodes = filing13FPage.getByXPath(source);
            if(domNodes.size() > 0) {
                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                return dateFormat.parse(domNodes.get(0).getTextContent());
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    Date getReportDate() {
        try {
            String source = "/html/body/div[4]/div[1]/div[2]/div[2]/div[2]";
            List<DomNode> domNodes = filing13FPage.getByXPath(source);
            if(domNodes.size() > 0) {
                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                return dateFormat.parse(domNodes.get(0).getTextContent());
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    String getCompanyName() {
        if (companyName != null && ! companyName.isEmpty())
            return companyName;

        try (final WebClient webClient = new WebClient()){
            String source = "/html/body/div[4]/div[2]/div[1]/table/tbody/tr[2]/td[3]/a/@href";
            List<DomNode> domNodes = filing13FPage.getByXPath(source);
            if (domNodes.size() > 0) {
                final HtmlPage primaryDoc = webClient.getPage(searchSite+domNodes.get(0).getTextContent());
                String companySource = "/html/body/table[4]/tbody/tr[2]/td[2]";
                List<DomNode> domNodesList = primaryDoc.getByXPath(companySource);

                if (domNodesList.size() > 0) {
                    companyName = domNodesList.get(0).getTextContent();
                    return companyName;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}