package com.deppon.foss.services.training.shared.response;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.deppon.foss.services.training.shared.vo.WaybillTrackingInfo;

public class QueryWaybillTrackingInfoResponse implements Serializable{
	private static final long serialVersionUID = 1L;
	private List<WaybillTrackingInfo> waybillTrackingInfo;

	  public List<WaybillTrackingInfo> getWaybillTrackingInfo() {
	        if (waybillTrackingInfo == null) {
	            waybillTrackingInfo = new ArrayList<WaybillTrackingInfo>();
	        }
	        return this.waybillTrackingInfo;
	    }
}
