package com.deppon.foss.module.base.baseinfo.server.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.SqlSessionDaoSupport;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IExpressAutoScheduleDao;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.ExpressAutoScheduleEntity;

public class ExpressAutoScheduleDao extends SqlSessionDaoSupport implements
		IExpressAutoScheduleDao {
	
	private static final String NAMESPACE = "foss.bse.bse-baseinfo.expressautoschedule.";
	 
	/**
	 * 
	 *
	 * @Function: com.deppon.foss.module.base.baseinfo.api.server.dao.IExpressAutoScheduleDao.addAutoScheduleManage
	 * @Description:添加快递自动调度城市管理信息
	 *
	 * @param entity
	 * @return
	 *
	 * @version:v1.0
	 * @author:yangkang
	 * @date:2014-4-24 上午8:53:39
	 *
	 * Modification History:
	 * Date         Author      Version     Description
	 * -----------------------------------------------------------------
	 * 2014-4-24    yangkang      v1.0.0         create
	 */
	public int addExpressAutoSchedule(ExpressAutoScheduleEntity entity) {
		return this.getSqlSession().insert(NAMESPACE+"insert", entity);
	}
	/**
	 * 
	 *
	 * @Function: com.deppon.foss.module.base.baseinfo.api.server.dao.IExpressAutoScheduleDao.deleteAutoScheduleManagesMore
	 * @Description:根据id数组逻辑删除快递自动调度城市管理信息
	 *
	 * @param codes
	 * @return
	 *
	 * @version:v1.0
	 * @author:yangkang
	 * @date:2014-4-24 上午8:54:53
	 *
	 * Modification History:
	 * Date         Author      Version     Description
	 * -----------------------------------------------------------------
	 * 2014-4-24    yangkang      v1.0.0         create
	 */
	@Override
	public int deleteExpressAutoScheduleMore(String[] codes) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("codes", codes);
		return this.getSqlSession().delete(NAMESPACE+"deleteByCode", map);
	}
    /**
     * 
     *
     * @Function: com.deppon.foss.module.base.baseinfo.api.server.dao.IExpressAutoScheduleDao.updateAutoScheduleManage
     * @Description:更新快递自动调度城市管理信息
     *
     * @param entity
     * @return
     *
     * @version:v1.0
     * @author:yangkang
     * @date:2014-4-24 上午8:56:40
     *
     * Modification History:
     * Date         Author      Version     Description
     * -----------------------------------------------------------------
     * 2014-4-24    yangkang      v1.0.0         create
     */
	@Override
	public int updateExpressAutoSchedule(ExpressAutoScheduleEntity entity) {
		return this.getSqlSession().update(NAMESPACE+"update", entity);
	}
    /**
     * 
     *
     * @Function: com.deppon.foss.module.base.baseinfo.api.server.dao.IExpressAutoScheduleDao.queryRecordCount
     * @Description:分页查询统计总信息条数
     *
     * @param entity
     * @return
     *
     * @version:v1.0
     * @author:yangkang
     * @date:2014-4-24 上午8:58:00
     *
     * Modification History:
     * Date         Author      Version     Description
     * -----------------------------------------------------------------
     * 2014-4-24    yangkang      v1.0.0         create
     */
	@Override
	public Long queryRecordCount(ExpressAutoScheduleEntity entity) {
		return (Long) this.getSqlSession().selectOne(NAMESPACE + "queryCount",
				entity);
	}
    /**
     * 
     *
     * @Function: com.deppon.foss.module.base.baseinfo.api.server.dao.IExpressAutoScheduleDao.queryCityNameCount
     * @Description:统计同一个城市的自动调度信息的条数
     *
     * @param entity
     * @return
     *
     * @version:v1.0
     * @author:yangkang
     * @date:2014-4-24 上午8:59:43
     *
     * Modification History:
     * Date         Author      Version     Description
     * -----------------------------------------------------------------
     * 2014-4-24    yangkang      v1.0.0         create
     */
	@Override
	public Long queryCityNameCount(ExpressAutoScheduleEntity entity) {
		 return (Long) this.getSqlSession().selectOne(NAMESPACE + "queryCityNameCount",
					entity);
	}
    /**
     * 
     *
     * @Function: com.deppon.foss.module.base.baseinfo.api.server.dao.IExpressAutoScheduleDao.queryAutoScheduleManageListByCityName
     * @Description:根据输入的城市的名称分页模糊查询自动调度的信息
     *
     * @param entity
     * @param limit
     * @param start
     * @return
     *
     * @version:v1.0
     * @author:yangkang
     * @date:2014-4-24 上午9:01:19
     *
     * Modification History:
     * Date         Author      Version     Description
     * -----------------------------------------------------------------
     * 2014-4-24    yangkang      v1.0.0         create
     */
	@SuppressWarnings("unchecked")
	@Override
	public List<ExpressAutoScheduleEntity> queryExpressAutoScheduleListByCityName(ExpressAutoScheduleEntity entity, int limit, int start) {
		
		RowBounds rowBounds = new RowBounds(start, limit);

		return this.getSqlSession().selectList(NAMESPACE + "select",entity, rowBounds);
	}
	

    @SuppressWarnings("unchecked")
    @Override
	public List<ExpressAutoScheduleEntity> queryExpressAutoScheduleList(ExpressAutoScheduleEntity entity) {
		
		RowBounds rowBounds = new RowBounds(0, Integer.MAX_VALUE);
		return this.getSqlSession().selectList(NAMESPACE + "selectByEntity",entity, rowBounds);
	}
}
