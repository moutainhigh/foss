package com.deppon.pda.bdm.module.foss.unload.shared.domain.driverunload;

/**
 * TODO(描述类的职责)
 * 
 * @ClassName TranUnloadBillModel.java
 * @Description
 * @author 245955
 * @date 2015-4-14
 */
public class TranUnloadBillModel {
	/** 单据编号 */
	private String billNo;
	/** 单据类型 */
	private String unloadOrderType;

	public String getBillNo() {
		return billNo;
	}

	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}

	public String getUnloadOrderType() {
		return unloadOrderType;
	}

	public void setUnloadOrderType(String unloadOrderType) {
		this.unloadOrderType = unloadOrderType;
	}

}
