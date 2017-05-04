package com.deppon.foss.module.pickup.pricing.api.shared.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
/**
 * 
 * Copyright (C) 2015 Asiainfo-Linkage
 *
 *
 * @className:com.deppon.foss.module.pickup.pricing.api.shared.dto.ExpressDiscountDto
 * @description:快递折扣方案查询dto
 *
 * @version:v1.0.0
 * @author:DP-FOSS-YANGKANG
 *
 * Modification History:
 * Date         Author      Version     Description
 * -----------------------------------------------------------------
 * 2015-1-9     DP-FOSS-YANGKANG       v1.0.0        create
 *
 *
 */
public class ExpressDiscountDto implements Serializable {
	
	/**
	 *序列化ID
	 */
	private static final long serialVersionUID = 1L;
		//快递折扣方案ID
		private String expressDiscountPlanId;
		//明细信息ID
		private String id;
		// 版本信息
		private Long versionNo;
		// 创建时间
		private Date createTime;
		// 修改时间
		private Date modifyTime;
		// 创建人
		private String createUserCode;
		// 修改人
		private String modifyUserCode;
		// 创建部门
		private String createOrgCode;
		// 修改部门
		private String modifyOrgCode;
		//货物类型CODE
		private String goodsTypeCode;
		//货物类型ID
		private String goodsTypeId;
		//货物名字Name
		private String goodsTypeName;
		//产品类型
		private String productCode;
		//产品名称
		private String productName;
		//出发区域CODE
		private String startRegionCode;
		//出发区域名称
		private String startRegionName;
		//到达区域CODE
		private String arriveRegionCode;
		//到达区域名称
		private String arriveRegionName;
		//折扣费用类型
		private String pricingEntryCode;
		//折扣规则
		private String discountRule;
		//折扣规则名称
		private String discountRuleName;
		//币种
		private String currencyCode;
		//首重折扣
		private BigDecimal firstDiscountRate;
		//续重折扣
		private BigDecimal renewalDiscountRate;
		//开始范围
		private BigDecimal leftRange;
		//结束范围
		private BigDecimal rightRange;
		//业务日期
		private Date billTime;
		// 开始时间
		private Date beginTime;
		// 截止时间
		private Date endTime;
		// 方案状态
		private String active;
		//客户编码
		private String customerCode;
		//传参使用3级产品
		String productCodeL3;
		//续重最低费率
		private BigDecimal continueHeavyLowestRate;
		
		public BigDecimal getContinueHeavyLowestRate() {
			return continueHeavyLowestRate;
		}
		public void setContinueHeavyLowestRate(BigDecimal continueHeavyLowestRate) {
			this.continueHeavyLowestRate = continueHeavyLowestRate;
		}
		public String getProductCodeL3() {
			return productCodeL3;
		}
		public void setProductCodeL3(String productCodeL3) {
			this.productCodeL3 = productCodeL3;
		}
		public Long getVersionNo() {
			return versionNo;
		}
		public void setVersionNo(Long versionNo) {
			this.versionNo = versionNo;
		}
		public Date getCreateTime() {
			return createTime;
		}
		public void setCreateTime(Date createTime) {
			this.createTime = createTime;
		}
		public Date getModifyTime() {
			return modifyTime;
		}
		public void setModifyTime(Date modifyTime) {
			this.modifyTime = modifyTime;
		}
		public String getCreateUserCode() {
			return createUserCode;
		}
		public void setCreateUserCode(String createUserCode) {
			this.createUserCode = createUserCode;
		}
		public String getModifyUserCode() {
			return modifyUserCode;
		}
		public void setModifyUserCode(String modifyUserCode) {
			this.modifyUserCode = modifyUserCode;
		}
		public String getCreateOrgCode() {
			return createOrgCode;
		}
		public void setCreateOrgCode(String createOrgCode) {
			this.createOrgCode = createOrgCode;
		}
		public String getModifyOrgCode() {
			return modifyOrgCode;
		}
		public void setModifyOrgCode(String modifyOrgCode) {
			this.modifyOrgCode = modifyOrgCode;
		}
		
		public String getGoodsTypeCode() {
			return goodsTypeCode;
		}
		public void setGoodsTypeCode(String goodsTypeCode) {
			this.goodsTypeCode = goodsTypeCode;
		}
		public String getGoodsTypeId() {
			return goodsTypeId;
		}
		public void setGoodsTypeId(String goodsTypeId) {
			this.goodsTypeId = goodsTypeId;
		}
		public String getProductCode() {
			return productCode;
		}
		public void setProductCode(String productCode) {
			this.productCode = productCode;
		}
		public String getStartRegionCode() {
			return startRegionCode;
		}
		public void setStartRegionCode(String startRegionCode) {
			this.startRegionCode = startRegionCode;
		}
		public String getArriveRegionCode() {
			return arriveRegionCode;
		}
		public void setArriveRegionCode(String arriveRegionCode) {
			this.arriveRegionCode = arriveRegionCode;
		}
		public String getPricingEntryCode() {
			return pricingEntryCode;
		}
		public void setPricingEntryCode(String pricingEntryCode) {
			this.pricingEntryCode = pricingEntryCode;
		}
		public String getDiscountRule() {
			return discountRule;
		}
		public void setDiscountRule(String discountRule) {
			this.discountRule = discountRule;
		}
		public String getCurrencyCode() {
			return currencyCode;
		}
		public void setCurrencyCode(String currencyCode) {
			this.currencyCode = currencyCode;
		}
		public BigDecimal getFirstDiscountRate() {
			return firstDiscountRate;
		}
		public void setFirstDiscountRate(BigDecimal firstDiscountRate) {
			this.firstDiscountRate = firstDiscountRate;
		}
		public BigDecimal getRenewalDiscountRate() {
			return renewalDiscountRate;
		}
		public void setRenewalDiscountRate(BigDecimal renewalDiscountRate) {
			this.renewalDiscountRate = renewalDiscountRate;
		}
		public BigDecimal getLeftRange() {
			return leftRange;
		}
		public void setLeftRange(BigDecimal leftRange) {
			this.leftRange = leftRange;
		}
		public BigDecimal getRightRange() {
			return rightRange;
		}
		public void setRightRange(BigDecimal rightRange) {
			this.rightRange = rightRange;
		}
		public String getExpressDiscountPlanId() {
			return expressDiscountPlanId;
		}
		public void setExpressDiscountPlanId(String expressDiscountPlanId) {
			this.expressDiscountPlanId = expressDiscountPlanId;
		}
		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
		}
		public String getProductName() {
			return productName;
		}
		public void setProductName(String productName) {
			this.productName = productName;
		}
		public String getStartRegionName() {
			return startRegionName;
		}
		public void setStartRegionName(String startRegionName) {
			this.startRegionName = startRegionName;
		}
		public String getArriveRegionName() {
			return arriveRegionName;
		}
		public void setArriveRegionName(String arriveRegionName) {
			this.arriveRegionName = arriveRegionName;
		}
		public String getDiscountRuleName() {
			return discountRuleName;
		}
		public void setDiscountRuleName(String discountRuleName) {
			this.discountRuleName = discountRuleName;
		}
		public Date getBillTime() {
			return billTime;
		}
		public void setBillTime(Date billTime) {
			this.billTime = billTime;
		}
		public Date getBeginTime() {
			return beginTime;
		}
		public void setBeginTime(Date beginTime) {
			this.beginTime = beginTime;
		}
		public Date getEndTime() {
			return endTime;
		}
		public void setEndTime(Date endTime) {
			this.endTime = endTime;
		}
		public String getActive() {
			return active;
		}
		public void setActive(String active) {
			this.active = active;
		}
		public String getCustomerCode() {
			return customerCode;
		}
		public void setCustomerCode(String customerCode) {
			this.customerCode = customerCode;
		}
		public String getGoodsTypeName() {
			return goodsTypeName;
		}
		public void setGoodsTypeName(String goodsTypeName) {
			this.goodsTypeName = goodsTypeName;
		}
		
}
