package com.deppon.esb.pojo.domain.crm2foss;

/** 
 * 
 * 客户圈同步接口响应实体
 * @author 308861 
 * @date 2016-12-26 上午9:52:03
 * @since
 * @version
 */
public class CustCircleRelationResponse{
	//是否成功
    private Boolean resultCode;
    //异常信息
    private String reason;
    //交易编码
    private String transactionCode;
    
	public Boolean getResultCode() {
		return resultCode;
	}
	public void setResultCode(Boolean resultCode) {
		this.resultCode = resultCode;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public String getTransactionCode() {
		return transactionCode;
	}
	public void setTransactionCode(String transactionCode) {
		this.transactionCode = transactionCode;
	}
}
