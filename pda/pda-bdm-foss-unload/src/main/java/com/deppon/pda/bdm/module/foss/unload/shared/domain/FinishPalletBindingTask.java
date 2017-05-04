package com.deppon.pda.bdm.module.foss.unload.shared.domain;

import java.util.Date;

import com.deppon.pda.bdm.module.core.shared.domain.ScanMsgEntity;

/**
 * 完成叉车绑定实体
 * @author wenwuneng
 * @date 2013-08-09
 * @version 1.0
 * @since
 */
public class FinishPalletBindingTask extends ScanMsgEntity{
	/** 
	 * @fields serialVersionUID 
	 */ 
	
	private static final long serialVersionUID = 1L;
	/**
	 *绑定唯一编码
	 */
	private String bindingNo;
	/**
	 *完成任务时间
	 */
	private Date fnshDate;
	public String getBindingNo() {
		return bindingNo;
	}
	public void setBindingNo(String bindingNo) {
		this.bindingNo = bindingNo;
	}
	public Date getFnshDate() {
		return fnshDate;
	}
	public void setFnshDate(Date fnshDate) {
		this.fnshDate = fnshDate;
	}
	
	
	
	
}