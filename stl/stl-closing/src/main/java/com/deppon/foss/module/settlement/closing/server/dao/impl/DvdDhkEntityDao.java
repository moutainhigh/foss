package com.deppon.foss.module.settlement.closing.server.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.settlement.closing.api.server.dao.IDvdDhkEntityDao;
import com.deppon.foss.module.settlement.closing.api.shared.domain.DvdDhkEntity;
import com.deppon.foss.module.settlement.closing.api.shared.dto.DvdDhkDto;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;

/**
 * 代汇款明细报表.
 *
 * @author 163576-foss-guxinhua
 * @date 2013-11-6 下午5:56:31
 */
public class DvdDhkEntityDao extends iBatis3DaoImpl implements IDvdDhkEntityDao {

	/** The Constant NAMESPACES. */
	private static final String NAMESPACES = "foss.stv.DvdDhkEntityDao.";// 命名空间路径
	
	/**
	 * 查询代汇款明细报表
	 * 
	 * @author 163576-foss-guxinhua
	 * @date 2013-11-6 下午5:56:31
	 * @param dto
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<DvdDhkEntity> selectByConditions(DvdDhkDto dto, int start, int limit) {
		
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
	 * 查询代汇款明细报表合计
	 * 
	 * @author 163576-foss-guxinhua
	 * @date 2013-11-6 下午5:56:31
	 * @param dto
	 * @return
	 */
	@Override
	public DvdDhkDto selectTotalByConditions(DvdDhkDto dto) {
		
		if(null == dto){
			//内部错误，参数为空
			throw new SettlementException("内部错误，参数为空！");
		}
		
		// 检查并封装查询条件.
		Map<String, Object> map = checkAndPackageDtoToMapCondition(dto);
		// 合计查询
		return (DvdDhkDto) this.getSqlSession().selectOne(NAMESPACES + "selectTotalByConditions", map);
	}
	
	/**
	 * 检查并封装查询条件.
	 *
	 * @param dto the dto
	 * @return the map
	 * @author 163576-foss-guxinhua
	 * @date 2013-11-6 下午5:56:31
	 */
	private Map<String, Object> checkAndPackageDtoToMapCondition(DvdDhkDto dto){
		
		if(null == dto){
			//内部错误，参数为空
			throw new SettlementException("内部错误，参数为空！");
		}
		
		Map<String, Object> map = new HashMap<String, Object>();
		// 设置期间
		map.put("period", dto.getPeriod());
		
		map.put("startDate", dto.getStartDate());
		map.put("endDate", dto.getEndDate());
		
		// 设置用户数据查询权限
		map.put("userCode", dto.getUserCode());
		 
		if(StringUtils.isNotBlank(dto.getByremitOrgCode())){
			map.put("byremitOrgCode", dto.getByremitOrgCode());
		}
		
		if(StringUtils.isNotBlank(dto.getRemitOrgCode())){
			map.put("remitOrgCode", dto.getRemitOrgCode());
		}
		
		if(StringUtils.isNotBlank(dto.getBillType())){
			map.put("billType", dto.getBillType());
		}
		
		if(StringUtils.isNotBlank(dto.getPaymentType())){
			map.put("paymentType", dto.getPaymentType());
		}
		
		return map;
	}

}
