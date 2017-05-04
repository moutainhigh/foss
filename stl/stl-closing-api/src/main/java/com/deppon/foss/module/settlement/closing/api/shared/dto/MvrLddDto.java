package com.deppon.foss.module.settlement.closing.api.shared.dto;

import java.util.List;
import com.deppon.foss.module.settlement.closing.api.shared.domain.MvrLddEntity;

/**
 * 空运月报表对账单.
 * 
 * @author 046644-foss-zengbinwen
 * @date 2013-3-7 上午8:51:16
 */
public class MvrLddDto extends MvrLddEntity {

	/**
	 * 序列化id
	 */
	private static final long serialVersionUID = -8281462844008823663L;

	/** 总条数. */
	private int count;

	/** 用户编码-设置用户数据查询权限. */
	private String userCode;

	/** 查询结果列表. */
	private List<MvrLddEntity> queryList;

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
	 * @param count
	 *            the new count
	 */
	public void setCount(int count) {
		this.count = count;
	}

	/**
	 * @GET
	 * @return queryList
	 */
	public List<MvrLddEntity> getQueryList() {
		/*
		 * @get
		 * 
		 * @ return queryList
		 */
		return queryList;
	}

	/**
	 * @SET
	 * @param queryList
	 */
	public void setQueryList(List<MvrLddEntity> queryList) {
		/*
		 * @set
		 * 
		 * @this.queryList = queryList
		 */
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
