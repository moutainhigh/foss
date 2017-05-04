package com.deppon.foss.module.base.baseinfo.server.dao.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.deppon.foss.base.util.ComnConst;
import com.deppon.foss.framework.server.components.dataaccess.ibatis.SqlSessionDaoSupport;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IMarkLineDeptDao;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.MarkLineDeptEntity;

/**
 * 市场推广活动线路信息接口实现类Dao层
 *
 * @author 078816
 * @date   2014-04-01
 * 
 */
public class MarkLineDeptDao extends SqlSessionDaoSupport implements
		IMarkLineDeptDao {
	
	private static final String NAMESPACE = ComnConst.MYBATIS_NAMESPACE_BASEINFO_PREFIX + ".markLineDept.";
	
	/**
	 * 新增一个活动线路信息对象
	 * auther:wangpeng_078816 
	 * date:2014-4-8
	 * 
	 */
	@Override
	public int addMarkActivitiesLineDept(List<MarkLineDeptEntity> lineList) {
		int m = 0 ;
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("lineList", lineList);
		m = this.getSqlSession().insert(NAMESPACE + "addMarkActivitiesLineDept", map);
		return m;
	}

	/**
	 * 修改一个活动线路信息对象
	 * auther:wangpeng_078816 
	 * date:2014-4-8
	 * 
	 */
	@Override
	public int updateMarkActivitiesLineDept(MarkLineDeptEntity entity) {
		int m = 0;
		m = this.getSqlSession().update(NAMESPACE + "updateMarkActivitiesLineDept", entity);
		return m;
	}

	/**
	 * 根据crmID查询线路信息是否存在
	 * auther:wangpeng_078816 
	 * date:2014-4-8
	 * 
	 */
	@Override
	public MarkLineDeptEntity queryMarkActivitiesLineDeptByCrmId(
			BigDecimal crmId) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("crmId", crmId);
		MarkLineDeptEntity entity  = (MarkLineDeptEntity) this.getSqlSession()
									.selectOne(NAMESPACE + "queryMarkActivitiesLineDeptByCrmId", map);
		return entity;
	}

	/**
	 * 查询活动的线路部门信息（目前根据活动crmId和外场编码查询）
	 *
	 * auther:wangpeng_078816
	 * date:2014-4-17
	 *
	 */
	@Override
	public List<MarkLineDeptEntity> queryMarkActivityLineDept(
			MarkLineDeptEntity entity) {
		@SuppressWarnings("unchecked")
		List<MarkLineDeptEntity> list = this.getSqlSession().selectList(NAMESPACE + "queryMarkActivityLineDept", entity);
		return list;
	}

}
