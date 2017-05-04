package com.deppon.foss.module.settlement.closing.api.shared.dto;

import java.util.List;

import com.deppon.foss.module.settlement.closing.api.shared.domain.MvrPlrEntity;


/**
 * 偏线月报DTO.
 *
 * @author 095793-foss-LiQin
 * @date 2013-3-13 上午10:38:48
 */
public class MvrPlrDto extends MvrPlrEntity {

	/** 偏线月报序列化. */
	private static final long serialVersionUID = 450981389165065410L;

	/** 总条数. */
	private int count;

	/** 用户编码-设置用户数据查询权限. */
	private String userCode;

	/** 查询结果列表. */
	private List<MvrPlrEntity> queryList;

	
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
	public List<MvrPlrEntity> getQueryList() {
		return queryList;
	}

	
	/**
	 * Sets the query list.
	 *
	 * @param queryList the new query list
	 */
	public void setQueryList(List<MvrPlrEntity> queryList) {
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
