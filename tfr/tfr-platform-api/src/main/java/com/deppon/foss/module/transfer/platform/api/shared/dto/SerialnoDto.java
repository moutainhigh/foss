package com.deppon.foss.module.transfer.platform.api.shared.dto;

import java.io.Serializable;

/**
 * 运单流水Dto
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:163580,date:2014-4-3 上午10:30:16,content: </p>
 * @author 163580
 * @date 2014-4-3 上午10:30:16
 * @since
 * @version
 */
public class SerialnoDto implements Serializable {
	private static final long serialVersionUID = 6219697067462543512L;
	
	/**
	 * 运单号
	 */
	private String wayBillNo;
	
	/**
	 * 流水号
	 */
	private String serialno;

	public String getWayBillNo() {
		return wayBillNo;
	}

	public void setWayBillNo(String wayBillNo) {
		this.wayBillNo = wayBillNo;
	}

	public String getSerialno() {
		return serialno;
	}

	public void setSerialno(String serialno) {
		this.serialno = serialno;
	}
}