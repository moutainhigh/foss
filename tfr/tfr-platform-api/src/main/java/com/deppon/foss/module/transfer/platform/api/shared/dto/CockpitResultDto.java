package com.deppon.foss.module.transfer.platform.api.shared.dto;

import java.io.Serializable;
import java.util.Date;

public class CockpitResultDto implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 当前时间
	 */
	private Date currentTime;

	/**
	 * 上班人数
	 */
	private String onDutyNums;

	/**
	 * 异常人数
	 */
	private String absenteeNums;

	/**
	 * 在线理货员人数
	 */
	private String tallyNums;

	/**
	 * 在线电叉司机人数
	 */
	private String forkNums;

	/**
	 * 货台库存货量(吨)
	 */
	private String stockWeight;

	/**
	 * 待卸货量(吨)
	 */
	private String waitUnloadWeight;

	/**
	 * (到达本外场)长途在途车辆数
	 */
	private String lngDisOnTheWayNums;

	/**
	 * (到达本外场)短途在途车辆数
	 */
	private String shtDisOnTheWayNums;

	/**
	 * (到达本外场)长途到达未卸车辆数
	 */
	private String lngDisArrivedNums;

	/**
	 * (到达本外场)短途到达未卸车辆数
	 */
	private String shtDisArrivedNums;

	/**
	 * 装卸车进度异常数
	 */
	private String loadUnloadProgressAbnormalNums;

	/**
	 * 月台使用率(百分比)
	 */
	private String platformUsageRate;

	/**
	 * 派送库存体积(方)
	 */
	private String dispatchStockVolume;

	/**
	 * 派送退单率(百分比)
	 */
	private String sendBackPct;

	/**
	 * 仓库饱和度(百分比)
	 */
	private String stockSaturation;

	public Date getCurrentTime() {
		return currentTime;
	}

	public void setCurrentTime(Date currentTime) {
		this.currentTime = currentTime;
	}

	public String getOnDutyNums() {
		return onDutyNums;
	}

	public void setOnDutyNums(String onDutyNums) {
		this.onDutyNums = onDutyNums;
	}

	public String getAbsenteeNums() {
		return absenteeNums;
	}

	public void setAbsenteeNums(String absenteeNums) {
		this.absenteeNums = absenteeNums;
	}

	public String getTallyNums() {
		return tallyNums;
	}

	public void setTallyNums(String tallyNums) {
		this.tallyNums = tallyNums;
	}

	public String getForkNums() {
		return forkNums;
	}

	public void setForkNums(String forkNums) {
		this.forkNums = forkNums;
	}

	public String getStockWeight() {
		return stockWeight;
	}

	public void setStockWeight(String stockWeight) {
		this.stockWeight = stockWeight;
	}

	public String getWaitUnloadWeight() {
		return waitUnloadWeight;
	}

	public void setWaitUnloadWeight(String waitUnloadWeight) {
		this.waitUnloadWeight = waitUnloadWeight;
	}

	public String getLngDisOnTheWayNums() {
		return lngDisOnTheWayNums;
	}

	public void setLngDisOnTheWayNums(String lngDisOnTheWayNums) {
		this.lngDisOnTheWayNums = lngDisOnTheWayNums;
	}

	public String getShtDisOnTheWayNums() {
		return shtDisOnTheWayNums;
	}

	public void setShtDisOnTheWayNums(String shtDisOnTheWayNums) {
		this.shtDisOnTheWayNums = shtDisOnTheWayNums;
	}

	public String getLngDisArrivedNums() {
		return lngDisArrivedNums;
	}

	public void setLngDisArrivedNums(String lngDisArrivedNums) {
		this.lngDisArrivedNums = lngDisArrivedNums;
	}

	public String getShtDisArrivedNums() {
		return shtDisArrivedNums;
	}

	public void setShtDisArrivedNums(String shtDisArrivedNums) {
		this.shtDisArrivedNums = shtDisArrivedNums;
	}

	public String getLoadUnloadProgressAbnormalNums() {
		return loadUnloadProgressAbnormalNums;
	}

	public void setLoadUnloadProgressAbnormalNums(String loadUnloadProgressAbnormalNums) {
		this.loadUnloadProgressAbnormalNums = loadUnloadProgressAbnormalNums;
	}

	public String getPlatformUsageRate() {
		return platformUsageRate;
	}

	public void setPlatformUsageRate(String platformUsageRate) {
		this.platformUsageRate = platformUsageRate;
	}

	public String getDispatchStockVolume() {
		return dispatchStockVolume;
	}

	public void setDispatchStockVolume(String dispatchStockVolume) {
		this.dispatchStockVolume = dispatchStockVolume;
	}

	public String getSendBackPct() {
		return sendBackPct;
	}

	public void setSendBackPct(String sendBackPct) {
		this.sendBackPct = sendBackPct;
	}

	public String getStockSaturation() {
		return stockSaturation;
	}

	public void setStockSaturation(String stockSaturation) {
		this.stockSaturation = stockSaturation;
	}

	@Override
	public String toString() {
		return "CockpitResultDto [currentTime=" + currentTime + ", onDutyNums=" + onDutyNums + ", absenteeNums="
				+ absenteeNums + ", tallyNums=" + tallyNums + ", forkNums=" + forkNums + ", stockWeight=" + stockWeight
				+ ", waitUnloadWeight=" + waitUnloadWeight + ", lngDisOnTheWayNums=" + lngDisOnTheWayNums
				+ ", shtDisOnTheWayNums=" + shtDisOnTheWayNums + ", lngDisArrivedNums=" + lngDisArrivedNums
				+ ", shtDisArrivedNums=" + shtDisArrivedNums + ", loadUnloadProgressAbnormalNums="
				+ loadUnloadProgressAbnormalNums + ", platformUsageRate=" + platformUsageRate
				+ ", dispatchStockVolume=" + dispatchStockVolume + ", sendBackPct=" + sendBackPct
				+ ", stockSaturation=" + stockSaturation + "]";
	}

}
