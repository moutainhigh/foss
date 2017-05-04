/*
 * Copyright by Deppon and the original author or authors.
 * 
 * This document only allow internal use ,Any of your behaviors using the file
 * not internal will pay legal responsibility.
 *
 * You may learn more information about Deppon from
 *
 *      http://www.deppon.com
 *
 */ 
package com.deppon.foss.module.base.baseinfo.server.service.impl;

import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.server.dao.IHolidayDao;
import com.deppon.foss.module.base.baseinfo.api.server.service.IHolidayService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.HolidayEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.HolidayException;
import com.deppon.foss.util.CollectionUtils;

/**
 * 法定节假日基础资料Service实现
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:187862,date:2015-3-19 下午3:58:55,content:TODO </p>
 * @author 187862-dujunhui 
 * @date 2015-3-19 下午3:58:55
 * @since
 * @version
 */
public class HolidayService implements IHolidayService {

	/**
	 * 法定节假日基础资料DAO
	 */
	private IHolidayDao holidayDao;
	
	/**
	 * @param holidayDao the holidayDao to set
	 */
	public void setHolidayDao(IHolidayDao holidayDao) {
		this.holidayDao = holidayDao;
	}

	/** 
	 * <p>新增法定节假日基础资料</p> 
	 * @author 187862-dujunhui 
	 * @date 2015-3-19 下午3:58:55
	 * @param entity
	 * @return 
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.IHolidayService#addHoliday(com.deppon.foss.module.base.baseinfo.api.shared.domain.HolidayEntity)
	 */
	@Override
	public int addHoliday(HolidayEntity entity) {
		//做一次查询，确保法定节假日日期无冲突
		List<HolidayEntity> list=this.queryCountInterCrossDate(entity);
		if(CollectionUtils.isNotEmpty(list)){//不为空则说明有冲突
			throw new HolidayException("您所输入的节假日日期有冲突，请检查无误后，重新输入！");
		}
		return holidayDao.addHoliday(entity);
	}

	/** 
	 * <p>修改法定节假日基础资料</p> 
	 * @author 187862-dujunhui 
	 * @date 2015-3-19 下午3:58:55
	 * @param entity
	 * @return 
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.IHolidayService#uodateHoliday(com.deppon.foss.module.base.baseinfo.api.shared.domain.HolidayEntity)
	 */
	@Override
	public int updateHoliday(HolidayEntity entity) {
		//作废之前判断日期无冲突，防止作废后才发现修改后的日期冲突
		List<HolidayEntity> list=this.queryCountInterCrossDate(entity);
		if(CollectionUtils.isNotEmpty(list)){//不为空则说明“可能”有冲突
			for(HolidayEntity checkEntity:list){
				if(!checkEntity.getId().equals(entity.getId())){//如查询到的List实体中不含本条修改数据，则确实存在冲突
					throw new HolidayException("您所输入的节假日日期有冲突，请检查无误后，重新输入！");
				}
			}
		}
		String[] idArray=new String[1];
		idArray[0]=entity.getId();
		int i=holidayDao.deleteHolidays(idArray);
		int j=this.addHoliday(entity);
		return i+j;
	}

	/** 
	 * <p>作废法定节假日基础资料</p> 
	 * @author 187862-dujunhui 
	 * @date 2015-3-19 下午3:58:55
	 * @param voidNames
	 * @return 
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.IHolidayService#deleteHoliday(java.lang.String[])
	 */
	@Override
	public int deleteHoliday(String[] ids) {
		return holidayDao.deleteHolidays(ids);
	}

	/** 
	 * <p>根据条件查询法定节假日基础资料</p> 
	 * @author 187862-dujunhui 
	 * @date 2015-3-19 下午3:58:55
	 * @param entity
	 * @param start
	 * @param limit
	 * @return 
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.IHolidayService#queryHolidayListByCondition(com.deppon.foss.module.base.baseinfo.api.shared.domain.HolidayEntity, int, int)
	 */
	@Override
	public List<HolidayEntity> queryHolidayListByCondition(
			HolidayEntity entity, int start, int limit) {
		return holidayDao.queryHolidayListByCondition(entity, start, limit);
	}

	/** 
	 * <p>根据条件查询法定节假日基础资料条数</p> 
	 * @author 187862-dujunhui 
	 * @date 2015-3-19 下午5:19:08
	 * @param entity
	 * @return 
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.IHolidayService#queryHolidayListCountByCondition(com.deppon.foss.module.base.baseinfo.api.shared.domain.HolidayEntity)
	 */
	@Override
	public long queryHolidayListCountByCondition(HolidayEntity entity) {
		return holidayDao.queryHolidayListCountByCondition(entity);
	}

	/** 
	 * <p>查询法定节假日日期是否冲突</p> 
	 * @author 187862-dujunhui 
	 * @date 2015-3-20 下午2:31:45
	 * @param entity
	 * @return 
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.IHolidayService#queryCountInterCrossDate(com.deppon.foss.module.base.baseinfo.api.shared.domain.HolidayEntity)
	 */
	@Override
	public List<HolidayEntity> queryCountInterCrossDate(HolidayEntity entity) {
		return holidayDao.queryCountInterCrossDate(entity);
	}

}
