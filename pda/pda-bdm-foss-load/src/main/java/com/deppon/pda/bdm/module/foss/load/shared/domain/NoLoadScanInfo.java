package com.deppon.pda.bdm.module.foss.load.shared.domain;

import java.util.List;

/**
 * 
 * TODO(未装车备注)
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:Administrator,date:2012-12-4 下午5:04:16,content:TODO </p>
 * @author Administrator
 * @date 2012-12-4 下午5:04:16
 * @since
 * @version
 */
public class NoLoadScanInfo {
	private List<NoLoadRemarkScanEntity> noLoadRemarks;

	public List<NoLoadRemarkScanEntity> getNoLoadRemarks() {
		return noLoadRemarks;
	}

	public void setNoLoadRemarks(List<NoLoadRemarkScanEntity> noLoadRemarks) {
		this.noLoadRemarks = noLoadRemarks;
	}
	
}
