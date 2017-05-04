/*
 * 党章，是一个政党为保证全党在政治上，思想上的一致和组织上，行动上的统一所制定的章程。
 * 一个党的党章的主要内容应该包括该党的性质、指导思想、纲领任务、组织结构、组织制度，党员的条件、权利、义务和纪律等项。
 * 通常衡量一个政党是否成熟党章也是关键因素之一。党章是政党的宗旨和行为规范。
 * 中国共产党现行党章于中国共产党第十八次全国代表大会部分修改，于2012年11月14日通过。
 * 除总纲外共十一章五十三条。规定了党的纲领、组织机构、组织制度、党员的条件、党员的义务和权利、党的纪律等项.
 */
package com.deppon.foss.module.settlement.writeoff.api.shared.dto;

import com.deppon.foss.framework.entity.BaseEntity;

public class WaybillChangeDetailPrintDto extends BaseEntity {

	/**
	 * 更改单明细打印
	 */
	private static final long serialVersionUID = -2735428227693403752L;

	/**
	 * 运单变更ID
	 */
	private String waybillRfcId;

	/**
	 * 变更项
	 */
	private String rfcItems;

	/**
	 * 变更前信息
	 */
	private String beforeRfcInfo;

	/**
	 * 变更后信息
	 */
	private String afterRfcInfo;

	/**
	 * 是否允许显示
	 */
	private String visiable;

	/**
	 * 变更项名称
	 */
	private String rfcItemsName;

	/**
	 * @return waybillRfcId
	 */
	public String getWaybillRfcId() {
		return waybillRfcId;
	}

	/**
	 * @param waybillRfcId
	 */
	public void setWaybillRfcId(String waybillRfcId) {
		this.waybillRfcId = waybillRfcId;
	}

	/**
	 * @return rfcItems
	 */
	public String getRfcItems() {
		return rfcItems;
	}

	/**
	 * @param rfcItems
	 */
	public void setRfcItems(String rfcItems) {
		this.rfcItems = rfcItems;
	}

	/**
	 * @return beforeRfcInfo
	 */
	public String getBeforeRfcInfo() {
		return beforeRfcInfo;
	}

	/**
	 * @param beforeRfcInfo
	 */
	public void setBeforeRfcInfo(String beforeRfcInfo) {
		this.beforeRfcInfo = beforeRfcInfo;
	}

	/**
	 * @return afterRfcInfo
	 */
	public String getAfterRfcInfo() {
		return afterRfcInfo;
	}

	/**
	 * @param afterRfcInfo
	 */
	public void setAfterRfcInfo(String afterRfcInfo) {
		this.afterRfcInfo = afterRfcInfo;
	}

	/**
	 * @return visiable
	 */
	public String getVisiable() {
		return visiable;
	}

	/**
	 * @param visiable
	 */
	public void setVisiable(String visiable) {
		this.visiable = visiable;
	}

	/**
	 * @return rfcItemsName
	 */
	public String getRfcItemsName() {
		return rfcItemsName;
	}

	/**
	 * @param rfcItemsName
	 */
	public void setRfcItemsName(String rfcItemsName) {
		this.rfcItemsName = rfcItemsName;
	}

}
