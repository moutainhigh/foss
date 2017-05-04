package com.deppon.foss.module.base.baseinfo.server.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.SqlSessionDaoSupport;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IAutoScheduleManageDao;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.AutoScheduleManageEntity;

public class AutoScheduleManageDao extends SqlSessionDaoSupport implements
		IAutoScheduleManageDao {
	
	 private static final String NAMESPACE = "foss.bse.bse-baseinfo.autoschedulemanage.";
		/**
		 * 
		 *
		 * @Function: com.deppon.foss.module.base.baseinfo.api.server.dao.IAutoScheduleManageDao.addAutoScheduleManage
		 * @Description:添加部门自动调度开启和关闭信息
		 *
		 * @param entity
		 * @return
		 *
		 * @version:v1.0
		 * @author:yangkang
		 * @date:2014-4-24 下午1:48:05
		 *
		 * Modification History:
		 * Date         Author      Version     Description
		 * -----------------------------------------------------------------
		 * 2014-4-24    yangkang      v1.0.0         create
		 */
	@Override
	public int addAutoScheduleManage(AutoScheduleManageEntity entity) {
		
		return this.getSqlSession().insert(NAMESPACE+"insert", entity);
	}
	/**
	 * 
	 *
	 * @Function: com.deppon.foss.module.base.baseinfo.api.server.dao.IAutoScheduleManageDao.deleteAutoScheduleManagesMore
	 * @Description:根据id数组批量删除部门自动调度开启和关闭信息
	 *
	 * @param codes
	 * @return
	 *
	 * @version:v1.0
	 * @author:yangkang
	 * @date:2014-4-24 下午1:48:27
	 *
	 * Modification History:
	 * Date         Author      Version     Description
	 * -----------------------------------------------------------------
	 * 2014-4-24    yangkang      v1.0.0         create
	 */
	@Override
	public int deleteAutoScheduleManagesMore(String[] codes) {
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("codes", codes);
		return this.getSqlSession().delete(NAMESPACE+"deleteByCode", map);
	}
    /**
     * 
     *
     * @Function: com.deppon.foss.module.base.baseinfo.api.server.dao.IAutoScheduleManageDao.updateAutoScheduleManage
     * @Description:更新部门自动调度开启和关闭信息 
     *
     * @param entity
     * @return
     *
     * @version:v1.0
     * @author:yangkang
     * @date:2014-4-24 下午1:49:10
     *
     * Modification History:
     * Date         Author      Version     Description
     * -----------------------------------------------------------------
     * 2014-4-24    yangkang      v1.0.0         create
     */
	@Override
	public int updateAutoScheduleManage(AutoScheduleManageEntity entity) {
		
		return this.getSqlSession().update(NAMESPACE+"update", entity);
	}
    /**
     * 
     *
     * @Function: com.deppon.foss.module.base.baseinfo.api.server.dao.IAutoScheduleManageDao.queryAutoScheduleManageListByDeptName
     * @Description:根据输入的部门名称模糊查询自动调度开启和关闭的信息
     *
     * @param entity
     * @param limit
     * @param start
     * @return
     *
     * @version:v1.0
     * @author:yangkang
     * @date:2014-4-24 下午1:50:42
     *
     * Modification History:
     * Date         Author      Version     Description
     * -----------------------------------------------------------------
     * 2014-4-24    yangkang      v1.0.0         create
     */
	@SuppressWarnings("unchecked")
	@Override
	public List<AutoScheduleManageEntity> queryAutoScheduleManageListByDeptName(AutoScheduleManageEntity entity,int limit,int start) {

		RowBounds rowBounds = new RowBounds(start, limit);

		return this.getSqlSession().selectList(NAMESPACE + "select",entity, rowBounds);
	}
    /**
     * 
     *
     * @Function: com.deppon.foss.module.base.baseinfo.api.server.dao.IAutoScheduleManageDao.queryRecordCount
     * @Description:统计分页查询时的总记录数
     *
     * @param entity
     * @return
     *
     * @version:v1.0
     * @author:yangkang
     * @date:2014-4-24 下午1:49:42
     *
     * Modification History:
     * Date         Author      Version     Description
     * -----------------------------------------------------------------
     * 2014-4-24    yangkang      v1.0.0         create
     */
	@Override
	public Long queryRecordCount(AutoScheduleManageEntity entity) {
		return (Long) this.getSqlSession().selectOne(NAMESPACE + "queryCount",
				entity);
	}
    /**
     * 
     *
     * @Function: com.deppon.foss.module.base.baseinfo.api.server.dao.IAutoScheduleManageDao.queryDeptNameCount
     * @Description:统计同一个部门的自动调度信息的条数
     *
     * @param entity
     * @return
     *
     * @version:v1.0
     * @author:yangkang
     * @date:2014-4-24 下午1:50:20
     *
     * Modification History:
     * Date         Author      Version     Description
     * -----------------------------------------------------------------
     * 2014-4-24    yangkang      v1.0.0         create
     */
	@Override
	public Long queryDeptNameCount(AutoScheduleManageEntity entity) {
		
	   return (Long) this.getSqlSession().selectOne(NAMESPACE + "queryDeptNameCount",
				entity);
	}
	/** 
	 * <p>TODO(根据输入的部门实体查询自动调度开启和关闭的信息)</p> 
	 * @author 187862
	 * @date 2014-5-19 上午9:43:48
	 * @param deptCode
	 * @param queryTime
	 * @return 
	 * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IAutoScheduleManageDao#queryAutoScheduleManageBydeptCode(java.lang.String, java.lang.String)
	 */
	@Override
	public AutoScheduleManageEntity queryAutoScheduleManageBydeptCode(
			String deptCode, String active) {
		// TODO Auto-generated method stub
		AutoScheduleManageEntity entity=new AutoScheduleManageEntity();
		entity.setDeptCode(deptCode);
		entity.setActive(active);
		return (AutoScheduleManageEntity) this.getSqlSession().selectOne(NAMESPACE + "selectByEntity", entity);
	}
}
