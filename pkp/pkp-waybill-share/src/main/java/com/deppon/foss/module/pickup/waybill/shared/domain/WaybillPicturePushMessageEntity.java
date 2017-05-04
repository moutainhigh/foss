package com.deppon.foss.module.pickup.waybill.shared.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * 推送消息实体（图片开单）
 * @author hehaisu
 * @date 2015-2-7 上午10:36:15
 */
public class WaybillPicturePushMessageEntity implements Serializable{
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
	 * 百度id
	 */
	private String baiduId;
	/**
	 * 描述
	 */
	private String description;
	/**
	 * 创建人code
	 */
	private String createUserCode;
	/**
	 * 创建人
	 */
	private String createUserName;
	/**
	 * 创建部门code
	 */
	private String createOrgCode;
	/**
	 * 创建部门名称
	 */
	private String createOrgName;
	/**
	 * 创建时间
	 */
	private Date createTime;
	
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
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getCreateUserCode() {
		return createUserCode;
	}
	public void setCreateUserCode(String createUserCode) {
		this.createUserCode = createUserCode;
	}
	public String getCreateUserName() {
		return createUserName;
	}
	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
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
	public String getCreateOrgName() {
		return createOrgName;
	}
	public void setCreateOrgName(String createOrgName) {
		this.createOrgName = createOrgName;
	}
	public String getBaiduId() {
		return baiduId;
	}
	public void setBaiduId(String baiduId) {
		this.baiduId = baiduId;
	}
	
}
