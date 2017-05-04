package com.deppon.pda.bdm.module.foss.load.shared.domain;

import com.deppon.pda.bdm.module.core.shared.domain.ScanMsgEntity;

/**
 * 
 * TODO(未装车备注)
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:Administrator,date:2012-12-3 上午10:46:29,content:TODO </p>
 * @author Administrator
 * @date 2012-12-3 上午10:46:29
 * @since
 * @version
 */
public class NoLoadRemarkScanEntity extends ScanMsgEntity{
	/**
	 * 未装车原因
	 */
	private String noLoadReson;

	public String getNoLoadReson() {
		return noLoadReson;
	}

	public void setNoLoadReson(String noLoadReson) {
		this.noLoadReson = noLoadReson;
	}
	
}
