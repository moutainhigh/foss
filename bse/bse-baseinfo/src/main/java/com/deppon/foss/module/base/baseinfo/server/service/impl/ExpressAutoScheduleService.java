package com.deppon.foss.module.base.baseinfo.server.service.impl;

import java.util.List;


import org.apache.cxf.common.util.StringUtils;

import com.deppon.foss.module.base.baseinfo.api.server.dao.IExpressAutoScheduleDao;
import com.deppon.foss.module.base.baseinfo.api.server.service.IExpressAutoScheduleService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.ExpressAutoScheduleEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.ExpressAutoScheduleException;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;
/**
 * 快递自动调度城市管理
 * @author yangkang
 *
 */
public class ExpressAutoScheduleService implements IExpressAutoScheduleService {
	/**
	 * 快递自动调度城市管理DAO
	 */
	private IExpressAutoScheduleDao expressAutoScheduleDao;
	
	public void setExpressAutoScheduleDao(
			IExpressAutoScheduleDao expressAutoScheduleDao) {
		this.expressAutoScheduleDao = expressAutoScheduleDao;
	}
	
	public IExpressAutoScheduleDao getExpressAutoScheduleDao() {
		return expressAutoScheduleDao;
	}

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
	@Override
	public int addExpressAutoSchedule(ExpressAutoScheduleEntity entity) {
			if (null == entity) {
			    return FossConstants.FAILURE;
			}
			entity.setId(UUIDUtils.getUUID());
		return this.expressAutoScheduleDao.addExpressAutoSchedule(entity);
	}
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
	@Override
	public int deleteExpressAutoScheduleMore(String[] codes) {
		
		return this.expressAutoScheduleDao.deleteExpressAutoScheduleMore(codes);
	}
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
	@Override
	public int updateExpressAutoSchedule(ExpressAutoScheduleEntity entity) {
		if(StringUtils.isEmpty(entity.getId())){
			throw new ExpressAutoScheduleException("传入的参数不允许为空！");
		}else{
			
			return this.expressAutoScheduleDao.updateExpressAutoSchedule(entity);
			
		}
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
		if (null == entity) {
			throw new ExpressAutoScheduleException("传入的参数不允许为空！");
		} else {
			return this.expressAutoScheduleDao.queryRecordCount(entity);
		}
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
		return this.expressAutoScheduleDao.queryCityNameCount(entity);
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
	@Override
	public List<ExpressAutoScheduleEntity> queryExpressAutoScheduleListByCityName(
			ExpressAutoScheduleEntity entity, int limit, int start) {
		if (null == entity) {
			throw new ExpressAutoScheduleException("传入的参数不允许为空！");
		} else {
			return this.expressAutoScheduleDao.queryExpressAutoScheduleListByCityName(entity, limit, start);
		}
	}

/** 
 * <p>接口：根据实体查询List</p> 
 * @author 187862-dujunhui
 * @date 2014-7-15 下午3:42:38
 * @param entity
 * @return 
 * @see com.deppon.foss.module.base.baseinfo.api.server.service.IExpressAutoScheduleService#queryExpressAutoScheduleList(com.deppon.foss.module.base.baseinfo.api.shared.domain.ExpressAutoScheduleEntity)
 */
    @Override
    public List<ExpressAutoScheduleEntity> queryExpressAutoScheduleList(
		ExpressAutoScheduleEntity entity) {

    	return expressAutoScheduleDao.queryExpressAutoScheduleList(entity);
}
	
	

}
