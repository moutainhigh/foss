package com.deppon.foss.module.transfer.edi.server.domain;

import java.math.BigDecimal;
import java.util.List;

/**
 * 
* @description 更改单传数据至FOSS
* @version 1.0
* @author 269701-foss-lln
* @update 2016年5月12日 下午5:35:26
 */
public class OppUpdateAirPickUpRequest {

	//运单号
	private String waybillNo;
	//流水号
	private List<String> serialList;
	//代收货款
	private BigDecimal collectionFee=new BigDecimal(0);
	//操作类型--多货 20/少货30
	private String operType;
	/**
	 * @return waybillNo : return the property waybillNo.
	 * @author 269701-foss-lln
	 * @update 2016年5月12日 下午5:42:52
	 * @version V1.0
	 */
	public String getWaybillNo() {
		return waybillNo;
	}
	/**
	 * @param waybillNo : set the property waybillNo.
	 * @author 269701-foss-lln
	 * @update 2016年5月12日 下午5:42:52
	 * @version V1.0
	 */
	
	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}
	/**
	 * @return serialList : return the property serialList.
	 * @author 269701-foss-lln
	 * @update 2016年5月12日 下午5:42:52
	 * @version V1.0
	 */
	public List<String> getSerialList() {
		return serialList;
	}
	/**
	 * @param serialList : set the property serialList.
	 * @author 269701-foss-lln
	 * @update 2016年5月12日 下午5:42:52
	 * @version V1.0
	 */
	
	public void setSerialList(List<String> serialList) {
		this.serialList = serialList;
	}
	/**
	 * @return collectionFee : return the property collectionFee.
	 * @author 269701-foss-lln
	 * @update 2016年5月12日 下午5:42:52
	 * @version V1.0
	 */
	public BigDecimal getCollectionFee() {
		return collectionFee;
	}
	/**
	 * @param collectionFee : set the property collectionFee.
	 * @author 269701-foss-lln
	 * @update 2016年5月12日 下午5:42:52
	 * @version V1.0
	 */
	
	public void setCollectionFee(BigDecimal collectionFee) {
		this.collectionFee = collectionFee;
	}
	/**
	 * @return operType : return the property operType.
	 * @author 269701-foss-lln
	 * @update 2016年5月12日 下午5:42:52
	 * @version V1.0
	 */
	public String getOperType() {
		return operType;
	}
	/**
	 * @param operType : set the property operType.
	 * @author 269701-foss-lln
	 * @update 2016年5月12日 下午5:42:52
	 * @version V1.0
	 */
	
	public void setOperType(String operType) {
		this.operType = operType;
	}
	
	
}
