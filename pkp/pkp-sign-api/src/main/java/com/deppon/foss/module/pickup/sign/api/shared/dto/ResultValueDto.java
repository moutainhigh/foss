package com.deppon.foss.module.pickup.sign.api.shared.dto;

import java.io.Serializable;
import java.util.List;
/**
 * 返回给大客户系统的结果对象（签收数、异常签收数、签收率、运单明细集合）
 * @author 269871
 *
 */

public class ResultValueDto implements Serializable {
	/**
	 * 序列号
	 */
	private static final long serialVersionUID = -6839153124787579268L;
	//已签收票数
	private int signedNum;
	//异常签收票数
	private int excepNum;
	//签收率
	private String signRate;
	//运单明细集合
	private List<	SignDetailResultDto> list;
	//派送中票数
	private int deliveingNum;
	public int getDeliveingNum() {
		return deliveingNum;
	}
	public void setDeliveingNum(int deliveingNum) {
		this.deliveingNum = deliveingNum;
	}
	public List<SignDetailResultDto> getList() {
		return list;
	}
	public void setList(List<SignDetailResultDto> list) {
		this.list = list;
	}
	public int getSignedNum() {
		return signedNum;
	}
	public void setSignedNum(int signedNum) {
		this.signedNum = signedNum;
	}
	public int getExcepNum() {
		return excepNum;
	}
	public void setExcepNum(int excepNum) {
		this.excepNum = excepNum;
	}
	public String getSignRate() {
		return signRate;
	}
	public void setSignRate(String signRate) {
		this.signRate = signRate;
	}
}
