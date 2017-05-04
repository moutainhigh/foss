package com.deppon.foss.module.settlement.common.api.shared.dto;

import java.io.Serializable;
import java.util.List;

import com.deppon.foss.module.settlement.common.api.shared.domain.PayInfoDO;

/**
 * 打款信息查询请求类
 * 
 * @ClassName: PayInfoRequest
 * @author &269052 |周禹安
 * @date 2017-1-4 下午10:15:11
 */
public class FossPayInfoRequest implements Serializable{
	private static final long serialVersionUID = 8598593937284009440L;
	
	/**
	 * GG,PDA_BILL,POS_CARD
	 */
	private String queryType;
	
	/**
	 * 运单号
	 */
	private String wayBillNo;
	
	/**
	 * 交易流水号
	 */
	private String serialNo;
	
	/**
	 * 刷卡部门编码
	 */
	private String deptCode;
	
	private List<PayInfoDO> payInfoDOs;
	
	private String isSuccess;
	
	private String errorMessage;

	public String getDeptCode() {
		return deptCode;
	}

	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}

	public String getQueryType() {
		return queryType;
	}

	public void setQueryType(String queryType) {
		this.queryType = queryType;
	}

	public String getWayBillNo() {
		return wayBillNo;
	}

	public void setWayBillNo(String wayBillNo) {
		this.wayBillNo = wayBillNo;
	}

	public String getSerialNo() {
		return serialNo;
	}

	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public List<PayInfoDO> getPayInfoDOs() {
		return payInfoDOs;
	}

	public void setPayInfoDOs(List<PayInfoDO> payInfoDOs) {
		this.payInfoDOs = payInfoDOs;
	}

	public String getIsSuccess() {
		return isSuccess;
	}

	public void setIsSuccess(String isSuccess) {
		this.isSuccess = isSuccess;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	
	
}
