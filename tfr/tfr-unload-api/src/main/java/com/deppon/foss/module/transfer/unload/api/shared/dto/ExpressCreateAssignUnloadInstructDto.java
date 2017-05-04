package com.deppon.foss.module.transfer.unload.api.shared.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

    /**
     * @description 同步分配卸车指令 Dto
     * @version 1.0
     * @author 328768-foss-gaojianfu
     * @update 2016年4月29日 下午1:36:15
     */
public class ExpressCreateAssignUnloadInstructDto implements Serializable {

	

	private static final long serialVersionUID = -344272510545411478L;

	/** 分配卸车指令编号 */
	private String commandNo;

	/** 车辆任务明细编号 */
	private String vehicleTaskDetailNo;

	/** 车牌号 */
	private String vehicleNo;

	/** 车型 */
	private String vehicleTypeLength;

	/** 卸车类型 */
	private String unloadType;

	/** 到达时间 */
	private Date arriveTime;
	
	/** 总重量 */
	private BigDecimal totalWeight;

	/** 总体积 */
	private BigDecimal totalVolume;

	/** 总件数 */
	private Long totalQty;

	/** 月台号 */
	private String platformNo;

	/**
	 * 创建人工号
	 */
	private String createNo;
	
	/**
	 * 创建组织编号
	 */
	private String createOrgCode;

	/**
	 * 创建时间
	 */
	private Date createTime;

	/**
	 * 明细
	 */
	private List<ExpressUnloadcmdAssignDetailDto> unloadcmdAssignDetailList;

    /**
     * @description 获取 分配卸车指令编号
     * @return
     * @version 1.0
     * @author 328768-foss-gaojianfu
     * @update 2016年4月29日 下午1:37:07
     */
	public String getCommandNo() {
		return commandNo;
	}

	/**
	 * @description 设置 分配卸车指令编号
	 * @param commandNo
	 * @version 1.0
	 * @author 328768-foss-gaojianfu
	 * @update 2016年4月29日 下午1:37:29
	 */
	public void setCommandNo(String commandNo) {
		this.commandNo = commandNo;
	}

	/**
	 * @description 获取 车辆任务明细编号
	 * @return
	 * @version 1.0
	 * @author 328768-foss-gaojianfu
	 * @update 2016年4月29日 下午1:37:45
	 */
	public String getVehicleTaskDetailNo() {
		return vehicleTaskDetailNo;
	}

	/**
	 * @description 设置 车辆任务明细编号
	 * @param vehicleTaskDetailNo
	 * @version 1.0
	 * @author 328768-foss-gaojianfu
	 * @update 2016年4月29日 下午1:38:02
	 */
	public void setVehicleTaskDetailNo(String vehicleTaskDetailNo) {
		this.vehicleTaskDetailNo = vehicleTaskDetailNo;
	}

	/**
	 * @description 获取 车牌号
	 * @return
	 * @version 1.0
	 * @author 328768-foss-gaojianfu
	 * @update 2016年4月29日 下午1:38:15
	 */
	public String getVehicleNo() {
		return vehicleNo;
	}

	/**
	 * @description 设置 车牌号
	 * @param vehicleNo
	 * @version 1.0
	 * @author 328768-foss-gaojianfu
	 * @update 2016年4月29日 下午1:38:32
	 */
	public void setVehicleNo(String vehicleNo) {
		this.vehicleNo = vehicleNo;
	}

	/**
	 * @description 获取 车型
	 * @return
	 * @version 1.0
	 * @author 328768-foss-gaojianfu
	 * @update 2016年4月29日 下午1:38:47
	 */
	public String getVehicleTypeLength() {
		return vehicleTypeLength;
	}

	/**
	 * @description 设置 车型
	 * @param vehicleTypeLength
	 * @version 1.0
	 * @author 328768-foss-gaojianfu
	 * @update 2016年4月29日 下午1:39:05
	 */
	public void setVehicleTypeLength(String vehicleTypeLength) {
		this.vehicleTypeLength = vehicleTypeLength;
	}

	/**
	 * @description 获取 卸车类型
	 * @return
	 * @version 1.0
	 * @author 328768-foss-gaojianfu
	 * @update 2016年4月29日 下午1:39:49
	 */
	public String getUnloadType() {
		return unloadType;
	}

	/**
	 * @description 设置 卸车类型
	 * @param unloadType
	 * @version 1.0
	 * @author 328768-foss-gaojianfu
	 * @update 2016年4月29日 下午1:40:04
	 */
	public void setUnloadType(String unloadType) {
		this.unloadType = unloadType;
	}

	/**
	 * @description 获取 到达时间
	 * @return
	 * @version 1.0
	 * @author 328768-foss-gaojianfu
	 * @update 2016年4月29日 下午1:40:19
	 */
	public Date getArriveTime() {
		return arriveTime;
	}

	/**
	 * @description 设置 到达时间
	 * @param arriveTime
	 * @version 1.0
	 * @author 328768-foss-gaojianfu
	 * @update 2016年4月29日 下午1:40:33
	 */
	public void setArriveTime(Date arriveTime) {
		this.arriveTime = arriveTime;
	}
	/**
	 * @description 获取 总重量
	 * @return
	 * @version 1.0
	 * @author 328768-foss-gaojianfu
	 * @update 2016年4月29日 下午1:40:48
	 */
	public BigDecimal getTotalWeight() {
		return totalWeight;
	}

	/**
	 * @description 设置 总重量
	 * @param totalWeight
	 * @version 1.0
	 * @author 328768-foss-gaojianfu
	 * @update 2016年4月29日 下午1:41:20
	 */
	public void setTotalWeight(BigDecimal totalWeight) {
		this.totalWeight = totalWeight;
	}

	/**
	 * @description 获取 总体积
	 * @return
	 * @version 1.0
	 * @author 328768-foss-gaojianfu
	 * @update 2016年4月29日 下午1:41:35
	 */
	public BigDecimal getTotalVolume() {
		return totalVolume;
	}

	/**
	 * @description 设置 总体积
	 * @param totalVolume
	 * @version 1.0
	 * @author 328768-foss-gaojianfu
	 * @update 2016年4月29日 下午1:41:53
	 */
	public void setTotalVolume(BigDecimal totalVolume) {
		this.totalVolume = totalVolume;
	}

	/**
	 * @description 获取 总件数
	 * @return
	 * @version 1.0
	 * @author 328768-foss-gaojianfu
	 * @update 2016年4月29日 下午1:42:13
	 */
	public Long getTotalQty() {
		return totalQty;
	}

	/**
	 * @description 设置 总件数
	 * @param totalQty
	 * @version 1.0
	 * @author 328768-foss-gaojianfu
	 * @update 2016年4月29日 下午1:42:37
	 */
	public void setTotalQty(Long totalQty) {
		this.totalQty = totalQty;
	}

	/**
	 * @description 获取 月台号
	 * @return
	 * @version 1.0
	 * @author 328768-foss-gaojianfu
	 * @update 2016年4月29日 下午1:42:53
	 */
	public String getPlatformNo() {
		return platformNo;
	}

	/**
	 * @description 设置 月台号
	 * @param platformNo
	 * @version 1.0
	 * @author 328768-foss-gaojianfu
	 * @update 2016年4月29日 下午1:48:22
	 */
	public void setPlatformNo(String platformNo) {
		this.platformNo = platformNo;
	}

	/**
	 * @description 获取 创建人工号
	 * @return
	 * @version 1.0
	 * @author 328768-foss-gaojianfu
	 * @update 2016年4月29日 下午1:48:55
	 */
	public String getCreateNo() {
		return createNo;
	}

	/**
	 * @description 设置 创建人工号
	 * @param createNo
	 * @version 1.0
	 * @author 328768-foss-gaojianfu
	 * @update 2016年4月29日 下午1:49:09
	 */
	public void setCreateNo(String createNo) {
		this.createNo = createNo;
	}
	
	/**
	 * @description 获取 创建组织编号
	 * @return
	 * @version 1.0
	 * @author 328768-foss-gaojianfu
	 * @update 2016年5月16日 上午11:25:37
	 */

	public String getCreateOrgCode() {
		return createOrgCode;
	}

	/**
	 * @description 设置  创建组织编号
	 * @param createOrgCode
	 * @version 1.0
	 * @author 328768-foss-gaojianfu
	 * @update 2016年5月16日 上午11:26:15
	 */
	public void setCreateOrgCode(String createOrgCode) {
		this.createOrgCode = createOrgCode;
	}

	/**
	 * @description 获取 创建时间
	 * @return
	 * @version 1.0
	 * @update 2016年4月29日 下午1:49:27
	 */
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * @description 设置 创建时间
	 * @param createTime
	 * @version 1.0
	 * @author 328768-foss-gaojianfu
	 * @update 2016年4月29日 下午1:49:40
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public List<ExpressUnloadcmdAssignDetailDto> getUnloadcmdAssignDetailList() {
		return unloadcmdAssignDetailList;
	}

	public void setUnloadcmdAssignDetailList(List<ExpressUnloadcmdAssignDetailDto> unloadcmdAssignDetailList) {
		this.unloadcmdAssignDetailList = unloadcmdAssignDetailList;
	}
}
