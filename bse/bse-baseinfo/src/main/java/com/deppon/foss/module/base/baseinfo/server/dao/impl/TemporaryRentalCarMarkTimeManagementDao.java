package com.deppon.foss.module.base.baseinfo.server.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.SqlSessionDaoSupport;
import com.deppon.foss.module.base.baseinfo.api.server.dao.ITemporaryRentalCarMarkTimeManagementDao;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.TemporaryRentalCarMarkTimeManagementEntity;
import com.deppon.foss.util.define.FossConstants;
/**
 * DAO
 * @author 218392  张永雪
 * @date 创建时间：2014-12-18 上午9:29:42
 */
public class TemporaryRentalCarMarkTimeManagementDao extends SqlSessionDaoSupport implements ITemporaryRentalCarMarkTimeManagementDao{
	
	
	private static final String NAMESPACE = "foss.bse.bse-baseinfo.temporaryRentalCarMarkTimeManagement.";

	/**
	 * 新增临时租车标记时间管理信息
	 */
	@Override
	public int addTemporaryRentalCarMarkTimeManagement(TemporaryRentalCarMarkTimeManagementEntity entity) {
		
		return this.getSqlSession().insert(NAMESPACE + "insertTemporaryRentalCarMarkTimeManagement", entity);
		
	}

	/**
	 * 修改临时租车标记时间管理信息
	 */
	@Override
	public int updateTemporaryRentalCarMarkTimeManagement(
			TemporaryRentalCarMarkTimeManagementEntity entity) {
		return this.getSqlSession().update(NAMESPACE + "updateTemporaryRentalCarMarkTimeManagement", entity);
	}

	/**
	 * 作废临时租车标记时间管理信息
	 */
	@Override
	public int deleteByIdTemporaryRentalCarMarkTimeManagement(
			List<String> idList) {
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("idList", idList);
		map.put("inactive", FossConstants.INACTIVE);
		map.put("active", FossConstants.ACTIVE);
		
		return this.getSqlSession().update(NAMESPACE + "deleteByIdTemporaryRentalCarMarkTimeManagement", map);
		
	}

	/**
	 * 根据传入的对象查询符合条件所有临时租车标记时间管理信息
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<TemporaryRentalCarMarkTimeManagementEntity> queryAllTemporaryRentalCarMarkTimeManagement(
			TemporaryRentalCarMarkTimeManagementEntity entity,int limit, int start) {
		
		RowBounds rowBounds = new RowBounds(start, limit);
		
		return this.getSqlSession().selectList(NAMESPACE + "queryAllTemporaryRentalCarMarkTimeManagement", entity, rowBounds);
		
	}

	/**
	 * 统计总记录数
	 */
	@Override
	public Long queryCount(TemporaryRentalCarMarkTimeManagementEntity entity) {
		
		return (Long)this.getSqlSession().selectOne(NAMESPACE + "queryCount", entity);
		
	}

	/**
	 * 根据部门属性code查询设置时长
	 */
	@Override
	public String querySetTimeByDeptAttributes(String deptAttributes) {
		Map<String,String> map = new HashMap<String,String>();
		map.put("deptAttributes", deptAttributes);
		map.put("active", FossConstants.ACTIVE);
		return (String)this.getSqlSession().selectOne(NAMESPACE + "querySetTimeByDeptAttributes", map);
	}
	

}
