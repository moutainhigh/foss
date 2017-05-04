package com.deppon.foss.module.transfer.common.api.shared.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.formula.functions.T;

/**
 *@description 临时租车标记快递机场扫描单据Entity 返回参数
 *@version 1.0
 *@author 313352  gouyy
 *@date  2016-12-06 下午2:55:43
 */

public class ExpressAirportEntity implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// 状态 0:失败,1:成功
	private int status;
	// 异常信息
	private String exMsg;
	// 页大小
	private Long totalRows;
	// 总条数
	private List<QueryExpressAirportEntity> data = new ArrayList<QueryExpressAirportEntity>();  
	
	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getExMsg() {
		return exMsg;
	}

	public void setExMsg(String exMsg) {
		this.exMsg = exMsg;
	}

	public Long getTotalRows() {
		return totalRows;
	}

	public void setTotalRows(Long totalRows) {
		this.totalRows = totalRows;
	}

	public List<QueryExpressAirportEntity> getData() {
		return data;
	}

	public void setData(List<QueryExpressAirportEntity> data) {
		this.data = data;
	}

	
}
