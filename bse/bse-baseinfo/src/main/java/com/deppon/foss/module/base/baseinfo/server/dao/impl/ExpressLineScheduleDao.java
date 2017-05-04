package com.deppon.foss.module.base.baseinfo.server.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.base.util.ComnConst;
import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.server.components.dataaccess.ibatis.SqlSessionDaoSupport;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IExpressLineScheduleDao;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.ExpressLineScheduleEntity;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;
/**
 *<p>Title: ExpressLineScheduleDao</p>
 * <p>Description:快递班车线路时刻表Dao实现类 </p>
 * <p>Company: Deppon</p>
 * @author    130566-ZengJunfan
 * @date       2014-5-13
 */
public class ExpressLineScheduleDao extends SqlSessionDaoSupport implements
		IExpressLineScheduleDao {
	private static final String NAMESPACE =ComnConst.MYBATIS_NAMESPACE_BASEINFO_PREFIX
			+ ".expressLineSchedule.";
	/**
	 *<p>Title: addExpressLineSchedule</p>
	 *<p>新增快递班车线路时刻表</p>
	 *@author 130566-ZengJunfan
	 *@date 2014-5-13下午5:45:21
	 * @param entity
	 * @return
	 */
	@Override
	public ExpressLineScheduleEntity addExpressLineSchedule(
			ExpressLineScheduleEntity entity) {
		Date now =new Date();
		entity.setId(UUIDUtils.getUUID());
		entity.setActive(FossConstants.ACTIVE);
		entity.setCreateDate(now);
		entity.setModifyUser(entity.getCreateUser());
		entity.setModifyDate(new Date(NumberConstants.ENDTIME));
		entity.setVersionNo(now.getTime());
		int result =this.getSqlSession().insert(NAMESPACE+"addExpressLineSchedule",entity);
		
		return result ==NumberConstants.ZERO? null:entity;
	}
	/**
	 *<p>Title: deleteExpressLineSchedule</p>
	 *<p>根据查询条件作废</p>
	 *@author 130566-ZengJunfan
	 *@date 2014-5-13下午5:47:19
	 * @param entity
	 * @return
	 */
	@Override
	public int deleteExpressLineSchedule(ExpressLineScheduleEntity entity) {
		entity.setActive(FossConstants.INACTIVE);
		Date now =new Date();
		entity.setModifyDate(now);
		entity.setVersionNo(now.getTime());
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("entity", entity);
		map.put("conditionActive", FossConstants.ACTIVE);
		return this.getSqlSession().update(NAMESPACE+"deleteExpressLineSchedule", map);
	}
	/**
	 *<p>Title: queryExpressLineScheduleList</p>
	 *<p>分页查询</p>
	 *@author 130566-ZengJunfan
	 *@date 2014-5-13下午5:54:33
	 * @param entity
	 * @param start
	 * @param limit
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<ExpressLineScheduleEntity> queryExpressLineScheduleList(
			ExpressLineScheduleEntity entity, int start, int limit) {
		RowBounds bounds =new RowBounds(start, limit);
		entity.setActive(FossConstants.ACTIVE);
		return this.getSqlSession().selectList(NAMESPACE+"queryExpressLineScheduleList", entity, bounds);
	}
	/**
	 *<p>Title: queryCount</p>
	 *<p>查询记录数</p>
	 *@author 130566-ZengJunfan
	 *@date 2014-5-13下午5:56:17
	 * @param entity
	 * @return
	 */
	@Override
	public long queryCount(ExpressLineScheduleEntity entity) {
		entity.setActive(FossConstants.ACTIVE);
		return (Long) this.getSqlSession().selectOne(NAMESPACE+"queryCount", entity);
	}
	/**
	 *<p>Title: queryDeptGisIdsByLineName</p>
	 *<p>根据线路名称查询线路集合</p>
	 *@author 130566-ZengJunfan
	 *@date 2014-5-17上午10:01:20
	 * @param lineName
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<ExpressLineScheduleEntity> queryExpressLineScheduleListByLineName(
			String lineName,String programName) {
		ExpressLineScheduleEntity entity =new ExpressLineScheduleEntity();
		entity.setLineName(lineName);
		entity.setProgramName(programName);
		entity.setActive(FossConstants.ACTIVE);
		return this.getSqlSession().selectList(NAMESPACE+"queryExpressLineScheduleListByLineName", entity);
	}
}
