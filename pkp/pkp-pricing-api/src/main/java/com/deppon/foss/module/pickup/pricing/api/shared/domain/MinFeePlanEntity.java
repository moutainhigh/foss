package com.deppon.foss.module.pickup.pricing.api.shared.domain;

import java.util.Date;

import com.deppon.foss.framework.entity.BaseEntity;

public class MinFeePlanEntity extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7589559705538063366L;

	/**
	 * 最低一票方案CODE
	 */
	private String code;

	/**
	 * 最低一票方案名称
	 */
	private String name;

	/**
	 * 产品code
	 */
	private String productCode;

	/**
	 * 产品id
	 */
	private String productId;

	/**
	 * 产品名
	 */
	private String productName;

	/**
	 * 渠道code
	 */
	private String channelCode;

	/**
	 * 渠道id
	 */
	private String channelId;

	/**
	 * 渠道名
	 */
	private String channelName;

	/**
	 * 开始时间
	 */
	private Date beginTime;

	/**
	 * 结束时间
	 */
	private Date endTime;

	/**
	 * 最低一票
	 */
	private Long minFee;

	/**
	 * 方案描述 这个属性对应数据库字段为description，因为其中含有'script'敏感字，不能正常从request中取值
	 */
	private String remark;

	/**
	 * 创建人code
	 */
	private String createUserCode;

	/**
	 * 创建人name
	 */
	private String createUserName;

	/**
	 * 创建时间
	 */
	private Date createTime;

	/**
	 * 创建部门
	 */
	private String createOrgCode;

	/**
	 * 修改人code
	 */
	private String modifyUserCode;

	/**
	 * 修改人name
	 */
	private String modifyUserName;

	/**
	 * 修改时间
	 */
	private Date modifyTime;

	/**
	 * 修改部门
	 */
	private String modifyOrgCode;

	/**
	 * 版本号
	 */
	private Long versionNo;

	/**
	 * 激活状态
	 */
	private String active;

	/**
	 * 为接口提供的方案名，如“淘宝自提件”“阿里巴巴自提件”
	 */
	private String planName;

	/**
	 * 为接口提供的业务时间
	 */
	private Date businessDate;

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

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getChannelCode() {
		return channelCode;
	}

	public void setChannelCode(String channelCode) {
		this.channelCode = channelCode;
	}

	public String getChannelId() {
		return channelId;
	}

	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}

	public String getChannelName() {
		return channelName;
	}

	public void setChannelName(String channelName) {
		this.channelName = channelName;
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

	public Long getMinFee() {
		return minFee;
	}

	public void setMinFee(Long minFee) {
		this.minFee = minFee;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getCreateUserCode() {
		return createUserCode;
	}

	public void setCreateUserCode(String createUserCode) {
		this.createUserCode = createUserCode;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getCreateOrgCode() {
		return createOrgCode;
	}

	public void setCreateOrgCode(String createOrgCode) {
		this.createOrgCode = createOrgCode;
	}

	public String getModifyUserCode() {
		return modifyUserCode;
	}

	public void setModifyUserCode(String modifyUserCode) {
		this.modifyUserCode = modifyUserCode;
	}

	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	public String getModifyOrgCode() {
		return modifyOrgCode;
	}

	public void setModifyOrgCode(String modifyOrgCode) {
		this.modifyOrgCode = modifyOrgCode;
	}

	public Long getVersionNo() {
		return versionNo;
	}

	public void setVersionNo(Long versionNo) {
		this.versionNo = versionNo;
	}

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}

	public String getPlanName() {
		return planName;
	}

	public void setPlanName(String planName) {
		this.planName = planName;
	}

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

	public String getModifyUserName() {
		return modifyUserName;
	}

	public void setModifyUserName(String modifyUserName) {
		this.modifyUserName = modifyUserName;
	}

}
