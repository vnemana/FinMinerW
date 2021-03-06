package com.mahesh.web;

import com.mahesh.utilities.HoldingRecord;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.PropertyModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class SearchResultsDisplayPage extends WebPage {
    private static final long serialVersionUID = 1L;

    public SearchResultsDisplayPage (final HashMap<String, HoldingRecord>
                                             holdingRecords) {
        super();
        List<HoldingRecord> holdingRecordList = new ArrayList<>();
        for (Object o : holdingRecords.entrySet()) {
            Map.Entry pair = (Map.Entry) o;
            holdingRecordList.add((HoldingRecord) pair.getValue());
        }

        add(new ListView<HoldingRecord>("holdings", holdingRecordList) {
            @Override
            protected void populateItem(ListItem<HoldingRecord> listItem) {
                listItem.add(new Label("issuername", new PropertyModel<>
                        (listItem.getModel(), "issuerName")));
                listItem.add(new Label("cusip", new PropertyModel<>(
                        listItem.getModel(), "cusip")));
                listItem.add(new Label("numberofshares", new PropertyModel<>(
                        listItem.getModel(), "numberOfShares")));
                listItem.add(new Label("position", new PropertyModel<>(
                        listItem.getModel(), "position")));
            }
        });
    }
}
