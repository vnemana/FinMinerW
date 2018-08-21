package com.mahesh.database;

import com.mahesh.utilities.HoldingRecord;

import java.util.ArrayList;

public class Holdings {

    //If Fund already exists, then retrieve fund id from fund table
    //  Else Insert into Fund table
    //  Get FundId
    //If Filing record already exists, get FilingID from Filing table.
    //  Else insert into Filing table.
    //  Get FilingId
    //For each holding record
    //Check if record exists for filing id, fund id and cusip.
    //If Yes, get Holding Id. Update it.
    //If No, insert Holding record.

    public void StoreFilingData(ArrayList<HoldingRecord> holdingRecords) {
        FundReportsDb fundReportsDb = new FundReportsDb();
        fundReportsDb.getConn();
    }
}
