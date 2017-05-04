package com.deppon.foss.module.base.baseinfo.api.shared.vo;

import java.io.Serializable;
import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.GeneralTaxpayerInfoEntity;

public class TaxpayerInfoVo implements Serializable{

	/**
	 * 序列化
	 */
	private static final long serialVersionUID = -6333022543933577302L;
	
	/**
	 * 条件
	 */
	private GeneralTaxpayerInfoEntity taxpayerEntity;
	
	/**
	 * 返回结果
	 */
	private List<GeneralTaxpayerInfoEntity> taxpayerEntitys;

	public GeneralTaxpayerInfoEntity getTaxpayerEntity() {
		return taxpayerEntity;
	}

	public void setTaxpayerEntity(GeneralTaxpayerInfoEntity taxpayerEntity) {
		this.taxpayerEntity = taxpayerEntity;
	}

	public List<GeneralTaxpayerInfoEntity> getTaxpayerEntitys() {
		return taxpayerEntitys;
	}

	public void setTaxpayerEntitys(List<GeneralTaxpayerInfoEntity> taxpayerEntitys) {
		this.taxpayerEntitys = taxpayerEntitys;
	}
}
