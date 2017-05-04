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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/action/DepartureStandardAction.java
 * 
 * FILE NAME        	: DepartureStandardAction.java
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

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.base.util.define.SymbolConstants;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.server.service.IExpressDepartureStandardService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.ExpressDepartureStandardEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.DepartureStandardException;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.MessageType;
import com.deppon.foss.module.base.baseinfo.api.shared.vo.ExpressDepartureStandardVo;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;


/**
 * 快递发车标准控制类
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:094463-foss-xieyantao,date:2012-11-14 下午2:26:02 </p>
 * @author 094463-foss-xieyantao
 * @date 2012-11-14 下午2:26:02
 * @since
 * @version
 */
public class ExpressDepartureStandardAction extends AbstractAction {
    
    /**
     * 日志.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(ExpressDepartureStandardAction.class);

    /**
     * serialVersionUID.
     */
    private static final long serialVersionUID = -3396460228248439138L;
    
    /**
     * 发车标准service.
     */
    private IExpressDepartureStandardService expressdepartureStandardService;
    
    /**
     * 发车标准Vo.
     */
    private ExpressDepartureStandardVo standardVo = new ExpressDepartureStandardVo(); 

    /**
     * 获取 发车标准Vo.
     *
     * @return the 发车标准Vo
     */
    public ExpressDepartureStandardVo getStandardVo() {
        return standardVo;
    }
    
    /**
     * 设置 发车标准Vo.
     *
     * @param standardVo the new 发车标准Vo
     */
    public void setStandardVo(ExpressDepartureStandardVo standardVo) {
        this.standardVo = standardVo;
    }

    /**
     * 设置 发车标准service.
     *
     * @param expressdepartureStandardService the new 发车标准service
     */
    public void setExpressdepartureStandardService(
			IExpressDepartureStandardService expressdepartureStandardService) {
		this.expressdepartureStandardService = expressdepartureStandardService;
	}
    
    /**
     * <p>添加发车标准</p>.
     *
     * @return 
     * @author 094463-foss-xieyantao
     * @date 2012-11-14 下午2:30:10
     * @see
     */
    @JSON
    public String addDepartureStandard(){
	try {
	    UserEntity user =  FossUserContext.getCurrentUser();// 获取当前登录用户
	    String userCode = user.getEmployee().getEmpCode();// 当前登录用户empcode
	    ExpressDepartureStandardEntity standardEntity = standardVo.getStartStandardEntity();
	    //设置创建人
	    standardEntity.setCreateUser(userCode);
	    //设置修改人
	    standardEntity.setModifyUser(userCode);
	    //准点出发时间
	    String leaveTime = standardEntity.getLeaveTime();
	    // 准点到达时间(eg: 1645)
	    String arriveTime = standardEntity.getArriveTime();
	    if(StringUtil.isNotBlank(leaveTime)){
		//去掉冒号
		leaveTime = replaceColon(leaveTime);
		standardEntity.setLeaveTime(leaveTime);
	    }
	    if(StringUtil.isNotBlank(arriveTime)){
		arriveTime = replaceColon(arriveTime);
		standardEntity.setArriveTime(arriveTime);
	    }
	    // 新增发车标准，返回新增的对象
	    ExpressDepartureStandardEntity startStandardEntity = expressdepartureStandardService
			.addDepartureStandard(standardEntity);
    	    standardVo.setStartStandardEntity(convertInfos(startStandardEntity));
    	    
	    return returnSuccess(MessageType.SAVE_SUCCESS);
	} catch (DepartureStandardException e) {
	    LOGGER.debug(e.getMessage(), e);
	    return returnError(e);
	}
    }
    
    

	/**
     * <p>删除发车标准</p>.
     *
     * @return 
     * @author 094463-foss-xieyantao
     * @date 2012-11-14 下午2:30:10
     * @see
     */
    @JSON
    public String deleteDepartureStandard(){
	try {
	    // 获取当前登录用户
	    UserEntity user =  FossUserContext.getCurrentUser();
	    // 当前登录用户empcode
	    String userCode = user.getEmployee().getEmpCode();
	    ExpressDepartureStandardEntity standardEntity = standardVo.getStartStandardEntity();
	    standardEntity.setModifyUser(userCode);
	    expressdepartureStandardService.deleteDepartureStandard(standardEntity);
	    
	    return returnSuccess(MessageType.DELETE_SUCCESS);
	} catch (DepartureStandardException e) {
	    LOGGER.debug(e.getMessage(), e);
	    return returnError(e);
	}
    }
    
    /**
     * <p>修改发车标准</p>.
     *
     * @return 
     * @author 094463-foss-xieyantao
     * @date 2012-11-14 下午2:30:10
     * @see
     */
    @JSON
    public String updateDepartureStandard(){
	try {
	    // 获取当前登录用户
	    UserEntity user =  FossUserContext.getCurrentUser();
	    // 当前登录用户empcode
	    String userCode = user.getEmployee().getEmpCode();
	    ExpressDepartureStandardEntity standardEntity = standardVo.getStartStandardEntity();
	    standardEntity.setModifyUser(userCode);
	    //准点出发时间
	    String leaveTime = standardEntity.getLeaveTime();
	    // 准点到达时间(eg: 1645)
	    String arriveTime = standardEntity.getArriveTime();
	    if(StringUtil.isNotBlank(leaveTime)){
		//去掉冒号
		leaveTime = replaceColon(leaveTime);
		standardEntity.setLeaveTime(leaveTime);
	    }
	    if(StringUtil.isNotBlank(arriveTime)){
		arriveTime = replaceColon(arriveTime);
		standardEntity.setArriveTime(arriveTime);
	    }
	    //修改成功返回实体
	    standardEntity = expressdepartureStandardService.updateDepartureStandard(standardEntity);
	    standardVo.setStartStandardEntity(convertInfos(standardEntity));
	    
	    return returnSuccess(MessageType.UPDATE_SUCCESS);
	} catch (DepartureStandardException e) {
	    LOGGER.debug(e.getMessage(), e);
	    return returnError(e);
	}
    }
    
    /**
     * <p>替换掉冒号</p>.
     *
     * @param colonStr 
     * @return 
     * @author 094463-foss-xieyantao
     * @date 2012-12-6 下午6:55:05
     * @see
     */
    private String replaceColon(String colonStr){
	if(StringUtil.isNotBlank(colonStr)){
	    return colonStr.replace(SymbolConstants.EN_COLON, StringUtils.EMPTY);
	}else{
	    return null;
	}
    }
    
    /**
     * <p>为准点发车时间、准点到达时间添加冒号</p> 
     * @author 094463-foss-xieyantao
     * @date 2013-3-16 下午2:59:56
     * @param entity
     * @return
     * @see
     */
    private ExpressDepartureStandardEntity convertInfos(ExpressDepartureStandardEntity entity){
	if(null != entity){
	    //准点发车时间
	    entity.setLeaveTime(addColon(entity.getLeaveTime()));
	    // 准点到达时间
	    entity.setArriveTime(addColon(entity.getArriveTime()));
	    
	    return entity;
	}else {
	    return entity;
	}
    }
    
    /**
     * <p>添加冒号字符串</p> 
     * @author 094463-foss-xieyantao
     * @date 2013-3-16 下午2:55:40
     * @param colonStr 
     * @return
     * @see
     */
    private String addColon(String colonStr){
	if(StringUtil.isNotBlank(colonStr)){
	    StringBuffer buffer = new StringBuffer();
	    //添加冒号
	    return buffer.append(colonStr.substring(NumberConstants.STRING_LOCATION_0, NumberConstants.STRING_LOCATION_2)).append(":").append(colonStr.substring(NumberConstants.STRING_LOCATION_2, NumberConstants.STRING_LOCATION_4)).toString();
	}else {
	    return colonStr;
	}
    }

}
