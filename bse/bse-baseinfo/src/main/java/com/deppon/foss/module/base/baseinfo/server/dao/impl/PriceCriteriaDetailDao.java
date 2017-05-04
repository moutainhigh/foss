package com.deppon.foss.module.base.baseinfo.server.dao.impl;

import java.util.Date;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.SqlSessionDaoSupport;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IPriceCriteriaDetailDao;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.PriceCriteriaDetailEntity;
/**
 * (计价方式明细,负责对所有相关价格明细管理)
 * @author dujunhui-187862
 * @date 2014-10-10 下午3:34:06
 * @since
 * @version
 */
public class PriceCriteriaDetailDao extends SqlSessionDaoSupport implements
		IPriceCriteriaDetailDao {
	// 计价方式明细ibatis命名空间
	private static final String PRICING_ENTITY_CRITERIADETAIL = "foss.bse.bse-baseinfo.priceCriteriaDetailEntityMapper.";
	// 插入计价方式明细
	private static final String INSERTSELECTIVE = "insertSelective";
	// 修改计价方式明细 
	private static final String UPDATECRITERIADETAIL = "updateCriteriaDetailByPrimaryKey";
	// 根据计费规则ID删除计价规则明细
	private static final String DELETEBYPRICEVALUATIONID = "deleteByPriceValuationId";
	// 根据主键ID查询计价规则明细
	private static final String SELECTBYPK = "selectByPrimaryKey";

	/**
	 * <p>(插入一条计价方式明细)</p>
	 * @author dujunhui-187862
	 * @date 2014-10-10 下午3:34:06
	 * @param record
	 * @see
	 */
	@Override
	public int insertSelective(PriceCriteriaDetailEntity record) {
		//创建时间
		record.setCreateDate(new Date());
		//修改时间
		record.setModifyDate(record.getCreateDate());
		return getSqlSession().insert(
				PRICING_ENTITY_CRITERIADETAIL + INSERTSELECTIVE, record);
	}
	/**
	 * <p>(原装插入)</p>
	 * @author dujunhui-187862
	 * @date 2014-10-29 上午9:26:04
	 * @param record
	 * @see
	 */
	@Override
	public void copyOriginalSelective(PriceCriteriaDetailEntity record) {
		//创建时间
		record.setCreateDate(record.getCreateDate()==null ? new Date():record.getCreateDate());
		//修改时间
		record.setModifyDate(new Date());
		getSqlSession().insert(PRICING_ENTITY_CRITERIADETAIL + "copyOriginalSelective", record);
	}

	/**
	 * 
	 * @Description: 根据主键查询
	 * @author dujunhui-187862
	 * @date 2014-10-29 上午9:20:12
	 * @param id
	 * @return
	 * @version 
	 */
	@Override
	public PriceCriteriaDetailEntity selectByPrimaryKey(String id) {
		return (PriceCriteriaDetailEntity)getSqlSession().selectOne(PRICING_ENTITY_CRITERIADETAIL + SELECTBYPK, id);
	}
	/**
	 * <p>修改计价方式明细，查询条件是ID</p>
	 * @author dujunhui-187862
	 * @date 2014-10-25 下午4:35:04
	 * @param record
	 * @see
	 */
	@Override
	public int updateCriteriaDetailByPrimaryKey(PriceCriteriaDetailEntity record) {
		//修改时间
		record.setModifyDate(new Date());
		return getSqlSession().update(
				PRICING_ENTITY_CRITERIADETAIL + UPDATECRITERIADETAIL, record);
	}
	/**
	 * 根据计费规则ID单条作废计价规则明细
	 * @author dujunhui-187862
	 * @date 2014-10-30 上午9:41:26
	 * @param valuationId
	 * @see
	 */
	@Override
	public void deleteCriteriaDetailByValuationId(String valuationId) {
		getSqlSession().delete(PRICING_ENTITY_CRITERIADETAIL + DELETEBYPRICEVALUATIONID, valuationId);
	}
}