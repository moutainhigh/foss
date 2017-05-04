package com.deppon.foss.module.transfer.airfreight.api.shared.domain;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description: FOSS同步航空正单明细信息至OPP--相应FOSS对应实体
 * @date 2016-04-05 下午3:06:04
 * @author 269701
 */
public class AirWaybillDetialRequest implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	// 正单号
	private String airWaybillNo;
	// 操作状态--新增（INSERT）/修改（UPDATE）/删除（DELETE）
	private String operStatus;
	// 流水信息List
	private List<OPPNeedAirWaybillDetailEntity> airWaybillDetialList = new ArrayList<OPPNeedAirWaybillDetailEntity>();
	/**
	 * @return airWaybillNo : return the property airWaybillNo.
	 * @author 269701-foss-lln
	 * @update 2016年5月30日 上午9:15:47
	 * @version V1.0
	 */
	public String getAirWaybillNo() {
		return airWaybillNo;
	}
	/**
	 * @param airWaybillNo : set the property airWaybillNo.
	 * @author 269701-foss-lln
	 * @update 2016年5月30日 上午9:15:47
	 * @version V1.0
	 */
	
	public void setAirWaybillNo(String airWaybillNo) {
		this.airWaybillNo = airWaybillNo;
	}
	
	/**
	 * @return operStatus : return the property operStatus.
	 * @author 269701-foss-lln
	 * @update 2016年5月30日 上午9:15:47
	 * @version V1.0
	 */
	public String getOperStatus() {
		return operStatus;
	}
	/**
	 * @param operStatus : set the property operStatus.
	 * @author 269701-foss-lln
	 * @update 2016年5月30日 上午9:15:47
	 * @version V1.0
	 */
	
	public void setOperStatus(String operStatus) {
		this.operStatus = operStatus;
	}
	/**
	 * @return airWaybillDetialList : return the property airWaybillDetialList.
	 * @author 269701-foss-lln
	 * @update 2016年5月30日 上午9:56:35
	 * @version V1.0
	 */
	public List<OPPNeedAirWaybillDetailEntity> getAirWaybillDetialList() {
		return airWaybillDetialList;
	}
	/**
	 * @param airWaybillDetialList : set the property airWaybillDetialList.
	 * @author 269701-foss-lln
	 * @update 2016年5月30日 上午9:56:35
	 * @version V1.0
	 */
	
	public void setAirWaybillDetialList(
			List<OPPNeedAirWaybillDetailEntity> airWaybillDetialList) {
		this.airWaybillDetialList = airWaybillDetialList;
	}

}
