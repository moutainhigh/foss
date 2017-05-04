package com.deppon.foss.services.training.shared.request;

import java.io.Serializable;

public class QueryWaybillTrajectoryRequest implements Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String waybillNo;

    /**
     * Gets the value of the waybillNo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getWaybillNo() {
        return waybillNo;
    }

    /**
     * Sets the value of the waybillNo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setWaybillNo(String value) {
        this.waybillNo = value;
    }

}
