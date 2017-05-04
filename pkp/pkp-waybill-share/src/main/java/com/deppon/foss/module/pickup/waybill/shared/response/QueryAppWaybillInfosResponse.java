package com.deppon.foss.module.pickup.waybill.shared.response;

import java.util.List;

import com.deppon.foss.module.pickup.waybill.shared.vo.AppWayBillDetaillVo;
import com.deppon.foss.util.define.FossConstants;

/**
 * APP查询Foss的发货单/收货单信息 返回信息
 * @author 272311
 * 2015.09.06
 */
public class QueryAppWaybillInfosResponse {

	/**
	 * 总记录数
	 */
	private int count = 0 ; 
	/**
	 * 收发货单详情列表
	 */
	private List<AppWayBillDetaillVo> waybillList ;
	
	/**
	 * 是否成功
	 */
	private String success = FossConstants.YES;
	
	/**
	 * 错误信息
	 */
	private String errorMsg ;
	
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
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public List<AppWayBillDetaillVo> getWaybillList() {
		return waybillList;
	}
	public void setWaybillList(List<AppWayBillDetaillVo> waybillList) {
		this.waybillList = waybillList;
	}

}
