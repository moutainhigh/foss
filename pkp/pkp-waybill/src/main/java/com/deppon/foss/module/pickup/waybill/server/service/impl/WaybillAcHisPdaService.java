/**
 *  initial comments.
 */
/*******************************************************************************
 * Copyright 2013 PKP
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *    http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * PROJECT NAME	: pkp-waybill
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/waybill/server/service/impl/WaybillAcHisPdaService.java
 * 
 * FILE NAME        	: WaybillAcHisPdaService.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
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
package com.deppon.foss.module.pickup.waybill.server.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.Map;
import java.util.Set;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.server.service.IEmployeeService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.EmployeeEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.pickup.waybill.api.server.dao.IWaybillAcHisPdaDao;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillAcHisPdaService;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillAcHisPdaEntity;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillPdaDto;
import com.deppon.foss.module.pickup.waybill.shared.exception.WaybillImportException;

/**
 * 
 * pda WAYBILL SERVICE
 * 
 * @author 026113-foss-linwensheng
 * @date 2012-10-22 下午6:43:03
 */
public class WaybillAcHisPdaService implements IWaybillAcHisPdaService {

	/**
	 * 日志
	 */
	protected static final Logger LOG = LoggerFactory.getLogger(WaybillAcHisPdaService.class.getName());

	private static final String WAYBILL_PENDING_ID = "waybillPendingId";

	private IWaybillAcHisPdaDao waybillAcHisPdaDao;
	
	/**
	 * 组织接口
	 */
	private IOrgAdministrativeInfoService orgAdministrativeInfoService;
	
	
	/**
	 * 人员接口
	 */
	private IEmployeeService employeeService;
	
	
	
	
	

	public IOrgAdministrativeInfoService getOrgAdministrativeInfoService() {
		return orgAdministrativeInfoService;
	}

	public void setOrgAdministrativeInfoService(
			IOrgAdministrativeInfoService orgAdministrativeInfoService) {
		this.orgAdministrativeInfoService = orgAdministrativeInfoService;
	}

	public IEmployeeService getEmployeeService() {
		return employeeService;
	}

	public void setEmployeeService(IEmployeeService employeeService) {
		this.employeeService = employeeService;
	}


	public void setWaybillAcHisPdaDao(IWaybillAcHisPdaDao waybillAcHisPdaDao) {
		this.waybillAcHisPdaDao = waybillAcHisPdaDao;
	}

	/**
	 * 新增运单PDA操作记录
	 * 
	 * @author 043260-foss-suyujun
	 * @date 2012-12-11
	 * @param entity
	 * @return
	 * @see com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillAcHisPdaService#createWaybillAcHisPdaEntity(com.deppon.foss.module.pickup.waybill.shared.domain.WaybillAcHisPdaEntity)
	 */
	@Override
	public int createWaybillAcHisPdaEntity(WaybillAcHisPdaEntity entity) {
		return waybillAcHisPdaDao.insert(entity);
	}

	/**
	 * 删除运单PDA操作记录
	 * 
	 * @author 043260-foss-suyujun
	 * @date 2012-12-11
	 * @param id
	 * @return
	 * @see com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillAcHisPdaService#deleteByPrimaryKey(java.lang.String)
	 */
	@Override
	public int deleteByPrimaryKey(String id) {
		return waybillAcHisPdaDao.deleteByPrimaryKey(id);
	}

	/**
	 * 
	 * @author 043260-foss-suyujun
	 * @date 2012-12-11
	 * @param entity
	 * @return
	 * @see com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillAcHisPdaService#updateWaybillAcHisPdaEntityById(com.deppon.foss.module.pickup.waybill.shared.domain.WaybillAcHisPdaEntity)
	 */
	@Override
	public int updateWaybillAcHisPdaEntityById(WaybillAcHisPdaEntity entity) {
		return waybillAcHisPdaDao.updateWaybillAcHisPdaEntity(entity);
	}

	/**
	 * 
	 * <p>
	 * 运单补录（PDA，订单导入）记录更改历史
	 * </p>
	 * 
	 * @author foss-gengzhe
	 * @date 2012-12-20 下午2:47:50
	 * @param newWaybillPda
	 * @param oldWaybillPda
	 * @see com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillAcHisPdaService#addWaybillAcHisPdaEntity(com.deppon.foss.module.pickup.waybill.shared.dto.WaybillPdaDto,
	 *      com.deppon.foss.module.pickup.waybill.shared.dto.WaybillPdaDto)
	 */
	@SuppressWarnings("unchecked")
	public void addWaybillAcHisPdaEntity(WaybillPdaDto newWaybillPda, WaybillPdaDto oldWaybillPda) {
		if ("".equals(StringUtil.defaultIfNull(newWaybillPda.getWaybillPendingId())) || "".equals(StringUtil.defaultIfNull(oldWaybillPda.getWaybillPendingId()))) {
			throw new WaybillImportException(WaybillImportException.WAYBILLPENDINGID_NULL);
		}

		if (newWaybillPda.getOperateTime() == null || oldWaybillPda.getOperateTime() == null) {
			throw new WaybillImportException(WaybillImportException.OPERATETIME_NULL);
		}

		try {
			// 对比补录前后运单信息
			Map<String, Object> newMap = BeanUtils.describe(newWaybillPda);

			Set<String> propertyNames = newMap.keySet();
			Object newValue;
			Object oldValue;
			String newValueString = "";
			String oldValueString = "";
			// 定义待补录PDA运单ID
			String pendingId = BeanUtils.getProperty(oldWaybillPda, WAYBILL_PENDING_ID);
			/**
			 * DMANA-3119 @liyongfei 在操作历史表中加入运单号字段，表示由补录PDA开单，与运单表相关联的
			 */
			// 补录运单的运单编号
			String waybillNo = oldWaybillPda.getWaybillNo();
			for(String propertyName : propertyNames){
				newValue = newMap.get(propertyName);
				oldValue = BeanUtils.getProperty(oldWaybillPda, propertyName);
				if("licensePlateNum".equals(propertyName)){
					//零担的运单补录，车牌号不会发生改变
					newValue= oldValue;
				}
				if (newValue != null) {
					newValueString = newValue.toString();
				}
				if (oldValue != null) {
					oldValueString = oldValue.toString();
				}
				// 记录更改历史,应DMANA-3119FOSS货物轨迹查询优化需求 要求，即使件数没有发生变更，pda补录时候也把件数的记录保存下来
				if (!newValueString.equals(oldValueString)) {
					WaybillAcHisPdaEntity waybillAcHisPda = new WaybillAcHisPdaEntity();
					waybillAcHisPda.setActionItem(propertyName);
					waybillAcHisPda.setAfterChange(newValueString);
					waybillAcHisPda.setBeforeChange(oldValueString);
					waybillAcHisPda.settSrvWaybillPending(pendingId);
					EmployeeEntity employee = employeeService.queryEmployeeByEmpCode(oldWaybillPda.getCreateUserCode());
					OrgAdministrativeInfoEntity orgAdministrativeInfoEntity = null;
					if(employee!=null && employee.getOrgCode()!=null){
						orgAdministrativeInfoEntity = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(employee.getOrgCode());
					}
					//定价体系优化项目POP-563---判空
					if(employee!=null){
						waybillAcHisPda.setOperatorOrgCode(employee.getOrgCode());
						waybillAcHisPda.setOperator(employee.getEmpName());
					}
					if(orgAdministrativeInfoEntity!=null){
						waybillAcHisPda.setOperatorOrg(orgAdministrativeInfoEntity.getName());
					}
//					waybillAcHisPda.setOperatorOrgCode(FossUserContext.getCurrentInfo().getCurrentDeptCode());
//					waybillAcHisPda.setOperatorOrg(FossUserContext.getCurrentInfo().getCurrentDeptName());
					waybillAcHisPda.setOperateTime(new Date());
					waybillAcHisPda.setOperatorCode(oldWaybillPda.getCreateUserCode());
//					waybillAcHisPda.setOperator(FossUserContext.getCurrentInfo().getEmpName());
//					waybillAcHisPda.setOperatorCode(FossUserContext.getCurrentInfo().getEmpCode());
					waybillAcHisPda.setWaybillNo(waybillNo);
					waybillAcHisPdaDao.insert(waybillAcHisPda);
				}
			}
		} catch (IllegalAccessException e) {
			LOG.error("exception", e);
		} catch (InvocationTargetException e) {
			LOG.error("exception", e);
		} catch (NoSuchMethodException e) {
			LOG.error("exception", e);
		}
	}
}