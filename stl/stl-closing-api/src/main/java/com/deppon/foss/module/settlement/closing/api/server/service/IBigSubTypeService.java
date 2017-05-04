package com.deppon.foss.module.settlement.closing.api.server.service;

import java.util.List;

import com.deppon.foss.module.settlement.closing.api.shared.domain.BigSubTypeEntity;

public interface IBigSubTypeService {

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
	 * 根据表名获得所有的大类小类
	 */
	public List<BigSubTypeEntity> queryAllTypes(BigSubTypeEntity dto);
	
}
