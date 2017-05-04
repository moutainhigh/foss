package com.deppon.foss.module.base.baseinfo.server.dao.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.deppon.foss.base.util.ComnConst;
import com.deppon.foss.framework.server.components.dataaccess.ibatis.SqlSessionDaoSupport;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IMarkActivitiesDeptDao;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.MarkActivitiesDeptEntity;

/**
 * 市场推广活动开展部门接口实现类Dao层
 *
 * @author 078816
 * @date   2014-04-01 
 *
 */
public class MarkActivitiesDeptDao extends SqlSessionDaoSupport implements
		IMarkActivitiesDeptDao {

	private static final String NAMESPACE = ComnConst.MYBATIS_NAMESPACE_BASEINFO_PREFIX + ".markActivitiesDept.";
	
	/**
	 * 新增一个活动开展部门对象
	 * auther:wangpeng_078816 
	 * date:2014-4-8
	 * 
	 */
	@Override
	public int addMarkActivitiesDept(List<MarkActivitiesDeptEntity> deptList) {
		int m = 0;
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("deptList", deptList);
		m = this.getSqlSession().insert(NAMESPACE + "addMarkActivitiesDept", map);
		return m;
	}

	/**
	 * 修改一个活动开展部门对象 
	 * auther:wangpeng_078816
	 * date:2014-4-8
	 * 
	 */
	@Override
	public int updateMarkActivitiesDept(MarkActivitiesDeptEntity entity) {
		int m = 0;
		m = this.getSqlSession().update(NAMESPACE + "updateMarkActivitiesDept", entity);
		return m;
	}

	/**
	 * 根据crmId查询活动开展部门是否存在 
	 * auther:wangpeng_078816 
	 * date:2014-4-8
	 * 
	 */
	@Override
	public MarkActivitiesDeptEntity queryMarkActivitiesDeptByCrmId(
			BigDecimal crmId) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("crmId", crmId);
		MarkActivitiesDeptEntity entity = (MarkActivitiesDeptEntity) this.getSqlSession()
				                 .selectOne(NAMESPACE + "queryMarkActivitiesDeptByCrmId", map);
		return entity;
	}

	/**
	 * 查询活动开展部门信息（活动crmId和部门编码）
	 *
	 * auther:wangpeng_078816
	 * date:2014-4-17
	 *
	 */
	@Override
	public List<MarkActivitiesDeptEntity> queryMarkActivitiesDept(
			MarkActivitiesDeptEntity entity) {
		@SuppressWarnings("unchecked")
		List<MarkActivitiesDeptEntity> list =  this.getSqlSession().selectList(NAMESPACE+"queryMarkActivitiesDept", entity);
		return list;
	}
	
	/**
	 * 查询活动开展部门信息（活动crmId和部门编码）(根据时间建模，适用于更改单)
	 *
	 * auther:WangQianJin
	 * date:2014-08-03
	 *
	 */
	@Override
	public List<MarkActivitiesDeptEntity> queryMarkActivitiesDeptByBillTime(MarkActivitiesDeptEntity entity,Date billlingTime){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("activiteCrmId", entity.getActiviteCrmId());
		map.put("billlingTime", billlingTime);
		@SuppressWarnings("unchecked")
		List<MarkActivitiesDeptEntity> list =  this.getSqlSession().selectList(NAMESPACE+"queryMarkActivitiesDeptByBillTime", map);
		return list;
	}

}
