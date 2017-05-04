package com.deppon.foss.module.base.baseinfo.api.shared.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.ProductItemEntity;

/**
 * 价格发券方案明细dto
 * @author dujunhui-187862
 * @date 2014-10-8 上午10:34:28
 * @since
 * @version
 */
public class PriceCouponDto implements Serializable{

	/**
	 * 序列ID
	 */
	private static final long serialVersionUID = -2486994110658450367L;

	/**
	 * 折扣(折扣明细、优惠明细)ID
	 */
	private String discountId;
	
	/**
	 * 优先级类型、产品、渠道、客户
	 */
	private String type;
	/**
	 * 优先级类型名称、产品、渠道、客户
	 */
	private String typeName;
	/**
	 * 费用类型(增值费用明细或者运费明细)id
	 */
	private String chargeDetailId;
	/**
	 * 市场ID
	 */
	private String marketId;
	/**
	 * 市场CODE
	 */
	private String marketCode;
	/**
	 * 市场NAME
	 */
	private String marketName;
	/**
	 * 市场活动类型
	 */
	private String marketType;
	/**
	 * 折扣方案开始时间
	 */
	private Date beginDate;
	/**
	 * 折扣方案结束时间
	 */
	private Date endDate;
	 /**
     * 线路区域要求
     */
    private String lineRegion;
    
    /**
     * 优惠券有效期
     */
    private String availablePeriod;
    /**
     * 客户行业编码
     */
    private String customerProfessionCode;
    /**
     * 客户行业
     */
    private String customerProfession;
    /**
     * 客户等级
     */
    private String customerDegree;
    /**
     * 客户等级编码
     */
    private String customerDegreeCode;
    /**
     * 订单来源
     */
    private String orderSource;
    /**
     * 订单来源编码
     */
    private String orderSourceCode;
    /**
     * 产品类型
     */
    private String productItem;
    /**
     * 产品类型编码
     */
    private String productItemCode;
	/**
	 * 业务日期
	 */
	private Date businessDate;
	/**
	 * 方案描述(短信内容)
	 */
	private String programDesc;
	/**
	 * 数据状态
	 */
	private String active;
	/**
	 * 创建人CODE
	 */
	private String createUserCode;
	/**
	 * 创建组织CODE
	 */
	private String createOrgCode;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 创建人姓名
	 */
	private String createUserName;
	/**
	 * 修改时间
	 */
	private Date modifyTime;
	/**
	 * 市场活动适用渠道ID
	 */
	private String marketChannelId;
	/**
	 * 方案渠道ID
	 */
	private String saleChannelId;
	/**
	 * 方案渠道CODE
	 */
	private String saleChannelCode;
	/**
	 * 方案渠道NAME
	 */
	private String saleChannelName;
	
	/**
	 * 折扣适用起始目的组织网点ID
	 */
	private String discountOrgId;
	/**
	 * 出发区域ID
	 */
	private String deptOrgId;
	/**
	 * 出发区域CODE
	 */
	private String deptOrgCode;
	/**
	 * 出发区域NAME
	 */
	private String deptOrgName;
	/**
	 * 目的区域ID
	 */
	private String arrvOrgId;
	/**
	 * 目的区域CODE
	 */
	private String arrvOrgCode;
	/**
	 * 目的区域NAME
	 */
	private String arrvOrgName;
	/**
	 * 出发组织类型CODE
	 */
	private String deptOrgTypeCode;
	/**
	 * 出发组织类型NAME
	 */
	private String deptOrgTypeName;
	/**
	 * 到达组织类型CODE
	 */
	private String arrvOrgTypeCode;
	/**
	 * 到达组织类型NAME
	 */
	private String arrvOrgTypeName;
	/**
	 * 计费规则ID
	 */
	private String priceValuationId;
	/**
	 * 计价条目ID
	 */
	private String priceEntryId;
	/**
	 * 计价条目CODE
	 */
	private String priceEntryCode;
	/**
	 * 计价条目CODE
	 */
	private String priceEntryName;
	/**
	 * 子类型
	 */
	private String subType;
	/**
	 * 产品ID
	 */
	private String productId;
	/**
	 * 产品CODE
	 */
	private String productCode;
	/**
	 * 产品条目名称
	 */
	private String productName;
	
    /**
     * 产品类型多选公共选择器实体List
     */
    private List<ProductItemEntity> productEntityList;
    /**
     * 产品类型多选公共选择器value
     */
    private List<String> productValue;
	
	/**
	 * 货物类型ID
	 */
	private String goodsTypeId;
	/**
	 * 货物类型CODE
	 */
	private String goodsTypeCode;
	/**
	 * 货物类型NAME
	 */
	private String googsTypeName;
	/**
	 * 所属行业ID
	 */
	private String pricingIndustryId;
	/**
	 * 所属行业CODE
	 */
	private String pricingIndustryCode;
	/**
	 * 计价方式ID
	 */
	private String priceCriteriaId;
	/**
	 * 价格左区间
	 */
	private BigDecimal leftRange;
	/**
	 * 价格右区间
	 */
	private BigDecimal rightRange;
	/**
	 * 返券系数
	 */
	private BigDecimal couponRate;
	/**
	 * 返券时间
	 */
	private BigDecimal couponTimeToSend;
	/**
	 * 折扣规则
	 */
	private String caculateType;
	/**
	 * 最低一票
	 */
	private Long minFee;
	/**
	 * 最高一票
	 */
	private Long maxFee;
	/**
	 * 减免的费用
	 */
	private BigDecimal reduceFee; 
	/**
	 * 优选级别
	 */
	private int priorityLevel;
	
	/**
	 * 营销活动CODE
	 */
	private String activeCode;
	/**
	 * 营销活动名称
	 */
	private String activeName;
	/**
	 * 营销活动开始时间
	 */
	private Date activeStartTime;
	/**
	 * 营销活动结束时间
	 */
	private Date activeEndTime;
	/**
	 * 营销活动折扣关联的CRM_ID
	 */
	private BigDecimal optionsCrmId;
	
	/** 原始费用 **/
    private BigDecimal originnalCost; 
    
    /**
	 * 重量范围左空间
	 */
	private BigDecimal weightLeftRange;
	/**
	 * 重量范围右空间
	 */
	private BigDecimal weightRightRange;
	/**
	 * 体积范围左空间
	 */
	private BigDecimal volumeLeftRange;
	/**
	 * 体积范围右空间
	 */
	private BigDecimal volumeRightRange;
	/**
	 * 是否接货
	 */
	private String isPickUp;
	/**
	 * 是否作废
	 */
	private String isVoid;
	
	public BigDecimal getOriginnalCost() {
		return originnalCost;
	}

	public void setOriginnalCost(BigDecimal originnalCost) {
		this.originnalCost = originnalCost;
	}

	public String getActiveCode() {
		return activeCode;
	}

	public void setActiveCode(String activeCode) {
		this.activeCode = activeCode;
	}

	public String getActiveName() {
		return activeName;
	}

	public void setActiveName(String activeName) {
		this.activeName = activeName;
	}

	public Date getActiveStartTime() {
		return activeStartTime;
	}

	public void setActiveStartTime(Date activeStartTime) {
		this.activeStartTime = activeStartTime;
	}

	public Date getActiveEndTime() {
		return activeEndTime;
	}

	public void setActiveEndTime(Date activeEndTime) {
		this.activeEndTime = activeEndTime;
	}

	public BigDecimal getOptionsCrmId() {
		return optionsCrmId;
	}

	public void setOptionsCrmId(BigDecimal optionsCrmId) {
		this.optionsCrmId = optionsCrmId;
	}
	
	/**
	 * 获取 市场ID.
	 *
	 * @return the 市场ID
	 */
	public String getMarketId() {
		return marketId;
	}
	
	/**
	 * 设置 市场ID.
	 *
	 * @param marketId the new 市场ID
	 */
	public void setMarketId(String marketId) {
		this.marketId = marketId;
	}
	
	/**
	 * 获取 市场CODE.
	 *
	 * @return the 市场CODE
	 */
	public String getMarketCode() {
		return marketCode;
	}
	
	/**
	 * 设置 市场CODE.
	 *
	 * @param marketCode the new 市场CODE
	 */
	public void setMarketCode(String marketCode) {
		this.marketCode = marketCode;
	}
	
	/**
	 * 获取 市场NAME.
	 *
	 * @return the 市场NAME
	 */
	public String getMarketName() {
		return marketName;
	}
	
	/**
	 * 设置 市场NAME.
	 *
	 * @param marketName the new 市场NAME
	 */
	public void setMarketName(String marketName) {
		this.marketName = marketName;
	}
	
	/**
	 * 获取 市场活动类型.
	 *
	 * @return the 市场活动类型
	 */
	public String getMarketType() {
		return marketType;
	}
	
	/**
	 * 设置 市场活动类型.
	 *
	 * @param marketType the new 市场活动类型
	 */
	public void setMarketType(String marketType) {
		this.marketType = marketType;
	}
	
	/**
	 * 获取 折扣方案开始时间.
	 *
	 * @return the 折扣方案开始时间
	 */
	public Date getBeginDate() {
		return beginDate;
	}
	
	/**
	 * 设置 折扣方案开始时间.
	 *
	 * @param beginDate the new 折扣方案开始时间
	 */
	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}
	
	/**
	 * 获取 折扣方案结束时间.
	 *
	 * @return the 折扣方案结束时间
	 */
	public Date getEndDate() {
		return endDate;
	}
	
	/**
	 * 设置 折扣方案结束时间.
	 *
	 * @param endDate the new 折扣方案结束时间
	 */
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	
	/**
	 * 获取 方案描述.
	 *
	 * @return the 方案描述
	 */
	public String getProgramDesc() {
		return programDesc;
	}
	
	/**
	 * 设置 方案描述.
	 *
	 * @param programDesc the new 方案描述
	 */
	public void setProgramDesc(String programDesc) {
		this.programDesc = programDesc;
	}
	
	/**
	 * 获取 数据状态.
	 *
	 * @return the 数据状态
	 */
	public String getActive() {
		return active;
	}
	
	/**
	 * 设置 数据状态.
	 *
	 * @param active the new 数据状态
	 */
	public void setActive(String active) {
		this.active = active;
	}
	
	/**
	 * 获取 创建人CODE.
	 *
	 * @return the 创建人CODE
	 */
	public String getCreateUserCode() {
		return createUserCode;
	}
	
	/**
	 * 设置 创建人CODE.
	 *
	 * @param createUserCode the new 创建人CODE
	 */
	public void setCreateUserCode(String createUserCode) {
		this.createUserCode = createUserCode;
	}
	
	/**
	 * 获取 创建组织CODE.
	 *
	 * @return the 创建组织CODE
	 */
	public String getCreateOrgCode() {
		return createOrgCode;
	}
	
	/**
	 * 设置 创建组织CODE.
	 *
	 * @param createOrgCode the new 创建组织CODE
	 */
	public void setCreateOrgCode(String createOrgCode) {
		this.createOrgCode = createOrgCode;
	}
	
//	/**
//	 * 获取 计价条目ID.
//	 *
//	 * @return the 计价条目ID
//	 */
//	public String getPricingEntryId() {
//		return pricingEntryId;
//	}
//	
//	/**
//	 * 设置 计价条目ID.
//	 *
//	 * @param pricingEntryId the new 计价条目ID
//	 */
//	public void setPricingEntryId(String pricingEntryId) {
//		this.pricingEntryId = pricingEntryId;
//	}
//	
//	/**
//	 * 获取 计价条目CODE.
//	 *
//	 * @return the 计价条目CODE
//	 */
//	public Date getPricingEntryCode() {
//		return pricingEntryCode;
//	}
//	
//	/**
//	 * 设置 计价条目CODE.
//	 *
//	 * @param pricingEntryCode the new 计价条目CODE
//	 */
//	public void setPricingEntryCode(Date pricingEntryCode) {
//		this.pricingEntryCode = pricingEntryCode;
//	}
//	
	
	/**
	 * 获取 市场活动适用渠道ID.
	 *
	 * @return the 市场活动适用渠道ID
	 */
	public String getMarketChannelId() {
		return marketChannelId;
	}
	
	/**
	 * 设置 市场活动适用渠道ID.
	 *
	 * @param marketChannelId the new 市场活动适用渠道ID
	 */
	public void setMarketChannelId(String marketChannelId) {
		this.marketChannelId = marketChannelId;
	}
	
	/**
	 * 获取 方案渠道ID.
	 *
	 * @return the 方案渠道ID
	 */
	public String getSaleChannelId() {
		return saleChannelId;
	}
	
	/**
	 * 设置 方案渠道ID.
	 *
	 * @param saleChannelId the new 方案渠道ID
	 */
	public void setSaleChannelId(String saleChannelId) {
		this.saleChannelId = saleChannelId;
	}
	
	/**
	 * 获取 方案渠道CODE.
	 *
	 * @return the 方案渠道CODE
	 */
	public String getSaleChannelCode() {
		return saleChannelCode;
	}
	
	/**
	 * 设置 方案渠道CODE.
	 *
	 * @param saleChannelCode the new 方案渠道CODE
	 */
	public void setSaleChannelCode(String saleChannelCode) {
		this.saleChannelCode = saleChannelCode;
	}
	
	/**
	 * 获取 方案渠道NAME.
	 *
	 * @return the 方案渠道NAME
	 */
	public String getSaleChannelName() {
		return saleChannelName;
	}
	
	/**
	 * 设置 方案渠道NAME.
	 *
	 * @param saleChannelName the new 方案渠道NAME
	 */
	public void setSaleChannelName(String saleChannelName) {
		this.saleChannelName = saleChannelName;
	}
	
	/**
	 * 获取 折扣适用起始目的组织网点ID.
	 *
	 * @return the 折扣适用起始目的组织网点ID
	 */
	public String getDiscountOrgId() {
		return discountOrgId;
	}
	
	/**
	 * 设置 折扣适用起始目的组织网点ID.
	 *
	 * @param discountOrgId the new 折扣适用起始目的组织网点ID
	 */
	public void setDiscountOrgId(String discountOrgId) {
		this.discountOrgId = discountOrgId;
	}
	
	/**
	 * 获取 出发区域ID.
	 *
	 * @return the 出发区域ID
	 */
	public String getDeptOrgId() {
		return deptOrgId;
	}
	
	/**
	 * 设置 出发区域ID.
	 *
	 * @param deptOrgId the new 出发区域ID
	 */
	public void setDeptOrgId(String deptOrgId) {
		this.deptOrgId = deptOrgId;
	}
	
	/**
	 * 获取 出发区域CODE.
	 *
	 * @return the 出发区域CODE
	 */
	public String getDeptOrgCode() {
		return deptOrgCode;
	}
	
	/**
	 * 设置 出发区域CODE.
	 *
	 * @param deptOrgCode the new 出发区域CODE
	 */
	public void setDeptOrgCode(String deptOrgCode) {
		this.deptOrgCode = deptOrgCode;
	}
	
	/**
	 * 获取 出发区域NAME.
	 *
	 * @return the 出发区域NAME
	 */
	public String getDeptOrgName() {
		return deptOrgName;
	}
	
	/**
	 * 设置 出发区域NAME.
	 *
	 * @param deptOrgName the new 出发区域NAME
	 */
	public void setDeptOrgName(String deptOrgName) {
		this.deptOrgName = deptOrgName;
	}
	
	/**
	 * 获取 目的区域ID.
	 *
	 * @return the 目的区域ID
	 */
	public String getArrvOrgId() {
		return arrvOrgId;
	}
	
	/**
	 * 设置 目的区域ID.
	 *
	 * @param arrvOrgId the new 目的区域ID
	 */
	public void setArrvOrgId(String arrvOrgId) {
		this.arrvOrgId = arrvOrgId;
	}
	
	/**
	 * 获取 目的区域CODE.
	 *
	 * @return the 目的区域CODE
	 */
	public String getArrvOrgCode() {
		return arrvOrgCode;
	}
	
	/**
	 * 设置 目的区域CODE.
	 *
	 * @param arrvOrgCode the new 目的区域CODE
	 */
	public void setArrvOrgCode(String arrvOrgCode) {
		this.arrvOrgCode = arrvOrgCode;
	}
	
	/**
	 * 获取 目的区域NAME.
	 *
	 * @return the 目的区域NAME
	 */
	public String getArrvOrgName() {
		return arrvOrgName;
	}
	
	/**
	 * 设置 目的区域NAME.
	 *
	 * @param arrvOrgName the new 目的区域NAME
	 */
	public void setArrvOrgName(String arrvOrgName) {
		this.arrvOrgName = arrvOrgName;
	}
	
	/**
	 * 获取 出发组织类型CODE.
	 *
	 * @return the 出发组织类型CODE
	 */
	public String getDeptOrgTypeCode() {
		return deptOrgTypeCode;
	}
	
	/**
	 * 设置 出发组织类型CODE.
	 *
	 * @param deptOrgTypeCode the new 出发组织类型CODE
	 */
	public void setDeptOrgTypeCode(String deptOrgTypeCode) {
		this.deptOrgTypeCode = deptOrgTypeCode;
	}
	
	/**
	 * 获取 出发组织类型NAME.
	 *
	 * @return the 出发组织类型NAME
	 */
	public String getDeptOrgTypeName() {
		return deptOrgTypeName;
	}
	
	/**
	 * 设置 出发组织类型NAME.
	 *
	 * @param deptOrgTypeName the new 出发组织类型NAME
	 */
	public void setDeptOrgTypeName(String deptOrgTypeName) {
		this.deptOrgTypeName = deptOrgTypeName;
	}
	
	/**
	 * 获取 到达组织类型CODE.
	 *
	 * @return the 到达组织类型CODE
	 */
	public String getArrvOrgTypeCode() {
		return arrvOrgTypeCode;
	}
	
	/**
	 * 设置 到达组织类型CODE.
	 *
	 * @param arrvOrgTypeCode the new 到达组织类型CODE
	 */
	public void setArrvOrgTypeCode(String arrvOrgTypeCode) {
		this.arrvOrgTypeCode = arrvOrgTypeCode;
	}
	
	/**
	 * 获取 到达组织类型NAME.
	 *
	 * @return the 到达组织类型NAME
	 */
	public String getArrvOrgTypeName() {
		return arrvOrgTypeName;
	}
	
	/**
	 * 设置 到达组织类型NAME.
	 *
	 * @param arrvOrgTypeName the new 到达组织类型NAME
	 */
	public void setArrvOrgTypeName(String arrvOrgTypeName) {
		this.arrvOrgTypeName = arrvOrgTypeName;
	}
	
	/**
	 * 获取 计费规则ID.
	 *
	 * @return the 计费规则ID
	 */
	public String getPriceValuationId() {
		return priceValuationId;
	}
	
	/**
	 * 设置 计费规则ID.
	 *
	 * @param priceValuationId the new 计费规则ID
	 */
	public void setPriceValuationId(String priceValuationId) {
		this.priceValuationId = priceValuationId;
	}
	
	/**
	 * 获取 产品ID.
	 *
	 * @return the 产品ID
	 */
	public String getProductId() {
		return productId;
	}
	
	/**
	 * 设置 产品ID.
	 *
	 * @param productId the new 产品ID
	 */
	public void setProductId(String productId) {
		this.productId = productId;
	}
	
	/**
	 * 获取 产品CODE.
	 *
	 * @return the 产品CODE
	 */
	public String getProductCode() {
		return productCode;
	}
	
	/**
	 * 设置 产品CODE.
	 *
	 * @param productCode the new 产品CODE
	 */
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	
	/**
	 * 获取 产品条目名称.
	 *
	 * @return the 产品条目名称
	 */
	public String getProductName() {
		return productName;
	}
	
	/**
	 * 设置 产品条目名称.
	 *
	 * @param productName the new 产品条目名称
	 */
	public void setProductName(String productName) {
		this.productName = productName;
	}
	
	/**
	 * 获取 计价方式ID.
	 *
	 * @return the 计价方式ID
	 */
	public String getPriceCriteriaId() {
		return priceCriteriaId;
	}
	
	/**
	 * 设置 计价方式ID.
	 *
	 * @param priceCriteriaId the new 计价方式ID
	 */
	public void setPriceCriteriaId(String priceCriteriaId) {
		this.priceCriteriaId = priceCriteriaId;
	}
	
	/**
	 * 获取 价格左区间.
	 *
	 * @return the 价格左区间
	 */
	public BigDecimal getLeftRange() {
		return leftRange;
	}
	
	/**
	 * 设置 价格左区间.
	 *
	 * @param leftRange the new 价格左区间
	 */
	public void setLeftRange(BigDecimal leftRange) {
		this.leftRange = leftRange;
	}
	
	/**
	 * 获取 价格右区间.
	 *
	 * @return the 价格右区间
	 */
	public BigDecimal getRightRange() {
		return rightRange;
	}
	
	/**
	 * 设置 价格右区间.
	 *
	 * @param rightRange the new 价格右区间
	 */
	public void setRightRange(BigDecimal rightRange) {
		this.rightRange = rightRange;
	}
	
	/**
	 * 获取 折扣率.
	 *
	 * @return the 折扣率
	 */
	public BigDecimal getCouponRate() {
		return couponRate;
	}
	
	/**
	 * 设置 折扣率.
	 *
	 * @param discountRate the new 折扣率
	 */
	public void setCouponRate(BigDecimal couponRate) {
		this.couponRate = couponRate;
	}
	/**
	 * 获取返券时间.
	 *
	 * @return couponTimeToSend
	 */
	public BigDecimal getCouponTimeToSend() {
		return couponTimeToSend;
	}
	/**
	 * 设置 返券时间.
	 *
	 * @param couponTimeToSend
	 */
	public void setCouponTimeToSend(BigDecimal couponTimeToSend) {
		this.couponTimeToSend = couponTimeToSend;
	}

	/**
	 * 获取 折扣规则.
	 *
	 * @return the 折扣规则
	 */
	public String getCaculateType() {
		return caculateType;
	}
	
	/**
	 * 设置 折扣规则.
	 *
	 * @param caculateType the new 折扣规则
	 */
	public void setCaculateType(String caculateType) {
		this.caculateType = caculateType;
	}
	
	/**
	 * 获取 货物类型ID.
	 *
	 * @return the 货物类型ID
	 */
	public String getGoodsTypeId() {
		return goodsTypeId;
	}
	
	/**
	 * 设置 货物类型ID.
	 *
	 * @param goodsTypeId the new 货物类型ID
	 */
	public void setGoodsTypeId(String goodsTypeId) {
		this.goodsTypeId = goodsTypeId;
	}
	
	/**
	 * 获取 货物类型CODE.
	 *
	 * @return the 货物类型CODE
	 */
	public String getGoodsTypeCode() {
		return goodsTypeCode;
	}
	
	/**
	 * 设置 货物类型CODE.
	 *
	 * @param goodsTypeCode the new 货物类型CODE
	 */
	public void setGoodsTypeCode(String goodsTypeCode) {
		this.goodsTypeCode = goodsTypeCode;
	}
	
	/**
	 * 获取 货物类型NAME.
	 *
	 * @return the 货物类型NAME
	 */
	public String getGoogsTypeName() {
		return googsTypeName;
	}
	
	/**
	 * 设置 货物类型NAME.
	 *
	 * @param googsTypeName the new 货物类型NAME
	 */
	public void setGoogsTypeName(String googsTypeName) {
		this.googsTypeName = googsTypeName;
	}
	
	/**
	 * 获取 最低一票.
	 *
	 * @return the 最低一票
	 */
	public Long getMinFee() {
		return minFee;
	}
	
	/**
	 * 设置 最低一票.
	 *
	 * @param minFee the new 最低一票
	 */
	public void setMinFee(Long minFee) {
		this.minFee = minFee;
	}
	
	/**
	 * 获取 创建人姓名.
	 *
	 * @return the 创建人姓名
	 */
	public String getCreateUserName() {
		return createUserName;
	}
	
	/**
	 * 设置 创建人姓名.
	 *
	 * @param createUserName the new 创建人姓名
	 */
	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}
	
	/**
	 * 获取 创建时间.
	 *
	 * @return the 创建时间
	 */
	public Date getCreateTime() {
		return createTime;
	}
	
	/**
	 * 设置 创建时间.
	 *
	 * @param createTime the new 创建时间
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	/**
	 * 获取 修改时间.
	 *
	 * @return the 修改时间
	 */
	public Date getModifyTime() {
		return modifyTime;
	}
	
	/**
	 * 设置 修改时间.
	 *
	 * @param modifyTime the new 修改时间
	 */
	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}
	
	/**
	 * 获取 业务日期.
	 *
	 * @return the 业务日期
	 */
	public Date getBusinessDate() {
		return businessDate;
	}
	
	/**
	 * 设置 业务日期.
	 *
	 * @param businessDate the new 业务日期
	 */
	public void setBusinessDate(Date businessDate) {
		this.businessDate = businessDate;
	}
	
	/**
	 * 获取 计价条目ID.
	 *
	 * @return the 计价条目ID
	 */
	public String getPriceEntryId() {
		return priceEntryId;
	}
	
	/**
	 * 设置 计价条目ID.
	 *
	 * @param priceEntryId the new 计价条目ID
	 */
	public void setPriceEntryId(String priceEntryId) {
		this.priceEntryId = priceEntryId;
	}
	
	/**
	 * 获取 计价条目CODE.
	 *
	 * @return the 计价条目CODE
	 */
	public String getPriceEntryCode() {
		return priceEntryCode;
	}
	
	/**
	 * 设置 计价条目CODE.
	 *
	 * @param priceEntryCode the new 计价条目CODE
	 */
	public void setPriceEntryCode(String priceEntryCode) {
		this.priceEntryCode = priceEntryCode;
	}
	
	/**
	 * 获取 子类型.
	 *
	 * @return the 子类型
	 */
	public String getSubType() {
		return subType;
	}

	/**
	 * 设置 子类型.
	 *
	 * @param subType the new 子类型
	 */
	public void setSubType(String subType) {
		this.subType = subType;
	}

	/**
	 * 获取 最高一票.
	 *
	 * @return the 最高一票
	 */
	public Long getMaxFee() {
		return maxFee;
	}
	
	/**
	 * 设置 最高一票.
	 *
	 * @param maxFee the new 最高一票
	 */
	public void setMaxFee(Long maxFee) {
		this.maxFee = maxFee;
	}
	
	/**
	 * 获取 所属行业ID.
	 *
	 * @return the 所属行业ID
	 */
	public String getPricingIndustryId() {
		return pricingIndustryId;
	}
	
	/**
	 * 设置 所属行业ID.
	 *
	 * @param pricingIndustryId the new 所属行业ID
	 */
	public void setPricingIndustryId(String pricingIndustryId) {
		this.pricingIndustryId = pricingIndustryId;
	}
	
	/**
	 * 获取 所属行业CODE.
	 *
	 * @return the 所属行业CODE
	 */
	public String getPricingIndustryCode() {
		return pricingIndustryCode;
	}
	
	/**
	 * 设置 所属行业CODE.
	 *
	 * @param pricingIndustryCode the new 所属行业CODE
	 */
	public void setPricingIndustryCode(String pricingIndustryCode) {
		this.pricingIndustryCode = pricingIndustryCode;
	}
	
	/**
	 * 获取 减免的费用.
	 *
	 * @return the 减免的费用
	 */
	public BigDecimal getReduceFee() {
		return reduceFee;
	}
	
	/**
	 * 设置 减免的费用.
	 *
	 * @param reduceFee the new 减免的费用
	 */
	public void setReduceFee(BigDecimal reduceFee) {
		this.reduceFee = reduceFee;
	}

	/**
	 * 获取 序列ID.
	 *
	 * @return the 序列ID
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	/**
	 * 获取 优选级别.
	 *
	 * @return the 优选级别
	 */
	public int getPriorityLevel() {
		return priorityLevel;
	}

	/**
	 * 设置 优选级别.
	 *
	 * @param priorityLevel the new 优选级别
	 */
	public void setPriorityLevel(int priorityLevel) {
		this.priorityLevel = priorityLevel;
	}

	
	
	
	/**
	 * 获取 折扣ID.
	 *
	 * @return the 折扣ID
	 */
	public String getDiscountId() {
	    return discountId;
	}

	
	/**
	 * 设置 折扣ID.
	 *
	 * @param discountId the new 折扣ID
	 */
	public void setDiscountId(String discountId) {
	    this.discountId = discountId;
	}

	
	/**
	 * 获取 类型 discount 为公布价折扣  promotion 为增值服务优惠.
	 *
	 * @return the 类型 discount 为公布价折扣  promotion 为增值服务优惠
	 */
	public String getType() {
	    return type;
	}

	
	/**
	 * 设置 类型 discount 为公布价折扣  promotion 为增值服务优惠.
	 *
	 * @param type the new 类型 discount 为公布价折扣  promotion 为增值服务优惠
	 */
	public void setType(String type) {
	    this.type = type;
	}

	
	/**
	 * 获取 费用类型id.
	 *
	 * @return the 费用类型id
	 */
	public String getChargeDetailId() {
	    return chargeDetailId;
	}

	
	/**
	 * 设置 费用类型id.
	 *
	 * @param chargeDetailId the new 费用类型id
	 */
	public void setChargeDetailId(String chargeDetailId) {
	    this.chargeDetailId = chargeDetailId;
	}

	/**
	 * 获取 优先级类型名称、产品、渠道、客户.
	 *
	 * @return the 优先级类型名称、产品、渠道、客户
	 */
	public String getTypeName() {
		return typeName;
	}

	/**
	 * 设置 优先级类型名称、产品、渠道、客户.
	 *
	 * @param typeName the new 优先级类型名称、产品、渠道、客户
	 */
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	/**
	 * 获取 计价条目CODE.
	 *
	 * @return the 计价条目CODE
	 */
	public String getPriceEntryName() {
		return priceEntryName;
	}

	/**
	 * 设置 计价条目CODE.
	 *
	 * @param priceEntryName the new 计价条目CODE
	 */
	public void setPriceEntryName(String priceEntryName) {
		this.priceEntryName = priceEntryName;
	}

	public BigDecimal getWeightLeftRange() {
		return weightLeftRange;
	}

	public void setWeightLeftRange(BigDecimal weightLeftRange) {
		this.weightLeftRange = weightLeftRange;
	}

	public BigDecimal getWeightRightRange() {
		return weightRightRange;
	}

	public void setWeightRightRange(BigDecimal weightRightRange) {
		this.weightRightRange = weightRightRange;
	}

	public BigDecimal getVolumeLeftRange() {
		return volumeLeftRange;
	}

	public void setVolumeLeftRange(BigDecimal volumeLeftRange) {
		this.volumeLeftRange = volumeLeftRange;
	}

	public BigDecimal getVolumeRightRange() {
		return volumeRightRange;
	}

	public void setVolumeRightRange(BigDecimal volumeRightRange) {
		this.volumeRightRange = volumeRightRange;
	}

	public String getIsPickUp() {
		return isPickUp;
	}

	public void setIsPickUp(String isPickUp) {
		this.isPickUp = isPickUp;
	}

	public String getIsVoid() {
		return isVoid;
	}

	public void setIsVoid(String isVoid) {
		this.isVoid = isVoid;
	}

	/**
	 * @return  the productEntityList
	 */
	public List<ProductItemEntity> getProductEntityList() {
		return productEntityList;
	}

	/**
	 * @param productEntityList the productEntityList to set
	 */
	public void setProductEntityList(List<ProductItemEntity> productEntityList) {
		this.productEntityList = productEntityList;
	}

	/**
	 * @return  the productValue
	 */
	public List<String> getProductValue() {
		return productValue;
	}

	/**
	 * @param productValue the productValue to set
	 */
	public void setProductValue(List<String> productValue) {
		this.productValue = productValue;
	}

	/**
	 * @return  the lineRegion
	 */
	public String getLineRegion() {
		return lineRegion;
	}

	/**
	 * @param lineRegion the lineRegion to set
	 */
	public void setLineRegion(String lineRegion) {
		this.lineRegion = lineRegion;
	}

	/**
	 * @return  the availablePeriod
	 */
	public String getAvailablePeriod() {
		return availablePeriod;
	}

	/**
	 * @param availablePeriod the availablePeriod to set
	 */
	public void setAvailablePeriod(String availablePeriod) {
		this.availablePeriod = availablePeriod;
	}

	/**
	 * @return  the customerProfessionCode
	 */
	public String getCustomerProfessionCode() {
		return customerProfessionCode;
	}

	/**
	 * @param customerProfessionCode the customerProfessionCode to set
	 */
	public void setCustomerProfessionCode(String customerProfessionCode) {
		this.customerProfessionCode = customerProfessionCode;
	}

	/**
	 * @return  the customerProfession
	 */
	public String getCustomerProfession() {
		return customerProfession;
	}

	/**
	 * @param customerProfession the customerProfession to set
	 */
	public void setCustomerProfession(String customerProfession) {
		this.customerProfession = customerProfession;
	}

	/**
	 * @return  the customerDegree
	 */
	public String getCustomerDegree() {
		return customerDegree;
	}

	/**
	 * @param customerDegree the customerDegree to set
	 */
	public void setCustomerDegree(String customerDegree) {
		this.customerDegree = customerDegree;
	}

	/**
	 * @return  the customerDegreeCode
	 */
	public String getCustomerDegreeCode() {
		return customerDegreeCode;
	}

	/**
	 * @param customerDegreeCode the customerDegreeCode to set
	 */
	public void setCustomerDegreeCode(String customerDegreeCode) {
		this.customerDegreeCode = customerDegreeCode;
	}

	/**
	 * @return  the orderSource
	 */
	public String getOrderSource() {
		return orderSource;
	}

	/**
	 * @param orderSource the orderSource to set
	 */
	public void setOrderSource(String orderSource) {
		this.orderSource = orderSource;
	}

	/**
	 * @return  the orderSourceCode
	 */
	public String getOrderSourceCode() {
		return orderSourceCode;
	}

	/**
	 * @param orderSourceCode the orderSourceCode to set
	 */
	public void setOrderSourceCode(String orderSourceCode) {
		this.orderSourceCode = orderSourceCode;
	}

	/**
	 * @return  the productItem
	 */
	public String getProductItem() {
		return productItem;
	}

	/**
	 * @param productItem the productItem to set
	 */
	public void setProductItem(String productItem) {
		this.productItem = productItem;
	}

	/**
	 * @return  the productItemCode
	 */
	public String getProductItemCode() {
		return productItemCode;
	}

	/**
	 * @param productItemCode the productItemCode to set
	 */
	public void setProductItemCode(String productItemCode) {
		this.productItemCode = productItemCode;
	}

}