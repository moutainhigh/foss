package com.deppon.foss.shared.response;

import java.io.Serializable;
import java.util.List;

import com.deppon.foss.shared.vo.WaybillDetail;

/**
 * @author Administrator
 *
 */
public class WaybillDetailForSOCResponse implements Serializable{
	
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
	private List<WaybillDetail> waybillDetailForOfficialList;
	
	/**
	 * @return the waybillDetailForOfficialList
	 */
	public List<WaybillDetail> getWaybillDetailForOfficialList() {
		return waybillDetailForOfficialList;
	}
	/**
	 * @param waybillDetailForOfficialList the waybillDetailForOfficialList to set
	 */
	public void setWaybillDetailForOfficialList(
			List<WaybillDetail> waybillDetailForOfficialList) {
		this.waybillDetailForOfficialList = waybillDetailForOfficialList;
	}

}
