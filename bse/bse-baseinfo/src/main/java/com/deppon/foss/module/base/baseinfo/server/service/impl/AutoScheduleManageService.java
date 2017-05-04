package com.deppon.foss.module.base.baseinfo.server.service.impl;

import java.util.List;

import org.apache.cxf.common.util.StringUtils;

import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IAutoScheduleManageDao;
import com.deppon.foss.module.base.baseinfo.api.server.service.IAutoScheduleManageService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.AutoScheduleManageEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.AutoScheduleManageException;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

/**
 * 部门自动调度管理service接口实现：对部门自动调度管理信息提供增删改查操作
 * <p style="display:none">
 * modifyRecord
 * </p>
 * <p style="display:none">
 * version:V1.0,author:130376--yangkang,date:2014-4-22 上午9:49:29
 * </p>
 * 
 * @author 130376--yangkang
 * @date 2014-4-22 上午9:49:29
 * @since
 * @version
 */
public class AutoScheduleManageService implements IAutoScheduleManageService {
    private IAutoScheduleManageDao autoScheduleManageDao;
	/**
	 * 设置自动调度信息dao
	 * */
	public void setAutoScheduleManageDao(
			IAutoScheduleManageDao autoScheduleManageDao) {
		this.autoScheduleManageDao = autoScheduleManageDao;
	}
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
	public int addautoScheduleManage(AutoScheduleManageEntity entity) {
		if (null == entity) {
		    return FossConstants.FAILURE;
		}
		entity.setId(UUIDUtils.getUUID());
		
		return this.autoScheduleManageDao.addAutoScheduleManage(entity);
	}
	@Override
	public int deleteAutoScheduleManagesMore(String[] codes) {
		
		return this.autoScheduleManageDao.deleteAutoScheduleManagesMore(codes);
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
		if(StringUtils.isEmpty(entity.getId())){
			throw new AutoScheduleManageException("传入的参数不允许为空！");
		}else{
			
			return this.autoScheduleManageDao.updateAutoScheduleManage(entity);
		}
		
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
	@Override
	public List<AutoScheduleManageEntity> queryAutoScheduleManageListByDeptName(AutoScheduleManageEntity entity,int limit,int start) {
		if (null == entity) {
			throw new AutoScheduleManageException("传入的参数不允许为空！");
		} else {
			return autoScheduleManageDao.queryAutoScheduleManageListByDeptName(entity, limit, start);
		}
	}
	
	/*	
	 * 接口调用
	 * *
	 */
	public List<AutoScheduleManageEntity> queryAutoScheduleManageList(AutoScheduleManageEntity entity,int limit,int start) {
		
		AutoScheduleManageEntity autoEntity  = new AutoScheduleManageEntity();
		autoEntity.setActive("Y");  
		return autoScheduleManageDao.queryAutoScheduleManageListByDeptName(entity, Integer.MAX_VALUE, 0);
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
		if (null == entity) {
			throw new AutoScheduleManageException("传入的参数不允许为空！");
		} else {
			// entity.setActive(FossConstants.ACTIVE);
			return autoScheduleManageDao.queryRecordCount(entity);
		}
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
		
		return this.autoScheduleManageDao.queryDeptNameCount(entity);
	}
	/*	
	 * 接口调用2
	 * *
	 */
	/** 
	 * <p>TODO(根据输入的部门实体查询自动调度开启和关闭的信息)</p> 
	 * @author 187862
	 * @date 2014-5-19 上午9:39:58
	 * @param deptCode
	 * @param queryTime
	 * @return 
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.IAutoScheduleManageService#queryAutoScheduleManageBydeptCode(java.lang.String, java.lang.String)
	 */
	@Override
	public AutoScheduleManageEntity queryAutoScheduleManageBydeptCode(String deptCode ,String active ){
		// TODO Auto-generated method stub
		if (StringUtil.isEmpty(deptCode)&& StringUtil.isEmpty(active)) {
			throw new AutoScheduleManageException("传入的参数不允许为空！");
		} else {
			return autoScheduleManageDao.queryAutoScheduleManageBydeptCode(deptCode, active);
		}
	}

}
