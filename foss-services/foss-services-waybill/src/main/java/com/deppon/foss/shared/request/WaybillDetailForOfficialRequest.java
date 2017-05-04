package com.deppon.foss.shared.request;

import java.io.Serializable;
import java.util.List;

public class WaybillDetailForOfficialRequest implements Serializable{
	
	
	/**
	  * @author WangZengming
	  * @date 2015-9-6
	  * @see:
	  */
	private static final long serialVersionUID = -5173165534971497218L;
	/**
	  * @author WangZengming
	  * @date 2015-9-2
	  * @see:运单号集合
	  */
	private List<String> waybillNoList;

	
	public List<String> getWaybillNoList() {
		return waybillNoList;
	}

	public void setWaybillNoList(List<String> waybillNoList) {
		this.waybillNoList = waybillNoList;
	}
	
	
}
