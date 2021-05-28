package com.akounto.accountingsoftware.response;

import com.google.gson.annotations.SerializedName;

public class GetCompanyResponse {
    @SerializedName("Data")
    private BusinessDetails businessDetails;
    @SerializedName("TransactionStatus")
    private TransactionStatus transactionStatus;

    public void setTransactionStatus(TransactionStatus transactionStatus2) {
        this.transactionStatus = transactionStatus2;
    }

    public TransactionStatus getTransactionStatus() {
        return this.transactionStatus;
    }

    public void setBusinessDetails(BusinessDetails businessDetails2) {
        this.businessDetails = businessDetails2;
    }

    public BusinessDetails getBusinessDetails() {
        return this.businessDetails;
    }
}
