package com.deppon.foss.module.transfer.airfreight.api.shared.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description: FOSS同步合大票清单流水信息至OPP--相应FOSS对应实体
 * @date 2016-04-05 下午3:06:04
 * @author 269701
 */
public class AirPickUpSerialRequest implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	// 清单号/正单号
	private String airWayBillNo;
	// 运单号
	private String wayBillNo;
	// 操作状态--新增（INSERT）/修改（UPDATE）/删除（DELETE）
	private String operStatus;
	// 流水信息List
	private List<AirPickUpSerialInfoEntity> airPickUpSerialList = new ArrayList<AirPickUpSerialInfoEntity>();

	/**
	 * @return the airWayBillNo
	 */
	public String getAirWayBillNo() {
		return airWayBillNo;
	}

	/**
	 * @param airWayBillNo
	 *            the airWayBillNo to set
	 */
	public void setAirWayBillNo(String airWayBillNo) {
		this.airWayBillNo = airWayBillNo;
	}

	/**
	 * @return the wayBillNo
	 */
	public String getWayBillNo() {
		return wayBillNo;
	}

	/**
	 * @param wayBillNo
	 *            the wayBillNo to set
	 */
	public void setWayBillNo(String wayBillNo) {
		this.wayBillNo = wayBillNo;
	}

	/**
	 * @return the operStatus
	 */
	public String getOperStatus() {
		return operStatus;
	}

	/**
	 * @param operStatus
	 *            the operStatus to set
	 */
	public void setOperStatus(String operStatus) {
		this.operStatus = operStatus;
	}

	public List<AirPickUpSerialInfoEntity> getAirPickUpSerialList() {
		return airPickUpSerialList;
	}

	public void setAirPickUpSerialList(
			List<AirPickUpSerialInfoEntity> airPickUpSerialList) {
		this.airPickUpSerialList = airPickUpSerialList;
	}


}
