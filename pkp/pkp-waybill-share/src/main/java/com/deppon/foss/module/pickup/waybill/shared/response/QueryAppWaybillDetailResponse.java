package com.deppon.foss.module.pickup.waybill.shared.response;

import java.io.Serializable;
import java.util.List;

import com.deppon.foss.module.pickup.waybill.shared.vo.WaybillDetailVo;
import com.deppon.foss.util.define.FossConstants;

public class QueryAppWaybillDetailResponse implements Serializable{
	
	private List<WaybillDetailVo> wayBillDetailVoList;
	
	/**
	 * 总记录数
	 */
	private int count = 0 ;
	
	/**
	 * 是否成功
	 */
	private String success = FossConstants.YES;
	
	/**
	 * 错误信息
	 */
	private String errorMsg ;

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public String getSuccess() {
		return success;
	}

	public void setSuccess(String success) {
		this.success = success;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public List<WaybillDetailVo> getWayBillDetailVoList() {
		return wayBillDetailVoList;
	}

	public void setWayBillDetailVoList(List<WaybillDetailVo> wayBillDetailVoList) {
		this.wayBillDetailVoList = wayBillDetailVoList;
	}



}
