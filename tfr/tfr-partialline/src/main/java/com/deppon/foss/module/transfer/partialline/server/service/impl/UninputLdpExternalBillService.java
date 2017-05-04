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
 *  DESCRIPTION   : 落地配外发管理
 *  
 *  FILE PATH     :src/main/java/com/deppon/foss/module/transfer/partialline/server/service/impl/UninputLdpExternalBillService.java
 * 
 *  FILE NAME     :UninputLdpExternalBillService.java
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
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.base.util.define.BizTypeConstants;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.web.message.IMessageBundle;
import com.deppon.foss.module.base.baseinfo.api.server.service.complex.IOrgAdministrativeInfoComplexService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.common.api.server.service.IBusinessLockService;
import com.deppon.foss.module.base.common.api.shared.define.MutexElementType;
import com.deppon.foss.module.base.common.api.shared.dto.MutexElement;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.transfer.partialline.api.server.dao.ILdpExternalBillDao;
import com.deppon.foss.module.transfer.partialline.api.server.dao.IUninputLdpExternalBillDao;
import com.deppon.foss.module.transfer.partialline.api.server.service.ILdpExternalBillService;
import com.deppon.foss.module.transfer.partialline.api.server.service.IUninputLdpExternalBillService;
import com.deppon.foss.module.transfer.partialline.api.shared.define.PartiallineConstants;
import com.deppon.foss.module.transfer.partialline.api.shared.dto.LdpExternalBillDto;
import com.deppon.foss.module.transfer.partialline.api.shared.dto.LdpHandoverBillDetailDto;
import com.deppon.foss.module.transfer.partialline.api.shared.exception.ExternalBillException;

/**
 * 未录入外发单查询Service
 * 
 * @author ibm-liuzhaowei
 * @date 2013-07-16 上午9:18:36
 */
public class UninputLdpExternalBillService implements IUninputLdpExternalBillService {

	/**
	 * 日志
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(UninputLdpExternalBillService.class);
	/**
	 * 未录入落地配查询DAO接口
	 */
	private IUninputLdpExternalBillDao uninputLdpExternalBillDao;
	/**
	 * 落地配service接口
	 */
	private ILdpExternalBillService ldpExternalBillService;
	/**
	 * 落地配DAO接口
	 */
	private ILdpExternalBillDao ldpExternalBillDao;
	/**
	 * 业务锁
	 */
	private IBusinessLockService businessLockService;
	/**
	 * 部门Service
	 */
	private IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService;
	
	private IMessageBundle messageBundle;
	
	/**
	 * 业务锁Service
	 */
	public void setBusinessLockService(IBusinessLockService businessLockService) {
		this.businessLockService = businessLockService;
	}
	
	/**
	 * Service
	 */
	public void setLdpExternalBillService(ILdpExternalBillService ldpExternalBillService) {
		this.ldpExternalBillService = ldpExternalBillService;
	}

	public void setOrgAdministrativeInfoComplexService(IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService) {
		this.orgAdministrativeInfoComplexService = orgAdministrativeInfoComplexService;
	}
	
	public void setMessageBundle(IMessageBundle messageBundle) {
		this.messageBundle = messageBundle;
	}

	/**
	 * 未录入落地配查询DAO接口
	 * 
	 * @author ibm-liuzhaowei
	 * @date 2013-07-17 下午6:49:27
	 */
	public void setUninputLdpExternalBillDao(IUninputLdpExternalBillDao uninputLdpExternalBillDao) {
		this.uninputLdpExternalBillDao = uninputLdpExternalBillDao;
	}

	/**
	 * 落地配DAO接口
	 * 
	 * @author ibm-liuzhaowei
	 * @date 2013-07-17 上午8:33:10
	 */
	public void setLdpExternalBillDao(ILdpExternalBillDao ldpExternalBillDao) {
		this.ldpExternalBillDao = ldpExternalBillDao;
	}
	
	/**
	 * 保存落地配外发单，并同步结算数据
	 * 
	 * @author ibm-liuzhaowei
	 * @date 2013-07-17 上午11:47:04
	 * 
	 * 一票多件，按照流水号生成外发单;保存落地配外发单，并同步结算数据
	 * @update-author foss--lln--269701
	 * @update-param wayBillSerialNos未录入落地配运单号和流水号列表
	 * @update-date 2015-10-22 下午14:58:58
	 * @update-return 批量生成外发单失败的运单和流水号的集合
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public String addLdpExternalBill(List<LdpHandoverBillDetailDto> wayBillSerialNos,CurrentInfo currentInfo) {
		//生成落地配外发单失败的流水号集合
		StringBuffer failedWayBillSerialNos = new StringBuffer("");
		
		//创建业务锁--运单号+流水号
		if(wayBillSerialNos.size()!=0){
			//声明锁对象集合
			List<MutexElement> mutexesList = new ArrayList<MutexElement>();
	
				//循环map集合
				for (int i=0;i<wayBillSerialNos.size();i++) {
					
					//运单号
					String wayBillNo = wayBillSerialNos.get(i).getWaybillNo();
					if(StringUtils.isNotBlank(wayBillNo)){
						
						//运单对应流水号
						String serialNo = wayBillSerialNos.get(i).getSerialNo();
						//判空
						if(StringUtils.isNotBlank(serialNo)){
							//声明锁定流水号对象
							MutexElement mtx = new MutexElement("ExternalBill"+wayBillNo+serialNo,PartiallineConstants.TFR_LDP_LOCK_DESC, MutexElementType.WAYBILL_SERIAL_NO);
							mutexesList.add(mtx);
							
						}else{
							throw new ExternalBillException("选择的流水号为空！", "");
						}
						
					}else{
						throw new ExternalBillException("选择的运单为空！", "");
					}
				}
				
				//用于记录对象是否被锁定
				boolean flag = false;
				
				//锁定运单和流水号
				if(CollectionUtils.isNotEmpty(mutexesList)){
					flag = businessLockService.lock(mutexesList, 0);
				}
				
				//如果成功获得锁定，则循环生成落地配外发单
				if(flag){
					if(currentInfo == null ){
						currentInfo = FossUserContext.getCurrentInfo();
					}
					
						for(int j=0;j<wayBillSerialNos.size();j++){
						
							//运单号
							String wayBillNo = wayBillSerialNos.get(j).getWaybillNo();
							//运单对应流水号
							String serialNo = wayBillSerialNos.get(j).getSerialNo();
							
								try{
									//根据流水号，新增单条落地配外发单
									ldpExternalBillService.addOneLdpExternalBill(wayBillNo,serialNo, currentInfo);
								
								}catch(ExternalBillException e){
									//如果某条运单的流水号生成落地配外发单失败，则记录下运单号和对应的流水号，不影响其他运单的生成
									failedWayBillSerialNos.append(wayBillNo).append(", ").append(serialNo).append(": ").append(messageBundle.getMessage(e.getErrorCode(), e.getErrorArguments())).append(";\n");
								}catch(BusinessException e){
									failedWayBillSerialNos.append(wayBillNo).append(", ").append(serialNo).append(": ").append(e.getMessage()).append(";\n");
								}
					}
				}else{
					failedWayBillSerialNos.append("存在流水号正在生成快递代理外发单，请稍后重试，或重新点击生成按钮！");
				}
				
				//解锁运单和流水号
				if(CollectionUtils.isNotEmpty(mutexesList)){
					businessLockService.unlock(mutexesList);
				}
				
				//返回生成外发单失败的运单和流水号集合
				String failedWayBillSerialNoStr = failedWayBillSerialNos.toString();
				if(failedWayBillSerialNoStr.length() > 0){
					failedWayBillSerialNoStr = failedWayBillSerialNoStr.substring(0, failedWayBillSerialNoStr.length() - 1);
				}
				return failedWayBillSerialNoStr;

			}else{
				throw new ExternalBillException("选择的运单为空！", "");
			}
	}

	/**
	 * 查询未录入落地配外发单列表（查询交接类型为落地配，且未录入外发单的交接单明细）
	 * 
	 * @author ibm-liuzhaowei
	 * @date 2013-07-17 上午11:00:38
	 */
	@Override
	public List<LdpHandoverBillDetailDto> queryUninputLdpExternalBill(LdpHandoverBillDetailDto detailDto, int limit, int start) {
		
		return uninputLdpExternalBillDao.queryUninputLdpExternalBill(detailDto, limit, start);
	}

	/**
	 * 查询未录入落地配外发单列表（查询交接类型为落地配，且未录入外发单的交接单明细）总条数
	 * 
	 * @author ibm-liuzhaowei
	 * @date 2013-07-17 上午11:00:38
	 */
	@Override
	public Long queryUninputLdpExternalBillCount(LdpHandoverBillDetailDto detailDto) {
		
		return uninputLdpExternalBillDao.queryUninputLdpExternalBillCount(detailDto);
	}

	/**
	 * 查询运单号是否存在未录入的交接单
	 * 
	 * @author ibm-liuzhaowei
	 * @date 2013-07-17 上午10:42:57
	 */
	@Override
	public void queryHandedUninputWaybill(LdpExternalBillDto dto) {
		// 首先判断运单号,不为空
		if (dto != null && StringUtils.isNotBlank(dto.getWaybillNo())) {
			// 查询运单对应的交接明细是否存在
			Long listSize = ldpExternalBillDao.queryHandedUninputWaybill(dto);
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
	 * @author ibm-liuzhaowei
	 * @date 2013-07-17 上午10:02:45
	 */
	@Override
	public List<String> queryTransCenterChildrenCodes(String currentDeptCode) {
		List<String> rs = null;
		// 当前部门所对应的外场编码
		String transCenterCode = "";
		OrgAdministrativeInfoEntity transCenter = querySuperiorOrgByOrgCode(currentDeptCode);
		if (transCenter != null) {
			transCenterCode = transCenter.getCode();
		}
		// 根据外场查询子孙部门编码
		if (StringUtils.isNotBlank(transCenterCode)) {
			List<OrgAdministrativeInfoEntity> orgs = orgAdministrativeInfoComplexService.queryOrgAdministrativeInfoEntityAllSubByCode(transCenterCode);
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
	 * @author ibm-liuzhaowei
	 * @date 2013-07-17 下午4:17:21
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