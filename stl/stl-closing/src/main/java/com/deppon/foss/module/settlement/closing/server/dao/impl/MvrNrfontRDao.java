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
import com.deppon.foss.module.settlement.closing.api.server.dao.IMvrNrfontRDao;
import com.deppon.foss.module.settlement.closing.api.shared.domain.MvrNrfontREntity;
import com.deppon.foss.module.settlement.closing.api.shared.dto.MvrNrfontRDto;

/**
 * 02普通业务重分类报表DaoImpl
 * @author 340778 foss
 * @createTime 2016年8月17日 上午10:53:21
 */
public class MvrNrfontRDao extends iBatis3DaoImpl implements IMvrNrfontRDao {
	/**
	 * 命名空间路径
	 */
	private static final String NAMESPACES = "foss.stv.MvrNrfontREntityDao.";
	/**
	 * 条件-汇总-单记录查询-02普通业务重分类报表
	 */
	@Override
	public MvrNrfontRDto queryTotalByCondition(MvrNrfontRDto dto) {
		// 检查并封装查询条件.
		Map<String, Object> map = checkAndPackageDtoToMapCondition(dto);
		// 合计查询
		return (MvrNrfontRDto) this.getSqlSession().selectOne(NAMESPACES + "selectTotalByConditions", map);
	}
	
	/**
	 * 条件-多记录查询-02普通业务重分类报表
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<MvrNrfontREntity> queryByConditions(MvrNrfontRDto dto,int start,int limit) {
		// 检查并封装查询条件.
		Map<String, Object> map = checkAndPackageDtoToMapCondition(dto);
		// 设置分页条件
		RowBounds rowBounds = new RowBounds(start, limit);
		return this.getSqlSession().selectList(NAMESPACES + "selectByConditions", map, rowBounds);
	}

	/**
	 * 检查并封装dto信息
	 * @author 340778 foss
	 * @createTime 2016年8月17日 上午11:08:54
	 * @param dto
	 * @return
	 */
	private Map<String, Object> checkAndPackageDtoToMapCondition(MvrNrfontRDto dto) {
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
