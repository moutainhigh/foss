package com.deppon.pda.bdm.module.foss.accept.shared.domain;

import java.io.Serializable;
import java.util.List;

import com.deppon.pda.bdm.module.core.shared.domain.ScanMsgEntity;

/**
 * 
 * @package com.deppon.pda.bdm.module.foss.accept.shared.domain 
 * @file WaybillExpressEntity.java 
 * @description PDA快递开单
 * @author ZhangZhenXian
 * @created 2013-07-22 下午17:24:57    
 * @version 1.0
 */
public class WaybillExpressEntity extends ScanMsgEntity implements Serializable{

    /**    
     * serialVersionUID:TODO（用一句话描述这个变量表示什么）    
     *    
     * @since Ver 1.1    
     */    
    
    private static final long serialVersionUID = 1L;
    

    /**
     * 订单号
     */
    private String orderCode;

    /**
     * 出发部门
     */
    private String departDeptCode;

    /**
     * 提货方式
     */
    private String takeType;

    /**
     * 目的地编号
     */
    private String destinationCode;

    /**
     * 运输性质
     */
    private String transType;

    /**
     * 重量
     */
    private double weight;

    /**
     * 体积
     */
    private double volume;

    /**
     * 代打木架体积
     */
    private double gallowsVolume;

    /**
     * 代打木箱体积
     */
    private double boxVolume;

    /**
     * 包装类型
     */
    private String wrapType;

    /**
     * 货物类型
     */
    private String crgType;

    /**
     * 车牌号
     */
    private String truckCode;
    
    /**
     * 运费
     */
    private double freight;

    /**
     * 实收运费
     */
    private double paidFreight;

    /**
     * 优惠券
     */
    private String couponCode;
    
    /**
     * 优惠金额
     */
    private double couponMoney;

    /**
     * 付款方式
     */
    private String paidType;

    /**
     * 是否打木架
     */
    private String isGallows;

    /**
     * 增值服务项
     */
    private List<ValueAddServiceEntity> appreciationService;

    /**
     * 返单类别
     */
    private String returnBillType;
    
    /**
     * 退款类型
     */
    private String refundType;
    
    /**
     * 快递员Code  
     */
    private String expressEmpCode;
    
    /**
     * 快递员名称
     */
    private String expressEmpName;
    
    /**
     * 收入部门code    
     */
    private String expressOrgCode;
    
    /**
     * 收入部门名称
     */
    private String expressOrgName;
    
    /**
     * PDA串号
     */
    private String pdaSerial;
    
    /**
     * 银行交易流水号
     */
    private String bankTradeSerail;
    
    /**
     * 发货快递员工号
     */
    private String sendEmployeeCode;
   
    /**
     * 是否内部带货
     */  
    private String needDepponCustomerCode;
   
    /**
     * 是否发送短信服务   Y/N 
     */   
    private String isSMS;
    
    /**
     * 营销编码
     */
    private String marketingCode;
    /**       
     *营销名称
     */
    private String marketingName;
    
    /**快递工时节点-接送货*/
    private String timeNode;
    
    /**
     * 原单号
     */
    private String oldWayBill;
    
    /**
     * 是否返货业务 Y/N
     */
    private String isReturnGoods;

    /**
     * 送货费
     * 
     */
    public double acctExpense;
    
    /**
     * 子母件新增返货方式 245955 
     * @return
     */
    public String returnWay;
    
    /**
     * 新增刷卡信息 298403
     * @return
     */
    public AccountStatementEntitys posCardInfo;
    
	public AccountStatementEntitys getPosCardInfo() {
		return posCardInfo;
	}

	public void setPosCardInfo(AccountStatementEntitys posCardInfo) {
		this.posCardInfo = posCardInfo;
	}

	public String getReturnWay() {
		return returnWay;
	}

	public void setReturnWay(String returnWay) {
		this.returnWay = returnWay;
	}

	public double getAcctExpense() {
		return acctExpense;
	}

	public void setAcctExpense(double acctExpense) {
		this.acctExpense = acctExpense;
	}

	public String getReturnBillType() {
        return returnBillType;
    }

    public void setReturnBillType(String returnBillType) {
        this.returnBillType = returnBillType;
    }

    public String getRefundType() {
        return refundType;
    }

    public void setRefundType(String refundType) {
        this.refundType = refundType;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public String getDepartDeptCode() {
        return departDeptCode;
    }

    public void setDepartDeptCode(String departDeptCode) {
        this.departDeptCode = departDeptCode;
    }

    public String getTakeType() {
        return takeType;
    }

    public void setTakeType(String takeType) {
        this.takeType = takeType;
    }

    public String getDestinationCode() {
        return destinationCode;
    }

    public void setDestinationCode(String destinationCode) {
        this.destinationCode = destinationCode;
    }

    public String getTransType() {
        return transType;
    }

    public void setTransType(String transType) {
        this.transType = transType;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getVolume() {
        return volume;
    }

    public void setVolume(double volume) {
        this.volume = volume;
    }

    public double getGallowsVolume() {
        return gallowsVolume;
    }

    public void setGallowsVolume(double gallowsVolume) {
        this.gallowsVolume = gallowsVolume;
    }

    public double getBoxVolume() {
        return boxVolume;
    }

    public void setBoxVolume(double boxVolume) {
        this.boxVolume = boxVolume;
    }

    public String getWrapType() {
        return wrapType;
    }

    public void setWrapType(String wrapType) {
        this.wrapType = wrapType;
    }

    public String getCrgType() {
        return crgType;
    }

    public void setCrgType(String crgType) {
        this.crgType = crgType;
    }

    public String getTruckCode() {
        return truckCode;
    }

    public void setTruckCode(String truckCode) {
        this.truckCode = truckCode;
    }

    public double getPaidFreight() {
        return paidFreight;
    }

    public void setPaidFreight(double paidFreight) {
        this.paidFreight = paidFreight;
    }

    public String getCouponCode() {
        return couponCode;
    }

    public void setCouponCode(String couponCode) {
        this.couponCode = couponCode;
    }

    public String getPaidType() {
        return paidType;
    }

    public void setPaidType(String paidType) {
        this.paidType = paidType;
    }

    public String getIsGallows() {
        return isGallows;
    }

    public void setIsGallows(String isGallows) {
        this.isGallows = isGallows;
    }

    public List<ValueAddServiceEntity> getAppreciationService() {
        return appreciationService;
    }

    public void setAppreciationService(
            List<ValueAddServiceEntity> appreciationService) {
        this.appreciationService = appreciationService;
    }

    public double getCouponMoney() {
        return couponMoney;
    }

    public void setCouponMoney(double couponMoney) {
        this.couponMoney = couponMoney;
    }

    public double getFreight() {
        return freight;
    }

    public void setFreight(double freight) {
        this.freight = freight;
    }

    public String getExpressEmpCode() {
        return expressEmpCode;
    }

    public void setExpressEmpCode(String expressEmpCode) {
        this.expressEmpCode = expressEmpCode;
    }

    public String getExpressEmpName() {
        return expressEmpName;
    }

    public void setExpressEmpName(String expressEmpName) {
        this.expressEmpName = expressEmpName;
    }

    public String getExpressOrgCode() {
        return expressOrgCode;
    }

    public void setExpressOrgCode(String expressOrgCode) {
        this.expressOrgCode = expressOrgCode;
    }

    public String getExpressOrgName() {
        return expressOrgName;
    }

    public void setExpressOrgName(String expressOrgName) {
        this.expressOrgName = expressOrgName;
    }

    public String getPdaSerial() {
        return pdaSerial;
    }

    public void setPdaSerial(String pdaSerial) {
        this.pdaSerial = pdaSerial;
    }

    public String getBankTradeSerail() {
        return bankTradeSerail;
    }

    public void setBankTradeSerail(String bankTradeSerail) {
        this.bankTradeSerail = bankTradeSerail;
    }

    public String getSendEmployeeCode() {
        return sendEmployeeCode;
    }

    public void setSendEmployeeCode(String sendEmployeeCode) {
        this.sendEmployeeCode = sendEmployeeCode;
    }

    public String getNeedDepponCustomerCode() {
        return needDepponCustomerCode;
    }

    public void setNeedDepponCustomerCode(String needDepponCustomerCode) {
        this.needDepponCustomerCode = needDepponCustomerCode;
    }

    public String getIsSMS() {
        return isSMS;
    }

    public void setIsSMS(String isSMS) {
        this.isSMS = isSMS;
    }

    public String getMarketingCode() {
        return marketingCode;
    }

    public void setMarketingCode(String marketingCode) {
        this.marketingCode = marketingCode;
    }

    public String getMarketingName() {
        return marketingName;
    }

    public void setMarketingName(String marketingName) {
        this.marketingName = marketingName;
    }

	/**
	 * get快递各工时节点
	 */
	public String getTimeNode() {
		return timeNode;
	}

	/**
	 * set快递各工时节点
	 */
	public void setTimeNode(String timeNode) {
		this.timeNode = timeNode;
	}

	public String getOldWayBill() {
		return oldWayBill;
	}

	public void setOldWayBill(String oldWayBill) {
		this.oldWayBill = oldWayBill;
	}

	public String getIsReturnGoods() {
		return isReturnGoods;
	}

	public void setIsReturnGoods(String isReturnGoods) {
		this.isReturnGoods = isReturnGoods;
	}
    
    

}
