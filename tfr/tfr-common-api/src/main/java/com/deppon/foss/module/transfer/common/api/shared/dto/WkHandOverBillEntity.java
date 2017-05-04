package com.deppon.foss.module.transfer.common.api.shared.dto;

import java.io.Serializable;

/**
* @description 同步给快递系统的:交接单实体类
* @version 1.0
* @author 332209-foss ruilibao
* @update 2016年4月27日 上午10:06:21
*/
public class WkHandOverBillEntity implements Serializable{
	
	/**
	* @fields serialVersionUID
	* @author 332209-foss ruilibao
	* @update 2016年4月27日 上午10:08:09
	* @version V1.0
	*/
	
	private static final long serialVersionUID = 1L;

	/*交接单号*/
	private String handoverBillNo;
	/*操作部门，用作与交接单组织分片查询*/
	private String operationOrgCode;
	
	/**
	 * 获取交接单
	 * @return
	 */
	public String getHandoverBillNo() {
		return handoverBillNo;
	}
	/**
	 * 设置交接单
	 * @param handoverBillNo
	 */
	public void setHandoverBillNo(String handoverBillNo) {
		this.handoverBillNo = handoverBillNo;
	}
	
	/**
	 * 获取操作部门，用作与交接单组织分片查询
	 * @return
	 */
	public String getOperationOrgCode() {
		return operationOrgCode;
	}
	
	/**
	 * 设置操作部门，用作与交接单组织分片查询
	 * @param operationOrgCode
	 */
	public void setOperationOrgCode(String operationOrgCode) {
		this.operationOrgCode = operationOrgCode;
	}
	
	
	
}
