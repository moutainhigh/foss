/*******************************************************************************
 * Copyright 2013 BSE TEAM
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
 * PROJECT NAME	: bse-baseinfo
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/service/impl/esb/SyncSalesDepartmentService.java
 * 
 * FILE NAME        	: SyncSalesDepartmentService.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
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
package com.deppon.foss.module.base.baseinfo.server.service.impl.esb;


import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Date;
import java.util.List;

import org.jgroups.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.esb.api.ESBJMSAccessor;
import com.deppon.esb.api.domain.AccessHeader;
import com.deppon.esb.core.exception.ESBException;
import com.deppon.esb.inteface.domain.oms.MotorcadeInfo;
import com.deppon.esb.inteface.domain.oms.MotorcadeServeDistrictInfo;
import com.deppon.esb.inteface.domain.oms.MotorcadeServeSalesAreaInfo;
import com.deppon.esb.inteface.domain.oms.SalesMotorcadeInfo;
import com.deppon.esb.inteface.domain.oms.SyncMotorcadeRequest;
import com.deppon.esb.pojo.transformer.jaxb.InformationSyncRequestTrans;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IEsbErrorLoggingDao;
import com.deppon.foss.module.base.baseinfo.api.server.service.esb.ISyncInformationService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.EsbErrorLogging;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.MotorcadeEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.MotorcadeServeDistrictEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.MotorcadeServeSalesAreaEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SalesMotorcadeEntity;
import com.deppon.foss.util.CollectionUtils;
import com.deppon.foss.util.define.FossConstants;

/**
 * 
 * 同步车队信息给OMS
 * 
 * @author
 * @date 
 */
public class SyncInformationService implements ISyncInformationService {

	private static final Logger log = LoggerFactory
			.getLogger(SyncInformationService.class);

	private static final String ESB_SERVICE_CODE = "ESB_FOSS2ESB_INFORMATION_TABLE";
	private static final String VERSION = "1.0";
	 private IEsbErrorLoggingDao esbErrorLoggingDao;
		public void setEsbErrorLoggingDao(IEsbErrorLoggingDao esbErrorLoggingDao) {
			this.esbErrorLoggingDao = esbErrorLoggingDao;
		}
	/**
	 * 
	 * 同步车队信息给OMS
	 * 
	 * @author 
	 * @date 
	 */

	@Override
	public void syncMotorcadeToOms(List<MotorcadeEntity> entitys, int operateType) {

		if (CollectionUtils.isEmpty(entitys)) {
			throw new BusinessException("传入的对象为空");
		}
		SyncMotorcadeRequest request = ConvertEsbEntity(entitys,operateType);
		
		// 创建服务头信息
		AccessHeader accessHeader = new AccessHeader();
		accessHeader.setEsbServiceCode(ESB_SERVICE_CODE);
		accessHeader.setBusinessId(UUID.randomUUID().toString());
		accessHeader.setBusinessDesc1("同步车队信息给OMS");
		accessHeader.setVersion(VERSION);
		long startTime = System.currentTimeMillis();
		EsbErrorLogging erlog = new EsbErrorLogging();
		try {
			erlog.setParamenter(new InformationSyncRequestTrans().fromMessage(request));
			erlog.setRequestSystem("ESB");
			erlog.setRequestTime(new Date());
			erlog.setMethodDesc("同步车队信息给OMS");
			erlog.setMethodName(this.getClass().getName()+".syncMotorcadeToOms");
			log.info("开始调用 同步车队信息给OMS：\n"
					+ new InformationSyncRequestTrans().fromMessage(request));
			ESBJMSAccessor.asynReqeust(accessHeader, request);
			long responseTime = (System.currentTimeMillis()-startTime); 
			if(responseTime>FossConstants.MAX_RESPONSE_TIME){
				erlog.setCreateTime(new Date());
				erlog.setErrorMessage("响应超时");
				erlog.setResponseTime(responseTime);
				esbErrorLoggingDao.addErrorMessage(erlog);
			}
			log.info("结束调用 同步车队信息给OMS：\n"+ new InformationSyncRequestTrans().fromMessage(request));
		} catch (ESBException e) {
			//esb发生异常未进行处理？？？？？是否要修改
			e.printStackTrace();
			log.error(e.getMessage(), e);
			erlog.setResponseTime(System.currentTimeMillis()-startTime);
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			erlog.setCreateTime(new Date());
			e.printStackTrace(pw);
			erlog.setErrorMessage(sw.toString());
			esbErrorLoggingDao.addErrorMessage(erlog);
		}
		log.info("send ExpressDeliveryRegions to GW System ：同步数据到OMS \n");
		
	}
	private SyncMotorcadeRequest ConvertEsbEntity(List<MotorcadeEntity> list, int operateType) {
		SyncMotorcadeRequest request = new SyncMotorcadeRequest();
		
		List<MotorcadeInfo> motorcadeInfos = request.getMotorcadeInfo();
		MotorcadeInfo motorcadeInfo = null;
		for(MotorcadeEntity entity : list){
			motorcadeInfo = new MotorcadeInfo();
			motorcadeInfo.setActive(entity.getActive());
			motorcadeInfo.setCode(entity.getCode());
			motorcadeInfo.setCreateTime(entity.getCreateDate());
			motorcadeInfo.setCreateUserCode(entity.getCreateUser());
			motorcadeInfo.setDispatchTeam(entity.getDispatchTeam());
			motorcadeInfo.setFleetType(entity.getFleetType());
			motorcadeInfo.setId(entity.getId());
			motorcadeInfo.setIsManageVehicle(entity.getIsManageVehicle());
			motorcadeInfo.setIsTopMotorcade(entity.getIsTopFleet());
			motorcadeInfo.setModifyTime(entity.getModifyDate());
			motorcadeInfo.setModifyUserCode(entity.getModifyUser());
			motorcadeInfo.setName(entity.getName());
			motorcadeInfo.setOperatorSign(operateType);
			motorcadeInfo.setParentOrgCode(entity.getParentOrgCode());
			motorcadeInfo.setPinyin(entity.getPinyin());
			motorcadeInfo.setServeBillTerm(entity.getServeBillTerm());
			motorcadeInfo.setService(entity.getService());
			motorcadeInfo.setServiceCode(entity.getServiceCode());
			motorcadeInfo.setServiceTeam(entity.getServiceTeam());
			motorcadeInfo.setTransferCenter(entity.getTransferCenter());
			//车队负责的行政区域
			MotorcadeServeDistrictInfo motorcadeServeDistrictInfo = null;
			//新增的集合
			if(null != entity.getMotorcadeServeDistrictEntityAddList() && entity.getMotorcadeServeDistrictEntityAddList().size()>0){
			for(MotorcadeServeDistrictEntity motoEntity : entity.getMotorcadeServeDistrictEntityAddList()){
				motorcadeServeDistrictInfo = new MotorcadeServeDistrictInfo();
				motorcadeServeDistrictInfo.setActive(motoEntity.getActive());
				motorcadeServeDistrictInfo.setCreateTime(motoEntity.getCreateDate());
				motorcadeServeDistrictInfo.setCreateUserCode(motoEntity.getCreateUser());
				motorcadeServeDistrictInfo.setId(motoEntity.getId());
				motorcadeServeDistrictInfo.setModifyTime(motoEntity.getModifyDate());
				motorcadeServeDistrictInfo.setModifyUserCode(motoEntity.getModifyUser());
				motorcadeServeDistrictInfo.setMotorcadeCode(motoEntity.getMotorcadeCode());
				motorcadeServeDistrictInfo.setMotorcadeName(motoEntity.getMotorcadeName());
				motorcadeServeDistrictInfo.setVirtualCode(motoEntity.getVirtualCode());
				motorcadeServeDistrictInfo.setDistrictCode(motoEntity.getDistrictCode());
				motorcadeServeDistrictInfo.setDistrictName(motoEntity.getDistrictName());
				motorcadeInfo.getMotorcadeServeDistrictInfo().add(motorcadeServeDistrictInfo);
				
			}
			}
			//删除的集合
			if(null != entity.getMotorcadeServeDistrictEntityDeleteList() && entity.getMotorcadeServeDistrictEntityDeleteList().size()>0){
				for(MotorcadeServeDistrictEntity motoEntity : entity.getMotorcadeServeDistrictEntityDeleteList()){
					motorcadeServeDistrictInfo = new MotorcadeServeDistrictInfo();
					motorcadeServeDistrictInfo.setActive(motoEntity.getActive());
					motorcadeServeDistrictInfo.setCreateTime(motoEntity.getCreateDate());
					motorcadeServeDistrictInfo.setCreateUserCode(motoEntity.getCreateUser());
					motorcadeServeDistrictInfo.setId(motoEntity.getId());
					motorcadeServeDistrictInfo.setModifyTime(motoEntity.getModifyDate());
					motorcadeServeDistrictInfo.setModifyUserCode(motoEntity.getModifyUser());
					motorcadeServeDistrictInfo.setMotorcadeCode(motoEntity.getMotorcadeCode());
					motorcadeServeDistrictInfo.setMotorcadeName(motoEntity.getMotorcadeName());
					motorcadeServeDistrictInfo.setVirtualCode(motoEntity.getVirtualCode());
					motorcadeServeDistrictInfo.setDistrictCode(motoEntity.getDistrictCode());
					motorcadeServeDistrictInfo.setDistrictName(motoEntity.getDistrictName());
					motorcadeInfo.getMotorcadeServeDistrictInfo().add(motorcadeServeDistrictInfo);
					
				}
				}
			//车队负责的营业区域
			MotorcadeServeSalesAreaInfo motorcadeServeSalesAreaInfo = null;
			//新增的集合
			if(null != entity.getMotorcadeServeSalesAreaEntityAddList() && entity.getMotorcadeServeSalesAreaEntityAddList().size()>0){
			for(MotorcadeServeSalesAreaEntity motoEntity : entity.getMotorcadeServeSalesAreaEntityAddList()){
				motorcadeServeSalesAreaInfo = new MotorcadeServeSalesAreaInfo();
				motorcadeServeSalesAreaInfo.setActive(motoEntity.getActive());
				motorcadeServeSalesAreaInfo.setCreateTime(motoEntity.getCreateDate());
				motorcadeServeSalesAreaInfo.setCreateUserCode(motoEntity.getCreateUser());
				motorcadeServeSalesAreaInfo.setId(motoEntity.getId());
				motorcadeServeSalesAreaInfo.setModifyTime(motoEntity.getModifyDate());
				motorcadeServeSalesAreaInfo.setModifyUserCode(motoEntity.getModifyUser());
				motorcadeServeSalesAreaInfo.setMotorcadeCode(motoEntity.getMotorcadeCode());
				motorcadeServeSalesAreaInfo.setMotorcadeName(motoEntity.getMotorcadeName());
				motorcadeServeSalesAreaInfo.setVirtualCode(motoEntity.getVirtualCode());
				motorcadeServeSalesAreaInfo.setSalesareaCode(motoEntity.getSalesareaCode());
				motorcadeServeSalesAreaInfo.setSalesareaName(motoEntity.getSalesareaName());
				motorcadeInfo.getMotorcadeServeSalesAreaInfo().add(motorcadeServeSalesAreaInfo);
				
			}
			}
			//删除的集合
			if(null != entity.getMotorcadeServeSalesAreaEntityDeleteList() && entity.getMotorcadeServeSalesAreaEntityDeleteList().size()>0){
				for(MotorcadeServeSalesAreaEntity motoEntity : entity.getMotorcadeServeSalesAreaEntityDeleteList()){
					motorcadeServeSalesAreaInfo = new MotorcadeServeSalesAreaInfo();
					motorcadeServeSalesAreaInfo.setActive(motoEntity.getActive());
					motorcadeServeSalesAreaInfo.setCreateTime(motoEntity.getCreateDate());
					motorcadeServeSalesAreaInfo.setCreateUserCode(motoEntity.getCreateUser());
					motorcadeServeSalesAreaInfo.setId(motoEntity.getId());
					motorcadeServeSalesAreaInfo.setModifyTime(motoEntity.getModifyDate());
					motorcadeServeSalesAreaInfo.setModifyUserCode(motoEntity.getModifyUser());
					motorcadeServeSalesAreaInfo.setMotorcadeCode(motoEntity.getMotorcadeCode());
					motorcadeServeSalesAreaInfo.setMotorcadeName(motoEntity.getMotorcadeName());
					motorcadeServeSalesAreaInfo.setVirtualCode(motoEntity.getVirtualCode());
					motorcadeServeSalesAreaInfo.setSalesareaCode(motoEntity.getSalesareaCode());
					motorcadeServeSalesAreaInfo.setSalesareaName(motoEntity.getSalesareaName());
					motorcadeInfo.getMotorcadeServeSalesAreaInfo().add(motorcadeServeSalesAreaInfo);
					
				}
				}
			//营业部车队关系
			SalesMotorcadeInfo salesMotorcadeInfo = null;
			//新增的集合
			if(null != entity.getMotorcadeServeSalesDeptEntityAddList() && entity.getMotorcadeServeSalesDeptEntityAddList().size()>0){
			for(SalesMotorcadeEntity motoEntity : entity.getMotorcadeServeSalesDeptEntityAddList()){
				salesMotorcadeInfo = new SalesMotorcadeInfo();
				salesMotorcadeInfo.setActive(motoEntity.getActive());
				salesMotorcadeInfo.setCreateTime(motoEntity.getCreateDate());
				salesMotorcadeInfo.setCreateUserCode(motoEntity.getCreateUser());
				salesMotorcadeInfo.setId(motoEntity.getId());
				salesMotorcadeInfo.setModifyTime(motoEntity.getModifyDate());
				salesMotorcadeInfo.setModifyUserCode(motoEntity.getModifyUser());
				salesMotorcadeInfo.setMotorcadeCode(motoEntity.getMotorcadeCode());
				salesMotorcadeInfo.setSalesdeptCode(motoEntity.getSalesdeptCode());
				salesMotorcadeInfo.setVirtualCode(motoEntity.getVirtualCode());
				motorcadeInfo.getSalesMotorcadeInfo().add(salesMotorcadeInfo);
				
			}
			}
			//删除的集合
			if(null != entity.getMotorcadeServeSalesDeptEntityDeleteList() && entity.getMotorcadeServeSalesDeptEntityDeleteList().size()>0){
				for(SalesMotorcadeEntity motoEntity : entity.getMotorcadeServeSalesDeptEntityDeleteList()){
					salesMotorcadeInfo = new SalesMotorcadeInfo();
					salesMotorcadeInfo.setActive(motoEntity.getActive());
					salesMotorcadeInfo.setCreateTime(motoEntity.getCreateDate());
					salesMotorcadeInfo.setCreateUserCode(motoEntity.getCreateUser());
					salesMotorcadeInfo.setId(motoEntity.getId());
					salesMotorcadeInfo.setModifyTime(motoEntity.getModifyDate());
					salesMotorcadeInfo.setModifyUserCode(motoEntity.getModifyUser());
					salesMotorcadeInfo.setMotorcadeCode(motoEntity.getMotorcadeCode());
					salesMotorcadeInfo.setSalesdeptCode(motoEntity.getSalesdeptCode());
					salesMotorcadeInfo.setVirtualCode(motoEntity.getVirtualCode());
					motorcadeInfo.getSalesMotorcadeInfo().add(salesMotorcadeInfo);
					
				}
				}
			motorcadeInfos.add(motorcadeInfo);
		}
		
		return request;
	}
	}
	

	
	
	
	

