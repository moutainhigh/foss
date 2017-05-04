package com.deppon.pda.bdm.module.foss.accept.shared.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 
 * @package com.deppon.pda.bdm.module.foss.accept.shared.domain 
 * @file AcctOrderEntity.java 
 * @description 接货订单返回对象
 * @author ChenLiang
 * @created 2012-12-31 下午2:58:57    
 * @version 1.0
 */
public class AcctOrderEntity implements Serializable {

	/**
	 * TODO（用一句话描述这个变量表示什么）
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 订单编号
	 */
	private String orderCode;

	/**
	 * 276198-duhao-增加订单状态201608031812
	 */
	private String orderStatus;
	
	/**
	 * 276198-duhao-优先取货20160830
	 */
	private String isThePriority;
	
	/**
	 * 276198-duhao-异地调货20160830
	 */
	private String isOffSiteTransfer;
	// 货物名称 author:huangkaibing 20161009
	private String goodsName;
	// 收货联系人电话 author:huangkaibing 20161009
	private String receivePhone;
	// 发货人电话电话 author:huangkaibing 20161009
	private String tel;

	/**
	 * 营业部
	 */
	private String deptCode;

	/**
	 * 提货方式
	 */
	private String takeType;

	/**
	 * 运输性质
	 */
	private String transType;

	/**
	 * 接货地址
	 */
	private String acctAddress;

	/**
	 * 包装类型
	 */
	private String wrapType;

	/**
	 * 重量
	 */
	private double weight;

	/**
	 * 体积
	 */
	private double volume;

	/**
	 * 货物类型
	 */
	private String crgType;

	/**
	 * 客户姓名
	 */
	private String customerName;

	/**
	 * 客户电话
	 */
	private String customerPhone;

	/**
	 * 营业部联系电话
	 */
	private String deptPhone;

	/**
	 * 下单时间
	 */
	private Date orderTime;

	/**
	 * 最早接货时间
	 */
	private Date firstAcctTime;

	/**
	 * 最晚接货时间
	 */
	private Date lastAcctTime;

	/**
	 * 订单类型
	 */
	private String orderType;

	/**
	 * 请车专员姓名
	 */
	private String carCommissionerName;

	/**
	 * 受理人
	 */
	private String assignees;

	/**
	 * 车型需求
	 */
	private String modelsDemand;

	/**
	 * 件数
	 */
	private int pieces;

	/**
	 * 营业部联系人
	 */
	private String deptContactName;

	/**
	 * 提货网点编号
	 */
	private String customerPickupOrgCode;
	/**
	 * 接货省
	 */
	private String pickupProvince;
	/**
	 * 接货市
	 */
	private String pickupCity;
	/**
	 * 接货区
	 */
	private String pickupCounty;
	
	/**
	 * QQ通道编码
	 */
	private String channelCode;
	
	/**
	 * 备注
	 */
	private String remark;
	
	/**
	 * 运单号
	 */
	private String waybill_no;
	private String waybillNo;
	/**
	 * 运单类型
	 */
	private String waybillType;
	
	/**
     * 付款类型  2013年11月8日新增
     */
    private String paidType;
	
    /*快递新增*/
    /**
     * 收入部门编码
     */ 
    private String receiveOrgCode;
    /**
     * 收入部门名称
     */
    private String receiveOrgName;
    
    /**
     * 是否采集地址  (Y/N)
     */
   private String isCollectGps;
   /**

	 * 代收货款类型

	 */

  private String reciveLoanType;

  

  /**

 	 * 代收货款金额

 	 */

  private String reviceMoneyAmount;



  /**

 	 * 保价声明价值

 	 */

  private String insuredAmount;

  

  /**

 	 * 优惠券编码

 	 */

  private String couponNumber;
   
   /**
    * 送货地址
    */
   private String deliverAddress;
   
   /**
	 * 转发订单到司机工号
	 */
   private String fromDriverCode;
	
	/**
	 * 转发订单到司机姓名
	 */
	private String fromDriverName;
	
	
	/**
	 *送货的区域名称
	 */
	private String deliverCounty;
	
	/**
	 *付款方式
	 */
	private String payType;
	
     /**
      * 接货省
      */
	private String province;

    /**
     * 接货市
     */
	private String city;

    /**
     * 接货区
     */
	private String region;
	
	/**特安客户*/
	private BigDecimal taCustomUpLimit;
	
	/**
	 * 采购单号
	 */
	private String purchaseCode;
	
	/**
	 * 渠道单号
	 */
	private String channelNumber;

	/**
	 *  收货人
	 */
	private String receiver;
	
	/**
	 * 收货人联系
	 */
	private String receiverPhone;
	
	/**
	 * 客户分群
	 */
	private String customerGroup;
	
	/**
	 *裹裹编码 245955 2015-12-22 
	 */
	private String  serverType;
	
	/**
	 * 收货具体省份
	 */
	private String consigneeProvince;
	
	/**
	 * 收货具体城市
	 */
	private String consigneeCity;
	
	/**
	 * 收货具体市区
	 */
	private String consigneeCounty;
	/**
	 * 订单流入时间
	 */
	private String inflowTime;
	/**
	 * 取件员ID
	 */
	private String pickupManId;
	/**
	 * 裹裹订单类型
	 */
	private String ggOrderType;
	/**
	 * 上门揽件时效要求（120分钟、30分钟）
	 */
	private String gotInTime;
	
   public String getInflowTime() {
		return inflowTime;
	}

	public void setInflowTime(String inflowTime) {
		this.inflowTime = inflowTime;
	}

	public String getPickupManId() {
		return pickupManId;
	}

	public void setPickupManId(String pickupManId) {
		this.pickupManId = pickupManId;
	}

	public String getGgOrderType() {
		return ggOrderType;
	}

	public void setGgOrderType(String ggOrderType) {
		this.ggOrderType = ggOrderType;
	}

	public String getGotInTime() {
		return gotInTime;
	}

	public void setGotInTime(String gotInTime) {
		this.gotInTime = gotInTime;
	}

public String getConsigneeProvince() {
		return consigneeProvince;
	}

	public void setConsigneeProvince(String consigneeProvince) {
		this.consigneeProvince = consigneeProvince;
	}

	public String getConsigneeCity() {
		return consigneeCity;
	}

	public void setConsigneeCity(String consigneeCity) {
		this.consigneeCity = consigneeCity;
	}

	public String getConsigneeCounty() {
		return consigneeCounty;
	}

	public void setConsigneeCounty(String consigneeCounty) {
		this.consigneeCounty = consigneeCounty;
	}

public String getServerType() {
		return serverType;
	}

	public void setServerType(String serverType) {
		this.serverType = serverType;
	}

/**
	 * @return the customerGroup
	 */
	public String getCustomerGroup() {
		return customerGroup;
	}

	/**
	 * @param customerGroup the customerGroup to set
	 */
	public void setCustomerGroup(String customerGroup) {
		this.customerGroup = customerGroup;
	}


public String getPurchaseCode() {
		return purchaseCode;
	}


	public void setPurchaseCode(String purchaseCode) {
		this.purchaseCode = purchaseCode;
	}


	public String getChannelNumber() {
		return channelNumber;
	}


	public void setChannelNumber(String channelNumber) {
		this.channelNumber = channelNumber;
	}


	public String getReceiver() {
		return receiver;
	}


	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}


	public String getReceiverPhone() {
		return receiverPhone;
	}


	public void setReceiverPhone(String receiverPhone) {
		this.receiverPhone = receiverPhone;
	}


public String getProvince() {
		return province;
	}


	public void setProvince(String province) {
		this.province = province;
	}


	public String getCity() {
		return city;
	}


	public void setCity(String city) {
		this.city = city;
	}


	public String getRegion() {
		return region;
	}


	public void setRegion(String region) {
		this.region = region;
	}


public String getWaybill_no() {
        return waybill_no;
    }

 
	public String getFromDriverCode() {
	return fromDriverCode;
}


public void setFromDriverCode(String fromDriverCode) {
	this.fromDriverCode = fromDriverCode;
}


public String getFromDriverName() {
	return fromDriverName;
}


public void setFromDriverName(String fromDriverName) {
	this.fromDriverName = fromDriverName;
}


	public void setWaybill_no(String waybill_no) {
        this.waybill_no = waybill_no;
    }

    
	public String getChannelCode() {
		return channelCode;
	}

	public void setChannelCode(String channelCode) {
		this.channelCode = channelCode;
	}

	public String getCarCommissionerName() {
		return carCommissionerName;
	}

	public void setCarCommissionerName(String carCommissionerName) {
		this.carCommissionerName = carCommissionerName;
	}

	public String getAssignees() {
		return assignees;
	}

	public void setAssignees(String assignees) {
		this.assignees = assignees;
	}

	public String getModelsDemand() {
		return modelsDemand;
	}

	public void setModelsDemand(String modelsDemand) {
		this.modelsDemand = modelsDemand;
	}

	public int getPieces() {
		return pieces;
	}

	public void setPieces(int pieces) {
		this.pieces = pieces;
	}

	public String getCustomerPhone() {
		return customerPhone;
	}

	public String getReceiveOrgCode() {
        return receiveOrgCode;
    }

    public void setReceiveOrgCode(String receiveOrgCode) {
        this.receiveOrgCode = receiveOrgCode;
    }

    public void setCustomerPhone(String customerPhone) {
		this.customerPhone = customerPhone;
	}

	public String getDeptContactName() {
		return deptContactName;
	}

	public void setDeptContactName(String deptContactName) {
		this.deptContactName = deptContactName;
	}

	public String getAcctAddress() {
		return acctAddress;
	}

	public void setAcctAddress(String acctAddress) {
		this.acctAddress = acctAddress;
	}

	public String getCrgType() {
		return crgType;
	}

	public void setCrgType(String crgType) {
		this.crgType = crgType;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getDeptCode() {
		return deptCode;
	}

	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}

	public String getDeptPhone() {
		return deptPhone;
	}

	public void setDeptPhone(String deptPhone) {
		this.deptPhone = deptPhone;
	}

	public Date getFirstAcctTime() {
		return firstAcctTime;
	}

	public void setFirstAcctTime(Date firstAcctTime) {
		this.firstAcctTime = firstAcctTime;
	}

	public Date getLastAcctTime() {
		return lastAcctTime;
	}

	public void setLastAcctTime(Date lastAcctTime) {
		this.lastAcctTime = lastAcctTime;
	}

	public String getOrderCode() {
		return orderCode;
	}

	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}

	public Date getOrderTime() {
		return orderTime;
	}

	public void setOrderTime(Date orderTime) {
		this.orderTime = orderTime;
	}

	public String getTakeType() {
		return takeType;
	}

	public void setTakeType(String takeType) {
		this.takeType = takeType;
	}

	public String getTransType() {
		return transType;
	}

	public void setTransType(String transType) {
		this.transType = transType;
	}

	public double getVolume() {
		return volume;
	}

	public void setVolume(double volume) {
		this.volume = volume;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	public String getWrapType() {
		return wrapType;
	}

	public void setWrapType(String wrapType) {
		this.wrapType = wrapType;
	}

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	public String getCustomerPickupOrgCode() {
		return customerPickupOrgCode;
	}

	public void setCustomerPickupOrgCode(String customerPickupOrgCode) {
		this.customerPickupOrgCode = customerPickupOrgCode;
	}

	public String getPickupProvince() {
		return pickupProvince;
	}

	public void setPickupProvince(String pickupProvince) {
		this.pickupProvince = pickupProvince;
	}

	public String getPickupCity() {
		return pickupCity;
	}

	public void setPickupCity(String pickupCity) {
		this.pickupCity = pickupCity;
	}

	public String getPickupCounty() {
		return pickupCounty;
	}

	public void setPickupCounty(String pickupCounty) {
		this.pickupCounty = pickupCounty;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getReceiveOrgName() {
		return receiveOrgName;
	}

	public void setReceiveOrgName(String receiveOrgName) {
		this.receiveOrgName = receiveOrgName;
	}

    public String getPaidType() {
        return paidType;
    }

    public void setPaidType(String paidType) {
        this.paidType = paidType;
    }

    public String getIsCollectGps() {
        return isCollectGps;
    }

    public void setIsCollectGps(String isCollectGps) {
        this.isCollectGps = isCollectGps;
    }

    public String getDeliverAddress() {
        return deliverAddress;
    }

    public void setDeliverAddress(String deliverAddress) {
        this.deliverAddress = deliverAddress;
    }

	public String getReciveLoanType() {
		return reciveLoanType;
	}

	public void setReciveLoanType(String reciveLoanType) {
		this.reciveLoanType = reciveLoanType;
	}

	public String getReviceMoneyAmount() {
		return reviceMoneyAmount;
	}

	public void setReviceMoneyAmount(String reviceMoneyAmount) {
		this.reviceMoneyAmount = reviceMoneyAmount;
	}

	public String getInsuredAmount() {
		return insuredAmount;
	}

	public void setInsuredAmount(String insuredAmount) {
		this.insuredAmount = insuredAmount;
	}

	public String getCouponNumber() {
		return couponNumber;
	}

	public void setCouponNumber(String couponNumber) {
		this.couponNumber = couponNumber;
	}


	/**
	 * @return the deliverCounty
	 */
	public String getDeliverCounty() {
		return deliverCounty;
	}


	/**
	 * @param deliverCounty the deliverCounty to set
	 */
	public void setDeliverCounty(String deliverCounty) {
		this.deliverCounty = deliverCounty;
	}


	public String getPayType() {
		return payType;
	}


	public void setPayType(String payType) {
		this.payType = payType;
	}


	public BigDecimal getTaCustomUpLimit() {
		return taCustomUpLimit;
	}


	public void setTaCustomUpLimit(BigDecimal taCustomUpLimit) {
		this.taCustomUpLimit = taCustomUpLimit;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public String getIsThePriority() {
		return isThePriority;
	}

	public void setIsThePriority(String isThePriority) {
		this.isThePriority = isThePriority;
	}

	public String getIsOffSiteTransfer() {
		return isOffSiteTransfer;
	}

	public void setIsOffSiteTransfer(String isOffSiteTransfer) {
		this.isOffSiteTransfer = isOffSiteTransfer;
	}

	public String getWaybillNo() {
		return waybillNo;
	}

	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}

	public String getWaybillType() {
		return waybillType;
	}

	public void setWaybillType(String waybillType) {
		this.waybillType = waybillType;
	}

	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	public String getReceivePhone() {
		return receivePhone;
	}

	public void setReceivePhone(String receivePhone) {
		this.receivePhone = receivePhone;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	
    
    
}
