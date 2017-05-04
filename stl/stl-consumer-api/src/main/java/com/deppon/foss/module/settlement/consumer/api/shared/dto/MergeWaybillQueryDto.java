package com.deppon.foss.module.settlement.consumer.api.shared.dto;

import java.util.Date;

/**
 * Created by 322906 on 2016/6/22.
 */
public class MergeWaybillQueryDto {
    /**
     * 合并运单号集合
     */
    private String[] mergeWaybillNos;
    /**
     * 运单号集合
     */
    private String[] waybillNos;

    /**
     * 客户编码（页面输入的客户编码）
     */
    private String[] customerCodes;
    /**
     * 发票抬头
     */
    private String invoiceTitle;
    /**
     * 税务登记号
     */
    private String taxId;

    private Date startDate;
    private Date endDate;

    public String[] getMergeWaybillNos() {
        return mergeWaybillNos;
    }

    public void setMergeWaybillNos(String[] mergeWaybillNos) {
        this.mergeWaybillNos = mergeWaybillNos;
    }

    public String[] getWaybillNos() {
        return waybillNos;
    }

    public void setWaybillNos(String[] waybillNos) {
        this.waybillNos = waybillNos;
    }


        public String[] getCustomerCodes() {
        return customerCodes;
    }

    public void setCustomerCodes(String[] customerCodes) {
        this.customerCodes = customerCodes;
    }

    public String getInvoiceTitle() {
        return invoiceTitle;
    }

    public void setInvoiceTitle(String invoiceTitle) {
        this.invoiceTitle = invoiceTitle;
    }

    public String getTaxId() {
        return taxId;
    }

    public void setTaxId(String taxId) {
        this.taxId = taxId;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}
