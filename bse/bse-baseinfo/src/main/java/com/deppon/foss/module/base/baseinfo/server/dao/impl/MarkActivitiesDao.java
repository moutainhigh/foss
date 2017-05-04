package com.deppon.foss.module.base.baseinfo.server.dao.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.deppon.foss.base.util.ComnConst;
import com.deppon.foss.framework.server.components.dataaccess.ibatis.SqlSessionDaoSupport;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IMarkActivitiesDao;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.MarkActivitiesEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.MarkActivitiesQueryConditionDto;
import com.deppon.foss.util.define.FossConstants;

/**
 * 市场活动接口实现类Dao层
 * 
 * @author 078816
 * @date   2014-03-31 
 *
 */
public class MarkActivitiesDao extends SqlSessionDaoSupport implements
		IMarkActivitiesDao {

	private static final String NAMESPACE = ComnConst.MYBATIS_NAMESPACE_BASEINFO_PREFIX + ".markActivities.";

	/**
	 *新增一个活动对象
	 * auther:wangpeng_078816
	 * date:2014-4-8
	 *
	 */
	@Override
	public int addMarkActivities(MarkActivitiesEntity entity) {
		int m = 0;
		m = this.getSqlSession().insert(NAMESPACE + "addMarkActivities", entity);
		return m;
	}

	/**
	 *修改一个活动对象
	 * auther:wangpeng_078816
	 * date:2014-4-8
	 *
	 */
	@Override
	public int updateMarkActivities(MarkActivitiesEntity entity) {
		int m = 0;
		m = this.getSqlSession().update(NAMESPACE + "updateMarkActivities", entity);
		return m;
	}
	
	/**
	 * 根据开单提供的参数查询该客户的当前运单可以享受的折扣信息
	 *
	 * auther:wangpeng_078816
	 * date:2014-4-16
	 *
	 */
	@Override
	public List<MarkActivitiesQueryConditionDto> queryMarkActivitiesByCondition(
			MarkActivitiesQueryConditionDto entity) {
		@SuppressWarnings("unchecked")
		List<MarkActivitiesQueryConditionDto> list = this.getSqlSession().selectList(NAMESPACE + "queryMarkActivitiesByCondition", entity);
		return list;
	}

	/**
	 *根据活动编码查询活动信息
	 * auther:wangpeng_078816
	 * date:2014-4-8
	 *
	 */
	@Override
	public MarkActivitiesEntity queryMarkActivitiesByCode(String activityCode) {
		Map<String,String> map = new HashMap<String,String>();
		map.put("code", activityCode);
		map.put("active",FossConstants.ACTIVE);
		
		MarkActivitiesEntity entity = (MarkActivitiesEntity) this.getSqlSession()
							.selectOne(NAMESPACE + "queryMarkActivitiesByCode", map);
		return entity;
	}
	
	/**
	 * 根据活动编码和开单时间查询活动信息(不考虑活动是否有效)
	 *
	 * auther:WangQianJin
	 * date:2014-06-12
	 *
	 */
	@Override
	public MarkActivitiesEntity queryMarkActivitiesByCodeAndBilltime(String activityCode,Date billlingTime){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("code", activityCode);
		map.put("billlingTime",billlingTime);
		
		MarkActivitiesEntity entity = (MarkActivitiesEntity) this.getSqlSession()
							.selectOne(NAMESPACE + "queryMarkActivitiesByCodeAndBilltime", map);
		return entity;
	}

	/**
	 *根据活动名称查询活动信息
	 * auther:wangpeng_078816
	 * date:2014-4-8
	 *
	 */
	@Override
	public MarkActivitiesEntity queryMarkActivitiesByName(String activityName) {
		Map<String,String> map = new HashMap<String,String>();
		map.put("name", activityName);
		map.put("active",FossConstants.ACTIVE);
		
		MarkActivitiesEntity entity = (MarkActivitiesEntity) this.getSqlSession()
							.selectOne(NAMESPACE + "queryMarkActivitiesByName", map);
		return entity;
	}
	

	/**
	 *根据crmId查询活动信息
	 * auther:wangpeng_078816
	 * date:2014-4-8
	 *
	 */
	@Override
	public MarkActivitiesEntity queryMarkActivitiesByCrmId(BigDecimal crmId) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("activeCrmId", crmId);
		map.put("active",FossConstants.ACTIVE);
		
		MarkActivitiesEntity entity = (MarkActivitiesEntity) this.getSqlSession()
							.selectOne(NAMESPACE + "queryMarkActivitiesByCrmId", map);
		return entity;
	}

}
