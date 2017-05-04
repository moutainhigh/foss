package com.deppon.foss.module.base.baseinfo.api.shared.domain;

import com.deppon.foss.framework.entity.BaseEntity;
/**
 * 
 * 同步延时扣款审核返回参数
 * @author 308861 
 * @date 2016-11-1 上午10:26:37
 * @since
 * @version
 */
public class SyncDelayDeductRecordCheckResponseEntity  extends BaseEntity   {
	
	private static final long serialVersionUID = 131677974956L;

	/**
	 * 是否成功 0：成功；1：失败
	 */
	private String isSuccess;
	/**
	 * 异常信息
	 */
	private String exptionMSG;
	
	public String getIsSuccess() {
		return isSuccess;
	}
	public void setIsSuccess(String isSuccess) {
		this.isSuccess = isSuccess;
	}
	public String getExptionMSG() {
		return exptionMSG;
	}
	public void setExptionMSG(String exptionMSG) {
		this.exptionMSG = exptionMSG;
	}
}
