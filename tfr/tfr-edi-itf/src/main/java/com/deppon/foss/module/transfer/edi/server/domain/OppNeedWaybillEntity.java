
package com.deppon.foss.module.transfer.edi.server.domain;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 
* @description OPP系统需要的运单实体对应
* @version 1.0
* @author 269701-foss-lln
* @update 2016年5月15日 上午11:22:00
 */
public class OppNeedWaybillEntity implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 3804228315728929322L;

	/**
	 * 运单号
	 */
	private String waybillNo;

	/**
	 * 收货客户名称
	 */
	private String receiveCustomerName;

	/**
	 * 收货客户手机
	 */
	private String receiveCustomerMobilephone;

	/**
	 * 收货客户电话
	 */
	private String receiveCustomerPhone;

	/**
	 * 收货客户联系人
	 */
	private String receiveCustomerContact;

	/**
	 * 收货具体地址
	 */
	private String receiveCustomerAddress;

	/**
	 * 收货部门(出发部门)
	 */
	private String receiveOrgCode;

	/**
	 * 提货方式
	 */
	private String receiveMethod;

	/**
	 * 目的站
	 */
	private String targetOrgCode;
	/**
	 * 货物名称
	 */
	private String goodsName;

	/**
	 * 货物总件数
	 */
	private BigDecimal goodsQtyTotal;

	/**
	 * 货物总重量
	 */
	private BigDecimal goodsWeightTotal;

	/**
	 * 货物总体积
	 */
	private BigDecimal goodsVolumeTotal;

	/**
	 * 货物尺寸
	 */
	private String goodsSize;

	/**
	 * 货物包装
	 */
	private String goodsPackage;

	/**
	 * 代收货款
	 */
	private BigDecimal codAmount;

	/**
	 * 到付金额
	 */
	private BigDecimal toPayAmount;

	/**
	 * 送货费
	 */
	private BigDecimal deliveryGoodsFee;

	/**
	 * 开单付款方式
	 */
	private String paidMethod;

	/**
	 * 总费用
	 */
	private BigDecimal totalFee;

	/**
	 * 计费重量
	 */
	private BigDecimal billWeight;

	/**
	 * 运单状态
	 */
	private String active;
	/**
	 * 币种
	 */
	private String currencyCode;

	/**
	 * @return waybillNo : return the property waybillNo.
	 * @author 269701-foss-lln
	 * @update 2016年5月13日 下午5:06:34
	 * @version V1.0
	 */
	public String getWaybillNo() {
		return waybillNo;
	}

	/**
	 * @param waybillNo : set the property waybillNo.
	 * @author 269701-foss-lln
	 * @update 2016年5月13日 下午5:06:34
	 * @version V1.0
	 */
	
	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}

	/**
	 * @return receiveCustomerName : return the property receiveCustomerName.
	 * @author 269701-foss-lln
	 * @update 2016年5月13日 下午5:06:34
	 * @version V1.0
	 */
	public String getReceiveCustomerName() {
		return receiveCustomerName;
	}

	/**
	 * @param receiveCustomerName : set the property receiveCustomerName.
	 * @author 269701-foss-lln
	 * @update 2016年5月13日 下午5:06:34
	 * @version V1.0
	 */
	
	public void setReceiveCustomerName(String receiveCustomerName) {
		this.receiveCustomerName = receiveCustomerName;
	}

	/**
	 * @return receiveCustomerMobilephone : return the property receiveCustomerMobilephone.
	 * @author 269701-foss-lln
	 * @update 2016年5月13日 下午5:06:34
	 * @version V1.0
	 */
	public String getReceiveCustomerMobilephone() {
		return receiveCustomerMobilephone;
	}

	/**
	 * @param receiveCustomerMobilephone : set the property receiveCustomerMobilephone.
	 * @author 269701-foss-lln
	 * @update 2016年5月13日 下午5:06:34
	 * @version V1.0
	 */
	
	public void setReceiveCustomerMobilephone(String receiveCustomerMobilephone) {
		this.receiveCustomerMobilephone = receiveCustomerMobilephone;
	}

	/**
	 * @return receiveCustomerPhone : return the property receiveCustomerPhone.
	 * @author 269701-foss-lln
	 * @update 2016年5月13日 下午5:06:34
	 * @version V1.0
	 */
	public String getReceiveCustomerPhone() {
		return receiveCustomerPhone;
	}

	/**
	 * @param receiveCustomerPhone : set the property receiveCustomerPhone.
	 * @author 269701-foss-lln
	 * @update 2016年5月13日 下午5:06:34
	 * @version V1.0
	 */
	
	public void setReceiveCustomerPhone(String receiveCustomerPhone) {
		this.receiveCustomerPhone = receiveCustomerPhone;
	}

	/**
	 * @return receiveCustomerContact : return the property receiveCustomerContact.
	 * @author 269701-foss-lln
	 * @update 2016年5月13日 下午5:06:34
	 * @version V1.0
	 */
	public String getReceiveCustomerContact() {
		return receiveCustomerContact;
	}

	/**
	 * @param receiveCustomerContact : set the property receiveCustomerContact.
	 * @author 269701-foss-lln
	 * @update 2016年5月13日 下午5:06:34
	 * @version V1.0
	 */
	
	public void setReceiveCustomerContact(String receiveCustomerContact) {
		this.receiveCustomerContact = receiveCustomerContact;
	}

	/**
	 * @return receiveCustomerAddress : return the property receiveCustomerAddress.
	 * @author 269701-foss-lln
	 * @update 2016年5月13日 下午5:06:34
	 * @version V1.0
	 */
	public String getReceiveCustomerAddress() {
		return receiveCustomerAddress;
	}

	/**
	 * @param receiveCustomerAddress : set the property receiveCustomerAddress.
	 * @author 269701-foss-lln
	 * @update 2016年5月13日 下午5:06:34
	 * @version V1.0
	 */
	
	public void setReceiveCustomerAddress(String receiveCustomerAddress) {
		this.receiveCustomerAddress = receiveCustomerAddress;
	}

	/**
	 * @return receiveOrgCode : return the property receiveOrgCode.
	 * @author 269701-foss-lln
	 * @update 2016年5月13日 下午5:06:34
	 * @version V1.0
	 */
	public String getReceiveOrgCode() {
		return receiveOrgCode;
	}

	/**
	 * @param receiveOrgCode : set the property receiveOrgCode.
	 * @author 269701-foss-lln
	 * @update 2016年5月13日 下午5:06:34
	 * @version V1.0
	 */
	
	public void setReceiveOrgCode(String receiveOrgCode) {
		this.receiveOrgCode = receiveOrgCode;
	}

	/**
	 * @return receiveMethod : return the property receiveMethod.
	 * @author 269701-foss-lln
	 * @update 2016年5月13日 下午5:06:34
	 * @version V1.0
	 */
	public String getReceiveMethod() {
		return receiveMethod;
	}

	/**
	 * @param receiveMethod : set the property receiveMethod.
	 * @author 269701-foss-lln
	 * @update 2016年5月13日 下午5:06:34
	 * @version V1.0
	 */
	
	public void setReceiveMethod(String receiveMethod) {
		this.receiveMethod = receiveMethod;
	}

	/**
	 * @return targetOrgCode : return the property targetOrgCode.
	 * @author 269701-foss-lln
	 * @update 2016年5月13日 下午5:06:34
	 * @version V1.0
	 */
	public String getTargetOrgCode() {
		return targetOrgCode;
	}

	/**
	 * @param targetOrgCode : set the property targetOrgCode.
	 * @author 269701-foss-lln
	 * @update 2016年5月13日 下午5:06:34
	 * @version V1.0
	 */
	
	public void setTargetOrgCode(String targetOrgCode) {
		this.targetOrgCode = targetOrgCode;
	}

	/**
	 * @return goodsName : return the property goodsName.
	 * @author 269701-foss-lln
	 * @update 2016年5月13日 下午5:06:34
	 * @version V1.0
	 */
	public String getGoodsName() {
		return goodsName;
	}

	/**
	 * @param goodsName : set the property goodsName.
	 * @author 269701-foss-lln
	 * @update 2016年5月13日 下午5:06:34
	 * @version V1.0
	 */
	
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}



	public BigDecimal getGoodsQtyTotal() {
		return goodsQtyTotal;
	}

	public void setGoodsQtyTotal(BigDecimal goodsQtyTotal) {
		this.goodsQtyTotal = goodsQtyTotal;
	}

	/**
	 * @return goodsWeightTotal : return the property goodsWeightTotal.
	 * @author 269701-foss-lln
	 * @update 2016年5月13日 下午5:06:34
	 * @version V1.0
	 */
	public BigDecimal getGoodsWeightTotal() {
		return goodsWeightTotal;
	}

	/**
	 * @param goodsWeightTotal : set the property goodsWeightTotal.
	 * @author 269701-foss-lln
	 * @update 2016年5月13日 下午5:06:34
	 * @version V1.0
	 */
	
	public void setGoodsWeightTotal(BigDecimal goodsWeightTotal) {
		this.goodsWeightTotal = goodsWeightTotal;
	}

	/**
	 * @return goodsVolumeTotal : return the property goodsVolumeTotal.
	 * @author 269701-foss-lln
	 * @update 2016年5月13日 下午5:06:34
	 * @version V1.0
	 */
	public BigDecimal getGoodsVolumeTotal() {
		return goodsVolumeTotal;
	}

	/**
	 * @param goodsVolumeTotal : set the property goodsVolumeTotal.
	 * @author 269701-foss-lln
	 * @update 2016年5月13日 下午5:06:34
	 * @version V1.0
	 */
	
	public void setGoodsVolumeTotal(BigDecimal goodsVolumeTotal) {
		this.goodsVolumeTotal = goodsVolumeTotal;
	}

	/**
	 * @return goodsSize : return the property goodsSize.
	 * @author 269701-foss-lln
	 * @update 2016年5月13日 下午5:06:34
	 * @version V1.0
	 */
	public String getGoodsSize() {
		return goodsSize;
	}

	/**
	 * @param goodsSize : set the property goodsSize.
	 * @author 269701-foss-lln
	 * @update 2016年5月13日 下午5:06:34
	 * @version V1.0
	 */
	
	public void setGoodsSize(String goodsSize) {
		this.goodsSize = goodsSize;
	}

	/**
	 * @return goodsPackage : return the property goodsPackage.
	 * @author 269701-foss-lln
	 * @update 2016年5月13日 下午5:06:34
	 * @version V1.0
	 */
	public String getGoodsPackage() {
		return goodsPackage;
	}

	/**
	 * @param goodsPackage : set the property goodsPackage.
	 * @author 269701-foss-lln
	 * @update 2016年5月13日 下午5:06:34
	 * @version V1.0
	 */
	
	public void setGoodsPackage(String goodsPackage) {
		this.goodsPackage = goodsPackage;
	}

	/**
	 * @return codAmount : return the property codAmount.
	 * @author 269701-foss-lln
	 * @update 2016年5月13日 下午5:06:34
	 * @version V1.0
	 */
	public BigDecimal getCodAmount() {
		return codAmount;
	}

	/**
	 * @param codAmount : set the property codAmount.
	 * @author 269701-foss-lln
	 * @update 2016年5月13日 下午5:06:34
	 * @version V1.0
	 */
	
	public void setCodAmount(BigDecimal codAmount) {
		this.codAmount = codAmount;
	}

	/**
	 * @return toPayAmount : return the property toPayAmount.
	 * @author 269701-foss-lln
	 * @update 2016年5月13日 下午5:06:34
	 * @version V1.0
	 */
	public BigDecimal getToPayAmount() {
		return toPayAmount;
	}

	/**
	 * @param toPayAmount : set the property toPayAmount.
	 * @author 269701-foss-lln
	 * @update 2016年5月13日 下午5:06:34
	 * @version V1.0
	 */
	
	public void setToPayAmount(BigDecimal toPayAmount) {
		this.toPayAmount = toPayAmount;
	}

	/**
	 * @return deliveryGoodsFee : return the property deliveryGoodsFee.
	 * @author 269701-foss-lln
	 * @update 2016年5月13日 下午5:06:34
	 * @version V1.0
	 */
	public BigDecimal getDeliveryGoodsFee() {
		return deliveryGoodsFee;
	}

	/**
	 * @param deliveryGoodsFee : set the property deliveryGoodsFee.
	 * @author 269701-foss-lln
	 * @update 2016年5月13日 下午5:06:34
	 * @version V1.0
	 */
	
	public void setDeliveryGoodsFee(BigDecimal deliveryGoodsFee) {
		this.deliveryGoodsFee = deliveryGoodsFee;
	}

	/**
	 * @return paidMethod : return the property paidMethod.
	 * @author 269701-foss-lln
	 * @update 2016年5月13日 下午5:06:34
	 * @version V1.0
	 */
	public String getPaidMethod() {
		return paidMethod;
	}

	/**
	 * @param paidMethod : set the property paidMethod.
	 * @author 269701-foss-lln
	 * @update 2016年5月13日 下午5:06:34
	 * @version V1.0
	 */
	
	public void setPaidMethod(String paidMethod) {
		this.paidMethod = paidMethod;
	}

	/**
	 * @return totalFee : return the property totalFee.
	 * @author 269701-foss-lln
	 * @update 2016年5月13日 下午5:06:34
	 * @version V1.0
	 */
	public BigDecimal getTotalFee() {
		return totalFee;
	}

	/**
	 * @param totalFee : set the property totalFee.
	 * @author 269701-foss-lln
	 * @update 2016年5月13日 下午5:06:34
	 * @version V1.0
	 */
	
	public void setTotalFee(BigDecimal totalFee) {
		this.totalFee = totalFee;
	}

	/**
	 * @return billWeight : return the property billWeight.
	 * @author 269701-foss-lln
	 * @update 2016年5月13日 下午5:06:34
	 * @version V1.0
	 */
	public BigDecimal getBillWeight() {
		return billWeight;
	}

	/**
	 * @param billWeight : set the property billWeight.
	 * @author 269701-foss-lln
	 * @update 2016年5月13日 下午5:06:34
	 * @version V1.0
	 */
	
	public void setBillWeight(BigDecimal billWeight) {
		this.billWeight = billWeight;
	}

	/**
	 * @return active : return the property active.
	 * @author 269701-foss-lln
	 * @update 2016年5月13日 下午5:06:34
	 * @version V1.0
	 */
	public String getActive() {
		return active;
	}

	/**
	 * @param active : set the property active.
	 * @author 269701-foss-lln
	 * @update 2016年5月13日 下午5:06:34
	 * @version V1.0
	 */
	
	public void setActive(String active) {
		this.active = active;
	}

	/**
	 * @return currencyCode : return the property currencyCode.
	 * @author 269701-foss-lln
	 * @update 2016年5月15日 上午11:22:36
	 * @version V1.0
	 */
	public String getCurrencyCode() {
		return currencyCode;
	}

	/**
	 * @param currencyCode : set the property currencyCode.
	 * @author 269701-foss-lln
	 * @update 2016年5月15日 上午11:22:36
	 * @version V1.0
	 */
	
	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}


}
