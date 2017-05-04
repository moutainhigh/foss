/**
 * @company : com.deppon
 * @poroject : foss结算
 * @copyright : copyright (c) 2016
 * 
 * @description: 待刷卡运单管理接口参数实体
 * @author : 潘士奇(309613)
 * @date : 2016年2月18日 上午11:23:14
 * @version : v1.0
 */
package com.deppon.foss.module.settlement.common.api.shared.domain;

import java.io.Serializable;

/**
 * @description: 待刷卡运单管理接口参数实体
 * @className: WSCWayBillParamEntity
 * 
 * @authorCode 309613
 * @date 2016年2月18日 上午11:23:40
 * 
 */
public class WSCWayBillParamEntity extends WSCWayBillEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 操作人所属部门编号
	 */
	private String orgCode;

	/**
	 * 已刷卡运单中止or作废
	 */
	private boolean swipedBillInvalide = false;

	/**
	 * 运单减少金额,取绝对值
	 */
	private double billReduceAmount;

	/**
	 * 已刷卡运单付款方式更改为非银行卡
	 */
	private boolean swipedBillPayWayChange = false;

	/**  
	 * 获取 操作人所属部门编号  
	 * @return orgCode 操作人所属部门编号  
	 */
	public String getOrgCode() {
		return orgCode;
	}

	/**  
	 * 设置 操作人所属部门编号  
	 * @param orgCode 操作人所属部门编号  
	 */
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	/**  
	 * 获取 已刷卡运单中止or作废
	 * @return swipedBillInvalide 已刷卡运单终止or作废  
	 */
	public boolean isSwipedBillInvalide() {
		return swipedBillInvalide;
	}

	/**  
	 * 设置 已刷卡运单中止or作废
	 * @param swipedBillInvalide 已刷卡运单终止or作废  
	 */
	public void setSwipedBillInvalide(boolean swipedBillInvalide) {
		this.swipedBillInvalide = swipedBillInvalide;
	}

	/**  
	 * 获取 运单减少金额取绝对值  
	 * @return billReduceAmount 运单减少金额取绝对值  
	 */
	public double getBillReduceAmount() {
		return billReduceAmount;
	}

	/**  
	 * 设置 运单减少金额取绝对值  
	 * @param billReduceAmount 运单减少金额取绝对值  
	 */
	public void setBillReduceAmount(double billReduceAmount) {
		this.billReduceAmount = billReduceAmount;
	}

	/**  
	 * 获取 已刷卡运单付款方式更改为非银行卡  
	 * @return swipedBillPayWayChange 已刷卡运单付款方式更改为非银行卡  
	 */
	public boolean isSwipedBillPayWayChange() {
		return swipedBillPayWayChange;
	}

	/**  
	 * 设置 已刷卡运单付款方式更改为非银行卡  
	 * @param swipedBillPayWayChange 已刷卡运单付款方式更改为非银行卡  
	 */
	public void setSwipedBillPayWayChange(boolean swipedBillPayWayChange) {
		this.swipedBillPayWayChange = swipedBillPayWayChange;
	}
}
