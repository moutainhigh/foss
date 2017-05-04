package com.deppon.foss.module.trackings.api.shared.dto;

import java.util.Date;

import com.deppon.foss.framework.entity.BaseEntity;

public class OrderWaybillDto extends BaseEntity{
	private static final long serialVersionUID = 1L;
	//快递公司（德邦）
	private String company;
	//订阅单号
	private String code;
	//订阅类型
	private String operator;
	//回调参数
	private String callback;
	//订阅公司编码
	private String ordercompany;
	//订阅公司名称
	private String orderCmpName;
	//订阅时间
	private Date createTime;
	//更新时间
	private Date updateTime;
	//是否推送
	private String isPush;
	//推送状态
	private String pushResult;
	//最大路由id
	private String maxId;
	//跟踪状态
	private String watchStatus;
	//签收时间
	private Date signTime;
	//是否返回
	private String isReturn;
	//推送时间
	private Date pushTime;
	//返回时间
	private Date returnTime;
	//最新轨迹时间
	private Date latestTime;

	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	public String getCallback() {
		return callback;
	}
	public void setCallback(String callback) {
		this.callback = callback;
	}
	public String getOrdercompany() {
		return ordercompany;
	}
	public void setOrdercompany(String ordercompany) {
		this.ordercompany = ordercompany;
	}
	public String getOrderCmpName() {
		return orderCmpName;
	}
	public void setOrderCmpName(String orderCmpName) {
		this.orderCmpName = orderCmpName;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public String getIsPush() {
		return isPush;
	}
	public void setIsPush(String isPush) {
		this.isPush = isPush;
	}
	public String getPushResult() {
		return pushResult;
	}
	public void setPushResult(String pushResult) {
		this.pushResult = pushResult;
	}
	public String getMaxId() {
		return maxId;
	}
	public void setMaxId(String maxId) {
		this.maxId = maxId;
	}
	public String getWatchStatus() {
		return watchStatus;
	}
	public void setWatchStatus(String watchStatus) {
		this.watchStatus = watchStatus;
	}
	public Date getSignTime() {
		return signTime;
	}
	public void setSignTime(Date signTime) {
		this.signTime = signTime;
	}
	public String getIsReturn() {
		return isReturn;
	}
	public void setIsReturn(String isReturn) {
		this.isReturn = isReturn;
	}
	public Date getPushTime() {
		return pushTime;
	}
	public void setPushTime(Date pushTime) {
		this.pushTime = pushTime;
	}
	public Date getReturnTime() {
		return returnTime;
	}
	public void setReturnTime(Date returnTime) {
		this.returnTime = returnTime;
	}
	public Date getLatestTime() {
		return latestTime;
	}
	public void setLatestTime(Date latestTime) {
		this.latestTime = latestTime;
	}
	
}
