package com.deppon.pda.bdm.module.foss.unload.shared.domain;

import java.util.Date;
import java.util.List;

import com.deppon.pda.bdm.module.core.shared.domain.ScanMsgEntity;

/**
 * 托盘绑定扫描类实体
 * @author wenwuneng
 * @date 2013-08-09
 * @version 1.0
 * @since
 */
public class PalletBindingEntity extends ScanMsgEntity{
	/** 
	 * @fields serialVersionUID 
	 */ 
	
	private static final long serialVersionUID = 1L;
	/**
	 *绑定唯一编码
	 */
	private String bindingNo;
	/**
	 *绑定任务时间
	 *
	 */
	private Date bindingDate;
	/**
	 * 托盘扫描明细集合
	 */
	private List<BindingScanEntity> bindingScanInfoList;
	/**
	 * 卸车任务号
	 */
	private String unloadTaskCode;
	
	
    /**
     * PDA操作类型    手动拉车 ：HANDSCAN 叉车分货：UNSCAN
     * */
    private String operationType;
    
	
	
	
	
	
	public String getOperationType() {
		return operationType;
	}
	public void setOperationType(String operationType) {
		this.operationType = operationType;
	}
	public String getBindingNo() {
		return bindingNo;
	}
	public void setBindingNo(String bindingNo) {
		this.bindingNo = bindingNo;
	}
	
	
	public Date getBindingDate() {
		return bindingDate;
	}
	public void setBindingDate(Date bindingDate) {
		this.bindingDate = bindingDate;
	}
	public List<BindingScanEntity> getBindingScanInfoList() {
		return bindingScanInfoList;
	}
	public void setBindingScanInfoList(List<BindingScanEntity> bindingScanInfoList) {
		this.bindingScanInfoList = bindingScanInfoList;
	}
	public String getUnloadTaskCode() {
		return unloadTaskCode;
	}
	public void setUnloadTaskCode(String unloadTaskCode) {
		this.unloadTaskCode = unloadTaskCode;
	}
	
	
}