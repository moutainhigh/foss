package com.deppon.foss.module.base.baseinfo.server.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.components.dataaccess.ibatis.SqlSessionDaoSupport;
import com.deppon.foss.module.base.baseinfo.api.server.dao.ISalesExpenseMappingDao;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SalesExpenseMappingEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.SalesExpenseMappingQueryDto;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

/**
 * 营业部与外请车费用承担部门映射实现DAO
 * @author 307196
 *
 */
public class SalesExpenseMappingDao extends SqlSessionDaoSupport implements
ISalesExpenseMappingDao {

	
	/**
	 * 定义命名空间
	 */
	private static final String NAMESPACE = "foss.bse.bse-baseinfo.salesExpenseMappingEntity.";
	/**
	 * 新增
	 */
	@Override
	public int addSalesExpenseMapping(SalesExpenseMappingEntity entity,CurrentInfo currentInfo) {
			if(entity == null){
				throw new BusinessException("新增营业部与外请车费用承担部门映射时参数异常");
			}
			//当前时间
			Date date = new Date();
			entity.setId(UUIDUtils.getUUID());
			entity.setCreateUser(currentInfo.getEmpCode());//创建人
			entity.setModifyUser(currentInfo.getEmpCode());//修改人
			entity.setActive(FossConstants.ACTIVE);//是否有效
			entity.setCreateTime(date);//创建时间
			entity.setModifyTime(new Date(NumberConstants.ENDTIME));//修改时间
			// 新增
			return getSqlSession().insert(NAMESPACE + "addSalesExpenseMapping", entity);
		
	}
	/**
	 * 修改
	 */
	@Override
	public int updateSalesExpenseMapping(SalesExpenseMappingEntity entity,CurrentInfo currentInfo) {
		
		//当前时间
		Date date = new Date();
		entity.setModifyUser(currentInfo.getEmpCode());//修改人
		entity.setActive(FossConstants.INACTIVE);//是否有效
		entity.setModifyTime(date);//修改时间
		
		// 修改
		return getSqlSession().update(
				NAMESPACE + "updateOrDeleteSalesExpenseMapping", entity);
		
		
	}

	/**
	 * 查询总条数
	 */
	@Override
	public long queryTotalByCondition(SalesExpenseMappingQueryDto entity) {
		entity.setActive(FossConstants.ACTIVE);
		return (Long) getSqlSession().selectOne(
				NAMESPACE + "queryTotalByCondition", entity);
		
	}
	/**
	 * 分页查询
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<SalesExpenseMappingEntity> querySalesExpenseMappingByCondition(SalesExpenseMappingQueryDto entity,int start, int limit) {
		
		
		// 查询数据
		RowBounds rowBounds = new RowBounds(start, limit);
		entity.setActive(FossConstants.ACTIVE);
		return this.getSqlSession().selectList(
				NAMESPACE + "querySalesExpenseMappingByCondition",
				entity,rowBounds);
	}
	/**
	 * 根据营业部编码和预算承担部门编码查询
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<SalesExpenseMappingEntity> querySalesExpenseMappingBySubAndType(String businessDepartmentCode,String budgetDepartmentCode) {
		Map<Object, Object> map = new HashMap<Object, Object>();
		map.put("businessDepartmentCode", businessDepartmentCode);
		map.put("budgetDepartmentCode", budgetDepartmentCode);
		map.put("active", FossConstants.ACTIVE);
		return this.getSqlSession().selectList(
				NAMESPACE + "querySalesExpenseMappingBySubAndType",
				map);
		
		
	}
	
	/**
	 * 根据营业部编码查询
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<SalesExpenseMappingEntity> queryByBusinessDepart(String businessDepartmentCode) {
		Map<Object, Object> map = new HashMap<Object, Object>();
		map.put("businessDepartmentCode", businessDepartmentCode);
		map.put("active", FossConstants.ACTIVE);
		return this.getSqlSession().selectList(
				NAMESPACE + "queryByBusinessDepart",
				map);
	}
	/**
	 * 根据ids查询
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<SalesExpenseMappingEntity> queryInfosByIds(List<String> ids) {
		Map<Object, Object> map = new HashMap<Object, Object>();
		map.put("ids", ids);
		return this.getSqlSession().selectList(
				NAMESPACE + "queryInfosByIds",map);
		
		
	}
	
}
