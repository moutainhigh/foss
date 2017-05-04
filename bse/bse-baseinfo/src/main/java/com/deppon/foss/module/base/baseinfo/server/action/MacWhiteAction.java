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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/action/MacWhiteAction.java
 * 
 * FILE NAME        	: MacWhiteAction.java
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

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.components.security.SecurityNonCheckRequired;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.module.base.baseinfo.api.server.service.IMacWhiteService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.MacWhiteEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.MessageType;
import com.deppon.foss.module.base.baseinfo.api.shared.vo.MacWhiteVo;
import com.deppon.foss.module.frameworkimpl.server.interceptor.CookieNonCheckRequired;

/**
 * MAC地址白名单Action
 * 
 * @author 094463-foss-xieyantao
 * @date 2013-3-12 下午2:02:13
 * @since
 * @version
 * 新增：增，删，改，查 。 author:132599-foss-shenweihua,date:2013-4-25 上午10:31:13
 */
public class MacWhiteAction extends AbstractAction{
    
    /**
     * 日志.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(MacWhiteAction.class);
    
    /**
     * serialVersionUID.
     */
    private static final long serialVersionUID = 4515025370689117271L;
    
    /**
     * MAC地址白名单Service接口.
     */
    private IMacWhiteService macWhiteService;
    
    /**
     * 系统配置参数 Service接口
     */
  //  private IConfigurationParamsService configurationParamsService;
    
    /**
     * MAC地址白名单信息VO.
     */
    private MacWhiteVo macWhiteVo = new MacWhiteVo();
    
       
    /**
     * <p>根据MAC地址验证是否存在</p>.
     *
     * @return 
     * @author 094463-foss-xieyantao
     * @date 2013-3-12 下午2:13:38
     * @see
     */
    @CookieNonCheckRequired
    @SecurityNonCheckRequired
    @JSON
    public String queryMacWhite(){
	try {
	    LOGGER.info("调用MAC地址白名单验证接口开始.........");
	   // ConfigurationParamsEntity entity = configurationParamsService.queryConfigurationParamsByOrgCode(DictionaryConstants.SYSTEM_CONFIG_PARM__BAS,DictionaryValueConstants.MAC_WHITE_IS_VALIDATE,"DIP");
	    boolean isExist = true;
	   /*if(null != entity){
		//如果验证
		if(StringUtils.equals(FossConstants.YES, entity.getConfValue())){
		  //验证MAC地址是否存在
		  isExist = macWhiteService.queryMacAddressByMac(macWhiteVo.getMacAddress());
		}
	    }else {
		//验证MAC地址是否存在
		  isExist = macWhiteService.queryMacAddressByMac(macWhiteVo.getMacAddress());
	    }*/
	    macWhiteVo.setExist(isExist);
	    LOGGER.info("调用MAC地址白名单验证接口结束.........");
	    return returnSuccess();
	} catch (BusinessException e) {
	    return returnError(e);
	}
    }
    
    /**
     * 添加MAC地址白名单信息
     * 
     * @author 132599-foss-shenweihua
     * @date 2013-4-25 下午5:44:10
     * @return
     * @see
     */
    @JSON
    public String addMacWhite() {
	try {
		//判断macWhiteEntity是否为null
	    if(null == macWhiteVo.getEntity()){
	    	LOGGER.info("增加Mac地址白名单传入的参数不能为空!");
	    	return null;
	    }
		MacWhiteEntity entity = macWhiteVo.getEntity();
		
	    if(StringUtils.isEmpty(entity.getMacAddress())||StringUtils.isEmpty(entity.getUserCode())){
	    	LOGGER.info("MAC地址或员工工号为空!");
	    	return null;
	    }
	  
	    // 保存成功返回一个对象
	    macWhiteService.addMacWhite(entity);

	    return returnSuccess(MessageType.SAVE_SUCCESS);
	} catch (BusinessException e) {
	    LOGGER.debug(e.getMessage(), e);
	    return returnError(e);
	}
    }
    
    /**
     * 作废MAC地址白名单信息
     * 
     * @author 132599-foss-shenweihua
     * @date 2013-4-25 下午5:50:36
     * @return
     * @see
     */
    @JSON
    public String deleteMacWhite() {
	try {
		if(null == macWhiteVo.getIdList()){
	    	LOGGER.info("删除Mac地址白名单传入的参数不能为空!");
	    	return null;
	    }
		macWhiteService.deleteMacWhiteById(macWhiteVo.getIdList());
	    return returnSuccess(MessageType.DELETE_SUCCESS);
	} catch (BusinessException e) {
	    LOGGER.debug(e.getMessage(), e);
	    return returnError(e);
	}
    }
    
    /**
     * 修改MAC地址白名单信息
     *
     * @return 
     * @author 132599-foss-shenweihua
     * @date 2013-4-25 下午5:55:36
     * @see
     */
    @JSON
    public String updateMacWhite() {
	try {
	    // 获取Form信息
	    MacWhiteEntity entity = macWhiteVo.getEntity();
	    if(null == entity){
	    	LOGGER.info("更新Mac地址白名单传入的参数不能为空!");
	    	return null;
	    }
	    // 保存成功返回一个对象
	    macWhiteService.updateMacWhite(entity);

	    return returnSuccess(MessageType.UPDATE_SUCCESS);
	} catch (BusinessException e) {
	    LOGGER.debug(e.getMessage(), e);
	    return returnError(e);
	}
    }
    
    /**
     * 分页查询MAC地址白名单信息
     *
     * @return 
     * @author 132599-foss-shenweihua
     * @date 2013-4-25 下午6:04:36
     * @see
     */
    @JSON
    public String queryMacWhites() {
	try {
		
	    List<MacWhiteEntity> entityList = macWhiteService.queryAllMacWhite(macWhiteVo.getEntity(), limit, start);
	    macWhiteVo.setEntityList(entityList);

	    // 查询总记录数
	    Long totalCount = macWhiteService.queryRecordCount(macWhiteVo.getEntity());
	    // 设置totalCount
	    this.setTotalCount(totalCount);

	    return returnSuccess();
	} catch (BusinessException e) {
	    LOGGER.info(e.getMessage(), e);
	    return returnError(e);
	}
    }
   
    /**
     * 获取 mAC地址白名单信息VO.
     *
     * @return  the macWhiteVo
     */
    public MacWhiteVo getMacWhiteVo() {
        return macWhiteVo;
    }
    
    /**
     * @param configurationParamsService the configurationParamsService to set
     */
    /*public void setConfigurationParamsService(
    	IConfigurationParamsService configurationParamsService) {
        this.configurationParamsService = configurationParamsService;
    }*/

    /**
     * 设置 mAC地址白名单信息VO.
     *
     * @param macWhiteVo the macWhiteVo to set
     */
    public void setMacWhiteVo(MacWhiteVo macWhiteVo) {
        this.macWhiteVo = macWhiteVo;
    }

    /**
     * 设置 mAC地址白名单Service接口.
     *
     * @param macWhiteService the macWhiteService to set
     */
    public void setMacWhiteService(IMacWhiteService macWhiteService) {
        this.macWhiteService = macWhiteService;
    }

}
