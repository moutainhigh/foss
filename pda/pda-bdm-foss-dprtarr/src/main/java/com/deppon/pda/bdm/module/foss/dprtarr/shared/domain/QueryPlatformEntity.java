package com.deppon.pda.bdm.module.foss.dprtarr.shared.domain;

import java.io.Serializable;

/**   
 * @ClassName QueryPlatformEntity  
 * @Description TODO   
 * @author  092038 张贞献  
 * @date 2015-5-27    
 */ 
public class QueryPlatformEntity implements Serializable {
	/**  
	 * @Fields serialVersionUID : TODO 
	 */
	private static final long serialVersionUID = 1L;
	//车牌号
	private String truckCode;
	//月台号 
	private String platformNo;
	//业务类型(必填)  长途:LONG_DISTANCE ,短途:SHORT_DISTANCE,接送货：DIVISION
	private String transType;
	//进场情况 (必填) 已入场 IN,未入场 OUT
	private String isInOrOut;
	
	
	
	public String getTruckCode() {
		return truckCode;
	}
	public void setTruckCode(String truckCode) {
		this.truckCode = truckCode;
	}
	public String getPlatformNo() {
		return platformNo;
	}
	public void setPlatformNo(String platformNo) {
		this.platformNo = platformNo;
	}
	public String getTransType() {
		return transType;
	}
	public void setTransType(String transType) {
		this.transType = transType;
	}
	public String getIsInOrOut() {
		return isInOrOut;
	}
	public void setIsInOrOut(String isInOrOut) {
		this.isInOrOut = isInOrOut;
	}
	
	
	
}
