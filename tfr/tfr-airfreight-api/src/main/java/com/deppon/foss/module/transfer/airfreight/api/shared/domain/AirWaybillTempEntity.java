
package com.deppon.foss.module.transfer.airfreight.api.shared.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * 
* @description FOSS推送合大票信息至OPP系统-临时表
* @version 1.0
* @author 269701-foss-lln
* @update 2016年5月11日 上午11:14:06
 */
public class AirWaybillTempEntity implements Serializable {
	
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -2501190323492848731L;
	/**
	 * ID
	 */
	private String ID;
	
	/**
	 * 合大票清单号
	 */
	private String airPickNo;
	/**
	 * 合大票清单主表ID
	 */
	private String airPickUpId;
	
	/**
	 * 正单号
	 */
	private String airWaybillNo;
	/**
	 * 航空正单id
	 */
	private String airWaybillId;
	/**
	 * 运单号
	 */
	private String waybillNo;
	
	/**
	 * 是否推送完成 Y--推送完成/N--推送未完成
	 */
	private String pushStatus;
	/**
	 * 操作状态：INSERT/UPDATE/DELETE
	 */
	private String operateStatus;
	
	/**
	 * 单据类型：清单：20 正单 10
	 */
	private String billType;
	/**
	 * 创建时间          
	 */
	private Date createTime;
	/**
	 * 修改时间     
	 */
	private Date modifyTime;
	
	
	public String getBillType() {
		return billType;
	}
	public void setBillType(String billType) {
		this.billType = billType;
	}
	public String getAirPickNo() {
		return airPickNo;
	}
	public void setAirPickNo(String airPickNo) {
		this.airPickNo = airPickNo;
	}
	public String getAirWaybillId() {
		return airWaybillId;
	}
	public void setAirWaybillId(String airWaybillId) {
		this.airWaybillId = airWaybillId;
	}
	/**
	 * @return iD : return the property iD.
	 * @author 269701-foss-lln
	 * @update 2016年5月11日 上午11:31:59
	 * @version V1.0
	 */
	public String getID() {
		return ID;
	}
	/**
	 * @param iD : set the property iD.
	 * @author 269701-foss-lln
	 * @update 2016年5月11日 上午11:31:59
	 * @version V1.0
	 */
	
	public void setID(String iD) {
		ID = iD;
	}
	/**
	 * @return airWaybillNo : return the property airWaybillNo.
	 * @author 269701-foss-lln
	 * @update 2016年5月11日 上午11:31:59
	 * @version V1.0
	 */
	public String getAirWaybillNo() {
		return airWaybillNo;
	}
	/**
	 * @param airWaybillNo : set the property airWaybillNo.
	 * @author 269701-foss-lln
	 * @update 2016年5月11日 上午11:31:59
	 * @version V1.0
	 */
	
	public void setAirWaybillNo(String airWaybillNo) {
		this.airWaybillNo = airWaybillNo;
	}
	/**
	 * @return airPickUpId : return the property airPickUpId.
	 * @author 269701-foss-lln
	 * @update 2016年5月11日 上午11:31:59
	 * @version V1.0
	 */
	public String getAirPickUpId() {
		return airPickUpId;
	}
	/**
	 * @param airPickUpId : set the property airPickUpId.
	 * @author 269701-foss-lln
	 * @update 2016年5月11日 上午11:31:59
	 * @version V1.0
	 */
	
	public void setAirPickUpId(String airPickUpId) {
		this.airPickUpId = airPickUpId;
	}
	/**
	 * @return pushStatus : return the property pushStatus.
	 * @author 269701-foss-lln
	 * @update 2016年5月11日 上午11:31:59
	 * @version V1.0
	 */
	public String getPushStatus() {
		return pushStatus;
	}
	/**
	 * @param pushStatus : set the property pushStatus.
	 * @author 269701-foss-lln
	 * @update 2016年5月11日 上午11:31:59
	 * @version V1.0
	 */
	
	public void setPushStatus(String pushStatus) {
		this.pushStatus = pushStatus;
	}
	
	/**
	 * @return operateStatus : return the property operateStatus.
	 * @author 269701-foss-lln
	 * @update 2016年5月11日 下午2:24:34
	 * @version V1.0
	 */
	public String getOperateStatus() {
		return operateStatus;
	}
	/**
	 * @param operateStatus : set the property operateStatus.
	 * @author 269701-foss-lln
	 * @update 2016年5月11日 下午2:24:34
	 * @version V1.0
	 */
	
	public void setOperateStatus(String operateStatus) {
		this.operateStatus = operateStatus;
	}
	/**
	 * @return createTime : return the property createTime.
	 * @author 269701-foss-lln
	 * @update 2016年5月11日 上午11:31:59
	 * @version V1.0
	 */
	public Date getCreateTime() {
		return createTime;
	}
	/**
	 * @param createTime : set the property createTime.
	 * @author 269701-foss-lln
	 * @update 2016年5月11日 上午11:31:59
	 * @version V1.0
	 */
	
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	/**
	 * @return modifyTime : return the property modifyTime.
	 * @author 269701-foss-lln
	 * @update 2016年5月11日 上午11:44:14
	 * @version V1.0
	 */
	public Date getModifyTime() {
		return modifyTime;
	}
	/**
	 * @param modifyTime : set the property modifyTime.
	 * @author 269701-foss-lln
	 * @update 2016年5月11日 上午11:44:14
	 * @version V1.0
	 */
	
	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}
	/**
	 * @return waybillNo : return the property waybillNo.
	 * @author 269701-foss-lln
	 * @update 2016年5月21日 上午11:24:37
	 * @version V1.0
	 */
	public String getWaybillNo() {
		return waybillNo;
	}
	/**
	 * @param waybillNo : set the property waybillNo.
	 * @author 269701-foss-lln
	 * @update 2016年5月21日 上午11:24:37
	 * @version V1.0
	 */
	
	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}
	
}