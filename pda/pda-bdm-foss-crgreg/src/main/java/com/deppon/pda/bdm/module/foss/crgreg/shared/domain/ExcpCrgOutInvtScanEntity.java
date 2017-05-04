package com.deppon.pda.bdm.module.foss.crgreg.shared.domain;

import com.deppon.pda.bdm.module.core.shared.domain.ScanMsgEntity;
/**
 * 
 * TODO(异常物品出库)
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:Administrator,date:2012-12-1 下午2:43:17,content:TODO </p>
 * @author Administrator
 * @date 2012-12-1 下午2:43:17
 * @since
 * @version
 */
public class ExcpCrgOutInvtScanEntity extends ScanMsgEntity{
	/**
	 * 出库类型
	 */
	private String outInvtType;

	public String getOutInvtType() {
		return outInvtType;
	}
	
	public void setOutInvtType(String outInvtType) {
		this.outInvtType = outInvtType;
	}
	
	
}
