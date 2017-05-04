package com.deppon.foss.module.pickup.order.api.shared.domain;

import java.util.Date;

import com.deppon.foss.framework.entity.BaseEntity;


public class RPSOrderJobEntity extends BaseEntity {

	/**
	 * JOB扫描类
	 */
	private static final long serialVersionUID = 1231654651654651L;

	/**
	 * id
	 */
	private String id;
	/**
	 * 订单号
	 */
	private String orderNo;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 变更时间
	 */
	private Date changeTime;
	/**
	 * 变更状态
	 */
	private String changeStatus;
	/**
	 * JOB执行ID
	 */
	private String jobId;
	/**
	 * 产品类型
	 */
	private String productCode;
	/**
	 * 所属接货城市编码
	 */
	private String cityCode;
	/**
	 * 最早接货时间
	 */
	private Date earlistPickupTime;
	/**
	 * 查询条数
	 */
	private int count;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	
	public String getJobId() {
		return jobId;
	}
	public void setJobId(String jobId) {
		this.jobId = jobId;
	}
	public String getProductCode() {
		return productCode;
	}
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	public String getCityCode() {
		return cityCode;
	}
	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}
	
	public String getChangeStatus() {
		return changeStatus;
	}
	public void setChangeStatus(String changeStatus) {
		this.changeStatus = changeStatus;
	}
	
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getChangeTime() {
		return changeTime;
	}
	public void setChangeTime(Date changeTime) {
		this.changeTime = changeTime;
	}
	public Date getEarlistPickupTime() {
		return earlistPickupTime;
	}
	public void setEarlistPickupTime(Date earlistPickupTime) {
		this.earlistPickupTime = earlistPickupTime;
	}
	
	@Override
	public String toString() {
		return "OrderJobEntity [id=" + id + ", orderNo=" + orderNo
				+ ", createTime=" + createTime + ", changeTime=" + changeTime
				+ ", changeStatus=" + changeStatus + ", jobId=" + jobId
				+ ", productCode=" + productCode + ", cityCode=" + cityCode
				+ ", earlistPickupTime=" + earlistPickupTime + "]";
	}
}
