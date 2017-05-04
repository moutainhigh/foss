package com.deppon.foss.module.base.baseinfo.api.shared.domain;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.deppon.foss.framework.entity.BaseEntity;

/**
 * 市场活动实体类
 * @author dujunhui-187862
 * @date 2014-9-23 下午6:11:48
 */
public class MarketingSchemeEntity extends BaseEntity{

	/**
	 * 序列化
	 */
	private static final long serialVersionUID = 1L;

	/**
     * 市场活动CODe
     */
    private String code;

    /**
     * 市场活动名称 
     */
    private String name;

    /**
     * 开始时间
     */
    private Date beginTime;

    /**
     * 结束时间
     */
    private Date endTime;
    
    /**
     * 开单时间
     */
    private Date businessDate;

    /**
     * 方案描述
     */
    private String description;

    /**
     * 备注
     */
    private String remark;

    /**
     * 创建部门
     */
    private String createOrgCode;

    /**
     * 修改部门
     */
    private String modifyOrgCode;

    /**
     * 版本号
     */
    private Long versionNo;

    /**
     * 价格区域CODE
     */
    private String priceRegionCode;

    /**
     * 价格区域ID
     */
    private String priceRegionId;

    /**
     * 激活状态
     */
    private String active;

    /**
     * 活动类型
     */
    private String type;
    
    /**
     * 计价条目ID 
     */
    private String pricingEntryId;
    
    /**
     * 计价条目CODE
     */
    private String pricingEntryCode;
    
    /**
     * 计价条目NAME
     */
    private String pricingEntryName;
    
    /**
     * 创建人姓名
     */
    private String createUserName;
    
    /**
     * 创建人编码
     */
    private String createUser;
    
    /**
     * 产品类型
     */
    private String productItem;
    /**
     * 产品类型编码
     */
    private String productItemCode;
    /**
     * 产品类型多选公共选择器实体List
     */
    private List<ProductItemEntity> proItemEntityList;
    /**
     * 产品类型多选公共选择器value
     */
    private List<String> proItemValue;
    
    /**
     * 订单来源
     */
    private String orderSource;
    /**
     * 订单来源编码
     */
    private String orderSourceCode;
    /**
     * 订单来源多选公共选择器实体List
     */
    private List<CommonOrderSourceEntity> ordSouEntityList;
    /**
     * 订单来源多选公共选择器value
     */
    private List<String> ordSouValue;
    
    /**
     * 客户等级
     */
    private String customerDegree;
    /**
     * 客户等级编码
     */
    private String customerDegreeCode;
    /**
     * 客户等级多选公共选择器实体List
     */
    private List<CommonCustomerDegreeEntity> cusDgrEntityList;
    /**
     * 客户等级多选公共选择器value
     */
    private List<String> cusDgrValue;
    
    /**
     * 客户行业
     */
    private String customerProfession;
    /**
     * 客户行业编码
     */
    private String customerProfessionCode;
    /**
     * 客户行业多选公共选择器实体List
     */
    private List<CommonCustomerProfessionEntity> cusProEntityList;
    /**
     * 客户行业多选公共选择器value
     */
    private List<String> cusProValue;
    /**
     * 线路区域要求
     */
    private String lineRegion;
    
    /**
     * 优惠券有效期
     */
    private String availablePeriod;
    /**
     *  返券时间
     */
    private BigDecimal couponTimeToSend;
    
    /**
     * 是否作废状态
     */
    private String isVoid;


	/**
	 * 获取 市场活动CODe.
	 *
	 * @return the 市场活动CODe
	 */
	public String getCode() {
		return code;
	}

	/**
	 * 设置 市场活动CODe.
	 *
	 * @param code the new 市场活动CODe
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * 获取 市场活动名称.
	 *
	 * @return the 市场活动名称
	 */
	public String getName() {
		return name;
	}

	/**
	 * 设置 市场活动名称.
	 *
	 * @param name the new 市场活动名称
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 获取 开始时间.
	 *
	 * @return the 开始时间
	 */
	public Date getBeginTime() {
		return beginTime;
	}

	/**
	 * 设置 开始时间.
	 *
	 * @param beginTime the new 开始时间
	 */
	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}

	/**
	 * 获取 结束时间.
	 *
	 * @return the 结束时间
	 */
	public Date getEndTime() {
		return endTime;
	}

	/**
	 * 设置 结束时间.
	 *
	 * @param endTime the new 结束时间
	 */
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	/**
	 * 获取 开单时间.
	 *
	 * @return the 开单时间
	 */
	public Date getBusinessDate() {
		return businessDate;
	}

	/**
	 * 设置 开单时间.
	 *
	 * @param businessDate the new 开单时间
	 */
	public void setBusinessDate(Date businessDate) {
		this.businessDate = businessDate;
	}

	/**
	 * 获取 方案描述.
	 *
	 * @return the 方案描述
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * 设置 方案描述.
	 *
	 * @param description the new 方案描述
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	/**
	 * 获取 创建部门.
	 *
	 * @return the 创建部门
	 */
	public String getCreateOrgCode() {
		return createOrgCode;
	}

	/**
	 * 设置 创建部门.
	 *
	 * @param createOrgCode the new 创建部门
	 */
	public void setCreateOrgCode(String createOrgCode) {
		this.createOrgCode = createOrgCode;
	}

	/**
	 * 获取 修改部门.
	 *
	 * @return the 修改部门
	 */
	public String getModifyOrgCode() {
		return modifyOrgCode;
	}

	/**
	 * 设置 修改部门.
	 *
	 * @param modifyOrgCode the new 修改部门
	 */
	public void setModifyOrgCode(String modifyOrgCode) {
		this.modifyOrgCode = modifyOrgCode;
	}

	/**
	 * 获取 版本号.
	 *
	 * @return the 版本号
	 */
	public Long getVersionNo() {
		return versionNo;
	}

	/**
	 * 设置 版本号.
	 *
	 * @param versionNo the new 版本号
	 */
	public void setVersionNo(Long versionNo) {
		this.versionNo = versionNo;
	}

	/**
	 * 获取 价格区域CODE.
	 *
	 * @return the 价格区域CODE
	 */
	public String getPriceRegionCode() {
		return priceRegionCode;
	}

	/**
	 * 设置 价格区域CODE.
	 *
	 * @param priceRegionCode the new 价格区域CODE
	 */
	public void setPriceRegionCode(String priceRegionCode) {
		this.priceRegionCode = priceRegionCode;
	}

	/**
	 * 获取 价格区域ID.
	 *
	 * @return the 价格区域ID
	 */
	public String getPriceRegionId() {
		return priceRegionId;
	}

	/**
	 * 设置 价格区域ID.
	 *
	 * @param priceRegionId the new 价格区域ID
	 */
	public void setPriceRegionId(String priceRegionId) {
		this.priceRegionId = priceRegionId;
	}

	/**
	 * 获取 激活状态.
	 *
	 * @return the 激活状态
	 */
	public String getActive() {
		return active;
	}

	/**
	 * 设置 激活状态.
	 *
	 * @param active the new 激活状态
	 */
	public void setActive(String active) {
		this.active = active;
	}

	/**
	 * 获取 活动类型.
	 *
	 * @return the 活动类型
	 */
	public String getType() {
		return type;
	}

	/**
	 * 设置 活动类型.
	 *
	 * @param type the new 活动类型
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * 获取 计价条目ID.
	 *
	 * @return the 计价条目ID
	 */
	public String getPricingEntryId() {
		return pricingEntryId;
	}

	/**
	 * 设置 计价条目ID.
	 *
	 * @param pricingEntryId the new 计价条目ID
	 */
	public void setPricingEntryId(String pricingEntryId) {
		this.pricingEntryId = pricingEntryId;
	}

	/**
	 * 获取 计价条目CODE.
	 *
	 * @return the 计价条目CODE
	 */
	public String getPricingEntryCode() {
		return pricingEntryCode;
	}

	/**
	 * 设置 计价条目CODE.
	 *
	 * @param pricingEntryCode the new 计价条目CODE
	 */
	public void setPricingEntryCode(String pricingEntryCode) {
		this.pricingEntryCode = pricingEntryCode;
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

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	/**
	 * 获取 价格条目NAME.
	 *
	 * @return the 价格条目NAME
	 */
	public String getPricingEntryName() {
		return pricingEntryName;
	}

	/**
	 * 设置  价格条目NAME.
	 *
	 * @param createUserName the new 价格条目NAME
	 */
	public void setPricingEntryName(String pricingEntryName) {
		this.pricingEntryName = pricingEntryName;
	}

	public String getProductItem() {
		return productItem;
	}

	public void setProductItem(String productItem) {
		this.productItem = productItem;
	}

	public String getOrderSource() {
		return orderSource;
	}

	public void setOrderSource(String orderSource) {
		this.orderSource = orderSource;
	}

	public String getCustomerDegree() {
		return customerDegree;
	}

	public void setCustomerDegree(String customerDegree) {
		this.customerDegree = customerDegree;
	}

	public String getCustomerProfession() {
		return customerProfession;
	}

	public void setCustomerProfession(String customerProfession) {
		this.customerProfession = customerProfession;
	}

	public String getLineRegion() {
		return lineRegion;
	}

	public void setLineRegion(String lineRegion) {
		this.lineRegion = lineRegion;
	}

	public String getAvailablePeriod() {
		return availablePeriod;
	}

	public void setAvailablePeriod(String availablePeriod) {
		this.availablePeriod = availablePeriod;
	}

	public String getIsVoid() {
		return isVoid;
	}

	public void setIsVoid(String isVoid) {
		this.isVoid = isVoid;
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
	 * @return  the cusProEntityList
	 */
	public List<CommonCustomerProfessionEntity> getCusProEntityList() {
		return cusProEntityList;
	}

	/**
	 * @param cusProEntityList the cusProEntityList to set
	 */
	public void setCusProEntityList(
			List<CommonCustomerProfessionEntity> cusProEntityList) {
		this.cusProEntityList = cusProEntityList;
	}

	/**
	 * @return  the cusProValue
	 */
	public List<String> getCusProValue() {
		return cusProValue;
	}

	/**
	 * @param cusProValue the cusProValue to set
	 */
	public void setCusProValue(List<String> cusProValue) {
		this.cusProValue = cusProValue;
	}

	/**
	 * @return  the proItemEntityList
	 */
	public List<ProductItemEntity> getProItemEntityList() {
		return proItemEntityList;
	}

	/**
	 * @param proItemEntityList the proItemEntityList to set
	 */
	public void setProItemEntityList(List<ProductItemEntity> proItemEntityList) {
		this.proItemEntityList = proItemEntityList;
	}

	/**
	 * @return  the proItemValue
	 */
	public List<String> getProItemValue() {
		return proItemValue;
	}

	/**
	 * @param proItemValue the proItemValue to set
	 */
	public void setProItemValue(List<String> proItemValue) {
		this.proItemValue = proItemValue;
	}

	/**
	 * @return  the ordSouEntityList
	 */
	public List<CommonOrderSourceEntity> getOrdSouEntityList() {
		return ordSouEntityList;
	}

	/**
	 * @param ordSouEntityList the ordSouEntityList to set
	 */
	public void setOrdSouEntityList(List<CommonOrderSourceEntity> ordSouEntityList) {
		this.ordSouEntityList = ordSouEntityList;
	}

	/**
	 * @return  the ordSouValue
	 */
	public List<String> getOrdSouValue() {
		return ordSouValue;
	}

	/**
	 * @param ordSouValue the ordSouValue to set
	 */
	public void setOrdSouValue(List<String> ordSouValue) {
		this.ordSouValue = ordSouValue;
	}

	/**
	 * @return  the cusDgrEntityList
	 */
	public List<CommonCustomerDegreeEntity> getCusDgrEntityList() {
		return cusDgrEntityList;
	}

	/**
	 * @param cusDgrEntityList the cusDgrEntityList to set
	 */
	public void setCusDgrEntityList(
			List<CommonCustomerDegreeEntity> cusDgrEntityList) {
		this.cusDgrEntityList = cusDgrEntityList;
	}

	/**
	 * @return  the cusDgrValue
	 */
	public List<String> getCusDgrValue() {
		return cusDgrValue;
	}

	/**
	 * @param cusDgrValue the cusDgrValue to set
	 */
	public void setCusDgrValue(List<String> cusDgrValue) {
		this.cusDgrValue = cusDgrValue;
	}

	/**
	 * @return  the couponTimeToSend
	 */
	public BigDecimal getCouponTimeToSend() {
		return couponTimeToSend;
	}

	/**
	 * @param couponTimeToSend the couponTimeToSend to set
	 */
	public void setCouponTimeToSend(BigDecimal couponTimeToSend) {
		this.couponTimeToSend = couponTimeToSend;
	}
    
}