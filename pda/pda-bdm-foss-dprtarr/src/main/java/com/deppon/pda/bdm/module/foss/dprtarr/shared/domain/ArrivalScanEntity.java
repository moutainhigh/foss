package com.deppon.pda.bdm.module.foss.dprtarr.shared.domain;

import com.deppon.pda.bdm.module.core.shared.domain.ScanMsgEntity;


/**
 * 车辆到达扫描
 * @author gaojia
 * @date Sep 6,2012 10:00:30 AM
 * @version 1.0
 * @since
 */
public class ArrivalScanEntity extends ScanMsgEntity{
	/**
	 * @description
	 */      
	private static final long serialVersionUID = 2405568695605378632L;

	/**
	 *  扫描数据uuid
	 */
	private String id;
	/**
	 * 放行条码
	 */
	private String relseCode;
	/**
	 * 封签状态
	 */
	private String sealsStatus;
	/**
	 * 封签破损数量
	 */
	private int sealsBreaks;
	/**
	 * 车牌号
	 */
	private String truckCode;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getRelseCode() {
		return relseCode;
	}
	public void setRelseCode(String relseCode) {
		this.relseCode = relseCode;
	}
	public String getSealsStatus() {
		return sealsStatus;
	}
	public void setSealsStatus(String sealsStatus) {
		this.sealsStatus = sealsStatus;
	}
	public int getSealsBreaks() {
		return sealsBreaks;
	}
	public void setSealsBreaks(int sealsBreaks) {
		this.sealsBreaks = sealsBreaks;
	}
	public String getTruckCode() {
		return truckCode;
	}
	public void setTruckCode(String truckCode) {
		this.truckCode = truckCode;
	}
	
	
	
}