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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/action/OwnVehicleAction.java
 * 
 * FILE NAME        	: OwnVehicleAction.java
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
package com.deppon.foss.module.base.baseinfo.server.action;

import java.math.BigDecimal;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOwnVehicleService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OwnTruckEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.PaginationDto;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.LeasedDriverException;
import com.deppon.foss.module.base.baseinfo.api.shared.vo.OwnVehicleVo;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryValueConstants;
/**
 * 用来响应“公司车辆（厢式车、挂车、拖头）”的Action类：SUC-785 
 * 
 * @author 100847-foss-GaoPeng
 * @date 2012-12-19 下午4:03:12
 * @since
 * @version
 */
public class OwnVehicleAction extends AbstractAction {

    /**
     * 序列化对象
     */
    private static final long serialVersionUID = 3864003388052412082L;
    
	/**
	 * 声明日志对象
	 */
    private static final Logger LOGGER = LoggerFactory.getLogger(OwnVehicleAction.class);
    
    /**
     * 公司车辆"参数和结果对象
     */
    private OwnVehicleVo ownVehicleVo;

    /**
     * "公司车辆"服务
     */
    private IOwnVehicleService ownVehicleService;
    
    /**
     * <p>有条件的检索"公司车辆"列表</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-11-14 上午11:33:29
     * @return
     * @see
     */
    @JSON
    @SuppressWarnings("unchecked")
    public String queryOwnVehicleList(){
	OwnTruckEntity ownVehicle = getOwnVehicleVo().getOwnVehicle();
	try {
	    PaginationDto pagination = ownVehicleService.queryOwnVehicleListBySelectiveCondition(ownVehicle, null, start, limit);
	  //非空验证
	    if(null == pagination || CollectionUtils.isEmpty(pagination.getPaginationDtos())){
	    	//打印Log日志
	    	LOGGER.info("queryAll查询结果为空");
	    	return returnSuccess();
	    }
	    BigDecimal tempNum = new BigDecimal(NumberConstants.NUMBER_100);
	    for(int i = 0;i< pagination.getPaginationDtos().size();i++){
	    	OwnTruckEntity entity =(OwnTruckEntity) pagination.getPaginationDtos().get(i);
	    	if(null != entity.getVehicleLength()){
	    		entity.setVehicleLength(entity.getVehicleLength().divide(tempNum));
	    	}
	    	if(null != entity.getVehicleWidth()){
	    		entity.setVehicleWidth(entity.getVehicleWidth().divide(tempNum));
	    	}
	    	if(null != entity.getVehicleHeight()){
	    		entity.setVehicleHeight(entity.getVehicleHeight().divide(tempNum));
	    	}
	    }
	    getOwnVehicleVo().setOwnVehicleList(pagination.getPaginationDtos());
	    setTotalCount(pagination.getTotalCount());
	} catch (LeasedDriverException e) {
	    returnError(e);
	}
	return returnSuccess();
    }
    
    /**
     * <p>有条件的检索"公司车辆-拖头"列表</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-11-14 上午11:33:29
     * @return
     * @see
     */
    @JSON
    @SuppressWarnings("unchecked")
    public String queryOwnTractorsList(){
	OwnTruckEntity ownVehicle = getOwnVehicleVo().getOwnVehicle();
	try {
	    PaginationDto pagination = ownVehicleService.queryOwnVehicleListBySelectiveCondition(ownVehicle, DictionaryValueConstants.VEHICLE_TYPE_TRACTORS, start, limit);
	    getOwnVehicleVo().setOwnVehicleList(pagination.getPaginationDtos());
	    setTotalCount(pagination.getTotalCount());
	} catch (LeasedDriverException e) {
	    returnError(e);
	}
	return returnSuccess();
    }
    
    /**
     * <p>有条件的检索"公司车辆-挂车"列表</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-11-14 上午11:33:29
     * @return
     * @see
     */
    @JSON
    @SuppressWarnings("unchecked")
    public String queryOwnTrailerList(){
	OwnTruckEntity ownVehicle = getOwnVehicleVo().getOwnVehicle();
	try {
	    PaginationDto pagination = ownVehicleService.queryOwnVehicleListBySelectiveCondition(ownVehicle, DictionaryValueConstants.VEHICLE_TYPE_TRAILER, start, limit);
	  //非空验证
	    if(null == pagination || CollectionUtils.isEmpty(pagination.getPaginationDtos())){
	    	//打印Log日志
	    	LOGGER.info("queryOwnTrailerList查询结果为空");
	    	return returnSuccess();
	    }
	    BigDecimal tempNum = new BigDecimal(NumberConstants.NUMBER_100);
	    for(int i = 0;i< pagination.getPaginationDtos().size();i++){
	    	OwnTruckEntity entity =(OwnTruckEntity) pagination.getPaginationDtos().get(i);
	    	if(null != entity.getVehicleLength()){
	    		entity.setVehicleLength(entity.getVehicleLength().divide(tempNum));
	    	}
	    	if(null != entity.getVehicleWidth()){
	    		entity.setVehicleWidth(entity.getVehicleWidth().divide(tempNum));
	    	}
	    	if(null != entity.getVehicleHeight()){
	    		entity.setVehicleHeight(entity.getVehicleHeight().divide(tempNum));
	    	}
	    }
	    getOwnVehicleVo().setOwnVehicleList(pagination.getPaginationDtos());
	    setTotalCount(pagination.getTotalCount());
	} catch (LeasedDriverException e) {
	    returnError(e);
	}
	return returnSuccess();
    }
    
    /**
     * <p>有条件的检索"公司车辆-厢式车"列表</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-11-14 上午11:33:29
     * @return
     * @see
     */
    @JSON
    @SuppressWarnings("unchecked")
    public String queryOwnVanList(){
	OwnTruckEntity ownVehicle = getOwnVehicleVo().getOwnVehicle();
	try {
	    PaginationDto pagination = ownVehicleService.queryOwnVehicleListBySelectiveCondition(ownVehicle, DictionaryValueConstants.VEHICLE_TYPE_VAN, start, limit);
	    //非空验证
	    if(null == pagination || CollectionUtils.isEmpty(pagination.getPaginationDtos())){
	    	//打印Log日志
	    	LOGGER.info("queryOwnVanList查询结果为空");
	    	return returnSuccess();
	    }
	    BigDecimal tempNum = new BigDecimal(NumberConstants.NUMBER_100);
	    for(int i = 0;i< pagination.getPaginationDtos().size();i++){
	    	OwnTruckEntity entity =(OwnTruckEntity) pagination.getPaginationDtos().get(i);
	    	if(null != entity.getVehicleLength()){
	    		entity.setVehicleLength(entity.getVehicleLength().divide(tempNum));
	    	}
	    	if(null != entity.getVehicleWidth()){
	    		entity.setVehicleWidth(entity.getVehicleWidth().divide(tempNum));
	    	}
	    	if(null != entity.getVehicleHeight()){
	    		entity.setVehicleHeight(entity.getVehicleHeight().divide(tempNum));
	    	}
	    }
	    getOwnVehicleVo().setOwnVehicleList(pagination.getPaginationDtos());
	    setTotalCount(pagination.getTotalCount());
	} catch (LeasedDriverException e) {
	    returnError(e);
	}
	return returnSuccess();
    }
    
    /**
     * <p>有条件的检索"公司车辆-骨架车"列表</p> 
     * @author 187862-dujunhui
     * @date 2014-6-10 下午4:26:22
     * @return
     * @see
     */
    @JSON
    @SuppressWarnings("unchecked")
    public String queryOwnRQSVCList(){
	OwnTruckEntity ownVehicle = getOwnVehicleVo().getOwnVehicle();
	try {
	    PaginationDto pagination = ownVehicleService.queryOwnVehicleListBySelectiveCondition(ownVehicle, DictionaryValueConstants.VEHICLE_TYPE_RQSVC, start, limit);
	    //非空验证
	    if(null == pagination || CollectionUtils.isEmpty(pagination.getPaginationDtos())){
	    	//打印Log日志
	    	LOGGER.info("queryOwnRQSVCList查询结果为空");
	    	return returnSuccess();
	    }
	    BigDecimal tempNum = new BigDecimal(NumberConstants.NUMBER_100);
	    for(int i = 0;i< pagination.getPaginationDtos().size();i++){
	    	OwnTruckEntity entity =(OwnTruckEntity) pagination.getPaginationDtos().get(i);
	    	if(null != entity.getVehicleLength()){
	    		entity.setVehicleLength(entity.getVehicleLength().divide(tempNum));
	    	}
	    	if(null != entity.getVehicleWidth()){
	    		entity.setVehicleWidth(entity.getVehicleWidth().divide(tempNum));
	    	}
	    	if(null != entity.getVehicleHeight()){
	    		entity.setVehicleHeight(entity.getVehicleHeight().divide(tempNum));
	    	}
	    }
	    getOwnVehicleVo().setOwnVehicleList(pagination.getPaginationDtos());
	    setTotalCount(pagination.getTotalCount());
	} catch (LeasedDriverException e) {
	    returnError(e);
	}
	return returnSuccess();
    }
    
    /**
     * @return  the ownVehicleVo
     */
    public OwnVehicleVo getOwnVehicleVo() {
	if (null == ownVehicleVo) {
	    this.ownVehicleVo = new OwnVehicleVo();
	}
        return ownVehicleVo;
    }
    
    /**
     * @param ownVehicleVo the ownVehicleVo to set
     */
    public void setOwnVehicleVo(OwnVehicleVo ownVehicleVo) {
        this.ownVehicleVo = ownVehicleVo;
    }

    /**
     * @param ownVehicleService the ownVehicleService to set
     */
    public void setOwnVehicleService(IOwnVehicleService ownVehicleService) {
        this.ownVehicleService = ownVehicleService;
    }
}
