package com.deppon.foss.module.transfer.partialline.api.shared.domain;

import java.util.Date;

import com.deppon.foss.framework.entity.BaseEntity;
/**
 * 
 * @author alfred
 * 国际外发轨迹  
 * @author 352203
 */
public class InternationalTrackingEntity extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 运单号
	 */
	private String waybillNo;
	
	 /**
	  * 操作类型
	  * 
	  */
	 private String operateType;
	
	 /**
	  * 操作时间
	  * 
	  */
	 private Date operateTime;
	 
	 /**
	  * 备注
	  */
	 private String note;
	 
	 /**
	  * 创建时间
	  * @return
	  */
	 private Date createTime;
	 
	 /**
	  * 城市
	  * @return
	  */
	 private String cityName;
	 
	 /**
	  * 代理单号
	  * @return
	  */
	 private String agencyNo;
	 
	 /**
	  * 代理公司编码
	  * @return
	  */
	 private String agencyCompCode;
	 
	 /**
	  * 代理公司名称
	  * @return
	  */
	 private String agencyCompName;
	 
	 /**
	  * 目的国
	  * @return
	  */
	 private String destCountry;
	 
	 /**
	  * 渠道
	  * 1-韩国件  2-国际件
	  * 韩国件是很早就写好的，国际件是后面规范但是操作类型不同
	  * 所以只能以渠道分开 
	  * @return
	  */
	 private String channel;
	 
	public String getWaybillNo() {
		return waybillNo;
	}

	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}

	public String getOperateType() {
		return operateType;
	}

	public void setOperateType(String operateType) {
		this.operateType = operateType;
	}

	public Date getOperateTime() {
		return operateTime;
	}

	public void setOperateTime(Date operateTime) {
		this.operateTime = operateTime;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getAgencyNo() {
		return agencyNo;
	}

	public void setAgencyNo(String agencyNo) {
		this.agencyNo = agencyNo;
	}

	public String getAgencyCompCode() {
		return agencyCompCode;
	}

	public void setAgencyCompCode(String agencyCompCode) {
		this.agencyCompCode = agencyCompCode;
	}

	public String getAgencyCompName() {
		return agencyCompName;
	}

	public void setAgencyCompName(String agencyCompName) {
		this.agencyCompName = agencyCompName;
	}

	public String getDestCountry() {
		return destCountry;
	}

	public void setDestCountry(String destCountry) {
		this.destCountry = destCountry;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}
}