package com.deppon.foss.module.base.baseinfo.server.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.base.util.define.BizTypeConstants;
import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IExpressPartSalesDeptDao;
import com.deppon.foss.module.base.baseinfo.api.server.service.IAdministrativeRegionsService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IEmployeeService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IExpressCityService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IExpressPartSalesDeptService;
import com.deppon.foss.module.base.baseinfo.api.server.service.complex.IOrgAdministrativeInfoComplexService;
import com.deppon.foss.module.base.baseinfo.api.server.service.esb.ISyncExpressPartAndSalesDeptService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.AdministrativeRegionsEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.EmployeeEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.ExpressCityEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.ExpressPartSalesDeptEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.ExpressPartSalesDeptQueryDto;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.ExpressPartSalesDeptResultDto;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.ExpressSaleDepartmentResultDto;
import com.deppon.foss.module.base.common.api.server.service.IBusinessLockService;
import com.deppon.foss.module.base.common.api.shared.define.MutexElementType;
import com.deppon.foss.module.base.common.api.shared.dto.MutexElement;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryValueConstants;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

/**
 * 快递点部营业部映射关系Service实现
 * 
 * @author foss-qiaolifeng
 * @date 2013-7-25 上午10:14:43
 */
public class ExpressPartSalesDeptService implements
		IExpressPartSalesDeptService {

	/**
	 * 日志打印对象声明
	 */
	private static final Logger log = Logger
			.getLogger(ExpressPartSalesDeptService.class);

	/**
	 * 快递点部营业部映射关系Dao
	 */
	private IExpressPartSalesDeptDao expressPartSalesDeptDao;

	/**
	 * 人员接口service
	 */
	private IEmployeeService employeeService;

	/**
	 * 行政组织service
	 */
	private IAdministrativeRegionsService administrativeRegionsService;

	/**
	 * 网点组织service
	 */
	private IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService;
	
	/**
	 * 同步快递点部营业部映射信息
	 */
	private ISyncExpressPartAndSalesDeptService syncExpressPartAndSalesDeptService;
	
	/**
	 * 快递代理/试点城市映射关系service
	 */
	private IExpressCityService expressCityService;
	
	/**
	 * 业务锁SERVICE
	 */
	private IBusinessLockService businessLockService;
	
	public void setBusinessLockService(IBusinessLockService businessLockService) {
		this.businessLockService = businessLockService;
	}

	public IExpressCityService getExpressCityService() {
		return expressCityService;
	}

	public void setExpressCityService(IExpressCityService expressCityService) {
		this.expressCityService = expressCityService;
	}

	public ISyncExpressPartAndSalesDeptService getSyncExpressPartAndSalesDeptService() {
		return syncExpressPartAndSalesDeptService;
	}

	public void setSyncExpressPartAndSalesDeptService(
			ISyncExpressPartAndSalesDeptService syncExpressPartAndSalesDeptService) {
		this.syncExpressPartAndSalesDeptService = syncExpressPartAndSalesDeptService;
	}

	public IOrgAdministrativeInfoComplexService getOrgAdministrativeInfoComplexService() {
		return orgAdministrativeInfoComplexService;
	}

	public void setOrgAdministrativeInfoComplexService(
			IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService) {
		this.orgAdministrativeInfoComplexService = orgAdministrativeInfoComplexService;
	}

	public IAdministrativeRegionsService getAdministrativeRegionsService() {
		return administrativeRegionsService;
	}

	public void setAdministrativeRegionsService(
			IAdministrativeRegionsService administrativeRegionsService) {
		this.administrativeRegionsService = administrativeRegionsService;
	}

	public IEmployeeService getEmployeeService() {
		return employeeService;
	}

	public void setEmployeeService(IEmployeeService employeeService) {
		this.employeeService = employeeService;
	}

	public IExpressPartSalesDeptDao getExpressPartSalesDeptDao() {
		return expressPartSalesDeptDao;
	}

	public void setExpressPartSalesDeptDao(
			IExpressPartSalesDeptDao expressPartSalesDeptDao) {
		this.expressPartSalesDeptDao = expressPartSalesDeptDao;
	}

	/** 
	 * 根据快递点部名称查询快递点部营业部映射关系总条数
	 * @author foss-qiaolifeng
	 * @date 2013-8-27 下午2:33:35
	 * @param expressPartSalesDeptQueryDto
	 * @return
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.IExpressPartSalesDeptService#queryTotalByCondition(com.deppon.foss.module.base.baseinfo.api.shared.dto.ExpressPartSalesDeptQueryDto)
	 */
	@Override
	public long queryTotalByCondition(
			ExpressPartSalesDeptQueryDto expressPartSalesDeptQueryDto) {
		
		return expressPartSalesDeptDao.queryTotalByCondition(expressPartSalesDeptQueryDto);
	}
	
	/**
	 * 根据快递点部名称查询快递点部营业部映射关系
	 * 
	 * @author foss-qiaolifeng
	 * @date 2013-7-25 上午10:14:54
	 * @param expressPartSalesDeptQueryDto
	 * @return
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.IExpressPartSalesDeptService#queryExpressPartSalesDeptByExpressPartName(com.deppon.foss.module.base.baseinfo.api.shared.dto.ExpressPartSalesDeptQueryDto)
	 */
	@Override
	public List<ExpressPartSalesDeptResultDto> queryExpressPartSalesDeptByCondition(
			ExpressPartSalesDeptQueryDto expressPartSalesDeptQueryDto, int start, int limit) {

		// 验证参数
		if (expressPartSalesDeptQueryDto == null) {
			throw new BusinessException("查询快递点部营业部映射关系失败,快递点部名称参数异常");
		}
		// 开始日志
		log.info("begin queryExpressPartSalesDeptByCondition()...");

		// 生成返回结果
		List<ExpressPartSalesDeptResultDto> returnList = null;
		// 查询映射关系数据
		List<ExpressPartSalesDeptResultDto> expressPartSalesDeptResultDtoList = expressPartSalesDeptDao
				.queryExpressPartSalesDeptByCondition(expressPartSalesDeptQueryDto, start, limit);
		// 处理返回结果
		if (CollectionUtils.isNotEmpty(expressPartSalesDeptResultDtoList)) {
			returnList = new ArrayList<ExpressPartSalesDeptResultDto>();
			for (ExpressPartSalesDeptResultDto dto : expressPartSalesDeptResultDtoList) {

				// 调用方法，设置快递点部所属的快递大区信息
				List<OrgAdministrativeInfoEntity> resultList = orgAdministrativeInfoComplexService
						.queryOrgAdministrativeInfoUpByBizType(
								dto.getPartCode(),
								BizTypeConstants.EXPRESS_BIG_REGION);
				if (CollectionUtils.isNotEmpty(resultList)) {
					dto.setExpressBigRegionName(resultList.get(0).getName());// 快递大区名称
					dto.setExpressBigRegionCode(resultList.get(0).getCode());// 快递大区编码
				}

				// 调用方法，根据员工code设置员工name
				EmployeeEntity employeeEntityCreater = employeeService
						.querySimpleEmployeeByEmpCode(dto.getCreateUserCode());
				if(null != employeeEntityCreater){
					dto.setCreateUserName(employeeEntityCreater.getEmpName());
				}else{
					dto.setCreateUserName(dto.getCreateUserCode());
				}
				EmployeeEntity employeeEntityModifyer = employeeService
						.querySimpleEmployeeByEmpCode(dto.getModifyUserCode());
				if(null != employeeEntityModifyer){
					dto.setModifyUserName(employeeEntityModifyer.getEmpName());
				}else{
					dto.setModifyUserName(dto.getModifyUserCode());
				}
				
				// 将处理后结果加入到返回Dto
				returnList.add(dto);
			}
		}

		// 结束日志
		log.info("end queryExpressPartSalesDeptByCondition()...");
		return returnList;
	}


	/**
	 * 根据营业部编码和时间查询快递点部信息，时间为空时，查询当前时间的信息
	 * 
	 * @author foss-zhangjiheng
	 * @date 2013-7-25 上午10:06:34
	 * @param expressPartSalesDeptQueryDto
	 * @return
	 */

	public ExpressPartSalesDeptResultDto queryExpressPartSalesDeptBySalesCodeAndTime(
			String salesDeptCode, Date createTime) {
		// 判断时间是否为空，为空时设置为当前时间
		if (createTime == null) {
			createTime = new Date();
		}
		ExpressPartSalesDeptResultDto rtnDto = null;
		List<ExpressPartSalesDeptResultDto> list = expressPartSalesDeptDao
				.queryExpressPartSalesDeptBySalesCodeAndTime(salesDeptCode,
						createTime);
		if(CollectionUtils.isEmpty(list)){
			//根据营业部编码查询是否存在有效的快递代理/试点城市
			List<ExpressCityEntity> expressCityList = expressCityService.queryExpressCityByOrgCode(salesDeptCode, FossConstants.ACTIVE);
			//如果该营业部只配置了快递代理城市类型
			if(CollectionUtils.isNotEmpty(expressCityList)){
				if(expressCityList.size()==1){
					for(ExpressCityEntity entity:expressCityList){
						if(DictionaryValueConstants.EXPRESS_CITY_TYPE_LDP.equals(entity.getType())){
							
							//生成返回Dto，并赋值
							rtnDto = new ExpressPartSalesDeptResultDto();
							rtnDto.setSalesDeptCode(salesDeptCode);
							//rtnDto.setSalesDeptName(salesDeptName);
						}
					}
				}
			}else{
				//生成返回Dto，并赋值
				rtnDto = new ExpressPartSalesDeptResultDto();
				rtnDto.setSalesDeptCode(salesDeptCode);
			}
			return rtnDto;
		}else{
			return list.get(0);
		} 
		
	}

	/**
	 * 根据快递点部编码查询快递点部营业部映射关系
	 * 
	 * @author foss-qiaolifeng
	 * @date 2013-7-26 上午11:04:10
	 * @param expressPartSalesDeptQueryDto
	 * @return
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.IExpressPartSalesDeptService#queryExpressPartSalesDeptByExpressPartCode(com.deppon.foss.module.base.baseinfo.api.shared.dto.ExpressPartSalesDeptQueryDto)
	 */
	@Override
	public List<ExpressPartSalesDeptResultDto> queryExpressPartSalesDeptByExpressPartCode(
			ExpressPartSalesDeptQueryDto expressPartSalesDeptQueryDto) {

		// 验证参数
		if (expressPartSalesDeptQueryDto == null
				|| StringUtils.isEmpty(expressPartSalesDeptQueryDto
						.getExpressPartCode())) {
			throw new BusinessException("查看快递点部营业部映射信息失败,快递点部编码参数异常");
		}
		// 开始日志
		log.info("begin queryExpressPartSalesDeptByExpressPartCode()...,快递点部名称参数："
				+ expressPartSalesDeptQueryDto.getExpressPartCode());

		// 设置参数:有效
		expressPartSalesDeptQueryDto.setActive(FossConstants.ACTIVE);
		// 生成返回结果
		List<ExpressPartSalesDeptResultDto> returnList = null;
		// 查询映射关系数据
		List<ExpressPartSalesDeptResultDto> expressPartSalesDeptResultDtoList = expressPartSalesDeptDao
				.queryExpressPartSalesDeptByExpressPartCode(expressPartSalesDeptQueryDto);
		// 处理返回结果
		if (CollectionUtils.isNotEmpty(expressPartSalesDeptResultDtoList)) {
			returnList = new ArrayList<ExpressPartSalesDeptResultDto>();
			for (ExpressPartSalesDeptResultDto dto : expressPartSalesDeptResultDtoList) {

				// 调用方法，设置快递点部所属的快递大区信息
				List<OrgAdministrativeInfoEntity> resultList = orgAdministrativeInfoComplexService
						.queryOrgAdministrativeInfoUpByBizType(
								dto.getPartCode(),
								BizTypeConstants.EXPRESS_BIG_REGION);
				if (CollectionUtils.isNotEmpty(resultList)) {
					dto.setExpressBigRegionName(resultList.get(0).getName());// 快递大区名称
					dto.setExpressBigRegionCode(resultList.get(0).getCode());// 快递大区编码
				}

				// 将处理后结果加入到返回Dto
				returnList.add(dto);
			}
		}

		// 结束日志
		log.info("end queryExpressPartSalesDeptByExpressPartCode()...");
		return returnList;
	}

	/**
	 * 根据快递点部编码查询快递点部营业部映射关系
	 * 
	 * @author foss-qiaolifeng
	 * @date 2013-7-26 上午11:04:14
	 * @param expressPartSalesDeptQueryDto
	 * @return
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.IExpressPartSalesDeptService#querySaleDepartmentResultDtoByExpressPartCode(com.deppon.foss.module.base.baseinfo.api.shared.dto.ExpressPartSalesDeptQueryDto)
	 */
	@Override
	public List<ExpressSaleDepartmentResultDto> querySaleDepartmentResultDtoBySalesDeptCodeList(
			ExpressPartSalesDeptQueryDto expressPartSalesDeptQueryDto) {

		// 验证参数
		if (expressPartSalesDeptQueryDto == null
				|| CollectionUtils.isEmpty(expressPartSalesDeptQueryDto
						.getSelectedCodeList())) {
			throw new BusinessException("查看快递点部营业部映射信息失败,该快递点部没有配置的营业部信息");
		}
		// 开始日志
		log.info("begin querySaleDepartmentResultDtoByExpressPartCode()...");

		// 生成返回营业部列表
		List<ExpressSaleDepartmentResultDto> returnDto = null;
		// 查询营业部信息
		List<ExpressSaleDepartmentResultDto> saleDepartmentResultDtoList = expressPartSalesDeptDao
				.querySaleDepartmentResultDtoBySalesDeptCodeList(expressPartSalesDeptQueryDto);
		if (CollectionUtils.isNotEmpty(saleDepartmentResultDtoList)) {
			returnDto = new ArrayList<ExpressSaleDepartmentResultDto>();
			for (ExpressSaleDepartmentResultDto dto : saleDepartmentResultDtoList) {
				AdministrativeRegionsEntity administrativeRegionsEntity = administrativeRegionsService
						.queryAdministrativeRegionsByCode(dto.getCityCode());
				if (administrativeRegionsEntity != null) {
					dto.setCityName(administrativeRegionsEntity.getName());// 设置城市名称
					dto.setProvName(administrativeRegionsEntity
							.getParentDistrictName());// 设置省份名称
				}
				// 加入返回列表
				returnDto.add(dto);
			}
		}
		// 结束日志
		log.info("end querySaleDepartmentResultDtoByExpressPartCode()...");
		return returnDto;
	}

	/**
	 * 根据营业部条件查部营业部信息,多条件模糊查询
	 * 
	 * @author foss-qiaolifeng
	 * @date 2013-7-26 下午5:20:06
	 * @param expressPartSalesDeptQueryDto
	 * @return
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.IExpressPartSalesDeptService#querySaleDepartmentResultDtoByCondition(com.deppon.foss.module.base.baseinfo.api.shared.dto.ExpressPartSalesDeptQueryDto)
	 */
	@Override
	public List<ExpressSaleDepartmentResultDto> querySaleDepartmentResultDtoByCondition(
			ExpressPartSalesDeptQueryDto expressPartSalesDeptQueryDto) {

		// 验证参数
		if (expressPartSalesDeptQueryDto == null) {
			throw new BusinessException("查询营业部信息失败,参数异常");
		}
		// 开始日志
		log.info("begin querySaleDepartmentResultDtoByCondition()...");

		// 设置参数
		expressPartSalesDeptQueryDto.setActive(FossConstants.ACTIVE);

		// 查询营业部信息
		List<ExpressSaleDepartmentResultDto> saleDepartmentResultDtoList = expressPartSalesDeptDao
				.querySaleDepartmentResultDtoByCondition(expressPartSalesDeptQueryDto);
		// 生成返回营业部列表
		List<ExpressSaleDepartmentResultDto> returnDto = null;
		if (CollectionUtils.isNotEmpty(saleDepartmentResultDtoList)) {
			returnDto = new ArrayList<ExpressSaleDepartmentResultDto>();
			for (ExpressSaleDepartmentResultDto dto : saleDepartmentResultDtoList) {
				AdministrativeRegionsEntity administrativeRegionsEntity = administrativeRegionsService
						.queryAdministrativeRegionsByCode(dto.getCityCode());
				if (administrativeRegionsEntity != null) {
					dto.setCityName(administrativeRegionsEntity.getName());// 设置城市名称
					dto.setProvName(administrativeRegionsEntity
							.getParentDistrictName());// 设置省份名称
				}
				// 加入返回列表
				returnDto.add(dto);
			}
		}

		// 结束日志
		log.info("end querySaleDepartmentResultDtoByCondition()...");
		return returnDto;
	}

	/**
	 * 新增快递点部营业部映射信息
	 * 
	 * @author foss-qiaolifeng
	 * @date 2013-7-29 下午1:40:30
	 * @param expressPartSalesDeptQueryDto
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.IExpressPartSalesDeptService#addExpressPartSalesDept(com.deppon.foss.module.base.baseinfo.api.shared.dto.ExpressPartSalesDeptQueryDto)
	 */
	@Override
	@Transactional
	public void addExpressPartSalesDept(
			ExpressPartSalesDeptQueryDto expressPartSalesDeptQueryDto,
			CurrentInfo currentInfo) {
		
		// 验证参数
		if (expressPartSalesDeptQueryDto == null
				|| expressPartSalesDeptQueryDto
						.getExpressPartSalesDeptResultDto() == null
				|| CollectionUtils.isEmpty(expressPartSalesDeptQueryDto
						.getSaleDepartmentResultDtoList())) {
			throw new BusinessException("新增快递点部营业部映射信息失败,参数异常");
		}
		// 开始日志
		log.info("begin addExpressPartSalesDept()...,");

		//当前时间
		Date date = new Date();
		//生成同步快递点部营业部信息
		List<ExpressPartSalesDeptEntity> sysList = new ArrayList<ExpressPartSalesDeptEntity>();
		
		// 循环处理营业部信息
		for (ExpressSaleDepartmentResultDto salesDeptdto : expressPartSalesDeptQueryDto
				.getSaleDepartmentResultDtoList()) {
			//业务锁 273311
			List<MutexElement> mutexes = new ArrayList<MutexElement>();
			MutexElement mutex = new MutexElement(String.valueOf(salesDeptdto.getCode()), "EXPRESSPART_DEPT_CODE",
					MutexElementType.EXPRESSPART_DEPT_CODE);
			mutexes.add(mutex);
			log.info("开始加锁：" + mutex.getBusinessNo());
			boolean result = businessLockService.lock(mutexes,
					NumberConstants.ZERO);
			if(result){
				log.info("成功加锁：" + mutex.getBusinessNo());
			}else {
				throw new BusinessException("选中营业部存在并发处理，请稍后再试");
			}
			//根据营业部编码、快递点部编码、有效状态查询映射信息
			List<ExpressPartSalesDeptResultDto> oldEntityList = expressPartSalesDeptDao.queryResultDtoByExpressPartCodeAndSalesDeptCode(
					null, salesDeptdto.getCode(), FossConstants.ACTIVE);
			if(CollectionUtils.isNotEmpty(oldEntityList)){
				for(ExpressPartSalesDeptResultDto oldEntity:oldEntityList){
					//如果快递点部相等
					if(oldEntity.getPartCode().equals(expressPartSalesDeptQueryDto.getExpressPartSalesDeptResultDto().getPartCode())){
						throw new BusinessException("快递点部:"+oldEntity.getPartName()+" 与营业部:"+oldEntity.getSalesDeptName()+"的映射关系已存在，不允许保存");
					}else{
						throw new BusinessException("营业部:"+oldEntity.getSalesDeptName()+" 已有映射快递点部:"+oldEntity.getPartName()+"的映射关系已存在，不允许保存");
					}
				}
			}
			
			// 新建实体并保存
			ExpressPartSalesDeptEntity expressPartSalesDeptEntity = new ExpressPartSalesDeptEntity();
			expressPartSalesDeptEntity.setId(UUIDUtils.getUUID());// ID
			expressPartSalesDeptEntity.setPartCode(expressPartSalesDeptQueryDto
					.getExpressPartSalesDeptResultDto().getPartCode());// 快递点部编码
			expressPartSalesDeptEntity.setSalesDeptCode(salesDeptdto.getCode());// 营业部编码
			expressPartSalesDeptEntity.setCreateUserCode(currentInfo
					.getEmpCode());// 创建人
			expressPartSalesDeptEntity.setModifyUserCode(currentInfo
					.getEmpCode());// 修改人
			expressPartSalesDeptEntity.setCreateTime(date);// 创建时间
			expressPartSalesDeptEntity.setModifyTime(new Date(NumberConstants.ENDTIME));// 修改时间
			expressPartSalesDeptEntity.setBeginTime(date);//开始时间
			expressPartSalesDeptEntity.setEndTime(new Date(
					NumberConstants.ENDTIME));//结束时间
			expressPartSalesDeptEntity.setActive(FossConstants.ACTIVE);// 有效
			expressPartSalesDeptEntity.setVersionNo(date.getTime());// 版本号
			
			try{
				// 保存数据库
				expressPartSalesDeptDao
						.addExpressPartSalesDept(expressPartSalesDeptEntity);
				
				//加入到同步列表
				sysList.add(expressPartSalesDeptEntity);
			}finally{
				log.info("开始解锁：" + mutex.getBusinessNo());
				businessLockService.unlock(mutexes);
				log.info("完成解锁：" + mutex.getBusinessNo());
			}
			

		}
		
		//如果同步列表数据不为空，调用同步接口
		if(CollectionUtils.isNotEmpty(sysList)){
			//调用同步接口
			syncExpressPartAndSalesDeptService.syncExpressPartAndDeptToCrm(sysList);
		}
		// 结束日志
		log.info("end addExpressPartSalesDept()...");
	}

	/**
	 * 同步ecs数据
	 */
	@Override
	@Transactional
	public void sysAddExpressPartSalesDept(
			ExpressPartSalesDeptEntity expressPartSalesDeptEntity) {
		
		// 验证参数
		if (expressPartSalesDeptEntity == null) {
			throw new BusinessException("同步数据为空");
		}
		// 开始日志
		log.info("begin addExpressPartSalesDept()...,");
			//根据营业部编码、快递点部编码、有效状态查询映射信息
			List<ExpressPartSalesDeptResultDto> oldEntityList = expressPartSalesDeptDao.queryResultDtoByExpressPartCodeAndSalesDeptCode(
					null, expressPartSalesDeptEntity.getSalesDeptCode(), FossConstants.ACTIVE);
			if(CollectionUtils.isNotEmpty(oldEntityList)){
				for(ExpressPartSalesDeptResultDto oldEntity:oldEntityList){
					//如果快递点部相等
					if(oldEntity.getPartCode().equals(expressPartSalesDeptEntity.getPartCode())){
						throw new BusinessException("快递点部:"+oldEntity.getPartName()+" 与营业部:"+oldEntity.getSalesDeptName()+"的映射关系已存在，不允许保存");
					}else{
						throw new BusinessException("营业部:"+oldEntity.getSalesDeptName()+" 已有映射快递点部:"+oldEntity.getPartName()+"的映射关系已存在，不允许保存");
					}
				}
			}
				// 保存数据库
					expressPartSalesDeptDao.addExpressPartSalesDept(expressPartSalesDeptEntity);
			// 结束日志
			log.info("end addExpressPartSalesDept()...");
		}
	/**
	 * 修改快递点部营业部映射信息
	 * 
	 * @author foss-qiaolifeng
	 * @date 2013-7-29 下午1:58:22
	 * @param expressPartSalesDeptQueryDto
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.IExpressPartSalesDeptService#updateExpressPartSalesDept(com.deppon.foss.module.base.baseinfo.api.shared.dto.ExpressPartSalesDeptQueryDto)
	 */
	@Override
	@Transactional
	public void updateExpressPartSalesDept(
			ExpressPartSalesDeptQueryDto expressPartSalesDeptQueryDto,
			CurrentInfo currentInfo) {

		// 开始日志
		log.info("begin updateExpressPartSalesDept()...,");

		Date date = new Date();
		toDoSelectedIds(expressPartSalesDeptQueryDto);
		//生成同步快递点部营业部信息
		List<ExpressPartSalesDeptEntity> sysList = new ArrayList<ExpressPartSalesDeptEntity>();
		
		//1、根据ID查询营业部的原快递点部配置并作废
		List<ExpressPartSalesDeptEntity> toDelEntityList = expressPartSalesDeptDao.queryInfosByIds(expressPartSalesDeptQueryDto.getSelectedIdList());
		if(CollectionUtils.isNotEmpty(toDelEntityList)){
			//如果该数据为无效状态
			if(!FossConstants.ACTIVE.equals(toDelEntityList.get(0).getActive())){
				throw new BusinessException("该映射关系已无效，不允许修改");
			}
			if(expressPartSalesDeptQueryDto.getSalesDeptCode().equals(toDelEntityList.get(0).getSalesDeptCode())
					&&expressPartSalesDeptQueryDto.getExpressPartCode().equals(toDelEntityList.get(0).getPartCode())){
				throw new BusinessException("该映射关系没做任何修改操作");
			}
			
			//作废原数据
			ExpressPartSalesDeptEntity oldEntityEntity = toDelEntityList.get(0);
			oldEntityEntity.setActive(FossConstants.INACTIVE);//无效
			oldEntityEntity.setModifyUserCode(currentInfo.getEmpCode());//修改人
			oldEntityEntity.setModifyTime(date);//修改时间
			oldEntityEntity.setEndTime(date);//结束时间
			oldEntityEntity.setVersionNo(date.getTime());//版本号
			// 作废数据
			expressPartSalesDeptDao.updateExpressPartSalesDeptByPartCode(oldEntityEntity);
			//加入到带同步接口
			sysList.add(oldEntityEntity);
			
			//2、新增新的快递点部营业部配置
			ExpressPartSalesDeptEntity expressPartSalesDeptEntity = new ExpressPartSalesDeptEntity();
			expressPartSalesDeptEntity.setId(UUIDUtils.getUUID());// ID
			expressPartSalesDeptEntity.setPartCode(expressPartSalesDeptQueryDto.getExpressPartCode());// 快递点部编码
			expressPartSalesDeptEntity.setSalesDeptCode(expressPartSalesDeptQueryDto.getSalesDeptCode());// 营业部编码
			expressPartSalesDeptEntity.setCreateUserCode(currentInfo
					.getEmpCode());// 创建人
			expressPartSalesDeptEntity.setModifyUserCode(currentInfo
					.getEmpCode());// 修改人
			expressPartSalesDeptEntity.setCreateTime(date);// 创建时间
			expressPartSalesDeptEntity.setModifyTime(new Date(NumberConstants.ENDTIME));// 修改时间
			expressPartSalesDeptEntity.setBeginTime(date);//开始时间
			expressPartSalesDeptEntity.setEndTime(new Date(
					NumberConstants.ENDTIME));//结束时间
			expressPartSalesDeptEntity.setActive(FossConstants.ACTIVE);// 有效
			expressPartSalesDeptEntity.setVersionNo(date.getTime());// 版本号
			// 修改数据
			// 保存数据库
			expressPartSalesDeptDao.addExpressPartSalesDept(expressPartSalesDeptEntity);
			//加入到带同步接口
			sysList.add(expressPartSalesDeptEntity);
		}		
		
		//如果同步列表数据不为空，调用同步接口
		if(CollectionUtils.isNotEmpty(sysList)){
			//调用同步接口
			syncExpressPartAndSalesDeptService.syncExpressPartAndDeptToCrm(sysList);
		}
		
		// 结束日志
		log.info("end updateExpressPartSalesDept()...");
	}

	@Override
	@Transactional
	public void sysUpdateExpressPartSalesDept(ExpressPartSalesDeptEntity expressPartSalesDeptEntity) {

		// 开始日志
		log.info("begin updateExpressPartSalesDept()...,");
		//1、根据营业部编码查询营业部的原快递点部配置并作废
		List<ExpressPartSalesDeptResultDto> toDelEntityList = 
				expressPartSalesDeptDao.queryResultDtoByExpressPartCodeAndSalesDeptCode(null,expressPartSalesDeptEntity.getSalesDeptCode(),FossConstants.ACTIVE);
		if(CollectionUtils.isNotEmpty(toDelEntityList)){
			//如果该数据为无效状态
			if(!FossConstants.ACTIVE.equals(toDelEntityList.get(0).getActive())){
				throw new BusinessException("该映射关系已无效，不允许修改");
			}
			if(expressPartSalesDeptEntity.getSalesDeptCode().equals(toDelEntityList.get(0).getSalesDeptCode())
					&&expressPartSalesDeptEntity.getPartCode().equals(toDelEntityList.get(0).getPartCode())){
				throw new BusinessException("该映射关系没做任何修改操作");
			}
			
			//作废原数据
			ExpressPartSalesDeptEntity oldEntityEntity = toDelEntityList.get(0);
			oldEntityEntity.setActive(FossConstants.INACTIVE);//无效
			oldEntityEntity.setModifyUserCode(expressPartSalesDeptEntity.getCreateUserCode());//修改人
			oldEntityEntity.setModifyTime(expressPartSalesDeptEntity.getModifyTime());//修改时间
			// 作废数据
			expressPartSalesDeptDao.updateExpressPartSalesDeptByPartCode(oldEntityEntity);
			
			//2、新增新的快递点部营业部配置
			ExpressPartSalesDeptEntity expressPartSalesDeptEntityAdd = new ExpressPartSalesDeptEntity();
			expressPartSalesDeptEntityAdd.setId(expressPartSalesDeptEntity.getId());// ID
			expressPartSalesDeptEntityAdd.setPartCode(expressPartSalesDeptEntity.getPartCode());// 快递点部编码
			expressPartSalesDeptEntityAdd.setSalesDeptCode(expressPartSalesDeptEntity.getSalesDeptCode());// 营业部编码
			expressPartSalesDeptEntityAdd.setCreateUserCode(expressPartSalesDeptEntity.getCreateUserCode());// 创建人
			expressPartSalesDeptEntityAdd.setModifyUserCode(expressPartSalesDeptEntity.getModifyUserCode());// 修改人
			expressPartSalesDeptEntityAdd.setCreateTime(expressPartSalesDeptEntity.getCreateTime());// 创建时间
			expressPartSalesDeptEntityAdd.setModifyTime(expressPartSalesDeptEntity.getModifyTime());// 修改时间
			expressPartSalesDeptEntityAdd.setBeginTime(expressPartSalesDeptEntity.getBeginTime());//开始时间
			expressPartSalesDeptEntityAdd.setEndTime(expressPartSalesDeptEntity.getModifyTime());//结束时间
			expressPartSalesDeptEntityAdd.setActive(FossConstants.ACTIVE);// 有效
			expressPartSalesDeptEntityAdd.setVersionNo(expressPartSalesDeptEntity.getVersionNo());// 版本号
			// 修改数据
			// 保存数据库
			expressPartSalesDeptDao.addExpressPartSalesDept(expressPartSalesDeptEntityAdd);
			
		}		
		
		// 结束日志
		log.info("end updateExpressPartSalesDept()...");
	}
	/**
	 * 删除快递点部营业部映射信息
	 * 
	 * @author foss-qiaolifeng
	 * @date 2013-7-29 下午1:40:46
	 * @param expressPartSalesDeptQueryDto
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.IExpressPartSalesDeptService#deleteExpressPartSalesDept(com.deppon.foss.module.base.baseinfo.api.shared.dto.ExpressPartSalesDeptQueryDto)
	 */
	@Override
	@Transactional
	public void deleteExpressPartSalesDept(
			ExpressPartSalesDeptQueryDto expressPartSalesDeptQueryDto,
			CurrentInfo currentInfo) {

		// 开始日志
		log.info("begin deleteExpressPartSalesDept()...,");

		// 1、处理选中ID
		toDoSelectedIds(expressPartSalesDeptQueryDto);
		Date date = new Date();
		//生成同步快递点部营业部信息
		List<ExpressPartSalesDeptEntity> sysList = new ArrayList<ExpressPartSalesDeptEntity>();
		
		//根据id查询所有待作废的映射信息
		List<ExpressPartSalesDeptEntity> toDelEntityList = expressPartSalesDeptDao.queryInfosByIds(expressPartSalesDeptQueryDto.getSelectedIdList());
		if(CollectionUtils.isNotEmpty(toDelEntityList)){
			//循化处理待作废的映射数据
			for(ExpressPartSalesDeptEntity toDelEntity:toDelEntityList){
				//如果非有效状态，不可作废
				if(!FossConstants.ACTIVE.equals(toDelEntity.getActive())){
					throw new BusinessException("只有有效的映射信息才可作废");
				}
				
				//作废原数据
				toDelEntity.setActive(FossConstants.INACTIVE);//无效
				toDelEntity.setModifyUserCode(currentInfo.getEmpCode());//修改人
				toDelEntity.setModifyTime(date);//修改时间
				toDelEntity.setEndTime(date);//结束时间
				toDelEntity.setVersionNo(date.getTime());//版本号
				// 作废数据
				expressPartSalesDeptDao.updateExpressPartSalesDeptByPartCode(toDelEntity);
				//加入到带同步接口
				sysList.add(toDelEntity);
				
				
			}
		}
		
		//如果同步列表数据不为空，调用同步接口
		if(CollectionUtils.isNotEmpty(sysList)){
			//调用同步接口
			syncExpressPartAndSalesDeptService.syncExpressPartAndDeptToCrm(sysList);
		}
		
		// 结束日志
		log.info("end deleteExpressPartSalesDept()...");
	}
	@Override
	@Transactional
	public void sysDeleteExpressPartSalesDept(ExpressPartSalesDeptEntity expressPartSalesDeptEntity) {

		// 开始日志
		log.info("begin deleteExpressPartSalesDept()...,");
		//根据营业部编码code查询所有待作废的映射信息
		List<ExpressPartSalesDeptResultDto> toDelEntityList = 
				expressPartSalesDeptDao.queryResultDtoByExpressPartCodeAndSalesDeptCode(null,expressPartSalesDeptEntity.getSalesDeptCode(),FossConstants.ACTIVE);
		if(CollectionUtils.isNotEmpty(toDelEntityList)){
			//循化处理待作废的映射数据
			for(ExpressPartSalesDeptEntity toDelEntity:toDelEntityList){
				//如果非有效状态，不可作废
				if(!FossConstants.ACTIVE.equals(toDelEntity.getActive())){
					throw new BusinessException("只有有效的映射信息才可作废");
				}
				
				//作废原数据
				toDelEntity.setActive(FossConstants.INACTIVE);//无效
				toDelEntity.setModifyUserCode(expressPartSalesDeptEntity.getModifyUserCode());//修改人
				toDelEntity.setModifyTime(expressPartSalesDeptEntity.getModifyTime());//修改时间
				// 作废数据
				expressPartSalesDeptDao.updateExpressPartSalesDeptByPartCode(toDelEntity);
			}
		}
		// 结束日志
		log.info("end deleteExpressPartSalesDept()...");
	}

	/**
	 * 根据快递点部编码获取快递大区信息
	 * 
	 * @author foss-qiaolifeng
	 * @date 2013-7-30 上午10:31:55
	 * @param expressPartSalesDeptQueryDto
	 * @return
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.IExpressPartSalesDeptService#getExpressPartBigRegionByPartCode(com.deppon.foss.module.base.baseinfo.api.shared.dto.ExpressPartSalesDeptQueryDto)
	 */
	@Override
	public OrgAdministrativeInfoEntity getExpressPartBigRegionByPartCode(
			ExpressPartSalesDeptQueryDto expressPartSalesDeptQueryDto) {

		if (expressPartSalesDeptQueryDto == null
				|| StringUtils.isEmpty(expressPartSalesDeptQueryDto
						.getExpressPartCode())) {
			return null;
		}

		// 根据快递点部编码和快点大区类型，获取快递大区信息
		List<OrgAdministrativeInfoEntity> resultList = orgAdministrativeInfoComplexService
				.queryOrgAdministrativeInfoUpByBizType(
						expressPartSalesDeptQueryDto.getExpressPartCode(),
						BizTypeConstants.EXPRESS_BIG_REGION);

		// 如果存在，返回数据
		if (CollectionUtils.isNotEmpty(resultList)) {
			return resultList.get(0);
		}
		return null;
	}

	/** 
	 *  立即激活
	 * @author foss-qiaolifeng
	 * @date 2013-8-28 下午4:37:55
	 * @param expressPartSalesDeptQueryDto
	 * @param currentInfo
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.IExpressPartSalesDeptService#activeImmediatelyExpressPartSalesDept(com.deppon.foss.module.base.baseinfo.api.shared.dto.ExpressPartSalesDeptQueryDto, com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo)
	 */
	@Override
	public void activeImmediatelyExpressPartSalesDept(
			ExpressPartSalesDeptQueryDto expressPartSalesDeptQueryDto,
			CurrentInfo currentInfo) {
		
		// 开始日志
		log.info("begin activeImmediatelyExpressPartSalesDept()...,");

		//获取当前时间
		Date date = new Date();
		//生成同步快递点部营业部信息
		List<ExpressPartSalesDeptEntity> sysList = new ArrayList<ExpressPartSalesDeptEntity>();
		
		// 1、处理选中ID
		toDoSelectedIds(expressPartSalesDeptQueryDto);
		//根据id查询所有待激活的映射信息
		List<ExpressPartSalesDeptEntity> toActiveEntityList = expressPartSalesDeptDao.queryInfosByIds(expressPartSalesDeptQueryDto.getSelectedIdList());
		if(CollectionUtils.isNotEmpty(toActiveEntityList)){
			//循化处理待激活的映射数据
			for(ExpressPartSalesDeptEntity toActiveEntity:toActiveEntityList){
				//如果为激活状态，不能再次激活
				if(FossConstants.ACTIVE.equals(toActiveEntity.getActive())){
					throw new BusinessException("只有未激活的映射关系才可以激活");						
				}
				
				//根据营业部编码查询激活状态的营业部快递点部映射配置
				List<ExpressPartSalesDeptEntity>  activedEntityList = expressPartSalesDeptDao.queryInfosBySalesCode(toActiveEntity.getSalesDeptCode(), FossConstants.ACTIVE);
				if(CollectionUtils.isNotEmpty(activedEntityList)){
					for(ExpressPartSalesDeptEntity activedEntity:activedEntityList){
						//如果该激活数据的结束时间大于当前时间，将该激活数据的结束世间值为当前时间
						if(date.before(activedEntity.getEndTime())){
							activedEntity.setEndTime(date);
							//修改激活数据的结束时间
							expressPartSalesDeptDao.updateExpressPartSalesDeptByPartCode(activedEntity);
							
							//设置未删除状态，并加入到待同步列表
							activedEntity.setActive(FossConstants.INACTIVE);
							sysList.add(activedEntity);
						}
					}
				}
				
				//同时将待激活数据进行激活
				toActiveEntity.setActive(FossConstants.ACTIVE);//有效
				toActiveEntity.setVersionNo(date.getTime());//版本号
				toActiveEntity.setModifyTime(date);//修改时间
				toActiveEntity.setModifyUserCode(currentInfo.getEmpCode());//修改人编码
				toActiveEntity.setBeginTime(date);//开始时间
				expressPartSalesDeptDao.updateExpressPartSalesDeptByPartCode(toActiveEntity);
				//加入到同步列表
				sysList.add(toActiveEntity);
			}
		}
		
		//如果同步列表数据不为空，调用同步接口
		if(CollectionUtils.isNotEmpty(sysList)){
			//调用同步接口
			syncExpressPartAndSalesDeptService.syncExpressPartAndDeptToCrm(sysList);
		}
		
		// 结束日志
		log.info("end activeImmediatelyExpressPartSalesDept()...");
		
	}

	/** 
	 *  立即中止
	 * @author foss-qiaolifeng
	 * @date 2013-8-28 下午4:38:00
	 * @param expressPartSalesDeptQueryDto
	 * @param currentInfo
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.IExpressPartSalesDeptService#inActiveImmediatelyExpressPartSalesDept(com.deppon.foss.module.base.baseinfo.api.shared.dto.ExpressPartSalesDeptQueryDto, com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo)
	 */
	@Override
	public void inActiveImmediatelyExpressPartSalesDept(
			ExpressPartSalesDeptQueryDto expressPartSalesDeptQueryDto,
			CurrentInfo currentInfo) {
		
		// 开始日志
		log.info("begin activeImmediatelyExpressPartSalesDept()...,");
		//获取当前时间
		Date date = new Date();
		//生成同步快递点部营业部信息
		List<ExpressPartSalesDeptEntity> sysList = new ArrayList<ExpressPartSalesDeptEntity>();
		
		// 1、处理选中ID
		toDoSelectedIds(expressPartSalesDeptQueryDto);
		//根据id查询所有待中止的映射信息
		List<ExpressPartSalesDeptEntity> toInActiveEntityList = expressPartSalesDeptDao.queryInfosByIds(expressPartSalesDeptQueryDto.getSelectedIdList());
		if(CollectionUtils.isNotEmpty(toInActiveEntityList)){
			for(ExpressPartSalesDeptEntity toInActiveEntity :toInActiveEntityList){
				//如果为中止状态，不能再次中止
				if(FossConstants.INACTIVE.equals(toInActiveEntity.getActive())){
					throw new BusinessException("只有激活的映射关系才可以中止");						
				}
				//如果当前时间大于中止时间，无须中止
				if(date.after(toInActiveEntity.getEndTime())){
					throw new BusinessException("该数据未在激活有效期内");		
				}
				//将结束时间这是为当前时间
				toInActiveEntity.setEndTime(date);
				//修改为中止
				expressPartSalesDeptDao.updateExpressPartSalesDeptByPartCode(toInActiveEntity);
				//加入到同步列表
				sysList.add(toInActiveEntity);
			}
		}
		
		//如果同步列表数据不为空，调用同步接口
		if(CollectionUtils.isNotEmpty(sysList)){
			//调用同步接口
			syncExpressPartAndSalesDeptService.syncExpressPartAndDeptToCrm(sysList);
		}
			
		// 结束日志
		log.info("end activeImmediatelyExpressPartSalesDept()...");	
	}
	
	/** 
	 * 升级
	 * @author foss-qiaolifeng
	 * @date 2013-9-2 下午2:20:38
	 * @param expressPartSalesDeptQueryDto
	 * @param currentInfo
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.IExpressPartSalesDeptService#upgradeExpressPartSalesDept(com.deppon.foss.module.base.baseinfo.api.shared.dto.ExpressPartSalesDeptQueryDto, com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo)
	 */
	@Override
	public void upgradeExpressPartSalesDept(
			ExpressPartSalesDeptQueryDto expressPartSalesDeptQueryDto,
			CurrentInfo currentInfo) {
		
		// 开始日志
		log.info("begin upgradeExpressPartSalesDept()...,");

		Date date = new Date();
		toDoSelectedIds(expressPartSalesDeptQueryDto);
		
		//1、根据ID查询营业部的原快递点部配置
		List<ExpressPartSalesDeptEntity> oldEntityList = expressPartSalesDeptDao.queryInfosByIds(expressPartSalesDeptQueryDto.getSelectedIdList());
		if(CollectionUtils.isNotEmpty(oldEntityList)){
			
			// 新建实体并保存
			ExpressPartSalesDeptEntity expressPartSalesDeptEntity = new ExpressPartSalesDeptEntity();
			expressPartSalesDeptEntity.setId(UUIDUtils.getUUID());// ID
			expressPartSalesDeptEntity.setPartCode(oldEntityList.get(0).getPartCode());// 快递点部编码
			expressPartSalesDeptEntity.setSalesDeptCode(oldEntityList.get(0).getSalesDeptCode());// 营业部编码
			expressPartSalesDeptEntity.setCreateUserCode(currentInfo
					.getEmpCode());// 创建人
			expressPartSalesDeptEntity.setModifyUserCode(currentInfo
					.getEmpCode());// 修改人
			expressPartSalesDeptEntity.setCreateTime(date);// 创建时间
			expressPartSalesDeptEntity.setModifyTime(date);// 修改时间
			expressPartSalesDeptEntity.setBeginTime(new Date(
					NumberConstants.ENDTIME));// 开始件
			expressPartSalesDeptEntity.setEndTime(new Date(
					NumberConstants.ENDTIME));// 结束时间
			expressPartSalesDeptEntity.setActive(FossConstants.INACTIVE);// 未激活
			expressPartSalesDeptEntity.setVersionNo(date.getTime());// 版本号

			// 保存数据库
			expressPartSalesDeptDao
					.addExpressPartSalesDept(expressPartSalesDeptEntity);
		}		
		
		// 结束日志
		log.info("end upgradeExpressPartSalesDept()...");
		
	}
	
	/**
	 * 检查并处理选择ID
	 * 
	 * @author foss-qiaolifeng
	 * @date 2013-7-22 上午10:16:23
	 * @param selectedIds
	 */
	private void toDoSelectedIds(
			ExpressPartSalesDeptQueryDto expressPartSalesDeptQueryDto) {

		// 如果选择的ID为空，提示输入参数错误
		if (StringUtils
				.isEmpty(expressPartSalesDeptQueryDto.getSelectedIds())) {
			throw new BusinessException("没有选中的快递点部营业部映射信息");
		}

		// 按“,”分割选中Id字串，生成ID的list
		String[] idArray = expressPartSalesDeptQueryDto.getSelectedIds()
				.split(",");
		List<String> list = new ArrayList<String>();
		for (int i = 0; i < idArray.length; i++) {
			list.add(idArray[i].trim());
		}
		expressPartSalesDeptQueryDto.setSelectedIdList(list);

	}
}
