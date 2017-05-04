package com.deppon.foss.module.pickup.order.api.shared.domain;

import java.util.Date;

/**
 *  caohuibin
 * Created by 268217 on 2015/9/11.
 */
public class BigCustomeEntity {

    /**
     * ID
     */
    private String id;

    /**
     * 运单号
     */
    private String waybillno;

    /**
     * 渠道订单号或erp单号
     */
    private String erplogisticid;

    /**
     * 客户到达门店编号
     */
    private String arrivestorenum;

    /**
     * 客户标签编号
     */
    private String customerlablenum;

    /**
     * 订单来源
     */
    private String ordersource;

    /**
     * 发货客户编码
     */
    private String customercode;

    /**
     * 发货客户姓名
     */
    private String sendername;

    /**
     * 发货人手机号码
     */
    private String sendermobile;

    /**
     * 发货人电话号码
     */
    private String senderphone;

    /**
     * 发货人省份
     */
    private String senderprovince;

    /**
     * 发货人城市
     */
    private String sendercity;

    /**
     * 发货人区县
     */
    private String sendercounty;

    /**
     * 发货人镇街道
     */
    private String senderstreet;

    /**
     * 发货人地址
     */
    private String senderaddress;

    /**
     * 发货人省份编码
     */
    private String senderprovincecode;

    /**
     * 发货人城市编码
     */
    private String sendercitycode;

    /**
     * 发货人区县编码
     */
    private String sendercountycode;

    /**
     * 发货人镇街道编码
     */
    private String senderstreetcode;

    /**
     * 上门接货
     */
    private String vistreceive;

    /**
     * 始发网点名称
     */
    private String businessnetworkname;

    /**
     * 始发网点编码
     */
    private String businessnetworkno;

    /**
     * 收货联系人
     */
    private String receivername;

    /**
     * 收货人手机号码
     */
    private String receivermobile;

    /**
     * 收货人电话号码
     */
    private String receiverphone;

    /**
     * 收货人省份
     */
    private String receiverprovince;

    /**
     * 收货人城市
     */
    private String receivercity;

    /**
     * 收货人区县
     */
    private String receivercounty;

    /**
     * 收货人镇街道
     */
    private String receiverstreet;

    /**
     * 收货人地址
     */
    private String receiveraddress;

    /**
     * 收货人省份编码
     */
    private String receiverprovincecode;

    /**
     * 收货人城市编码
     */
    private String receivercitycode;

    /**
     * 收货人区县编码
     */
    private String receivercountycode;

    /**
     * 收货人镇街道编码
     */
    private String receiverstreetcode;

    /**
     * 流水号
     */
    private String serialno;

    /**
     * 运输性质
     */
    private String transporttype;

    /**
     * 付款方式
     */
    private String paytype;

    /**
     * 货物名称
     */
    private String cargoname;

    /**
     * 总件数
     */
    private Integer totalnumber;

    /**
     * 重量
     */
    private Double weight;

    /**
     * 体积
     */
    private Double volume;

    /**
     * 尺寸
     */
    private String goodssize;

    /**
     * 包装材料
     */
    private String packageservice;

    /**
     * 保险价值
     */
    private Double insurancevalue;

    /**
     * 签收单
     */
    private String backsignbill;

    /**
     * 送货方式或提货方式
     */
    private String deliverytype;

    /**
     * 短信通知
     */
    private String smsnotify;

    /**
     * 客户提交订单时间
     */
    private Date gmtcommit;

    /**
     * FOSS获得数据时系统时间
     */
    private Date foss_systime;

    /**
     * 储运事项或备注
     */
    private String remark;

    /**
     * 提货网点
     */
    private String customerpickuporgcode;

    public String getWaybillno() {
        return waybillno;
    }

    public void setWaybillno(String waybillno) {
        this.waybillno = waybillno;
    }

    public String getErplogisticid() {
        return erplogisticid;
    }

    public void setErplogisticid(String erplogisticid) {
        this.erplogisticid = erplogisticid;
    }

    public String getArrivestorenum() {
        return arrivestorenum;
    }

    public void setArrivestorenum(String arrivestorenum) {
        this.arrivestorenum = arrivestorenum;
    }

    public String getCustomerlablenum() {
        return customerlablenum;
    }

    public void setCustomerlablenum(String customerlablenum) {
        this.customerlablenum = customerlablenum;
    }

    public String getOrdersource() {
        return ordersource;
    }

    public void setOrdersource(String ordersource) {
        this.ordersource = ordersource;
    }

    public String getCustomercode() {
        return customercode;
    }

    public void setCustomercode(String customercode) {
        this.customercode = customercode;
    }

    public String getSendername() {
        return sendername;
    }

    public void setSendername(String sendername) {
        this.sendername = sendername;
    }

    public String getSenderphone() {
        return senderphone;
    }

    public void setSenderphone(String senderphone) {
        this.senderphone = senderphone;
    }

    public String getSenderprovince() {
        return senderprovince;
    }

    public void setSenderprovince(String senderprovince) {
        this.senderprovince = senderprovince;
    }

    public String getSendercity() {
        return sendercity;
    }

    public void setSendercity(String sendercity) {
        this.sendercity = sendercity;
    }

    public String getSendercounty() {
        return sendercounty;
    }

    public void setSendercounty(String sendercounty) {
        this.sendercounty = sendercounty;
    }

    public String getSenderstreet() {
        return senderstreet;
    }

    public void setSenderstreet(String senderstreet) {
        this.senderstreet = senderstreet;
    }

    public String getSenderaddress() {
        return senderaddress;
    }

    public void setSenderaddress(String senderaddress) {
        this.senderaddress = senderaddress;
    }

    public String getSenderprovincecode() {
        return senderprovincecode;
    }

    public void setSenderprovincecode(String senderprovincecode) {
        this.senderprovincecode = senderprovincecode;
    }

    public String getSendercitycode() {
        return sendercitycode;
    }

    public void setSendercitycode(String sendercitycode) {
        this.sendercitycode = sendercitycode;
    }

    public String getSendercountycode() {
        return sendercountycode;
    }

    public void setSendercountycode(String sendercountycode) {
        this.sendercountycode = sendercountycode;
    }

    public String getSenderstreetcode() {
        return senderstreetcode;
    }

    public void setSenderstreetcode(String senderstreetcode) {
        this.senderstreetcode = senderstreetcode;
    }

    public String getVistreceive() {
        return vistreceive;
    }

    public void setVistreceive(String vistreceive) {
        this.vistreceive = vistreceive;
    }

    public String getBusinessnetworkname() {
        return businessnetworkname;
    }

    public void setBusinessnetworkname(String businessnetworkname) {
        this.businessnetworkname = businessnetworkname;
    }

    public String getBusinessnetworkno() {
        return businessnetworkno;
    }

    public void setBusinessnetworkno(String businessnetworkno) {
        this.businessnetworkno = businessnetworkno;
    }

    public String getReceivername() {
        return receivername;
    }

    public void setReceivername(String receivername) {
        this.receivername = receivername;
    }

    public String getReceiverphone() {
        return receiverphone;
    }

    public void setReceiverphone(String receiverphone) {
        this.receiverphone = receiverphone;
    }

    public String getReceiverprovince() {
        return receiverprovince;
    }

    public void setReceiverprovince(String receiverprovince) {
        this.receiverprovince = receiverprovince;
    }

    public String getReceivercity() {
        return receivercity;
    }

    public void setReceivercity(String receivercity) {
        this.receivercity = receivercity;
    }

    public String getReceivercounty() {
        return receivercounty;
    }

    public void setReceivercounty(String receivercounty) {
        this.receivercounty = receivercounty;
    }

    public String getReceiverstreet() {
        return receiverstreet;
    }

    public void setReceiverstreet(String receiverstreet) {
        this.receiverstreet = receiverstreet;
    }

    public String getReceiveraddress() {
        return receiveraddress;
    }

    public void setReceiveraddress(String receiveraddress) {
        this.receiveraddress = receiveraddress;
    }

    public String getReceiverprovincecode() {
        return receiverprovincecode;
    }

    public void setReceiverprovincecode(String receiverprovincecode) {
        this.receiverprovincecode = receiverprovincecode;
    }

    public String getReceivercitycode() {
        return receivercitycode;
    }

    public void setReceivercitycode(String receivercitycode) {
        this.receivercitycode = receivercitycode;
    }

    public String getReceivercountycode() {
        return receivercountycode;
    }

    public void setReceivercountycode(String receivercountycode) {
        this.receivercountycode = receivercountycode;
    }

    public String getReceiverstreetcode() {
        return receiverstreetcode;
    }

    public void setReceiverstreetcode(String receiverstreetcode) {
        this.receiverstreetcode = receiverstreetcode;
    }

    public String getSerialno() {
        return serialno;
    }

    public void setSerialno(String serialno) {
        this.serialno = serialno;
    }

    public String getTransporttype() {
        return transporttype;
    }

    public void setTransporttype(String transporttype) {
        this.transporttype = transporttype;
    }

    public String getPaytype() {
        return paytype;
    }

    public void setPaytype(String paytype) {
        this.paytype = paytype;
    }

    public String getCargoname() {
        return cargoname;
    }

    public void setCargoname(String cargoname) {
        this.cargoname = cargoname;
    }

    public Integer getTotalnumber() {
        return totalnumber;
    }

    public void setTotalnumber(Integer totalnumber) {
        this.totalnumber = totalnumber;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public Double getVolume() {
        return volume;
    }

    public void setVolume(Double volume) {
        this.volume = volume;
    }

    public String getGoodssize() {
        return goodssize;
    }

    public void setGoodssize(String goodssize) {
        this.goodssize = goodssize;
    }

    public String getPackageservice() {
        return packageservice;
    }

    public void setPackageservice(String packageservice) {
        this.packageservice = packageservice;
    }

    public Double getInsurancevalue() {
        return insurancevalue;
    }

    public void setInsurancevalue(Double insurancevalue) {
        this.insurancevalue = insurancevalue;
    }

    public String getBacksignbill() {
        return backsignbill;
    }

    public void setBacksignbill(String backsignbill) {
        this.backsignbill = backsignbill;
    }

    public String getDeliverytype() {
        return deliverytype;
    }

    public void setDeliverytype(String deliverytype) {
        this.deliverytype = deliverytype;
    }

    public String getSmsnotify() {
        return smsnotify;
    }

    public void setSmsnotify(String smsnotify) {
        this.smsnotify = smsnotify;
    }

    public Date getGmtcommit() {
        return gmtcommit;
    }

    public void setGmtcommit(Date gmtcommit) {
        this.gmtcommit = gmtcommit;
    }

    public Date getFoss_systime() {
        return foss_systime;
    }

    public void setFoss_systime(Date foss_systime) {
        this.foss_systime = foss_systime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getSendermobile() {
        return sendermobile;
    }

    public void setSendermobile(String sendermobile) {
        this.sendermobile = sendermobile;
    }

    public String getReceivermobile() {
        return receivermobile;
    }

    public void setReceivermobile(String receivermobile) {
        this.receivermobile = receivermobile;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCustomerpickuporgcode() {
        return customerpickuporgcode;
    }

    public void setCustomerpickuporgcode(String customerpickuporgcode) {
        this.customerpickuporgcode = customerpickuporgcode;
    }
}
