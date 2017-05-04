package com.deppon.foss.module.base.baseinfo.api.server.dao;

import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.dto.LtlServiceScopeDto;

public interface ILtlServiceScopeDao {

	/**
	 * 精确查询 通过 行政区域编码CODE 查询行政区域信息
	 * 
	 * @Title: queryDistrictByCode
	 * @author 200664-yangjinheng
	 * @date 2014年9月30日 下午12:38:28
	 * @throws
	 */
	LtlServiceScopeDto queryDistrictByCode(String code);

	/**
	 * 根据name查询行政区域
	 * 
	 * @Title: queryDistrictByName
	 * @author 200664-yangjinheng
	 * @date 2014年9月30日 下午12:38:38
	 * @throws
	 */
	LtlServiceScopeDto queryDistrictByName(String name);

	/**
	 * 查询该行政区域的营业网点信息和派送范围信息
	 * 
	 * @Title: queryLtlServiceScopeInfo
	 * @author 200664-yangjinheng
	 * @date 2014年9月30日 下午4:18:36
	 * @throws
	 */
	List<LtlServiceScopeDto> queryLtlServiceScopeInfo(LtlServiceScopeDto dto);

}
