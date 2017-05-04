package com.deppon.foss.services.training.shared.request;

import java.io.Serializable;

public class QueryWaybillTrackingInfoRequest implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String waybillNo;

    public String getWaybillNo() {
        return waybillNo;
    }

    public void setWaybillNo(String value) {
        this.waybillNo = value;
    }

}
