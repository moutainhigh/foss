package com.deppon.pda.bdm.module.foss.clear.shared.domain;

import com.deppon.pda.bdm.module.core.shared.domain.ScanMsgEntity;

/**
 * 找货货区扫描
 * @author 245955
 *
 */
public class FindGoodsAdminScanEntity extends ScanMsgEntity{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**运单号*/
	private String waybillNo;
	/**流水号*/
	private String serialNo;
	/**扫描人*/
	private String user;
	/**部门*/
	private String orgCode;
	
	public String getWaybillNo() {
		return waybillNo;
	}
	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}
	public String getSerialNo() {
		return serialNo;
	}
	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getOrgCode() {
		return orgCode;
	}
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	
}
