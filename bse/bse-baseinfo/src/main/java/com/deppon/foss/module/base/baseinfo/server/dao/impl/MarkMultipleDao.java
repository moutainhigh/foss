package com.deppon.foss.module.base.baseinfo.server.dao.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.deppon.foss.base.util.ComnConst;
import com.deppon.foss.framework.server.components.dataaccess.ibatis.SqlSessionDaoSupport;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IMarkMultipleDao;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.MarkMultipleEntity;

/**
 * 市场推广活动枚举接口实现类Dao层
 * 
 * @author 078816
 * @date   2014-04-01
 *
 */
public class MarkMultipleDao extends SqlSessionDaoSupport implements
		IMarkMultipleDao {

    private static final String NAMESPACE = ComnConst.MYBATIS_NAMESPACE_BASEINFO_PREFIX + ".markMultiple.";

	/**
	 * 新增一个活动枚举对象
	 * auther:wangpeng_078816 
	 * date:2014-4-8
	 * 
	 */
	@Override
	public int addMarkActivitiesMultiple(List<MarkMultipleEntity> list) {
		 int m = 0;
		 Map<String,Object> map = new HashMap<String,Object>();
		 map.put("list", list);
		 m = this.getSqlSession().insert(NAMESPACE + "addMarkActivitiesMultiple", map);
		return m;
	}

	/**
	 * 修改一个活动枚举对象
	 * auther:wangpeng_078816 
	 * date:2014-4-8 
	 * 
	 */
	@Override
	public int updateMarkActivitiesMultiple(List<MarkMultipleEntity> multipleList) {
		int m = 0;
		 m = this.getSqlSession().update(NAMESPACE + "updateMarkActivitiesMultiple", multipleList);
		return m;
	}

	/**
	 * 根据活动crmID和枚举类型查询相关的枚举信息
	 * auther:wangpeng_078816 
	 * date:2014-4-8
	 * 
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<MarkMultipleEntity> queryMarkActivitiesMultiplieByCrmId(
			BigDecimal crmId, String type) {
		Map<String,Object> map  = new HashMap<String,Object>();
		map.put("activiteCrmId", crmId);
		map.put("type", type);
		List<MarkMultipleEntity> list = this.getSqlSession().selectList(NAMESPACE + "queryMarkActivitiesMultiplieByCrmId", map);
		return list;
	}

	/**
	  * 查询活动的枚举信息（目前根据活动crmId和枚举值编码查询）
	  *
	  * auther:wangpeng_078816
	  * date:2014-4-17
	  *
	  */
	@Override
	public List<MarkMultipleEntity> queryMarkActivityMultiplie(
			MarkMultipleEntity entity) {
		@SuppressWarnings("unchecked")
		List<MarkMultipleEntity> list = this.getSqlSession().selectList(NAMESPACE + "queryMarkActivityMultiplie", entity);
		return list;
	}

	 /**
	  * 根据活动CRMID作废其关联的枚举信息
	  *
	  * auther:wangpeng_078816
	  * date:2014-6-27
	  *
	  */
	@Override
	public int deleteMarkActivitiesMultiple(MarkMultipleEntity entity){
		int m = 0;
		m = this.getSqlSession().update(NAMESPACE + "deleteMarkActivitiesMultiple", entity);
		return m;
	}
}
