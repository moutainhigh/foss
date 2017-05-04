package com.deppon.foss.module.settlement.closing.api.shared.dto;

import java.util.List;

import com.deppon.foss.module.settlement.closing.api.shared.domain.MvrAfrEntity;

/**
 * 空运月报表对账单.
 *
 * @author 046644-foss-zengbinwen
 * @date 2013-3-7 上午8:51:16
 */
public class MvrAfrDto extends MvrAfrEntity {

	/** 总条数. */
	private int count;
	
	/** 用户编码-设置用户数据查询权限. */
	private String userCode;

	/** 查询结果列表. */
	private List<MvrAfrEntity> queryList;

	/**
	 * Gets the count.
	 *
	 * @return count
	 */
	public int getCount() {
		return count;
	}

	/**
	 * Sets the count.
	 *
	 * @param count the new count
	 */
	public void setCount(int count) {
		this.count = count;
	}

	/**
	 * Gets the query list.
	 *
	 * @return queryList
	 */
	public List<MvrAfrEntity> getQueryList() {
		return queryList;
	}

	/**
	 * Sets the query list.
	 *
	 * @param queryList the new query list
	 */
	public void setQueryList(List<MvrAfrEntity> queryList) {
		this.queryList = queryList;
	}

	/**
	 * @return userCode
	 */
	public String getUserCode() {
		return userCode;
	}

	/**
	 * @param userCode
	 */
	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	
}
