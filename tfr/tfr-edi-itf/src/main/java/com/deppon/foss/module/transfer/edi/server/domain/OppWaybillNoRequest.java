package com.deppon.foss.module.transfer.edi.server.domain;


/**
 * 
* @description 根据OPP传来的运单号查询FOSS流水号
* @version 1.0
* @author 269701-foss-lln
* @update 2016年5月12日 上午11:42:51
 */
public class OppWaybillNoRequest {

	//运单号
	private String waybillNo;


	/**
	 * @return waybillNo : return the property waybillNo.
	 * @author 269701-foss-lln
	 * @update 2016年5月12日 上午11:44:35
	 * @version V1.0
	 */
	public String getWaybillNo() {
		return waybillNo;
	}

	/**
	 * @param waybillNo : set the property waybillNo.
	 * @author 269701-foss-lln
	 * @update 2016年5月12日 上午11:44:35
	 * @version V1.0
	 */
	
	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}
	
}
