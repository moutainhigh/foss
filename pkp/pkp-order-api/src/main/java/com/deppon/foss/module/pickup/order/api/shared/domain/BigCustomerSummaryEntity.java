package com.deppon.foss.module.pickup.order.api.shared.domain;

import java.util.Date;

/**
 * caohuibin
 * Created by 268217 on 2015/9/15.
 */
public class BigCustomerSummaryEntity {

    /**
     *运单待处理信息id
     */
    private String id;

    /**
     *发货客户名称
     */
    private String deliverycustomername;

    /**
     *客户标签编号
     */
    private String customerlablenums;

    /**
     *运单号
     */
    private String waybillno;

    /**
     *ERP单号
     */
    private String erporderno;

    /**
     *流水号
     */
    private String serialno;

    /**
     *订单状态
     */
    private String active;

    /**
     *创建时间
     */
    private Date createtime;

    /**
     *开单时间
     */
    private Date billtime;

    /**
     *开单人
     */
    private String createusercode;

    /**
     *开单组织
     */
    private String createorgcode;

    /**
     *始发城市名称
     */
    private String departmentcityname;

    /**
     *收货地址
     */
    private String receivecustomeraddress;

    /**
     *收货门店编号
     */
    private String customerpickuporgcode;

    /**
     *收货人
     */
    private String receivecustomercontact;

    /**
     *提货方式
     */
    private String receivemethod;

    /**
     *运输性质
     */
    private String productcode;

    /**
     *包装类型
     */
    private String packageservice;

    /**
     *货物类型
     */
    private String goodstype;

    /**
     *付款方式
     */
    private String paidmethod;

    /**
     *总件数
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
     *送标记
     */
    private String send;

    /**
     *到达外场名称
     */
    private String desttranscentername;

    /**
     *是否发货大客户
     */
    private String deliverybigcustomer;

    /**
     *是否收货大客户
     */
    private String receivebigcustomer;

    /**
     *是否展会货
     */
    private String isexhibitcargo;

    /**
     *路由库位信息
     */
    private String goodsareas;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDeliverycustomername() {
        return deliverycustomername;
    }

    public void setDeliverycustomername(String deliverycustomername) {
        this.deliverycustomername = deliverycustomername;
    }

    public String getCustomerlablenums() {
        return customerlablenums;
    }

    public void setCustomerlablenums(String customerlablenums) {
        this.customerlablenums = customerlablenums;
    }

    public String getWaybillno() {
        return waybillno;
    }

    public void setWaybillno(String waybillno) {
        this.waybillno = waybillno;
    }

    public String getErporderno() {
        return erporderno;
    }

    public void setErporderno(String erporderno) {
        this.erporderno = erporderno;
    }

    public String getSerialno() {
        return serialno;
    }

    public void setSerialno(String serialno) {
        this.serialno = serialno;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Date getBilltime() {
        return billtime;
    }

    public void setBilltime(Date billtime) {
        this.billtime = billtime;
    }

    public String getCreateusercode() {
        return createusercode;
    }

    public void setCreateusercode(String createusercode) {
        this.createusercode = createusercode;
    }

    public String getCreateorgcode() {
        return createorgcode;
    }

    public void setCreateorgcode(String createorgcode) {
        this.createorgcode = createorgcode;
    }

    public String getDepartmentcityname() {
        return departmentcityname;
    }

    public void setDepartmentcityname(String departmentcityname) {
        this.departmentcityname = departmentcityname;
    }

    public String getReceivecustomeraddress() {
        return receivecustomeraddress;
    }

    public void setReceivecustomeraddress(String receivecustomeraddress) {
        this.receivecustomeraddress = receivecustomeraddress;
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

    public String getReceivemethod() {
        return receivemethod;
    }

    public void setReceivemethod(String receivemethod) {
        this.receivemethod = receivemethod;
    }

    public String getProductcode() {
        return productcode;
    }

    public void setProductcode(String productcode) {
        this.productcode = productcode;
    }

    public String getPackageservice() {
        return packageservice;
    }

    public void setPackageservice(String packageservice) {
        this.packageservice = packageservice;
    }

    public String getGoodstype() {
        return goodstype;
    }

    public void setGoodstype(String goodstype) {
        this.goodstype = goodstype;
    }

    public String getPaidmethod() {
        return paidmethod;
    }

    public void setPaidmethod(String paidmethod) {
        this.paidmethod = paidmethod;
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

    public String getSend() {
        return send;
    }

    public void setSend(String send) {
        this.send = send;
    }

    public String getDesttranscentername() {
        return desttranscentername;
    }

    public void setDesttranscentername(String desttranscentername) {
        this.desttranscentername = desttranscentername;
    }

    public String getDeliverybigcustomer() {
        return deliverybigcustomer;
    }

    public void setDeliverybigcustomer(String deliverybigcustomer) {
        this.deliverybigcustomer = deliverybigcustomer;
    }

    public String getReceivebigcustomer() {
        return receivebigcustomer;
    }

    public void setReceivebigcustomer(String receivebigcustomer) {
        this.receivebigcustomer = receivebigcustomer;
    }

    public String getIsexhibitcargo() {
        return isexhibitcargo;
    }

    public void setIsexhibitcargo(String isexhibitcargo) {
        this.isexhibitcargo = isexhibitcargo;
    }

    public String getGoodsareas() {
        return goodsareas;
    }

    public void setGoodsareas(String goodsareas) {
        this.goodsareas = goodsareas;
    }
}
