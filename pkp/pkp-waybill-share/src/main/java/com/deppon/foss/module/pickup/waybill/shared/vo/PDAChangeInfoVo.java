package com.deppon.foss.module.pickup.waybill.shared.vo;

import java.io.Serializable;
import java.util.List;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillRfcChangeDetailEntity;

/**
 * PDA变更信息
 * @author WangQianJin
 * @date 2013-7-26 上午8:38:24
 */
public class PDAChangeInfoVo implements Serializable {

	/**
	 * 是否业务变更(业务变更包括：运输性质、目的站、提货网点、件数、包装信息)
	 */
	private boolean isBusinessChange;
	/**
	 * 变更项明细列表
	 */
	private List<WaybillRfcChangeDetailEntity> changeDetailList;
	
	public boolean isBusinessChange() {
		return isBusinessChange;
	}
	
	public void setBusinessChange(boolean isBusinessChange) {
		this.isBusinessChange = isBusinessChange;
	}

	public List<WaybillRfcChangeDetailEntity> getChangeDetailList() {
		return changeDetailList;
	}

	public void setChangeDetailList(
			List<WaybillRfcChangeDetailEntity> changeDetailList) {
		this.changeDetailList = changeDetailList;
	}	

}
