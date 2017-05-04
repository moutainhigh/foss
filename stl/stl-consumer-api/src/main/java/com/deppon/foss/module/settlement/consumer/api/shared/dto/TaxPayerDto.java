package com.deppon.foss.module.settlement.consumer.api.shared.dto;

/**
 * Created by 322906 on 2016/6/12.
 * 保存纳税人信息
 */
public class TaxPayerDto {
    /**
     * 客户编码
     */
    private String customerCode;
    /**
     * 发票抬头
     */
    private String ftitle;
    /**
     * 税务登记号
     */
    private String ftaxnumber;


    public String getCustomerCode() {
        return customerCode;
    }

    public void setCustomerCode(String customerCode) {
        this.customerCode = customerCode;
    }

    public String getFtitle() {
        return ftitle;
    }

    public void setFtitle(String ftitle) {
        this.ftitle = ftitle;
    }

    public String getFtaxnumber() {
        return ftaxnumber;
    }

    public void setFtaxnumber(String ftaxnumber) {
        this.ftaxnumber = ftaxnumber;
    }
}
