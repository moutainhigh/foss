package com.deppon.foss.module.base.baseinfo.server.dao.impl;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.base.util.ComnConst;
import com.deppon.foss.framework.server.components.dataaccess.ibatis.SqlSessionDaoSupport;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IFailNonfixedCustomerDao;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.FailNonfixedCustomerEntity;

public class FailNonfixedCustomerDao extends SqlSessionDaoSupport implements IFailNonfixedCustomerDao{

	  private static final String NAMESPACE = ComnConst.MYBATIS_NAMESPACE_BASEINFO_PREFIX + ".failNonfixedCustomerEntity.";
	
	/**
	 * 分页查询
	 *273296
	 * @param start
	 * @param limit
	 * @return
	 */
	@Override
	public List<FailNonfixedCustomerEntity> queryListFailNonfixedCustomerEntity(
			int start, int limit) {
		RowBounds rowBounds = new RowBounds(start, limit);
		return this.getSqlSession().selectList(NAMESPACE+"queryInfosByPage",rowBounds);
	}

	/**
	 * 新增记录
	 *273296
	 * @param entity
	 */
	@Override
	public void insert(FailNonfixedCustomerEntity entity) {
		this.getSqlSession().insert(NAMESPACE+"insert",entity);
		
	}

	/**
	 * 变更推送失败记录数
	 *273296
	 * @param id
	 */
	@Override
	public void updateFailCount(FailNonfixedCustomerEntity entity) {
		 this.getSqlSession().update(NAMESPACE+"updateById",entity);
		
	}

	/**
	 * 删除推送成功的记录
	 *273296
	 * @param id
	 */
	@Override
	public void deleteFailNonfixedCustomerEntity(String id) {
		this.getSqlSession().update(NAMESPACE+"deleteById",id);
		
	}

}
