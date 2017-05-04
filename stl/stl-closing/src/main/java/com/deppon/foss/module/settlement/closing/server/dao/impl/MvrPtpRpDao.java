/**
 * 
 */
package com.deppon.foss.module.settlement.closing.server.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.settlement.closing.api.server.dao.IMvrPtpRpDao;
import com.deppon.foss.module.settlement.closing.api.shared.domain.MvrPtpRpEntity;
import com.deppon.foss.module.settlement.closing.api.shared.dto.MvrPtpRpDto;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;

/**
 * @author 231438
 * 合伙人奖罚月报表Dao
 */
public class MvrPtpRpDao extends iBatis3DaoImpl implements IMvrPtpRpDao {
	// 命名空间路径
	private static final String NAMESPACES = "foss.stv.MvrPtpRpEntityDao.";
	/**
	 * 查询合伙人奖罚月报表总数 
	 */
	@Override
	public MvrPtpRpDto queryTotalByCondition(MvrPtpRpDto dto) {
		if(null == dto){
			//内部错误，参数为空
			throw new SettlementException("内部错误，参数为空！");
		}
		// 检查并封装查询条件.
		Map<String, Object> map = checkAndPackageDtoToMapCondition(dto);
		// 合计查询
		return (MvrPtpRpDto) this.getSqlSession().selectOne(NAMESPACES + "selectTotalByConditions", map);
	}
	
	/**
	 * 按条件查询合伙人奖罚月报表 
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<MvrPtpRpEntity> queryByConditions(MvrPtpRpDto dto,int start,int limit) {
		if(null == dto){
			//内部错误，参数为空
			throw new SettlementException("内部错误，参数为空！");
		}
		// 检查并封装查询条件.
		Map<String, Object> map = checkAndPackageDtoToMapCondition(dto);
		// 设置分页条件
		RowBounds rowBounds = new RowBounds(start, limit);
		return this.getSqlSession().selectList(NAMESPACES + "selectByConditions", map, rowBounds);
	}



	/**
	 * @param dto
	 * @return
	 */
	private Map<String, Object> checkAndPackageDtoToMapCondition(MvrPtpRpDto dto) {
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
		 * 如果合伙人信息不为空 设置
		 */
		if(StringUtils.isNotBlank(dto.getCustomerCode())){
			map.put("customerCode", dto.getCustomerCode());
		}
		/**
		 * 如果应收部门编码不为空，设置应收部门编码
		 */
		if(StringUtils.isNotBlank(dto.getRecOrgCode())){
			map.put("recOrgCode", dto.getRecOrgCode());
		}
		/**
		 * 如果应付部门编码不为空，设置应付部门编码
		 */
		if(StringUtils.isNotBlank(dto.getPayOrgCode())){
			map.put("payOrgCode", dto.getPayOrgCode());
		}
		return map;
	}
}
