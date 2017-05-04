package com.deppon.pda.bdm.module.foss.load.shared.domain.derytranload;

import com.deppon.pda.bdm.module.core.shared.domain.ScanMsgEntity;

/**
 * 二程接驳派件交接扫描
 * @author 092038
 *
 */
public class DeryLoadScanEntity extends ScanMsgEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 派送单号
	 */
	private String deryCode;

	public String getDeryCode() {
		return deryCode;
	}

	public void setDeryCode(String deryCode) {
		this.deryCode = deryCode;
	}
	/**
	 * 到达联件数
	 */
	private int sumPieces;

	public int getSumPieces() {
		return sumPieces;
	}

	public void setSumPieces(int sumPieces) {
		this.sumPieces = sumPieces;
	}

}
