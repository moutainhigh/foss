package com.deppon.foss.shared.request;

import java.io.Serializable;
import java.util.List;

public class WaybillDetailForSOCRequest implements Serializable{

	
	/**
	  * @author WangZengming
	  * @date 2015-9-6
	  * @see:
	  */
	private static final long serialVersionUID = 1671020496261420569L;
	/**
	  * @author WangZengming
	  * @date 2015-9-2
	  * @see:运单号集合
	  */
	private List<String> waybillNoList;

	/**
	 * @return the waybillNoList
	 */
	public List<String> getWaybillNoList() {
		return waybillNoList;
	}

	/**
	 * @param waybillNoList the waybillNoList to set
	 */
	public void setWaybillNoList(List<String> waybillNoList) {
		this.waybillNoList = waybillNoList;
	}
	
	
	
}
