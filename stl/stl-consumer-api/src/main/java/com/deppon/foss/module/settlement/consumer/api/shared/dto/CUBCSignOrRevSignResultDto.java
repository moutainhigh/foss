package com.deppon.foss.module.settlement.consumer.api.shared.dto;

import java.io.Serializable;

/**
 * CUBC签收，反签收响应结果集实体
 * @author 353654
 *
 */
public class CUBCSignOrRevSignResultDto implements Serializable{
	/**
	 * 序列化版本号
	 */
	private static final long serialVersionUID = 6223131112244429219L;
	
	/**
	 * 运单号
	 */
	private String wabillNo;
	
	/**
	 * 操作结果标记。Y成功N失败
	 */
	private String resultMark;
	
	/**
	 * 异常信息,非必有字段
	 */
	private String meg;

	public String getWabillNo() {
		return wabillNo;
	}

	public void setWabillNo(String wabillNo) {
		this.wabillNo = wabillNo;
	}

	public String getResultMark() {
		return resultMark;
	}

	public void setResultMark(String resultMark) {
		this.resultMark = resultMark;
	}

	public String getMeg() {
		return meg;
	}

	public void setMeg(String meg) {
		this.meg = meg;
	}
}
