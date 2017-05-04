package com.deppon.foss.module.settlement.closing.server.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.settlement.closing.api.server.dao.IMvrRfdEntityDao;
import com.deppon.foss.module.settlement.closing.api.shared.domain.MvrRfdEntity;
import com.deppon.foss.module.settlement.closing.api.shared.dto.ClosingPeriodDto;
import com.deppon.foss.module.settlement.closing.api.shared.dto.MvrRfdDto;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.util.define.FossConstants;

/**
 * 专线到达.
 *
 * @author guxinhua
 * @date 2013-3-6 上午10:35:48
 */
public class MvrRfdEntityDao extends iBatis3DaoImpl implements IMvrRfdEntityDao {

	/** The Constant NAMESPACES. */
	private static final String NAMESPACES = "foss.stl.MvrRfdEntityDao.";// 命名空间路径
	
	/**
	 * 查询专线到达报表.
	 *
	 * @param dto the dto
	 * @param start the start
	 * @param limit the limit
	 * @return the list
	 * @author guxinhua
	 * @date 2013-3-6 上午11:25:45
	 * @see com.deppon.foss.module.settlement.closing.api.server.dao.IMvrRfdEntityDao#selectByConditions(com.deppon.foss.module.settlement.closing.api.shared.dto.MvrRfdDto)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<MvrRfdEntity> selectByConditions(MvrRfdDto dto, int start, int limit) {
		
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
	 * 查询专线到达报表合计.
	 *
	 * @param dto the dto
	 * @return the mvr rfd dto
	 * @author guxinhua
	 * @date 2013-3-6 上午11:26:10
	 * @see com.deppon.foss.module.settlement.closing.api.server.dao.IMvrRfdEntityDao#selectTotalByConditions(com.deppon.foss.module.settlement.closing.api.shared.dto.MvrRfdDto)
	 */
	@Override
	public MvrRfdDto selectTotalByConditions(MvrRfdDto dto) {
		
		if(null == dto){
			//内部错误，参数为空
			throw new SettlementException("内部错误，参数为空！");
		}
		
		// 检查并封装查询条件.
		Map<String, Object> map = checkAndPackageDtoToMapCondition(dto);
		// 合计查询
		return (MvrRfdDto) this.getSqlSession().selectOne(NAMESPACES + "selectTotalByConditions", map);
	}
	
	/**
	 * 检查并封装查询条件.
	 *
	 * @param dto the dto
	 * @return the map
	 * @author guxinhua
	 * @date 2013-3-6 上午11:44:51
	 */
	private Map<String, Object> checkAndPackageDtoToMapCondition(MvrRfdDto dto){
		
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

	@Override
	public List<ClosingPeriodDto> queryClosingPeriodList(){
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("active", FossConstants.INACTIVE);
		return this.getSqlSession().selectList(NAMESPACES+"queryClosingPeriodList",map);
	}
}
