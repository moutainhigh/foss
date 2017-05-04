package com.deppon.foss.module.transfer.unload.api.shared.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;


/**
 * @description 创建卸车任务DTO
 * @version 1.0
 * @author 328768-foss-gaojianfu
 * @update 2016年4月27日 下午1:33:57
 */
public class ExpressUnloadTaskAddnewDto implements Serializable {

	
	private static final long serialVersionUID = -8591316661397960102L;

	/** 卸车任务编号 */
	private String unloadTaskNo;

	/** 车牌号 */
	private String vehicleNo;

	/** 卸车类型 */
	private String unloadType;

	/** 月台号 */
	private String platformNo;

	/** 建立任务部门编号 */
	private String unloadOrgCode;

	/** 创建人工号 */
	private String createNo;
	
	/** 卸车方式 */
	private String unloadWay;

	/** 创建时间 */
	private Date createTime;
	
	/** 创建人部门编号 */
	private String createOrgCode;
	
	/**
	 * 操作设备
	 */
	private String operationDevice;
	
	/**
	 * 操作设备编码
	 */
	private String operationDeviceCode;

	/** 操作码 */
	private String operationCode;

	/** 辅助操作码 */
	private String operationAssistCode;

	/** 控制状态 */
	private String controlStatus;

	/** 添加的单据列表 */
	private List<ExpressUnloadTaskDetailDto> createUnloadTaskDetailList;

	/** 添加的卸车员列表 */
	private List<ExpressLoaderParticipateDto> createLoaderParticipateList;

	/**
	 * @description 获取 卸车任务编号
	 * @return
	 * @version 1.0
	 * @author 328768-foss-gaojianfu
	 * @update 2016年4月27日 下午1:35:37
	 */
	public String getUnloadTaskNo() {
		return unloadTaskNo;
	}

	/**
	 * @description 设置 卸车任务编号
	 * @param unloadTaskNo
	 * @version 1.0
	 * @author 328768-foss-gaojianfu
	 * @update 2016年4月27日 下午1:36:05
	 */
	public void setUnloadTaskNo(String unloadTaskNo) {
		this.unloadTaskNo = unloadTaskNo;
	}

	/**
	 * @description 获取 车牌号
	 * @return
	 * @version 1.0
	 * @author 328768-foss-gaojianfu
	 * @update 2016年4月27日 下午1:36:35
	 */
	public String getVehicleNo() {
		return vehicleNo;
	}

	/**
	 * @description 设置 车牌号
	 * @param vehicleNo
	 * @version 1.0
	 * @author 328768-foss-gaojianfu
	 * @update 2016年4月27日 下午1:36:54
	 */
	public void setVehicleNo(String vehicleNo) {
		this.vehicleNo = vehicleNo;
	}

	/**
	 * @description 获取 卸车类型
	 * @return
	 * @version 1.0
	 * @author 328768-foss-gaojianfu
	 * @update 2016年4月27日 下午1:37:11
	 */
	public String getUnloadType() {
		return unloadType;
	}

	/**
	 * @description 设置 卸车类型
	 * @param unloadType
	 * @version 1.0
	 * @author 328768-foss-gaojianfu
	 * @update 2016年4月27日 下午1:37:28
	 */
	public void setUnloadType(String unloadType) {
		this.unloadType = unloadType;
	}

	/**
	 * @description 获取 月台号
	 * @return
	 * @version 1.0
	 * @author 328768-foss-gaojianfu
	 * @update 2016年4月27日 下午1:37:51
	 */
	public String getPlatformNo() {
		return platformNo;
	}

	/**
	 * @description 设置 月台号
	 * @param platformNo
	 * @version 1.0
	 * @author 328768-foss-gaojianfu
	 * @update 2016年4月27日 下午1:38:10
	 */
	public void setPlatformNo(String platformNo) {
		this.platformNo = platformNo;
	}

	/**
	 * @description 获取 建立任务部门编号
	 * @return
	 * @version 1.0
	 * @author 328768-foss-gaojianfu
	 * @update 2016年4月27日 下午1:38:28
	 */
	public String getUnloadOrgCode() {
		return unloadOrgCode;
	}

	/**
	 * @description 设置 建立任务部门编号
	 * @param unloadOrgCode
	 * @version 1.0
	 * @author 328768-foss-gaojianfu
	 * @update 2016年4月27日 下午1:38:46
	 */
	public void setUnloadOrgCode(String unloadOrgCode) {
		this.unloadOrgCode = unloadOrgCode;
	}

	/**
	 * @description 获取 创建人工号
	 * @return
	 * @version 1.0
	 * @author 328768-foss-gaojianfu
	 * @update 2016年4月27日 下午1:39:04
	 */
	public String getCreateNo() {
		return createNo;
	}

	/**
	 * @description 设置 创建人工号
	 * @param createNo
	 * @version 1.0
	 * @author 328768-foss-gaojianfu
	 * @update 2016年4月27日 下午1:39:17
	 */
	public void setCreateNo(String createNo) {
		this.createNo = createNo;
	}
	
	/**
	 * @description 获取 卸车方式
	 * @return
	 * @version 1.0
	 * @author 328768-foss-gaojianfu
	 * @update 2016年5月3日 上午11:24:39
	 */

	public String getUnloadWay() {
		return unloadWay;
	}

	/**
	 * @description 设置 卸车方式
	 * @param unloadWay
	 * @version 1.0
	 * @author 328768-foss-gaojianfu
	 * @update 2016年5月3日 上午11:25:23
	 */
	public void setUnloadWay(String unloadWay) {
		this.unloadWay = unloadWay;
	}

	/**
	 * @description 获取 创建时间
	 * @return
	 * @version 1.0
	 * @author 328768-foss-gaojianfu
	 * @update 2016年4月27日 下午1:39:48
	 */
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * @description 设置 创建时间
	 * @param createTime
	 * @version 1.0
	 * @author 328768-foss-gaojianfu
	 * @update 2016年4月27日 下午1:40:08
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	/**
	 * @description 获取  操作设备
	 * @return
	 * @version 1.0
	 * @author 328768-foss-gaojianfu
	 * @update 2016年5月3日 上午11:26:01
	 */

	public String getOperationDevice() {
		return operationDevice;
	}

	/**
 	 * @description 设置  操作设备
	 * @param operationDevice
	 * @version 1.0
	 * @author 328768-foss-gaojianfu
	 * @update 2016年5月3日 上午11:26:22
	 */
	public void setOperationDevice(String operationDevice) {
		this.operationDevice = operationDevice;
	}
	
	
     /**
      * @description 获取  操作设备编码
      * @version 1.0
      * @author 328768-foss-gaojianfu
      * @update 2016年5月3日 上午11:27:00
      */
	public String getOperationDeviceCode() {
		return operationDeviceCode;
	}

	/**
	* @description 设置  操作设备编码
	* @param operationDeviceCode
	* @version 1.0
	* @author 328768-foss-gaojianfu
	* @update 2016年5月3日 上午11:27:25
	 */
	public void setOperationDeviceCode(String operationDeviceCode) {
		this.operationDeviceCode = operationDeviceCode;
	}

	/**
	* @description 获取  操作设备编码
	* @return
	* @version 1.0
	* @author 328768-foss-gaojianfu
	* @update 2016年5月3日 上午11:27:41
	 */
	public String getOperationCode() {
		return operationCode;
	}

	/**
	* @description 设置  操作设备编码
	* @param operationCode
	* @version 1.0
	* @author 328768-foss-gaojianfu
	* @update 2016年5月3日 上午11:28:00
	 */
	public void setOperationCode(String operationCode) {
		this.operationCode = operationCode;
	}

	/**
	* @description 获取  辅助操作码
	* @return
	* @version 1.0
	* @author 328768-foss-gaojianfu
	* @update 2016年5月3日 上午11:28:21
	 */
	public String getOperationAssistCode() {
		return operationAssistCode;
	}

	/**
	* @description 设置   辅助操作码
	* @param operationAssistCode
	* @version 1.0
	* @author 328768-foss-gaojianfu
	* @update 2016年5月3日 上午11:29:04
	 */
	public void setOperationAssistCode(String operationAssistCode) {
		this.operationAssistCode = operationAssistCode;
	}

	/**
	* @description 获取  控制状态
	* @return
	* @version 1.0
	* @author 328768-foss-gaojianfu
	* @update 2016年5月3日 上午11:29:33
	 */
	public String getControlStatus() {
		return controlStatus;
	}

	/**
	* @description 设置  控制状态
	* @param controlStatus
	* @version 1.0
	* @author 328768-foss-gaojianfu
	* @update 2016年5月3日 上午11:29:49
	 */
	public void setControlStatus(String controlStatus) {
		this.controlStatus = controlStatus;
	}

	/**
	 * @description 获取创建 卸车任务明细list
	 * @return
	 * @version 1.0
	 * @author 328768-foss-gaojianfu
	 * @update 2016年4月27日 下午1:40:28
	 */
	public List<ExpressUnloadTaskDetailDto> getCreateUnloadTaskDetailList() {
		return createUnloadTaskDetailList;
	}

	/**
	 * @description 设置创建 卸车任务明细list
	 * @param createUnloadTaskDetailList
	 * @version 1.0
	 * @author 328768-foss-gaojianfu
	 * @update 2016年4月27日 下午1:40:59
	 */
	public void setCreateUnloadTaskDetailList(List<ExpressUnloadTaskDetailDto> createUnloadTaskDetailList) {
		this.createUnloadTaskDetailList = createUnloadTaskDetailList;
	}

	/**
	 * @description 获取创建 卸车人员参与表 list
	 * @return
	 * @version 1.0
	 * @author 328768-foss-gaojianfu
	 * @update 2016年4月27日 下午1:41:23
	 */
	public List<ExpressLoaderParticipateDto> getCreateLoaderParticipateList() {
		return createLoaderParticipateList;
	}

	/**
	 * @description 设置创建 卸车人员参与表 list
	 * @param createLoaderParticipateList
	 * @version 1.0
	 * @author 328768-foss-gaojianfu
	 * @update 2016年4月27日 下午1:41:46
	 */
	public void setCreateLoaderParticipateList(List<ExpressLoaderParticipateDto> createLoaderParticipateList) {
		this.createLoaderParticipateList = createLoaderParticipateList;
	}

	public String getCreateOrgCode() {
		return createOrgCode;
	}

	public void setCreateOrgCode(String createOrgCode) {
		this.createOrgCode = createOrgCode;
	}

}
