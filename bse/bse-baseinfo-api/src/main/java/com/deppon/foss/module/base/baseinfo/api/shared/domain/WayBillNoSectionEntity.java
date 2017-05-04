package com.deppon.foss.module.base.baseinfo.api.shared.domain;

import java.util.Date;

import com.deppon.foss.framework.entity.BaseEntity;
/**
 * 运单号段实体
 * @author 262036 HuangWei
 *
 * @date 2015-6-17 上午10:32:08
 */
public class WayBillNoSectionEntity extends BaseEntity{

	/** serialVersionUID. */
	private static final long serialVersionUID = 10593343952L;

	/**
	 * 系统名称
	 */
	private String systemName;
	
	/**
	 * 渠道来源
	 */
	private String channelSourceCode;
	
	/**
	 * 客户编码
	 */
	private String customerCode;
	
	/**
	 * 开始运单号
	 */
	private String wayBillNoStart;
	
	/**
	 * 截止运单号
	 */
	private String wayBillNoEnd;
	
	/**
	 * 备注
	 */
	private String remark;
	
	/**
	 * 创建时间
	 */
	private Date createTime;

	/**
	 * 创建人
	 */
	private String createUserCode;
	
	/**
	 * 创建人名称
	 */
	private String createUserName;
	
	/**
	 * 创建开始时间
	 */
	private Date startDate;
	/**
	 * 创建结束时间
	 */
	private Date endDate;
	
	/**
	 * 申请数量
	 */
	private Long applyCount;
	
	/**
	 * 状态
	 */
	private int state;
	/**
	 * 是否使用中
	 */
	private String active;
	
	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public Long getApplyCount() {
		return applyCount;
	}

	public void setApplyCount(Long applyCount) {
		this.applyCount = applyCount;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getCreateUserName() {
		return createUserName;
	}

	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}

	public String getSystemName() {
		return systemName;
	}

	public void setSystemName(String systemName) {
		this.systemName = systemName;
	}

	public String getChannelSourceCode() {
		return channelSourceCode;
	}

	public void setChannelSourceCode(String channelSourceCode) {
		this.channelSourceCode = channelSourceCode;
	}

	public String getCustomerCode() {
		return customerCode;
	}

	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}

	public String getWayBillNoStart() {
		return wayBillNoStart;
	}

	public void setWayBillNoStart(String wayBillNoStart) {
		this.wayBillNoStart = wayBillNoStart;
	}

	public String getWayBillNoEnd() {
		return wayBillNoEnd;
	}

	public void setWayBillNoEnd(String wayBillNoEnd) {
		this.wayBillNoEnd = wayBillNoEnd;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getCreateUserCode() {
		return createUserCode;
	}

	public void setCreateUserCode(String createUserCode) {
		this.createUserCode = createUserCode;
	}
	
}
