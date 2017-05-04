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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/common/server/service/impl/ToDoMsgService.java
 * 
 * FILE NAME        	: ToDoMsgService.java
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
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.base.util.define.BizTypeConstants;
import com.deppon.foss.base.util.define.MessageConstants;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.server.service.ISaleDepartmentService;
import com.deppon.foss.module.base.baseinfo.api.server.service.complex.IOrgAdministrativeInfoComplexService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SaleDepartmentEntity;
import com.deppon.foss.module.base.common.api.server.dao.IToDoMsgDao;
import com.deppon.foss.module.base.common.api.server.service.IToDoMsgService;
import com.deppon.foss.module.base.common.api.shared.domain.ToDoMsgEntity;
import com.deppon.foss.module.base.common.api.shared.domain.ToDoMsgResult;
import com.deppon.foss.module.base.common.api.shared.dto.ToDoMsgDto;
import com.deppon.foss.module.base.common.api.shared.dto.TodoDto;
import com.deppon.foss.module.base.common.api.shared.exception.MessageException;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryValueConstants;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.pickup.waybill.shared.util.PropertiesUtil;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

/**
 * 待办事项Service
 * 
 * @author 101911-foss-zhouChunlai
 * @date 2012-12-12 下午7:08:02
 */
public class ToDoMsgService implements IToDoMsgService {
	/**
	 * logger
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(ToDoMsgService.class);

	/**
	 * 组织Service
	 */
	private IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService;
	
	private ISaleDepartmentService saleDepartmentService;
	private IOrgAdministrativeInfoService orgAdministrativeInfoService;

	/**
	 * 待办事项Dao
	 */
	private IToDoMsgDao toDoMsgDao;

	/**
	 * 创建待办(增量) 同种待办类型不同业务单号的待办进行新建待办
	 * 
	 * @param entity 待办事项实体
	 * @return 返回创建待办时成功和失败信息
	 * @author 101911-foss-zhouChunlai
	 * @date 2012-12-12 下午5:42:55
	 */
	@Override
	public List<ToDoMsgResult> createIncrementToDoMsg(List<ToDoMsgEntity> entityList) {
		// 校验传入参数
		if (CollectionUtils.isEmpty(entityList)) {
			return null;
		}
		LOGGER.info("enter  createIncrementToDoMsg ,size:" + entityList.size());
		// 创建待办时成功和失败信息的反馈结果集合
		List<ToDoMsgResult> toDoMsgRltList = new ArrayList<ToDoMsgResult>();
		for (ToDoMsgEntity entity : entityList) {
			// 待办结果实体
			ToDoMsgResult toDoMsgResult = new ToDoMsgResult(entity);
			// 记录错误信息
			String errorMsgInfo = toDoMsgResult.getInsertChkRlt();
			// 如果错误信息不会空，则记录错误信息，continue
			if (StringUtil.isNotBlank(errorMsgInfo)) {
				// 设置错误提示信息
				toDoMsgResult.setErrorMsg(errorMsgInfo);
				// 设置是否成功标识
				toDoMsgResult.setIsSuccess(false);
				// 添加具体待办实体
				toDoMsgResult.setToDoMsgEntity(entity);
				// 添加待办反馈信息
				toDoMsgRltList.add(toDoMsgResult);
				continue;
			} else {
				// 设置是否成功标识
				toDoMsgResult.setIsSuccess(true);
				// 添加具体待办实体
				toDoMsgResult.setToDoMsgEntity(entity);
				// 添加待办反馈信息
				toDoMsgRltList.add(toDoMsgResult);
			}
			//获取组织及组织下级所有的待办事项
			List<ToDoMsgEntity> toDoMsgLst= this.createToDoMsgEntity(entity);
			if(CollectionUtils.isNotEmpty(toDoMsgLst)){
        		// 批量保存待办事项
//        		toDoMsgDao.batchSaveToDoMsg(toDoMsgLst);
			    for(ToDoMsgEntity entity2 : toDoMsgLst){
			    	//类型为 快递补录， 则把对应的带补录单号的待补录消息标识为已读
			    	if(entity2.getBusinessType().equals(DictionaryValueConstants.TODOMSG__BUSINESS_TYPE__WAYBILL_PENDING)){
			    		toDoMsgDao.updateToDoMsgByCondition(entity2.getBusinessNo(),DictionaryValueConstants.TODOMSG__BUSINESS_TYPE__ECS_PDA_WAYBILL);
			    	}else{
				        toDoMsgDao.saveToDoMsg(entity2);
			    	}
			    }
			}
		}
		LOGGER.info("success exit createIncrementToDoMsg ");

		return toDoMsgRltList;
	}
	
	/**
	 * 单个结束待办
	 * 对同待办类型同业务单号的待办进行结束处理操作
	 * @param receiveOrgCode 接收网点编码
	 * @param businessType 待力业务类型
	 * @param serialNumber 待办业务流水号
	 * @return 保存待办的反馈结果
	 * @author 101911-foss-zhouChunlai
	 * @date 2012-12-12 下午5:42:55 
	 */
	@Override
	public ToDoMsgResult finishToDoMsg(String receiveOrgCode,String businessType, String serialNumber) {
		//实例化待办消息实体
		ToDoMsgEntity resultEntity=new ToDoMsgEntity();
		//设置接收组织
		resultEntity.setReceiveOrgCode(receiveOrgCode);
		//设置待办类型
		resultEntity.setBusinessType(businessType);
		//设置待办序列编码
		resultEntity.setSerialNumber(serialNumber);
		//校验保存参数合法性
		ToDoMsgResult toDoMsgResult=new ToDoMsgResult(resultEntity);
		//返回错误信息
		String errorMsgInfo=toDoMsgResult.checkSerialNumber(serialNumber);
		//判断是否存在错误信息 如存在则返回错误信息列表，不存在则进行更新操作
		if (StringUtil.isNotBlank(errorMsgInfo)) {
			// 设置错误提示信息
			toDoMsgResult.setErrorMsg(errorMsgInfo);
			// 设置是否成功标识
			toDoMsgResult.setIsSuccess(false);
			return toDoMsgResult;
		} else {
			// 设置是否成功标识
			toDoMsgResult.setIsSuccess(true);
			//对数据进行更新
			toDoMsgDao.updateToDoMsgByCondition(receiveOrgCode,businessType,serialNumber);
			return toDoMsgResult;
		}
	}
	/**
	 * 单个结束待办给接送货
	 * 对同待办类型同业务单号的待办进行结束处理操作
	 * @param receiveOrgCode 接收网点编码
	 * @param businessType 待力业务类型
	 * @param serialNumber 待办业务流水号
	 * @return 保存待办的反馈结果
	 * @author 130346-foss-lifanghong
	 * @date 2013-07-16 下午5:42:55 
	 */
	@Override
	public ToDoMsgResult finishToDoMsgs(String receiveOrgCode,String businessType, String serialNumber) {
		//实例化待办消息实体
		ToDoMsgEntity resultEntity=new ToDoMsgEntity();
		//设置接收组织
		resultEntity.setReceiveOrgCode(receiveOrgCode);
		//设置待办类型
		resultEntity.setBusinessType(businessType);
		//设置待办序列编码
		resultEntity.setSerialNumber(serialNumber);
		//校验保存参数合法性
		ToDoMsgResult toDoMsgResult=new ToDoMsgResult(resultEntity);
		//返回错误信息
		String errorMsgInfo=toDoMsgResult.checkSerialNumber(serialNumber);
		//判断是否存在错误信息 如存在则返回错误信息列表，不存在则进行更新操作
		if (StringUtil.isNotBlank(errorMsgInfo)) {
			// 设置错误提示信息
			toDoMsgResult.setErrorMsg(errorMsgInfo);
			// 设置是否成功标识
			toDoMsgResult.setIsSuccess(false);
			return toDoMsgResult;
		} else {
			// 设置是否成功标识
			toDoMsgResult.setIsSuccess(true);
			//对数据进行更新
			toDoMsgDao.updateMsgByCondition(receiveOrgCode,businessType,serialNumber);
			return toDoMsgResult;
		}
	}
	/**
	 * 创建待办(全量) 插入前先删除同类型的待办再新建
	 * 
	 * @param entityList
	 *            待办事项实体列表
	 * @author 101911-foss-zhouChunlai
	 * @date 2012-12-12 下午5:42:55
	 */
	@Override
	public List<ToDoMsgResult> createFullToDoMsg(List<ToDoMsgEntity> entityList) {
		// 校验传入参数
		if (CollectionUtils.isEmpty(entityList)) {
			return null;
		}
		LOGGER.info("enter  createIncrementToDoMsg ,size:" + entityList.size());
		//批量删除 参数为 接收方组织+接收角色+接收方类型+业务类型
//		toDoMsgDao.deleteToDoMsg(entityList);
		for(ToDoMsgEntity entity : entityList){
		    toDoMsgDao.deleteToDoMsgOne(entity);
		}
		// 创建待办时成功和失败信息的反馈结果集合
		List<ToDoMsgResult> toDoMsgRltList = new ArrayList<ToDoMsgResult>();
		for (ToDoMsgEntity entity : entityList) {
			// 待办结果实体
			ToDoMsgResult toDoMsgResult = new ToDoMsgResult(entity);
			// 记录错误信息
			String errorMsgInfo = toDoMsgResult.getInsertChkRlt();
			// 如果错误信息不会空，则记录错误信息，continue
			if (StringUtil.isNotBlank(errorMsgInfo)) {
				// 设置错误提示信息
				toDoMsgResult.setErrorMsg(errorMsgInfo);
				// 设置是否成功标识
				toDoMsgResult.setIsSuccess(false);
				// 添加具体待办实体
				toDoMsgResult.setToDoMsgEntity(entity);
				// 添加待办反馈信息
				toDoMsgRltList.add(toDoMsgResult);
				continue;
			} else {
				// 设置是否成功标识
				toDoMsgResult.setIsSuccess(true);
				// 添加具体待办实体
				toDoMsgResult.setToDoMsgEntity(entity);
				// 添加待办反馈信息
				toDoMsgRltList.add(toDoMsgResult);
			}
			//获取组织及组织下级所有的待办事项
			List<ToDoMsgEntity> toDoMsgLst= this.createToDoMsgEntity(entity);
			if(CollectionUtils.isNotEmpty(toDoMsgLst)){
        		// 批量保存待办事项
//        		toDoMsgDao.batchSaveToDoMsg(toDoMsgLst);
			    for(ToDoMsgEntity entity2 : toDoMsgLst){
				toDoMsgDao.saveToDoMsg(entity2);
			    }
			}
		}
		LOGGER.info("success exit createIncrementToDoMsg ");
		return toDoMsgRltList;
	}
	
	/**
	 * 创建组织及下级组织待办的公共方法
	 * @author 101911-foss-zhouChunlai
	 * @date 2012-12-15 下午4:40:29
	 */
	private List<ToDoMsgEntity> createToDoMsgEntity(ToDoMsgEntity entity){
		// 获取接收方组织及所有下级组织
		List<OrgAdministrativeInfoEntity> orgList = orgAdministrativeInfoComplexService
				.queryOrgAdministrativeInfoEntityAllSubByCode(entity.getReceiveOrgCode());
		// 保存待办事项记录
		List<ToDoMsgEntity> toDoMsgLst = new ArrayList<ToDoMsgEntity>();
		// 待办创建时间
		Date date = new Date();
		// 接收方组织编码
		String receiveOrgCode = entity.getReceiveOrgCode();
		// 为接收方组织及下级组织都生成待办事项
		for (OrgAdministrativeInfoEntity orgEntity : orgList) {
			ToDoMsgEntity toDoEntity = new ToDoMsgEntity();
			toDoEntity.setId(UUIDUtils.getUUID());// Id标识
			toDoEntity.setReceiveOrgCode(receiveOrgCode);// 接收方组织编码
			toDoEntity.setReceiveSubOrgCode(orgEntity.getCode());// 接收方下级组织编码
			toDoEntity.setReceiveSubOrgName(orgEntity.getName());// 接收方下级组织名称
			toDoEntity.setReceiveRoleCode(entity.getReceiveRoleCode());// 接收方角色编码
			toDoEntity.setReceiveType(entity.getReceiveType());// 接收方类型
			toDoEntity.setStatus(DictionaryValueConstants.TODOMSG__STATUS_TYPE__PROCESSING);// 未处理
			toDoEntity.setTitle(entity.getTitle());// 待办标题
			toDoEntity.setBusinessType(entity.getBusinessType());// 待办类型
			toDoEntity.setBusinessNo(entity.getBusinessNo());// 业务单号
			toDoEntity.setSerialNumber(entity.getSerialNumber());//业务流水号
			toDoEntity.setDealUrl(entity.getDealUrl());// 处理待办地址
			if(StringUtil.isEmpty(entity.getUrlType())){
				//默认为WEB段待办事项
				toDoEntity.setUrlType(MessageConstants.MSG__URL_TYPE__WEB);
			}else{
				toDoEntity.setUrlType(entity.getUrlType());
			}
			toDoEntity.setCreateTime(date);// 待办创建时间
			toDoEntity.setCreateUserCode(entity.getCreateUserCode());// 待办创建人编码
			toDoEntity.setCreateUserName(entity.getCreateUserName());// 待办创建人名称
			// 保存待办记录
			toDoMsgLst.add(toDoEntity);
		}
		
		return toDoMsgLst;
	}
	/**
	 * 根据查询条件查询待办明细
	 * 
	 * @author 101911-foss-zhouChunlai
	 * @date 2012-12-12 下午6:53:11
	 */
	@Override
	public List<ToDoMsgDto> queryToDoMsgDetail(ToDoMsgDto dto){
		// 校验传入参数
		if (null == dto) {
			throw new MessageException("输入参数错误");
		}
		if(StringUtil.isBlank(dto.getReceiveOrgCode())){
			throw new MessageException("接收方网点编码不能为空");
		}
		LOGGER.info("enter  queryToDoMsgDetail ,receiverOrgCode:"+ dto.getReceiveOrgCode());
		List<ToDoMsgDto> toDoMsgEntityList=toDoMsgDao.selectToDoMsgDetailByEntity(dto);
		LOGGER.info("success exit queryToDoMsgDetail resultEntityList:"+ (toDoMsgEntityList !=null ? toDoMsgEntityList.size() : 0));
		return toDoMsgEntityList;
	}
	
	
	

	/**
	 * 根据businesstype分类统计代办个数
	 * @author shixiaowei
	 * @param receiveOrgCode
	 * @param status
	 * @return
	 */
	public List<ToDoMsgDto> countToDoMsgDetail(String receiveOrgCode,
			String status) {
		// 校验传入参数
		if(StringUtil.isBlank(receiveOrgCode)){
			throw new MessageException("接收方网点编码不能为空");
		}
		LOGGER.info("enter  countToDoMsgDetail ,receiverOrgCode:"+ receiveOrgCode);
		List<ToDoMsgDto> toDoMsgEntityList=toDoMsgDao.countToDoMsgDetailGroupByBusinessType(receiveOrgCode,status);		
				
		return toDoMsgEntityList;
	}
	
	/**
	 * 根据接收组织，角色，业务类型和状态查询待办明细
	 * @param receiveOrgCode 接收组织编码
	 * @param receiveRoleCodes 接收角色集合
	 * @param businessType 待办业务类型
	 * @param status 待办状态
	 * @author 101911-foss-zhouChunlai
	 * @date 2012-12-12 下午6:53:11
	 */
	@Override
	public List<ToDoMsgDto> queryToDoMsgDetail(String receiveOrgCode,Set<String> receiveRoleCodes,String businessType,String status){
		// 校验传入参数
		if(StringUtil.isBlank(receiveOrgCode)){
			throw new MessageException("接收方网点编码不能为空");
		}
		LOGGER.info("enter  queryToDoMsgDetail ,receiverOrgCode:"+ receiveOrgCode);
		List<ToDoMsgDto> toDoMsgEntityList=toDoMsgDao.selectToDoMsgDetailByCondition(receiveOrgCode,receiveRoleCodes,businessType,status);
		LOGGER.info("success exit queryToDoMsgDetail resultEntityList:"+ (toDoMsgEntityList !=null ? toDoMsgEntityList.size() : 0));
		return toDoMsgEntityList;
	}
	
	
	/**
	 * 根据接收网点编码,接收方角色,待办类型,和状态 查询待办明细 查询一条代办
	 * @author shixiaowei
	 * @param receiveOrgCode 接收组织编码
	 * @param receiveRoleCodes 接收角色集合
	 * @param businessType 待办业务类型
	 * @param status 待办状态
	 * @author 101911-foss-zhouChunlai
	 * @date 2012-12-12 下午6:53:11
	 */
	@Override
	public List<ToDoMsgDto> queryToDoMsgDetailOne(String receiveOrgCode,
			Set<String> receiveRoleCodes,String businessType,String status){
		// 校验传入参数
		if(StringUtil.isBlank(receiveOrgCode)){
			throw new MessageException("接收方网点编码不能为空");
		}
		LOGGER.info("enter  queryToDoMsgDetailOne ,receiverOrgCode:"+ receiveOrgCode);
		List<ToDoMsgDto> toDoMsgEntityList=toDoMsgDao.selectToDoMsgDetailByConditionOne(receiveOrgCode,receiveRoleCodes,businessType,status);

		return toDoMsgEntityList;
	}
	
	//是否派送营业部
		private int INDEX_ORIGINAL = 0;
		private int INDEX_FINAL = 1;
		private int INDEX_ISDELIVERY = 2;
		private List<String> getCurrentDeptCode() {
	    	//当前登陆部门Code集合
	    	List<String> currentDeptCodeList = new ArrayList<String>();
	    	
	    	String deptCode = FossUserContext.getCurrentDeptCode();
	    	//原始部门
	    	currentDeptCodeList.add(deptCode);
	    	//是否派送营业部
	    	boolean isStation = false;
	    	SaleDepartmentEntity saleDepartmentEntity = saleDepartmentService.querySaleDepartmentByCode(deptCode);
	    	if(saleDepartmentEntity != null){
	    		//如果是派送中心，查询对应外场的待办
	    		if(FossConstants.YES.equals(saleDepartmentEntity.getStation())){
	    			String transferCenter = saleDepartmentEntity.getTransferCenter();
	    			if(StringUtil.isNotEmpty(transferCenter)){
	    				deptCode = transferCenter;
	    			}
	    	
	    			isStation = true;
	    		}
	    	}else{ //增加是否集中开单组的判断
	            OrgAdministrativeInfoEntity kaidanzu = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(deptCode);
	            if(FossConstants.YES.equals(kaidanzu.getBillingGroup())){
	              deptCode = "";
	            }else{
	              //非营业部找到它上级所属外场的编码
	                List<String> bizTypes = new ArrayList<String>();
	                bizTypes.add(BizTypeConstants.ORG_TRANSFER_CENTER);
	                OrgAdministrativeInfoEntity orgAdministrativeInfoEntity = orgAdministrativeInfoComplexService.queryOrgAdministrativeInfoIncludeSelfByCode(deptCode, bizTypes);
	                if(orgAdministrativeInfoEntity != null){
	                  deptCode = orgAdministrativeInfoEntity.getCode();
	                }
	            }    }
	    	//转换后部门
	    	currentDeptCodeList.add(deptCode);
	    	currentDeptCodeList.add(isStation?FossConstants.YES:FossConstants.NO);
	    	
			return currentDeptCodeList;
		}

	
	
	/**
	 * 统计当前用户未处理的待办事项
	 * @author 101911-foss-zhouChunlai
	 * @date 2012-12-17 上午10:56:22
	 */
	public List<ToDoMsgDto> queryToDoMsgTotal(CurrentInfo currentInfo,String urlType){
		//校验传入参数
//		if(null==currentInfo){
//			throw new MessageException("当前用户信息有误");
//		}
//		//获取当前用户下所有角色
//		Set<String> receiveRoleCodes=currentInfo.getUser().getRoleids();
		//return toDoMsgDao.queryToDoMsgTotal(currentInfo.getCurrentDeptCode(), receiveRoleCodes,urlType);
		
		List<String> currentDeptCodeList = getCurrentDeptCode();
		TodoDto dto = new TodoDto();
		if(FossConstants.YES.equals(currentDeptCodeList.get(INDEX_ISDELIVERY))){
    		//派送部
    		dto.setLastLoadOrgCode(currentDeptCodeList.get(INDEX_ORIGINAL));
    	}
		dto.setHandleOrgCode(currentDeptCodeList.get(INDEX_FINAL));
		String deptCode = FossUserContext.getCurrentDeptCode();//获取当前部门
		dto.setCurrentDept(deptCode);//设置当前部
		//查询更改单标签重打的待办个数
		List<ToDoMsgDto> dtoList = new ArrayList<ToDoMsgDto>();		
		Integer count = toDoMsgDao.queryTOdoMsgTotalFromWaybill(dto);
		if(count!=0){
			ToDoMsgDto lableMsg = new ToDoMsgDto();
			lableMsg.setNoDealNum(count);
			lableMsg.setBusinessType(DictionaryValueConstants.TODOMSG__BUSINESS_TYPE__WAYBILL_CHANGE_LABELED_PRINT);
			dtoList.add(lableMsg);
		}
		
		String toDoItemIp = PropertiesUtil.getKeyValue("foss.login.app.toDoItem.ip");
		
		//查询转寄退回件的待办个数
		long countReturnGood = this.countQueryToDoMsgByBisType(
				DictionaryValueConstants.TODOMSG__BUSINESS_TYPE__RETURN_GOODS,currentInfo,null);
		int countRG=(int)countReturnGood;
		if(countRG!=0){
			ToDoMsgDto returnGoodMsg = new ToDoMsgDto();
			returnGoodMsg.setNoDealNum(countRG);
			returnGoodMsg.setBusinessType(DictionaryValueConstants.TODOMSG__BUSINESS_TYPE__RETURN_GOODS);
			returnGoodMsg.setToDoItemIp(toDoItemIp);
			dtoList.add(returnGoodMsg);
		}
		
		//  快递更改单受理  -310854
		long acceptanceCount = this.countQueryToDoMsgByBisType(
				DictionaryValueConstants.TODOMSG__BUSINESS_TYPE__WAYBILL_CHANGE_ACCEPTANCE,currentInfo,null);
		int countAC=(int)acceptanceCount;
		if(countAC!=0){
			ToDoMsgDto returnGoodMsg = new ToDoMsgDto();
			returnGoodMsg.setNoDealNum(countAC);
			returnGoodMsg.setBusinessType(DictionaryValueConstants.TODOMSG__BUSINESS_TYPE__WAYBILL_CHANGE_ACCEPTANCE);
			returnGoodMsg.setToDoItemIp(toDoItemIp);
			dtoList.add(returnGoodMsg);
		}
	// 快递待补录提醒  
			long ecsWaybillPendingCount = this.countQueryToDoMsgByBisType(
					DictionaryValueConstants.TODOMSG__BUSINESS_TYPE__ECS_PDA_WAYBILL ,currentInfo,null);
			int countWPC=(int)ecsWaybillPendingCount;
			if(countWPC!=0){
				ToDoMsgDto returnGoodMsg = new ToDoMsgDto();
				returnGoodMsg.setNoDealNum(countWPC);
				returnGoodMsg.setBusinessType(DictionaryValueConstants.TODOMSG__BUSINESS_TYPE__ECS_PDA_WAYBILL);
				returnGoodMsg.setToDoItemIp(toDoItemIp);
				dtoList.add(returnGoodMsg);
			}
		return dtoList;
	}
	/**
	 * 统计库存超过90天自动转弃货未处理的提醒
	 * @author meiying
	 * @date 2014-6-6 上午10:56:22
	 */
	@Override
	public List<ToDoMsgDto> queryAbandGoodsTypeAutoTotal(CurrentInfo currentInfo,String urlType){
		TodoDto dto = new TodoDto();
		String deptCode = FossUserContext.getCurrentDeptCode();//获取当前部门
		dto.setLastLoadOrgCode(deptCode);//设置当前部
		Integer count = toDoMsgDao.queryAutoAbandGoodsMsgTotal(dto);
		List<ToDoMsgDto> dtoList = new ArrayList<ToDoMsgDto>();
		ToDoMsgDto msg = new ToDoMsgDto();
		msg.setNoDealNum(count);
		msg.setBusinessType(DictionaryValueConstants.TODOMSG__BUSINESS_TYPE__WAYBILL_CHANGE_LABELED_PRINT);
		dtoList.add(msg);
		return dtoList;
	}
	/**
	 * 根据待办实体参数查询待办信息 带分页
	 * @author 101911-foss-zhouChunlai
	 * @date 2012-12-17 上午10:56:22
	 */
	@Override
	public List<ToDoMsgDto> selectToDoMsgDetailByEntity(ToDoMsgDto dto,CurrentInfo currentInfo, int start, int limit){
		//校验传入参数
		if(dto==null || null==currentInfo){
			throw new MessageException("输入的参数有误");
		}
		//设置接收组织编码为当前登录用户所属组织编码
		dto.setReceiveOrgCode(currentInfo.getCurrentDeptCode());
		//设置接收下级组织编码为当前登录用户所属组织编码
		dto.setReceiveSubOrgCode(currentInfo.getCurrentDeptCode());
		//设置接收下级组织名称为当前登录用户所属组织编码
		dto.setReceiveSubOrgName(currentInfo.getCurrentDeptName());
		//设置当前用户所有角色编码
		dto.setReceiveRoleCodes(currentInfo.getUser().getRoleids());
		
		return toDoMsgDao.selectToDoMsgDetailByEntity(dto,start,limit);
	}
	
	/**
	 * 根据待办实体参数查询待办信息总条数
	 * @author 101911-foss-zhouChunlai
	 * @date 2012-12-17 上午11:05:43
	 */
	@Override
	public long countSelectToDoMsgDetailByEntity(ToDoMsgDto dto,CurrentInfo currentInfo) {
		//校验传入参数
		if(dto==null || null==currentInfo){
			throw new MessageException("输入的参数有误");
		}
		//设置接收组织编码为当前登录用户所属组织编码
		dto.setReceiveOrgCode(currentInfo.getCurrentDeptCode());
		//设置接收下级组织编码为当前登录用户所属组织编码
		dto.setReceiveSubOrgCode(currentInfo.getCurrentDeptCode());
		//设置接收下级组织名称为当前登录用户所属组织编码
		dto.setReceiveSubOrgName(currentInfo.getCurrentDeptName());
		//设置当前用户所有角色编码
		dto.setReceiveRoleCodes(currentInfo.getUser().getRoleids());
		
		return toDoMsgDao.countSelectToDoMsgDetailByEntity(dto);
	}
	  
	/**
	 * 根据待办类型查询待办信息
	 * @author 101911-foss-zhouChunlai
	 * @param 
	 * @date 2012-12-29 下午2:51:22
	 * @return 
	 */
	@Override
	public List<ToDoMsgDto> queryToDoMsgByBisType(String businessType,CurrentInfo currentInfo,String urlType,int start, int limit) {
		//校验传入参数
		if(null==currentInfo){
			throw new MessageException("当前用户信息有误");
		}
		if(StringUtil.isBlank(businessType)){
			throw new MessageException("待办类型不能为空");
		}
		//获取当前用户下所有角色
		Set<String> receiveRoleCodes=currentInfo.getUser().getRoleids();
		return toDoMsgDao.queryToDoMsgByBisType(businessType,currentInfo.getCurrentDeptCode(), receiveRoleCodes,urlType,start,limit);
	}
	/**
	 * 根据待办类型查询待办信息总条数
	 * @author 101911-foss-zhouChunlai
	 * @param 
	 * @date 2012-12-29 下午2:47:35
	 * @return 
	 */
	@Override
	public long countQueryToDoMsgByBisType(String businessType,CurrentInfo currentInfo,String urlType) {
		//校验传入参数
		if(null==currentInfo){
			throw new MessageException("当前用户信息有误");
		}
		//获取当前用户下所有角色
		Set<String> receiveRoleCodes=currentInfo.getUser().getRoleids();
		return toDoMsgDao.countQueryToDoMsgByBisType(businessType,currentInfo.getCurrentDeptCode(), receiveRoleCodes,urlType);
	}
	
	/**
	 * CC催单未解决数量
	 * @author 132599-foss-shenweihua 
	 * 
	 */
	@Override
	public List<ToDoMsgDto> queryCallCenterWaybillMsgTotal(
			CurrentInfo currentInfo) {
		TodoDto dto = new TodoDto();
		String deptCode = FossUserContext.getCurrentDept().getUnifiedCode();//获取当前部门
		if(StringUtil.isBlank(deptCode)){
			throw new MessageException("当前部门获取为空");
		}
		dto.setLastLoadOrgCode(deptCode);//设置当前部
		Integer count = toDoMsgDao.queryCallCenterWaybillMsgTotal(dto);
		List<ToDoMsgDto> dtoList = new ArrayList<ToDoMsgDto>();
		ToDoMsgDto msg = new ToDoMsgDto();
		msg.setNoDealNum(count);
		dtoList.add(msg);
		return dtoList;
	}
	 
	public IToDoMsgDao getToDoMsgDao() {
		return toDoMsgDao;
	}

	public void setToDoMsgDao(IToDoMsgDao toDoMsgDao) {
		this.toDoMsgDao = toDoMsgDao;
	}

	public IOrgAdministrativeInfoComplexService getOrgAdministrativeInfoComplexService() {
		return orgAdministrativeInfoComplexService;
	}

	public void setOrgAdministrativeInfoComplexService(
			IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService) {
		this.orgAdministrativeInfoComplexService = orgAdministrativeInfoComplexService;
	}

	public ISaleDepartmentService getSaleDepartmentService() {
		return saleDepartmentService;
	}

	public void setSaleDepartmentService(
			ISaleDepartmentService saleDepartmentService) {
		this.saleDepartmentService = saleDepartmentService;
	}

	public void setOrgAdministrativeInfoService(
			IOrgAdministrativeInfoService orgAdministrativeInfoService) {
		this.orgAdministrativeInfoService = orgAdministrativeInfoService;
	}



	
}
