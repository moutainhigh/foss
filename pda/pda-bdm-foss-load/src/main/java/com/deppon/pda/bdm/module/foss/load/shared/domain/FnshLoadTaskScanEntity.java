package com.deppon.pda.bdm.module.foss.load.shared.domain;

import com.deppon.pda.bdm.module.core.shared.domain.ScanMsgEntity;
/**
 * 完成装车任务扫描类
 * @author gaojia
 * @date Sep 6,2012 10:00:30 AM
 * @version 1.0
 * @since
 */
public class FnshLoadTaskScanEntity extends ScanMsgEntity{
	
	/**
	 * 装车类型
	 */
	private String loadType;
	/**
	 * 扫描件数
	 */
	private int scanPieces;
	/**
	 * 撤销件数
	 */
	private int caclScanPieces;
	/**
	 * 总票数
	 */
	private int totalVotes;
	
	public int getTotalVotes() {
		return totalVotes;
	}
	public void setTotalVotes(int totalVotes) {
		this.totalVotes = totalVotes;
	}
	public int getScanPieces() {
		return scanPieces;
	}
	public void setScanPieces(int scanPieces) {
		this.scanPieces = scanPieces;
	}
	public int getCaclScanPieces() {
		return caclScanPieces;
	}
	public void setCaclScanPieces(int caclScanPieces) {
		this.caclScanPieces = caclScanPieces;
	}
	
	public String getLoadType() {
		return loadType;
	}

	public void setLoadType(String loadType) {
		this.loadType = loadType;
	}
	
	
	
}