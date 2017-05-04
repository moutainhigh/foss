package com.deppon.foss.dubbo.crm.api.define;

import java.io.Serializable;
import java.util.List;

/**
 * @author 335284
 *
 */
public class WaybillDetailForSOCEntity implements Serializable {

	/**
	 * @author WangZengming
	 * @date 2015-9-2
	 * @see:
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * @author WangZengming
	 * @date 2015-9-2
	 * @see:运单详情集合
	 */
	private List<WaybillDetailEntity> waybillDetailForOfficialList;

	/**
	 * @return the waybillDetailForOfficialList
	 */
	public List<WaybillDetailEntity> getWaybillDetailForOfficialList() {
		return waybillDetailForOfficialList;
	}

	/**
	 * @param waybillDetailForOfficialList
	 *            the waybillDetailForOfficialList to set
	 */
	public void setWaybillDetailForOfficialList(List<WaybillDetailEntity> waybillDetailForOfficialList) {
		this.waybillDetailForOfficialList = waybillDetailForOfficialList;
	}

}
