package com.deppon.pda.bdm.module.foss.load.shared.domain;

import java.util.List;

import com.deppon.pda.bdm.module.core.shared.domain.ScanMsgEntity;

/**
 * 检查封签实体
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:Administrator,date:2012-12-3 上午10:36:21,content:TODO </p>
 * @author Administrator
 * @date 2012-12-3 上午10:36:21
 * @since
 * @version
 */
public class CheckLoadSeals extends ScanMsgEntity{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8616267671789204494L;
	/**
	 * 封签状态
	 */
	private String status;
	/**
	 * 车牌号
	 */
	private String truckCode;
	/**
	 * 封签异常备注
	 */
	private String checkOrgMemo;
	/**2013-11-07  注释掉
	 * 交接单号  
	 */
	//private List<String> receiptCodes;
	/**  2013-11-07 新增
	 *封签的录入方式  PDA_SCAN, PDA_HAND
	 */
	private String sealType;
	
	/**
     *到达封签明细 
     */
	private List<SealDestDetail> sealDestDetails;
	
	
	public String getTruckCode() {
		return truckCode;
	}
	public void setTruckCode(String truckCode) {
		this.truckCode = truckCode;
	}

	public String getCheckOrgMemo() {
		return checkOrgMemo;
	}
	public void setCheckOrgMemo(String checkOrgMemo) {
		this.checkOrgMemo = checkOrgMemo;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
/*	public List<String> getReceiptCodes() {
		return receiptCodes;
	}
	public void setReceiptCodes(List<String> receiptCodes) {
		this.receiptCodes = receiptCodes;
	}*/
    public String getSealType() {
        return sealType;
    }
    public void setSealType(String sealType) {
        this.sealType = sealType;
    }
    public List<SealDestDetail> getSealDestDetails() {
        return sealDestDetails;
    }
    public void setSealDestDetails(List<SealDestDetail> sealDestDetails) {
        this.sealDestDetails = sealDestDetails;
    }

	
}
