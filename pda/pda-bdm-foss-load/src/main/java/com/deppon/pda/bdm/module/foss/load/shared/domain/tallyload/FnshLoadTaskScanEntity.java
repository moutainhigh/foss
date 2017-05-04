package com.deppon.pda.bdm.module.foss.load.shared.domain.tallyload;

import com.deppon.pda.bdm.module.core.shared.domain.ScanMsgEntity;

/**
 * 完成接驳装车任务
 * @ClassName TranFnshLoadTaskEntity.java 
 * @Description 
 * @author 245955
 * @date 2015-4-14
 */
public class FnshLoadTaskScanEntity extends ScanMsgEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
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
	public String getLoadType() {
		return loadType;
	}
	public void setLoadType(String loadType) {
		this.loadType = loadType;
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
	public int getTotalVotes() {
		return totalVotes;
	}
	public void setTotalVotes(int totalVotes) {
		this.totalVotes = totalVotes;
	}

}
