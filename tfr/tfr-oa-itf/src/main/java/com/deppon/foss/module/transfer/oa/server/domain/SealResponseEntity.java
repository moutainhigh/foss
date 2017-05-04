package com.deppon.foss.module.transfer.oa.server.domain;



/**
 * @title: SealResponseEntity
 * @description：零担封签差错接口数据实体
 * @author： 习伟桢
 * @date： 2016-10-29 上午9:31:02
 */
public class SealResponseEntity {
	// 查询数据实体
	private SealModel result;
	// 结果标示
	private String isSuccess;
	// 返回信息
	private String mas;

	public SealModel getResult() {
		return result;
	}

	public void setResult(SealModel result) {
		this.result = result;
	}

	public String getIsSuccess() {
		return isSuccess;
	}

	public void setIsSuccess(String isSuccess) {
		this.isSuccess = isSuccess;
	}

	public String getMas() {
		return mas;
	}

	public void setMas(String mas) {
		this.mas = mas;
	}

}
