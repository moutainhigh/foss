package com.deppon.foss.module.base.baseinfo.server.service.impl.commonselector;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.deppon.foss.module.base.baseinfo.api.server.dao.commonselector.ICommonExpressEmployeeDao;
import com.deppon.foss.module.base.baseinfo.api.server.service.commonselector.ICommonExpressEmployeeService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.EmployeeEntity;

/**
 * 根据条件查询快递员信息--公共选择器Service
 * 
 * @author WangPeng
 * @date   2013-08-28 9:30 AM
 *
 */
public class CommonExpressEmployeeService implements
		ICommonExpressEmployeeService {

	ICommonExpressEmployeeDao commonExpressEmployeeDao;
	
	public ICommonExpressEmployeeDao getCommonExpressEmployeeDao() {
		return commonExpressEmployeeDao;
	}

	public void setCommonExpressEmployeeDao(
			ICommonExpressEmployeeDao commonExpressEmployeeDao) {
		this.commonExpressEmployeeDao = commonExpressEmployeeDao;
	}

	/**
	 * 根据条件查询快递员信息
	 * 
	 * @author WangPeng
	 * @Date   2013-8-28 上午9:36:24
	 * @param  entity
	 * @param  start
	 * @param  limit
	 * @return List<EmployeeEntity>
	 * 
	 *
	 */
	public List<EmployeeEntity> queryExpressEmployeeByCondition(EmployeeEntity entity,int start,int limit) {
		return commonExpressEmployeeDao.queryExpressEmployeeByCondition(entity, start, limit);
	}
	
	/**
	 * 记录行数
	 * 
	 * @author WangPeng
	 * @Date   2013-8-28 上午9:36:24
	 * @param  entity
	 * @return Long
	 * 
	 *
	 */
	public Long recordQueryCount(EmployeeEntity entity) {
		return commonExpressEmployeeDao.recordQueryCount(entity);
	}
	
	/**
	 * 根据工号查询出此员工是否为快递员
	 * 提供给中转-232608
	 */
	@Override
	public EmployeeEntity queryCourierByCode(String code) {
		// TODO Auto-generated method stub
		if(StringUtils.isEmpty(code)){
			return null;
		}
		EmployeeEntity employeeEntity=new EmployeeEntity();
		employeeEntity.setEmpCode(code);
		EmployeeEntity entity=commonExpressEmployeeDao.queryCourierByCode(employeeEntity);
		return entity;
	}


}
