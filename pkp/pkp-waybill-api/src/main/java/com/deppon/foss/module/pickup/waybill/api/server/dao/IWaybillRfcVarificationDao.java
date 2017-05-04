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
 * PROJECT NAME	: pkp-waybill-api
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/waybill/api/server/dao/IWaybillRfcVarificationDao.java
 * 
 * FILE NAME        	: IWaybillRfcVarificationDao.java
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
package com.deppon.foss.module.pickup.waybill.api.server.dao;

import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.CusAccountEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillRfcActionHistoryEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillRfcAgentEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillRfcAgentPersonEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillRfcChangeDetailEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillRfcCondition;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillRfcProofEntity;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillRfcChangeDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillRfcPrintDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillRfcQueryAgentConDto;

/**
 * 
 * <p>
 * 更改单受理相关查询接口方法定义<br />
 * </p>
 * @title IWaybillRfcChangeDao.java
 * @package com.deppon.foss.module.pickup.waybill.api.server.dao 
 * @author suyujun
 * @version 0.1 2012-11-27
 */
public interface IWaybillRfcVarificationDao {
	/**
	 * 
	 * <p>
	 * 依据查询条件进行更改单信息查询<br />
	 * </p>
	 * @author suyujun
	 * @version 0.1 2012-11-27
	 * @param condition
	 * @return
	 * List<WaybillRfcChangeDto>
	 */
	List<WaybillRfcChangeDto> queryWaybillRfcVarifyDto(WaybillRfcCondition condition);

	/**
	 * 
	 * <p>
	 * 依据更改单ID查询凭证<br />
	 * </p>
	 * @author suyujun
	 * @version 0.1 2012-11-28
	 * @param waybillRfcId
	 * @return
	 * List<WaybillRfcProofEntity>
	 */
	List<WaybillRfcProofEntity> queryWayBillRfcProofByRfcId(String waybillRfcId);
	
	
	/**
	 * 
	 * <p>
	 * 依据更改单ID查询审核受理信息<br />
	 * </p>
	 * @author helong
	 * @version 0.1 2013-06-18
	 * @param waybillRfcId
	 * @return
	 */
	List<WaybillRfcActionHistoryEntity> queryWayBillRfcActionHistory(String waybillRfcId);

	/**
	 * 
	 * <p>
	 * 新增更改单操作记录<br />
	 * </p>
	 * @author suyujun
	 * @version 0.1 2012-11-28
	 * @param actionHistory
	 * void
	 */
	void addWaybillRfcActionHistory(WaybillRfcActionHistoryEntity actionHistory);
	
	/**
	 * 
	 * <p>根据变更单号查询变更明细</p> 
	 * @author foss-gengzhe
	 * @date 2012-12-3 下午3:20:22
	 * @param waybillRfcId
	 * @return
	 * @see
	 */
	List<WaybillRfcChangeDetailEntity> queryWayBillRfcChangeDetailByRfcId(String waybillRfcId);

	/**
	 * 审核受理状态查询
	 * @author 043260-foss-suyujun
	 * @date 2012-12-7
	 * @param condition
	 * @return
	 * @return List<WaybillRfcChangeDto>
	 * @see
	 */
	List<WaybillRfcChangeDto> queryWaybillRfcChkAndPro(WaybillRfcCondition condition);

	/**
	 * 根据单号查询打印信息
	 * @author 043260-foss-suyujun
	 * @date 2012-12-10
	 * @param waybillNo
	 * @return
	 * @return WaybillRfcPrintDto
	 * @see
	 */
	WaybillRfcPrintDto queryWaybillRfcPrintDto(String waybillNo);
	/**
	 * 根据更改单ID 查询更改单打印信息
	 * @author 097972-foss-dengtingting
	 * @date 2013-4-9 下午4:01:09
	 */
	WaybillRfcPrintDto queryWaybillRfcPrintDtoByRfcid(String rfcId);

	/**
	 * 
	 * @author 043260-foss-suyujun
	 * @date 2012-12-24
	 * @param entity
	 * @return WaybillRfcAgentEntity
	 */
	WaybillRfcAgentEntity addWayBillRfcAgent(WaybillRfcAgentEntity entity);

	/**
	 * 新增更改代理人员信息
	 * @author 043260-foss-suyujun
	 * @date 2012-12-24
	 * @param agent
	 * @return void
	 */
	void addWayBillRfcAgentPerson(WaybillRfcAgentPersonEntity agent);

	/**
	 * 查询审核代理数据
	 * @author 043260-foss-suyujun
	 * @date 2012-12-24
	 * @param condition
	 * @return List<WaybillRfcAgentEntity>
	 */
	List<WaybillRfcAgentEntity> queryWaybillRfcAgent(WaybillRfcQueryAgentConDto condition);

	/**
	 * 根据代理实体ID查询代理对象
	 * @author 043260-foss-suyujun
	 * @date 2012-12-26
	 * @param id
	 * @return WaybillRfcAgentEntity
	 */
	WaybillRfcAgentEntity queryWaybillRfcAgentById(String id);

	/**
	 * 删除代理人实体
	 * @author 043260-foss-suyujun
	 * @date 2012-12-26
	 * @param entity
	 * @return void
	 */
	void deleteWaybillRfcAgentPerson(WaybillRfcAgentPersonEntity agentPerson);

	/**
	 * 删除代理实体
	 * @author 043260-foss-suyujun
	 * @date 2012-12-26
	 * @param originalEntity
	 * @return void
	 */
	void deleteWaybillRfcAgent(WaybillRfcAgentEntity originalEntity);

	/**
	 * 更新代理人实体
	 * @author 043260-foss-suyujun
	 * @date 2012-12-26
	 * @param agentPerson
	 * @return void
	 */
	void updateWaybillRfcAgentPerson(WaybillRfcAgentPersonEntity agentPerson);

	/**
	 * 更新代理实体
	 * @author 043260-foss-suyujun
	 * @date 2012-12-26
	 * @param originalEntity
	 * @return void
	 */
	void updateWaybillRfcAgent(WaybillRfcAgentEntity originalEntity);

	/**
	 * 根据工号查询是否有代理权限
	 * @author 043260-foss-suyujun
	 * @date 2012-12-28
	 * @param conditionDto
	 * @return List<WaybillRfcAgentEntity>
	 */
	List<WaybillRfcAgentEntity> queryAuthorityListByAgentCode(WaybillRfcQueryAgentConDto conditionDto);

	/**
	 * 
	 * 查询判断代理是否重复授权 ，返回重复的员工姓名
	 * @author foss-gengzhe
	 * @date 2013-1-25 上午9:52:24
	 * @param waybillRfcAgentEntity
	 * @return
	 * @see
	 */
	List<String> queryWaybillRfcAgentByCondition(WaybillRfcAgentEntity waybillRfcAgentEntity, List<String> currentEmpCodes);

	/**
	 * 
	 * 根据开户人名称、开户账号、开户行名称查询唯一的客户银行
	 * @author 102246-foss-shaohongliang
	 * @date 2013-2-21 下午8:40:29
	 */
	CusAccountEntity queryCusAccountByWaybillInfo(String accountName,String accountCode, String accountBank,String custCode);

	List<WaybillRfcChangeDto> queryWaybillRfcCargoVarificationDto(
			WaybillRfcCondition waybillRfcCondition);
	
	/**
	 * 获取客户最后一次开户银行信息
	 * @创建时间 2014-5-6 上午10:36:14   
	 * @创建人： WangQianJin
	 */
	CusAccountEntity queryCusAccountByCreateTime(String accountName,
			String accountCode, String accountBank,String custCode);
	
	/**
	 * 根据运单ID更新客户编码
	 * @创建时间 2014-7-1 上午10:47:17   
	 * @创建人： WangQianJin
	 */
	void updateCustomerCodeByWaybillId(WaybillEntity waybillEntity);
	
	//根据运单号查询改单状态 2016年3月17日 17:22:00 葛亮亮
	WaybillRfcChangeDto queryWaybillRfcTypeById(String wayBillId);
}