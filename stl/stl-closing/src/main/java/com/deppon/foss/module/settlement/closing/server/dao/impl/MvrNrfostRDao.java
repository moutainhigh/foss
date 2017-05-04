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
import com.deppon.foss.module.settlement.closing.api.server.dao.IMvrNrfostRDao;
import com.deppon.foss.module.settlement.closing.api.shared.domain.MvrNrfostREntity;
import com.deppon.foss.module.settlement.closing.api.shared.dto.MvrNrfostRDto;

/**
 * 02特殊业务重分类报表DaoImpl
 * @author 340778 foss
 * @createTime 2016年8月17日 下午8:21:54
 */
public class MvrNrfostRDao extends iBatis3DaoImpl implements IMvrNrfostRDao {
	// 命名空间路径
	private static final String NAMESPACES = "foss.stv.MvrNrfostREntityDao.";
	/**
	 * 查询02特殊业务重分类报表总数 
	 */
	@Override
	public MvrNrfostRDto queryTotalByCondition(MvrNrfostRDto dto) {
		// 检查并封装查询条件.
		Map<String, Object> map = checkAndPackageDtoToMapCondition(dto);
		// 合计查询
		return (MvrNrfostRDto) this.getSqlSession().selectOne(NAMESPACES + "selectTotalByConditions", map);
	}
	
	/**
	 * 按条件查询02特殊业务重分类报表 
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<MvrNrfostREntity> queryByConditions(MvrNrfostRDto dto,int start,int limit) {
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
	private Map<String, Object> checkAndPackageDtoToMapCondition(MvrNrfostRDto dto) {
		Map<String, Object> map = new HashMap<String, Object>();
		// 设置期间
		map.put("period", dto.getPeriod());
		// 设置用户数据查询权限
		map.put("userCode", dto.getUserCode());
		/**
		 * 如果产品类型编码不为空 设置
		 */
		if(StringUtils.isNotBlank(dto.getProductCode())){
			map.put("productCode", dto.getProductCode());
		}
		/**
		 * 如果收入所属子公司编码不为空 设置
		 */
		if(StringUtils.isNotBlank(dto.getSubIncomeCpmpanyCode())){
			map.put("subIncomeCpmpanyCode", dto.getSubIncomeCpmpanyCode());
		}
		return map;
	}
}
