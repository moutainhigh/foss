package com.deppon.pda.bdm.module.foss.unload.shared.domain;
/**
 * 
 * TODO(描述类的职责)
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:Administrator,date:2013-3-20 下午6:37:55,content:TODO </p>
 * @author Administrator
 * @date 2013-3-20 下午6:37:55
 * @since
 * @version
 */
public class UnldBillModel {
	/**单据编号*/
	private String billNo;
	/**单据类型*/
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
