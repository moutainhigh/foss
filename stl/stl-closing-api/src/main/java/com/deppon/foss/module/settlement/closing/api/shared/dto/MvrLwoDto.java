package com.deppon.foss.module.settlement.closing.api.shared.dto;

import java.util.List;

import com.deppon.foss.module.settlement.closing.api.shared.domain.MvrLwoEntity;

/**
 * 空运月报表对账单.
 * 
 * @author 046644-foss-zengbinwen
 * @date 2013-3-7 上午8:51:16
 */
/**
 * TODO（描述类的职责）
 * 
 * @author 045738-foss-maojianqiang
 * @date 2013-8-2 上午10:37:47
 */
public class MvrLwoDto extends MvrLwoEntity {

	/**
	 * 序列化id
	 */
	private static final long serialVersionUID = -6008115557470759021L;

	/** 总条数. */
	private int count;

	/** 用户编码-设置用户数据查询权限. */
	private String userCode;

	/** 查询结果列表. */
	private List<MvrLwoEntity> queryList;

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
	public List<MvrLwoEntity> getQueryList() {
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
	public void setQueryList(List<MvrLwoEntity> queryList) {
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
