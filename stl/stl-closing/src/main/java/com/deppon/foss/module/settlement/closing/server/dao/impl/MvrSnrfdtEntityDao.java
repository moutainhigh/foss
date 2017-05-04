package com.deppon.foss.module.settlement.closing.server.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.settlement.closing.api.server.dao.IMvrSnrfdtEntityDao;
import com.deppon.foss.module.settlement.closing.api.shared.domain.MvrNrfdtEntity;
import com.deppon.foss.module.settlement.closing.api.shared.dto.MvrNrfdtDto;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;

/**
 * 02到达月报表.
 *
 * @author 132599-foss-shenweihua
 * @date 2013-11-6 下午5:56:31
 */
public class MvrSnrfdtEntityDao extends iBatis3DaoImpl implements IMvrSnrfdtEntityDao{
	
	private static final String NAMESPACES = "foss.stv.MvrSnrfdtEntityDao.";// 命名空间路径
	/**
	 * 查询02特殊到达月报表
	 * 
	 * @author 132599-foss-shenweihua
	 * @date 2013-11-6 下午5:56:31
	 * @param dto
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<MvrNrfdtEntity> selectByConditions(MvrNrfdtDto dto, int start,
			int limit) {
		
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
	 * 查询02特殊到达月报表
	 * 
	 * @author 132599-foss-shenweihua
	 * @date 2013-11-6 下午5:56:31
	 * @param dto
	 * @return
	 */
	@Override
	public MvrNrfdtDto selectTotalByConditions(MvrNrfdtDto dto) {
		if(null == dto){
			//内部错误，参数为空
			throw new SettlementException("内部错误，参数为空！");
		}
		
		// 检查并封装查询条件.
		Map<String, Object> map = checkAndPackageDtoToMapCondition(dto);
		// 合计查询
		return (MvrNrfdtDto) this.getSqlSession().selectOne(NAMESPACES + "selectTotalByConditions", map);
	}
	
	/**
	 * 检查并封装查询条件.
	 *
	 * @param dto the dto
	 * @return the map
	 * @author 132599-foss-shenweihua
	 * @date 2014-06-12 下午5:56:31
	 */
	private Map<String, Object> checkAndPackageDtoToMapCondition(MvrNrfdtDto dto){
		
		if(null == dto){
			//内部错误，参数为空
			throw new SettlementException("内部错误，参数为空！");
		}
		
		Map<String, Object> map = new HashMap<String, Object>();
		// 设置期间
		map.put("period", dto.getPeriod());
		
		// 设置用户数据查询权限
		map.put("userCode", dto.getUserCode());
				
		 
		map.put("productCodeList", dto.getProductCodeList());
		 
		/**
		 * 如果客户编码不为空，设置客户编码
		 */
		if(StringUtils.isNotBlank(dto.getCustomerCode())){
			map.put("customerCode", dto.getCustomerCode());
		}
		/**
		 * 如果始发部门编码不为空，设置始发部门编码
		 */
		if(StringUtils.isNotBlank(dto.getOrigOrgCode())){
			map.put("origOrgCode", dto.getOrigOrgCode());
		}
		/**
		 * 如果到达部门编码不为空，设置到达部门编码
		 */
		if(StringUtils.isNotBlank(dto.getDestOrgCode())){
			map.put("destOrgCode", dto.getDestOrgCode());
		}
		return map;
	}

}
