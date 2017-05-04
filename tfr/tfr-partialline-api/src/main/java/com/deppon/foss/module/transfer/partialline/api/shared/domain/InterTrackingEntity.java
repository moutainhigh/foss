package com.deppon.foss.module.transfer.partialline.api.shared.domain;

import com.deppon.foss.framework.entity.BaseEntity;

/**
 * 
 * @author alfred
 * 国际外发轨迹  
 * @author 352203
 */
public class InterTrackingEntity extends BaseEntity {

	
	 /**  
	   * @author 245167
	   * @Fields serialVersionUID 
	   *   
	   * @since Ver 1.0   
	 */
	private static final long serialVersionUID = 8899051764435969707L;

	/**
	 *  1.操作时间
	 */
	private String operateTime;
	
	 
	 /**
	  * 2.城市
	  */
	private String cityName;
	 
	 /**
	  * 3.操作类型
	  */
	private String operateType;
	
	/**
	 * 备注
	 * @return
	 */
	private String note;

	public String getOperateTime() {
		return operateTime;
	}

	public void setOperateTime(String operateTime) {
		this.operateTime = operateTime;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getOperateType() {
		return operateType;
	}

	public void setOperateType(String operateType) {
		this.operateType = operateType;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}
	
}
