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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/waybill/server/service/impl/TodoActionService.java
 * 
 * FILE NAME        	: TodoActionService.java
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

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.base.util.define.BizTypeConstants;
import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.components.export.excel.ExportResource;
import com.deppon.foss.framework.server.components.export.excel.ExportSetting;
import com.deppon.foss.framework.server.components.export.excel.ExporterExecutor;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.server.service.ISaleDepartmentService;
import com.deppon.foss.module.base.baseinfo.api.server.service.complex.IOrgAdministrativeInfoComplexService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SaleDepartmentEntity;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.pickup.predeliver.api.shared.define.NotifyCustomerConstants;
import com.deppon.foss.module.pickup.waybill.api.server.dao.ILabeledGoodTodoDao;
import com.deppon.foss.module.pickup.waybill.api.server.dao.IPendingTodoDao;
import com.deppon.foss.module.pickup.waybill.api.server.dao.ITodoActionDao;
import com.deppon.foss.module.pickup.waybill.api.server.dao.IWaybillRfcDao;
import com.deppon.foss.module.pickup.waybill.api.server.service.ILabelPrintInfoService;
import com.deppon.foss.module.pickup.waybill.api.server.service.ITodoActionService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillManagerService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillRfcTodoJobService;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillRfcConstants;
import com.deppon.foss.module.pickup.waybill.shared.domain.LabeledGoodTodoEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.PendingTodoEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillRfcEntity;
import com.deppon.foss.module.pickup.waybill.shared.dto.BarcodePrintLabelDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.ExceptMsgActionDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.ExceptMsgConditionDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.LabelGoodTodoDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.LabeledGoodTodoDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.LatestHandOverDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.PendingMsgActionDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.PendingMsgConditionDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.PickupToDoMsgDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.ResultDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.TodoActionDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.TodoConditionDto;
import com.deppon.foss.module.pickup.waybill.shared.exception.WaybillRfcChangeException;
import com.deppon.foss.module.transfer.exceptiongoods.api.server.service.IPrintLabelService;
import com.deppon.foss.module.transfer.exceptiongoods.api.shared.domain.PrintLabelEntity;
import com.deppon.foss.util.DateUtils;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

/**
 * 
 * 代办事项服务类
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:DP-shaohongliang,date:2012-10-19 上午10:36:41, </p>
 * @author DP-shaohongliang
 * @date 2012-10-19 上午10:36:41
 * @since
 * @version
 */


/**
 * 
 * 代办事项服务类
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:DP-shaohongliang,date:2012-10-19 上午10:36:41, </p>
 * @author DP-shaohongliang
 * @date 2012-10-19 上午10:36:41
 * @since
 * @version
 */

/**
 * 
 * 代办事项服务类
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:DP-shaohongliang,date:2012-10-19 上午10:36:41, </p>
 * @author DP-shaohongliang
 * @date 2012-10-19 上午10:36:41
 * @since
 * @version
 */
public class TodoActionService implements ITodoActionService{
	private static final Logger LOGGER = LoggerFactory.getLogger(TodoActionService.class);

	/**
     * 待办事项
     */
	public static final String EIGHTZERO = "00000000";
    /**
     * 待办事项
     */
    private ITodoActionDao todoActionDao;
    
    private ITodoActionService todoActionService;
    /**
     * 得到标签打印的信息
     */
    private ILabelPrintInfoService labelPrintInfoService;
    
    /**
     * 运单管理接口
     */
    private IWaybillManagerService waybillManagerService;
    
    /**
	 * 营业部 Service接口
	 */
	private ISaleDepartmentService saleDepartmentService;
	
	/**
	 * 部门 复杂查询 service
	 */
	private IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService;

	private IWaybillRfcTodoJobService waybillRfcTodoJobService;

	/**
	 * 代办dao
	 */
	private IPendingTodoDao pendingTodoDao;
	
	/**
	 * 综合 组织信息 Service接口
	 */
	private IOrgAdministrativeInfoService orgAdministrativeInfoService;
	
	public void setPendingTodoDao(IPendingTodoDao pendingTodoDao) {
		this.pendingTodoDao = pendingTodoDao;
	}
	
	public void setWaybillRfcTodoJobService(
			IWaybillRfcTodoJobService waybillRfcTodoJobService) {
		this.waybillRfcTodoJobService = waybillRfcTodoJobService;
	}
	
	/**
	 * 待办相信信息Service
	 */
	private ILabeledGoodTodoDao labeledGoodTodoDao;
	
	/**
	 * 待办相信信息Service
	 */
	public void setLabeledGoodTodoDao(ILabeledGoodTodoDao labeledGoodTodoDao) {
		this.labeledGoodTodoDao = labeledGoodTodoDao;
	}
	
	/**
	 * 中专标签打印记录
	 */
	private IPrintLabelService printLabelService;
	
	private IWaybillRfcDao waybillRfcDao;
	
	public void setWaybillRfcDao(IWaybillRfcDao waybillRfcDao) {
		this.waybillRfcDao = waybillRfcDao;
	}
	
	/**
	 * 中专标签打印记录
	 */
	public void setPrintLabelService(IPrintLabelService printLabelService) {
		this.printLabelService = printLabelService;
	}
	
	//是否派送营业部
	private int INDEX_ORIGINAL = 0;
	private int INDEX_FINAL = 1;
	private int INDEX_ISDELIVERY = 2;

	public void setOrgAdministrativeInfoComplexService(
			IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService) {
		this.orgAdministrativeInfoComplexService = orgAdministrativeInfoComplexService;
	}
	
	/**
	 * @param saleDepartmentService the saleDepartmentService to set
	 */
	public void setSaleDepartmentService(
			ISaleDepartmentService saleDepartmentService) {
		this.saleDepartmentService = saleDepartmentService;
	}


	/**
	 * @param waybillManagerService the waybillManagerService to set
	 */
	public void setWaybillManagerService(
			IWaybillManagerService waybillManagerService) {
		this.waybillManagerService = waybillManagerService;
	}

    
	/**
	 * @param todoActionService the todoActionService to set
	 */
	public void setTodoActionService(ITodoActionService todoActionService) {
		this.todoActionService = todoActionService;
	}

	/**
	 * @param labelPrintInfoService the labelPrintInfoService to set
	 */
	public void setLabelPrintInfoService(
			ILabelPrintInfoService labelPrintInfoService) {
		this.labelPrintInfoService = labelPrintInfoService;
	}
	
	/**
	 * @param todoActionDao the todoActionDao to set
	 */
	public void setTodoActionDao(ITodoActionDao todoActionDao) {
		this.todoActionDao = todoActionDao;
	}

	/**
     * 
     * <p>生成指定运单号的待办事项</p> 
     * 1、找到运单对应的所有标签
     * @author DP-shaohongliang
     * @date 2012-10-19 上午10:39:16
     * @param waibillNo
     * @see
     */
    public void addTodoAction(String waibillNo){
	}

    /**
     * 获取当前登录部门，如果是派送中心，查询对应外场的待办
     * 返回 原始部门、转换后部门、是否派送营业部
     * @return
     */
    private List<String> getCurrentDeptCode() {
    	return getCurrentDeptCode(FossUserContext.getCurrentDeptCode());
    }
    
    /**
     * 获取当前登录部门，如果是派送中心，查询对应外场的待办
     * 返回 原始部门、转换后部门、是否派送营业部
     * @return
     */
    private List<String> getCurrentDeptCode(String currentDeptCode) {
    	//当前登陆部门Code集合
    	List<String> currentDeptCodeList = new ArrayList<String>();
    	String deptCode = currentDeptCode;
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
    	}else{
    		//非营业部找到它上级所属外场的编码
    		List<String> bizTypes = new ArrayList<String>();
    		bizTypes.add(BizTypeConstants.ORG_TRANSFER_CENTER);
    		OrgAdministrativeInfoEntity orgAdministrativeInfoEntity = orgAdministrativeInfoComplexService.queryOrgAdministrativeInfoIncludeSelfByCode(deptCode, bizTypes);
    		if(orgAdministrativeInfoEntity != null){
    			deptCode = orgAdministrativeInfoEntity.getCode();
    		}
    	}
    	//转换后部门
    	currentDeptCodeList.add(deptCode);
    	currentDeptCodeList.add(isStation?FossConstants.YES:FossConstants.NO);
    	
		return currentDeptCodeList;     
    }

    
    public void setOrgAdministrativeInfoService(
			IOrgAdministrativeInfoService orgAdministrativeInfoService) {
		this.orgAdministrativeInfoService = orgAdministrativeInfoService;
	}

	public void setINDEX_ORIGINAL(int iNDEX_ORIGINAL) {
		INDEX_ORIGINAL = iNDEX_ORIGINAL;
	}

	public void setINDEX_FINAL(int iNDEX_FINAL) {
		INDEX_FINAL = iNDEX_FINAL;
	}

	public void setINDEX_ISDELIVERY(int iNDEX_ISDELIVERY) {
		INDEX_ISDELIVERY = iNDEX_ISDELIVERY;
	}

	/**
     * 
     * <p>根据查询条件获取待办事项</p> 
     * @author 102246-foss-shaohongliang
     * @date 2012-10-23 上午11:09:03
     * @param todoConditionDto
     * @return 
     * @see com.deppon.foss.module.pickup.waybill.server.service.ITodoActionService#queryTodoActionsByCondition(com.deppon.foss.module.pickup.waybill.shared.dto.TodoConditionDto)
     */
    public List<TodoActionDto> queryTodoActionsByCondition(TodoConditionDto todoConditionDto, int start, int limit) {
    	todoConditionDto.setRfcStatus(WaybillRfcConstants.ACCECPT);//已同意
    	List<String> currentDeptCodeList = getCurrentDeptCode();
    	if(FossConstants.YES.equals(currentDeptCodeList.get(INDEX_ISDELIVERY))){
    		//派送部
    		todoConditionDto.setLastLoadOrgCode(currentDeptCodeList.get(INDEX_ORIGINAL));
    	}
    	todoConditionDto.setHandleOrgCode(currentDeptCodeList.get(INDEX_FINAL));
    	String deptCode = FossUserContext.getCurrentDeptCode();//获取当前部门
    	todoConditionDto.setCurrentDept(deptCode);//设置当前部门
    	/**
    	 * 在查询一次当前部门有没有待办
    	 */
    	List<TodoActionDto> todoActionDtoList = todoActionDao.queryTodoActionsByCondition(todoConditionDto, start, limit);//据查询条件获取待办事项
    	//合并两个受理部门相同的代办
//    	List<TodoActionDto> converterDtoList = converterTodoActionList(todoActionDtoList);
    	return todoActionDtoList;
    	
    }

    /**
     * 合并同一单号两个待办受理部门相同的代办
     * @deprecated
     * @author 105888-foss-zhangxingwang
     * @date 2013-9-23 8:33:44
     * @param todoActionDtoList
     * @return
     */
    private List<TodoActionDto> converterTodoActionList(
			List<TodoActionDto> todoActionDtoList) {
    	List<TodoActionDto> converterDtoList = new ArrayList<TodoActionDto>();
		if(CollectionUtils.isNotEmpty(todoActionDtoList)){
			//只有多余两个的待办数据才能进行循环比较，其他返回一个值即可
			if(todoActionDtoList.size()>1){
				for(int i=0; i<todoActionDtoList.size(); i++){
					//只有当单号和对应的待办受理部门一致时候才能进行合并
					for(int j=i+1;j<todoActionDtoList.size();j++){
						if(StringUtils.equals(todoActionDtoList.get(i).getWaybillNo(), todoActionDtoList.get(j).getWaybillNo())
								&& StringUtils.equals(todoActionDtoList.get(i).getHandleOrgCode(), todoActionDtoList.get(j).getHandleOrgCode())
								&& StringUtils.equals(todoActionDtoList.get(i).getWaybillRfcId(), todoActionDtoList.get(j).getWaybillRfcId())
								&& StringUtils.equals(todoActionDtoList.get(i).getStatus(), todoActionDtoList.get(j).getStatus())){
							todoActionDtoList.get(i).setWaybillNo(EIGHTZERO);
							//如果第一个交接单不为空，进行拼接
							if(StringUtils.isNotEmpty(todoActionDtoList.get(i).getHandlerOverNo())){
								if(StringUtils.isEmpty(todoActionDtoList.get(j).getHandlerOverNo())){
									todoActionDtoList.get(j).setHandlerOverNo(todoActionDtoList.get(i).getHandlerOverNo());
								}else{
									todoActionDtoList.get(j).setHandlerOverNo(
											todoActionDtoList.get(i).getHandlerOverNo()+"/"
													+todoActionDtoList.get(j).getHandlerOverNo());
								}
							}
							//如果第一个配载单不为空，进行拼接
							if(StringUtils.isNotEmpty(todoActionDtoList.get(i).getLoadNo())){
								if(StringUtils.isEmpty(todoActionDtoList.get(j).getLoadNo())){
									todoActionDtoList.get(j).setLoadNo(todoActionDtoList.get(i).getLoadNo());
								}else{
									todoActionDtoList.get(j).setLoadNo(
											todoActionDtoList.get(i).getLoadNo()+"/"
													+todoActionDtoList.get(j).getLoadNo());
								}
							}
						}
					}
				}
				//如果不为00000000的话
				for(int i=0; i<todoActionDtoList.size(); i++){
					if(!StringUtils.equals(todoActionDtoList.get(i).getWaybillNo(), EIGHTZERO)){
						converterDtoList.add(todoActionDtoList.get(i));
					}
				}
				//去除重复交接单、配载单号的情况
				return splitSameData(converterDtoList);
			}
		}
		return todoActionDtoList;
	}

    /**
     * 合并相同交接单号、配载单
     * @deprecated
     * @author 105888-foss-zhangxingwang
     * @date 2013-9-25 9:31:47
     * @param converterDtoList
     * @return
     */
	private List<TodoActionDto> splitSameData(
			List<TodoActionDto> converterDtoList) {
		for(int i=0;i<converterDtoList.size();i++){
			//合并交接单
			if(StringUtils.isNotEmpty(converterDtoList.get(i).getHandlerOverNo())){
				String handoverNos = splitSameHandNoAndLoadNo(converterDtoList.get(i).getHandlerOverNo());
				converterDtoList.get(i).setHandlerOverNo(handoverNos);
			}
			//合并配载单
			if(StringUtils.isNotEmpty(converterDtoList.get(i).getLoadNo())){
				String loadNos = splitSameHandNoAndLoadNo(converterDtoList.get(i).getLoadNo());
				converterDtoList.get(i).setLoadNo(loadNos);
			}
		}
		return converterDtoList;
	}
	/**
	 * 转换数据
	 * @deprecated
	 * @author 105888-foss-zhangxingwang
	 * @date 2013-9-25 10:00:23
	 * @param simpleData
	 * @return
	 */
	private String splitSameHandNoAndLoadNo(String simpleData) {
		if(StringUtils.isEmpty(simpleData)){
			return null;
		}
		String[] data = simpleData.split("/");
		for(int i=0;i<data.length;i++){
			for(int j=i;j<data.length;j++){
				if(StringUtils.equals(data[i], data[j])){
					data[i]=EIGHTZERO;
				}
			}			
		}
		StringBuilder sb= new StringBuilder();
		for(int i=0;i<data.length;i++){
			if(!StringUtils.equals(EIGHTZERO, data[i])){
				sb.append(data[i]+"/");
			}
		}
//		String result = sb.toString().substring(0, sb.length()-1);
		return sb.toString();
	}

	public List<TodoActionDto> queryTodoActionsByConditionAll(TodoConditionDto todoConditionDto) {
    	todoConditionDto.setRfcStatus(WaybillRfcConstants.ACCECPT);//已同意
    	List<String> currentDeptCodeList = getCurrentDeptCode();
    	if(FossConstants.YES.equals(currentDeptCodeList.get(INDEX_ISDELIVERY))){
    		//派送部
    		todoConditionDto.setLastLoadOrgCode(currentDeptCodeList.get(INDEX_ORIGINAL));
    	}
    	todoConditionDto.setHandleOrgCode(currentDeptCodeList.get(INDEX_FINAL));
    	String deptCode = FossUserContext.getCurrentDeptCode();//获取当前部门
    	todoConditionDto.setCurrentDept(deptCode);//设置当前部门
    	/**
    	 * 在查询一次当前部门有没有待办
    	 */
    	List<TodoActionDto> todoActionDtoList = todoActionDao.queryTodoActionsByCondition(todoConditionDto, 0, 0);//据查询条件获取待办事项
    	return todoActionDtoList;
    	
    }

	/**
     * 
     * <p>查询待办事项详情</p> 
     * @author 102246-foss-shaohongliang
     * @date 2012-10-23 上午10:59:32
     * @param queryVo
     * @return
     * @see
     */
    public LabeledGoodTodoDto queryTodoAction(String waybillRfcId, String currentDeptCode) {
    	List<String> currentDeptCodeList = getCurrentDeptCode(currentDeptCode);
		String deptCode = currentDeptCodeList.get(INDEX_FINAL);
    	String handleOrgCode = null;
    	if(FossConstants.YES.equals(currentDeptCodeList.get(INDEX_ISDELIVERY))){
    		//派送部
    		handleOrgCode= currentDeptCodeList.get(INDEX_ORIGINAL);
    	}
		LabeledGoodTodoDto labeledGoodTodoDto = todoActionDao.queryTodoAction(waybillRfcId, deptCode, currentDeptCode, handleOrgCode);//查询待办事项详情
		
		
		labeledGoodTodoDto.setWaybillRfcId(waybillRfcId);
    	return labeledGoodTodoDto;
    }

    /**
     * 
     * <p>获得待办事项总条数</p> 
     * @author 102246-foss-shaohongliang
     * @date 2012-10-24 下午2:40:49
     * @param todoConditionDto
     * @return
     * @see
     */
	public Long queryGoodsInfoCount(TodoConditionDto todoConditionDto) {
		todoConditionDto.setRfcStatus(WaybillRfcConstants.ACCECPT);//已同意
		List<String> currentDeptCodeList = getCurrentDeptCode();
    	if(FossConstants.YES.equals(currentDeptCodeList.get(INDEX_ISDELIVERY))){
    		//派送部
    		todoConditionDto.setLastLoadOrgCode(currentDeptCodeList.get(INDEX_ORIGINAL));
    	}
    	todoConditionDto.setHandleOrgCode(currentDeptCodeList.get(INDEX_FINAL));
    	String deptCode = FossUserContext.getCurrentDeptCode();//获取当前部门
    	todoConditionDto.setCurrentDept(deptCode);//设置当前部门
		Long count = todoActionDao.queryGoodsInfoCount(todoConditionDto);//获得待办事项总条数
    	return count;
	}

	 /**
     * 
     * 更新标签状态并返回标签打印的信息
     * @author 102246-foss-shaohongliang
     * @date 2012-12-26 上午9:59:07
     */
	public List<BarcodePrintLabelDto> getLabelPrintInfo(String waybillNo,
			List<LabeledGoodTodoEntity> labeledGoodTodoEntitys,String waybillRfcId) {
			
			//判断是否为空
			if(StringUtils.isEmpty(waybillNo)){
				throw new WaybillRfcChangeException("运单号不存在，请重试");
			}
			//waybillRfcId
			if(CollectionUtils.isEmpty(labeledGoodTodoEntitys)){
				throw new WaybillRfcChangeException("货签不存在，请重试");
			}
			//waybillRfcId
			if(StringUtils.isEmpty(waybillRfcId)){
				throw new WaybillRfcChangeException("更改单信息不存在，请重试");
			}
			List<String> serialNos = new ArrayList<String>();
			for(LabeledGoodTodoEntity todoEntity : labeledGoodTodoEntitys){
				serialNos.add(todoEntity.getSerialNo());
			}
			
			List<BarcodePrintLabelDto> barcodePrintLabelDtos = labelPrintInfoService.getLabelPrintInfoForDepart(waybillNo, serialNos);
    		
			
    		todoActionService.updateLabeledPringStatus(waybillNo, labeledGoodTodoEntitys, waybillRfcId);
			
			return barcodePrintLabelDtos;
		
	}
	
	@Transactional
	public List<String> updateLabeledPringStatus(String waybillNo,
			List<LabeledGoodTodoEntity> labeledGoodTodoEntitys,String waybillRfcId){
		//List<String> currentDeptCodeList = getCurrentDeptCode();
		//String currentOrgCode = currentDeptCodeList.get(INDEX_FINAL);
		//判断是否为空
		if(StringUtils.isEmpty(waybillNo)){
			throw new WaybillRfcChangeException("运单号不存在，请重试");
		}
		//waybillRfcId
		if(CollectionUtils.isEmpty(labeledGoodTodoEntitys)){
			throw new WaybillRfcChangeException("货签不存在，请重试");
		}
		//waybillRfcId
		if(StringUtils.isEmpty(waybillRfcId)){
			throw new WaybillRfcChangeException("更改单信息不存在，请重试");
		}
		List<String> serialNos = new ArrayList<String>();
		PrintLabelEntity printLabelEntity = new PrintLabelEntity();
		printLabelEntity.setPrintTime(new Date());
		printLabelEntity.setPrintUserCode(FossUserContext.getCurrentInfo().getEmpCode());
		printLabelEntity.setPrintUserName(FossUserContext.getCurrentInfo().getEmpName());
		printLabelEntity.setWaybillNo(waybillNo);
		List<PrintLabelEntity> printLabelList = new ArrayList<PrintLabelEntity>();
		//
		List<String> labelGoodsIdList = new ArrayList<String>();
		for(LabeledGoodTodoEntity todoEntity : labeledGoodTodoEntitys){	
			printLabelEntity.setSerialNo(todoEntity.getSerialNo());
			printLabelEntity.setId(UUIDUtils.getUUID());
			printLabelList.add(printLabelEntity);
			labelGoodsIdList.add(todoEntity.getLabeledGoodId());
		}
		//根据流水号ID和对应的打印状态，代办生成成状态更新所有该代办的流水号的打印信息
		if(labelGoodsIdList.size()==0){
			throw new WaybillRfcChangeException("货签不存在，请重试");
		}
		//查询所有更改单信息
		List<WaybillRfcEntity> rfcList = waybillRfcDao.queryWaybillRfcAcceptByWaybillNo(waybillNo, WaybillRfcConstants.ACCECPT);
		//做优化，如果该单只发过一次受理成功的更改，则只需执行更新就行，无需多次更新
		if(CollectionUtils.isEmpty(rfcList)){
			todoActionDao.updateLabeledPringStatusBatch(labeledGoodTodoEntitys);
		}else{//发过一次更改，则只需执行这个就行了
			if(rfcList.size() == 1){
				todoActionDao.updateLabeledPringStatusBatch(labeledGoodTodoEntitys);
			}else{//发过多次更改，只能先批量执行成系统自动受理，再把这条记录改回来
				todoActionDao.updateAllNotPrintLabeledBatch(labelGoodsIdList,FossConstants.YES,FossConstants.NO);
				todoActionDao.updateLabeledPringStatusBatch(labeledGoodTodoEntitys);
			}
		}
		//新增打印记录信息
		for(LabeledGoodTodoEntity todoEntity : labeledGoodTodoEntitys){			
			printLabelEntity.setSerialNo(todoEntity.getSerialNo());
			printLabelEntity.setId(UUIDUtils.getUUID());
			printLabelService.addPrintLabel(printLabelEntity);
			serialNos.add(todoEntity.getSerialNo());
		}		
		return serialNos;
	}
	
	
	
	
	

	@Override
	public void updateLabeledHandleStatusAndTime(String waybillRfcId, String status) {
		List<String> currentDeptCodeList = getCurrentDeptCode();
		String currentOrgCode = currentDeptCodeList.get(INDEX_FINAL);
		todoActionDao.updateLabeledHandleStatusAndTime(waybillRfcId, status, currentOrgCode);
	}

	/**
	 * 如果是偏线、空运，serials为空
	 */
	@Override
	public void updateLabeledStatusByWaybillNum(String waybillNo,
			List<String> serials) {
		if(CollectionUtils.isNotEmpty(serials))
		{
			int totalLen =serials.size();
			int len = NumberConstants.NUMBER_500;
			int processedLen = 0;
			while(processedLen<totalLen){
				List<String> curSerList = serials.subList(processedLen, processedLen+len>totalLen?totalLen:processedLen+len);
				todoActionDao.updateLabeledStatusByWaybillNum(waybillNo, curSerList);
				processedLen += len;
			}	
		}else
		{
			todoActionDao.updateLabeledStatusByWaybillNum(waybillNo, serials);
		}
		
	}

	/**
	 * 
	 * 导出
	 * @param todoConditionDto
	 * @return
	 * @author ibm-wangfei
	 * @date Jul 16, 2013 3:41:25 PM
	 * @see com.deppon.foss.module.pickup.waybill.api.server.service.ITodoActionService#exportTodoActionInfo(com.deppon.foss.module.pickup.waybill.shared.dto.TodoConditionDto)
	 */
	@Override
	public InputStream exportTodoActionInfo(TodoConditionDto todoConditionDto) {
		todoConditionDto.setRfcStatus(WaybillRfcConstants.ACCECPT);//已同意
    	List<String> currentDeptCodeList = getCurrentDeptCode();
    	if(FossConstants.YES.equals(currentDeptCodeList.get(INDEX_ISDELIVERY))){
    		//派送部
    		todoConditionDto.setLastLoadOrgCode(currentDeptCodeList.get(INDEX_ORIGINAL));
    	}
    	todoConditionDto.setHandleOrgCode(currentDeptCodeList.get(INDEX_FINAL));
    	String deptCode = FossUserContext.getCurrentDeptCode();//获取当前部门
    	todoConditionDto.setCurrentDept(deptCode);//设置当前部门
    	/**
    	 * 在查询一次当前部门有没有待办
    	 */
    	List<TodoActionDto> dtos = todoActionDao.queryTodoActionsByConditionAll(todoConditionDto);//据查询条件获取待办事项
    	
		if (CollectionUtils.isEmpty(dtos)) {
			return null;
		}
		List<List<String>> rowList = new ArrayList<List<String>>();
		for (TodoActionDto dto : dtos) {
			List<String> columnList = new ArrayList<String>();
			// 运单号
			columnList.add(dto.getWaybillNo());
			// 出发站
			columnList.add(dto.getStartOrgName());
			// 目的站
			columnList.add(dto.getTargetOrgName());
			// 重量
			columnList.add(dto.getWeight() + "");
			// 体积
			columnList.add(dto.getVolume() + "");
			// 件数
			columnList.add(dto.getGoodsQtyTotal() + "");
			// 包装
			columnList.add(dto.getGoodsPackage() + "");
			//其他包装
			columnList.add(dto.getOtherPackage() + "");
			// 变更申请部门
			columnList.add(dto.getDarftOrgName());
			// 变更受理部门
			columnList.add("".equals(dto.getOperateOrgName())? "系统自动受理":dto.getOperateOrgName());
			// 变更申请时间
			columnList.add(DateUtils.convert(dto.getOperateTime(), DateUtils.DATE_TIME_FORMAT));
			//是否目的站变更
			if(FossConstants.YES.equals(dto.getIsDestiChage())){
				columnList.add("是");
			}else{
				columnList.add("否");
			}
			// 更改内容
			columnList.add(dto.getRfcInfo());
			//变更申请人
			columnList.add(dto.getDarfter());
			rowList.add(columnList);
		}
		List<String> rowHeads = new ArrayList<String>();
		// 运单号
		rowHeads.add("运单号");
		// 出发站
		rowHeads.add("出发站");
		// 目的站
		rowHeads.add("目的站");
		//重量
		rowHeads.add("重量");
		//体积
		rowHeads.add("体积");
		//件数
		rowHeads.add("件数");
		// 包装
		rowHeads.add("包装");
		rowHeads.add("其他包装");
		//变更申请部门
		rowHeads.add("变更申请部门");
		//变更申请部门
		rowHeads.add("变更受理部门");
		//变更申请时间
		rowHeads.add("变更受理时间");
		rowHeads.add("更改目的站");
		//更改内容
		rowHeads.add("更改内容");		
		//变更申请人
		rowHeads.add("变更申请人");
		ExportResource exportResource = new ExportResource();
		exportResource.setHeads(rowHeads.toArray(new String[rowHeads.size()]));
		exportResource.setRowList(rowList);
		ExportSetting exportSetting = new ExportSetting();
		exportSetting.setSheetName("待办事项");
		exportSetting.setSize(NotifyCustomerConstants.EXPORT_NUMBER);
		ExporterExecutor objExporterExecutor = new ExporterExecutor();
		return objExporterExecutor.exportSync(exportResource, exportSetting);
	}
	
	/**
	 * @功能 查询所有代办异常数据
	 * @author 105888-foss-zhangxingwang
	 * @date 2013-7-29 17:03:56
	 */
	
	@Override
	public Long queryExceptMsgInfoCount(
			ExceptMsgConditionDto exceptMsgConditionDto) {
		if(exceptMsgConditionDto != null){
			exceptMsgConditionDto.setRfcStatus(WaybillRfcConstants.ACCECPT);//已同意
			//如果运单号集合不为空的话
	    	if(StringUtils.isNotBlank(exceptMsgConditionDto.getWaybillNo())){
	    		exceptMsgConditionDto.setWaybillNoList(Arrays.asList(exceptMsgConditionDto.getWaybillNo().split("\\n")));
	    	}
			if(StringUtils.isNotEmpty(exceptMsgConditionDto.getInStockOrgCode())){
	    		OrgAdministrativeInfoEntity entity = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCodeNoCache(exceptMsgConditionDto.getInStockOrgCode());
	    		if(entity != null){
	    			exceptMsgConditionDto.setInStockOrgName(entity.getName());
	    		}else{
	    			exceptMsgConditionDto.setInStockOrgCode("");
	    		}
	    	}
	    	return todoActionDao.queryExceptMsgInfoCount(exceptMsgConditionDto);
		}
		return null;
	}
	
	/**
	 * @功能 查询所有代办异常数据
	 * @author 105888-foss-zhangxingwang
	 * @date 2013-7-29 17:03:56
	 */
	@Override
	public List<ExceptMsgActionDto> queryExceptMsgActionDto(
			ExceptMsgConditionDto exceptMsgConditionDto, int start, int limit) {
		exceptMsgConditionDto.setRfcStatus(WaybillRfcConstants.ACCECPT);//已同意
    	if(StringUtils.isNotEmpty(exceptMsgConditionDto.getInStockOrgCode())){
    		OrgAdministrativeInfoEntity entity = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCodeNoCache(exceptMsgConditionDto.getInStockOrgCode());
    		if(entity != null){
    			exceptMsgConditionDto.setInStockOrgName(entity.getName());
    		}else{
    			exceptMsgConditionDto.setInStockOrgCode("");
    		}
    	}
    	//如果运单号集合不为空的话
    	if(StringUtils.isNotBlank(exceptMsgConditionDto.getWaybillNo())){
    		exceptMsgConditionDto.setWaybillNoList(Arrays.asList(exceptMsgConditionDto.getWaybillNo().split("\\n")));
    	}
    	return todoActionDao.queryTodoActionsByCondition(exceptMsgConditionDto, start, limit);//据查询条件获取待办事项;
	}	
	
	/**
	 * 查询所有未生成代办的数量
	 */
	@Override
	public Long queryPendTodoInfoCount(
			PendingMsgConditionDto pendingMsgConditionDto) {
		pendingMsgConditionDto.setRfcStatus(WaybillRfcConstants.ACCECPT);
		//如果运单号集合不为空的话
		if(StringUtils.isNotBlank(pendingMsgConditionDto.getWaybillNo())){
			pendingMsgConditionDto.setWaybillNoList(Arrays.asList(pendingMsgConditionDto.getWaybillNo().split("\\n")));
		}
		return todoActionDao.queryPendTodoActionDtoCount(pendingMsgConditionDto);
	}
	
	/**
	 * 查询所有未生成代办的详细信息
	 */
	@Override
	public List<PendingMsgActionDto> queryPendTodoActionDto(
			PendingMsgConditionDto pendingMsgConditionDto, int start, int limit) {
		pendingMsgConditionDto.setRfcStatus(WaybillRfcConstants.ACCECPT);
		//如果运单号集合不为空的话
		if(StringUtils.isNotBlank(pendingMsgConditionDto.getWaybillNo())){
			pendingMsgConditionDto.setWaybillNoList(Arrays.asList(pendingMsgConditionDto.getWaybillNo().split("\\n")));
		}
		return todoActionDao.queryPendTodoActionDto(pendingMsgConditionDto,start,limit);
	}
	
	@Override
	public List<LabelGoodTodoDto> queryLabelGoodTodo(String waybillRfcId){
		return todoActionDao.queryLabelGoodTodo(waybillRfcId);
		
	}
	
	@Override
	@Transactional
	public void updateLabelTodoAndNewPathDetail(String waybillRfcId){
		if(StringUtils.isEmpty(waybillRfcId)){
			throw new WaybillRfcChangeException("参数为空，请重试");
		}
		List<String> waybillRfcIdList = new ArrayList<String>();
		waybillRfcIdList.add(waybillRfcId);
		List<LabeledGoodTodoEntity> list = labeledGoodTodoDao.queryLabelTodoExceptMsgStatus(waybillRfcIdList ,FossConstants.NO);
		for(LabeledGoodTodoEntity entity : list){
			waybillRfcTodoJobService.handleSingleTodo(entity);
		}		
	}
	
	@Override
	public void updatePendTodoFailReason(List<String> pendTodoIdList){
		try{
			if(CollectionUtils.isNotEmpty(pendTodoIdList)){
				List<PendingTodoEntity> pendList = pendingTodoDao.queryPendingTodoByIds(pendTodoIdList);
				if(CollectionUtils.isNotEmpty(pendList)){
					for(int i=0;i<pendList.size();i++){
						waybillRfcTodoJobService.sendSingleTodo(pendList.get(i));
					}
				}
			}
		}catch (Exception e) {
			// 啥都不干，不然前台会报错，以为存在问题
		}
	}

	@Override
	public Long queryGoodsInfoWithHandleOrgCount(
			TodoConditionDto todoConditionDto) {
		todoConditionDto.setRfcStatus(WaybillRfcConstants.ACCECPT);//已同意
		return todoActionDao.queryGoodsInfoWithHandleOrgCount(todoConditionDto);
	}

	@Override
	public List<TodoActionDto> queryTodoActionsByConditionWithHandleOrg(
			TodoConditionDto todoConditionDto, int start, int limit) {
		todoConditionDto.setRfcStatus(WaybillRfcConstants.ACCECPT);//已同意
		return todoActionDao.queryTodoActionsByConditionWithHandleOrg(todoConditionDto,start,limit);
	}

	@Override
	public void updateExceptMsgBatch(List<String> waybillRfcIdList) {
		try{
			if(CollectionUtils.isEmpty(waybillRfcIdList)){
				throw new WaybillRfcChangeException("参数为空，请重试");
			}
			List<LabeledGoodTodoEntity> list = labeledGoodTodoDao.queryLabelTodoExceptMsgStatus(waybillRfcIdList,FossConstants.NO);
			for(LabeledGoodTodoEntity entity : list){
				waybillRfcTodoJobService.handleSingleTodo(entity);
			}
		}catch (Exception e) {
			// 啥都不干，不然前台会报错，以为存在问题
		}
	}

	@Override
	public void updateExceptMsgBatchStatus(List<String> waybillRfcIdList) {
		try{
			if(CollectionUtils.isEmpty(waybillRfcIdList)){
				throw new WaybillRfcChangeException("参数为空，请重试");
			}
			todoActionDao.updateExceptMsgBatchStatus(waybillRfcIdList);
		}catch (Exception e) {
			// 啥都不干，不然前台会报错，以为存在问题
		}
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<LatestHandOverDto> queryLatestHandoverDto(String waybillRfcId, String waybillNo){
		if(StringUtils.isEmpty(waybillRfcId)){
			return null;
		}
		TodoConditionDto todoConditionDto = new TodoConditionDto();
    	List<String> currentDeptCodeList = getCurrentDeptCode();
    	if(FossConstants.YES.equals(currentDeptCodeList.get(INDEX_ISDELIVERY))){
    		//派送部
    		todoConditionDto.setLastLoadOrgCode(currentDeptCodeList.get(INDEX_ORIGINAL));
    	}
    	todoConditionDto.setHandleOrgCode(currentDeptCodeList.get(INDEX_FINAL));
    	String deptCode = FossUserContext.getCurrentDeptCode();//获取当前部门
    	todoConditionDto.setCurrentDept(deptCode);//设置当前部门
    	todoConditionDto.setWaybillRfcId(waybillRfcId);
    	WaybillRfcEntity waybillRfcEntity = waybillRfcDao.selectByPrimaryKey(waybillRfcId);
    	if(waybillRfcEntity != null){
    		//如果修改单号，还需要查询旧的单号，因为这样会出现查询不到的现象
    		if(StringUtils.equals(FossConstants.YES, waybillRfcEntity.getIsChangeWaybillNo())){
    			WaybillEntity waybillentity = waybillManagerService.queryWaybillById(waybillRfcEntity.getNewVersionWaybillId());
    			if(waybillentity == null){
    				throw new WaybillRfcChangeException("运单实体信息为空，请重试");
    			}
    			todoConditionDto.setWaybillNo(waybillentity.getWaybillNo());
    		}else{//直接使用运单号
    			todoConditionDto.setWaybillNo(waybillRfcEntity.getWaybillNo());
    		}
    	}else{
    		throw new WaybillRfcChangeException("更改单实体信息为空，请重试");
    	}
    	List<LatestHandOverDto> list = todoActionDao.queryLatestHandoverDto(todoConditionDto);
    	//为空，返回null
    	if(list== null){
    		return null;
    	}
    	//为1不需判断
    	if(list.size()==1){
    		return list;
    	}
    	Collections.sort(list, new MyComparator());
    	return list;
	}
	
	/**
	 * 提供给中转使用：当车辆做到达时，更新在途中的待办为可见的状态
	 * @author Foss-105888-Zhangxingwang
	 * @date 2014-4-15 21:03:05
	 * @param handoverbillNo
	 * @param operatOrgCode
	 * @return
	 */
	@Transactional
	@Override
	public ResultDto updateTodoByHandoverBillNo(TodoActionDto todoActionDto) {
		ResultDto resultDto = new ResultDto();
		if(CollectionUtils.isEmpty(todoActionDto.getHandoverBillNoList())){
			throw new BusinessException("传入的交接单为空,请重试");
		}
		//转换组织至对应的顶级组织
		revertToTheTopOrg(todoActionDto);
		//根据交接明细与流水号进行对比查询出所有流水号ID
		List<String> serialList = todoActionDao.selectMaxCountHandoverSerialNo(todoActionDto.getHandoverBillNoList());
		//在生产上经过查询，一个交接单对应的总流水号件数大于1000，需要进行批量更新
		if(serialList != null && serialList.size() > 0){
			Map<Object, Object> maps = new HashMap<Object, Object>();
			//操作组织编码
			maps.put("handleOrgCode", todoActionDto.getHandleOrgCode());
			//操作部门名称
			maps.put("handleOrgName", todoActionDto.getHandleOrgName());
			//对应的待办可见时间
			maps.put("remindTime", new Date());
			if (serialList != null && serialList.size() >= 0) {
				if (serialList.size() < FossConstants.ORACLE_MAX_IN_SIZE) {
					maps.put("serilalIdList", serialList);
					todoActionDao.updateTodoByHandoverBillNo(maps);
				} else {
					List<List<String>> idsLists = com.deppon.foss.util.CollectionUtils.splitListBySize(serialList, FossConstants.ORACLE_MAX_IN_SIZE);
					for (List<String> lists : idsLists) {
						maps.put("serilalIdList", lists);
						todoActionDao.updateTodoByHandoverBillNo(maps);
					}
				}
				LOGGER.info("一共处理了" + serialList.size() + "条数据");
				resultDto.setCode(ResultDto.SUCCESS);
				resultDto.setMsg("一共处理了" + serialList.size() + "条数据");
			}else{
				LOGGER.info("一共处理了" + serialList.size() + "条数据");
				resultDto.setCode(ResultDto.SUCCESS);
				resultDto.setMsg("一共处理了0条数据");
			}
		}
		return resultDto;
	}

	/**
	 * 将当前组织切换到顶级组织
	 * @author Foss-105888-Zhangxingwang
	 * @date 2014-6-25 09:21:52
	 * @param todoActionDto
	 */
	private void revertToTheTopOrg(TodoActionDto todoActionDto) {
		String currentOrgCode = FossUserContext.getCurrentDeptCode();
		SaleDepartmentEntity saleDepartmentEntity = saleDepartmentService.querySaleDepartmentByCode(currentOrgCode);
    	if(saleDepartmentEntity != null){
    		//如果是派送中心，查询对应外场的待办
    		if(FossConstants.YES.equals(saleDepartmentEntity.getStation())){
    			String transferCenter = saleDepartmentEntity.getTransferCenter();
    			if(StringUtil.isNotEmpty(transferCenter)){
    				OrgAdministrativeInfoEntity entity = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCodeClean(transferCenter);
    				todoActionDto.setHandleOrgCode(entity.getCode());
        			todoActionDto.setHandleOrgName(entity.getName());
    			}
    		}else{
    			todoActionDto.setHandleOrgCode(saleDepartmentEntity.getCode());
    			todoActionDto.setHandleOrgName(saleDepartmentEntity.getName());
    		}
    	}else{
    		//非营业部找到它上级所属外场的编码
    		List<String> bizTypes = new ArrayList<String>();
    		bizTypes.add(BizTypeConstants.ORG_TRANSFER_CENTER);
    		OrgAdministrativeInfoEntity orgAdministrativeInfoEntity = orgAdministrativeInfoComplexService.queryOrgAdministrativeInfoIncludeSelfByCode(currentOrgCode, bizTypes);
    		if(orgAdministrativeInfoEntity != null){
    			todoActionDto.setHandleOrgCode(orgAdministrativeInfoEntity.getCode());
    			todoActionDto.setHandleOrgName(orgAdministrativeInfoEntity.getName());
    		}
    	}
	}
	
	/**
	 * DMANA-9463 整车运单未签收提示功能
	 * @author 218438-foss-zhulei
	 */
	@Override
	public List<PickupToDoMsgDto> queryWaybillNotSignDataList(PickupToDoMsgDto toDoMsgDto) {
		List<Integer> days = new ArrayList<Integer>();
		days.add(NumberConstants.NUMBER_7);
		days.add(NumberConstants.NUMBER_14);
		days.add(NumberConstants.NUMBER_21);
		return todoActionDao.queryWaybillNotSignDataList(toDoMsgDto,days);
	}
}

@SuppressWarnings("rawtypes")
class MyComparator implements Comparator {
	public int compare(Object o1, Object o2) {
		LatestHandOverDto p1 = (LatestHandOverDto) o1;
		LatestHandOverDto p2 = (LatestHandOverDto) o2;
		if (p1.getSerialNo().compareTo(p2.getSerialNo())<0) {
			return -1;
		} else {
			return 1;
		}
	}
}