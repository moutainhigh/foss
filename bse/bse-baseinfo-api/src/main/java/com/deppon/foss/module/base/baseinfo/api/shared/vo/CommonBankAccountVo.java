package com.deppon.foss.module.base.baseinfo.api.shared.vo;

import java.io.Serializable;
import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.CommonBankAccountEntity;

public class CommonBankAccountVo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 传递到前台的组织对公账户集合
	 */
	private List<CommonBankAccountEntity> commonBankAccountEntityList;
	private CommonBankAccountEntity CommonBankAccountEntity;
	
	public CommonBankAccountEntity getCommonBankAccountEntity() {
		return CommonBankAccountEntity;
	}
	public void setCommonBankAccountEntity(
			CommonBankAccountEntity commonBankAccountEntity) {
		CommonBankAccountEntity = commonBankAccountEntity;
	}
	public List<CommonBankAccountEntity> getCommonBankAccountEntityList() {
		return commonBankAccountEntityList;
	}
	public void setCommonBankAccountEntityList(
			List<CommonBankAccountEntity> commonBankAccountEntityList) {
		this.commonBankAccountEntityList = commonBankAccountEntityList;
	}
	

}
