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
 * PROJECT NAME	: pkp-sign
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/sign/server/service/impl/SignByOtherService.java
 * 
 * FILE NAME        	: SignByOtherService.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.sign.server.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.framework.server.web.upload.AttachementEntity;
import com.deppon.foss.module.base.baseinfo.api.server.service.IAttachementService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.login.server.service.ILoginService;
import com.deppon.foss.module.pickup.predeliver.api.server.process.IHandleQueryOutfieldService;
import com.deppon.foss.module.pickup.predeliver.api.shared.context.FossUserContextHelper;
import com.deppon.foss.module.pickup.sign.api.server.dao.IConsigneeAgentDao;
import com.deppon.foss.module.pickup.sign.api.server.service.ISignByOtherService;
import com.deppon.foss.module.pickup.sign.api.shared.domain.ConsigneeAgentEntity;
import com.deppon.foss.module.pickup.sign.api.shared.dto.RtSearchWaybillSignByOtherDto;
import com.deppon.foss.module.pickup.sign.api.shared.dto.SearchWaybillSignByOtherDto;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillManagerService;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillEntity;
import com.deppon.foss.util.UUIDUtils;

/***
 * 处理他人收件  Service
 * @author xwshi
 * @date 2012-11-26 上午10:36:08
 */
public class SignByOtherService implements ISignByOtherService {

	/**
	 * Logger日志
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(SignByOtherService.class);
	
	/**
	 * 运单管理Service
	 */
	private IWaybillManagerService waybillManagerService;
	
	/**
	 * 登陆密码验证对象
	 */
	private ILoginService loginService;
	
	/**
	 * 凭证对象
	 */
	private IConsigneeAgentDao consigneeAgentDao;
	
	//附件
	private IAttachementService attachementService;
	
	/**
	 * 组织信息 Service接口
	 */
	private IOrgAdministrativeInfoService orgAdministrativeInfoService;
	
	/**
	 * 外场相关共通接口
	 */
	private IHandleQueryOutfieldService handleQueryOutfieldService;

	/*
	 * 附件
	 */
	public void setAttachementService(IAttachementService attachementService) {
		this.attachementService = attachementService;
	}
	
	/***
	 * 营业员通过输入运单号，经部门经理授权后查询发货客户联系方式
	 * 根据录入条件查询弃货运单
	 * @date 2012-11-26 上午10:41:13
	 */
	public RtSearchWaybillSignByOtherDto queryWaybillReceiverInfo(SearchWaybillSignByOtherDto dto){
		
		//创建结果对象
		RtSearchWaybillSignByOtherDto resultdto = new RtSearchWaybillSignByOtherDto();
		
		//manager's password is error return error
		try{
			//得到当前登录人的管理人员工号
			String principalNo = FossUserContext.getCurrentDept().getPrincipalNo();
			//登陆
			boolean passLogin = loginService.validateUser(principalNo, dto.getAuthorizationCode());
			//登陆失败
			if(!passLogin){
				//不能改他人签收
				resultdto.setSearchResult(RtSearchWaybillSignByOtherDto.AUTHORIZATIONERROR);
				return resultdto;
			}
		}catch(Exception e){
			//登录出现异常 当作登录失败处理
			LOGGER.error("login Exception", e);
			resultdto.setSearchResult(RtSearchWaybillSignByOtherDto.AUTHORIZATIONERROR);
			return resultdto;
		}
		
		//waybill no is empty
		if(StringUtils.isEmpty(dto.getWaybillNo())){
			//没有运单号
			resultdto.setSearchResult(RtSearchWaybillSignByOtherDto.WAYBILLNOINVALID);
			return resultdto;
		}
		//查询运单号
		WaybillEntity waybillEntity = waybillManagerService.queryWaybillBasicByNo(dto.getWaybillNo());
		//no waybillentity is found, return empty message for invalid waybill no
		//运单号 is null
		if(waybillEntity==null){
			//运单号无效
			resultdto.setSearchResult(RtSearchWaybillSignByOtherDto.WAYBILLNOINVALID);
			return resultdto;
		}
		
		//该运单到达不是本部门
//		if(!waybillEntity.getLastLoadOrgCode().equals(FossUserContextHelper.getOrgCode())){
//			resultdto.setSearchResult(RtSearchWaybillSignByOtherDto.WAYBILLNOINVALID);
//			return resultdto;			
//		}

		//查询是否有本部门的库存
		// ibm-wangfei 2013-2-22 15:48:48 添加库存外场、库区默认查询条件
		List<String> list = handleQueryOutfieldService.getEndStockCodeAndExpressAreaCode(FossUserContextHelper.getOrgCode());
		if (CollectionUtils.isNotEmpty(list)) {
			long existInStock = consigneeAgentDao.selectExistInStock(waybillEntity.getWaybillNo(), list.get(0), list.get(1),list.get(2));
			//没有入库
			if(existInStock<=0){
				resultdto.setSearchResult(RtSearchWaybillSignByOtherDto.WAYBILLNOINVALID);
				return resultdto;	
			}
		}
		
		//成功
		resultdto.setSearchResult(RtSearchWaybillSignByOtherDto.SUCCESS);
		// 设置创建部门NAME
		if (StringUtils.isNotEmpty(waybillEntity.getCreateOrgCode())) {
			// 根据编码查询组织信息
			OrgAdministrativeInfoEntity orgAdministrativeInfoEntity = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(waybillEntity.getCreateOrgCode());
			// 设置始发部门名称
			waybillEntity.setCreateOrgCode(orgAdministrativeInfoEntity == null ? "" : orgAdministrativeInfoEntity.getName());
		}
		// set Value 用于界面
		resultdto.setWaybillEntity(waybillEntity);
		return resultdto;
	}
	
	
	/***
	 * 发货客户更改收货人的电子凭证
	 * @date 2012-11-26 上午11:41:13
	 */
	@Transactional
	public RtSearchWaybillSignByOtherDto updateWaybillReceiverInfo(
			SearchWaybillSignByOtherDto dto) {

		//发货客户更改收货人结果对象
		RtSearchWaybillSignByOtherDto resultdto = new RtSearchWaybillSignByOtherDto();
		
		//waybill no  is empty
		if(dto==null || StringUtils.isEmpty(dto.getWaybillNo())){
			///没有运单号
			resultdto.setSearchResult(RtSearchWaybillSignByOtherDto.WAYBILLNOINVALID);
			return resultdto;
		}
	
		//当前部门 查询到达部门要用
//		dto.setCurrentDeptCode(FossUserContextHelper.getOrgCode());
//		//查询是否运单存在 并且到达 并且入库
//		long existInStock = consigneeAgentDao.selectWaybillExistInStock(dto);
//				
//		//没有入库
//		if(existInStock<=0){
//			resultdto.setSearchResult(RtSearchWaybillSignByOtherDto.WAYBILLNOINVALID);
//			return resultdto;	
//		}
		//查询是否有本部门的库存
		// ibm-wangfei 2013-2-22 15:48:48 添加库存外场、库区默认查询条件
		List<String> list = handleQueryOutfieldService.getEndStockCodeAndExpressAreaCode(FossUserContextHelper.getOrgCode());
		if (CollectionUtils.isNotEmpty(list)) {
			long existInStock = consigneeAgentDao.selectExistInStock(dto.getWaybillNo(), list.get(0), list.get(1),list.get(2));
			//没有入库
			if(existInStock<=0){
				resultdto.setSearchResult(RtSearchWaybillSignByOtherDto.WAYBILLNOINVALID);
				return resultdto;	
			}
		}
				
		//save ConsigneeAgent 他人签收凭证
		this.saveConsigneeAgentEntity(dto);
		
		return null;
	}

	/**
	 * 保存ConsigneeAgent 他人签收凭证
	 * @date 2012-11-26 下午5:54:19
	 */
	private void saveConsigneeAgentEntity(SearchWaybillSignByOtherDto dto
			) {
		//他人签收凭证
		ConsigneeAgentEntity consigneeAgent = new ConsigneeAgentEntity();
		//id
		consigneeAgent.setId(UUIDUtils.getUUID());
		//运单号
		consigneeAgent.setWaybillNo(dto.getWaybillNo());
		//创建时间
		consigneeAgent.setCreateTime(new Date());
		//创建人工号
		consigneeAgent.setCreateUserCode(FossUserContextHelper.getUserCode());
		//创建人
		consigneeAgent.setCreateUserName(FossUserContextHelper.getUserName());
		//发货客户
		consigneeAgent.setRealConsignee(dto.getReceiveCustomerContact());
		//发货客户电话
		consigneeAgent.setConsigneePhone(dto.getReceiveCustomerPhone());
		consigneeAgentDao.insert(consigneeAgent);//save
		
		List<AttachementEntity> fileList = dto.getFiles();
		if (CollectionUtils.isNotEmpty(fileList)) {
			for (AttachementEntity attachementEntity : fileList) {
				//关联上传组件和业务
				attachementEntity.setRelatedKey(consigneeAgent.getId());
				//更新上传组件数据
				attachementService.updateAttachementInfo(attachementEntity);
			}
		} else {
			LOGGER.info("没有附件，不需要保存");
		}
		/*if(files!=null && !files.isEmpty()){//附件
			//依次处理每一个文件
			for (Iterator<FileDto> iterator = files.iterator(); iterator.hasNext();) {
				//取得文件
				FileDto file = iterator.next();
				
				if(FileDto.ADD.equals(file.getStatus())){
					//创建文件对象
					AgentProofEntity agentProofEntity = new AgentProofEntity();
					//id
					agentProofEntity.setId(UUIDUtils.getUUID());
					//凭证id
					agentProofEntity.settSrvConsigneeAgentId(consigneeAgent.getId());
					//路径
					agentProofEntity.setProofPath(file.getRelativePath());
					
					// this file name should be the real file name
					//文件名 必须用真实文件名
					agentProofEntity.setProofName(file.getName());
					
					// size
					agentProofEntity.setProofSize(file.getSize());
				
					//保存文件信息
					agentProofDao.insert(agentProofEntity);
				}
			}					
		}*/	
	}
	
	/**
	 * 
	 * 根据运单号，返回处理他人收件信息
	 * @param waybillNo
	 * @return
	 * @author ibm-wangfei
	 * @date Apr 16, 2013 11:40:48 AM
	 */
	@Override
	public SearchWaybillSignByOtherDto querySignByOtherDto(String waybillNo) {
		SearchWaybillSignByOtherDto dto = null;
		ConsigneeAgentEntity entity = null;
		if (StringUtils.isNotEmpty(waybillNo)) {
			List<ConsigneeAgentEntity> entitys = consigneeAgentDao.selectByWayblillNo(waybillNo);
			if (CollectionUtils.isNotEmpty(entitys)) {
				dto = new SearchWaybillSignByOtherDto();
				entity = entitys.get(0);
				// 真实收货人
				dto.setReceiveCustomerContact(entity.getRealConsignee());
				// 收货人电话
				dto.setReceiveCustomerPhone(entity.getConsigneePhone());
				//获得文件附件列表
				dto.setFiles(attachementService.getAttachementInfos(entity.getId(), null));
			}
		}
		return dto;
	}
	
	/**
	 *  get对象
	 */
	public IWaybillManagerService getWaybillManagerService() {
		return waybillManagerService;
	}
	/**
	 *  set对象
	 */
	public void setWaybillManagerService(
			IWaybillManagerService waybillManagerService) {
		this.waybillManagerService = waybillManagerService;
	}
	/**
	 *  get对象
	 */
	public ILoginService getLoginService() {
		return loginService;
	}
	/**
	 *  set对象
	 */
	public void setLoginService(ILoginService logService) {
		this.loginService = logService;
	}
	/**
	 *  get对象
	 */
	public IConsigneeAgentDao getConsigneeAgentDao() {
		return consigneeAgentDao;
	}
	/**
	 *  set对象
	 */
	public void setConsigneeAgentDao(IConsigneeAgentDao consigneeAgentDao) {
		this.consigneeAgentDao = consigneeAgentDao;
	}

	public void setOrgAdministrativeInfoService(IOrgAdministrativeInfoService orgAdministrativeInfoService) {
		this.orgAdministrativeInfoService = orgAdministrativeInfoService;
	}

	public void setHandleQueryOutfieldService(IHandleQueryOutfieldService handleQueryOutfieldService) {
		this.handleQueryOutfieldService = handleQueryOutfieldService;
	}

}