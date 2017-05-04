package com.deppon.foss.module.pickup.common.client.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang.ObjectUtils;
import org.apache.ibatis.session.SqlSession;
import org.springframework.util.CollectionUtils;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.pickup.common.client.dao.IMinFeePlanDao;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.MinFeePlanEntity;
import com.google.inject.Inject;

/**
 * 自提价最低一票Dao

  * @ClassName: IMinFeePlanDao

  * @Description: 20131011离线下载功能 BUG-55198

  * @author deppon-157229-zxy

  * @date 2013-10-11 上午8:08:58

  *
 */
public class MinFeePlanDao implements IMinFeePlanDao {

	/**
	 * 自提件最低一票Mapper NameSpace
	 */
	private static final String MIN_FEE_PLAN_NAME_SPACE = "com.deppon.foss.module.pickup.pricing.api.server.dao.MinFeePlanEntityMapper.";

	/**
	 * 新增自提件最低一票记录
	 */
	private static final String INSERT_MINFEEPLAN = "insertMinFeePlan";
	
	/**
	 * 根据指定条件查询记录集合
	 */
	public static final String SELECT_MINFEEPLAN_BY_CONDITION = "selectMinFeePlanByCondition";
	
	/**
	 * 删除指定id的记录
	 */
	private static final String DELETE_MINFEEPLAN_BY_ID = "deleteMinFeePlanById";
	
	/**
	 * 更新记录
	 */
	private static final String UPDATE_MINFEEPLAN_BY_ID = "updateMinFeePlanById";
	
	/**
	 * 名称空间
	 */
	private static final String NAMESPACE = "foss.pkp.NetGroupEntityMapper.";
	
	/**
	 * 数据库连接
	 */
	private SqlSession sqlSession;
	/**
	 * 数据库连接
	 * @return void
	 * @since:1.6
	 */
	@Inject
	public void setSqlSession(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}
	
	@Override
	public boolean insertMinFeePlan(MinFeePlanEntity minFeePlanEntity) {
		String sql = MIN_FEE_PLAN_NAME_SPACE + INSERT_MINFEEPLAN;
		Map<String, String> map = new HashMap<String, String>();
		map.put("id", minFeePlanEntity.getId());
		String id = sqlSession.selectOne(NAMESPACE + "selectById", map);
		if(ObjectUtils.NULL.equals(ObjectUtils.defaultIfNull(
				id, ObjectUtils.NULL))){
			this.sqlSession.insert(sql, minFeePlanEntity);
			return true;
		}else{
			return false;
		}
	}

	@Override
	public int deleteMinFeePlanById(String id) {
		String sql = MIN_FEE_PLAN_NAME_SPACE + DELETE_MINFEEPLAN_BY_ID;
		return this.sqlSession.delete(sql, id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public MinFeePlanEntity selectMinFeePlanById(String id) {
		MinFeePlanEntity minFeePlanEntity = new MinFeePlanEntity();
		minFeePlanEntity.setId(id);
		String sql = MIN_FEE_PLAN_NAME_SPACE + SELECT_MINFEEPLAN_BY_CONDITION;
		List<MinFeePlanEntity> list = this.sqlSession.selectList(sql, minFeePlanEntity);
		if(CollectionUtils.isEmpty(list)){
			return null;
		}else{
			if(list.size() > 1){
				throw new BusinessException("ID = " + id + "对应多条最低一票记录");			
			}else{
				return list.get(0);
			}
		}
	}

	@Override
	public int updateMinFeePlanEntity(MinFeePlanEntity minFeePlanEntity) {
		String sql = MIN_FEE_PLAN_NAME_SPACE + UPDATE_MINFEEPLAN_BY_ID;
		return this.sqlSession.delete(sql, minFeePlanEntity);
	}

}
