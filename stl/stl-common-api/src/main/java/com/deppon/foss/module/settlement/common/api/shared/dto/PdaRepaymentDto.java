package com.deppon.foss.module.settlement.common.api.shared.dto;

import java.util.List;
import java.util.Map;

/**
 * PDA结清货款Dto
 * 
 * @author yaq
 *
 */

public class PdaRepaymentDto {
	
	/**
	 * 是否成功 "Y"成功   "N"失败
	 * 
	 */
	private String isSuccess;

	/**
	 * 交易流水号
	 * 
	 */
	private String tradeSerialNo;
	
	/**
	 * 出错消息
	 * 
	 */
	private String msg;
	
	/**
	 * PDA展示实体List
	 * 
	 */
	private List<CommonQueryParamDto> commonQueryParamDtoList;
	
	/**
	 * 	失败 的 运单号List
	 * 
	 */
	private List<String> wayBillNoList;
	
	/**
	 * 失败的信息Map以及成功后因有保管费未能结清的信息
	 * 
	 */
	private Map<String,String> msgMap;

	public String getIsSuccess() {
		return isSuccess;
	}

	public void setIsSuccess(String isSuccess) {
		this.isSuccess = isSuccess;
	}

	public String getTradeSerialNo() {
		return tradeSerialNo;
	}

	public void setTradeSerialNo(String tradeSerialNo) {
		this.tradeSerialNo = tradeSerialNo;
	}

	public List<CommonQueryParamDto> getCommonQueryParamDtoList() {
		return commonQueryParamDtoList;
	}

	public void setCommonQueryParamDtoList(
			List<CommonQueryParamDto> commonQueryParamDtoList) {
		this.commonQueryParamDtoList = commonQueryParamDtoList;
	}

	public List<String> getWayBillNoList() {
		return wayBillNoList;
	}

	public void setWayBillNoList(List<String> wayBillNoList) {
		this.wayBillNoList = wayBillNoList;
	}

	public Map<String, String> getMsgMap() {
		return msgMap;
	}

	public void setMsgMap(Map<String, String> msgMap) {
		this.msgMap = msgMap;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
	
}
