package com.mahesh.utilities;

import org.junit.Test;

public class HoldingRecordTest {

    @Test
    public void setStockFromCusip() {
        HoldingRecord hr = new HoldingRecord("Berkshire & Hathaway",
                "92826C839", 1000, 100);
        hr.setStockFromCusip();
        HoldingRecord hr1 = new HoldingRecord("Berkshire & Hathaway",
                "68389X105", 1000, 100);
        hr1.setStockFromCusip();
    }
}