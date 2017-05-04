package com.deppon.foss.module.transfer.airfreight.api.shared.domain;


public class SerialEntity{	
	/**
	* @fields serialVersionUID
	* @author 269701-foss-lln
	* @update 2016年4月22日 下午3:34:06
	* @version V1.0
	*/
	
	private static final long serialVersionUID = 1L;
	/**
	 * id
	 */
	private String id;
	/**
	 * 合大票清单明细id
	 */
	private String airPickUpDetialId;
	/**
	 * 清单号
	 */
	private String airWaybillNo;
	/**
	 * 流水号
	 */
	private String serialNo;
	/**
	 * 运单号
	 */
	private String waybillNo;
	/**
	 * 备注
	 */
	private String notes;

	
	
	public String getAirWaybillNo() {
		return airWaybillNo;
	}

	public void setAirWaybillNo(String airWaybillNo) {
		this.airWaybillNo = airWaybillNo;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public String getAirPickUpDetialId() {
		return airPickUpDetialId;
	}

	public void setAirPickUpDetialId(String airPickUpDetialId) {
		this.airPickUpDetialId = airPickUpDetialId;
	}

	public String getSerialNo() {
		return serialNo;
	}

	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}

	public String getWaybillNo() {
		return waybillNo;
	}

	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}
	
}
