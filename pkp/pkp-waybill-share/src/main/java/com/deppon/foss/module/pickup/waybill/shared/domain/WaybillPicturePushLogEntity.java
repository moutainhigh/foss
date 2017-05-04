package com.deppon.foss.module.pickup.waybill.shared.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * 推送日志（图片开单）
 * @author hehaisu
 * @date 2015-2-7 上午10:36:15
 */
public class WaybillPicturePushLogEntity implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1317401798810648581L;
	/**
	 * 主键
	 */
	private String id;
	/**
	 * job Id
	 */
	private String jobId;
	/**
	 * 运单号
	 */
	private String waybillNo;
	/**
	 * 类型:百度云推送(PUSH)、短信(SEND)
	 */
	private String pushType;
	/**
	 * 
	 */
	private String content;
	/**
	 * 创建人code
	 */
	private String createUserCode;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 操作结果:success、failure
	 */
	private String operateResult;
	/**
	 * 失败原因
	 */
	private String failReason;
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getJobId() {
		return jobId;
	}
	public void setJobId(String jobId) {
		this.jobId = jobId;
	}
	public String getWaybillNo() {
		return waybillNo;
	}
	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}
	public String getPushType() {
		return pushType;
	}
	public void setPushType(String pushType) {
		this.pushType = pushType;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
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
	public String getOperateResult() {
		return operateResult;
	}
	public void setOperateResult(String operateResult) {
		this.operateResult = operateResult;
	}
	public String getFailReason() {
		return failReason;
	}
	public void setFailReason(String failReason) {
		this.failReason = failReason;
	}
	
}
