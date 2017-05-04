package com.deppon.foss.module.transfer.platform.server.dao.impl;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.EmployeeEntity;
import com.deppon.foss.module.transfer.platform.api.server.dao.ITfrCtrAbsenteeInfoDao;
import com.deppon.foss.module.transfer.platform.api.shared.domain.TfrCtrAbsenteeInfoEntity;
import com.deppon.foss.module.transfer.platform.api.shared.dto.TfrCtrAbsenteeInfoQcDto;

public class TfrCtrAbsenteeInfoDao extends iBatis3DaoImpl implements
		ITfrCtrAbsenteeInfoDao {

	private static final String NAMESPACE = "com.deppon.foss.module.transfer.platform.api.server.dao.ITfrCtrAbsenteeInfoDao.";

	@Override
	public void insertTfrCtrAbsenteeInfo(TfrCtrAbsenteeInfoEntity entity) {
		this.getSqlSession().insert(NAMESPACE + "insertTfrCtrAbsenteeInfo",
				entity);
	}
	
	@Override
	public void deleteTfrCtrAbsenteeInfos(List<String> ids) {
		for(String id : ids){
			this.getSqlSession().delete(NAMESPACE + "deleteTfrCtrAbsenteeInfo", id);
		}
	}

	@Override
	public int updateTfrCtrAbsenteeInfo(TfrCtrAbsenteeInfoEntity entity) {
		return this.getSqlSession().update(NAMESPACE + "updateTfrCtrAbsenteeInfo",
				entity);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<TfrCtrAbsenteeInfoEntity> queryTfrCtrAbsenteeInfos(
			TfrCtrAbsenteeInfoQcDto qcDto) {
		
		return this.getSqlSession().selectList(
				NAMESPACE + "queryTfrCtrAbsenteeInfos", qcDto);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TfrCtrAbsenteeInfoEntity> queryPagingTfrCtrAbsenteeInfos(
			TfrCtrAbsenteeInfoQcDto qcDto, int start, int limit) {

		RowBounds rowBounds = new RowBounds(start, limit);

		return this.getSqlSession().selectList(
				NAMESPACE + "queryTfrCtrAbsenteeInfos", qcDto, rowBounds);
	}

	@Override
	public Long queryTfrCtrAbsenteeInfoCount(TfrCtrAbsenteeInfoQcDto qcDto) {
		return (Long) this.getSqlSession().selectOne(
				NAMESPACE + "queryTfrCtrAbsenteeInfoCount", qcDto);
	}
	

	@Override
	public TfrCtrAbsenteeInfoEntity queryTfrCtrAbsenteeInfoById(String id) {
		return (TfrCtrAbsenteeInfoEntity) this.getSqlSession().selectOne(
				NAMESPACE + "queryTfrCtrAbsenteeInfoById", id);
	}
	
	@Override
	public TfrCtrAbsenteeInfoEntity select1TfrCtrAbsenteeInfo(TfrCtrAbsenteeInfoEntity entity){
		return (TfrCtrAbsenteeInfoEntity) this.getSqlSession().selectOne(
				NAMESPACE + "select1TfrCtrAbsenteeInfo", entity);
	}

	/**
	 * 分页查询外场人员异常信息 不分页
	 * @param qcDto
	 * @return 外场人员异常信息集合
	 * @date 2015-01-22
	 * @author wqh
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<TfrCtrAbsenteeInfoEntity> queryPagingTfrCtrAbsenteeInfos(TfrCtrAbsenteeInfoQcDto qcDto)
	{
		return this.getSqlSession().selectList(NAMESPACE + "queryTfrCtrAbsenteeInfos", qcDto);
	}
	
	/**
	 *com.deppon.foss.module.transfer.platform.api.server.dao  
	 *@desc 根据员工code查询入职日期 
	 *@param empCode 员工工号
	 *@return Date
	 *@author 105795
	 *@date 2015年5月19日上午10:13:16 
	 */
	public EmployeeEntity queryEmployeeByEmpCode(String empCode){
		return (EmployeeEntity) this.getSqlSession().selectOne(NAMESPACE+"queryEmployeeByEmpCode", empCode);
	}
}
