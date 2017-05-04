package com.deppon.esb.pojo.domain.dmp2foss;

import java.io.Serializable;
import java.util.Date;

/**
 * 零担大件家装轨迹回复DMP信息实体
 *@author 205109-foss-zenghaibin
 *@date 2015-04-29 上午 9:49：30 
 ***/
public class DMPSynTrackingToFOSSResponseEntity implements Serializable{
	private static final long serialVersionUID = 3031910894252017200L;

	/**运单号**/
	private String wayBillNo;
	
	//回复结果,Y/N
	private String respResult;
	
	//操作时间
	private Date operateTime;
	//当前状态
	private String currentStatus;
	
	
	public String getCurrentStatus() {
		return currentStatus;
	}
	public void setCurrentStatus(String currentStatus) {
		this.currentStatus = currentStatus;
	}
	public String getWayBillNo() {
		return wayBillNo;
	}
	public void setWayBillNo(String wayBillNo) {
		this.wayBillNo = wayBillNo;
	}
	public String getRespResult() {
		return respResult;
	}
	public void setRespResult(String respResult) {
		this.respResult = respResult;
	}
	public Date getOperateTime() {
		return operateTime;
	}
	public void setOperateTime(Date operateTime) {
		this.operateTime = operateTime;
	}

	
}
