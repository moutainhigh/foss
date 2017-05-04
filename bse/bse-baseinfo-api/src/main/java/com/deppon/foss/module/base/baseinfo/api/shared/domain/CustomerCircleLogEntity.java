package com.deppon.foss.module.base.baseinfo.api.shared.domain;

import com.deppon.foss.framework.entity.BaseEntity;

/**
 * 
 * 客户圈信息
 * @author 308861 
 * @date 2016-12-21 上午10:25:23
 * @since
 * @version
 */
public class CustomerCircleLogEntity extends BaseEntity{
	/**
	 * 序列化ID
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 处理编码
	 */
	private String transactionCode;	
	/**
	 * 返回结果
	 */
	private String result;
	/**
	 * 成功与否 0为失败，1为成功
	 */
	private String trueorfalse;
	/**
	 * 失败原因
	 */ 
	private String falsereason;
	/**
	 * 传递内容
	 */
	private String content;
	
	public String getTransactionCode() {
		return transactionCode;
	}
	public void setTransactionCode(String transactionCode) {
		this.transactionCode = transactionCode;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public String getTrueorfalse() {
		return trueorfalse;
	}
	public void setTrueorfalse(String trueorfalse) {
		this.trueorfalse = trueorfalse;
	}
	public String getFalsereason() {
		return falsereason;
	}
	public void setFalsereason(String falsereason) {
		this.falsereason = falsereason;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
}
