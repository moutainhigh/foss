package com.deppon.pda.bdm.module.foss.delivery.shared.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 
  * @ClassName DeryCrgDetail 
  * @Description 派送任务返回明细实体 
  * @author 245955 
  * @date 2015-06-26
 */
public class DeryCrgDetailEntity implements Serializable {
	private static final long serialVersionUID = -1507367689302343915L;
	//运单号
	private String wblCode;
	//日期
	private Date deryDate;
	//客户名称
	private String customerName;
	//客户手机号
	private String customerPhone;
	//客户地址
	private String customerAddress;
	//到付金额
	private double amount;
	//代收货款
	private double paymentAmt;
	//包装
	private String wrapType;
	//件数
	private int pieces;
	//重量
	private double weight;
	//体积	
	private double volume;
	//备注
	private String remark;
	//是否贵重物品
	private String isVal;
	//到达联编号
	private String arriveSheetNo;
	//流水号
	private List<String> labelCodes;
	//是否采集地址  (Y/N)
	private String isCollectGps; 
	//是否派送超时
	private String isDeryOvertime;
	//投诉变更状态
	private String complainStatus;
	//关联单号费用
	private String relBillFee;
	//应收总金额
	private String totalCost;
	//是否返货
	private String isRetrunGoods;
	//出发部门
	private  String startDeptCode;
	//发货人地址
	private  String  sendAddress;
	//签收单返单类型
	private  String receiptType;
	//是否开定额发票的字段
	//author:245960 Date:2015-06-19 comment:需求编号：DN201503300026  加一个“是否开定额发票的字段”
	private String arriveCentralizedSettlement;
	//开单总件数  author:245955  Date:2015-09-17  加一个"开单总件数"
	private Integer goodsQtyTotal;
	//子母件总件数 author:245955  Date:2015-09-17 
	private Integer twoInOneQty;
	//客户联系电话  author:268974 手机APP送货端手机/固定电话
	private String customerTelephonePhone; 
	//规定兑现时间  author：354682 
	private Date cashTime;
  
	public Date getCashTime() {
		return cashTime;
	}
	public void setCashTime(Date cashTime) {
		this.cashTime = cashTime;
	}
	public String getCustomerTelephonePhone() {
		return customerTelephonePhone;
	}
	public void setCustomerTelephonePhone(String customerTelephonePhone) {
		this.customerTelephonePhone = customerTelephonePhone;
	}
	public Integer getTwoInOneQty() {
		return twoInOneQty;
	}
	public void setTwoInOneQty(Integer twoInOneQty) {
		this.twoInOneQty = twoInOneQty;
	}
	public Integer getGoodsQtyTotal() {
		return goodsQtyTotal;
	}
	public void setGoodsQtyTotal(Integer goodsQtyTotal) {
		this.goodsQtyTotal = goodsQtyTotal;
	}
	//母件单号  
	//author:245955 Date:2015-09-11 
	private String parentWaybillNo;
	
	

	public String getParentWaybillNo() {
		return parentWaybillNo;
	}
	public void setParentWaybillNo(String parentWaybillNo) {
		this.parentWaybillNo = parentWaybillNo;
	}	
	public String getArriveCentralizedSettlement() {
		return arriveCentralizedSettlement;
	}
	public void setArriveCentralizedSettlement(String arriveCentralizedSettlement) {
		this.arriveCentralizedSettlement = arriveCentralizedSettlement;
	}
	/**
	 * @return the isDeryOvertime
	 */
	public String getIsDeryOvertime() {
		return isDeryOvertime;
	}
	/**
	 * @param isDeryOvertime the isDeryOvertime to set
	 */
	public void setIsDeryOvertime(String isDeryOvertime) {
		this.isDeryOvertime = isDeryOvertime;
	}
	public String getArriveSheetNo() {
		return arriveSheetNo;
	}
	public void setArriveSheetNo(String arriveSheetNo) {
		this.arriveSheetNo = arriveSheetNo;
	}
	public String getWblCode() {
		return wblCode;
	}
	public void setWblCode(String wblCode) {
		this.wblCode = wblCode;
	}
	public Date getDeryDate() {
		return deryDate;
	}
	public void setDeryDate(Date deryDate) {
		this.deryDate = deryDate;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getCustomerPhone() {
		return customerPhone;
	}
	public void setCustomerPhone(String customerPhone) {
		this.customerPhone = customerPhone;
	}
	public String getCustomerAddress() {
		return customerAddress;
	}
	public void setCustomerAddress(String customerAddress) {
		this.customerAddress = customerAddress;
	}
	
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public double getPaymentAmt() {
		return paymentAmt;
	}
	public void setPaymentAmt(double paymentAmt) {
		this.paymentAmt = paymentAmt;
	}
	public String getWrapType() {
		return wrapType;
	}
	public void setWrapType(String wrapType) {
		this.wrapType = wrapType;
	}
	public int getPieces() {
		return pieces;
	}
	public void setPieces(int pieces) {
		this.pieces = pieces;
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
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getIsVal() {
		return isVal;
	}
	public void setIsVal(String isVal) {
		this.isVal = isVal;
	}
	public List<String> getLabelCodes() {
		return labelCodes;
	}
	public void setLabelCodes(List<String> labelCodes) {
		this.labelCodes = labelCodes;
	}
    public String getIsCollectGps() {
        return isCollectGps;
    }
    public void setIsCollectGps(String isCollectGps) {
        this.isCollectGps = isCollectGps;
    }
	public String getComplainStatus() {
		return complainStatus;
	}
	public void setComplainStatus(String complainStatus) {
		this.complainStatus = complainStatus;
	}
	public String getRelBillFee() {
		return relBillFee;
	}
	public void setRelBillFee(String relBillFee) {
		this.relBillFee = relBillFee;
	}
	public String getTotalCost() {
		return totalCost;
	}
	public void setTotalCost(String totalCost) {
		this.totalCost = totalCost;
	}
	public String getIsRetrunGoods() {
		return isRetrunGoods;
	}
	public void setIsRetrunGoods(String isRetrunGoods) {
		this.isRetrunGoods = isRetrunGoods;
	}
	public String getStartDeptCode() {
		return startDeptCode;
	}
	public void setStartDeptCode(String startDeptCode) {
		this.startDeptCode = startDeptCode;
	}
	public String getSendAddress() {
		return sendAddress;
	}
	public void setSendAddress(String sendAddress) {
		this.sendAddress = sendAddress;
	}
	public String getReceiptType() {
		return receiptType;
	}
	public void setReceiptType(String receiptType) {
		this.receiptType = receiptType;
	}
	
}
