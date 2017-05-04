package com.deppon.foss.module.transfer.airfreight.api.shared.domain;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description: FOSS同步航空正单流水信息至OPP--相应FOSS对应实体
 * @date 2016-04-05 下午3:06:04
 * @author 269701
 */
public class AirWaybillSerialRequest implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	// 正单号
	private String airWayBillNo;
	// 运单号
	private String wayBillNo;
	// 操作状态--新增（INSERT）/修改（UPDATE）/删除（DELETE）
	private String operStatus;
	// 流水信息List
	private List<OppNeedAirWaybillSerialNoEntity> airWaybillSerialList = new ArrayList<OppNeedAirWaybillSerialNoEntity>();
	
	/**
	 * @return airWayBillNo : return the property airWayBillNo.
	 * @author 269701-foss-lln
	 * @update 2016年5月18日 下午4:22:11
	 * @version V1.0
	 */
	public String getAirWayBillNo() {
		return airWayBillNo;
	}
	/**
	 * @param airWayBillNo : set the property airWayBillNo.
	 * @author 269701-foss-lln
	 * @update 2016年5月18日 下午4:22:11
	 * @version V1.0
	 */
	
	public void setAirWayBillNo(String airWayBillNo) {
		this.airWayBillNo = airWayBillNo;
	}
	/**
	 * @return wayBillNo : return the property wayBillNo.
	 * @author 269701-foss-lln
	 * @update 2016年5月18日 下午4:22:11
	 * @version V1.0
	 */
	public String getWayBillNo() {
		return wayBillNo;
	}
	/**
	 * @param wayBillNo : set the property wayBillNo.
	 * @author 269701-foss-lln
	 * @update 2016年5月18日 下午4:22:11
	 * @version V1.0
	 */
	
	public void setWayBillNo(String wayBillNo) {
		this.wayBillNo = wayBillNo;
	}
	/**
	 * @return operStatus : return the property operStatus.
	 * @author 269701-foss-lln
	 * @update 2016年5月18日 下午4:22:11
	 * @version V1.0
	 */
	public String getOperStatus() {
		return operStatus;
	}
	/**
	 * @param operStatus : set the property operStatus.
	 * @author 269701-foss-lln
	 * @update 2016年5月18日 下午4:22:11
	 * @version V1.0
	 */
	
	public void setOperStatus(String operStatus) {
		this.operStatus = operStatus;
	}
	/**
	 * @return airWaybillSerialList : return the property airWaybillSerialList.
	 * @author 269701-foss-lln
	 * @update 2016年5月18日 下午4:22:11
	 * @version V1.0
	 */
	public List<OppNeedAirWaybillSerialNoEntity> getAirWaybillSerialList() {
		return airWaybillSerialList;
	}
	/**
	 * @param airWaybillSerialList : set the property airWaybillSerialList.
	 * @author 269701-foss-lln
	 * @update 2016年5月18日 下午4:22:11
	 * @version V1.0
	 */
	
	public void setAirWaybillSerialList(
			List<OppNeedAirWaybillSerialNoEntity> airWaybillSerialList) {
		this.airWaybillSerialList = airWaybillSerialList;
	}

	
}
