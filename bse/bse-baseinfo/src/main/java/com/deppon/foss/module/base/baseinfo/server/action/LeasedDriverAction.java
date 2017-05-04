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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/action/LeasedDriverAction.java
 * 
 * FILE NAME        	: LeasedDriverAction.java
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

import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.module.base.baseinfo.api.server.service.ILeasedDriverService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.LeasedDriverEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.PaginationDto;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.LeasedDriverException;
import com.deppon.foss.module.base.baseinfo.api.shared.vo.LeasedDriverVo;
import com.deppon.foss.module.base.common.api.server.service.IBusinessLockService;
import com.deppon.foss.module.base.common.api.shared.define.MutexElementType;
import com.deppon.foss.module.base.common.api.shared.dto.MutexElement;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
/**
 * 用来响应“外请车司机”的Action类：SUC-211 
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:100847-foss-GaoPeng,date:2012-11-14 上午10:42:41</p>
 * @author 100847-foss-GaoPeng
 * @date 2012-11-14 上午10:42:41
 * @since
 * @version
 */
public class LeasedDriverAction extends AbstractAction {

    /**
     * Serial Version UID
     */
    private static final long serialVersionUID = 1786235148926823730L;
    
    //"外请司机"参数和结果对象
    private LeasedDriverVo leasedDriverVo;
    
    //"外请司机"服务
    private ILeasedDriverService leasedDriverService;
    
    /**
     * 业务锁
     */
 	private IBusinessLockService businessLockService;
	 
	public void setBusinessLockService(IBusinessLockService businessLockService) {
		this.businessLockService = businessLockService;
	}

    /**
     * <p>通过司机信息来检索一个司机信息</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-11-14 上午11:15:30
     * @return
     * @see
     */
    @JSON
    public String queryLeasedDriver(){
	LeasedDriverEntity leasedDriver = getLeasedDriverVo().getLeasedDriver();
	try {
	    leasedDriver = leasedDriverService.queryLeasedDriverBySelective(leasedDriver);
	    getLeasedDriverVo().setLeasedDriver(leasedDriver);
	} catch (LeasedDriverException e) {
	    returnError(e);
	}
	return returnSuccess();
    }
    
    /**
     * <p>有条件的分页检索司机信息列表</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-11-14 上午11:33:29
     * @return
     * @see
     */
    @JSON
    @SuppressWarnings("unchecked")
    public String queryLeasedDriverList(){
	LeasedDriverEntity leasedDriver = getLeasedDriverVo().getLeasedDriver();
	try {
	    PaginationDto pagination = leasedDriverService.queryLeasedDriverListBySelectiveCondition(leasedDriver, start, limit);
	    getLeasedDriverVo().setLeasedDriverList(pagination.getPaginationDtos());
	    setTotalCount(pagination.getTotalCount());
	} catch (LeasedDriverException e) {
	    returnError(e);
	}
	return returnSuccess();
    }
    
    /**
     * <p>新增一个外请司机信息</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-11-29 下午2:00:04
     * @return
     * @see
     */
    @JSON
    public String addLeasedDriver(){
	LeasedDriverEntity leasedDriver = getLeasedDriverVo().getLeasedDriver();
	String createUser = getCurrentUser().getEmployee().getEmpCode();
	MutexElement mutex =null;
	try {
		//优化加锁—313353，并发优化，对idcard加锁
		mutex = new MutexElement(getLeasedDriverVo().
				getLeasedDriver().getIdCard(), 
				"LEASED_DRIVER_IDCARD",MutexElementType.
				LEASED_DRIVER_IDCARD);
		boolean result = businessLockService.lock(mutex,
				NumberConstants.ZERO);
		if (result) {
			leasedDriverService.addLeasedDriver(leasedDriver, createUser, false);
			return returnSuccess(LeasedDriverException.LEASEDDRIVER_ADD_SUCCESS);
		} else {
			return returnError("该司机数据正在更新中，请稍后再操作。");
		}
	} catch (LeasedDriverException e) {
	    returnError(e);
	} catch (BusinessException e) {
	    returnError(e);
	} finally {
		if (mutex != null){
			businessLockService.unlock(mutex);
		}
	}
	return returnSuccess();
    }
    
    /**
     * <p>删除一个外请司机信息</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-11-29 下午2:00:02
     * @return
     * @see
     */
    @JSON
    public String deleteLeasedDriver(){
	List<String> ids = getLeasedDriverVo().getBatchIds();
	String modifyUser = getCurrentUser().getEmployee().getEmpCode();
	try {
		leasedDriverService.deleteLeasedDriver(ids, modifyUser);
		return returnSuccess(LeasedDriverException.LEASEDDRIVER_DEL_SUCCESS);
	} catch (LeasedDriverException e) {
	    returnError(e);
	} catch (BusinessException e) {
	    returnError(e);
	}
	return returnSuccess();
    }
    
    /**
     * <p>修改一个外请司机信息</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-11-29 下午1:59:59
     * @return
     * @see
     */
    @JSON
    public String updateLeasedDriver(){
	LeasedDriverEntity leasedDriver = getLeasedDriverVo().getLeasedDriver();
	String modifyUser = getCurrentUser().getEmployee().getEmpCode();
	MutexElement mutex = null;
	try {
		//优化加锁—313353，并发优化，对idcard加锁
		mutex = new MutexElement(getLeasedDriverVo().
			getLeasedDriver().getIdCard(), 
			"LEASED_DRIVER_IDCARD",MutexElementType.
			LEASED_DRIVER_IDCARD);
		boolean result = businessLockService.lock(mutex,
			NumberConstants.ZERO);
		if (result) {
			leasedDriverService.updateLeasedDriver(leasedDriver, modifyUser, true);
			return returnSuccess(LeasedDriverException.LEASEDDRIVER_UPD_SUCCESS);
		} else {
			return returnError("该司机数据正在更新中，请稍后再操作。");
		}
	} catch (LeasedDriverException e) {
	    returnError(e);
	} catch (BusinessException e) {
	    returnError(e);
	} finally {
		if(mutex!=null){
			businessLockService.unlock(mutex);				
		}
	}
	return returnSuccess();
    }
    
    /**
     * <p>获取当前的登录用户</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-11-8 下午5:34:46
     * @return
     * @see
     */
    private UserEntity getCurrentUser(){
	UserEntity user = FossUserContext.getCurrentUser();
	return null == user ? new UserEntity() : user;
    }
    
    /**
     * @return  the leasedDriverVo
     */
    public LeasedDriverVo getLeasedDriverVo() {
	if (null == leasedDriverVo) {
	    this.leasedDriverVo = new LeasedDriverVo();
	}
        return leasedDriverVo;
    }
    
    /**
     * @param leasedDriverVo the leasedDriverVo to set
     */
    public void setLeasedDriverVo(LeasedDriverVo leasedDriverVo) {
        this.leasedDriverVo = leasedDriverVo;
    }
    
    /**
     * @param leasedDriverService the leasedDriverService to set
     */
    public void setLeasedDriverService(ILeasedDriverService leasedDriverService) {
        this.leasedDriverService = leasedDriverService;
    }
}
