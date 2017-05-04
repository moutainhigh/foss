package com.deppon.foss.module.settlement.pay.api.shared.dto;

import java.io.Serializable;
import java.util.List;

import com.deppon.foss.module.settlement.common.api.shared.domain.BillReceivableEntity;

/**
 * 
 * 网上支付，查询应收单结果DTO
 * 
 * @author 088933-foss-zhangjiheng
 * @date 2012-11-29 下午2:51:58
 */
public class BillSOADOnlineResultDto implements Serializable {

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = 6639370918408545955L;

	/**
	 * 应收单集合
	 */
	private List<BillReceivableEntity> billReceivableEntityList;

	/**
	 * 总条数
	 */
	private int countNum;

	/**
	 * 是否成功操作
	 */
	private Boolean result;

	/**
	 * 错误原因
	 */
	private String errorReasion;

	/**
	 * @return billReceivableEntityList
	 */
	public List<BillReceivableEntity> getBillReceivableEntityList() {
		return billReceivableEntityList;
	}

	/**
	 * @param billReceivableEntityList
	 */
	public void setBillReceivableEntityList(
			List<BillReceivableEntity> billReceivableEntityList) {
		this.billReceivableEntityList = billReceivableEntityList;
	}

	/**
	 * @return countNum
	 */
	public int getCountNum() {
		return countNum;
	}

	/**
	 * @param countNum
	 */
	public void setCountNum(int countNum) {
		this.countNum = countNum;
	}

	/**
	 * @return result
	 */
	public Boolean getResult() {
		return result;
	}

	/**
	 * @param result
	 */
	public void setResult(Boolean result) {
		this.result = result;
	}

	/**
	 * @return errorReasion
	 */
	public String getErrorReasion() {
		return errorReasion;
	}

	/**
	 * @param errorReasion
	 */
	public void setErrorReasion(String errorReasion) {
		this.errorReasion = errorReasion;
	}

}
