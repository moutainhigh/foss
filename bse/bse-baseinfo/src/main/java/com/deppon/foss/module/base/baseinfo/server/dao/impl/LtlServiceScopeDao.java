package com.deppon.foss.module.base.baseinfo.server.dao.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.deppon.foss.base.util.ComnConst;
import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.server.components.dataaccess.ibatis.SqlSessionDaoSupport;
import com.deppon.foss.module.base.baseinfo.api.server.dao.ILtlServiceScopeDao;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.LtlServiceScopeDto;
import com.deppon.foss.util.define.FossConstants;

public class LtlServiceScopeDao extends SqlSessionDaoSupport implements ILtlServiceScopeDao {

	/**
	 * 
	 * mybatis 命名空间
	 */
	private static final String NAMESPACE = ComnConst.MYBATIS_NAMESPACE_BASEINFO_PREFIX + ".ltlServiceScope.";

	/**
	 * 精确查询 通过 行政区域编码CODE 查询行政区域信息
	 * 
	 * @Title: queryDistrictByCode
	 * @author 200664-yangjinheng
	 * @date 2014年9月30日 下午12:39:47
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public LtlServiceScopeDto queryDistrictByCode(String code) {
		if (StringUtils.isBlank(code)) {
			return null;
		}

		// 构造查询条件：
		LtlServiceScopeDto entity = new LtlServiceScopeDto();
		entity.setActive(FossConstants.ACTIVE);
		entity.setCode(code);

		List<LtlServiceScopeDto> entitys = this.getSqlSession().selectList(NAMESPACE + "queryDistrictByCode", entity);
		if (entitys == null || entitys.isEmpty()) {
			return null;
		} else {
			return entitys.get(NumberConstants.ZERO);
		}
	}

	/**
	 * 根据name查询行政区域
	 * 
	 * @Title: queryDistrictByName
	 * @author 200664-yangjinheng
	 * @date 2014年9月30日 下午12:39:54
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public LtlServiceScopeDto queryDistrictByName(String name) {
		if (StringUtils.isBlank(name)) {
			return null;
		}
		// 构造查询条件：
		LtlServiceScopeDto entity = new LtlServiceScopeDto();
		entity.setActive(FossConstants.ACTIVE);
		entity.setName(name);

		List<LtlServiceScopeDto> entitys = this.getSqlSession().selectList(NAMESPACE + "queryDistrictByName", entity);
		if (entitys == null || entitys.isEmpty()) {
			return null;
		} else {
			return entitys.get(NumberConstants.ZERO);
		}
	}

	/**
	 * 查询该行政区域的营业网点信息和派送范围信息
	 * 
	 * @Title: queryLtlServiceScopeInfo
	 * @author 200664-yangjinheng
	 * @date 2014年9月30日 下午4:19:13
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public List<LtlServiceScopeDto> queryLtlServiceScopeInfo(LtlServiceScopeDto dto) {
		// 构造查询条件：
		LtlServiceScopeDto entity = new LtlServiceScopeDto();
		entity.setActive(FossConstants.ACTIVE);
		entity.setDegree(dto.getDegree());
		entity.setCode(dto.getCode());

		return this.getSqlSession().selectList(NAMESPACE + "queryLtlServiceScopeInfo", entity);
	}

}
