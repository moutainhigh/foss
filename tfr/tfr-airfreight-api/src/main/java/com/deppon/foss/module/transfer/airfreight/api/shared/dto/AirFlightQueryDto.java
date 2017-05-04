package com.deppon.foss.module.transfer.airfreight.api.shared.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * 查询空运航班实体
 * @author 105869
 * @date 2014年10月18日 08:58:19
 * */
public class AirFlightQueryDto implements Serializable {

	private static final long serialVersionUID = -2882546470242246341L;
	//出发部门
	private String departOrgCode;
	//到达部门
	private String destOrgCode;
	//正单号
	private String beingSingleNo;
	//起飞时间
	private Date beginFligtTime;
	//到达时间
	private Date endFligtTime;
	
	
	public String getDepartOrgCode() {
		return departOrgCode;
	}
	public void setDepartOrgCode(String departOrgCode) {
		this.departOrgCode = departOrgCode;
	}
	public String getDestOrgCode() {
		return destOrgCode;
	}
	public void setDestOrgCode(String destOrgCode) {
		this.destOrgCode = destOrgCode;
	}
	public String getBeingSingleNo() {
		return beingSingleNo;
	}
	public void setBeingSingleNo(String beingSingleNo) {
		this.beingSingleNo = beingSingleNo;
	}
	public Date getBeginFligtTime() {
		return beginFligtTime;
	}
	public void setBeginFligtTime(Date beginFligtTime) {
		this.beginFligtTime = beginFligtTime;
	}
	public Date getEndFligtTime() {
		return endFligtTime;
	}
	public void setEndFligtTime(Date endFligtTime) {
		this.endFligtTime = endFligtTime;
	}
	
	
	
}
