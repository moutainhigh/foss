package com.deppon.foss.module.base.baseinfo.api.shared.domain;

import com.deppon.foss.framework.entity.BaseEntity;
/**
 * 同步快递派送地址库信息到GIS的返回实体
 * @author 198771
 *
 */
public class SyncEDAResponseEntity  extends BaseEntity   {
	
	private static final long serialVersionUID = 131677974956L;

	/**
	 * 是否成功
	 */
	private String is_success;
	/**
	 * 异常信息
	 */
	private String exptionMSG;
	public String getIs_success() {
		return is_success;
	}
	public void setIs_success(String is_success) {
		this.is_success = is_success;
	}
	public String getExptionMSG() {
		return exptionMSG;
	}
	public void setExptionMSG(String exptionMSG) {
		this.exptionMSG = exptionMSG;
	}

}
