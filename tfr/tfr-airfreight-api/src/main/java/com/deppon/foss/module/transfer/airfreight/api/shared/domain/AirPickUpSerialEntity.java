package com.deppon.foss.module.transfer.airfreight.api.shared.domain;

import java.io.Serializable;
import java.util.Date;


/**
* @description FOSS推送合大票流水信息至OPP系统--FOSS推送实体
* @version 1.0
* @author 269701-foss-lln
* @update 2016年4月27日 上午11:35:22
 */
public class AirPickUpSerialEntity implements Serializable{

		
	/**
	* @fields serialVersionUID
	* @author 269701-foss-lln
	* @update 2016年4月27日 上午11:34:04
	* @version V1.0
	*/
	
	private static final long serialVersionUID = 1L;
	
	//清单明细id
	private String detialId;
	//流水ID
	private String serialID;
	// 运单号
	private String wayBillNo;
	// 流水号
	private String serialNo;
	//创建时间
	private Date createTime;
	//备注
	private String notes;
	/**
	 * @return detialId : return the property detialId.
	 * @author 269701-foss-lln
	 * @update 2016年5月20日 上午11:18:22
	 * @version V1.0
	 */
	public String getDetialId() {
		return detialId;
	}
	/**
	 * @param detialId : set the property detialId.
	 * @author 269701-foss-lln
	 * @update 2016年5月20日 上午11:18:22
	 * @version V1.0
	 */
	
	public void setDetialId(String detialId) {
		this.detialId = detialId;
	}
	/**
	 * @return serialID : return the property serialID.
	 * @author 269701-foss-lln
	 * @update 2016年5月20日 上午11:18:22
	 * @version V1.0
	 */
	public String getSerialID() {
		return serialID;
	}
	/**
	 * @param serialID : set the property serialID.
	 * @author 269701-foss-lln
	 * @update 2016年5月20日 上午11:18:22
	 * @version V1.0
	 */
	
	public void setSerialID(String serialID) {
		this.serialID = serialID;
	}
	/**
	 * @return wayBillNo : return the property wayBillNo.
	 * @author 269701-foss-lln
	 * @update 2016年5月20日 上午11:18:22
	 * @version V1.0
	 */
	public String getWayBillNo() {
		return wayBillNo;
	}
	/**
	 * @param wayBillNo : set the property wayBillNo.
	 * @author 269701-foss-lln
	 * @update 2016年5月20日 上午11:18:22
	 * @version V1.0
	 */
	
	public void setWayBillNo(String wayBillNo) {
		this.wayBillNo = wayBillNo;
	}
	/**
	 * @return serialNo : return the property serialNo.
	 * @author 269701-foss-lln
	 * @update 2016年5月20日 上午11:18:22
	 * @version V1.0
	 */
	public String getSerialNo() {
		return serialNo;
	}
	/**
	 * @param serialNo : set the property serialNo.
	 * @author 269701-foss-lln
	 * @update 2016年5月20日 上午11:18:22
	 * @version V1.0
	 */
	
	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}
	
	/**
	 * @return createTime : return the property createTime.
	 * @author 269701-foss-lln
	 * @update 2016年5月25日 下午8:37:54
	 * @version V1.0
	 */
	public Date getCreateTime() {
		return createTime;
	}
	/**
	 * @param createTime : set the property createTime.
	 * @author 269701-foss-lln
	 * @update 2016年5月25日 下午8:37:54
	 * @version V1.0
	 */
	
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	/**
	 * @return notes : return the property notes.
	 * @author 269701-foss-lln
	 * @update 2016年5月20日 上午11:18:22
	 * @version V1.0
	 */
	public String getNotes() {
		return notes;
	}
	/**
	 * @param notes : set the property notes.
	 * @author 269701-foss-lln
	 * @update 2016年5月20日 上午11:18:22
	 * @version V1.0
	 */
	
	public void setNotes(String notes) {
		this.notes = notes;
	}
	
	
}
