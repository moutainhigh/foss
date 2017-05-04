package com.deppon.foss.module.settlement.closing.api.shared.dto;

import com.deppon.foss.module.settlement.closing.api.shared.domain.MvrPtpAllDetailsEntity;

/**
 * 查询结果dto
 * @author 311396
 * @date 2016-3-22 下午4:12:24
 */
public class MvrPtpAllDetailResultDto extends MvrPtpAllDetailsEntity {
	/**
	 * 新增合计条数
	 */
	private int count;
	
	/**
	 * 大类显示名称
	 */
	private String typeCode;

	/**
	 * 小类显示名称
	 */
	private String subTypeCode;


	/**
	 * @GET
	 * @return count
	 */
	public int getCount() {
		/*
		 *@get
		 *@ return count
		 */
		return count;
	}

	/**
	 * @SET
	 * @param count
	 */
	public void setCount(int count) {
		/*
		 *@set
		 *@this.count = count
		 */
		this.count = count;
	}

	/**
	 * @GET
	 * @return typeCode
	 */
	public String getTypeCode() {
		/*
		 *@get
		 *@ return typeCode
		 */
		return typeCode;
	}

	/**
	 * @SET
	 * @param typeCode
	 */
	public void setTypeCode(String typeCode) {
		/*
		 *@set
		 *@this.typeCode = typeCode
		 */
		this.typeCode = typeCode;
	}

	/**
	 * @GET
	 * @return subTypeCode
	 */
	public String getSubTypeCode() {
		/*
		 *@get
		 *@ return subTypeCode
		 */
		return subTypeCode;
	}

	/**
	 * @SET
	 * @param subTypeCode
	 */
	public void setSubTypeCode(String subTypeCode) {
		/*
		 *@set
		 *@this.subTypeCode = subTypeCode
		 */
		this.subTypeCode = subTypeCode;
	}
	
	
}
