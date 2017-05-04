package com.deppon.pda.bdm.module.foss.crgreg.shared.domain;

import com.deppon.pda.bdm.module.core.shared.domain.ScanMsgEntity;
/**
 * 
 * TODO(异常物品入库)
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:Administrator,date:2012-12-1 下午2:42:59,content:TODO </p>
 * @author Administrator
 * @date 2012-12-1 下午2:42:59
 * @since
 * @version
 */
public class ExcpCrgInInvtScanEntity extends ScanMsgEntity{
	/**
	 * 异常货物类型
	 */
	private String excpCrgType;
	/**
	 * 异常货物包装类型
	 */
	private String excpWrapType;
	/**
	 * 备注
	 */
	private String remark;
	public String getExcpCrgType() {
		return excpCrgType;
	}
	public void setExcpCrgType(String excpCrgType) {
		this.excpCrgType = excpCrgType;
	}
	public String getExcpWrapType() {
		return excpWrapType;
	}
	public void setExcpWrapType(String excpWrapType) {
		this.excpWrapType = excpWrapType;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
}
