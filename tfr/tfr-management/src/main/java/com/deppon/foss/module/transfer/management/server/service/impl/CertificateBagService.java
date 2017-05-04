/**
 *  initial comments.
 */
/*******************************************************************************
 * Copyright 2013 TFR TEAM
 *  
 *  Licensed under the DEPPON License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *  
 *     http://www.deppon.com/licenses/LICENSE-2.0
 *  
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *  Contributors:
 *  038300-foss-pengzhen - initial API and implementation
 * 
 *  PROJECT NAME  : tfr-management
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/management/server/service/impl/CertificateBagService.java
 *  
 *  FILE NAME          :CertificateBagService.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.transfer.management.server.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;


import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.server.service.IEmployeeService;
import com.deppon.foss.module.base.baseinfo.api.server.service.complex.IOrgAdministrativeInfoComplexService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.EmployeeEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.transfer.common.api.shared.exception.TfrBusinessException;
import com.deppon.foss.module.transfer.departure.api.utils.Constants;
import com.deppon.foss.module.transfer.management.api.server.dao.ICertificateBagDao;
import com.deppon.foss.module.transfer.management.api.server.service.ICertificateBagService;
import com.deppon.foss.module.transfer.management.api.server.service.IConfigItemEntityService;
import com.deppon.foss.module.transfer.management.api.server.service.IConfigOrgRelationService;
import com.deppon.foss.module.transfer.management.api.shared.define.CertificatebagConstant;
import com.deppon.foss.module.transfer.management.api.shared.define.ConfigOrgRelationConstant;
import com.deppon.foss.module.transfer.management.api.shared.domain.CertificatebagEntity;
import com.deppon.foss.module.transfer.management.api.shared.domain.CertificatebagQueryEntity;
import com.deppon.foss.module.transfer.management.api.shared.domain.CertificatebagReturnEntity;
import com.deppon.foss.module.transfer.management.api.shared.domain.CertificatebagTakeEntity;
import com.deppon.foss.module.transfer.management.api.shared.domain.ConfigItemEntity;
import com.deppon.foss.module.transfer.management.api.shared.domain.ConfigOrgRelationEntity;
import com.deppon.foss.module.transfer.management.api.shared.domain.TruckTaskEntity;
import com.deppon.foss.module.transfer.management.api.shared.dto.CertificatebagDto;
import com.deppon.foss.module.transfer.management.api.shared.dto.CertificatebagReturnDto;
import com.deppon.foss.module.transfer.management.api.shared.dto.CertificatebagTakeDto;
import com.deppon.foss.util.UUIDUtils;
/**
 * 
 *  证件包Service
 * @author dp-liming
 * @date 2012-11-03 下午16:00:43
 */
public class CertificateBagService implements ICertificateBagService {
	//证件包Dao
	private ICertificateBagDao  certificateBagDao;
	//员工信息
	private IEmployeeService employeeService;
	//日志
	private static final Logger LOGGER=LoggerFactory.getLogger(CertificateBagService.class);
	
	// 配置项信息service
	private IConfigItemEntityService configItemEntityService;
	
	// 配置项与组织对应关系服务类
	private IConfigOrgRelationService configOrgRelationService;

	/**
	 * 部门 复杂查询 service
	 */
	private IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService;

	/**
	 * orgAdministrativeInfoComplexService
	 * 
	 * @return the orgAdministrativeInfoComplexService
	 * @since CodingExample Ver(编码范例查看) 1.0
	 */

	public IOrgAdministrativeInfoComplexService getOrgAdministrativeInfoComplexService() {
		return orgAdministrativeInfoComplexService;
	}

	/**
	 * @param orgAdministrativeInfoComplexService the orgAdministrativeInfoComplexService to set Date:2013-4-13下午1:41:05
	 */

	public void setOrgAdministrativeInfoComplexService(IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService) {
		this.orgAdministrativeInfoComplexService = orgAdministrativeInfoComplexService;
	}

	/**
	 * configOrgRelationService
	 * 
	 * @return the configOrgRelationService
	 * @since CodingExample Ver(编码范例查看) 1.0
	 */

	public IConfigOrgRelationService getConfigOrgRelationService() {
		return configOrgRelationService;
	}

	/**
	 * @param configOrgRelationService the configOrgRelationService to set Date:2013-4-13上午11:47:13
	 */

	public void setConfigOrgRelationService(IConfigOrgRelationService configOrgRelationService) {
		this.configOrgRelationService = configOrgRelationService;
	}

	/**
	 * configItemEntityService
	 * 
	 * @return the configItemEntityService
	 * @since CodingExample Ver(编码范例查看) 1.0
	 */

	public IConfigItemEntityService getConfigItemEntityService() {
		return configItemEntityService;
	}

	/**
	 * @param configItemEntityService the configItemEntityService to set Date:2013-4-13上午11:30:24
	 */

	public void setConfigItemEntityService(IConfigItemEntityService configItemEntityService) {
		this.configItemEntityService = configItemEntityService;
	}

	/**
	 * 分页查询证件包信息
	 * 
	 * @author dp-liming
	 * @date 2012-11-2 上午11:00:26
	 */
	@Override	
	public List<CertificatebagEntity> queryCertificateBagList(CertificatebagDto certificatebagDto, int start, int limit) {
		//分页查询查询证件包信息
		return  certificateBagDao.queryCertificateBagList(certificatebagDto, start, limit);
	}

	/**
	 * 证件包总数
	 * 
	 * @author dp-liming
	 * @date 2012-11-2 上午11:00:26
	 */
	@Override
	public Long queryCount(CertificatebagDto certificatebagDto) {	
		//获取总条数
		return certificateBagDao.queryCount(certificatebagDto);
	}

	/**
	 * 显示证件包明细
	 * 
	 * @author dp-liming
	 * @date 2012-11-2 上午11:10:24
	 */
	@Override
	public List<CertificatebagEntity> displayCertificateBagDetail(CertificatebagDto certificatebagEntity) {
		//得到证件包明细
		return certificateBagDao.displayCertificateBagDetail(certificatebagEntity);
		 
	}

	
	/**
	 * 归还证件包
	 */
	@Override
	@Transactional
	public int updateCertificateBagStatus(CertificatebagReturnDto returnDto) {
		//得到用户信息
		UserEntity user=FossUserContext.getCurrentUser();
		//得到员工信息
		EmployeeEntity emp = user.getEmployee();
		
		// modify by liangfuxiang 2013-3-18下午3:40:39 begin 去除魔鬼数据
		// LOGGER.info("归还证件包");
		LOGGER.info(CertificatebagConstant.RETURN_CERTIFICATEBAG);
		// modify by liangfuxiang 2013-3-18下午3:40:52 end;
		//车牌ID
		String vehicleId="";
		//判断车牌号是否为空
		if (!StringUtils.isBlank(returnDto.getVehicleNo())) {
			//定义一个归还的dto
			CertificatebagDto returnQueryDto=new CertificatebagDto();
			//领取的车牌号
			returnQueryDto.setVehicleNo(returnDto.getVehicleNo());
			//领取的证件包状态
			CertificatebagEntity  takeQueryEntity=new CertificatebagEntity();
			//证件包状态
			takeQueryEntity.setCertificatebagStatus(CertificatebagConstant.CERTIFICATEBAG_RETURN);
			
			returnQueryDto.setCertificatebagEntity(takeQueryEntity);
			//得到归还的证件包信息
			CertificatebagEntity  returnQueryEntity=certificateBagDao.queryCertificatebag(returnQueryDto);
			//归还之前检查车牌是否已经被领取过，  领取了可以归还证件包，否则抛出异常
			if (returnQueryEntity!=null) {
				//车牌entity
				CertificatebagEntity vehicleEntity=new CertificatebagEntity();//车牌实体
				//得到Id
				vehicleId=returnQueryEntity.getId();
				//id
				vehicleEntity.setId(vehicleId);
				//车牌号
				vehicleEntity.setVehicleNo(returnDto.getVehicleNo());
				// 业务类型
				vehicleEntity.setBusinessType(returnDto.getBusinessType());
				//证件包状态
				vehicleEntity.setCertificatebagStatus(CertificatebagConstant.CERTIFICATEBAG_TAKE);	
				// 归还备注	
				vehicleEntity.setActualReturnNotes(returnDto.getActualReturnNotes());	
				//证件包类型
				vehicleEntity.setCertificatebagType(CertificatebagConstant.CERTIFICATEBAG_TAKE);				
				if (emp != null) {
					// 归还操作人
					vehicleEntity.setActualReturnOperator(emp.getEmpName());
					// 归还操作人Code
					vehicleEntity.setPlanTakeOrgCode(emp.getEmpCode());		
					//日期
					vehicleEntity.setUpdateTime(new Date());
					//修改人Code
					vehicleEntity.setUpdateUserCode(emp.getEmpCode());
					//修改人Name
					vehicleEntity.setUpdateUserName(emp.getEmpName());
					//归还日期
					vehicleEntity.setActualReturnTime(new  Date());		
					
					OrgAdministrativeInfoEntity depart = emp.getDepartment();
					if (depart != null) {		
						//归还地点
						vehicleEntity.setActualReturnOrgCode(depart.getName());	
						//归还Code
						vehicleEntity.setUpdateOrgCode(depart.getCode());
						//机会归还地点
						vehicleEntity.setPlanReturnOrgCode(depart.getCode());
					}		
				}
				//根据Code找用户信息
				EmployeeEntity employee=employeeService.queryEmployeeByEmpCode(returnDto.getActualReturnUserCode());
				// 归还人姓名
				if (employee!=null) {
					vehicleEntity.setActualReturnUserName(employee.getEmpName());
				}else if(StringUtils.isNotBlank(returnDto.getActualReturnUserName())){
					// 归还人姓名
					vehicleEntity.setActualReturnUserName(returnDto.getActualReturnUserName());
				}else{
					// modify by liangfuxiang 2013-3-18下午3:44:21 begin 国际化
					// throw new TfrBusinessException("归还人输入有误，请输入司机工号", "");
					throw new TfrBusinessException(CertificatebagConstant.RETURN_WRONG_EMPLOYEE_ID, CertificatebagConstant.BLANK_STRING);
					// modify by liangfuxiang 2013-3-18下午3:44:34 end;
				}	
				// 归还人姓名
				vehicleEntity.setActualReturnUserCode(returnDto.getActualReturnUserCode());
				//交接情况是否异常 
				String takeRefId=returnDto.getRefId();
				//证件包Dto
				CertificatebagDto dto=new CertificatebagDto();
				//证件包Entity
				CertificatebagEntity entity=new CertificatebagEntity();
				//关联Id
				entity.setRefId(takeRefId);
				dto.setCertificatebagEntity(entity);
				
				//判断领取证件包是交接情况是否正常
				if(StringUtils.equals(CertificatebagConstant.HANDOVERSTATUS_NORMAL, returnQueryEntity.getHandOverStatus())){	
					// 交接情况 Y正常
					vehicleEntity.setHandOverStatus(returnDto.getVehicleHandOverStatus());					
				}else{
					// 交接情况 0 正常
					vehicleEntity.setHandOverStatus(CertificatebagConstant.HANDOVERSTATUS_ERROR);
				}	
				// 修改证件包信息
				certificateBagDao.updateCertificateBagAction(vehicleEntity);
			}else{
				// modify by liangfuxiang 2013-3-18下午3:52:18 begin 国际化
				// throw new TfrBusinessException("车牌号未被领取，请输入已被领取的车牌号码", "");
				throw new TfrBusinessException(CertificatebagConstant.VEHICLENO_NOT_TAKEN, CertificatebagConstant.BLANK_STRING);
				// modify by liangfuxiang 2013-3-18下午3:52:28 end;
			}
			
		}	
		//车柜Id
		String containerId=null;
		//货柜号是否为空
		if (!StringUtils.isBlank(returnDto.getContainerNo())) {
			//证件包Dto
			CertificatebagDto returnQueryDto=new CertificatebagDto();
			//车牌号
			returnQueryDto.setVehicleNo(returnDto.getContainerNo());
			//证件包entity
			CertificatebagEntity takeQueryEntity=new CertificatebagEntity();
			//领取的证件包状态
			takeQueryEntity.setCertificatebagStatus(CertificatebagConstant.CERTIFICATEBAG_RETURN);
			returnQueryDto.setCertificatebagEntity(takeQueryEntity);			
			CertificatebagEntity  returnQueryEntity=certificateBagDao.queryCertificatebag(returnQueryDto);
			if (returnQueryEntity==null) {
				// modify by liangfuxiang 2013-3-18下午3:58:57 begin 国际化
				// throw new TfrBusinessException("货柜号未被领取，请输入已被领取的车货柜码", "");
				throw new TfrBusinessException(CertificatebagConstant.CONTAINERNO_NOT_TAKEN, CertificatebagConstant.BLANK_STRING);
				// modify by liangfuxiang 2013-3-18下午3:59:18 end;
			}
			//证件包entity
			CertificatebagEntity containerEntity=new CertificatebagEntity();
			//主键id
			containerId=returnQueryEntity.getId();
			//货柜号ID
			containerEntity.setId(containerId);
			//货柜号
			containerEntity.setVehicleNo(returnDto.getContainerNo());
			// 业务类型
			containerEntity.setBusinessType(returnDto.getBusinessType());
			//证件包状态
			containerEntity.setCertificatebagStatus(CertificatebagConstant.CERTIFICATEBAG_TAKE);	
			//证件包类型	
			containerEntity.setCertificatebagType(CertificatebagConstant.CERTIFICATEBAG_TAKE);	
			//状态
			containerEntity.setStatus(CertificatebagConstant.CONTAINER);
			// 归还备注
			containerEntity.setActualReturnNotes(returnDto.getActualReturnNotes());		
			
			if (emp != null) {
				//归还操作人
				containerEntity.setActualReturnOperator(emp.getEmpName());
				// 计划领取地点
				containerEntity.setPlanTakeOrgCode(emp.getEmpCode());
				//创建人名字
				containerEntity.setCreateUserCode(emp.getEmpCode());
				//创建人Code
				containerEntity.setCreateUserName(emp.getEmpName());
				//修改时间
				containerEntity.setUpdateTime(new Date());
				//修改人Code
				containerEntity.setUpdateUserCode(emp.getEmpCode());
				//修改人姓名
				containerEntity.setUpdateUserName(emp.getEmpName());
				//日期
				containerEntity.setActualReturnTime(new  Date());
				//组织				
				OrgAdministrativeInfoEntity depart = emp.getDepartment();		
				if (depart != null) {
					//归还地点
					containerEntity.setActualReturnOrgCode(depart.getName());
					//修改部门
					containerEntity.setUpdateOrgCode(depart.getCode());
					//计划归还地点
					containerEntity.setPlanReturnOrgCode(depart.getCode());
				}
									
			}
			//得到员工信息
			EmployeeEntity employee=employeeService.queryEmployeeByEmpCode(returnDto.getActualReturnUserCode());
			if (employee!=null) {
				// 归还人姓名
				containerEntity.setActualReturnUserName(employee.getEmpName());
			}else if(StringUtils.isNotBlank(returnDto.getActualReturnUserName())){
				// 归还人姓名
				containerEntity.setActualReturnUserName(returnDto.getActualReturnUserName());
			}else{
				// modify by liangfuxiang 2013-3-18下午3:44:21 begin 国际化
				// throw new TfrBusinessException("归还人输入有误，请输入司机工号", "");
				throw new TfrBusinessException(CertificatebagConstant.RETURN_WRONG_EMPLOYEE_ID, CertificatebagConstant.BLANK_STRING);
				// modify by liangfuxiang 2013-3-18下午3:44:34 end;
			}
			containerEntity.setActualReturnUserCode(returnDto.getActualReturnUserCode());
			//判断交接情况是否正常
			if(StringUtils.equals(CertificatebagConstant.HANDOVERSTATUS_NORMAL, returnQueryEntity.getHandOverStatus())){
				//检查车辆证件是否全部选择  如果全部选择证件正常否则异常
				// 交接情况 Y 正常
				containerEntity.setHandOverStatus(returnDto.getContainerHandOverStatus());				
			}else{
				// 交接情况 N 异常
				containerEntity.setHandOverStatus(CertificatebagConstant.HANDOVERSTATUS_ERROR);
			}
			//添加车辆证件包动作action
			certificateBagDao.updateCertificateBagAction(containerEntity);
		}
		//归还证件包
		List<CertificatebagReturnEntity> returnList=new ArrayList<CertificatebagReturnEntity>();
		String vehiclehead[]=returnDto.getVehiclehead();
		//循环新增车辆证件
		if(vehiclehead!=null&&vehiclehead.length>0){
			for (int i = 0; i <vehiclehead.length; i++) {				
				CertificatebagReturnEntity permisoReturnEntity =new CertificatebagReturnEntity();
				//id
				permisoReturnEntity.setId(UUIDUtils.getUUID());
				//证件包动作ID	
				permisoReturnEntity.setCertificatebagActionId(vehicleId);		
				//证件包状态  车头证件	
				permisoReturnEntity.setType(CertificatebagConstant.VEHICLEHEAD_TYPE);	
				//证件类型
				permisoReturnEntity.setStatus(vehiclehead[i]);
				//创建时间
				permisoReturnEntity.setCreateTime(new Date());
				//修改时间
				permisoReturnEntity.setUpdateTime(new Date());
				if (emp != null) {  	
					//创建人
					permisoReturnEntity.setCreateUserCode(emp.getEmpCode());
					//创建人名字
					permisoReturnEntity.setCreateUserName(emp.getEmpName());
					//修改人
					permisoReturnEntity.setUpdateUserCode(emp.getEmpCode());
					//修改人名字
					permisoReturnEntity.setUpdateUserName(emp.getEmpName());				
					OrgAdministrativeInfoEntity depart = emp.getDepartment();
					if (depart != null) {
						//配载部门编号
						permisoReturnEntity.setCreateOrgCode(depart.getCode());
						//修改部门
						permisoReturnEntity.setUpdateOrgCode(depart.getCode());
					}		
				}
				returnList.add(permisoReturnEntity);//往集合中新增证件信息
			}
		}
		
		String vehicleType[]=returnDto.getVehicle();
		//循环新增车辆证件
		if(vehicleType!=null&&vehicleType.length>0){
			for (int i = 0; i <vehicleType.length; i++) {				
				CertificatebagReturnEntity containerPermisoEntity =new CertificatebagReturnEntity();
				//id
				containerPermisoEntity.setId(UUIDUtils.getUUID());
				//证件包动作ID	
				containerPermisoEntity.setCertificatebagActionId(vehicleId);	
				//证件包状态  车柜证件	
				containerPermisoEntity.setType(CertificatebagConstant.VEHICLE_TYPE);
				//证件类型
				containerPermisoEntity.setStatus(vehicleType[i]);
				//创建时间
				containerPermisoEntity.setCreateTime(new Date());
				//修改时间
				containerPermisoEntity.setUpdateTime(new Date());
				if (emp != null) {  	
					//创建人
					containerPermisoEntity.setCreateUserCode(emp.getEmpCode());
					//创建人名字
					containerPermisoEntity.setCreateUserName(emp.getEmpName());
					//修改人
					containerPermisoEntity.setUpdateUserCode(emp.getEmpCode());
					//修改人名字
					containerPermisoEntity.setUpdateUserName(emp.getEmpName());				
					OrgAdministrativeInfoEntity depart = emp.getDepartment();
					if (depart != null) {
						//配载部门编号
						containerPermisoEntity.setCreateOrgCode(depart.getCode());
						//修改部门
						containerPermisoEntity.setUpdateOrgCode(depart.getCode());
					}		
				}
				returnList.add(containerPermisoEntity);
			}
		}
		
		String container[]=returnDto.getContainer();
		//循环新增车辆证件
		if(container!=null&&container.length>0){
			for (int i = 0; i <container.length; i++) {				
				CertificatebagReturnEntity containerPermisoEntity =new CertificatebagReturnEntity();			
				containerPermisoEntity.setId(UUIDUtils.getUUID());//id
				containerPermisoEntity.setCertificatebagActionId(containerId);		//证件包动作ID	
				containerPermisoEntity.setType(CertificatebagConstant.CONTAINER_TYPE);//证件包状态  车柜证件			
				containerPermisoEntity.setStatus(container[i]);//证件类型
				containerPermisoEntity.setCreateTime(new Date());//创建时间
				containerPermisoEntity.setUpdateTime(new Date());//修改时间
				if (emp != null) {  	
					//创建人
					containerPermisoEntity.setCreateUserCode(emp.getEmpCode());
					//创建人名字
					containerPermisoEntity.setCreateUserName(emp.getEmpName());
					//修改人
					containerPermisoEntity.setUpdateUserCode(emp.getEmpCode());
					//修改人名字
					containerPermisoEntity.setUpdateUserName(emp.getEmpName());				
					OrgAdministrativeInfoEntity depart = emp.getDepartment();
					if (depart != null) {
						//配载部门编号
						containerPermisoEntity.setCreateOrgCode(depart.getCode());
						//修改部门
						containerPermisoEntity.setUpdateOrgCode(depart.getCode());
					}		
				}
				returnList.add(containerPermisoEntity);
			}
		}
				
		certificateBagDao.updateCertificateBagStatus(returnList);//批量新增归还的证件包证件
		
		return 1;
	}

	/**
	 * 
	 * 领取证件包
	 */
	@Override
	@Transactional	
	public int takeCertificateBag(CertificatebagTakeDto takeDto) {	
		UserEntity user = (UserEntity) FossUserContext.getCurrentUser();
		EmployeeEntity emp = user.getEmployee();
		// 关联id
		String refId=UUIDUtils.getUUID();
		//主键id
		String vehicleId=UUIDUtils.getUUID();
		//处理
		if (!StringUtils.isBlank(takeDto.getVehicleNo())) {
			CertificatebagDto dto=new CertificatebagDto();
			//车牌号
			dto.setVehicleNo(takeDto.getVehicleNo());
			CertificatebagEntity takeQueryEntity=new CertificatebagEntity();
			//证件包状态  未上交
			takeQueryEntity.setCertificatebagStatus(CertificatebagConstant.CERTIFICATEBAG_RETURN);
			dto.setCertificatebagEntity(takeQueryEntity);
			//查询已被领取的证件包信息
			CertificatebagEntity  takeInfoEntity=certificateBagDao.queryCertificatebag(dto);
			//如果没有找到   说明该车牌 可以领取
			if (takeInfoEntity==null) {
				//证件包动作entity
				CertificatebagEntity vehicleEntity=new CertificatebagEntity();//				
				vehicleEntity.setId(vehicleId);
				//车牌号
				vehicleEntity.setVehicleNo(takeDto.getVehicleNo());
				// 业务类型
				vehicleEntity.setBusinessType(takeDto.getBusinessType());
				//证件包状态	
				vehicleEntity.setCertificatebagStatus(CertificatebagConstant.CERTIFICATEBAG_RETURN);
				// 领取人姓名
//				vehicleEntity.setActualTakeUserName(takeDto.getActualTakeUserName());	
				// 领取时间
				vehicleEntity.setActualTakeTime(new Date());
				// 领取备注	
				vehicleEntity.setActualTakeNotes(takeDto.getActualTakeNotes());		
				//证件包类型
				vehicleEntity.setCertificatebagType(CertificatebagConstant.CERTIFICATEBAG_RETURN);	
				//当前创建时间	
				vehicleEntity.setCreateTime(new Date());
				//关联Id	
				vehicleEntity.setRefId(refId);
				//状态	
				vehicleEntity.setStatus(CertificatebagConstant.VEHICLE);	
				//根据code找name
				EmployeeEntity employee=employeeService.queryEmployeeByEmpCode(takeDto.getActualTakeUserCode());
				if (employee!=null) {
					// 归还人姓名
					vehicleEntity.setActualTakeUserName(employee.getEmpName());
				}else if(StringUtils.isNotBlank(takeDto.getActualTakeUserName())){
					// 归还人姓名
					vehicleEntity.setActualTakeUserName(takeDto.getActualTakeUserName());
				}else{
					// modify by liangfuxiang 2013-3-18下午4:04:02 begin 国际化
					// throw new TfrBusinessException("领取人输入有误，请输入司机工号", "");
					throw new TfrBusinessException(CertificatebagConstant.RECEIVER_WRONG_EMPID, CertificatebagConstant.BLANK_STRING);
					// modify by liangfuxiang 2013-3-18下午4:04:16 end;
				}
				//领取人编号
				vehicleEntity.setActualTakeUserCode(takeDto.getActualTakeUserCode());
				if (emp != null) {	
					//领取操作人	
					vehicleEntity.setActualTakeOperator(emp.getEmpName());
					vehicleEntity.setCreateUserCode(emp.getEmpCode());
					//创建人	
					vehicleEntity.setCreateUserName(emp.getEmpName());
					//修改时间	
					vehicleEntity.setUpdateTime(new Date());
					//修改人
					vehicleEntity.setUpdateUserCode(emp.getEmpCode());
					//修改人名字
					vehicleEntity.setUpdateUserName(emp.getEmpName());
					//组织信息
					OrgAdministrativeInfoEntity depart = emp.getDepartment();
					if (depart != null) {
						//配载部门编号
						vehicleEntity.setCreateOrgCode(depart.getCode());  
						// 领取地点
						vehicleEntity.setActualTakeOrgCode(depart.getCode());	
						//计划领取地点
						vehicleEntity.setPlanTakeOrgCode(depart.getCode());		
						//部门编号
						vehicleEntity.setUpdateOrgCode(depart.getCode());
					}		
				}
				//绑定任务车辆
				//任务车辆id
				String truckTaskId=null;
				//检查是否有长途车	
				List<TruckTaskEntity>  truckTaskList=certificateBagDao.verifyTruckDepartPlan(takeDto.getVehicleNo());	
				//如果找到任务车辆
				if (truckTaskList.size()>0) {
					String queryTruckTaskId=truckTaskList.get(0).getId();		
					//根据任务车辆id找证件包动作
					CertificatebagEntity cerEntity=certificateBagDao.queryByTruckTaskId(queryTruckTaskId);
					//如果证件包动作为空  绑定任务车辆  否则放入为空
					if (cerEntity==null) {
						truckTaskId=queryTruckTaskId;
					}
				}
				// 任务车辆ID 绑定任务车辆
				vehicleEntity.setTruckTaskId(truckTaskId);
				// 交接情况 Y正常
				vehicleEntity.setHandOverStatus(takeDto.getVehicleHandOverStatus());
				//添加车辆证件包动作action
				certificateBagDao.addCertificateBagAction(vehicleEntity);
			}else {
				// modify by liangfuxiang 2013-3-18下午4:10:47 begin 国际化
				// throw new TfrBusinessException("车牌号已被领取，请选择一个未被领取的车牌号", "");
				throw new TfrBusinessException(CertificatebagConstant.VEHICLENO_BEEN_TAKEN, CertificatebagConstant.BLANK_STRING);
				// modify by liangfuxiang 2013-3-18下午4:11:01 end;
			}
		}		
		
		String containerId=null;//车柜Id
		//货柜号是否为空
		if (!StringUtils.isBlank(takeDto.getContainerNo())) {
			CertificatebagDto dto=new CertificatebagDto();
			//货柜号
			dto.setVehicleNo(takeDto.getContainerNo());
			CertificatebagEntity takeQueryEntity=new CertificatebagEntity();
			//状态
			takeQueryEntity.setCertificatebagStatus(CertificatebagConstant.CERTIFICATEBAG_RETURN);
			dto.setCertificatebagEntity(takeQueryEntity);
			//查询已被领取的证件包信息
			CertificatebagEntity  takeInfoEntity=certificateBagDao.queryCertificatebag(dto);
			//如果没有找到   说明该车牌 可以领取
			if (takeInfoEntity==null) {
				//证件包entity
				CertificatebagEntity containerEntity=new CertificatebagEntity();//
				//主键id
				containerId=UUIDUtils.getUUID();
				containerEntity.setId(containerId);
				//货柜号
				containerEntity.setVehicleNo(takeDto.getContainerNo());
				// 业务类型
				containerEntity.setBusinessType(takeDto.getBusinessType());
				//证件包状态
				containerEntity.setCertificatebagStatus(CertificatebagConstant.CERTIFICATEBAG_RETURN);
				// 领取时间
				containerEntity.setActualTakeTime(new Date());
				// 领取备注
				containerEntity.setActualTakeNotes(takeDto.getActualTakeNotes());
				//证件包类型
				containerEntity.setCertificatebagType(CertificatebagConstant.CERTIFICATEBAG_RETURN);
				//当前创建时间	
				containerEntity.setCreateTime(new Date());	
				//关联Id
				containerEntity.setRefId(refId);		
				//状态
				containerEntity.setStatus(CertificatebagConstant.CONTAINER);	
				EmployeeEntity employee=employeeService.queryEmployeeByEmpCode(takeDto.getActualTakeUserCode());
				if (employee!=null) {
					// 领取人姓名
					containerEntity.setActualTakeUserName(employee.getEmpName());
				}else if(StringUtils.isNotBlank(takeDto.getActualTakeUserName())){
					// 归还人姓名
					containerEntity.setActualTakeUserName(takeDto.getActualTakeUserName());
				}else{
					// modify by liangfuxiang 2013-3-18下午4:04:02 begin 国际化
					// throw new TfrBusinessException("领取人输入有误，请输入司机工号", "");
					throw new TfrBusinessException(CertificatebagConstant.RECEIVER_WRONG_EMPID, CertificatebagConstant.BLANK_STRING);
					// modify by liangfuxiang 2013-3-18下午4:04:16 end;
				}
				//领取人编号
				containerEntity.setActualTakeUserCode(takeDto.getActualTakeUserCode());
				//检查车辆证件是否全部选择  如果全部选择证件正常否则异常
				// 交接情况 N 异常
				containerEntity.setHandOverStatus(takeDto.getContainerHandOverStatus());
				if (emp != null) {
					//领取操作人
					containerEntity.setActualTakeOperator(emp.getEmpName());	
					//创建人
					containerEntity.setCreateUserCode(emp.getEmpCode());
					//创建人名字
					containerEntity.setCreateUserName(emp.getEmpName());
					//创建时间
					containerEntity.setUpdateTime(new Date());
					//修改人
					containerEntity.setUpdateUserCode(emp.getEmpCode());
					//修改人姓名
					containerEntity.setUpdateUserName(emp.getEmpName());				
					OrgAdministrativeInfoEntity depart = emp.getDepartment();
					if (depart != null) {
						//配载部门编号
						containerEntity.setCreateOrgCode(depart.getCode());  
						// 领取地点	
						containerEntity.setActualTakeOrgCode(depart.getCode());
						//计划领取地点
						containerEntity.setPlanTakeOrgCode(depart.getCode());
						//修改部门
						containerEntity.setUpdateOrgCode(depart.getCode());
					}		
				}
				//添加车辆证件包动作action
				certificateBagDao.addCertificateBagAction(containerEntity);
			}else {
				// modify by liangfuxiang 2013-3-18下午4:15:04 begin 国际化
				// throw new TfrBusinessException("货柜号已被领取，请选择一个未被领取的货柜号", "");
				throw new TfrBusinessException(CertificatebagConstant.CONTAINERNO_BEEN_TAKEN, CertificatebagConstant.BLANK_STRING);
				// modify by liangfuxiang 2013-3-18下午4:15:26 end;
			}
			
		}
		
		
		//领取证件包
		List<CertificatebagTakeEntity> takeList=new ArrayList<CertificatebagTakeEntity>();
				
		String vehiclehead[]=takeDto.getVehiclehead();
		//循环新增车辆证件
		if(vehiclehead!=null&&vehiclehead.length>0){
			for (int i = 0; i <vehiclehead.length; i++) {				
				CertificatebagTakeEntity permisoReturnEntity =new CertificatebagTakeEntity();
				//id
				permisoReturnEntity.setId(UUIDUtils.getUUID());
				//证件包动作ID	
				permisoReturnEntity.setCertificatebagActionId(vehicleId);		
				//证件包状态  车头证件	
				permisoReturnEntity.setType(CertificatebagConstant.VEHICLEHEAD_TYPE);
				//证件类型
				permisoReturnEntity.setStatus(vehiclehead[i]);
				//创建时间
				permisoReturnEntity.setCreateTime(new Date());
				//修改时间
				permisoReturnEntity.setUpdateTime(new Date());
				if (emp != null) {  		
					//创建人
					permisoReturnEntity.setCreateUserCode(emp.getEmpCode());
					//创建人名字
					permisoReturnEntity.setCreateUserName(emp.getEmpName());
					//修改人
					permisoReturnEntity.setUpdateUserCode(emp.getEmpCode());
					//修改人名字
					permisoReturnEntity.setUpdateUserName(emp.getEmpName());	
					//组织
					OrgAdministrativeInfoEntity depart = emp.getDepartment();
					if (depart != null) {
						//创建部门编号
						permisoReturnEntity.setCreateOrgCode(depart.getCode());  
						//修改人部门编号
						permisoReturnEntity.setUpdateOrgCode(depart.getCode());
					}		
				}
				takeList.add(permisoReturnEntity);
			}
		}
		String vehicleType[]=takeDto.getVehicle();
		//循环新增车辆证件
		if(vehicleType!=null&&vehicleType.length>0){
			for (int i = 0; i <vehicleType.length; i++) {				
				CertificatebagTakeEntity containerPermisoEntity =new CertificatebagTakeEntity();	
				//id
				containerPermisoEntity.setId(UUIDUtils.getUUID());
				//证件包动作ID	
				containerPermisoEntity.setCertificatebagActionId(vehicleId);	
				//证件包状态  车柜证件	
				containerPermisoEntity.setType(CertificatebagConstant.VEHICLE_TYPE);	
				//证件类型
				containerPermisoEntity.setStatus(vehicleType[i]);
				//创建时间
				containerPermisoEntity.setCreateTime(new Date());
				//修改时间
				containerPermisoEntity.setUpdateTime(new Date());
				if (emp != null) {  		
					//创建人
					containerPermisoEntity.setCreateUserCode(emp.getEmpCode());
					//创建人名字
					containerPermisoEntity.setCreateUserName(emp.getEmpName());
					//修改人
					containerPermisoEntity.setUpdateUserCode(emp.getEmpCode());
					//修改人名字
					containerPermisoEntity.setUpdateUserName(emp.getEmpName());	
					//组织
					OrgAdministrativeInfoEntity depart = emp.getDepartment();
					if (depart != null) {
						//创建部门编号
						containerPermisoEntity.setCreateOrgCode(depart.getCode());  
						//修改人部门编号
						containerPermisoEntity.setUpdateOrgCode(depart.getCode());
					}		
				}
				takeList.add(containerPermisoEntity);
			}
		}
		
		String container[]=takeDto.getContainer();
		//循环新增车辆证件
		if(container!=null&&container.length>0){
			for (int i = 0; i <container.length; i++) {				
				CertificatebagTakeEntity containerEntity =new CertificatebagTakeEntity();	
				//id
				containerEntity.setId(UUIDUtils.getUUID());
				//证件包动作ID	
				containerEntity.setCertificatebagActionId(containerId);		
				//证件包状态  车柜证件	
				containerEntity.setType(CertificatebagConstant.CONTAINER_TYPE);		
				//证件类型
				containerEntity.setStatus(container[i]);
				//创建时间
				containerEntity.setCreateTime(new Date());
				//修改时间
				containerEntity.setUpdateTime(new Date());
				if (emp != null) {  		
					//创建人
					containerEntity.setCreateUserCode(emp.getEmpCode());
					//创建人名字
					containerEntity.setCreateUserName(emp.getEmpName());
					//修改人
					containerEntity.setUpdateUserCode(emp.getEmpCode());
					//修改人名字
					containerEntity.setUpdateUserName(emp.getEmpName());	
					//组织
					OrgAdministrativeInfoEntity depart = emp.getDepartment();
					if (depart != null) {
						//创建部门编号
						containerEntity.setCreateOrgCode(depart.getCode());  
						//修改人部门编号
						containerEntity.setUpdateOrgCode(depart.getCode());
					}		
				}
				takeList.add(containerEntity);
			}
		}		
		//批量新增证件  
		certificateBagDao.takeCertificateBag(takeList);	
		return 1;
		
	}

	/**
	 * 根据关联refid找到领取证件包信息
	 * 
	 * @author dp-liming
	 * @date 2012-11-2 上午11:36:10
	 */
	@Override
	public List<CertificatebagQueryEntity> queryTakeRefId(
			CertificatebagDto certificatebagDto) {
		//根据关联refid找到领取证件包信息
		return certificateBagDao.queryTakeRefId(certificatebagDto);
		
	}

	/**
	 * 根据关联refid找到领取证件包信息
	 * 
	 * @author dp-liming
	 * @date 2012-11-2 上午11:36:10
	 */
	@Override
	public List<CertificatebagQueryEntity> queryReturnByRefId(
			CertificatebagDto certificatebagDto) {
		//根据关联refid找到领取证件包信息
		return certificateBagDao.queryReturnByRefId(certificatebagDto);
		
	}
	
	
	/**
	 * 设置 证件包服务
	 *
	 * @param handOverStatus the new 交接情况
	 */
	public void setCertificateBagDao(ICertificateBagDao certificateBagDao) {
		this.certificateBagDao = certificateBagDao;
	}
	
	
	/**
	 * 获取 证件包服务.
	 *
	 * @return the 证件包服务
	 */
	public void setEmployeeService(IEmployeeService employeeService) {
		this.employeeService = employeeService;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.deppon.foss.module.transfer.management.api.server.service.ICertificateBagService#convertVehicleCode2Name(java.lang.String)
	 */

	@Override
	public String convertVehicleCode2Name(String vehicleNo) {
		// modify by liangfuxiang 2013-5-17下午1:28:13 begin TE-1733 
		//return Constants.convertVehicleCode2Name(vehicleNo);
		String newVehicleNo=Constants.convertVehicleCode2Name(vehicleNo);
		//查询车牌号是否真实存在，若存在，则返回，否则抛出异常
		if(verifyVehicleNo(newVehicleNo)){
			return newVehicleNo;
		}
		else{
			LOGGER.error("CertificateBagService[convertVehicleCode2Name()]:"+CertificatebagConstant.VEHICLENO_NOT_EXIST);
			throw new TfrBusinessException(CertificatebagConstant.VEHICLENO_NOT_EXIST);
		}
		// modify by liangfuxiang 2013-5-17下午1:28:22 end TE-1733 ;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.deppon.foss.module.transfer.management.api.server.service.ICertificateBagService#getContainerTakenInfo(com.deppon.foss.module.transfer.management.api.shared.dto.CertificatebagDto)
	 */

	/** 
	* @Title: verifyVehicleNo 
	* @Description: 判断车牌号是否存在
	* @param newVehicleNo
	* @return  设定文件 
	* @return boolean    返回类型 
	* @see verifyVehicleNo
	* @author: liangfuxiang liangfux@cn.ibm.com  
	* @version: 2013-5-17 下午1:34:56   
	* @throws 
	*/ 
	private boolean verifyVehicleNo(String newVehicleNo) {
		// 查询是否是已经启用的公司车
		int count = certificateBagDao.isOwnTruck(newVehicleNo);
		// 若不是公司车，则查看是否是已经启用的外请车
		if (count < 1) {
			count = certificateBagDao.isLeasedTruck(newVehicleNo);
			// 不是外请车
			if (count < 1) {
				// 非公司内部使用车
				return false;
			}
			else {
				// 是外请车
				return true;
			}
		}
		else {
			// 是公司车
			return true;
		}
	}

	@Override
	public CertificatebagDto getContainerTakenInfo(CertificatebagDto certificatebagDto) {
		//车牌号
		certificatebagDto.setVehicleNo(certificatebagDto.getContainerNo());
		//证件包entity
		CertificatebagEntity takeQueryEntity=new CertificatebagEntity();
		//领取的证件包状态
		takeQueryEntity.setCertificatebagStatus(CertificatebagConstant.CERTIFICATEBAG_RETURN);
		certificatebagDto.setCertificatebagEntity(takeQueryEntity);			
		CertificatebagEntity  returnQueryEntity=certificateBagDao.queryCertificatebag(certificatebagDto);
		//货柜号已领取
		if (returnQueryEntity!=null) {
			CertificatebagDto newCertificatebagDto=new CertificatebagDto(); 
			newCertificatebagDto.setCertificatebagEntity(returnQueryEntity);
			return newCertificatebagDto;
		}
		
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.deppon.foss.module.transfer.management.api.server.service.ICertificateBagService#queryContainerCardItemsByOrgCode()
	 */

	@Override
	public List<ConfigOrgRelationEntity> queryConfigItemsByConfType(String confType) throws TfrBusinessException{

		//入参有效性判断
		if(StringUtils.isEmpty(confType)){
			LOGGER.error("CertificateBagService[queryConfigItemsByConfType()]:"+CertificatebagConstant.CONF_TYPE_IS_NULL);
			throw new TfrBusinessException(CertificatebagConstant.CONF_TYPE_IS_NULL);
		}
		
		// 配置参数信息
		List<ConfigOrgRelationEntity> configOrgRelationEntityList = new ArrayList<ConfigOrgRelationEntity>();

		// 获取当前用户所在部门
		String orgCode = FossUserContext.getCurrentUser().getEmployee().getOrgCode();

		// 部门为空
		if (StringUtils.isEmpty(orgCode)) {
			// modify by liangfuxiang 2013-5-22下午1:29:34 ISSUE-2093 begin  总部可以统一配置，如配置办公机构后，各车队证件包明细页面，显示统一配置的证件明细；
//			4）当特殊部门自己配置证件包明细时，此部门证件包明细页面，就显示自己配置的证件包明细；
//			5)部门未配置证件包时，默认显示统一配置的信息。
//			6）证件包配置参数-配置项 查询页面 加入一列 “顶级组织”，此列结果集显示DIP对应的组织名称”办公门户机构人员“；
			// 则查询配置参数所有信息
			//configOrgRelationEntityList = queryConfigOrgInfoByConfType(confType);
			//查询顶级组织对应的配置项信息
			configOrgRelationEntityList= queryDipConfigOrgInfoByConfType(confType);
			// modify by liangfuxiang 2013-5-22下午1:29:43 end;
		}
		else {
			// modify by liangfuxiang 2013-5-22下午1:29:34 begin
			//configOrgRelationEntityList = queryConfigOrgInfoByUpOrgCode(confType, orgCode);
			configOrgRelationEntityList = queryConfigOrgInfoByConfTypeAndOrgCode(confType, orgCode);
			// modify by liangfuxiang 2013-5-22下午1:29:43 end;
		}

		//返回值为空,说明未配置配置项信息
		if(CollectionUtils.isEmpty(configOrgRelationEntityList)){
			LOGGER.error("CertificateBagService[queryConfigItemsByConfType()]:"+ConfigOrgRelationConstant.getCertificateBagCardTypeMap().get(confType)+ConfigOrgRelationConstant.CONFIGORGRELATIONENTITY_CONFCODE_NULL_AND_CONFTYPE);
			throw new TfrBusinessException(ConfigOrgRelationConstant.getCertificateBagCardTypeMap().get(confType)+ConfigOrgRelationConstant.CONFIGORGRELATIONENTITY_CONFCODE_NULL_AND_CONFTYPE);
		}
		
		return configOrgRelationEntityList;
	}

	/** 
	* @Title: queryConfigOrgInfoByConfTypeAndOrgCode 
	* @Description: 根据部门编码,配置项烈性查询有效的配置项信息
	* @param confType
	* @param orgCode
	* @return  设定文件 
	* @return List<ConfigOrgRelationEntity>    返回类型 
	* @see queryConfigOrgInfoByConfTypeAndOrgCode
	* @author: liangfuxiang liangfux@cn.ibm.com  
	* @version: 2013-5-22 下午2:08:30   
	* @throws 
	*/ 
	private List<ConfigOrgRelationEntity> queryConfigOrgInfoByConfTypeAndOrgCode(String confType, String orgCode) {
		
		//根据部门编码,配置项烈性查询有效的配置项信息
		List<ConfigOrgRelationEntity> configOrgRelationEntityList=configOrgRelationService.queryConfigOrgInfoByConfTypeAndOrgCode(confType,orgCode);
		//若未查询到组织配置项信息
		if(CollectionUtils.isEmpty(configOrgRelationEntityList)){
			//查询顶级组织对应的配置项信息
			configOrgRelationEntityList=queryDipConfigOrgInfoByConfType(confType);
		}
		return configOrgRelationEntityList;
	}

	/** 
	* @Title: queryDipConfigOrgInfoByConfType 
	* @Description: 查询顶级配置项信息
	* @param confType
	* @return  设定文件 
	* @return List<ConfigOrgRelationEntity>    返回类型 
	* @see queryDipConfigOrgInfoByConfType
	* @author: liangfuxiang liangfux@cn.ibm.com  
	* @version: 2013-5-22 下午1:29:14   
	* @throws 
	*/ 
	private List<ConfigOrgRelationEntity> queryDipConfigOrgInfoByConfType(String confType) {
		// 查询配置项信息
		List<ConfigOrgRelationEntity> configOrgRelationEntityList = configOrgRelationService.queryDipConfigOrgInfoByConfType(confType);
		return configOrgRelationEntityList;
	}

	/**
	 * 
	 * @Title: queryConfigOrgInfoByUpOrgCode
	 * @Description:查找上级部门对应的配置参数信息
	 * @param confType
	 * @param orgCode
	 * @return 设定文件
	 * @return List<ConfigOrgRelationEntity> 返回类型
	 * @see queryConfigOrgInfoByUpOrgCode
	 * @author: liangfuxiang liangfux@cn.ibm.com
	 * @version: 2013-4-13 下午2:06:10
	 * @throws
	 */
	// modify by liangfuxiang 2013-5-22下午2:28:54 begin ISSUE-2093 :根据这张单的需求，这个接口暂时作废，请勿删除，以防以后会使用到。
	public List<ConfigOrgRelationEntity> queryConfigOrgInfoByUpOrgCode(String confType, String orgCode) {

		List<ConfigOrgRelationEntity> configOrgRelationEntityList = new ArrayList<ConfigOrgRelationEntity>();
		// 查询上级部门编码对应的配置参数信息：
		List<OrgAdministrativeInfoEntity> orgAdministrativeInfoEntityList = orgAdministrativeInfoComplexService.queryOrgAdministrativeInfoEntityAllUpByCode(orgCode);

		// 若未查询到上级部门信息
		if (CollectionUtils.isEmpty(orgAdministrativeInfoEntityList)) {
			// 返回所有的配置关系信息
			configOrgRelationEntityList = queryConfigOrgInfoByConfType(confType);
		}
		else {
			// 获取当前部门信息
			OrgAdministrativeInfoEntity currentOrgAdministrativeInfoEntity = getCurrentOrgAdministrativeInfoEntity(orgCode, orgAdministrativeInfoEntityList);

			if (null != currentOrgAdministrativeInfoEntity) {

				int count = 0;
				// 查询上级配置项信息
				configOrgRelationEntityList = getConfigOrgRelationEntityListByUpOrgCode(currentOrgAdministrativeInfoEntity, orgAdministrativeInfoEntityList, confType, count);

			}
			if (CollectionUtils.isEmpty(configOrgRelationEntityList)) {
				// 若根据上级部门编码仍然无法获取配置项信息，则返回所有!
				configOrgRelationEntityList = queryConfigOrgInfoByConfType(confType);
			}
		}

		return configOrgRelationEntityList;
	}

	/**
	 * @param confType
	 * @Title: getConfigOrgRelationEntityListByUpOrgCode
	 * @Description: 递归查询上级配置项信息
	 * @param currentOrgAdministrativeInfoEntity
	 * @param orgAdministrativeInfoEntityList
	 * @return 设定文件
	 * @return List<ConfigOrgRelationEntity> 返回类型
	 * @see getConfigOrgRelationEntityListByUpOrgCode
	 * @author: liangfuxiang liangfux@cn.ibm.com
	 * @version: 2013-4-13 下午2:24:17
	 * @throws
	 */
	private List<ConfigOrgRelationEntity> getConfigOrgRelationEntityListByUpOrgCode(OrgAdministrativeInfoEntity currentOrgAdministrativeInfoEntity,
			List<OrgAdministrativeInfoEntity> orgAdministrativeInfoEntityList, String confType, int count) {

		List<ConfigOrgRelationEntity> configOrgRelationEntityList = new ArrayList<ConfigOrgRelationEntity>();

		// 计数，计算停止条件。
		if (count == orgAdministrativeInfoEntityList.size()) {
			return configOrgRelationEntityList;
		}

		count++;
		// 获取当前编码
		String currentCode = currentOrgAdministrativeInfoEntity.getCode();
		if (StringUtil.isNotEmpty(currentCode)) {
			ConfigOrgRelationEntity configOrgRelationEntity = new ConfigOrgRelationEntity();
			configOrgRelationEntity.setConfType(confType);
			configOrgRelationEntity.setOrgCode(currentCode);
			configOrgRelationEntityList = configOrgRelationService.queryConfigOrgRelationEntityListNoPage(configOrgRelationEntity);
			if (CollectionUtils.isEmpty(configOrgRelationEntityList)) {
				// 获取上级部门编码
				String parentCode = currentOrgAdministrativeInfoEntity.getParentOrgCode();

				if (StringUtil.isNotEmpty(parentCode)) {
					// 查询当前部门的配置参数信息
					currentOrgAdministrativeInfoEntity = getCurrentOrgAdministrativeInfoEntity(parentCode, orgAdministrativeInfoEntityList);
					if (null == currentOrgAdministrativeInfoEntity) {
						return configOrgRelationEntityList;
					}
					getConfigOrgRelationEntityListByUpOrgCode(currentOrgAdministrativeInfoEntity, orgAdministrativeInfoEntityList, confType, count);
				}
				else {
					return configOrgRelationEntityList;
				}

			}
		}

		return configOrgRelationEntityList;
	}

	/**
	 * 
	 * @Title: getCurrentOrgAdministrativeInfoEntity
	 * @Description: 获取当前部门信息
	 * @param orgCode
	 * @param orgAdministrativeInfoEntityList
	 * @param currentOrgAdministrativeInfoEntity
	 * @return 设定文件
	 * @return OrgAdministrativeInfoEntity 返回类型
	 * @see getCurrentOrgAdministrativeInfoEntity
	 * @author: liangfuxiang liangfux@cn.ibm.com
	 * @version: 2013-4-13 下午2:33:04
	 * @throws
	 */
	private OrgAdministrativeInfoEntity getCurrentOrgAdministrativeInfoEntity(String orgCode, List<OrgAdministrativeInfoEntity> orgAdministrativeInfoEntityList) {
		OrgAdministrativeInfoEntity orgAdministrativeInfoEntity = null;
		// 获取当前部门
		for (int i = 0; i < orgAdministrativeInfoEntityList.size(); i++) {
			orgAdministrativeInfoEntity = orgAdministrativeInfoEntityList.get(i);
			String code = orgAdministrativeInfoEntity.getCode();
			if (StringUtil.isNotEmpty(code) && orgCode.equalsIgnoreCase(code)) {
				// 获取当前部门信息
				return orgAdministrativeInfoEntity;
			}
		}
		return orgAdministrativeInfoEntity;
	}

	/**
	 * 
	 * @Title: queryConfigOrgInfoByConfType
	 * @Description: 查询出车头柜所有信息
	 * @param confType
	 * @return 设定文件
	 * @return List<ConfigOrgRelationEntity> 返回类型
	 * @see queryConfigOrgInfoByConfType
	 * @author: liangfuxiang liangfux@cn.ibm.com
	 * @version: 2013-4-13 上午11:52:47
	 * @throws
	 */
	private List<ConfigOrgRelationEntity> queryConfigOrgInfoByConfType(String confType) {
		// 查询出车头柜所有信息
		ConfigItemEntity configItemEntity = new ConfigItemEntity();
		// 设置配置项类型
		configItemEntity.setConfType(confType);
		// 查询配置项信息
		List<ConfigItemEntity> configItemEntityList = configItemEntityService.queryConfigItemEntitsByConfigType(configItemEntity);
		// 将configItemEntityList转换为configOrgRelationEntityList
		List<ConfigOrgRelationEntity> configOrgRelationEntityList = transalteConfigItemEntityList(configItemEntityList);
		return configOrgRelationEntityList;
	}

	/**
	 * @Title: transalteConfigItemEntityList
	 * @Description: 将configItemEntityList转换为configOrgRelationEntityList
	 * @param configItemEntityList
	 * @return 设定文件
	 * @return List<ConfigOrgRelationEntity> 返回类型
	 * @see transalteConfigItemEntityList
	 * @author: liangfuxiang liangfux@cn.ibm.com
	 * @version: 2013-4-13 上午11:38:11
	 * @throws
	 */
	private List<ConfigOrgRelationEntity> transalteConfigItemEntityList(List<ConfigItemEntity> configItemEntityList) {
		// 配置参数信息
		List<ConfigOrgRelationEntity> configOrgRelationEntityList = new ArrayList<ConfigOrgRelationEntity>();
		// 是否为空判断
		if (!CollectionUtils.isEmpty(configItemEntityList)) {
			ConfigOrgRelationEntity configOrgRelationEntity = null;
			for (ConfigItemEntity configItemEntity : configItemEntityList) {
				configOrgRelationEntity = new ConfigOrgRelationEntity();
				configOrgRelationEntity.setConfCode(configItemEntity.getConfCode());
				configOrgRelationEntity.setConfName(configItemEntity.getConfName());
				configOrgRelationEntityList.add(configOrgRelationEntity);
			}
		}

		return configOrgRelationEntityList;
	}

	  
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.deppon.foss.module.transfer.management.api.server.service.ICertificateBagService#convertVehicleCode2NameForQuery(java.lang.String)
	 */
	@Override
	public String convertVehicleCode2NameForQuery(String vehicleNo) {
		
		String newVehicleNo= Constants.convertVehicleCode2Name(vehicleNo);
		if(verifyVehicleNoWithOutActive(newVehicleNo)){
			return newVehicleNo;
		}
		else{
			LOGGER.error("CertificateBagService[convertVehicleCode2Name()]:"+CertificatebagConstant.VEHICLENO_NEVER_USE);
			throw new TfrBusinessException(CertificatebagConstant.VEHICLENO_NEVER_USE);
		}
	}

	/** 
	* @Title: verifyVehicleNoWithOutActive 
	* @Description: 判断是否是公司正在使用或使用过的车牌号
	* @param newVehicleNo
	* @return  设定文件 
	* @return boolean    返回类型 
	* @see verifyVehicleNoWithOutActive
	* @author: liangfuxiang liangfux@cn.ibm.com  
	* @version: 2013-5-17 下午3:04:37   
	* @throws 
	*/ 
	private boolean verifyVehicleNoWithOutActive(String newVehicleNo) {
		// 查询是否是已经启用的公司车
		int count = certificateBagDao.isOwnTruckWithoutActive(newVehicleNo);
		// 若不是公司车，则查看是否是已经启用的外请车
		if (count < 1) {
			count = certificateBagDao.isLeasedTruckWithoutActive(newVehicleNo);
			// 不是外请车
			if (count < 1) {
				// 非公司内部使用车
				return false;
			}
			else {
				// 是外请车
				return true;
			}
		}
		else {
			// 是公司车
			return true;
		}
		
	}
	
}