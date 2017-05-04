package com.deppon.foss.module.base.baseinfo.api.shared.domain;

import java.util.Date;
import java.util.List;

import com.deppon.foss.framework.entity.BaseEntity;


/**
 *  晚到补差价——方案实体
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:232607,date:2015-7-17 下午4:03:38,content:TODO </p>
 * @author 232607 
 * @date 2015-7-17 下午4:03:38
 * @since
 * @version
 */
public class LateCouponEntity extends BaseEntity{

	/**
	 * 序列化
	 */
	private static final long serialVersionUID = 1L;

	/**
     * 方案编码
     */
    private String code;
    /**
     * 方案名称
     */
    private String name;
    /**
     * 出发大区
     */
    private String startBigzone;
    /**
     * 出发大区实体集合（用于多选选择器赋值，存入Store）
     */
    private List<OrgAdministrativeInfoEntity> startBigzoneEntitys;
    /**
     * 出发大区编码集合（用于多选选择器赋值，setValue）
     */
    private List<String> startBigzoneCodes;
    /**
     * 出发大区下属的营业部及集中开单组（用于给接送货的接口）
     */
    private List<String> startSalesDept;
    /**
     * 到达大区
     */
    private String arriveBigzone;
    /**
     * 到达大区实体集合（用于多选选择器赋值，存入Store）
     */
    private List<OrgAdministrativeInfoEntity> arriveBigzoneEntitys;
    /**
     * 到达大区编码集合（用于多选选择器赋值，setValue）
     */
    private List<String> arriveBigzoneCodes;
    /**
     * 到达大区下属的营业部及集中开单组（用于给接送货的接口）
     */
    private List<String> arriveSalesDept;
    /**
     * 短息发送对象
     */
    private String smsSent;
    /**
     * 产品类型，可多选，逗号分隔
     */
    private String productItem;
    /**
     * 产品类型实体集合（含编码和名称，用于多选选择器赋值，存入Store）
     */
    private List<ProductEntity> productEntitys;
    /**
     * 产品类型编码集合（用于多选选择器赋值，setValue）
     */
    private List<String> productCodes;
    /**
     * 提货方式，可多选，逗号分隔
     */
    private String deliveryMethod;
    /**
     * 客户分群，可多选，逗号分隔
     */
    private String customerGroup;
    /**
     * 最高面额
     */
    private String maxValue;
    /**
     * 开始时间
     */
    private Date beginTime;
    /**
     * 结束时间
     */
    private Date endTime;
    /**
     * 优惠券有效期
     */
    private String validDays;
    /**
     * 编码是否匹配
     */
    private String  isCodeMatching;
    /**
     * 优惠券短信内容
     */
    private String  smsInfo;
    /**
     * 是否激活
     */
    private String  isActivation;
    /**
     * 是否启用
     */
    private String  active;
    /**
     * 创建人姓名（用于前台结果显示）
     */
    private String  createUserName;
    /**
     * 业务日期（用于前台查询的某个时间点）
     */
    private Date businessDate;
    

    
    
	public Date getBusinessDate() {
		return businessDate;
	}
	public void setBusinessDate(Date businessDate) {
		this.businessDate = businessDate;
	}
	public String getCreateUserName() {
		return createUserName;
	}
	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getStartBigzone() {
		return startBigzone;
	}
	public void setStartBigzone(String startBigzone) {
		this.startBigzone = startBigzone;
	}
	public String getArriveBigzone() {
		return arriveBigzone;
	}
	public void setArriveBigzone(String arriveBigzone) {
		this.arriveBigzone = arriveBigzone;
	}
	public String getSmsSent() {
		return smsSent;
	}
	public void setSmsSent(String smsSent) {
		this.smsSent = smsSent;
	}
	public String getProductItem() {
		return productItem;
	}
	public void setProductItem(String productItem) {
		this.productItem = productItem;
	}
	public String getDeliveryMethod() {
		return deliveryMethod;
	}
	public void setDeliveryMethod(String deliveryMethod) {
		this.deliveryMethod = deliveryMethod;
	}
	public String getCustomerGroup() {
		return customerGroup;
	}
	public void setCustomerGroup(String customerGroup) {
		this.customerGroup = customerGroup;
	}
	public String getMaxValue() {
		return maxValue;
	}
	public void setMaxValue(String maxValue) {
		this.maxValue = maxValue;
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
	public String getValidDays() {
		return validDays;
	}
	public void setValidDays(String validDays) {
		this.validDays = validDays;
	}
	public String getIsCodeMatching() {
		return isCodeMatching;
	}
	public void setIsCodeMatching(String isCodeMatching) {
		this.isCodeMatching = isCodeMatching;
	}
	public String getSmsInfo() {
		return smsInfo;
	}
	public void setSmsInfo(String smsInfo) {
		this.smsInfo = smsInfo;
	}
	public String getIsActivation() {
		return isActivation;
	}
	public void setIsActivation(String isActivation) {
		this.isActivation = isActivation;
	}
	public String getActive() {
		return active;
	}
	public void setActive(String active) {
		this.active = active;
	}
	public List<String> getStartSalesDept() {
		return startSalesDept;
	}
	public void setStartSalesDept(List<String> startSalesDept) {
		this.startSalesDept = startSalesDept;
	}
	public List<String> getArriveSalesDept() {
		return arriveSalesDept;
	}
	public void setArriveSalesDept(List<String> arriveSalesDept) {
		this.arriveSalesDept = arriveSalesDept;
	}
	public List<OrgAdministrativeInfoEntity> getStartBigzoneEntitys() {
		return startBigzoneEntitys;
	}
	public void setStartBigzoneEntitys(
			List<OrgAdministrativeInfoEntity> startBigzoneEntitys) {
		this.startBigzoneEntitys = startBigzoneEntitys;
	}
	public List<String> getStartBigzoneCodes() {
		return startBigzoneCodes;
	}
	public void setStartBigzoneCodes(List<String> startBigzoneCodes) {
		this.startBigzoneCodes = startBigzoneCodes;
	}
	public List<OrgAdministrativeInfoEntity> getArriveBigzoneEntitys() {
		return arriveBigzoneEntitys;
	}
	public void setArriveBigzoneEntitys(
			List<OrgAdministrativeInfoEntity> arriveBigzoneEntitys) {
		this.arriveBigzoneEntitys = arriveBigzoneEntitys;
	}
	public List<String> getArriveBigzoneCodes() {
		return arriveBigzoneCodes;
	}
	public void setArriveBigzoneCodes(List<String> arriveBigzoneCodes) {
		this.arriveBigzoneCodes = arriveBigzoneCodes;
	}
	public List<ProductEntity> getProductEntitys() {
		return productEntitys;
	}
	public void setProductEntitys(List<ProductEntity> productEntitys) {
		this.productEntitys = productEntitys;
	}
	public List<String> getProductCodes() {
		return productCodes;
	}
	public void setProductCodes(List<String> productCodes) {
		this.productCodes = productCodes;
	}
    
    
}