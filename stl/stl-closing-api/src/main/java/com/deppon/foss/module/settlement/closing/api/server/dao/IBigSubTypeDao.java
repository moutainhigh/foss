package com.deppon.foss.module.settlement.closing.api.server.dao;

import java.util.List;

import com.deppon.foss.module.settlement.closing.api.shared.domain.BigSubTypeEntity;

public interface IBigSubTypeDao {

	/**
	 * 查询大类
	 * @param dto
	 * @return
	 */
	public List<BigSubTypeEntity> queryBigType(BigSubTypeEntity dto);
	
	
	/**
	 * 查询小类
	 * @param dto
	 * @return
	 */
	public List<BigSubTypeEntity> querySubType(BigSubTypeEntity dto);
	
	/**
	 * 
	 * @param dto
	 * @return
	 */
	public List<BigSubTypeEntity> queryAllTypes(BigSubTypeEntity dto);
	
}
