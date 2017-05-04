package com.deppon.foss.module.base.baseinfo.server.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.base.util.ComnConst;
import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.components.export.excel.ExportResource;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IClassifiedIncomeDao;
import com.deppon.foss.module.base.baseinfo.api.server.service.IClassifiedIncomeService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IEmployeeService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.ClassifiedIncomeEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.EmployeeEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.ClassifiedIncomeQueryDto;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.util.define.FossConstants;

/**
 * 重分类基础信息关系Service实现
 * @author 307196
 *
 */
public class ClassifiedIncomeService implements
IClassifiedIncomeService {

	/**
	 * 日志打印对象声明
	 */
	private static final Logger log = Logger
			.getLogger(ClassifiedIncomeService.class);

	private IClassifiedIncomeDao classifiedIncomeDao;
	/**
	 * 人员接口service
	 */
	private IEmployeeService employeeService;
	
	
	public void setEmployeeService(IEmployeeService employeeService) {
		this.employeeService = employeeService;
	}

	public void setClassifiedIncomeDao(IClassifiedIncomeDao classifiedIncomeDao) {
		this.classifiedIncomeDao = classifiedIncomeDao;
	}

	/**
	 * 新增重分类基础信息
	 */
	@Override
	@Transactional
	public ClassifiedIncomeEntity addClassifiedIncome(
			ClassifiedIncomeEntity entity, CurrentInfo currentInfo) {
		
		// 验证参数
		if (entity == null) {
			throw new BusinessException("新增重分类基础信息失败,参数异常");
		}
		// 开始日志
			log.info("begin addClassifiedIncome()...,");
			//根据产品类型和所属子公司查询
			List<ClassifiedIncomeEntity> classifiedIncomeEntitys  = classifiedIncomeDao.queryClassifiedIncomeBySubAndType(entity.getProductTypeCode(), entity.getOwendSubsidiaryCode());
			if(CollectionUtils.isNotEmpty(classifiedIncomeEntitys)){
				throw new BusinessException("新增重分类基础信息已存在");
			}
			
			return classifiedIncomeDao.addClassifiedIncome(entity,currentInfo,null,"add");
	}
	
	/**
	 * 新增重分类基础信息
	 */
	@Override
	@Transactional
	public void importClassifiedIncomeList(
			List<ClassifiedIncomeEntity> entitys) {
			if(CollectionUtils.isEmpty(entitys)){
				throw new BusinessException("导入数据为空");
			}
			// 导入开始日志
			log.info("begin importClassifiedIncomeList()...,");
			// 获取当前登录用户信息
			CurrentInfo currentInfo = FossUserContext.getCurrentInfo();
			for(int i = 0 ; i < entitys.size() ; i ++){
				//根据产品类型和所属子公司查询
				List<ClassifiedIncomeEntity> classifiedIncomeEntitys  = classifiedIncomeDao.queryClassifiedIncomeBySubAndType(entitys.get(i).getProductTypeCode(), entitys.get(i).getOwendSubsidiaryCode());
				if(CollectionUtils.isNotEmpty(classifiedIncomeEntitys)){
					String productTypeName = entitys.get(i).getProductTypeName();
					String owendSubsidiaryName = entitys.get(i).getOwendSubsidiaryName();
					
					throw new BusinessException("产品类型为："+productTypeName+"；所属子公司为："+owendSubsidiaryName+"的信息已存在");
				}
				
				classifiedIncomeDao.addClassifiedIncome(entitys.get(i),currentInfo,null,"add");
			}
	}
	/**
	 * 修改重分类基础信息
	 */
	@Override
	@Transactional
	public void updateClassifiedIncome(
			ClassifiedIncomeQueryDto entity, CurrentInfo currentInfo) {
		// 验证参数
		if (entity == null) {
			throw new BusinessException("修改重分类基础信息失败,参数异常");
		}
		
		toDoSelectedIds(entity);
		//根据ID查询
		List<ClassifiedIncomeEntity> oldClassifiedIncomeEntitys = classifiedIncomeDao.queryInfosByIds(entity.getSelectedIdList());
		if(CollectionUtils.isNotEmpty(oldClassifiedIncomeEntitys)){
			//作废老数据
			ClassifiedIncomeEntity classifiedIncomeEntity = classifiedIncomeDao.updateClassifiedIncome(oldClassifiedIncomeEntitys.get(0), currentInfo);
			//新增数据
			classifiedIncomeEntity.setSixPercent(entity.getSixPercent());
			classifiedIncomeEntity.setElevenPercent(entity.getElevenPercent());
			classifiedIncomeDao.addClassifiedIncome(classifiedIncomeEntity, currentInfo, classifiedIncomeEntity.getModifyTime(),FossConstants.OPERATE_MODIFY);
		}
	}
	/**
	 * 作废数据
	 */
	@Override
	@Transactional
	public void deleteClassifiedIncome(
			ClassifiedIncomeQueryDto entity, CurrentInfo currentInfo) {
		toDoSelectedIds(entity);
		
	
		//根据ID查询
		List<ClassifiedIncomeEntity> oldClassifiedIncomeEntitys = classifiedIncomeDao.queryInfosByIds(entity.getSelectedIdList());
		for(ClassifiedIncomeEntity toDelEntity:oldClassifiedIncomeEntitys){
			//如果非有效状态，不可作废
			if(!FossConstants.ACTIVE.equals(toDelEntity.getActive())){
				throw new BusinessException("只有有效的信息才可作废");
			}
			classifiedIncomeDao.updateClassifiedIncome(toDelEntity, currentInfo);
			
		}
		
	}
	/**
	 * 查询总条数
	 */
	@Override
	public long queryTotalByCondition(ClassifiedIncomeQueryDto entity) {
	
		return classifiedIncomeDao.queryTotalByCondition(entity);
	}
	/**
	 * 分页查询
	 */
	@Override
	public List<ClassifiedIncomeEntity> queryClassifiedIncomeByCondition(
			ClassifiedIncomeQueryDto entity, int start, int limit) {
		List<ClassifiedIncomeEntity> classifiedIncomeEntitys = classifiedIncomeDao.queryClassifiedIncomeByCondition(entity, start, limit);
		//处理返回结果
		for(ClassifiedIncomeEntity dto:classifiedIncomeEntitys){
			//把修改人名称加上
			EmployeeEntity employeeEntityModifyer = employeeService
					.querySimpleEmployeeByEmpCode(dto.getModifyUser());
			if(null != employeeEntityModifyer){
				dto.setModifyUserName(employeeEntityModifyer.getEmpName());
			}else{
				dto.setModifyUserName(dto.getModifyUser());
			}
		}
		
		return classifiedIncomeEntitys;
	}
	/**
	 * 检查并处理选择ID
	 * 
	 * @author foss-qiaolifeng
	 * @date 2013-7-22 上午10:16:23
	 * @param selectedIds
	 */
	private void toDoSelectedIds(
			ClassifiedIncomeQueryDto classifiedIncomeQueryDto) {

		// 如果选择的ID为空，提示输入参数错误
		if (StringUtils.isEmpty(classifiedIncomeQueryDto.getSelectedIds())) {
			throw new BusinessException("没有选中的信息");
		}

		// 按“,”分割选中Id字串，生成ID的list
		String[] idArray = classifiedIncomeQueryDto.getSelectedIds()
				.split(",");
		List<String> list = new ArrayList<String>();
		for (int i = 0; i < idArray.length; i++) {
			list.add(idArray[i].trim());
		}
		classifiedIncomeQueryDto.setSelectedIdList(list);

	}
	/**
	 * 导出数据
	 */
	@Override
	public ExportResource exportClassifiedIncomeList(
			ClassifiedIncomeQueryDto entity) {

		 if (null == entity) {
			    entity = new ClassifiedIncomeQueryDto();
		    }
		// 根据传入对象查询符合条件所有重分类基础信息.
		List<ClassifiedIncomeEntity> list = classifiedIncomeDao.queryClassifiedIncomeByCondition(
				entity, NumberConstants.NUMERAL_ZORE, Integer.MAX_VALUE);
		// 集合验证
		if (null == list) {
			// 定义一个集合
			list = new ArrayList<ClassifiedIncomeEntity>();
		}
		// 定义集合
		List<List<String>> resultList = new ArrayList<List<String>>();
		// 迭代循环
		for (ClassifiedIncomeEntity classifiedIncome : list) {
			// 实体信息封装到集合中
			List<String> result = exportInfoList(classifiedIncome);
			// 存放到集合
			resultList.add(result);
		}
		// 导出对象创建
		ExportResource sheet = new ExportResource();
		// 设置Excel表头
		sheet.setHeads(ComnConst.CLASSIFIED_INCOME_TITLE);
		// 设置导出数据
		sheet.setRowList(resultList);
		return sheet;
	
	}

	/**
	 * 把查询结果转换成导出集合
	 */
	
	private List<String> exportInfoList(ClassifiedIncomeEntity entity) {
		SimpleDateFormat sdf =   new SimpleDateFormat( " yyyy-MM-dd HH:mm:ss " );
		// 定义一个集合
		List<String> result = new ArrayList<String>();
		// 产品类型名称
		result.add(entity.getProductTypeName());
		// 产品类型编码
		result.add(entity.getProductTypeCode());
		// 所属子公司名称.
		result.add(entity.getOwendSubsidiaryName());
		// 所属子公司编码.
		result.add(entity.getOwendSubsidiaryCode());
		//6%比例
		result.add(entity.getSixPercent());
		//11%比例
		result.add(entity.getElevenPercent());
		//开始时间
		result.add(sdf.format(entity.getCreateTime()));		
		//修改时间
		result.add(sdf.format(entity.getModifyTime()));
		//是否有效
		result.add(entity.getActive());
		//返回集合
		return result;
	}
}




