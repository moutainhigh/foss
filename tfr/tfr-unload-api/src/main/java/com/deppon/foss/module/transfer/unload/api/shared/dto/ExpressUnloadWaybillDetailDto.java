package com.deppon.foss.module.transfer.unload.api.shared.dto;

import java.io.Serializable;
import java.util.Date;

    /**
     * @description 快递卸车单据明细DTO
     * @version 1.0
     * @author 328768-foss-gaojianfu
     * @update 2016年4月29日 下午4:47:47
     */
public class ExpressUnloadWaybillDetailDto implements Serializable {

	
	private static final long serialVersionUID = -6690579762522038703L;

	/** 卸车任务编号 */
	private String unloadTaskNo;

	/** 单据号 */
	private String handoverBillNo;

	/** 件号 */
	private String cargoNo;

	/** 件类型 */
	private String cargoType;

	/** 已操作件数 */
	private Long operationGoodsQty;

	/** 扫描件数 */
	private Long scanGoodsQty;

	/** 出发部门编号 */
	private String origOrgCode;

	/** 出发部门名称 */
	private String origOrgName;

	/** 到达部门编号 */
	private String destOrgCode;

	/** 到达部门名称 */
	private String destOrgName;

	/** 产品类型 */
	private String transportType;

	/** 任务建立时间 */
	private Date taskBeginTime;

	/** 扫描状态 */
	private String scanStatus;

	/** 货物状态 */
	private String goodsStatus;

	/**
	 * @description 获取 卸车任务编号
	 * @return
	 * @version 1.0
	 * @author 328768-foss-gaojianfu
	 * @update 2016年4月29日 下午4:53:49
	 */
	public String getUnloadTaskNo() {
		return unloadTaskNo;
	}

	/**
	 * @description 设置 卸车任务编号
	 * @param unloadTaskNo
	 * @version 1.0
	 * @author 328768-foss-gaojianfu
	 * @update 2016年4月29日 下午4:54:47
	 */
	public void setUnloadTaskNo(String unloadTaskNo) {
		this.unloadTaskNo = unloadTaskNo;
	}

	/**
	 * @description 获取 单据号
	 * @return
	 * @version 1.0
	 * @author 328768-foss-gaojianfu
	 * @update 2016年4月29日 下午4:54:56
	 */
	public String getHandoverBillNo() {
		return handoverBillNo;
	}

	/**
	 * @description 设置 单据号
	 * @param handoverBillNo
	 * @version 1.0
	 * @author 328768-foss-gaojianfu
	 * @update 2016年4月29日 下午4:55:04
	 */
	public void setHandoverBillNo(String handoverBillNo) {
		this.handoverBillNo = handoverBillNo;
	}

	/**
	 * @description 获取 件号
	 * @return
	 * @version 1.0
	 * @author 328768-foss-gaojianfu
	 * @update 2016年4月29日 下午4:55:13
	 */
	public String getCargoNo() {
		return cargoNo;
	}

	/**
	 * @description 设置 件号
	 * @param cargoNo
	 * @version 1.0
	 * @author 328768-foss-gaojianfu
	 * @update 2016年4月29日 下午4:55:20
	 */
	public void setCargoNo(String cargoNo) {
		this.cargoNo = cargoNo;
	}

	/**
	 * @description 获取 件类型
	 * @return
	 * @version 1.0
	 * @author 328768-foss-gaojianfu
	 * @update 2016年4月29日 下午4:55:28
	 */
	public String getCargoType() {
		return cargoType;
	}

	/**
	 * @description 设置 件类型
	 * @param cargoType
	 * @version 1.0
	 * @author 328768-foss-gaojianfu
	 * @update 2016年4月29日 下午4:55:37
	 */
	public void setCargoType(String cargoType) {
		this.cargoType = cargoType;
	}

	/**
	 * @description 获取 已操作件数
	 * @return
	 * @version 1.0
	 * @author 328768-foss-gaojianfu
	 * @update 2016年4月29日 下午4:55:44
	 */
	public Long getOperationGoodsQty() {
		return operationGoodsQty;
	}

	/**
	 * @description 设置 已操作件数
	 * @param operationGoodsQty
	 * @version 1.0
	 * @author 328768-foss-gaojianfu
	 * @update 2016年4月29日 下午4:55:53
	 */
	public void setOperationGoodsQty(Long operationGoodsQty) {
		this.operationGoodsQty = operationGoodsQty;
	}

	/**
	 * @description 获取 扫描件数
	 * @return
	 * @version 1.0
	 * @author 328768-foss-gaojianfu
	 * @update 2016年4月29日 下午4:56:02
	 */
	public Long getScanGoodsQty() {
		return scanGoodsQty;
	}

	/**
 	 * @description 设置 扫描件数
	 * @param scanGoodsQty
	 * @version 1.0
	 * @author 328768-foss-gaojianfu
	 * @update 2016年4月29日 下午4:56:10
	 */
	public void setScanGoodsQty(Long scanGoodsQty) {
		this.scanGoodsQty = scanGoodsQty;
	}

	/**
	 * @description 获取 出发部门编号
	 * @return
	 * @version 1.0
	 * @author 328768-foss-gaojianfu
	 * @update 2016年4月29日 下午4:56:18
	 */
	public String getOrigOrgCode() {
		return origOrgCode;
	}

	/**
	 * @description 设置 出发部门编号
	 * @param origOrgCode
	 * @version 1.0
	 * @author 328768-foss-gaojianfu
	 * @update 2016年4月29日 下午4:56:26
	 */
	public void setOrigOrgCode(String origOrgCode) {
		this.origOrgCode = origOrgCode;
	}

	/**
	 * @description 获取 出发部门名称
	 * @return
	 * @version 1.0
	 * @author 328768-foss-gaojianfu
	 * @update 2016年4月29日 下午4:56:33
	 */
	public String getOrigOrgName() {
		return origOrgName;
	}

	/**
	 * @description 设置 出发部门名称
	 * @param origOrgName
	 * @version 1.0
	 * @author 328768-foss-gaojianfu
	 * @update 2016年4月29日 下午4:56:43
	 */
	public void setOrigOrgName(String origOrgName) {
		this.origOrgName = origOrgName;
	}

	/**
	 * @description 获取 到达部门编号
	 * @return
	 * @version 1.0
	 * @author 328768-foss-gaojianfu
	 * @update 2016年4月29日 下午4:56:53
	 */
	public String getDestOrgCode() {
		return destOrgCode;
	}

	/**
	 * @description 设置 到达部门编号
	 * @param destOrgCode
	 * @version 1.0
	 * @author 328768-foss-gaojianfu
	 * @update 2016年4月29日 下午4:57:01
	 */
	public void setDestOrgCode(String destOrgCode) {
		this.destOrgCode = destOrgCode;
	}

	/**
	 * @description 获取 到达部门名称
	 * @return
	 * @version 1.0
	 * @author 328768-foss-gaojianfu
	 * @update 2016年4月29日 下午4:57:11
	 */
	public String getDestOrgName() {
		return destOrgName;
	}

	/**
	 * @description 设置 到达部门名称
	 * @param destOrgName
	 * @version 1.0
	 * @author 328768-foss-gaojianfu
	 * @update 2016年4月29日 下午4:57:19
	 */
	public void setDestOrgName(String destOrgName) {
		this.destOrgName = destOrgName;
	}

	/**
	 * @description 获取 产品类型
 	 * @return
	 * @version 1.0
	 * @author 328768-foss-gaojianfu
	 * @update 2016年4月29日 下午4:57:26
	 */
	public String getTransportType() {
		return transportType;
	}

	/**
	 * @description 设置 产品类型
	 * @param transportType
	 * @version 1.0
	 * @author 328768-foss-gaojianfu
	 * @update 2016年4月29日 下午4:57:35
	 */
	public void setTransportType(String transportType) {
		this.transportType = transportType;
	}

	/**
	 * @description 获取 任务建立时间
	 * @return
	 * @version 1.0
	 * @author 328768-foss-gaojianfu
	 * @update 2016年4月29日 下午4:57:44
	 */
	public Date getTaskBeginTime() {
		return taskBeginTime;
	}

	/**
	 * @description 设置 任务建立时间
	 * @param taskBeginTime
	 * @version 1.0
	 * @author 328768-foss-gaojianfu
	 * @update 2016年4月29日 下午4:57:53
	 */
	public void setTaskBeginTime(Date taskBeginTime) {
		this.taskBeginTime = taskBeginTime;
	}

	/**
	 * @description 获取 扫描状态
	 * @return
	 * @version 1.0
	 * @author 328768-foss-gaojianfu
	 * @update 2016年4月29日 下午4:58:01
	 */
	public String getScanStatus() {
		return scanStatus;
	}

	/**
	 * @description 设置 扫描状态
	 * @param scanStatus
	 * @version 1.0
	 * @author 328768-foss-gaojianfu
	 * @update 2016年4月29日 下午4:58:09
	 */
	public void setScanStatus(String scanStatus) {
		this.scanStatus = scanStatus;
	}

	/**
	 * @description 获取 货物状态
	 * @return
	 * @version 1.0
	 * @author 328768-foss-gaojianfu
	 * @update 2016年4月29日 下午4:58:17
	 */
	public String getGoodsStatus() {
		return goodsStatus;
	}

	/**
	 * @description 设置 货物状态
	 * @param goodsStatus
	 * @version 1.0
	 * @author 328768-foss-gaojianfu
	 * @update 2016年4月29日 下午4:58:28
	 */
	public void setGoodsStatus(String goodsStatus) {
		this.goodsStatus = goodsStatus;
	}
	
}
