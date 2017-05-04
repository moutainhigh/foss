package com.deppon.foss.module.transfer.unload.api.shared.domain;

import com.deppon.foss.framework.entity.BaseEntity;
public class UnloadbindTrayExpressEntity extends BaseEntity{

	/**
	 * @author 105795
	 * @date 2016-4-22
	 * @desc 卸车任务绑定托盘实体  快递
	 * 
	 * */
	
	private static final long serialVersionUID = -5798129780662092967L;

	//卸车任务号
	 private String unloadTaskNo;
	
	 //卸车创建人
	 private String unloadCreator;
	 
	 //卸车创建人编码
	 
	 private String unloadCreatorCode;
	 
	 //总票数
	 private int totalTicks;
	 
	 //已经绑定件数
	 private int bindPieces;
	 
	 //托盘绑定率
	 private String bindRate;
	 
	 //叉车扫描件数
	 private int scanPieces;
	 
	 //卸车扫描件数
	 private int unLoadScanPieces;
	 
	 //卸车任务车牌号
	 private String vehicleNo ;
	 
	 //叉车票数
	 private int forkliftTicks;
	 
	 //绑定的托盘任务总数
	 private int trayTaskTotal;
	 
	 //已扫描的托盘任务总数
	 private int scannedTotal;
	 
	 //创建任务时间
	 private String unloadTaskCreateTime;
	 
	 private long count;
	 
	public int getTrayTaskTotal() {
		return trayTaskTotal;
	}
	public void setTrayTaskTotal(int trayTaskTotal) {
		this.trayTaskTotal = trayTaskTotal;
	}
	public int getScannedTotal() {
		return scannedTotal;
	}
	public void setScannedTotal(int scannedTotal) {
		this.scannedTotal = scannedTotal;
	}
	public String getUnloadTaskNo() {
		return unloadTaskNo;
	}
	public void setUnloadTaskNo(String unloadTaskNo) {
		this.unloadTaskNo = unloadTaskNo;
	}
	public String getUnloadCreator() {
		return unloadCreator;
	}
	public void setUnloadCreator(String unloadCreator) {
		this.unloadCreator = unloadCreator;
	}
	public int getTotalTicks() {
		return totalTicks;
	}
	public void setTotalTicks(int totalTicks) {
		this.totalTicks = totalTicks;
	}
	public int getBindPieces() {
		return bindPieces;
	}
	public void setBindPieces(int bindPieces) {
		this.bindPieces = bindPieces;
	}
	
	public String getBindRate() {
		return bindRate;
	}
	public void setBindRate(String bindRate) {
		this.bindRate = bindRate;
	}
	public int getScanPieces() {
		return scanPieces;
	}
	public void setScanPieces(int scanPieces) {
		this.scanPieces = scanPieces;
	}
	public String getVehicleNo() {
		return vehicleNo;
	}
	public void setVehicleNo(String vehicleNo) {
		this.vehicleNo = vehicleNo;
	}
	public int getForkliftTicks() {
		return forkliftTicks;
	}
	public void setForkliftTicks(int forkliftTicks) {
		this.forkliftTicks = forkliftTicks;
	}
	public String getUnloadCreatorCode() {
		return unloadCreatorCode;
	}
	public void setUnloadCreatorCode(String unloadCreatorCode) {
		this.unloadCreatorCode = unloadCreatorCode;
	}
	public int getUnLoadScanPieces() {
		return unLoadScanPieces;
	}
	public void setUnLoadScanPieces(int unLoadScanPieces) {
		this.unLoadScanPieces = unLoadScanPieces;
	}
	
	public String getUnloadTaskCreateTime() {
		return unloadTaskCreateTime;
	}
	public void setUnloadTaskCreateTime(String unloadTaskCreateTime) {
		this.unloadTaskCreateTime = unloadTaskCreateTime;
	}
	
	public long getCount() {
		return count;
	}
	public void setCount(long count) {
		this.count = count;
	}
	@Override
	public String toString() {
		return "UnloadbindTrayExpressEntity [unloadTaskNo=" + unloadTaskNo
				+ ", unloadCreator=" + unloadCreator + ", unloadCreatorCode="
				+ unloadCreatorCode + ", totalTicks=" + totalTicks
				+ ", bindPieces=" + bindPieces + ", bindRate=" + bindRate
				+ ", scanPieces=" + scanPieces + ", unLoadScanPieces="
				+ unLoadScanPieces + ", vehicleNo=" + vehicleNo
				+ ", forkliftTicks=" + forkliftTicks + ", trayTaskTotal="
				+ trayTaskTotal + ", scannedTotal=" + scannedTotal + "]";
	}
	
	
	 
}
