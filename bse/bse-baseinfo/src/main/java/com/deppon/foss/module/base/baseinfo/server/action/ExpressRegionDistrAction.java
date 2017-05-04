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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/action/DestinationLineAction.java
 * 
 * FILE NAME        	: DestinationLineAction.java
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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.module.base.baseinfo.api.server.service.IExpressBigRegionDistrService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.ExpressBigRegionDistrEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.MessageType;
import com.deppon.foss.module.base.baseinfo.api.shared.vo.ExpressRegionDistrVo;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;

/**
 * 
 * 快递大区与行政区域映射关系
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:094463-foss-xieyantao,date:2013-7-25 下午3:04:20 </p>
 * @author 094463-foss-xieyantao
 * @date 2013-7-25 下午3:04:20
 * @since
 * @version
 */
public class ExpressRegionDistrAction extends AbstractAction {
    
    /**
     * 日志.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(ExpressRegionDistrAction.class);
    
    /**
     * serialVersionUID.
     */
    private static final long serialVersionUID = -2889332728710787995L;
    
    /**
     * 快递大区与行政区域映射关系Service接口.
     */
    private IExpressBigRegionDistrService expressBigRegionDistrService;
    
    /**
     * 设置 快递大区与行政区域映射关系Service接口.
     *
     * @param expressBigRegionDistrService the expressBigRegionDistrService to set
     */
    public void setExpressBigRegionDistrService(
    	IExpressBigRegionDistrService expressBigRegionDistrService) {
        this.expressBigRegionDistrService = expressBigRegionDistrService;
    }

    /**
     * 前台注入参数.
     */
    private ExpressRegionDistrVo regionDistrVo = new ExpressRegionDistrVo();
    
    
    /**
     * <p>
     * 新增快递大区与行政区域映射关系
     * </p>.
     *
     * @return 
     * @author 094463-foss-xieyantao
     * @date 2013-7-25 下午3:04:20
     * @see
     */
    @JSON
    public String addBigRegionDistr() {
	try {
	    UserEntity user = FossUserContext.getCurrentUser();// 获取当前登录用户
	    String userCode = user.getEmployee().getEmpCode();// 当前登录用户empcode
	   
	    expressBigRegionDistrService.batchAddInfos(regionDistrVo.getEntityList(),userCode);

	    return returnSuccess(MessageType.SAVE_SUCCESS);
	} catch (BusinessException e) {
	    LOGGER.debug(e.getMessage(), e);
	    return returnError(e.getMessage(), e);
	}
    }
    
    /**
     * <p>根据ID查询快递大区与行政区域映射关系实体</p> 
     * @author 094463-foss-xieyantao
     * @date 2013-8-15 下午4:14:26
     * @return
     * @see
     */
    @JSON
    public String queryBigRegionDistrById(){
	try {
	    //根据ID查询快递大区与行政区域映射关系实体
	    ExpressBigRegionDistrEntity entity = expressBigRegionDistrService.queryInfoById(regionDistrVo.getEntity().getId());
	    regionDistrVo.setEntity(entity);
	    return returnSuccess();
	} catch (BusinessException e) {
	    LOGGER.debug(e.getMessage(), e);
	    return returnError(e.getMessage(), e);
	}
    }

    /**
     * <p>
     * 作废快递大区与行政区域映射关系
     * </p>.
     *
     * @return 
     * @author 094463-foss-xieyantao
     * @date 2013-7-25 下午3:04:20
     * @see
     */
    @JSON
    public String deleteBigRegionDistr() {
	try {
	    UserEntity user = FossUserContext.getCurrentUser();// 获取当前登录用户
	    String userCode = user.getEmployee().getEmpCode();// 当前登录用户empcode
	    //批量作废信息
	    expressBigRegionDistrService.deleteInfo(regionDistrVo.getCodeList(),userCode);
	    //返回消息
	    return returnSuccess(MessageType.DELETE_SUCCESS);
	} catch (BusinessException e) {
	    LOGGER.debug(e.getMessage(), e);
	    return returnError(e.getMessage(), e);
	}
    }

    /**
     * <p>
     * 修改快递大区与行政区域映射关系
     * </p>.
     *
     * @return 
     * @author 094463-foss-xieyantao
     * @date 2013-7-25 下午3:04:20
     * @see
     */
    @JSON
    public String updateBigRegionDistr() {

	try {
	    UserEntity user =  FossUserContext.getCurrentUser();// 获取当前登录用户
	    String userCode = user.getEmployee().getEmpCode();// 当前登录用户empcode
	    
	    //批量修改快递大区与行政区域映射关系
	    expressBigRegionDistrService.batchUpdateInfos(regionDistrVo.getAddList(), regionDistrVo.getDeleteList(), userCode);

	    return returnSuccess(MessageType.SAVE_SUCCESS);
	} catch (BusinessException e) {
	    LOGGER.debug(e.getMessage(), e);
	    return returnError(e.getMessage(), e);
	}

    }

    /**
     * <p>
     * 分页查询快递大区与行政区域映射关系所有信息
     * </p>.
     *
     * @return 
     * @author 094463-foss-xieyantao
     * @date 2013-7-25 下午3:04:20
     * @see
     */
    @JSON
    public String queryBigRegionDistrs() {
	try {
	    ExpressBigRegionDistrEntity entity = regionDistrVo.getEntity();
	    // 设置线路类型为：快递大区与行政区域映射关系
	    List<ExpressBigRegionDistrEntity> entityList = expressBigRegionDistrService.queryInfos(entity, this.getLimit(),this.getStart());
	    regionDistrVo.setEntityList(entityList);

	    // 查询总记录数
	    Long totalCount = expressBigRegionDistrService.queryRecordCount(entity);
	    // 设置totalCount
	    this.setTotalCount(totalCount);

	    return returnSuccess();
	} catch (BusinessException e) {
	    LOGGER.debug(e.getMessage(), e);
	    return returnError(e.getMessage(), e);
	}
    }
    
    
    /**
     * 获取 前台注入参数.
     *
     * @return  the regionDistrVo
     */
    public ExpressRegionDistrVo getRegionDistrVo() {
        return regionDistrVo;
    }

    
    /**
     * 设置 前台注入参数.
     *
     * @param regionDistrVo the regionDistrVo to set
     */
    public void setRegionDistrVo(ExpressRegionDistrVo regionDistrVo) {
        this.regionDistrVo = regionDistrVo;
    }
    
    
    

}
