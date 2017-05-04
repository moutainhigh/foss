package com.deppon.foss.module.pickup.order.api.shared.dto;

/**
 * Created by 268217 on 2015/9/11.
 */
public class BigCustomeDto {
	
	/**
     * 运单号
     */
    private String waybillno;
	
	/**
     * 渠道订单号或erp单号
     */
    private String erplogisticid;

    /**
     * 标签条码号
     */
    private String customerlablenum;

    /**
     * 收货门店编号
     */
    private String arrivestorenum;

    /**
     * 收货地址
     */
    private String receiveraddress;

    /**
     * 收货人
     */
    private String receivername;

    /**
     * 付款方式
     */
    private String paytype;

    /**
     * 运输性质
     */
    private String transporttype;

    /**
     * 提货方式
     */
    private String deliverytype;

    /**
     * 件数
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

    public String getReceiveraddress() {
        return receiveraddress;
    }

    public void setReceiveraddress(String receiveraddress) {
        this.receiveraddress = receiveraddress;
    }

    public String getReceivername() {
        return receivername;
    }

    public void setReceivername(String receivername) {
        this.receivername = receivername;
    }

    public String getPaytype() {
        return paytype;
    }

    public void setPaytype(String paytype) {
        this.paytype = paytype;
    }

    public String getTransporttype() {
        return transporttype;
    }

    public void setTransporttype(String transporttype) {
        this.transporttype = transporttype;
    }

    public String getDeliverytype() {
        return deliverytype;
    }

    public void setDeliverytype(String deliverytype) {
        this.deliverytype = deliverytype;
    }

    public int getTotalnumber() {
        return totalnumber;
    }

    public void setTotalnumber(int totalnumber) {
        this.totalnumber = totalnumber;
    }

    public String getCustomerlablenum() {
        return customerlablenum;
    }

    public void setCustomerlablenum(String customerlablenum) {
        this.customerlablenum = customerlablenum;
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

	public String getWaybillno() {
		return waybillno;
	}

	public void setWaybillno(String waybillno) {
		this.waybillno = waybillno;
	}
}
