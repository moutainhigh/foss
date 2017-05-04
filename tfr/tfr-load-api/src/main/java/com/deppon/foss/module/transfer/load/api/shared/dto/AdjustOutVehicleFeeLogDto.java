package com.deppon.foss.module.transfer.load.api.shared.dto;

import java.io.Serializable;

/**
 * 外请车费用调整修改日志Dto
 * @author 269701
 * @date 2015-07-13 下午 16:36
 */
public class AdjustOutVehicleFeeLogDto implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -1450968692126059993L;

	/**
	 * Id
	 */
	private String id;
	
	/**
	 * 外请车费用ID
	 */
	private String adjustOutVehicleFeeId;
	
	/**
	 * 操作人
	 */
	private String createUserName;
	
	/**
	 * 操作人code
	 */
	private String createUserCode;
	
	/**
	 * 創建時間
	 */
	private String createTime;
	
	/**
	 * 操作類別
	 */
	private String logType;
	
	/**
	 * 操作內容
	 */
	private String modifyContent;
	
	/* (non-Javadoc)
	 * @see com.deppon.foss.framework.entity.BaseEntity#getId()
	 */
	public String getId() {
		return id;
	}

	/* (non-Javadoc)
	 * @see com.deppon.foss.framework.entity.BaseEntity#setId(java.lang.String)
	 */
	public void setId(String id) {
		this.id = id;
	}

	
	/**
	 * 获取 外请车费用ID
	 * @return the adjustOutVehicleFeeId
	 */
	public String getAdjustOutVehicleFeeId() {
		return adjustOutVehicleFeeId;
	}

	/**
	 * 设置 外请车费用ID
	 * @param adjustOutVehicleFeeId the adjustOutVehicleFeeId to set
	 */
	public void setAdjustOutVehicleFeeId(String adjustOutVehicleFeeId) {
		this.adjustOutVehicleFeeId = adjustOutVehicleFeeId;
	}

	/**
	 * 获取 操作人.
	 * @return the createUserName
	 */
	public String getCreateUserName() {
		return createUserName;
	}

	/**
	 * 設置 操作人.
	 * @param createUserName the createUserName to set
	 */
	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}

	
	/**
	 * 获取 操作人code.
	 * @return the createUserCode
	 */
	public String getCreateUserCode() {
		return createUserCode;
	}

	/**
	 * 设置 操作人code.
	 * @param createUserCode the createUserCode to set
	 */
	public void setCreateUserCode(String createUserCode) {
		this.createUserCode = createUserCode;
	}

	/**
	 * 获取 创建时间.
	 * @return the createTime
	 */
	public String getCreateTime() {
		return createTime;
	}

	/**
	 * 设置 创建时间.
	 * @param createTime the createTime to set
	 */
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	/**
	 * 获取 日志类别.
	 * @return the logType
	 */
	public String getLogType() {
		return logType;
	}

	/**
	 * 设置 日志类别.
	 * @param logType the logType to set
	 */
	public void setLogType(String logType) {
		this.logType = logType;
	}

	/**
	 * 获取 操作内容.
	 * @return the modifyContent
	 */
	public String getModifyContent() {
		return modifyContent;
	}

	/**
	 * 设置 操作内容.
	 * @param modifyContent the modifyContent to set
	 */
	public void setModifyContent(String modifyContent) {
		this.modifyContent = modifyContent;
	}

	
}
