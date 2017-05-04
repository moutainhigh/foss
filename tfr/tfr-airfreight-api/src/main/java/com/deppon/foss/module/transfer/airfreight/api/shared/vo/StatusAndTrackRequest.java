
package com.deppon.foss.module.transfer.airfreight.api.shared.vo;

/**
 * 状态信息和货物轨迹
* @author 200942
 * @date 2016-5-10 下午8:37:39
 */
public class StatusAndTrackRequest implements java.io.Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4319108731101460017L;
	
	//运单号
	private String waybillNo;

	/**
	 * @return the waybillNo
	 */
	public String getWaybillNo() {
		return waybillNo;
	}

	/**
	 * @param waybillNo the waybillNo to set
	 */
	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}
	
	

}