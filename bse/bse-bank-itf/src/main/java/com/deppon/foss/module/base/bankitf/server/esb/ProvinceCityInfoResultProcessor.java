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
 * PROJECT NAME	: bse-bank-itf
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/bankitf/server/esb/ProvinceCityInfoResultProcessor.java
 * 
 * FILE NAME        	: ProvinceCityInfoResultProcessor.java
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
package com.deppon.foss.module.base.bankitf.server.esb;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.esb.core.exception.ESBBusinessException;
import com.deppon.esb.core.process.IProcess;
import com.deppon.esb.inteface.domain.payment.ProvinceCityInfo;
import com.deppon.esb.inteface.domain.payment.ProvinceCityInfoNotificationRequest;
import com.deppon.esb.inteface.domain.payment.ProvinceCityInfoNotificationResponse;
import com.deppon.esb.inteface.domain.payment.ProvinceCityInfoProcessResult;
import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.server.service.IProvinceCityInfoService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.ProvinceCityEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.ProvinceCityInfoException;
import com.deppon.foss.module.base.common.api.server.service.IBusinessLockService;
import com.deppon.foss.module.base.common.api.shared.define.MutexElementType;
import com.deppon.foss.module.base.common.api.shared.dto.MutexElement;

/**
 * 同步银行省市信息省市信息
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:094463-foss-xieyantao,date:2012-11-19 下午7:43:17 </p>
 * @author 094463-foss-xieyantao
 * @date 2012-11-19 下午7:43:17
 * @since
 * @version
 */
public class ProvinceCityInfoResultProcessor implements IProcess {
    
    /**
     * 日志.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(ProvinceCityInfoResultProcessor.class);
    
    /**
     * 银行省市信息Service接口.
     */
    private IProvinceCityInfoService provinceCityInfoService;
    
    /**
     * 业务互斥锁服务.
     */
    private IBusinessLockService businessLockService;
    
    /**
     * 设置 银行省市信息Service接口.
     *
     * @param provinceCityInfoService the provinceCityInfoService to set
     */
    public void setProvinceCityInfoService(
    	IProvinceCityInfoService provinceCityInfoService) {
        this.provinceCityInfoService = provinceCityInfoService;
    }
    
    /**
     * 设置 业务互斥锁服务.
     *
     * @param businessLockService the businessLockService to set
     */
    public void setBusinessLockService(IBusinessLockService businessLockService) {
        this.businessLockService = businessLockService;
    }

    /**
     * <p>异常处理</p>.
     *
     * @param req 
     * @return 
     * @throws ESBBusinessException 
     * @author 094463-foss-xieyantao
     * @date 2012-11-19 下午7:43:17
     * @see com.deppon.esb.core.process.IProcess#errorHandler(java.lang.Object)
     */
    @Override
    public Object errorHandler(Object req) throws ESBBusinessException {
	LOGGER.error("同步银企系统银行省市信息异常："+ req);
	return null;
    }

    /**
     * <p>接收银企系统同步过来的银行省市信息</p>.
     *
     * @param req 
     * @return 
     * @throws ESBBusinessException 
     * @author 094463-foss-xieyantao
     * @date 2012-11-19 下午7:43:17
     * @see com.deppon.esb.core.process.IProcess#process(java.lang.Object)
     */
    @Override
    public Object process(Object req) throws ESBBusinessException {
	LOGGER.info("同步银行省市信息开始");
	//接收对象
	ProvinceCityInfoNotificationRequest request = (ProvinceCityInfoNotificationRequest)req;
	//响应对象
	ProvinceCityInfoNotificationResponse response = new ProvinceCityInfoNotificationResponse();
	//定义一个操作用户
	String userCode = "YINQI";
	
	if(request != null){
	   /* //同步信息处理结果集合
	    List<ProvinceCityInfoProcessResult> processResulttList = new ArrayList<ProvinceCityInfoProcessResult>();
	    //同步成功总数
	    int successCount = 0;
	    //同步失败总数
	    int failedCount = 0;*/
	    //获取同步过来的数据
	    List<ProvinceCityInfo> list = request.getProvinceCityInfo();
	    
	    if(CollectionUtils.isNotEmpty(list)){
	    	this.getProcessResult(list,userCode,response);
		/*for(ProvinceCityInfo provinceCityInfo : list){
		    //定义银行省市信息信息实体
		    ProvinceCityEntity entity = new ProvinceCityEntity();
		    if(StringUtil.isNotBlank(provinceCityInfo.getProvinceCityId())){
			//行政区域编码
			entity.setDistrictCode(provinceCityInfo.getProvinceCityId().trim());
		    }else {
			//行政区域编码
			entity.setDistrictCode(provinceCityInfo.getProvinceCityId());
		    }
		    
		    //区域全称
		    entity.setDistrictName(provinceCityInfo.getProvinceCityName());
		    if(StringUtil.isNotBlank(provinceCityInfo.getProvenceId())){
			//上级行政区域编码
			entity.setParentDistrictCode(provinceCityInfo.getProvenceId().trim());
		    }else {
			//上级行政区域编码
			entity.setParentDistrictCode(provinceCityInfo.getProvenceId());
		    }
		    
		    //行政区域级别 1-省 2-市
		    entity.setDistrictDegree(provinceCityInfo.getProvinceOrCity());
		    
		    //定义一个同步处理结果对象
		    ProvinceCityInfoProcessResult processResult = new ProvinceCityInfoProcessResult();
		    //银行省市信息编码
		    processResult.setProvinceCityId(provinceCityInfo.getProvinceCityId());
		    processResult.setOperateCode(provinceCityInfo.getOperateCode());
		 // 业务锁
			MutexElement mutex = new MutexElement(provinceCityInfo.getProvinceCityId(), "PROVINCECITY_ID",
				    MutexElementType.PROVINCECITY_ID);
			LOGGER.info("开始加锁：" + mutex.getBusinessNo());
			boolean result = businessLockService.lock(mutex, NumberConstants.ZERO);
			
			if (result) {
			    LOGGER.info("成功加锁：" + mutex.getBusinessNo());
			    entity.setCreateUser(userCode);
			    entity.setModifyUser(userCode);
			  
		    if(StringUtil.equals(NumberConstants.NUMERAL_S_ONE, provinceCityInfo.getOperateCode())){//新增信息
			try {
				int addResult = 0;
			    
			    addResult = provinceCityInfoService.addProvinceCity(entity);
    			    if(addResult > 0){//保存成功
    				processResult.setResult(true);
    				processResult.setReason("FOSS:success");
    				//成功数
    				successCount++;
    			    }else {
    			        //保存失败
    				processResult.setResult(false);
    				processResult.setReason("FOSS: failed");
    				//失败数
    				failedCount++;
			    }
			} catch (ProvinceCityInfoException e) {
			    LOGGER.error(e.getMessage(), e);
			    //记录异常原因
			    processResult.setReason("FOSS:"+e.getMessage());
			    processResult.setResult(false);
			    //失败数
			    failedCount++;
			}
			
		    }
		    if(StringUtil.equals(NumberConstants.NUMERAL_S_TWO, provinceCityInfo.getOperateCode())){//修改信息
			try {
				
			    //执行修改操作
			    int updateResult = provinceCityInfoService.updateProvinceCity(entity);
			    if(updateResult > 0){
				//修改成功
				processResult.setResult(true);
				processResult.setReason("FOSS: success");
    				//成功数
    				successCount++;
			    }else {
				 processResult.setResult(false);
				 processResult.setReason("FOSS: failed");
				 //失败数
				 failedCount++;
			    }
			    
			} catch (ProvinceCityInfoException e) {
			    LOGGER.error(e.getMessage(), e);
			    //保存失败原因
			    processResult.setReason("FOSS:"+e.getMessage());
			    processResult.setResult(false);
			    //失败数
			    failedCount++;
			}
			
		    }
		    if(StringUtil.equals(NumberConstants.NUMERAL_S_THREE, provinceCityInfo.getOperateCode())){//删除信息
			try {
				
			    int deleteResult = provinceCityInfoService.deleteProvinceCity(entity.getParentDistrictCode(), userCode);
			    if(deleteResult > 0){
				//成功
				processResult.setResult(true);
				processResult.setReason("FOSS:success");
    				//成功数
    				successCount++;
			    }else {
				 processResult.setResult(false);
				 processResult.setReason("FOSS: failed");
				 //失败数
				 failedCount++;
			    }
			    
			} catch (ProvinceCityInfoException e) {
			    LOGGER.error(e.getMessage(), e);
			    //失败原因
			    processResult.setReason(e.getMessage());
			    processResult.setResult(false);
			    //失败数
			    failedCount++;
			}
		    }
		    LOGGER.info("开始解锁：" + mutex.getBusinessNo());
			// 解业务锁
			businessLockService.unlock(mutex);
			LOGGER.info("完成解锁：" + mutex.getBusinessNo());   
		} else {
		    LOGGER.info("失败加锁：" + mutex.getBusinessNo());
		    //保存失败原因
		    processResult.setReason("出现了高并发操作！");
		    failedCount++;
		    continue;
		}
		    //把处理结果放在集合里面
		    processResulttList.add(processResult);
		}
		
		//成功总数
		response.setSuccessCount(successCount);
		response.getProcessResult().addAll((processResulttList));
		//失败总数
		response.setFailedCount(failedCount);*/
	    }
	}
	LOGGER.info("同步银行省市信息结束");
	return response;
    }
    /**
     * <p>
     * 同步信息情况
     * </p>
     * @author 273311
     * @date 2016-4-26 下午16:30:51
     * @param 
     * @return
     * @see
     */
    private void getProcessResult(List<ProvinceCityInfo> list, String userCode, ProvinceCityInfoNotificationResponse response) {
    	 //同步信息处理结果集合
	    List<ProvinceCityInfoProcessResult> processResulttList = new ArrayList<ProvinceCityInfoProcessResult>();
	    //同步成功总数
	    int successCount = 0;
	    //同步失败总数
	    int failedCount = 0;
	    //获取同步过来的数据
	    for(ProvinceCityInfo provinceCityInfo : list){
		    //定义银行省市信息信息实体
		    ProvinceCityEntity entity = this.convertInfo(provinceCityInfo);
		    
		    //定义一个同步处理结果对象
		    ProvinceCityInfoProcessResult processResult = new ProvinceCityInfoProcessResult();
		    //银行省市信息编码
		    processResult.setProvinceCityId(provinceCityInfo.getProvinceCityId());
		    processResult.setOperateCode(provinceCityInfo.getOperateCode());
		 // 业务锁
			MutexElement mutex = new MutexElement(provinceCityInfo.getProvinceCityId(), "PROVINCECITY_ID",
				    MutexElementType.PROVINCECITY_ID);
			LOGGER.info("开始加锁：" + mutex.getBusinessNo());
			boolean result = businessLockService.lock(mutex, NumberConstants.ZERO);
			
			if (result) {
			    LOGGER.info("成功加锁：" + mutex.getBusinessNo());
			    entity.setCreateUser(userCode);
			    entity.setModifyUser(userCode);
			  
		    if(StringUtil.equals(NumberConstants.NUMERAL_S_ONE, provinceCityInfo.getOperateCode())){//新增信息
			try {
				int addResult = 0;
			    
			    addResult = provinceCityInfoService.addProvinceCity(entity);
    			    if(addResult > 0){//保存成功
    				processResult.setResult(true);
    				processResult.setReason("FOSS:success");
    				//成功数
    				successCount++;
    			    }else {
    			        //保存失败
    				processResult.setResult(false);
    				processResult.setReason("FOSS: failed");
    				//失败数
    				failedCount++;
			    }
			} catch (ProvinceCityInfoException e) {
			    LOGGER.error(e.getMessage(), e);
			    //记录异常原因
			    processResult.setReason("FOSS:"+e.getMessage());
			    processResult.setResult(false);
			    //失败数
			    failedCount++;
			}
			
		    }
		    if(StringUtil.equals(NumberConstants.NUMERAL_S_TWO, provinceCityInfo.getOperateCode())){//修改信息
			try {
				
			    //执行修改操作
			    int updateResult = provinceCityInfoService.updateProvinceCity(entity);
			    if(updateResult > 0){
				//修改成功
				processResult.setResult(true);
				processResult.setReason("FOSS: success");
    				//成功数
    				successCount++;
			    }else {
				 processResult.setResult(false);
				 processResult.setReason("FOSS: failed");
				 //失败数
				 failedCount++;
			    }
			    
			} catch (ProvinceCityInfoException e) {
			    LOGGER.error(e.getMessage(), e);
			    //保存失败原因
			    processResult.setReason("FOSS:"+e.getMessage());
			    processResult.setResult(false);
			    //失败数
			    failedCount++;
			}
			
		    }
		    if(StringUtil.equals(NumberConstants.NUMERAL_S_THREE, provinceCityInfo.getOperateCode())){//删除信息
			try {
				
			    int deleteResult = provinceCityInfoService.deleteProvinceCity(entity.getParentDistrictCode(), userCode);
			    if(deleteResult > 0){
				//成功
				processResult.setResult(true);
				processResult.setReason("FOSS:success");
    				//成功数
    				successCount++;
			    }else {
				 processResult.setResult(false);
				 processResult.setReason("FOSS: failed");
				 //失败数
				 failedCount++;
			    }
			    
			} catch (ProvinceCityInfoException e) {
			    LOGGER.error(e.getMessage(), e);
			    //失败原因
			    processResult.setReason(e.getMessage());
			    processResult.setResult(false);
			    //失败数
			    failedCount++;
			}
		    }
		    LOGGER.info("开始解锁：" + mutex.getBusinessNo());
			// 解业务锁
			businessLockService.unlock(mutex);
			LOGGER.info("完成解锁：" + mutex.getBusinessNo());   
		} else {
		    LOGGER.info("失败加锁：" + mutex.getBusinessNo());
		    //保存失败原因
		    processResult.setReason("出现了高并发操作！");
		    failedCount++;
		    continue;
		}
		    //把处理结果放在集合里面
		    processResulttList.add(processResult);
		}
		
		//成功总数
		response.setSuccessCount(successCount);
		response.getProcessResult().addAll((processResulttList));
		//失败总数
		response.setFailedCount(failedCount);
	    
	}

	/**
     * <p>
     * 把同步过来的信息转化成Foss信息实体
     * </p>
     * @author 273311
     * @date 2016-4-26 上午10:10:39
     * @param easCostCenterInfo
     * @return
     * @see
     */
    private ProvinceCityEntity convertInfo(ProvinceCityInfo provinceCityInfo) {
    	 //定义银行省市信息信息实体
	    ProvinceCityEntity entity = new ProvinceCityEntity();
	    if(StringUtil.isNotBlank(provinceCityInfo.getProvinceCityId())){
		//行政区域编码
		entity.setDistrictCode(provinceCityInfo.getProvinceCityId().trim());
	    }else {
		//行政区域编码
		entity.setDistrictCode(provinceCityInfo.getProvinceCityId());
	    }
	    
	    //区域全称
	    entity.setDistrictName(provinceCityInfo.getProvinceCityName());
	    if(StringUtil.isNotBlank(provinceCityInfo.getProvenceId())){
		//上级行政区域编码
		entity.setParentDistrictCode(provinceCityInfo.getProvenceId().trim());
	    }else {
		//上级行政区域编码
		entity.setParentDistrictCode(provinceCityInfo.getProvenceId());
	    }
	    
	    //行政区域级别 1-省 2-市
	    entity.setDistrictDegree(provinceCityInfo.getProvinceOrCity());
		return entity;
	    
	}
}
