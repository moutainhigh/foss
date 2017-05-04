package com.deppon.foss.module.transfer.platform.api.shared.domain;

import com.deppon.foss.framework.entity.BaseEntity;


/**
* @description 外场对应的负责人实体
* @version 1.0
* @author 14022-foss-songjie
* @update 2014年4月12日 上午9:33:40
*/
public class PersonForTransferEntity extends BaseEntity {

	
	/**
	* @fields serialVersionUID
	* @author 14022-foss-songjie
	* @update 2014年4月12日 上午9:33:56
	* @version V1.0
	*/
	
	private static final long serialVersionUID = 1L;
	
	
	/**
	 * 手机号
	* @fields mobilePhone
	* @author 14022-foss-songjie
	* @update 2014年4月12日 上午9:34:51
	* @version V1.0
	*/
	private String mobilePhone;
	
	/**
	 * 外场code
	* @fields orgCode
	* @author 14022-foss-songjie
	* @update 2014年4月12日 上午9:34:54
	* @version V1.0
	*/
	private String orgCode;
	
	/**
	 * 外场name
	* @fields orgName
	* @author 14022-foss-songjie
	* @update 2014年4月12日 上午9:34:56
	* @version V1.0
	*/
	private String orgName;

	
	/**
	* @description 手机号
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年4月12日 上午9:35:26
	*/
	public String getMobilePhone() {
		return mobilePhone;
	}

	
	/**
	* @description 手机号
	* @param mobilePhone
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年4月12日 上午9:35:31
	*/
	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	
	/**
	* @description 外场code
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年4月12日 上午9:35:51
	*/
	public String getOrgCode() {
		return orgCode;
	}

	
	/**
	 * 
	* @description 外场code
	* @param orgCode
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年4月12日 上午9:35:58
	*/
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	
	/**
	* @description 外场name
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年4月12日 上午9:36:06
	*/
	public String getOrgName() {
		return orgName;
	}

	
	/**
	* @description 外场name
	* @param orgName
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年4月12日 上午9:36:22
	*/
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	
	

}
