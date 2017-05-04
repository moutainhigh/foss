package com.deppon.foss.module.transfer.edi.server.domain;

import java.util.ArrayList;
import java.util.List;


/**
 * 
* @description 根据OPP传来的运单号查询FOSS流水号
* @version 1.0
* @author 269701-foss-lln
* @update 2016年5月12日 上午11:42:51
 */
public class OPPLocusResponse {

	//运单号
	private List<String> waybillNo=new ArrayList<String>();
	//清单号
	private List<String> airWaybillNo=new ArrayList<String>();
	//返回是否成功的标志  true ,false
	private boolean beSuccess ;
	//失败原因 
	private String failureReason;
	//返回类型，接口类型 
	private String returnType;
	
	/**
	 * @return waybillNo : return the property waybillNo.
	 * @author 269701-foss-lln
	 * @update 2016年6月2日 上午8:39:27
	 * @version V1.0
	 */
	public List<String> getWaybillNo() {
		return waybillNo;
	}
	/**
	 * @param waybillNo : set the property waybillNo.
	 * @author 269701-foss-lln
	 * @update 2016年6月2日 上午8:39:27
	 * @version V1.0
	 */
	
	public void setWaybillNo(List<String> waybillNo) {
		this.waybillNo = waybillNo;
	}
	/**
	 * @return airWaybillNo : return the property airWaybillNo.
	 * @author 269701-foss-lln
	 * @update 2016年6月2日 上午8:39:27
	 * @version V1.0
	 */
	public List<String> getAirWaybillNo() {
		return airWaybillNo;
	}
	/**
	 * @param airWaybillNo : set the property airWaybillNo.
	 * @author 269701-foss-lln
	 * @update 2016年6月2日 上午8:39:27
	 * @version V1.0
	 */
	
	public void setAirWaybillNo(List<String> airWaybillNo) {
		this.airWaybillNo = airWaybillNo;
	}
	public boolean isBeSuccess() {
		return beSuccess;
	}
	public void setBeSuccess(boolean beSuccess) {
		this.beSuccess = beSuccess;
	}
	public String getFailureReason() {
		return failureReason;
	}
	public void setFailureReason(String failureReason) {
		this.failureReason = failureReason;
	}
	public String getReturnType() {
		return returnType;
	}
	public void setReturnType(String returnType) {
		this.returnType = returnType;
	}
}
