/**
 * company   : com.deppon
 * poroject   : foss结算
 * copyright : copyright (c) 2016
 * 
 * @description: TODO
 * @author   : 潘士奇(309613)
 * @date     : 2016年2月28日 上午9:51:47
 * @version  : v1.0
 */
package com.deppon.foss.module.settlement.common.api.shared.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 
* @description: 待刷卡运单刷卡结果记录实体
* @className: WSCRecordEntity
* 
* @authorCode 309613
* @date 2016年2月28日 上午9:54:31 
*
 */
public class WSCRecordEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 刷卡记录集合,一笔刷卡交易包含多笔待刷卡运单
	 */
	private List<WSCWayBillEntity> wscRecordList;

	/**
	 * 交易流水号
	 */
	private String serialNo;

	/**
	 * 构造函数 
	 */
	public WSCRecordEntity() {
		if (wscRecordList == null) {
			this.setWscRecordList(new ArrayList<WSCWayBillEntity>());
		}
	}

	/**  
	 * 获取 刷卡记录集合  
	 * @return wscRecordList 刷卡记录集合  
	 */
	public List<WSCWayBillEntity> getWscRecordList() {
		return wscRecordList;
	}

	/**  
	 * 设置 刷卡记录集合  
	 * @param wscRecordList 刷卡记录集合  
	 */
	public void setWscRecordList(List<WSCWayBillEntity> wscRecordList) {
		this.wscRecordList = wscRecordList;
	}

	/**  
	 * 获取 交易流水号  
	 * @return serialNo 交易流水号  
	 */
	public String getSerialNo() {
		return serialNo;
	}

	/**  
	 * 设置 交易流水号  
	 * @param serialNo 交易流水号  
	 */
	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}

}
