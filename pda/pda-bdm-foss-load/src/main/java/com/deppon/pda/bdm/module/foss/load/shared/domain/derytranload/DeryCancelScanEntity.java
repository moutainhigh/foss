package com.deppon.pda.bdm.module.foss.load.shared.domain.derytranload;

import com.deppon.pda.bdm.module.core.shared.domain.ScanMsgEntity;

/**
 * 派件交接扫描-撤销
 * @author 245955
 *
 */
public class DeryCancelScanEntity extends ScanMsgEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 派送单号
	 */
	private String deryCode;
	/**
	 * 到达联件数
	 */
	private int sumPieces;
	public String getDeryCode() {
		return deryCode;
	}
	public void setDeryCode(String deryCode) {
		this.deryCode = deryCode;
	}
	public int getSumPieces() {
		return sumPieces;
	}
	public void setSumPieces(int sumPieces) {
		this.sumPieces = sumPieces;
	}
	
}
