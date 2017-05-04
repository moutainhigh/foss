package com.deppon.pda.bdm.module.foss.accept.shared.domain.electronicbill;

import java.io.Serializable;
import java.util.Date;

/**   
 * @ClassName CustomEWaybillEntity  
 * @Description 下拉客户订单明细 
 * @author  092038 张贞献  
 * @date 2014-8-24    
 */ 
public class IndividualCustomEWaybillEntity  implements Serializable{
	/**  
	 * @Fields serialVersionUID : TODO 
	 */
	private static final long serialVersionUID = 1L;
	//订单号
	private String orderCode;
	//运单号
	private String waybillCode;	
	//快递收入部门编码
    private String revenueCode;
	//快递收入部门名称
     private String evenueName;

   //目的站
    private String destDeptName;
   //目的站code
    private String destDeptCode;	
   //第二城市
    private String secCity;
   //第二外场
    private String secField;
   //最终外场
    private String finalField;
   //路由明细
    private String routeDetail;

	//收货客户姓名
    private String addresseeName;
	//收货人地址
    private String addresseeAddr;
	//收货客户手机
    private String addresseeTel;
	//运输方式
    private String transType;
	//提货方式
    private String takeType;
	//付款方式
    private String payType;
	//货物名称(品名) 
    private String goodsName;
	//件数
    private int pieces;
	//重量
    private String weight;
	//体积
    private String volume;
	//保险声明价值
    private String insuredAmount;
	//代收货款类型
    private String reciveLoanType;
	//代收货款金额
    private String reviceMoneyAmount;
	//代收货款账号
    private String reviceLoadAccount;
	//返单类别
    private String returnBillingType;
	//下单时间
    private Date orderTime;
/*********************************************************    
	*散客专有字段 
**********************************************************/   
  //发货人客户姓名
    private String senderName;
  //发货人地址	
    private String senderAddr;
  //发货人手机号
    private String senderTel;
  //备注
    private String remark;
  //优惠券
    private String couponNum;
  //最早接货时间
    private Date firstAcctTime;
  //最晚接货时间
    private Date lastAcctTime;
  //订单渠道
    private String channelCode;
  //是否采集
    private String isCollectGps;
 
  //是否打印五角星
	private String isStarFlag;
  //是否外发	
	private String isPrintAt;
    
	public String getOrderCode() {
		return orderCode;
	}
	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}
	public String getWaybillCode() {
		return waybillCode;
	}
	public void setWaybillCode(String waybillCode) {
		this.waybillCode = waybillCode;
	}
	public String getRevenueCode() {
		return revenueCode;
	}
	public void setRevenueCode(String revenueCode) {
		this.revenueCode = revenueCode;
	}
	public String getEvenueName() {
		return evenueName;
	}
	public void setEvenueName(String evenueName) {
		this.evenueName = evenueName;
	}
	public String getDestDeptName() {
		return destDeptName;
	}
	public void setDestDeptName(String destDeptName) {
		this.destDeptName = destDeptName;
	}
	public String getSecCity() {
		return secCity;
	}
	public void setSecCity(String secCity) {
		this.secCity = secCity;
	}
	public String getSecField() {
		return secField;
	}
	public void setSecField(String secField) {
		this.secField = secField;
	}
	public String getFinalField() {
		return finalField;
	}
	public void setFinalField(String finalField) {
		this.finalField = finalField;
	}
	public String getRouteDetail() {
		return routeDetail;
	}
	public void setRouteDetail(String routeDetail) {
		this.routeDetail = routeDetail;
	}
	public String getAddresseeName() {
		return addresseeName;
	}
	public void setAddresseeName(String addresseeName) {
		this.addresseeName = addresseeName;
	}
	public String getAddresseeAddr() {
		return addresseeAddr;
	}
	public void setAddresseeAddr(String addresseeAddr) {
		this.addresseeAddr = addresseeAddr;
	}
	public String getAddresseeTel() {
		return addresseeTel;
	}
	public void setAddresseeTel(String addresseeTel) {
		this.addresseeTel = addresseeTel;
	}
	public String getTransType() {
		return transType;
	}
	public void setTransType(String transType) {
		this.transType = transType;
	}
	public String getTakeType() {
		return takeType;
	}
	public void setTakeType(String takeType) {
		this.takeType = takeType;
	}
	public String getPayType() {
		return payType;
	}
	public void setPayType(String payType) {
		this.payType = payType;
	}
	public String getGoodsName() {
		return goodsName;
	}
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}
	public int getPieces() {
		return pieces;
	}
	public void setPieces(int pieces) {
		this.pieces = pieces;
	}
	public String getWeight() {
		return weight;
	}
	public void setWeight(String weight) {
		this.weight = weight;
	}
	public String getVolume() {
		return volume;
	}
	public void setVolume(String volume) {
		this.volume = volume;
	}
	public String getInsuredAmount() {
		return insuredAmount;
	}
	public void setInsuredAmount(String insuredAmount) {
		this.insuredAmount = insuredAmount;
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
	public String getReviceLoadAccount() {
		return reviceLoadAccount;
	}
	public void setReviceLoadAccount(String reviceLoadAccount) {
		this.reviceLoadAccount = reviceLoadAccount;
	}
	public String getReturnBillingType() {
		return returnBillingType;
	}
	public void setReturnBillingType(String returnBillingType) {
		this.returnBillingType = returnBillingType;
	}
	public Date getOrderTime() {
		return orderTime;
	}
	public void setOrderTime(Date orderTime) {
		this.orderTime = orderTime;
	}
	public String getSenderName() {
		return senderName;
	}
	public void setSenderName(String senderName) {
		this.senderName = senderName;
	}
	public String getSenderAddr() {
		return senderAddr;
	}
	public void setSenderAddr(String senderAddr) {
		this.senderAddr = senderAddr;
	}
	public String getSenderTel() {
		return senderTel;
	}
	public void setSenderTel(String senderTel) {
		this.senderTel = senderTel;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getCouponNum() {
		return couponNum;
	}
	public void setCouponNum(String couponNum) {
		this.couponNum = couponNum;
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
	public String getChannelCode() {
		return channelCode;
	}
	public void setChannelCode(String channelCode) {
		this.channelCode = channelCode;
	}
	public String getIsCollectGps() {
		return isCollectGps;
	}
	public void setIsCollectGps(String isCollectGps) {
		this.isCollectGps = isCollectGps;
	}
	public String getIsStarFlag() {
		return isStarFlag;
	}
	public void setIsStarFlag(String isStarFlag) {
		this.isStarFlag = isStarFlag;
	}
	public String getIsPrintAt() {
		return isPrintAt;
	}
	public void setIsPrintAt(String isPrintAt) {
		this.isPrintAt = isPrintAt;
	}
	public String getDestDeptCode() {
		return destDeptCode;
	}
	public void setDestDeptCode(String destDeptCode) {
		this.destDeptCode = destDeptCode;
	}
	
}
