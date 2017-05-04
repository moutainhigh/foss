package com.deppon.foss.module.pickup.order.api.shared.dto;

/**
 * caohuibin
 * Created by 268217 on 2015/9/14.
 */
public class BigCustomerSummaryDto {

    /**
     *运单号
     */
    private String waybillno;

    /**
     *收货门店编码
     */
    private String customerpickuporgcode;

    /**
     *收货人
     */
    private String receivecustomercontact;

    /**
     *收货地址
     */
    private String receivecustomeraddress;

    /**
     *提货方式
     */
    private String receivemethod;

    /**
     *付款方式
     */
    private String paidmethod;

    /**
     *运输性质
     */
    private String productcode;

    /**
     *件数
     */
    private Integer pieces;

    /**
     *重量
     */
    private Double goodsweighttotal;

    /**
     *体积
     */
    private Double goodsvolumetotal;

    /**
     *ERP单号
     */
    private String erporderno;

    /**
     *标签条码编号
     */
    private String customerlablenums;

    public String getWaybillno() {
        return waybillno;
    }

    public void setWaybillno(String waybillno) {
        this.waybillno = waybillno;
    }

    public String getCustomerpickuporgcode() {
        return customerpickuporgcode;
    }

    public void setCustomerpickuporgcode(String customerpickuporgcode) {
        this.customerpickuporgcode = customerpickuporgcode;
    }

    public String getReceivecustomercontact() {
        return receivecustomercontact;
    }

    public void setReceivecustomercontact(String receivecustomercontact) {
        this.receivecustomercontact = receivecustomercontact;
    }

    public String getReceivecustomeraddress() {
        return receivecustomeraddress;
    }

    public void setReceivecustomeraddress(String receivecustomeraddress) {
        this.receivecustomeraddress = receivecustomeraddress;
    }

    public String getReceivemethod() {
        return receivemethod;
    }

    public void setReceivemethod(String receivemethod) {
        this.receivemethod = receivemethod;
    }

    public String getPaidmethod() {
        return paidmethod;
    }

    public void setPaidmethod(String paidmethod) {
        this.paidmethod = paidmethod;
    }

    public String getProductcode() {
        return productcode;
    }

    public void setProductcode(String productcode) {
        this.productcode = productcode;
    }

    public Integer getPieces() {
        return pieces;
    }

    public void setPieces(Integer pieces) {
        this.pieces = pieces;
    }

    public Double getGoodsweighttotal() {
        return goodsweighttotal;
    }

    public void setGoodsweighttotal(Double goodsweighttotal) {
        this.goodsweighttotal = goodsweighttotal;
    }

    public Double getGoodsvolumetotal() {
        return goodsvolumetotal;
    }

    public void setGoodsvolumetotal(Double goodsvolumetotal) {
        this.goodsvolumetotal = goodsvolumetotal;
    }

    public String getErporderno() {
        return erporderno;
    }

    public void setErporderno(String erporderno) {
        this.erporderno = erporderno;
    }

    public String getCustomerlablenums() {
        return customerlablenums;
    }

    public void setCustomerlablenums(String customerlablenums) {
        this.customerlablenums = customerlablenums;
    }
}
