package com.deppon.foss.module.base.baseinfo.api.server.service;

import java.util.List;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.ExpressAutoScheduleEntity;

public interface IExpressAutoScheduleService extends IService {
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
	int addExpressAutoSchedule(ExpressAutoScheduleEntity entity);


	/**
	 * 
	 *
	 * @Function: com.deppon.foss.module.base.baseinfo.api.server.dao.IExpressAutoScheduleDao.deleteAutoScheduleManagesMore
	 * @Description:根据id数组逻辑删除快递自动调度城市管理的信息
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
    int deleteExpressAutoScheduleMore(String[] codes);
	
	
    /**
     * 
     *
     * @Function: com.deppon.foss.module.base.baseinfo.api.server.dao.IExpressAutoScheduleDao.updateAutoScheduleManage
     * @Description:更新快递自动调度城市管理的信息
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
    int updateExpressAutoSchedule(ExpressAutoScheduleEntity entity);
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
    Long queryRecordCount(ExpressAutoScheduleEntity entity);
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
    Long queryCityNameCount(ExpressAutoScheduleEntity entity);
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
     List<ExpressAutoScheduleEntity> queryExpressAutoScheduleListByCityName(ExpressAutoScheduleEntity entity,int limit,int start);


	/**
	 * <p>TODO(根据实体查询List)</p> 
	 * @author 187862-dujunhui
	 * @date 2014-7-15 下午3:35:52
	 * @param entity
	 * @return
	 * @see
	 */
	List<ExpressAutoScheduleEntity> queryExpressAutoScheduleList(
			ExpressAutoScheduleEntity entity);
}
