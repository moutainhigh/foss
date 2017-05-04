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
package com.deppon.foss.module.base.baseinfo.server.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.server.components.dataaccess.ibatis.SqlSessionDaoSupport;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IHolidayDao;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.HolidayEntity;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

/**
 * 法定节假日基础资料DAO实现
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:187862,date:2015-3-19 下午3:38:02,content:TODO </p>
 * @author 187862-dujunhui 
 * @date 2015-3-19 下午3:38:02
 * @since
 * @version
 */
public class HolidayDao extends SqlSessionDaoSupport implements IHolidayDao {

	private static final String NAMESAPCE="foss.bse.bse-baseinfo.holiday.";
	/** 
	 * <p>新增法定节假日基础资料</p> 
	 * @author 187862-dujunhui 
	 * @date 2015-3-19 下午3:38:03
	 * @param entity
	 * @return 
	 * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IHolidayDao#addHoliday(com.deppon.foss.module.base.baseinfo.api.shared.domain.HolidayEntity)
	 */
	@Override
	public int addHoliday(HolidayEntity entity) {
		entity.setId(UUIDUtils.getUUID());
		entity.setCreateDate(new Date());
		entity.setCreateUser(FossUserContext.getCurrentUser().getEmployee().getEmpCode());
		entity.setModifyDate(new Date(NumberConstants.ENDTIME));
		entity.setActive(FossConstants.ACTIVE);
		return this.getSqlSession().insert(NAMESAPCE + "addHoliday", entity);
	}

	/** 
	 * <p>修改法定节假日基础资料（已作废）</p> 
	 * @author 187862-dujunhui 
	 * @date 2015-3-19 下午3:38:03
	 * @param entity
	 * @return 
	 * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IHolidayDao#updateHoliday(com.deppon.foss.module.base.baseinfo.api.shared.domain.HolidayEntity)
	 */
	@Override
	public int updateHoliday(HolidayEntity entity) {
		return 0;
	}

	/** 
	 * <p>作废法定节假日基础资料</p> 
	 * @author 187862-dujunhui 
	 * @date 2015-3-19 下午3:38:03
	 * @param voidNames
	 * @return 
	 * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IHolidayDao#deleteHolidays(java.lang.String[])
	 */
	@Override
	public int deleteHolidays(String[] ids) {
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("inActive", FossConstants.INACTIVE);
		map.put("modifyDate", new Date());
		map.put("modifyUser", FossUserContext.getCurrentUser().getEmployee().getEmpCode());
		map.put("versionNo", new Date().getTime());
		map.put("active", FossConstants.ACTIVE);
		map.put("ids", ids);
		return this.getSqlSession().update(NAMESAPCE + "deleteHolidays", map);
	}

	/** 
	 * <p>根据条件查询法定节假日基础资料</p> 
	 * @author 187862-dujunhui 
	 * @date 2015-3-19 下午3:38:03
	 * @param entity
	 * @return 
	 * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IHolidayDao#queryHolidayListByCondition(com.deppon.foss.module.base.baseinfo.api.shared.domain.HolidayEntity)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<HolidayEntity> queryHolidayListByCondition(HolidayEntity entity,int start,int limit) {
		RowBounds rb=new RowBounds(start, limit);
		entity.setActive(FossConstants.ACTIVE);
		List<HolidayEntity> list=this.getSqlSession().selectList(NAMESAPCE + "queryHolidayListByCondition", entity, rb);
		return list==null?null:list;
	}

	/** 
	 * <p>根据条件查询法定节假日基础资料条数</p> 
	 * @author 187862-dujunhui 
	 * @date 2015-3-19 下午5:15:02
	 * @param entity
	 * @return 
	 * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IHolidayDao#queryHolidayListCountByCondition(com.deppon.foss.module.base.baseinfo.api.shared.domain.HolidayEntity)
	 */
	@Override
	public long queryHolidayListCountByCondition(HolidayEntity entity) {
		entity.setActive(FossConstants.ACTIVE);
		return (Long) this.getSqlSession().selectOne(NAMESAPCE + "countHolidayListByCondition", entity);
	}

	/** 
	 * <p>查询法定节假日日期是否冲突</p> 
	 * @author 187862-dujunhui 
	 * @date 2015-3-20 下午2:28:25
	 * @param entity
	 * @return 
	 * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IHolidayDao#queryCountInterCrossDate(com.deppon.foss.module.base.baseinfo.api.shared.domain.HolidayEntity)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<HolidayEntity> queryCountInterCrossDate(HolidayEntity entity) {
		entity.setActive(FossConstants.ACTIVE);
		List<HolidayEntity> list=this.getSqlSession().selectList(NAMESAPCE + "queryCountInterCrossDate", entity);
		return list==null?null:list;
	}

}
