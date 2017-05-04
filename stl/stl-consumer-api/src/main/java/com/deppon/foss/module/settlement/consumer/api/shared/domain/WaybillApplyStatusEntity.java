package com.deppon.foss.module.settlement.consumer.api.shared.domain;

import com.deppon.foss.framework.entity.BaseEntity;

import java.util.Date;

/**
 * Created by 322906 on 2016/6/14.
 * 运单（合并运单）发票申请状态表
 * 发票系统将运单（合并运单）申请发票后，同步过来的数据
 */
public class WaybillApplyStatusEntity extends BaseEntity {
    /**
     * 单号（运单号/合并运单号)
     */
    private String billNo;
    /**
     * 发票申请状态
     * ('Y'-已开具，'N'-未开具)
     */
    private String status;
    /**
     * 开票日期
     */
    private Date invoiceCreateDate;


    public String getBillNo() {
        return billNo;
    }

    public void setBillNo(String billNo) {
        this.billNo = billNo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getInvoiceCreateDate() {
        return invoiceCreateDate;
    }

    public void setInvoiceCreateDate(Date invoiceCreateDate) {
        this.invoiceCreateDate = invoiceCreateDate;
    }
}
