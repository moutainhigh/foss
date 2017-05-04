package com.deppon.foss.dubbo.ptp.api.define;

import java.io.Serializable;

/**
 *  修改应收单扣款状态同步接口接收与返回参数实体
 * 
 * @ClassName: UpdateWithholdStatusEntity
 * @author &307204 hemingyu
 * @date 2016-01-20 08:39:36
 */
public class UpdateWithholdStatusEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/***DOP推送数据****/
	
	/**
	 * 运单号
	 */
	private String wayBillNo;
    /**
     * 应收类型
     */
    private String billType;
    /**
     * 扣款状态
     */
    private String withholdStatus;
	/**
	 * 是否成功
	 */
	private String isSuccess;

	/**
	 * 错误信息
	 */
	private String errorMsg;
	
	/**
	 * 错误类型
	 */
	private String errorType;

	public String getWayBillNo() {
		return wayBillNo;
	}

	public void setWayBillNo(String wayBillNo) {
		this.wayBillNo = wayBillNo;
	}

	public String getBillType() {
		return billType;
	}

	public void setBillType(String billType) {
		this.billType = billType;
	}

	public String getWithholdStatus() {
		return withholdStatus;
	}

	public void setWithholdStatus(String withholdStatus) {
		this.withholdStatus = withholdStatus;
	}

	public String getIsSuccess() {
		return isSuccess;
	}

	public void setIsSuccess(String isSuccess) {
		this.isSuccess = isSuccess;
	}

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }


    public String getErrorType() {
		return errorType;
	}

	public void setErrorType(String errorType) {
		this.errorType = errorType;
	}

	@Override
	public String toString() {
		return "UpdateWithholdStatusEntity [wayBillNo=" + wayBillNo + ", billType="
				+ billType + ", withholdStatus=" + withholdStatus+ ", isSuccess="
				+ isSuccess + ", errorMsg=" + errorMsg + "]";
	}
	
}
