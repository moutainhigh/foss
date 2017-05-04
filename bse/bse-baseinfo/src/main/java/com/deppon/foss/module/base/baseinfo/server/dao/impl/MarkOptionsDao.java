package com.deppon.foss.module.base.baseinfo.server.dao.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.deppon.foss.base.util.ComnConst;
import com.deppon.foss.framework.server.components.dataaccess.ibatis.SqlSessionDaoSupport;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IMarkOptionsDao;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.MarkOptionsEntity;

/**
 * 市场推广活动多选接口实现类Dao层
 * 价格折扣信息
 * @author 078816
 * @date   2014-03-31
 *
 */
public class MarkOptionsDao extends SqlSessionDaoSupport implements
		IMarkOptionsDao {
	private static final String NAMESPACE = ComnConst.MYBATIS_NAMESPACE_BASEINFO_PREFIX + ".markOptions.";
	
	/**
	 *新增一条活动价格折扣信息
	 * auther:wangpeng_078816
	 * date:2014-4-8
	 * 
	 */
	@Override
	public int addMarkActivitiesOptions(List<MarkOptionsEntity> optionList) {
		int m = 0;
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("optionList", optionList);
		m = this.getSqlSession().insert(NAMESPACE + "addMarkActivitiesOptions", map);
		return m;
	}

	/**
	 *修改一条活动价格折扣信息
	 * auther:wangpeng_078816
	 * date:2014-4-8
	 *
	 */
	@Override
	public int updateMarkActivitiesOptions(MarkOptionsEntity entity) {
		int m = 0;
		m  = this.getSqlSession().update(NAMESPACE + "updateMarkActivitiesOptions", entity);
		return m;
	}

	/**
	 *根据价格折扣的crmID查询该记录是否存在
	 * auther:wangpeng_078816
	 * date:2014-4-8
	 *
	 */
	@Override
	public MarkOptionsEntity queryMarkActivitiesOptionsByCrmId(BigDecimal crmId) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("crmId", crmId);
		MarkOptionsEntity entity = (MarkOptionsEntity) this.getSqlSession().selectOne(NAMESPACE + "queryMarkActivitiesOptionsByCrmId", map);
		return entity;
	}

	/**
	  * 查询活动的折扣信息（目前根据活动crmId和折扣名称查询）
	  *
	  * auther:wangpeng_078816
	  * date:2014-4-17
	  *
	  */
	@Override
	public List<MarkOptionsEntity> queryMarkActivityOptions(
			MarkOptionsEntity entity) {
		@SuppressWarnings("unchecked")
		List<MarkOptionsEntity> list = this.getSqlSession().selectList(NAMESPACE + "queryMarkActivityOptions", entity);
		return list;
	}
	
	/**
	 * 根据crmId查询活动多选信息（根据时间建模查询，适用于更改单）
	 * 折扣信息
	 * auther:WangQianJin
	 * date:2014-08-03
	 *
	 */
	@Override
	public List<MarkOptionsEntity> queryMarkActivityOptionsByBillTime(MarkOptionsEntity entity,Date billlingTime){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("activeCrmId", entity.getActiveCrmId());
		map.put("name", entity.getName());
		map.put("includeType", entity.getIncludeType());
		map.put("billlingTime", billlingTime);
		List<MarkOptionsEntity> list = this.getSqlSession().selectList(NAMESPACE + "queryMarkActivityOptionsByBillTime", map);
		return list;
	}

}
