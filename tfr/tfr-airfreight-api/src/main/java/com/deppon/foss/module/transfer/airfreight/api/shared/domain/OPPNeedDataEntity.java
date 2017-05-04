package com.deppon.foss.module.transfer.airfreight.api.shared.domain;

import java.io.Serializable;
import java.util.List;

/**
 * 
* @description FOSS推送OPP需要的合大票清单信息以及正单信息
* @version 1.0
* @author 269701-foss-lln
* @update 2016年5月18日 下午3:08:00
 */
public class OPPNeedDataEntity implements Serializable{

	
	/**
	* @fields serialVersionUID
	* @author 269701-foss-lln
	* @update 2016年5月18日 下午3:13:28 
	* @version V1.0
	*/
	
	private static final long serialVersionUID = 1L;

	/**
	 * 操作类型 正单：20 清单：10
	 */
	private String operType;
	
	/**
	 * 清单主表信息
	 */
	private AirPickUpInfoEntity airPickUpInfo;
	
	/**
	 * 清单明细信息
	 */
	private List<AirPickUpDetialInfoEntity>  airPickUpDetialInfoList;
	
	/**
	 * 清单流水信息
	 */
	private List<AirPickUpSerialInfoEntity> airPickUpSerialInfoList;
	
	/**
	 * 正单主表信息
	 */
	private OPPNeedAirWaybillEntity oppAirWaybillNoInfo;
	
	/**
	 * 正单明细信息
	 */
	private List<OPPNeedAirWaybillDetailEntity>  OppAirWaybillDetialList;
	/**
	 * 正单流水信息
	 */
	private List<OppNeedAirWaybillSerialNoEntity> oppAirWaybillSerialList;
	
	
	public String getOperType() {
		return operType;
	}
	public void setOperType(String operType) {
		this.operType = operType;
	}
	public AirPickUpInfoEntity getAirPickUpInfo() {
		return airPickUpInfo;
	}
	public void setAirPickUpInfo(AirPickUpInfoEntity airPickUpInfo) {
		this.airPickUpInfo = airPickUpInfo;
	}
	public List<AirPickUpDetialInfoEntity> getAirPickUpDetialInfoList() {
		return airPickUpDetialInfoList;
	}
	public void setAirPickUpDetialInfoList(
			List<AirPickUpDetialInfoEntity> airPickUpDetialInfoList) {
		this.airPickUpDetialInfoList = airPickUpDetialInfoList;
	}
	public List<AirPickUpSerialInfoEntity> getAirPickUpSerialInfoList() {
		return airPickUpSerialInfoList;
	}
	public void setAirPickUpSerialInfoList(
			List<AirPickUpSerialInfoEntity> airPickUpSerialInfoList) {
		this.airPickUpSerialInfoList = airPickUpSerialInfoList;
	}
	public OPPNeedAirWaybillEntity getOppAirWaybillNoInfo() {
		return oppAirWaybillNoInfo;
	}
	public void setOppAirWaybillNoInfo(OPPNeedAirWaybillEntity oppAirWaybillNoInfo) {
		this.oppAirWaybillNoInfo = oppAirWaybillNoInfo;
	}
	public List<OPPNeedAirWaybillDetailEntity> getOppAirWaybillDetialList() {
		return OppAirWaybillDetialList;
	}
	public void setOppAirWaybillDetialList(
			List<OPPNeedAirWaybillDetailEntity> oppAirWaybillDetialList) {
		OppAirWaybillDetialList = oppAirWaybillDetialList;
	}
	public List<OppNeedAirWaybillSerialNoEntity> getOppAirWaybillSerialList() {
		return oppAirWaybillSerialList;
	}
	public void setOppAirWaybillSerialList(
			List<OppNeedAirWaybillSerialNoEntity> oppAirWaybillSerialList) {
		this.oppAirWaybillSerialList = oppAirWaybillSerialList;
	}
	
	
}
