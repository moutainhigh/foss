package com.deppon.foss.module.base.baseinfo.server.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.components.dataaccess.ibatis.SqlSessionDaoSupport;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IClassifiedIncomeDao;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.ClassifiedIncomeEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.ClassifiedIncomeQueryDto;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

/**
 * 重分类基础信息实现DAO
 * @author 307196
 *
 */
public class ClassifiedIncomeDao extends SqlSessionDaoSupport implements
IClassifiedIncomeDao {

	
	/**
	 * 定义命名空间
	 */
	private static final String NAMESPACE = "foss.bse.bse-baseinfo.classifiedIncomeEntity.";
	/**
	 * 新增
	 */
	@Override
	public ClassifiedIncomeEntity addClassifiedIncome(
			ClassifiedIncomeEntity entity,CurrentInfo currentInfo,Date createTime,String flag) {
			if(entity == null){
				throw new BusinessException("新增重分类基础信息时参数异常");
			}
			//当前时间
			Date date = new Date();
			entity.setId(UUIDUtils.getUUID());
			entity.setCreateUser(currentInfo.getEmpCode());//创建人
			entity.setModifyUser(currentInfo.getEmpCode());//修改人
			entity.setActive(FossConstants.ACTIVE);//是否有效
			if(flag.equals(FossConstants.OPERATE_MODIFY)){
				entity.setCreateTime(createTime);//创建时间
			}else{
				entity.setCreateTime(date);//创建时间
			}
			
			entity.setModifyTime(new Date(NumberConstants.ENDTIME));//修改时间
			// 新增
			int result = getSqlSession().insert(NAMESPACE + "addClassifiedIncome", entity);
			return result > NumberConstants.ZERO ? entity : null;
		
	}
	/**
	 * 修改
	 */
	@Override
	public ClassifiedIncomeEntity updateClassifiedIncome(
			ClassifiedIncomeEntity entity, CurrentInfo currentInfo) {
		
		//当前时间
		Date date = new Date();
		entity.setModifyUser(currentInfo.getEmpCode());//修改人
		entity.setActive(FossConstants.INACTIVE);//是否有效
		entity.setModifyTime(date);//修改时间
		
		// 修改
		int result = getSqlSession().update(
				NAMESPACE + "updateOrDeleteClassifiedIncome", entity);
		return result > NumberConstants.ZERO ? entity : null;
		
		
	}

	/**
	 * 查询总条数
	 */
	@Override
	public long queryTotalByCondition(ClassifiedIncomeQueryDto entity) {
		return (Long) getSqlSession().selectOne(
				NAMESPACE + "queryTotalByCondition", entity);
		
	}
	/**
	 * 分页查询
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<ClassifiedIncomeEntity> queryClassifiedIncomeByCondition(
			ClassifiedIncomeQueryDto entity, int start, int limit) {
		
		
		// 查询数据
		RowBounds rowBounds = new RowBounds(start, limit);
		return this.getSqlSession().selectList(
				NAMESPACE + "queryClassifiedIncomeByCondition",
				entity,rowBounds);
	}
	/**
	 * 根据产品类型和所属子公司查询
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<ClassifiedIncomeEntity> queryClassifiedIncomeBySubAndType(
			String productTypeCode, String owendSubsidiaryCode) {
		Map<Object, Object> map = new HashMap<Object, Object>();
		map.put("productTypeCode", productTypeCode);
		map.put("owendSubsidiaryCode", owendSubsidiaryCode);
		map.put("active", FossConstants.ACTIVE);
		return this.getSqlSession().selectList(
				NAMESPACE + "queryClassifiedIncomeBySubAndType",
				map);
		
		
	}
	
	
	/**
	 * 根据ids查询
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<ClassifiedIncomeEntity> queryInfosByIds(List<String> ids) {
		Map<Object, Object> map = new HashMap<Object, Object>();
		map.put("ids", ids);
		return this.getSqlSession().selectList(
				NAMESPACE + "queryInfosByIds",map);
		
		
	}
	
}
