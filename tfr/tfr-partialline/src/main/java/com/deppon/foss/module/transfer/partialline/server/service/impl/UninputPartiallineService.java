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
 *  
 *  PROJECT NAME  : tfr-partialline
 *  
 *  PACKAGE NAME  : 
 * 
 *  DESCRIPTION   : 偏线外发管理
 *  
 *  FILE PATH     :src/main/java/com/deppon/foss/module/transfer/partialline/server/service/impl/UninputPartiallineService.java
 * 
 *  FILE NAME     :UninputPartiallineService.java
 *  
 *  AUTHOR        : FOSS中转开发组
 *  
 *  TIME          : 
 *  
 *  HOME PAGE     :  http://www.deppon.com
 *  
 *  COPYRIGHT     : Copyright (c) 2013  Deppon All Rights Reserved.
 * 
 *  VERSION       :0.1
 * 
 *  LAST MODIFY TIME:
 ******************************************************************************/
/*
 * PROJECT NAME: tfr-partialline
 * PACKAGE NAME: com.deppon.foss.module.transfer.partialline.server.service.impl
 * FILE    NAME: UninputPartiallineService.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */
package com.deppon.foss.module.transfer.partialline.server.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.base.util.define.BizTypeConstants;
import com.deppon.foss.module.base.baseinfo.api.server.service.complex.IOrgAdministrativeInfoComplexService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.transfer.partialline.api.server.dao.IExternalBillDao;
import com.deppon.foss.module.transfer.partialline.api.server.dao.IUninputPartiallineDao;
import com.deppon.foss.module.transfer.partialline.api.server.service.IUninputPartiallineService;
import com.deppon.foss.module.transfer.partialline.api.shared.define.PartiallineConstants;
import com.deppon.foss.module.transfer.partialline.api.shared.dto.ExternalBillDto;
import com.deppon.foss.module.transfer.partialline.api.shared.dto.HandoverBillDetailDto;
import com.deppon.foss.module.transfer.partialline.api.shared.exception.ExternalBillException;

/**
 * 未录入外发单查询Service
 * 
 * @author 096598-foss-zhongyubing
 * @date 2012-10-22 下午6:52:30
 */
public class UninputPartiallineService implements IUninputPartiallineService {

	/**
	 * 日志
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(UninputPartiallineService.class);
	/**
	 * 未录入偏线查询DAO接口
	 */
	private IUninputPartiallineDao uninputPartiallineDao;
	/**
	 * 偏线DAO接口
	 */
	private IExternalBillDao externalBillDao;
	/**
	 * 部门Service
	 */
	private IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService;
	

	public void setOrgAdministrativeInfoComplexService(
			IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService) {
		this.orgAdministrativeInfoComplexService = orgAdministrativeInfoComplexService;
	}

	/**
	 * 未录入偏线查询DAO接口
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2012-10-22 下午6:49:27
	 */
	public void setUninputPartiallineDao(IUninputPartiallineDao uninputPartiallineDao) {
		this.uninputPartiallineDao = uninputPartiallineDao;
	}

	/**
	 * 偏线DAO接口
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2012-12-25 上午8:33:10
	 */
	public void setExternalBillDao(IExternalBillDao externalBillDao) {
		this.externalBillDao = externalBillDao;
	}

	/**
	 * 查询未录入偏线外发单列表（查询交接类型为偏线，且未录入外发单的交接单明细）
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2012-10-22 上午11:00:38
	 * @see com.deppon.foss.module.transfer.partialline.api.server.service.IExternalBillService#queryUninputedpartial(com.deppon.foss.module.transfer.partialline.api.shared.dto.HandoverBillDetailDto,
	 *      int, int)
	 */
	@Override
	public List<HandoverBillDetailDto> queryUninputedpartial(HandoverBillDetailDto detailDto, int limit, int start,
			CurrentInfo currentInfo) {
		if (detailDto != null && currentInfo != null) {
			LOGGER.info("查询筛选的当前部门名称:" + currentInfo.getCurrentDeptName() + ",部门编码:" + currentInfo.getCurrentDeptCode());
			//queryTransCenterChildrenCodes
			//detailDto.setFilterOrgCode(currentInfo.getCurrentDeptCode());
			detailDto.setOrgCodes(queryTransCenterChildrenCodes(currentInfo.getCurrentDeptCode()));
		}
		return uninputPartiallineDao.queryUninputedpartial(detailDto, limit, start);
	}

	/**
	 * 查询未录入偏线外发单列表（查询交接类型为偏线，且未录入外发单的交接单明细）总条数
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2012-10-22 上午11:00:38
	 * @see com.deppon.foss.module.transfer.partialline.api.server.service.IExternalBillService#queryUninputedpartialCount(com.deppon.foss.module.transfer.partialline.api.shared.dto.HandoverBillDetailDto)
	 */
	@Override
	public Long queryUninputedpartialCount(HandoverBillDetailDto detailDto,CurrentInfo currentInfo) {
		if (detailDto != null && currentInfo != null) {
			LOGGER.info("查询筛选的当前部门名称:" + currentInfo.getCurrentDeptName() + ",部门编码:" + currentInfo.getCurrentDeptCode());
			//queryTransCenterChildrenCodes
			//detailDto.setFilterOrgCode(currentInfo.getCurrentDeptCode());
			detailDto.setOrgCodes(queryTransCenterChildrenCodes(currentInfo.getCurrentDeptCode()));
		}
		return uninputPartiallineDao.queryUninputedpartialCount(detailDto);
	}

	/**
	 * 查询运单号是否存在未录入的交接单
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2012-12-18 上午10:42:57
	 * @see com.deppon.foss.module.transfer.partialline.api.server.service.IUninputPartiallineService#queryHandedUninputWaybill(com.deppon.foss.module.transfer.partialline.api.shared.dto.ExternalBillDto)
	 */
	@Override
	public void queryHandedUninputWaybill(ExternalBillDto dto) {
		// 首先判断运单号,不为空
		if (dto != null && StringUtils.isNotBlank(dto.getWaybillNo())) {
			// 查询运单对应的交接明细是否存在
			Long listSize = externalBillDao.queryHandedUninputWaybill(dto);
			// 小于1
			if (listSize < PartiallineConstants.LIST_SIZE_ONE) {
				LOGGER.error("未查询到对应的未录入的已交接运单,请输入正确的单号:" + dto.getWaybillNo());
				throw new ExternalBillException("未查询到对应的未录入的已交接运单,请输入正确的单号", "");
			}
		}
	}
	

	/**
	 * 查询当前用户部门对应的外场下所有的子孙部门
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2013-4-11 上午10:02:45
	 */
	private List<String> queryTransCenterChildrenCodes(String currentDeptCode) {
		List<String> rs = null;
		// 当前部门所对应的外场编码
		String transCenterCode = "";
		OrgAdministrativeInfoEntity transCenter = querySuperiorOrgByOrgCode(currentDeptCode);
		if (transCenter != null) {
			transCenterCode = transCenter.getCode();
		}
		// 根据外场查询子孙部门编码
		if (StringUtils.isNotBlank(transCenterCode)) {
			List<OrgAdministrativeInfoEntity> orgs = orgAdministrativeInfoComplexService
					.queryOrgAdministrativeInfoEntityAllSubByCode(transCenterCode);
			if (CollectionUtils.isNotEmpty(orgs)) {
				rs = new ArrayList<String>();
				for (OrgAdministrativeInfoEntity org : orgs) {
					rs.add(org.getCode());
				}
			}
		}
		return rs;
	}
	
	
	/**
	 * 根据传入的部门code，获取该部门所属的外场
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2013-4-2 下午4:17:21
	 * @see com.deppon.foss.module.transfer.load.api.server.service.ILoadService#querySuperiorOrgByOrgCode(java.lang.String)
	 */
	private OrgAdministrativeInfoEntity querySuperiorOrgByOrgCode(String orgCode) {

		// 设置查询参数
		List<String> bizTypesList = new ArrayList<String>();
		// 外场类型
		bizTypesList.add(BizTypeConstants.ORG_TRANSFER_CENTER);
		// 查询上级部门
		OrgAdministrativeInfoEntity orgAdministrativeInfoEntity = orgAdministrativeInfoComplexService
				.queryOrgAdministrativeInfoIncludeSelfByCode(orgCode, bizTypesList);
		if (orgAdministrativeInfoEntity != null) {
			// 返回部门
			return orgAdministrativeInfoEntity;
		} else {
			// 获取上级部门失败
			LOGGER.error("################查询组织（code：" + orgCode + "）所属的上级部门失败(包括外场)！##########");
			return null;
		}
	}

}