package com.deppon.foss.module.base.baseinfo.server.service.impl;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.base.baseinfo.api.server.dao.ISalesExpenseMappingDao;
import com.deppon.foss.module.base.baseinfo.api.server.service.ISalesExpenseMappingService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SalesExpenseMappingEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.SalesExpenseMappingQueryDto;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;

/**
 * 营业部与外请车费用承担部门映射Service实现
 * @author 307196
 *
 */
public class SalesExpenseMappingService implements
ISalesExpenseMappingService {

	/**
	 * 日志打印对象声明
	 */
	private static final Logger log = Logger
			.getLogger(SalesExpenseMappingService.class);

	private ISalesExpenseMappingDao salesExpenseMappingDao;
	public void setSalesExpenseMappingDao(
			ISalesExpenseMappingDao salesExpenseMappingDao) {
		this.salesExpenseMappingDao = salesExpenseMappingDao;
	}


	/**
	 * 新增营业部与外请车费用承担部门映射信息
	 */
	@Override
	@Transactional
	public void addSalesExpenseMapping(
			List<SalesExpenseMappingEntity> entitys, CurrentInfo currentInfo) {
		
	/*	// 验证参数
		if (CollectionUtils.isEmpty(entitys)) {
			throw new BusinessException("新增营业部与外请车费用承担部门映射失败,参数异常");
		}*/
		// 开始日志
			log.info("begin addClassifiedIncome()...,");
			for(SalesExpenseMappingEntity entity:entitys){
				//根据营业部编码和预算承担部门编码查询
				List<SalesExpenseMappingEntity> salesExpenseMappingEntitys  = salesExpenseMappingDao.querySalesExpenseMappingBySubAndType(entity.getBusinessDepartmentCode(), entity.getBudgetDepartmentCode());
				if(CollectionUtils.isNotEmpty(salesExpenseMappingEntitys)){
					throw new BusinessException("新营业部与外请车费用承担部门映射已存在");
				}
				
			salesExpenseMappingDao.addSalesExpenseMapping(entity,currentInfo);
			}
			
	}
	
	
	/**
	 * 修改营业部与外请车费用承担部门映射信息
	 */
	@Override
	@Transactional
	public void updateSalesExpenseMapping(
			SalesExpenseMappingQueryDto entity, CurrentInfo currentInfo) {
		// 验证参数
		if (entity == null) {
			throw new BusinessException("修改营业部与外请车费用承担部门映射失败,参数异常");
		}
		
		toDoSelectedIds(entity);
		//根据ID查询
		List<SalesExpenseMappingEntity> oldSalesExpenseMappingEntitys = salesExpenseMappingDao.queryInfosByIds(entity.getSelectedIdList());
		if(CollectionUtils.isNotEmpty(oldSalesExpenseMappingEntitys)){
			List<SalesExpenseMappingEntity> salesExpenseMappingEntitys  = salesExpenseMappingDao.querySalesExpenseMappingBySubAndType(oldSalesExpenseMappingEntitys.get(0).getBusinessDepartmentCode(), entity.getBudgetDepartmentCode());
			if(CollectionUtils.isNotEmpty(salesExpenseMappingEntitys)){
				throw new BusinessException("新营业部与外请车费用承担部门映射已存在");
			}
			//作废老数据
			salesExpenseMappingDao.updateSalesExpenseMapping(oldSalesExpenseMappingEntitys.get(0), currentInfo);
			
			//新增数据
			SalesExpenseMappingEntity salesExpenseMappingEntity = new SalesExpenseMappingEntity();
			salesExpenseMappingEntity.setBusinessDepartmentCode(oldSalesExpenseMappingEntitys.get(0).getBusinessDepartmentCode());
			salesExpenseMappingEntity.setBusinessDepartmentName(oldSalesExpenseMappingEntitys.get(0).getBusinessDepartmentName());
			salesExpenseMappingEntity.setBudgetDepartmentCode(entity.getBudgetDepartmentCode());
			salesExpenseMappingEntity.setBudgetDepartmentName(entity.getBudgetDepartmentName());
			salesExpenseMappingDao.addSalesExpenseMapping(salesExpenseMappingEntity, currentInfo);
		}
	}
	/**
	 * 作废数据
	 */
	@Override
	@Transactional
	public void deleteSalesExpenseMapping(
			SalesExpenseMappingEntity entity, CurrentInfo currentInfo) {
			salesExpenseMappingDao.updateSalesExpenseMapping(entity, currentInfo);
		
		
	}
	/**
	 * 查询总条数
	 */
	@Override
	public long queryTotalByCondition(SalesExpenseMappingQueryDto entity) {
	
		return salesExpenseMappingDao.queryTotalByCondition(entity);
	}
	/**
	 * 分页查询
	 */
	@Override
	public List<SalesExpenseMappingEntity> querySalesExpenseMappingByCondition(SalesExpenseMappingQueryDto entity,int start, int limit) {
		return salesExpenseMappingDao.querySalesExpenseMappingByCondition(entity, start, limit);
	}
	/**
	 * 检查并处理选择ID
	 * @author 307196
	 */
	private void toDoSelectedIds(
			SalesExpenseMappingQueryDto salesExpenseMappingQueryDto) {

		// 如果选择的ID为空，提示输入参数错误
		if (StringUtils.isEmpty(salesExpenseMappingQueryDto.getSelectedIds())) {
			throw new BusinessException("没有选中的信息");
		}

		// 按“,”分割选中Id字串，生成ID的list
		String[] idArray = salesExpenseMappingQueryDto.getSelectedIds()
				.split(",");
		List<String> list = new ArrayList<String>();
		for (int i = 0; i < idArray.length; i++) {
			list.add(idArray[i].trim());
		}
		salesExpenseMappingQueryDto.setSelectedIdList(list);

	}
	/**
	 * 结算的方法接口 根据营业部编码查询
	 * @author 307196
	 */
	@Override
	public List<SalesExpenseMappingEntity> queryByBusinessDepart(String businessDepartmentCode){
		
		return salesExpenseMappingDao.queryByBusinessDepart(businessDepartmentCode);
	}

}




