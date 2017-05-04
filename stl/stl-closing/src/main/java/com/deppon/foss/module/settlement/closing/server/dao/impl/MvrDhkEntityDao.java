package com.deppon.foss.module.settlement.closing.server.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.settlement.closing.api.server.dao.IMvrDhkEntityDao;
import com.deppon.foss.module.settlement.closing.api.shared.domain.MvrDhkEntity;
import com.deppon.foss.module.settlement.closing.api.shared.dto.MvrDhkDto;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;

/**
 * 代汇款月报表.
 *
 * @author 163576-foss-guxinhua
 * @date 2013-11-6 下午5:56:31
 */
public class MvrDhkEntityDao extends iBatis3DaoImpl implements IMvrDhkEntityDao {

	/** The Constant NAMESPACES. */
	private static final String NAMESPACES = "foss.stv.MvrDhkEntityDao.";// 命名空间路径
	
	/**
	 * 查询代汇款月报表
	 * 
	 * @author 163576-foss-guxinhua
	 * @date 2013-11-6 下午5:56:31
	 * @param dto
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<MvrDhkEntity> selectByConditions(MvrDhkDto dto, int start, int limit) {
		
		if(null == dto){
			//内部错误，参数为空
			throw new SettlementException("内部错误，参数为空！");
		}
		// 检查并封装查询条件.
		Map<String, Object> map = checkAndPackageDtoToMapCondition(dto);
		
		// 设置分页条件
		RowBounds rowBounds = new RowBounds(start, limit);
		// 分页查询
		return this.getSqlSession().selectList(NAMESPACES + "selectByConditions", map, rowBounds);
	}

	/**
	 * 查询代汇款月报表合计
	 * 
	 * @author 163576-foss-guxinhua
	 * @date 2013-11-6 下午5:56:31
	 * @param dto
	 * @return
	 */
	@Override
	public MvrDhkDto selectTotalByConditions(MvrDhkDto dto) {
		
		if(null == dto){
			//内部错误，参数为空
			throw new SettlementException("内部错误，参数为空！");
		}
		
		// 检查并封装查询条件.
		Map<String, Object> map = checkAndPackageDtoToMapCondition(dto);
		// 合计查询
		return (MvrDhkDto) this.getSqlSession().selectOne(NAMESPACES + "selectTotalByConditions", map);
	}
	
	/**
	 * 检查并封装查询条件.
	 *
	 * @param dto the dto
	 * @return the map
	 * @author 163576-foss-guxinhua
	 * @date 2013-11-6 下午5:56:31
	 */
	private Map<String, Object> checkAndPackageDtoToMapCondition(MvrDhkDto dto){
		
		if(null == dto){
			//内部错误，参数为空
			throw new SettlementException("内部错误，参数为空！");
		}
		
		Map<String, Object> map = new HashMap<String, Object>();
		// 设置期间
		map.put("period", dto.getPeriod());
		
		// 设置用户数据查询权限
		map.put("userCode", dto.getUserCode());
				
		if(StringUtils.isNotBlank(dto.getByremitOrgCode())){
			map.put("byremitOrgCode", dto.getByremitOrgCode());
		}
		
		if(StringUtils.isNotBlank(dto.getRemitOrgCode())){
			map.put("remitOrgCode", dto.getRemitOrgCode());
		}
		
		if(StringUtils.isNotBlank(dto.getCollectionType())){
			map.put("collectionType", dto.getCollectionType());
		}
		
		return map;
	}

}
