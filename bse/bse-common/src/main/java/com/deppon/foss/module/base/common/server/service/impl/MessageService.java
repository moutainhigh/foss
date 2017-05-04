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
 * PROJECT NAME	: bse-common
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/common/server/service/impl/MessageService.java
 * 
 * FILE NAME        	: MessageService.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.common.server.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import com.deppon.foss.base.util.define.MessageConstants;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.server.service.IEmployeeService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IUserOrgRoleService;
import com.deppon.foss.module.base.baseinfo.api.server.service.complex.IOrgAdministrativeInfoComplexService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.common.api.server.dao.IInstationMsgDao;
import com.deppon.foss.module.base.common.api.server.dao.IMsgOnlineDao;
import com.deppon.foss.module.base.common.api.server.service.IMessageService;
import com.deppon.foss.module.base.common.api.shared.domain.InstationJobMsgEntity;
import com.deppon.foss.module.base.common.api.shared.domain.InstationMsgEntity;
import com.deppon.foss.module.base.common.api.shared.dto.MessagesDto;
import com.deppon.foss.module.base.common.api.shared.exception.MessageException;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryValueConstants;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.pickup.predeliver.api.server.dao.IExceptionProcessDao;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.ExceptionProcessConditionDto;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

/**
 * 站内消息Service
 * @author 101911-foss-zhouChunlai
 * @date 2012-12-10 上午9:06:18
 */
public class MessageService implements IMessageService {

	/**
	 * logger
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(MessageService.class);
	
	/**
	 * 员工Service
	 */
	private IEmployeeService employeeService;
	
	/**
	 * 组织信息 Service
	 */
	private IOrgAdministrativeInfoService orgAdministrativeInfoService ;

	/**
	 * 组织Service
	 */
	private IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService;
	
	/**
	 * 用户角色信息Service
	 */
	private IUserOrgRoleService userOrgRoleService;
	/**
	 * 站内消息分发Dao
	 */
	//private IInstationJobMsgDao instationJobMsgDao;

	/**
	 * 站内消息明细Dao
	 */
	private IInstationMsgDao instationMsgDao;
	
	private IMsgOnlineDao msgOnlineDao;

	/** 获取处理异常dao. */
	private IExceptionProcessDao exceptionProcessDao;
	
	
	public IExceptionProcessDao getExceptionProcessDao() {
		return exceptionProcessDao;
	}
	public void setExceptionProcessDao(IExceptionProcessDao exceptionProcessDao) {
		this.exceptionProcessDao = exceptionProcessDao;
	}
	/**外围模块调用 
	 * 人员对人员的站内消息发送
	 * @author 101911-foss-zhouChunlai
	 * @date 2012-12-12 下午6:57:59
	 */
	@Override
	public void createInstationMsg(InstationMsgEntity entity) {
		//保存站内消息
		saveInstationMsg(entity);
	}
	/**
	 * 手动发送全网消息
	 * 
	 * @author 101911-foss-zhouChunlai
	 * @date 2012-12-12 下午6:57:59
	 */
	@Override
	@Transactional
	public void manualCreateInstationMsg(InstationJobMsgEntity entity,CurrentInfo currentInfo) {
		InstationMsgEntity msgEntity=new InstationMsgEntity();
		//Id标识
		msgEntity.setId(UUIDUtils.getUUID());
		//接收用户编码 
		//msgEntity.setReceiveUserCode(MessageConstants.MSG__SYS_USER_CODE);
		//接收用户编码 
		//msgEntity.setReceiveUserName(MessageConstants.MSG__SYS_USER_CODE); 
		//接收子组织编码 
		msgEntity.setReceiveSubOrgCode(FossConstants.ROOT_ORG_CODE);
		//接收子组织名称 
		msgEntity.setReceiveSubOrgName(FossConstants.ROOT_ORG_NAME);
		//接收组织编码 
		msgEntity.setReceiveOrgCode(FossConstants.ROOT_ORG_CODE);
		//接收组织名称 
		msgEntity.setReceiveOrgName(FossConstants.ROOT_ORG_NAME);
		//发送用户编码
		msgEntity.setSendUserCode(currentInfo.getEmpCode());
		//发送用户名称
		msgEntity.setSendUserName(currentInfo.getEmpName());
		//发送用户所属部门编码
		msgEntity.setSendOrgCode(currentInfo.getCurrentDeptCode());
		//发送用户所属部门名称
		msgEntity.setSendOrgName(currentInfo.getCurrentDeptName());
		//全网消息
		msgEntity.setMsgType(DictionaryValueConstants.MSG_TYPE__ALLNET);
		//消息内容
		msgEntity.setContext(entity.getContext());
		//失效日期
		msgEntity.setExpireTime(entity.getExpireTime()); 
		//保存站内消息
		this.saveInstationMsg(msgEntity);
	}
	
	/**
	 * 公用人员对人员的站内消息发送
	 * @author 101911-foss-zhouChunlai
	 * @param entity 消息明细实体
	 * @date 2012-12-27 下午3:25:01
	 * @return 
	 */
	private void saveInstationMsg(InstationMsgEntity entity){
		// 校验传入参数
		if (null == entity) {
			throw new MessageException("输入参数错误");
		}
		LOGGER.info("entering  service ,receiverUserCode:"+ entity.getReceiveUserCode());
		//313353 sonar
		this.sonarSplitOne(entity);
		
		//当是站内消息类型为全网时则不需要输入接收人信息
		if(StringUtil.equals(DictionaryValueConstants.MSG_TYPE__NORMAL , entity.getMsgType())){
			if (StringUtil.isEmpty(entity.getReceiveUserCode())) {
				throw new MessageException("接收人不能为空");
			}
		}else {
			if(null ==entity.getExpireTime()){
				throw new MessageException("失效日期不能为空");
			}
		}
		if (StringUtil.isEmpty(entity.getContext())) {
			throw new MessageException("发送内容不能为空");
		}
		// 有效状态
		entity.setActive(FossConstants.ACTIVE);
		// 标识消息未读
		entity.setIsReaded(DictionaryValueConstants.MSG__READ_STATUS__UNREAD);
		//创建消息时间
		entity.setCreateTime(new Date());
		//消息生成方式 手动或系统
		entity.setCreateType(MessageConstants.MSG__CREATE_TYPE__MANUAL);
		//消息序号标识
		entity.setSerialNumber(UUIDUtils.getUUID());
		
		int i=instationMsgDao.insertInstationMsg(entity);
		if(1!=i){
			throw new MessageException("发送消息失败");
		}
		LOGGER.info("successfully exit service, receiverUserCode: " + entity.getReceiveUserCode());
	}
	
	/**
	 * sonar优化拆分
	 * 
	 * @author 313353
	 */
	private void sonarSplitOne(InstationMsgEntity entity) {
		if (StringUtil.isEmpty(entity.getSendUserCode())) {
			throw new MessageException("发送人不能为空");
		}
		if (StringUtil.isEmpty(entity.getSendUserName())) {
			throw new MessageException("发送人名称不能为空");
		}
		if (StringUtil.isEmpty(entity.getSendOrgCode())) {
			throw new MessageException("发送人所属部门编码不能为空");
		}
		if (StringUtil.isEmpty(entity.getSendOrgName())) {
			throw new MessageException("发送人所属部门名称不能为空");
		}
		if(StringUtil.isEmpty(entity.getMsgType())) {
			throw new MessageException("消息类型不能为空");
		}
	}
	
	/** 
	 * <p>ECS快递将站内消息推给FOSS，FOSS新增到数据库中</p> 
	 * @author 232607 
	 * @date 2016-4-28 下午5:00:00
	 * @param entity 
	 * @see com.deppon.foss.module.base.common.api.server.service.IMessageService#saveInstationMsg(com.deppon.foss.module.base.common.api.shared.domain.InstationMsgEntity)
	 */
	@Override
	public void saveInstationMsgByECS(InstationMsgEntity entity){
		//313353 sonar
		this.sonarSplitTwo(entity);
		
		//状态：未处理
		entity.setStatus(MessageConstants.MSG__STATUS__PROCESSING);
		//未读
		entity.setIsReaded(DictionaryValueConstants.MSG__READ_STATUS__UNREAD);
		entity.setId(UUIDUtils.getUUID());
		entity.setActive(FossConstants.ACTIVE);
		entity.setCreateTime(new Date());
		//如果为全网发送，则接收组织设定为DIP
		if(StringUtil.equals(DictionaryValueConstants.MSG_TYPE__ALLNET , entity.getMsgType())){
			if(null ==entity.getExpireTime()){
				throw new MessageException("全网发送时，失效日期不能为空");
			}
			entity.setReceiveSubOrgCode(FossConstants.ROOT_ORG_CODE);
			entity.setReceiveSubOrgName(FossConstants.ROOT_ORG_NAME);
			entity.setReceiveOrgCode(FossConstants.ROOT_ORG_CODE);
			entity.setReceiveOrgName(FossConstants.ROOT_ORG_NAME);
			entity.setSerialNumber(UUIDUtils.getUUID());
			// 标识消息未读
			instationMsgDao.insertInstationMsg(entity);
		}
		//如果为普通发送，则查询接收组织的下级组织，给每一个下级组织都发消息
		if(StringUtil.equals(DictionaryValueConstants.MSG_TYPE__NORMAL , entity.getMsgType())){
			List<OrgAdministrativeInfoEntity> sonOrgList = orgAdministrativeInfoComplexService.queryOrgAdministrativeInfoEntityAllSubByCode(entity.getReceiveOrgCode());
			if(CollectionUtils.isNotEmpty(sonOrgList)){
				for(OrgAdministrativeInfoEntity sonOrg:sonOrgList){
					entity.setId(UUIDUtils.getUUID());
					entity.setReceiveSubOrgCode(sonOrg.getCode());
					entity.setReceiveSubOrgName(sonOrg.getName());
					entity.setSerialNumber(UUIDUtils.getUUID());
					instationMsgDao.insertInstationMsg(entity);
				}				
			}
		}
		
		//  供应商补录失败消息
		if(StringUtil.equals(DictionaryValueConstants.MSG_TYPE__SUPPLIER_FAIL_PATCH , entity.getMsgType())){
			if (StringUtil.isEmpty(entity.getSendUserCode())) {
				throw new MessageException("发送人员编码不能为空");
			}
			if (StringUtil.isEmpty(entity.getSendUserName())) {
				throw new MessageException("发送人员名称不能为空");
			}
			if (StringUtil.isEmpty(entity.getSendOrgCode())) {
				throw new MessageException("发送方组织编码不能为空");
			}
			if (StringUtil.isEmpty(entity.getSendOrgName())) {
				throw new MessageException("发送方组织名称不能为空");
			}
			if (StringUtil.isEmpty(entity.getReceiveType())) {
				throw new MessageException("接收方类型不能为空");
			}
			List<OrgAdministrativeInfoEntity> sonOrgList = 
					orgAdministrativeInfoComplexService.queryOrgAdministrativeInfoEntityAllSubByCode(entity.getReceiveOrgCode());
			if(CollectionUtils.isNotEmpty(sonOrgList)){
				for(OrgAdministrativeInfoEntity sonOrg:sonOrgList){
					entity.setId(UUIDUtils.getUUID());
					entity.setReceiveSubOrgCode(sonOrg.getCode());
					entity.setReceiveSubOrgName(sonOrg.getName());
					entity.setSerialNumber(UUIDUtils.getUUID());
					instationMsgDao.insertInstationMsg(entity);
				}
			}
		}
	}
	/**
	 * sonar优化拆分
	 * 
	 * @author 313353
	 */
	private void sonarSplitTwo(InstationMsgEntity entity) {
		if (null == entity) {
			throw new MessageException("输入参数错误");
		}
		if(StringUtil.isEmpty(entity.getReceiveOrgCode())) {
			throw new MessageException("接收组织编码不能为空");
		}
		if(StringUtil.isEmpty(entity.getMsgType())) {
			throw new MessageException("消息类型不能为空");
		}
		if(StringUtil.isEmpty(entity.getContext())) {
			throw new MessageException("发送内容不能为空");
		}
	}
	
	/** 
	 * 外围模块调用 
	 * 人员对组织的站内消息发送
	 * @author 101911-foss-zhouChunlai
	 * @param 人对组织消息实体
	 * @date 2012-12-27 下午3:40:43
	 * @return 
	 * @see com.deppon.foss.module.base.common.api.server.service.IMessageService#createBatchInstationMsg(com.deppon.foss.module.base.common.api.shared.domain.InstationJobMsgEntity)
	 */
	@Override
	public void createBatchInstationMsg(InstationJobMsgEntity entity) {
		//保存站内消息
		saveBatchInstationMsg(entity,MessageConstants.MSG__CREATE_TYPE__AUTO);
	}
	
	/**
	 * 手动发送人员对组织站内消息
	 * 
	 * @author 101911-foss-zhouChunlai
	 * @date 2012-12-12 下午6:57:59
	 */
	@Override
	@Transactional
	public void manualCreateBatchInstationMsg(InstationJobMsgEntity entity,CurrentInfo currentInfo) {
		//实例消息分发数据
		InstationJobMsgEntity msgJobEntity=new InstationJobMsgEntity();
		//设置ID标识
		msgJobEntity.setId(UUIDUtils.getUUID());
		//设置发送消息类型
		msgJobEntity.setMsgType(DictionaryValueConstants.MSG_TYPE__NORMAL);
		//设置发送内容
		msgJobEntity.setContext(entity.getContext());
		//设置发送方编码
		msgJobEntity.setSenderCode(currentInfo.getEmpCode()); 
		//设置发送人名称
		msgJobEntity.setSenderName(currentInfo.getEmpName());
		//设置发送网点编码
		msgJobEntity.setSenderOrgCode(currentInfo.getCurrentDeptCode());
		//设置发送方名称
		msgJobEntity.setSenderOrgName(currentInfo.getCurrentDeptName());
		//设置接收组织编码
		msgJobEntity.setReceiveOrgCode(entity.getReceiveOrgCode()); 
		//设置接收组织名称
		msgJobEntity.setReceiveOrgName(entity.getReceiveOrgName());
		//设置发送组织为系统
		msgJobEntity.setReceiveType(MessageConstants.MSG__RECEIVE_TYPE__ORG);
		//保存站内消息
		saveBatchInstationMsg(msgJobEntity,MessageConstants.MSG__CREATE_TYPE__MANUAL);
	} 
	/**
	 * 人员对组织的公用消息发送
	 * @author 101911-foss-zhouChunlai
	 * @date 2012-12-12 下午6:57:59
	 */
	private void saveBatchInstationMsg(InstationJobMsgEntity entity,String createType){
		// 校验传入参数
		if (null == entity) {
			throw new MessageException("输入参数错误");
		}

		LOGGER.info("entering  service ,receiverOrgCode:"+ entity.getReceiveOrgCode());
		//313353 sonar
		this.sonarSplitThree(entity);
		
		if (StringUtil.isEmpty(entity.getReceiveOrgCode())) {
			throw new MessageException("接收组织不能为空");
		}
		
		if (StringUtil.isEmpty(entity.getContext())) {
			throw new MessageException("发送内容不能为空");
		}
		
		if (StringUtil.isEmpty(entity.getReceiveType())) {
			throw new MessageException("接收方类型不能为空");
		}
		
		if (StringUtil.isEmpty(entity.getMsgType())) {
			throw new MessageException("消息类型不能为空");
		}
		//当是站内消息类型为全网时则失效日期必输
		if(StringUtil.equals(DictionaryValueConstants.MSG_TYPE__ALLNET , entity.getMsgType()) && null ==entity.getExpireTime()){
		    throw new MessageException("失效日期不能为空");
		}
		//设置消息发送日期
		entity.setPostDate(new Date());
		//消息状态 未处理
		entity.setStatus(MessageConstants.MSG__STATUS__PROCESSING);
		//保存消息分发信息
		//int i=instationJobMsgDao.insertInstationJobMsg(entity);
		/*if(1!=i){
			throw new MessageException("发送消息失败");
		}*/
		this.batchCreateInstationMsg(entity, createType);
		LOGGER.info("successfully exit service, receiverOrgCode: " + entity.getReceiveOrgCode());
	}
	
	/**
	 * sonar优化拆分
	 * 
	 * @author 313353
	 */
	private void sonarSplitThree(InstationJobMsgEntity entity) {
		if (StringUtil.isEmpty(entity.getSenderCode())) {
			throw new MessageException("发送人员编码不能为空");
		}
		if (StringUtil.isEmpty(entity.getSenderName())) {
			throw new MessageException("发送人员名称不能为空");
		}
		if (StringUtil.isEmpty(entity.getSenderOrgCode())) {
			throw new MessageException("发送方组织编码不能为空");
		}
		if (StringUtil.isEmpty(entity.getSenderOrgName())) {
			throw new MessageException("发送方组织名称不能为空");
		}
	}
	 
	/**
	 * 根据站内消息分发表中的未处理状态,批量生成站内消息
	 * @author 101911-foss-zhouChunlai
	 * @date 2012-12-13 上午9:47:13
	 */
	@Transactional
	public void batchCreateInstationMsg(InstationJobMsgEntity msgEntity,String createType) { 
		if(null != msgEntity){
			if(StringUtils.equals(msgEntity.getMsgType(), "FAILINGINVOICE")){
					InstationMsgEntity instationMsgEntity=new InstationMsgEntity();
					//id
					instationMsgEntity.setId(UUIDUtils.getUUID());
					//发送方编码
					instationMsgEntity.setSendUserCode(msgEntity.getSenderCode());
					//发送方名称
					instationMsgEntity.setSendUserName(msgEntity.getSenderName());
					//发送方所属部门编码
					instationMsgEntity.setSendOrgCode(msgEntity.getSenderOrgCode());
					//发送方所属部门名称
					instationMsgEntity.setSendOrgName(msgEntity.getSenderOrgName());
					//站内消息类型
					instationMsgEntity.setMsgType(msgEntity.getMsgType());
					//状态
					instationMsgEntity.setActive(FossConstants.ACTIVE);
					//生成方式为自动生成
					instationMsgEntity.setCreateType(createType);
					//记录消息状态为未读
					instationMsgEntity.setIsReaded(DictionaryValueConstants.MSG__READ_STATUS__UNREAD);
					//创建时间
					instationMsgEntity.setCreateTime(msgEntity.getPostDate());
					//失效时间
					instationMsgEntity.setExpireTime(msgEntity.getExpireTime());
					//消息内容 
					instationMsgEntity.setContext(msgEntity.getContext());
					//接收方组织编码
					instationMsgEntity.setReceiveOrgCode(msgEntity.getReceiveOrgCode());
					//接收方组织名称
					instationMsgEntity.setReceiveOrgName(msgEntity.getReceiveOrgName());
					//接收方下级组织编码
					instationMsgEntity.setReceiveSubOrgCode(msgEntity.getReceiveOrgCode());
					//接收方下级组织名称
					instationMsgEntity.setReceiveSubOrgName(msgEntity.getReceiveOrgName());
					//接收方角色编码
					instationMsgEntity.setReceiveRoleCode(msgEntity.getReceiveRoleCode());
					//接收方类型
					instationMsgEntity.setReceiveType(msgEntity.getReceiveType());
					//状态
					instationMsgEntity.setStatus(msgEntity.getStatus());
					//序号标识
					instationMsgEntity.setSerialNumber(UUIDUtils.getUUID());
					
					instationMsgDao.insertInstationMsg(instationMsgEntity);
				
				}else{
				// 获取接收方组织及所有下级组织
				List<OrgAdministrativeInfoEntity> orgList = orgAdministrativeInfoComplexService.queryOrgAdministrativeInfoEntityAllSubByCode(msgEntity.getReceiveOrgCode());
				 if(CollectionUtils.isNotEmpty(orgList)){
					//记录要保存的消息列表
					List<InstationMsgEntity> instationMsgList=new ArrayList<InstationMsgEntity>();
					//序号标识
					String serialNumber = UUIDUtils.getUUID();
					for(OrgAdministrativeInfoEntity orgEntity : orgList){
						InstationMsgEntity instationMsgEntity=new InstationMsgEntity();
						//id
						instationMsgEntity.setId(UUIDUtils.getUUID());
						//发送方编码
						instationMsgEntity.setSendUserCode(msgEntity.getSenderCode());
						//发送方名称
						instationMsgEntity.setSendUserName(msgEntity.getSenderName());
						//发送方所属部门编码
						instationMsgEntity.setSendOrgCode(msgEntity.getSenderOrgCode());
						//发送方所属部门名称
						instationMsgEntity.setSendOrgName(msgEntity.getSenderOrgName());
						//站内消息类型
						instationMsgEntity.setMsgType(msgEntity.getMsgType());
						//状态
						instationMsgEntity.setActive(FossConstants.ACTIVE);
						//生成方式为自动生成
						instationMsgEntity.setCreateType(createType);
						//记录消息状态为未读
						instationMsgEntity.setIsReaded(DictionaryValueConstants.MSG__READ_STATUS__UNREAD);
						//创建时间
						instationMsgEntity.setCreateTime(msgEntity.getPostDate());
						//失效时间
						instationMsgEntity.setExpireTime(msgEntity.getExpireTime());
						//消息内容 
						instationMsgEntity.setContext(msgEntity.getContext());
						//接收方组织编码
						instationMsgEntity.setReceiveOrgCode(msgEntity.getReceiveOrgCode());
						//接收方组织名称
						instationMsgEntity.setReceiveOrgName(msgEntity.getReceiveOrgName());
						//接收方下级组织编码
						instationMsgEntity.setReceiveSubOrgCode(orgEntity.getCode());
						//接收方下级组织名称
						instationMsgEntity.setReceiveSubOrgName(orgEntity.getName());
						//接收方角色编码
						instationMsgEntity.setReceiveRoleCode(msgEntity.getReceiveRoleCode());
						//接收方类型
						instationMsgEntity.setReceiveType(msgEntity.getReceiveType());
						//状态
						instationMsgEntity.setStatus(msgEntity.getStatus());
						//序号标识
						instationMsgEntity.setSerialNumber(serialNumber);
						//保存消息实体
						instationMsgList.add(instationMsgEntity);
					}
					//如果消息记录实体不为空则保存消息内容
					if(CollectionUtils.isNotEmpty(instationMsgList)){
	    				//批量保存消息
						//instationMsgDao.batchSaveMsg(instationMsgList);
					    for(InstationMsgEntity entity : instationMsgList){
						instationMsgDao.insertInstationMsg(entity);
					    }
					}
				}
				
			  }
			
		}
	}
	 
	/**
	 * 根据条件查询站内消息
	 * @author 101911-foss-zhouChunlai
	 * @date 2012-12-12 下午8:02:56
	 */
	@Override
	public List<MessagesDto> queryInstationMsgByEntity(MessagesDto dto,CurrentInfo currentInfo, int start,int limit) { 
		//对输入参数校验
		if(null == dto || null == currentInfo){
			throw new MessageException("输入参数有误");
		}
		LOGGER.info("entering  service  method ：queryInstationMsgByEntity ,receiverUserCode:"+ dto.getReceiveUserCode());
		//有效数据
		dto.setActive(FossConstants.ACTIVE);
		//当前登录人
		dto.setReceiveUserCode(currentInfo.getEmpCode());
		//当前登录人组织编码
		dto.setReceiveSubOrgCode(currentInfo.getCurrentDeptCode());
		//获取查询站内消息的结果集
		List<MessagesDto>  instationMsgList= instationMsgDao.queryInstationMsgByEntity(dto, start, limit);
		
		LOGGER.info("successfully exit service method : queryInstationMsgByEntity, resultEntityList:"+ (instationMsgList !=null ? instationMsgList.size() : 0));
		return instationMsgList;
	}
	/**
	 * 根据条件查询站内消息总条数
	 * @author 101911-foss-zhouChunlai
	 * @param 
	 * @date 2012-12-18 上午10:03:24
	 * @return 
	 */
	@Override
	public Long countQueryInstationMsgByEntity(MessagesDto dto,CurrentInfo currentInfo) {
		//对输入参数校验
		if(null == dto || null == currentInfo){
			throw new MessageException("输入参数有误");
		}
		LOGGER.info("entering  service  method ：countQueryInstationMsgByEntity ,receiverUserCode:"+ currentInfo.getEmpCode());
		//有效数据
		dto.setActive(FossConstants.ACTIVE);
		//当前登录人
		dto.setReceiveUserCode(currentInfo.getEmpCode());
		//当前登录人组织编码
		dto.setReceiveSubOrgCode(currentInfo.getCurrentDeptCode());
		return instationMsgDao.countQueryInstationMsgByEntity(dto);
	}
	/**
	 * 根据当前用户编码查询站内消息
	 * @author 101911-foss-zhouChunlai
	 * @date 2012-12-12 下午8:02:56
	 */
	@Override
	public List<MessagesDto> queryInstationMsgByEmpInfo(CurrentInfo currentInfo,int start, int limit) {
		//对输入参数校验
		if(null == currentInfo){
			throw new MessageException("用户信息不正确!");
		}
		//获取查询站内消息的结果集
		List<MessagesDto>  instationMsgList= instationMsgDao.queryInstationMsgByReceiveUserInfo(currentInfo.getEmpCode(),currentInfo.getCurrentDeptCode(),null, start, limit);
		LOGGER.info("successfully exit service method : queryInstationMsgByEntity, resultEntityList:"+ (instationMsgList !=null ? instationMsgList.size() : 0));
		return instationMsgList;
	}
	/** 
	 * 根据当前用户编码查询站内消息总条数
	 * @author 101911-foss-zhouChunlai
	 * @param 
	 * @date 2012-12-18 上午10:05:35
	 * @return 
	 * @see com.deppon.foss.module.base.common.api.server.service.IMessageService#countQueryInstationMsgByCurrentInfo(com.deppon.foss.module.base.common.api.shared.domain.InstationMsgEntity)
	 */
	@Override
	public Long  countQueryInstationMsgByEmpInfo(CurrentInfo currentInfo) {
		return instationMsgDao.countQueryInstationMsgByReceiveUserInfo(currentInfo.getEmpCode(),currentInfo.getCurrentDeptCode(),null);
	}
	
	/**
	 * 根据Id更新站内消息的读取状态
	 * @author 101911-foss-zhouChunlai
	 * @param 
	 * @date 2012-12-25 下午3:19:12
	 * @return 
	 */
	@Override
	public void  updateInstationMsgByIds(List<String> ids,CurrentInfo currentInfo) {
		if(CollectionUtils.isEmpty(ids)){
			throw new MessageException("站内消息Id不能为空");
		}
		int i=instationMsgDao.updateInstationMsgByIds(ids,currentInfo.getEmpCode(),currentInfo.getEmpName());
		if(i==0){ 
			throw new MessageException("更新失败");
		} 
	}
	
	/**
	 * 统计当前用户未处理站内消息数据总条数
	 * @author 101911-foss-zhouChunlai
	 * @date 2012-12-17 上午10:56:22
	 */
	@Override
	public List<MessagesDto> queryInstationMsgTotal(CurrentInfo currentInfo,String msgType){
		//对输入参数校验
		if(null == currentInfo){
			throw new MessageException("用户信息不正确!");
		}
		if(StringUtils.isBlank(msgType)){
			throw new MessageException("请输入消息类型");
		}
		return instationMsgDao.queryInstationMsgTotal(currentInfo.getEmpCode(),currentInfo.getCurrentDeptCode(),null, msgType);
	}
	/** 
	 * 查询当前用户未处理站内消息数据
	 * @author 101911-foss-zhouChunlai
	 * @param 
	 * @date 2012-12-29 上午8:32:45
	 * @return 
	 * @see com.deppon.foss.module.base.common.api.server.dao.IInstationMsgDao#queryInstationMsgTotal(java.lang.String, java.lang.String)
	 */
	@Override
	public List<MessagesDto> queryMsgByMsgType(String msgType,CurrentInfo currentInfo, int start, int limit) { 
		if(null == currentInfo){
			throw new MessageException("Session失效，请重新登录");
		}
		if(StringUtils.isBlank(msgType)){
			throw new MessageException("请输入消息类型");
		}
		List<MessagesDto> instationMsgList = instationMsgDao.queryMsgByMsgType(currentInfo.getEmpCode(),currentInfo.getCurrentDeptCode(),null, msgType,start,limit);
		
		return instationMsgList;
	}
	
	/**
	 *	查询当前用户未处理站内消息数据总条数
	 * @author 101911-foss-zhouChunlai
	 * @date 2012-12-17 上午11:05:43
	 */
	@Override
	public long countQueryMsgByMsgType(String msgType,CurrentInfo currentInfo) { 
		if(null == currentInfo){
			throw new MessageException("Session失效，请重新登录");
		}
		if(StringUtils.isBlank(msgType)){
			throw new MessageException("请输入消息类型");
		}
		
		return instationMsgDao.countQueryMsgByMsgType(currentInfo.getEmpCode(),currentInfo.getCurrentDeptCode(),null, msgType);
	} 
	
	/**
	 * 统计当前用户未处理站内消息数据总条数
	 * @author 132599-foss-shenweihua
	 * @date 2013-07-22  下午3:36:22
	 */
	@Override
	public List<MessagesDto> queryQWInstationMsgTotal(CurrentInfo currentInfo) {
		List<MessagesDto> messagesDtos = new ArrayList<MessagesDto>();
		MessagesDto messagesDto = new MessagesDto();
			if(null == currentInfo){
				throw new MessageException("用户信息不正确!");
			}
		int count = msgOnlineDao.queryInstationMsgTotal(currentInfo);
		messagesDto.setNoDealNum(count);
		messagesDtos.add(messagesDto);
		return messagesDtos;
	}
	
	@Override
	public List<MessagesDto> queryFailingInvoiceMsgTotal(CurrentInfo currentInfo, String msgType) {
		//对输入参数校验
		if(null == currentInfo){
			throw new MessageException("用户信息不正确!");
		}
		return instationMsgDao.queryFailingInvoiceMsgTotal(currentInfo.getCurrentDeptCode(), msgType);
	}
	
	public IEmployeeService getEmployeeService() {
		return employeeService;
	}

	
	public void setEmployeeService(IEmployeeService employeeService) {
		this.employeeService = employeeService;
	}
 
	public IUserOrgRoleService getUserOrgRoleService() {
		return userOrgRoleService;
	}

	
	public void setUserOrgRoleService(IUserOrgRoleService userOrgRoleService) {
		this.userOrgRoleService = userOrgRoleService;
	}

	public IOrgAdministrativeInfoService getOrgAdministrativeInfoService() {
		return orgAdministrativeInfoService;
	}

	
	public void setOrgAdministrativeInfoService(
			IOrgAdministrativeInfoService orgAdministrativeInfoService) {
		this.orgAdministrativeInfoService = orgAdministrativeInfoService;
	}

	public IInstationMsgDao getInstationMsgDao() {
		return instationMsgDao;
	}

	
	public void setInstationMsgDao(IInstationMsgDao instationMsgDao) {
		this.instationMsgDao = instationMsgDao;
	}
	public IOrgAdministrativeInfoComplexService getOrgAdministrativeInfoComplexService() {
		return orgAdministrativeInfoComplexService;
	}
	public void setOrgAdministrativeInfoComplexService(
			IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService) {
		this.orgAdministrativeInfoComplexService = orgAdministrativeInfoComplexService;
	}
	
	public void setMsgOnlineDao(IMsgOnlineDao msgOnlineDao) {
		this.msgOnlineDao = msgOnlineDao;
	}
	/** 
	 * <p>TODO(查询未对账月结客户消息)</p> 
	 * @author 187862-dujunhui
	 * @date 2014-7-2 上午9:39:33
	 * @param currentInfo
	 * @param msgTypeUnreconciliation
	 * @return 
	 * @see com.deppon.foss.module.base.common.api.server.service.IMessageService#queryUnReconciliationMsgTotal(com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo, java.lang.String)
	 */
	@Override
	public List<MessagesDto> queryUnReconciliationMsgTotal(
			CurrentInfo currentInfo, String msgType) {
		// TODO Auto-generated method stub
		//对输入参数校验
		if(null == currentInfo){
			throw new MessageException("用户信息不正确!");
		}
		return instationMsgDao.queryUnReconciliationMsgTotal(currentInfo.getCurrentDeptCode(), msgType);
	}
	/**
	 * 派送退回提醒
	 * @author 307196-zhaoliujun
	 * @param exceptionOperate
	 * @param status
	 * @return
	 */
	@Override
	public List<MessagesDto> queryDeliveryReturnMsgTotal(String exceptionOperate, String status,CurrentInfo currentInfo){
		//对输入参数校验
		if(null == currentInfo){
			throw new MessageException("用户信息不正确!");
		}
		ExceptionProcessConditionDto exceptionProcessConditionDto = new ExceptionProcessConditionDto();
		
		exceptionProcessConditionDto.setExceptionOperate(exceptionOperate);
		
		exceptionProcessConditionDto.setStatus(status);
		//部门
		exceptionProcessConditionDto.setDepartmentCode(currentInfo.getCurrentDeptCode());
		List<MessagesDto> listMsgDto = new ArrayList<MessagesDto>();
		MessagesDto messagesDto = new MessagesDto();
//		if(exceptionOperate.equals("PKP_EXCEPTION_SEND_RETURN")){
			messagesDto.setNoDealNum((int)getTotalNum(exceptionProcessConditionDto));
			messagesDto.setExceptionOperate(exceptionOperate);
			listMsgDto.add(messagesDto);
//		}else{
//			messagesDto.setNoDealNum((int)getTotalNum(exceptionProcessConditionDto));
//			messagesDto.setExceptionOperate(exceptionOperate);
//			listMsgDto.add(messagesDto);
//		}
		
		return listMsgDto;
	}
	/**
	 * 获取总条数
	 */
	public long getTotalNum(ExceptionProcessConditionDto exceptionProcessConditionDto){
		Long count1 = Long.valueOf(0);
		Long count2 = Long.valueOf(0);
		// 返回收货部门查询总条数
		count1 = exceptionProcessDao
				.queryExceptionProcessInfoCount(exceptionProcessConditionDto);
		// 返回最终配载部门查询总条数
		count2 = exceptionProcessDao
				.queryExceptionProcessInfoCountLast(exceptionProcessConditionDto);

		return count1+count2;
	}
	/** 
	 * <p>TODO(查询距结款时间不足5日还未还款月结客户消息)</p> 
	 * @author 187862-dujunhui
	 * @date 2014-7-2 上午9:39:33
	 * @param currentInfo
	 * @param msgTypeNorepayment
	 * @return 
	 * @see com.deppon.foss.module.base.common.api.server.service.IMessageService#queryNoRepaymentMsgTotal(com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo, java.lang.String)
	 */
	@Override
	public List<MessagesDto> queryNoRepaymentMsgTotal(CurrentInfo currentInfo,
			String msgType) {
		// TODO Auto-generated method stub
		//对输入参数校验
		if(null == currentInfo){
			throw new MessageException("用户信息不正确!");
		}
		return instationMsgDao.queryNoRepaymentMsgTotal(currentInfo.getCurrentDeptCode(), msgType);
	}
	

}
