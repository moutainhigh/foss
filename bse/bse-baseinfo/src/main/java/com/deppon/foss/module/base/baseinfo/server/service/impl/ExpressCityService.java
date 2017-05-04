package com.deppon.foss.module.base.baseinfo.server.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IExpressCityDao;
import com.deppon.foss.module.base.baseinfo.api.server.service.IAdministrativeRegionsService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IExpressCityService;
import com.deppon.foss.module.base.baseinfo.api.server.service.esb.ISyncExpressCityService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.AdministrativeRegionsEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.ExpressCityEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.ExpressCityQueryDto;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.ExpressCityResultDto;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryValueConstants;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;
import com.google.inject.Inject;


/**
 * 试点城市service
 * @author foss-qiaolifeng
 * @date 2013-7-17 下午2:24:23
 */
public class ExpressCityService implements IExpressCityService {
	@Inject
	private IExpressCityDao expressCityDao;
	@Inject
	private IAdministrativeRegionsService administrativeRegionsService;
	
	/**
	 * 同步快递代理/试点城市到周边系统
	 */
	private ISyncExpressCityService syncExpressCityService;
	
	/** 
	 * 查询总条数
	 * @author foss-qiaolifeng
	 * @date 2013-7-17 下午2:25:07
	 * @param expressCityQueryDto
	 * @return
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.IExpressCityService#queryExpressCityCountByCondition(com.deppon.foss.module.base.baseinfo.api.shared.dto.ExpressCityQueryDto)
	 */
	@Override
	public long queryExpressCityCountByCondition(
			ExpressCityQueryDto expressCityQueryDto) {

		//检查查询条件
		if(expressCityQueryDto==null){
			throw new BusinessException("查询快递代理/试点城市时参数异常");
		}
		
		//默认只查有效数据
		expressCityQueryDto.setActive(FossConstants.ACTIVE);
		
		//查询并返回总条数
		return expressCityDao.queryExpressCityCountByCondition(expressCityQueryDto);
	}

	/** 
	 * 查询列表
	 * @author foss-qiaolifeng
	 * @date 2013-7-17 下午2:24:54
	 * @param expressCityQueryDto
	 * @return
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.IExpressCityService#queryExpressCityListByCondition(com.deppon.foss.module.base.baseinfo.api.shared.dto.ExpressCityQueryDto)
	 */
	@Override
	public List<ExpressCityResultDto> queryExpressCityListByCondition(
			ExpressCityQueryDto expressCityQueryDto, int start, int limit) {
		// 检查查询条件
		if (expressCityQueryDto == null) {
			throw new BusinessException("查询快递代理/试点城市时参数异常");
		}
		
		//默认只查有效数据
		expressCityQueryDto.setActive(FossConstants.ACTIVE);
		
		//查询列表
		List<ExpressCityEntity> entityList = expressCityDao.queryExpressCityListByCondition(expressCityQueryDto, start, limit);
		
		//创建返回Dto
		List<ExpressCityResultDto> expressCityResultDtoList = new ArrayList<ExpressCityResultDto>();
		
		//循环设置
		if(CollectionUtils.isNotEmpty(entityList)){
			for(ExpressCityEntity entity: entityList){
				ExpressCityResultDto dto = new ExpressCityResultDto();
				try {
					PropertyUtils.copyProperties(dto, entity);
				} catch (IllegalAccessException e) {
					throw new BusinessException("快递代理/试点城市数据转换异常");
				} catch (InvocationTargetException e) {
					throw new BusinessException("快递代理/试点城市数据转换异常");
				} catch (NoSuchMethodException e) {
					throw new BusinessException("快递代理/试点城市数据转换异常");
				}
				//BeanUtils.copyProperties(entity, dto);
				
				//调用接口封装身份城市名称等信息
				AdministrativeRegionsEntity administrativeRegionsEntity = administrativeRegionsService.queryAdministrativeRegionsByCode(dto.getDistrictCode());
				if(administrativeRegionsEntity!=null){
					dto.setCityCode(dto.getDistrictCode());
					dto.setCityName(administrativeRegionsEntity.getName());
					dto.setProvCode(administrativeRegionsEntity.getParentDistrictCode());
					dto.setProvName(administrativeRegionsEntity.getParentDistrictName());
				}
				expressCityResultDtoList.add(dto);
			}
		}
		
		return expressCityResultDtoList;
	}

	/** 
	 * 根据条件查询一条快递代理/试点城市
	 * @author foss-qiaolifeng
	 * @date 2013-7-18 下午4:18:54
	 * @param expressCityQueryDto
	 * @return
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.IExpressCityService#queryOneExpressCityByCondition(com.deppon.foss.module.base.baseinfo.api.shared.dto.ExpressCityQueryDto)
	 */
	@Override
	public ExpressCityResultDto queryOneExpressCityByCondition(
			ExpressCityQueryDto expressCityQueryDto) {
		// 检查查询条件
		if (expressCityQueryDto == null
				|| StringUtils.isEmpty(expressCityQueryDto.getId())) {
			throw new BusinessException("查看快递代理/试点城市时参数异常");
		}
		
		//默认只查有效数据
		expressCityQueryDto.setActive(FossConstants.ACTIVE);
		
		//查询快递代理/城市
		ExpressCityEntity expressCityEntity = expressCityDao.queryOneExpressCityById(expressCityQueryDto);
		//生成返回Dto
		ExpressCityResultDto expressCityResultDto = new ExpressCityResultDto();
		//如果查询结果存在，将查询结果拷贝到Dto上
		if(expressCityEntity!=null){
			try {
				PropertyUtils.copyProperties(expressCityResultDto, expressCityEntity);
			} catch (IllegalAccessException e) {
				throw new BusinessException("快递代理/试点城市数据转换异常");
			} catch (InvocationTargetException e) {
				throw new BusinessException("快递代理/试点城市数据转换异常");
			} catch (NoSuchMethodException e) {
				throw new BusinessException("快递代理/试点城市数据转换异常");
			}
			//BeanUtils.copyProperties(expressCityEntity, expressCityResultDto);
			AdministrativeRegionsEntity administrativeRegionsEntity = administrativeRegionsService.queryAdministrativeRegionsByCodeNotCache(expressCityEntity.getDistrictCode());
			expressCityResultDto.setCityCode(expressCityEntity.getDistrictCode());
			expressCityResultDto.setCityName(administrativeRegionsEntity.getName());
			expressCityResultDto.setProvCode(administrativeRegionsEntity.getParentDistrictCode());
			expressCityResultDto.setProvName(administrativeRegionsEntity.getParentDistrictName());
		}
		return expressCityResultDto;
	}
	
	/** 
	 * 新增
	 * @author foss-qiaolifeng
	 * @date 2013-7-17 下午2:24:58
	 * @param expressCityQueryDtoList
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.IExpressCityService#addExpressCityList(java.util.List)
	 */
	@Override
	@Transactional
	public void addExpressCityList(List<ExpressCityEntity> expressCityEntityList,CurrentInfo currentInfo) {

		// 检查新增参数条件
		if(CollectionUtils.isEmpty(expressCityEntityList)){
			throw new BusinessException("新增快递代理/试点城市时参数异常");
		}

		//生成同步快递代理/试点城市数据列表
		List<ExpressCityResultDto> sysList = new ArrayList<ExpressCityResultDto>();
		
		//循化处理
		for(ExpressCityEntity entity:expressCityEntityList){
			
			if(StringUtils.isEmpty(entity.getDistrictCode())
					||StringUtils.isEmpty(entity.getType())){
				throw new BusinessException("新增快递代理/试点城市时城市编码/类型参数异常");
			}
			
			//根据城市编码和城市类型查询数据
			ExpressCityQueryDto expressCityQueryDto = new ExpressCityQueryDto();
			expressCityQueryDto.setActive(FossConstants.ACTIVE);//有效
			expressCityQueryDto.setDistrictCode(entity.getDistrictCode());//城市编码
			
//			if(DictionaryValueConstants.EXPRESS_CITY_TYPE_LDP.equals(entity.getType())){
//				expressCityQueryDto.setType(DictionaryValueConstants.EXPRESS_CITY_TYPE_LDP);//城市类别:快递代理
//			}else if(DictionaryValueConstants.EXPRESS_CITY_TYPE_PILOT.equals(entity.getType())){
//				expressCityQueryDto.setType(DictionaryValueConstants.EXPRESS_CITY_TYPE_PILOT);//城市类别：试点
//			}else {
//				throw new BusinessException("新增快递代理/试点城市时,城市类型参数异常");
//			}
			
			List<ExpressCityEntity> oldExpressCityEntityList = expressCityDao.queryOneExpressCityListByCodeAndType(expressCityQueryDto);
			//验证是否已经配置过快递代理/试点城市
			if(CollectionUtils.isNotEmpty(oldExpressCityEntityList)){
				AdministrativeRegionsEntity administrativeRegionsEntity = administrativeRegionsService.queryAdministrativeRegionsByCodeNotCache(oldExpressCityEntityList.get(0).getDistrictCode());
				throw new BusinessException(administrativeRegionsEntity.getParentDistrictName()+":"+administrativeRegionsEntity.getName()+"已配置过快递代理/试点城市,请重新选择!");
			}else{
				
				Date date = new Date();
				
				//新增快递代理/试点城市
				entity.setId(UUIDUtils.getUUID());//ID
				entity.setActive(FossConstants.ACTIVE);//有效
				entity.setCreateTime(date);//创建时间
				entity.setModifyTime(new Date(NumberConstants.ENDTIME));//修改时间
				entity.setCreateUserCode(currentInfo.getEmpCode());//创建人编码
				entity.setModifyUserCode(currentInfo.getEmpCode());//修改人编码
				entity.setVersionNo(date.getTime());//版本号
				
				//新增
				expressCityDao.addExpressCityEntity(entity);
				
				//新增同步Dto类型
				ExpressCityResultDto sysDto = new ExpressCityResultDto();
				//拷贝信息
				try {
					PropertyUtils.copyProperties(sysDto, entity);
				} catch (IllegalAccessException e) {
					throw new BusinessException("快递代理/试点城市数据转换异常");
				} catch (InvocationTargetException e) {
					throw new BusinessException("快递代理/试点城市数据转换异常");
				} catch (NoSuchMethodException e) {
					throw new BusinessException("快递代理/试点城市数据转换异常");
				}
				//BeanUtils.copyProperties(entity, sysDto);
				//调用接口封装城市名称等信息
				AdministrativeRegionsEntity administrativeRegionsEntity = administrativeRegionsService.queryAdministrativeRegionsByCode(sysDto.getDistrictCode());
				if(administrativeRegionsEntity!=null){
					sysDto.setCityName(administrativeRegionsEntity.getName());
					sysDto.setCityCode(administrativeRegionsEntity.getCode());
				}
				//加入到同步快递代理/试点城市数据列表
				sysList.add(sysDto);
			}
		}
		
		//调用同步接口
		if(CollectionUtils.isNotEmpty(sysList)){
			syncExpressCityService.syncExpressCityToOws(sysList);
		}
	}

	/** 
	 * 作废
	 * @author foss-qiaolifeng
	 * @date 2013-7-17 下午2:25:01
	 * @param expressCityQueryDtoList
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.IExpressCityService#disabledExpressCity(java.util.List)
	 */
	@Override
	@Transactional
	public void disabledExpressCity(
			ExpressCityQueryDto expressCityQueryDto,CurrentInfo currentInfo) {
		
		//检查并处理选中的ID
		toDoSelectedIds(expressCityQueryDto);
		
		Date date = new Date();
		
		//生成同步快递代理/试点城市数据列表
		List<ExpressCityResultDto> sysList = new ArrayList<ExpressCityResultDto>();
		
		//循环处理
		for(String id:expressCityQueryDto.getIdList()){
			
			//根据ID查询
			ExpressCityQueryDto queryDto = new ExpressCityQueryDto();
			queryDto.setId(id);//ID
			queryDto.setActive(FossConstants.ACTIVE);//有效
			ExpressCityEntity  expressCityEntity = expressCityDao.queryOneExpressCityById(queryDto);
			
			//如果为空，提示该城市已经被作废
			if(expressCityEntity==null){
				throw new BusinessException("选择的城市已被其他用户作废了,请重新选择!");
			}else{
				//修改
				expressCityEntity.setActive(FossConstants.INACTIVE);
				expressCityEntity.setModifyTime(date);
				expressCityEntity.setModifyUserCode(currentInfo.getEmpCode());
				expressCityEntity.setVersionNo(date.getTime());
				expressCityDao.updateExpressCityEntity(expressCityEntity);
					
				//新增同步Dto类型
				ExpressCityResultDto sysDto = new ExpressCityResultDto();
				//拷贝信息
				try {
					PropertyUtils.copyProperties(sysDto, expressCityEntity);
				} catch (IllegalAccessException e) {
					throw new BusinessException("快递代理/试点城市数据转换异常");
				} catch (InvocationTargetException e) {
					throw new BusinessException("快递代理/试点城市数据转换异常");
				} catch (NoSuchMethodException e) {
					throw new BusinessException("快递代理/试点城市数据转换异常");
				}
				//BeanUtils.copyProperties(expressCityEntity, sysDto);
				//调用接口封装城市名称等信息
				AdministrativeRegionsEntity administrativeRegionsEntity = administrativeRegionsService.queryAdministrativeRegionsByCode(sysDto.getDistrictCode());
				if(administrativeRegionsEntity!=null){
					sysDto.setCityName(administrativeRegionsEntity.getName());
					sysDto.setCityCode(administrativeRegionsEntity.getCode());
				}
				//加入到同步快递代理/试点城市数据列表
				sysList.add(sysDto);
			}
		}
		
		//调用同步接口
		if(CollectionUtils.isNotEmpty(sysList)){
			syncExpressCityService.syncExpressCityToOws(sysList);
		}		
	}

	/** 
	 * 修改
	 * @author foss-qiaolifeng
	 * @date 2013-7-17 下午2:25:04
	 * @param oldExpressCityQueryDto
	 * @param newExpressCityQueryDtoList
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.IExpressCityService#updateExpressCity(com.deppon.foss.module.base.baseinfo.api.shared.dto.ExpressCityQueryDto, java.util.List)
	 */
	@Override
	@Transactional
	public void updateExpressCity(ExpressCityEntity oldExpressCityEntity,
			List<ExpressCityEntity> newExpressCityEntityList,CurrentInfo currentInfo) {
		
		//验证参数
		//313353 sonar
		this.sonarSplitCheckException(oldExpressCityEntity, newExpressCityEntityList);

		Date date = new Date();
		
		//生成同步快递代理/试点城市数据列表
		List<ExpressCityResultDto> sysList = new ArrayList<ExpressCityResultDto>();
		
		//1、作废旧城市
		//313353 sonar
		this.sonarSplitOne(oldExpressCityEntity, newExpressCityEntityList, date, currentInfo, sysList);
		
		//2、新增新城市配置
		for(ExpressCityEntity entity:newExpressCityEntityList){
			
			//根据城市编码和城市类型查询数据
			ExpressCityQueryDto expressCityQueryDto = new ExpressCityQueryDto();
			expressCityQueryDto.setActive(FossConstants.ACTIVE);//有效
			expressCityQueryDto.setDistrictCode(entity.getDistrictCode());//城市编码
			
//			if(DictionaryValueConstants.EXPRESS_CITY_TYPE_LDP.equals(entity.getType())){
//				expressCityQueryDto.setType(DictionaryValueConstants.EXPRESS_CITY_TYPE_LDP);//城市类别:快递代理
//			}else if(DictionaryValueConstants.EXPRESS_CITY_TYPE_PILOT.equals(entity.getType())){
//				expressCityQueryDto.setType(DictionaryValueConstants.EXPRESS_CITY_TYPE_PILOT);//城市类别：试点
//			}else {
//				throw new BusinessException("修改快递代理/试点城市时,城市类型参数异常");
//			}
			
			//根据新的城市编码和类型查询该信息是否存在
			List<ExpressCityEntity> oldExpressCityEntityListTemp = expressCityDao.queryOneExpressCityListByCodeAndType(expressCityQueryDto);
			//如果被配置，给与提示
			if(CollectionUtils.isNotEmpty(oldExpressCityEntityListTemp)){
				AdministrativeRegionsEntity administrativeRegionsEntity = administrativeRegionsService.queryAdministrativeRegionsByCodeNotCache(oldExpressCityEntityListTemp.get(0).getDistrictCode());
				throw new BusinessException(administrativeRegionsEntity.getParentDistrictName()+":"+administrativeRegionsEntity.getName()+"已经被配置!");
			}else{
			//若没配置，请新增
				ExpressCityEntity newExpressCityEntity = new ExpressCityEntity();
				newExpressCityEntity.setId(UUIDUtils.getUUID());//ID
				newExpressCityEntity.setDistrictCode(entity.getDistrictCode());//城市编码

				if(DictionaryValueConstants.EXPRESS_CITY_TYPE_LDP.equals(entity.getType())){
					newExpressCityEntity.setType(DictionaryValueConstants.EXPRESS_CITY_TYPE_LDP);//城市类别:快递代理
				}else if(DictionaryValueConstants.EXPRESS_CITY_TYPE_PILOT.equals(entity.getType())){
					newExpressCityEntity.setType(DictionaryValueConstants.EXPRESS_CITY_TYPE_PILOT);//城市类别：试点
				}else {
					throw new BusinessException("修改快递代理/试点城市时,城市类型参数异常");
				}
				
				newExpressCityEntity.setActive(FossConstants.ACTIVE);//有效
				newExpressCityEntity.setCreateUserCode(currentInfo.getEmpCode());//创建人编码
				newExpressCityEntity.setModifyUserCode(currentInfo.getEmpCode());//修改人编码
				newExpressCityEntity.setCreateTime(date);//创建时间
				newExpressCityEntity.setModifyTime(new Date(NumberConstants.ENDTIME));//修改时间
				newExpressCityEntity.setVersionNo(date.getTime());//版本号
			
				//保存新增
				expressCityDao.addExpressCityEntity(newExpressCityEntity);
				
				//新增同步Dto类型
				ExpressCityResultDto sysDto = new ExpressCityResultDto();
				//拷贝信息
				try {
					PropertyUtils.copyProperties(sysDto, newExpressCityEntity);
				} catch (IllegalAccessException e) {
					throw new BusinessException("快递代理/试点城市数据转换异常");
				} catch (InvocationTargetException e) {
					throw new BusinessException("快递代理/试点城市数据转换异常");
				} catch (NoSuchMethodException e) {
					throw new BusinessException("快递代理/试点城市数据转换异常");
				}
				//BeanUtils.copyProperties(newExpressCityEntity, sysDto);
				//调用接口封装城市名称等信息
				AdministrativeRegionsEntity administrativeRegionsEntity = administrativeRegionsService.queryAdministrativeRegionsByCode(sysDto.getDistrictCode());
				if(administrativeRegionsEntity!=null){
					sysDto.setCityName(administrativeRegionsEntity.getName());
					sysDto.setCityCode(administrativeRegionsEntity.getCode());
				}
				//加入到同步快递代理/试点城市数据列表
				sysList.add(sysDto);
			}
		}
		
		//调用同步接口
		if(CollectionUtils.isNotEmpty(sysList)){
			syncExpressCityService.syncExpressCityToOws(sysList);
		}	
	}
	
	/**
	 * sonar优化拆分
	 * 
	 * @author 313353
	 */
	private void sonarSplitCheckException(ExpressCityEntity oldExpressCityEntity,
			List<ExpressCityEntity> newExpressCityEntityList) {
		if(oldExpressCityEntity==null
				||StringUtils.isEmpty(oldExpressCityEntity.getDistrictCode())
				||StringUtils.isEmpty(oldExpressCityEntity.getType())){
			throw new BusinessException("修改失败，选择的待修改参数异常!");
		}
		if(CollectionUtils.isEmpty(newExpressCityEntityList)){
			throw new BusinessException("修改失败，修改时新增参数异常!");
		}
	}
	
	/**
	 * sonar优化拆分
	 * 
	 * @author 313353
	 */
	private void sonarSplitOne(ExpressCityEntity oldExpressCityEntity,
			List<ExpressCityEntity> newExpressCityEntityList, Date date, CurrentInfo currentInfo
			,List<ExpressCityResultDto> sysList) {
		ExpressCityQueryDto oldExpressCityQueryDto = new ExpressCityQueryDto();
		oldExpressCityQueryDto.setActive(FossConstants.ACTIVE);//有效
		oldExpressCityQueryDto.setDistrictCode(oldExpressCityEntity.getDistrictCode());//城市编码
		
		if(DictionaryValueConstants.EXPRESS_CITY_TYPE_LDP.equals(oldExpressCityEntity.getType())){
			oldExpressCityQueryDto.setType(DictionaryValueConstants.EXPRESS_CITY_TYPE_LDP);//城市类别:快递代理
		}else if(DictionaryValueConstants.EXPRESS_CITY_TYPE_PILOT.equals(oldExpressCityEntity.getType())){
			oldExpressCityQueryDto.setType(DictionaryValueConstants.EXPRESS_CITY_TYPE_PILOT);//城市类别：试点
		}else {
			throw new BusinessException("修改快递代理/试点城市时,城市类型参数异常");
		}
		
		List<ExpressCityEntity> oldExpressCityEntityList = expressCityDao.queryOneExpressCityListByCodeAndType(oldExpressCityQueryDto);
		if(CollectionUtils.isNotEmpty(oldExpressCityEntityList)){
			
			ExpressCityEntity expressCityEntity = oldExpressCityEntityList.get(0);
			//修改
			expressCityEntity.setActive(FossConstants.INACTIVE);
			expressCityEntity.setModifyTime(date);
			expressCityEntity.setModifyUserCode(currentInfo.getEmpCode());
			expressCityEntity.setVersionNo(date.getTime());
			expressCityDao.updateExpressCityEntity(expressCityEntity);
			
			//新增同步Dto类型
			ExpressCityResultDto sysDto = new ExpressCityResultDto();
			//拷贝信息
			try {
				PropertyUtils.copyProperties(sysDto, expressCityEntity);
			} catch (IllegalAccessException e) {
				throw new BusinessException("快递代理/试点城市数据转换异常");
			} catch (InvocationTargetException e) {
				throw new BusinessException("快递代理/试点城市数据转换异常");
			} catch (NoSuchMethodException e) {
				throw new BusinessException("快递代理/试点城市数据转换异常");
			}
			//BeanUtils.copyProperties(expressCityEntity, sysDto);
			//调用接口封装城市名称等信息
			AdministrativeRegionsEntity administrativeRegionsEntity = administrativeRegionsService.queryAdministrativeRegionsByCode(sysDto.getDistrictCode());
			if(administrativeRegionsEntity!=null){
				sysDto.setCityName(administrativeRegionsEntity.getName());
				sysDto.setCityCode(administrativeRegionsEntity.getCode());
			}
			//加入到同步快递代理/试点城市数据列表
			sysList.add(sysDto);
		}
	}

	/** 
	 * 根据营业部网点编码获取该营业部所在城市的快递代理/试点城市类型(优先试点城市)
	 * @author foss-qiaolifeng
	 * @date 2013-7-23 下午1:36:59
	 * @param orgCode
	 * @return
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.IExpressCityService#queryExpressCityTypeByOrgCode(java.lang.String)
	 */
	@Override
	public ExpressCityResultDto queryExpressCityTypeByOrgCode(String orgCode) {
		
		//检查传入参数
		if(StringUtils.isEmpty(orgCode)){
			return null;
		}
		
		//查询
		List<ExpressCityEntity> expressCityEntityList = expressCityDao.queryExpressCityByOrgCode(orgCode, FossConstants.ACTIVE);
		
		ExpressCityResultDto expressCityResultDto = null;
		//1、如果为空直接返回Null
		if(CollectionUtils.isEmpty(expressCityEntityList)){
			return null;
		//2、如果返回对象为1个,返回该对象	
		}else if(expressCityEntityList.size()==1){
			expressCityResultDto = new ExpressCityResultDto();
			//313353 sonar
			this.sonarSplitCopyData(expressCityResultDto, expressCityEntityList);
			
			//BeanUtils.copyProperties(expressCityEntityList.get(0), expressCityResultDto);
			AdministrativeRegionsEntity administrativeRegionsEntity = administrativeRegionsService.queryAdministrativeRegionsByCodeNotCache(expressCityEntityList.get(0).getDistrictCode());
			if(administrativeRegionsEntity!=null){
				expressCityResultDto.setProvName(administrativeRegionsEntity.getParentDistrictName());
				expressCityResultDto.setProvCode(administrativeRegionsEntity.getParentDistrictName());
				expressCityResultDto.setCityName(administrativeRegionsEntity.getName());
				expressCityResultDto.setCityCode(administrativeRegionsEntity.getCode());
			}
		//3、如果两个对象，且包含类型为试点城市对象，返回试点城市，否者返回快递代理城市	
		}else{
			boolean flag = false;
			for(ExpressCityEntity entity:expressCityEntityList){
				if(DictionaryValueConstants.EXPRESS_CITY_TYPE_PILOT.equals(entity.getType())){
					flag = true;
				}
			}
			
			expressCityResultDto = new ExpressCityResultDto();
			//313353 sonar
			this.sonarSplitCopyData(expressCityResultDto, expressCityEntityList);
			
			//BeanUtils.copyProperties(expressCityEntityList.get(0), expressCityResultDto);
			AdministrativeRegionsEntity administrativeRegionsEntity = administrativeRegionsService.queryAdministrativeRegionsByCodeNotCache(expressCityEntityList.get(0).getDistrictCode());
			if(administrativeRegionsEntity!=null){
				expressCityResultDto.setProvName(administrativeRegionsEntity.getParentDistrictName());
				expressCityResultDto.setProvCode(administrativeRegionsEntity.getParentDistrictName());
				expressCityResultDto.setCityName(administrativeRegionsEntity.getName());
				expressCityResultDto.setCityCode(administrativeRegionsEntity.getCode());
			}
			
			if(flag){
				expressCityResultDto.setType(DictionaryValueConstants.EXPRESS_CITY_TYPE_PILOT);
			}else{
				expressCityResultDto.setType(DictionaryValueConstants.EXPRESS_CITY_TYPE_LDP);
			}
		}
		return expressCityResultDto;
	}
	
	/**
	 * sonar优化拆分
	 * 
	 * @author 313353
	 */
	private void sonarSplitCopyData(ExpressCityResultDto expressCityResultDto, List<ExpressCityEntity> expressCityEntityList) {
		try {
			PropertyUtils.copyProperties(expressCityResultDto, expressCityEntityList.get(0));
		} catch (IllegalAccessException e) {
			throw new BusinessException("快递代理/试点城市数据转换异常");
		} catch (InvocationTargetException e) {
			throw new BusinessException("快递代理/试点城市数据转换异常");
		} catch (NoSuchMethodException e) {
			throw new BusinessException("快递代理/试点城市数据转换异常");
		}
	}
	
	/**
	 * 根据营业部网点编码获取该营业部所在城市的快递代理/试点城市类型（优先快递代理城市）
	 * @author foss-qiaolifeng
	 * @date 2013-7-23 上午11:08:14
	 * @param orgCode
	 * @return
	 */
	@Override
	public ExpressCityResultDto queryLdpExpressCityTypeByOrgCode(String orgCode) {
		
		//检查传入参数
		if(StringUtils.isEmpty(orgCode)){
			return null;
		}
		
		//查询
		List<ExpressCityEntity> expressCityEntityList = expressCityDao.queryExpressCityByOrgCode(orgCode, FossConstants.ACTIVE);
		
		ExpressCityResultDto expressCityResultDto = null;
		//1、如果为空直接返回Null
		if(CollectionUtils.isEmpty(expressCityEntityList)){
			return null;
		//2、如果返回对象为1个,返回该对象	
		}else if(expressCityEntityList.size()==1){
			expressCityResultDto = new ExpressCityResultDto();
			//313353 sonar
			this.sonarSplitCopyData(expressCityResultDto, expressCityEntityList);
			
			//BeanUtils.copyProperties(expressCityEntityList.get(0), expressCityResultDto);
			AdministrativeRegionsEntity administrativeRegionsEntity = administrativeRegionsService.queryAdministrativeRegionsByCodeNotCache(expressCityEntityList.get(0).getDistrictCode());
			if(administrativeRegionsEntity!=null){
				expressCityResultDto.setProvName(administrativeRegionsEntity.getParentDistrictName());
				expressCityResultDto.setProvCode(administrativeRegionsEntity.getParentDistrictName());
				expressCityResultDto.setCityName(administrativeRegionsEntity.getName());
				expressCityResultDto.setCityCode(administrativeRegionsEntity.getCode());
			}
		//3、如果两个对象，且包含类型为快递代理城市对象，返回快递代理城市，否者返回试点城市	
		}else{
			boolean flag = false;
			for(ExpressCityEntity entity:expressCityEntityList){
				if(DictionaryValueConstants.EXPRESS_CITY_TYPE_LDP.equals(entity.getType())){
					flag = true;
				}
			}
			
			expressCityResultDto = new ExpressCityResultDto();
			//313353 sonar
			this.sonarSplitCopyData(expressCityResultDto, expressCityEntityList);
			
			//BeanUtils.copyProperties(expressCityEntityList.get(0), expressCityResultDto);
			AdministrativeRegionsEntity administrativeRegionsEntity = administrativeRegionsService.queryAdministrativeRegionsByCodeNotCache(expressCityEntityList.get(0).getDistrictCode());
			if(administrativeRegionsEntity!=null){
				expressCityResultDto.setProvName(administrativeRegionsEntity.getParentDistrictName());
				expressCityResultDto.setProvCode(administrativeRegionsEntity.getParentDistrictName());
				expressCityResultDto.setCityName(administrativeRegionsEntity.getName());
				expressCityResultDto.setCityCode(administrativeRegionsEntity.getCode());
			}
			
			if(flag){
				expressCityResultDto.setType(DictionaryValueConstants.EXPRESS_CITY_TYPE_LDP);
			}else{
				expressCityResultDto.setType(DictionaryValueConstants.EXPRESS_CITY_TYPE_PILOT);
			}
		}
		return expressCityResultDto;
	}

	/** 
	 * 根据营业部编码查询快递代理/试点城市映射信息
	 * @author foss-qiaolifeng
	 * @date 2013-8-19 下午4:10:10
	 * @param orgCode
	 * @param active
	 * @return
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.IExpressCityService#queryExpressCityByOrgCode(java.lang.String, java.lang.String)
	 */
	@Override
	public List<ExpressCityEntity> queryExpressCityByOrgCode(String orgCode,
			String active) {
		//检查传入参数
		if(StringUtils.isEmpty(orgCode)){
			return null;
		}
				
		//查询
		return  expressCityDao.queryExpressCityByOrgCode(orgCode, active);
	}	
	
	/**
	 * 检查并处理选择单号
	 * @author foss-qiaolifeng
	 * @date 2013-7-22 上午10:16:23
	 * @param selectedIds
	 */
	private void toDoSelectedIds(ExpressCityQueryDto expressCityQueryDto) {

		// 如果选择的ID为空，提示输入参数错误
		if (StringUtils.isEmpty(expressCityQueryDto.getSelectedIds())) {
			throw new BusinessException("没有选中的快递代理/试点城市信息");
		}

		// 按“,”分割选中Id字串，生成ID的list
		String[] idArray = expressCityQueryDto.getSelectedIds().split(",");
		List<String> list = new ArrayList<String>();
		for (int i = 0; i < idArray.length; i++) {
			list.add(idArray[i].trim());
		}
		expressCityQueryDto.setIdList(list);

	}
	
	public IExpressCityDao getExpressCityDao() {
		return expressCityDao;
	}

	public void setExpressCityDao(IExpressCityDao expressCityDao) {
		this.expressCityDao = expressCityDao;
	}

	public IAdministrativeRegionsService getAdministrativeRegionsService() {
		return administrativeRegionsService;
	}

	public void setAdministrativeRegionsService(
			IAdministrativeRegionsService administrativeRegionsService) {
		this.administrativeRegionsService = administrativeRegionsService;
	}

	public ISyncExpressCityService getSyncExpressCityService() {
		return syncExpressCityService;
	}

	public void setSyncExpressCityService(
			ISyncExpressCityService syncExpressCityService) {
		this.syncExpressCityService = syncExpressCityService;
	}
}
