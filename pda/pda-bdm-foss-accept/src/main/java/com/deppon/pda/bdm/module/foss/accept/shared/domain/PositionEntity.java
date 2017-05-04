package com.deppon.pda.bdm.module.foss.accept.shared.domain;

import java.io.Serializable;
import java.util.Date;

import com.deppon.pda.bdm.module.core.shared.domain.ScanMsgEntity;

/**
 * pdam项目经纬度数据实体
 * @author 245960
 *
 */
public class PositionEntity extends ScanMsgEntity implements Serializable{
	private static final long serialVersionUID = -1591183705228810088L;
	//员工号	
	private	String userCode;
	//经度
	private String longitude;
	//纬度
	private String latitude;
	//位置传入时间
	private Date positionTime;
	//uuid
	private String uuid;
	
	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getLongitude() {
		return longitude;
	}
	
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	
	public String getLatitude() {
		return latitude;
	}
	
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	
	public String getUserCode() {
		return userCode;
	}
	
	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	public Date getPositionTime() {
		return positionTime;
	}

	public void setPositionTime(Date positionTime) {
		this.positionTime = positionTime;
	}
	
}
