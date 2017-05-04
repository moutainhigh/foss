package com.deppon.foss.module.transfer.airfreight.api.shared.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description: FOSS同步合大票清单明细信息至OPP--相应FOSS对应实体
 * @date 2016-04-05 下午3:06:04
 * @author 269701
 */
public class AirPickUpDetialRequest implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	// 清单号
	private String airPickNo;
	// 操作状态--新增（INSERT）/修改（UPDATE）/删除（DELETE）
	private String operStatus;
	// 明细信息List
	private List<AirPickUpDetialInfoEntity> airPickUpDetialList = new ArrayList<AirPickUpDetialInfoEntity>();

	
	/**
	 * @return airPickNo : return the property airPickNo.
	 * @author 269701-foss-lln
	 * @update 2016年5月29日 下午6:11:27
	 * @version V1.0
	 */
	public String getAirPickNo() {
		return airPickNo;
	}

	/**
	 * @param airPickNo : set the property airPickNo.
	 * @author 269701-foss-lln
	 * @update 2016年5月29日 下午6:11:27
	 * @version V1.0
	 */
	
	public void setAirPickNo(String airPickNo) {
		this.airPickNo = airPickNo;
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

	/**
	 * @return airPickUpDetialList : return the property airPickUpDetialList.
	 * @author 269701-foss-lln
	 * @update 2016年5月29日 下午6:12:25
	 * @version V1.0
	 */
	public List<AirPickUpDetialInfoEntity> getAirPickUpDetialList() {
		return airPickUpDetialList;
	}

	/**
	 * @param airPickUpDetialList : set the property airPickUpDetialList.
	 * @author 269701-foss-lln
	 * @update 2016年5月29日 下午6:12:25
	 * @version V1.0
	 */
	
	public void setAirPickUpDetialList(
			List<AirPickUpDetialInfoEntity> airPickUpDetialList) {
		this.airPickUpDetialList = airPickUpDetialList;
	}

}
