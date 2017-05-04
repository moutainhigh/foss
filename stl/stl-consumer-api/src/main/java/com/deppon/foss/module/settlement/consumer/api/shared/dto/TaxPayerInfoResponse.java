package com.deppon.foss.module.settlement.consumer.api.shared.dto;

import java.util.List;

/**
 * CRM纳税人信息接口返回的信息封装类
 * Created by 322906 on 2016/7/12.
 */
public class TaxPayerInfoResponse {
    /**
     * 已绑定的客户编码
     */
    private List<TaxPayerDto> taxPayerList;

    /**
     * 成功失败状态，成功--1 ，失败--0
     */
    private String result;
    /**
     * 消息（失败原因）
     */
    private String message;

    public List<TaxPayerDto> getTaxPayerList() {
        return taxPayerList;
    }

    public void setTaxPayerList(List<TaxPayerDto> taxPayerList) {
        this.taxPayerList = taxPayerList;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
