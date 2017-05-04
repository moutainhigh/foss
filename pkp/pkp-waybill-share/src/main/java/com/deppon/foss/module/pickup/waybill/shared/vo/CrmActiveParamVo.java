package com.deppon.foss.module.pickup.waybill.shared.vo;

import java.io.Serializable;
import java.util.Date;

/**
 * 获取营销活动传参信息
 * @创建时间 2014-4-16 下午7:16:56   
 * @创建人： WangQianJin
 */
public class CrmActiveParamVo implements Serializable {

	//当前时间
    private Date currentDate;
    //活动类别
  	private String activityCategory;
  	//状态是否有效
  	private String active;  	
  	//当前登陆部门code
  	private String currentOrgCode;
  	
  	
  	
	public String getCurrentOrgCode() {
		return currentOrgCode;
	}

	public void setCurrentOrgCode(String currentOrgCode) {
		this.currentOrgCode = currentOrgCode;
	}

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}

	public String getActivityCategory() {
		return activityCategory;
	}

	public void setActivityCategory(String activityCategory) {
		this.activityCategory = activityCategory;
	}

	public Date getCurrentDate() {
		return currentDate;
	}

	public void setCurrentDate(Date currentDate) {
		this.currentDate = currentDate;
	}
}
