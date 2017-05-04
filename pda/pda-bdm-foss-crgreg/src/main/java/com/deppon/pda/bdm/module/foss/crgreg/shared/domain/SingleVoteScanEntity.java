package com.deppon.pda.bdm.module.foss.crgreg.shared.domain;

import com.deppon.pda.bdm.module.core.shared.domain.ScanMsgEntity;

/**
 * 单票入库
 * 
 * @author gaojia
 * @date Sep 6,2012 10:00:30 AM
 * @version 1.0
 * @since
 * @author chenliang
 * @modifyTime 20120908 15:11:20
 */
public class SingleVoteScanEntity extends ScanMsgEntity {

	/**
	 * @description
	 */

	private static final long serialVersionUID = 1719119618900365427L;

	/**
	 * 入库类型
	 */
	private String inInvtType;

	public String getInInvtType() {
		return inInvtType;
	}

	public void setInInvtType(String inInvtType) {
		this.inInvtType = inInvtType;
	}

}