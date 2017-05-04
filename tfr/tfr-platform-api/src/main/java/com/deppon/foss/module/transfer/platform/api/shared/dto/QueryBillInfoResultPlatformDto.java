package com.deppon.foss.module.transfer.platform.api.shared.dto;


import java.math.BigDecimal;
import java.util.Date;

import com.deppon.foss.module.transfer.platform.api.shared.define.PlatformConstants;

/**
 * 查询单据信息结果
 * @author 046130-foss-xuduowei
 * @date 2012-12-13 下午4:09:09
 */
public class QueryBillInfoResultPlatformDto {
	
	/**
	 * 单据编号
	 */
	private String billNumber;
	/**
	 * 车辆到达时间
	 */
	private Date arrivedTime;
	/**
	 * 分配时间
	 */
	private Date distributeTime;
	/**
	 * 总体积
	 */
	private BigDecimal totalVolume;
	/**
	 * 总重量
	 */
	private BigDecimal totalWeight;
	/**
	 * 总件数
	 */
	private int totalPieces;
	/**
	 * 出发部门
	 */
	private String leaveDept;
	/**
	 * 获取 单据编号.
	 *
	 * @return the 单据编号
	 */
	public String getBillNumber() {
		return billNumber;
	}
	
	/**
	 * 设置 单据编号.
	 *
	 * @param billNumber the new 单据编号
	 */
	public void setBillNumber(String billNumber) {
		this.billNumber = billNumber;
	}
	
	/**
	 * 获取 车辆到达时间.
	 *
	 * @return the 车辆到达时间
	 */
	public Date getArrivedTime() {
		return arrivedTime;
	}
	
	/**
	 * 设置 车辆到达时间.
	 *
	 * @param arrivedTime the new 车辆到达时间
	 */
	public void setArrivedTime(Date arrivedTime) {
		this.arrivedTime = arrivedTime;
	}
	
	/**
	 * 获取 分配时间.
	 *
	 * @return the 分配时间
	 */
	public Date getDistributeTime() {
		return distributeTime;
	}
	
	/**
	 * 设置 分配时间.
	 *
	 * @param distributeTime the new 分配时间
	 */
	public void setDistributeTime(Date distributeTime) {
		this.distributeTime = distributeTime;
	}
	
	/**
	 * 获取 总体积.
	 *
	 * @return the 总体积
	 */
	public BigDecimal getTotalVolume() {
		return totalVolume;
	}
	
	/**
	 * 设置 总体积.
	 *
	 * @param totalVolume the new 总体积
	 */
	public void setTotalVolume(BigDecimal totalVolume) {
		this.totalVolume = totalVolume.setScale(PlatformConstants.SONAR_NUMBER_3,BigDecimal.ROUND_HALF_UP);
	}
	
	/**
	 * 获取 总重量.
	 *
	 * @return the 总重量
	 */
	public BigDecimal getTotalWeight() {
		return totalWeight;
	}
	
	/**
	 * 设置 总重量.
	 *
	 * @param totalWeight the new 总重量
	 */
	public void setTotalWeight(BigDecimal totalWeight) {
		this.totalWeight = totalWeight.setScale(PlatformConstants.SONAR_NUMBER_3,BigDecimal.ROUND_HALF_UP);
	}
	
	/**
	 * 获取 总件数.
	 *
	 * @return the 总件数
	 */
	public int getTotalPieces() {
		return totalPieces;
	}
	
	/**
	 * 设置 总件数.
	 *
	 * @param totalPieces the new 总件数
	 */
	public void setTotalPieces(int totalPieces) {
		this.totalPieces = totalPieces;
	}

	/**
	 * 获取 出发部门.
	 *
	 * @return the 出发部门
	 */
	public String getLeaveDept() {
		return leaveDept;
	}

	/**
	 * 设置 出发部门.
	 *
	 * @param leaveDept the new 出发部门
	 */
	public void setLeaveDept(String leaveDept) {
		this.leaveDept = leaveDept;
	}
	
	
	

}
