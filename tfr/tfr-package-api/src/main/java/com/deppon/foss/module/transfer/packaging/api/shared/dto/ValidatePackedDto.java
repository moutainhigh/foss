package com.deppon.foss.module.transfer.packaging.api.shared.dto;

import com.deppon.foss.framework.entity.BaseEntity;

/**
 * 校验包装的相关参数
 * */
public class ValidatePackedDto extends BaseEntity{
	
	  /**
	 * 
	 */
	private static final long serialVersionUID = 1384353858340933207L;
	//运单号	
	String waybillNo;
	  //加托个数
	int maskQty;
	  //其他需校验参数后期再加
	  
	public String getWaybillNo() {
		return waybillNo;
	}
	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}
	public int getMaskQty() {
		return maskQty;
	}
	public void setMaskQty(int maskQty) {
		this.maskQty = maskQty;
	}
	
	
}
