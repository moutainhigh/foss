/**
 * Project Name:pkp-waybill-share
 * File Name:WaybillPictureDto.java
 * Package Name:com.deppon.foss.module.pickup.waybill.shared.dto
 * Date:2014-10-11上午9:37:22
 * Copyright (c) 2014, chenzhou1025@126.com All Rights Reserved.
 *
*/

package com.deppon.foss.module.pickup.waybill.shared.dto;

import java.math.BigDecimal;
import java.util.Date;

import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillPictureEntity;

/**
 * ClassName:WaybillPictureDto <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * 
 * Date:     2014-10-11 上午9:37:22 <br/>
 * @author   157229-zxy
 * @version  
 * @since    JDK 1.6
 */
public class WaybillPictureDto extends WaybillPictureEntity{
	/**
	 * 排除类型集合
	 */
	private String excludePendgingType;
	/**
	 * 包含类型集合
	 */
	private String includePendgingType;
	
	/**
	 * 起始时间
	 */
	private Date createTimeStart;
	
	/**
	 * 结束时间
	 */
	private Date createTimeEnd;
	/**
	 * 分页起始坐标
	 */
	private int start;
	/**
	 * 每页显示条数
	 */
	private int limit;
	
	/**
	 * 审单状态
	 * */
	private String checkbillstatus;
	/**
	 * 司机姓名
	 * */
	private String driverName;
	/**
	 * 收货部门
	 * */
    private String receiveorgname;
	/**
	 * 开单时间
	 * */
	private Date  billtime;
	/**
	 * 开单人姓名
	 * */
	private String operatorname;
	/**
	 * 发货人姓名
	 * */
	private String deliveryCustomerContact;
	

	/**
	 * 发货人手机号
	 * */
	private String deliveryCustomerMobilephone;
	/**
	 * 发货人电话
	 * */
	private String deliveryCustomerPhone;
	/**
	 * 收货人姓名
	 * */
	private String receiveCustomerContact;
	/**
	 * 收货人手机号
	 * */
	private String receiveCustomerMobilephone;
	/**
	 * 收货人电话
	 * */
	private String receiveCustomerPhone;

	/**
	 * 收货人地址
	 * */
	private String receiveCustomerAddress;
	/**
	 *  目的站
	 * */
	private String targetOrgCode;
	/**
	 *  对内备注
	 * */
	private String innerNotes;
	/**
	 *  纸包装
	 * */
	private int paperNum;  
	/**
	 *  总件数
	 * */
	private int goodsQtyTotal;
	/**
	 *  总重量
	 * */
	private int goodsWeightTotal;	
	/**
	 *  体积
	 * */
	private int goodsVolumeTotal;	
	/**
	 *  保价
	 * */
	private int insuranceAmount; 
	/**
	 *  代收
	 * */
	private int codAmount;
	/**
	 *  运输性质
	 * */
	private String productCode;
	/**
	 *  提货方式
	 * */
	private String receiveMethod; 
	/**
	 *  付款方式
	 * */
	private String orderPaidMethod; 
	/**
	 *  尺寸
	 * */
	private String goodsSize; 
	/**
	 *  反单类型
	 * */
	private String returnBillType;
	
	/**
	 *  退款类型
	 * */
	private String refundType;
	
	/**
	 *  收款账号
	 * */
	private String accountCode;
	
	/**
	 *  包装手续费
	 * */
	private String packageFee;
	
	/**
	 *  包装备注
	 * */
	private String packageRemark;
	
	/**
	 *  装卸费
	 * */
	private String dcServicefee;
	
	
	/**
	 * 
	 * 专用参数
	 */
	private BigDecimal  dgoodsWeightTotal ;
	private BigDecimal  dgoodsVolumeTotal ;
	private String strpaperNum;
	/**
	 * 货物品名
	 * */
	private String  goodsname;
	public String getGoodsname() {
		return goodsname;
	}

	public void setGoodsname(String goodsname) {
		this.goodsname = goodsname;
	}

	public String getDeliverycustomercode() {
		return deliverycustomercode;
	}

	public void setDeliverycustomercode(String deliverycustomercode) {
		this.deliverycustomercode = deliverycustomercode;
	}

	/**
	 * 客户编码
	 * */
	private String deliverycustomercode;
	public BigDecimal getDgoodsWeightTotal() {
		return dgoodsWeightTotal;
	}

	public void setDgoodsWeightTotal(BigDecimal dgoodsWeightTotal) {
		this.dgoodsWeightTotal = dgoodsWeightTotal;
	}

	public BigDecimal getDgoodsVolumeTotal() {
		return dgoodsVolumeTotal;
	}

	public void setDgoodsVolumeTotal(BigDecimal dgoodsVolumeTotal) {
		this.dgoodsVolumeTotal = dgoodsVolumeTotal;
	}

	public String getStrpaperNum() {
		return strpaperNum;
	}

	public void setStrpaperNum(String strpaperNum) {
		this.strpaperNum = strpaperNum;
	}
	public String getDeliveryCustomerContact() {
		return deliveryCustomerContact;
	}

	public void setDeliveryCustomerContact(String deliveryCustomerContact) {
		this.deliveryCustomerContact = deliveryCustomerContact;
	}

	public String getDeliveryCustomerMobilephone() {
		return deliveryCustomerMobilephone;
	}

	public void setDeliveryCustomerMobilephone(String deliveryCustomerMobilephone) {
		this.deliveryCustomerMobilephone = deliveryCustomerMobilephone;
	}

	public String getReceiveCustomerContact() {
		return receiveCustomerContact;
	}

	public void setReceiveCustomerContact(String receiveCustomerContact) {
		this.receiveCustomerContact = receiveCustomerContact;
	}

	public String getReceiveCustomerMobilephone() {
		return receiveCustomerMobilephone;
	}

	public void setReceiveCustomerMobilephone(String receiveCustomerMobilephone) {
		this.receiveCustomerMobilephone = receiveCustomerMobilephone;
	}

	public String getReceiveCustomerAddress() {
		return receiveCustomerAddress;
	}

	public void setReceiveCustomerAddress(String receiveCustomerAddress) {
		this.receiveCustomerAddress = receiveCustomerAddress;
	}

	public String getTargetOrgCode() {
		return targetOrgCode;
	}

	public void setTargetOrgCode(String targetOrgCode) {
		this.targetOrgCode = targetOrgCode;
	}

	public String getReceiveCustomerPhone() {
		return receiveCustomerPhone;
	}

	public void setReceiveCustomerPhone(String receiveCustomerPhone) {
		this.receiveCustomerPhone = receiveCustomerPhone;
	}
	public String getInnerNotes() {
		return innerNotes;
	}

	public void setInnerNotes(String innerNotes) {
		this.innerNotes = innerNotes;
	}

	public int getPaperNum() {
		return paperNum;
	}

	public void setPaperNum(int paperNum) {
		this.paperNum = paperNum;
	}

	public int getGoodsQtyTotal() {
		return goodsQtyTotal;
	}

	public void setGoodsQtyTotal(int goodsQtyTotal) {
		this.goodsQtyTotal = goodsQtyTotal;
	}

	public int getGoodsWeightTotal() {
		return goodsWeightTotal;
	}

	public void setGoodsWeightTotal(int goodsWeightTotal) {
		this.goodsWeightTotal = goodsWeightTotal;
	}

	public int getGoodsVolumeTotal() {
		return goodsVolumeTotal;
	}

	public void setGoodsVolumeTotal(int goodsVolumeTotal) {
		this.goodsVolumeTotal = goodsVolumeTotal;
	}

	public int getInsuranceAmount() {
		return insuranceAmount;
	}

	public void setInsuranceAmount(int insuranceAmount) {
		this.insuranceAmount = insuranceAmount;
	}

	public int getCodAmount() {
		return codAmount;
	}

	public void setCodAmount(int codAmount) {
		this.codAmount = codAmount;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public String getReceiveMethod() {
		return receiveMethod;
	}

	public void setReceiveMethod(String receiveMethod) {
		this.receiveMethod = receiveMethod;
	}

	public String getOrderPaidMethod() {
		return orderPaidMethod;
	}

	public void setOrderPaidMethod(String orderPaidMethod) {
		this.orderPaidMethod = orderPaidMethod;
	}

	public String getGoodsSize() {
		return goodsSize;
	}

	public void setGoodsSize(String goodsSize) {
		this.goodsSize = goodsSize;
	}

	public String getReturnBillType() {
		return returnBillType;
	}

	public void setReturnBillType(String returnBillType) {
		this.returnBillType = returnBillType;
	}
	public String getCheckbillstatus() {
		return checkbillstatus;
	}

	public void setCheckbillstatus(String checkbillstatus) {
		this.checkbillstatus = checkbillstatus;
	}

	public String getOperatorname() {
		return operatorname;
	}

	public void setOperatorname(String operatorname) {
		this.operatorname = operatorname;
	}

	public String getReceiveorgname() {
		return receiveorgname;
	}

	public void setReceiveorgname(String receiveorgname) {
		this.receiveorgname = receiveorgname;
	}

	public Date getBilltime() {
		return billtime;
	}

	public void setBilltime(Date billtime) {
		this.billtime = billtime;
	}

	public String getDriverName() {
		return driverName;
	}

	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}

	public Date getCreateTimeStart() {
		return createTimeStart;
	}

	public void setCreateTimeStart(Date createTimeStart) {
		this.createTimeStart = createTimeStart;
	}

	public Date getCreateTimeEnd() {
		return createTimeEnd;
	}

	public void setCreateTimeEnd(Date createTimeEnd) {
		this.createTimeEnd = createTimeEnd;
	}

	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	public String getExcludePendgingType() {
		return excludePendgingType;
	}

	public void setExcludePendgingType(String excludePendgingType) {
		this.excludePendgingType = excludePendgingType;
	}

	public String getIncludePendgingType() {
		return includePendgingType;
	}

	public void setIncludePendgingType(String includePendgingType) {
		this.includePendgingType = includePendgingType;
	}
	public String getDeliveryCustomerPhone() {
		return deliveryCustomerPhone;
	}

	public void setDeliveryCustomerPhone(String deliveryCustomerPhone) {
		this.deliveryCustomerPhone = deliveryCustomerPhone;
	}

	public String getRefundType() {
		return refundType;
	}

	public void setRefundType(String refundType) {
		this.refundType = refundType;
	}

	public String getAccountCode() {
		return accountCode;
	}

	public void setAccountCode(String accountCode) {
		this.accountCode = accountCode;
	}

	public String getPackageFee() {
		return packageFee;
	}

	public void setPackageFee(String packageFee) {
		this.packageFee = packageFee;
	}

	public String getPackageRemark() {
		return packageRemark;
	}

	public void setPackageRemark(String packageRemark) {
		this.packageRemark = packageRemark;
	}

	public String getDcServicefee() {
		return dcServicefee;
	}

	public void setDcServicefee(String dcServicefee) {
		this.dcServicefee = dcServicefee;
	}
	
}

