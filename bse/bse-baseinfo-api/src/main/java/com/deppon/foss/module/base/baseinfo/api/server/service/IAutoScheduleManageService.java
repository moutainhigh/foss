package com.deppon.foss.module.base.baseinfo.api.server.service;

import java.util.List;


import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.AutoScheduleManageEntity;

public interface IAutoScheduleManageService extends IService {
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
	int addautoScheduleManage(AutoScheduleManageEntity entity);

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
    int deleteAutoScheduleManagesMore(String[] codes);
	
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
    int updateAutoScheduleManage(AutoScheduleManageEntity entity);
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
     List<AutoScheduleManageEntity> queryAutoScheduleManageListByDeptName(AutoScheduleManageEntity entity,int limit, int start);
     
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
     Long queryRecordCount(AutoScheduleManageEntity entity);
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
     Long queryDeptNameCount(AutoScheduleManageEntity entity);
     /**
      * @Function: com.deppon.foss.module.base.baseinfo.api.server.dao.IAutoScheduleManageDao.queryAutoScheduleManageBydeptCode
      * @Description:根据输入的部门实体查询自动调度开启和关闭的信息
      * @author:187862-dujunhui
      * @date:2014-5-19 上午9:30:23
      */
      AutoScheduleManageEntity queryAutoScheduleManageBydeptCode(String deptCode,String active);
}
