package com.deppon.foss.module.transfer.pda.api.shared.dto;

import java.util.List;

/**
* @description PDA传给foss的装车任务参数
* @version 1.0
* @author 328864-foss-xieyang
* @update 2016年5月5日 上午8:04:57
*/
public class WKLoadTaskDto extends PDATaskDto {

	
	/**
	* @fields serialVersionUID
	* @author 328864-foss-xieyang
	* @update 2016年5月5日 上午8:05:42
	* @version V1.0
	*/
	
	private static final long  serialVersionUID = 1L;
	
	/**任务类型*/
	private String loadTaskType;
	/**货物类型*/
	private String goodsType;
	/**派送单号*/
	private String deliverBillNo;
	/**目的站编码*/
	private List<String> destOrgCodes;
	/**快递员工号**/
	private String tallyerCode;
	
	/** 装车类型 快递 1, 零担0, 合单2 **/
	private Integer sendType;
	
	/** 同步给悟空系统的操作人员信息 **/
	private LoadTaskCreateDto loadTaskCreateDto;
	
	
	/** 同步给悟空系统创建交接单详细	*/
//	private LoadTaskDetailDto loadTaskDetailDto;
	
	/**
	 * Gets the 任务类型.
	 *
	 * @return the 任务类型
	 */
	public String getLoadTaskType() {
		return loadTaskType;
	}
	
	/**
	 * Sets the 任务类型.
	 *
	 * @param loadTaskType the new 任务类型
	 */
	public void setLoadTaskType(String loadTaskType) {
		this.loadTaskType = loadTaskType;
	}
	
	/**
	 * Gets the 货物类型.
	 *
	 * @return the 货物类型
	 */
	public String getGoodsType() {
		return goodsType;
	}
	
	/**
	 * Sets the 货物类型.
	 *
	 * @param goodsType the new 货物类型
	 */
	public void setGoodsType(String goodsType) {
		this.goodsType = goodsType;
	}
	
	/**
	 * Gets the 派送单号.
	 *
	 * @return the 派送单号
	 */
	public String getDeliverBillNo() {
		return deliverBillNo;
	}
	
	/**
	 * Sets the 派送单号.
	 *
	 * @param deliverBillNo the new 派送单号
	 */
	public void setDeliverBillNo(String deliverBillNo) {
		this.deliverBillNo = deliverBillNo;
	}
	
	/**
	 * Gets the 目的站编码.
	 *
	 * @return the 目的站编码
	 */
	public List<String> getDestOrgCodes() {
		return destOrgCodes;
	}
	
	/**
	 * Sets the 目的站编码.
	 *
	 * @param destOrgCodes the new 目的站编码
	 */
	public void setDestOrgCodes(List<String> destOrgCodes) {
		this.destOrgCodes = destOrgCodes;
	}

	public String getTallyerCode() {
		return tallyerCode;
	}

	public void setTallyerCode(String tallyerCode) {
		this.tallyerCode = tallyerCode;
	}

	/** 装车类型 快递 1, 零担0, 合单2 **/
	public Integer getSendType() {
		return sendType;
	}

	/** 装车类型 快递 1, 零担0, 合单2 **/
	public void setSendType(Integer sendType) {
		this.sendType = sendType;
	}

	/** 获取同步给悟空系统的操作人员信息 **/
	public LoadTaskCreateDto getLoadTaskCreateDto() {
		return loadTaskCreateDto;
	}

	/** 设置同步给悟空系统的操作人员信息 **/
	public void setLoadTaskCreateDto(LoadTaskCreateDto loadTaskCreateDto) {
		this.loadTaskCreateDto = loadTaskCreateDto;
	}

	@Override
	public String toString() {
		return "WKLoadTaskDto [loadTaskType=" + loadTaskType + ", goodsType="
				+ goodsType + ", deliverBillNo=" + deliverBillNo
				+ ", destOrgCodes=" + destOrgCodes + ", tallyerCode="
				+ tallyerCode + ", sendType=" + sendType
				+ ", loadTaskCreateDto=" + loadTaskCreateDto
				+ ", getLoaderCodes()=" + getLoaderCodes()
				+ ", getVehicleNo()=" + getVehicleNo() + ", getPlatformNo()="
				+ getPlatformNo() + ", getCreateOrgCode()="
				+ getCreateOrgCode() + ", getCreateTime()=" + getCreateTime()
				+ ", getOperatorCode()=" + getOperatorCode()
				+ ", getDeviceNo()=" + getDeviceNo() + ", getTaskNo()="
				+ getTaskNo() + ", getTransitGoodsType()="
				+ getTransitGoodsType() + ", getClass()=" + getClass()
				+ ", hashCode()=" + hashCode() + ", toString()="
				+ super.toString() + "]";
	}

	/** 获取同步给悟空系统创建交接单详细	*/
/*	public LoadTaskDetailDto getLoadTaskDetailDto() {
		return loadTaskDetailDto;
	}

	*//** 设置同步给悟空系统创建交接单详细	*//*
	public void setLoadTaskDetailDto(LoadTaskDetailDto loadTaskDetailDto) {
		this.loadTaskDetailDto = loadTaskDetailDto;
	}*/
	

}
