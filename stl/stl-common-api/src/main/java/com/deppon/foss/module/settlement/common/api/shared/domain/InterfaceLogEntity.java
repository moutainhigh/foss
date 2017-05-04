package com.deppon.foss.module.settlement.common.api.shared.domain;

import java.util.Date;

import com.deppon.foss.framework.entity.BaseEntity;

/** 
 * 异步接口日志实体
 * @author foss结算-306579-guoxinru 
 * @date 2016-6-17 上午9:04:16    
 */
public class InterfaceLogEntity extends BaseEntity{
	/**
	 * 序列化
	 */
	private static final long serialVersionUID = -8853255634581981287L;
	
	/**
	 * ID
	 */
	private String id;
	
	/**
	 * 运单号
	 */
	private String waybillNo;
	
	/**
	 * 接口编码
	 */
	private String esbCode;
	
	/**
	 * 系统类别--整车:vts
	 */
	private String systemType;
	
	/**
	 * 推送内容
	 */
	private String sendContent;
	
	/**
	 * 创建人
	 */
	private String createUser;
	
	/**
	 * 创建时间
	 */
	private Date createTime;
	
	/**
	 * 修改人
	 */
	private String modifyUser;
	
	/**
	 * 修改时间
	 */
	private Date modifyTime;
	
	/**
	 * 正确日志记录
	 */
	private String correctLog;
	
	/**
	 * 错误日志记录
	 */
	private String errorLog;
	
	/**
	 * 是否成功
	 */
	private String isSuccess;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getWaybillNo() {
		return waybillNo;
	}

	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}

	public String getEsbCode() {
		return esbCode;
	}

	public void setEsbCode(String esbCode) {
		this.esbCode = esbCode;
	}

	public String getSystemType() {
		return systemType;
	}

	public void setSystemType(String systemType) {
		this.systemType = systemType;
	}

	public String getSendContent() {
		return sendContent;
	}

	public void setSendContent(String sendContent) {
		this.sendContent = sendContent;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getModifyUser() {
		return modifyUser;
	}

	public void setModifyUser(String modifyUser) {
		this.modifyUser = modifyUser;
	}

	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	public String getCorrectLog() {
		return correctLog;
	}

	public void setCorrectLog(String correctLog) {
		this.correctLog = correctLog;
	}

	public String getErrorLog() {
		return errorLog;
	}

	public void setErrorLog(String errorLog) {
		this.errorLog = errorLog;
	}

	public String getIsSuccess() {
		return isSuccess;
	}

	public void setIsSuccess(String isSuccess) {
		this.isSuccess = isSuccess;
	}
	
	
}
