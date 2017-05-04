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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/action/LeasedVehicleAction.java
 * 
 * FILE NAME        	: LeasedVehicleAction.java
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
import com.deppon.foss.module.base.baseinfo.api.server.service.ILeasedVehicleService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.LeasedTruckEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.PaginationDto;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.LeasedVehicleException;
import com.deppon.foss.module.base.baseinfo.api.shared.vo.LeasedVehicleVo;
import com.deppon.foss.module.base.common.api.server.service.IBusinessLockService;
import com.deppon.foss.module.base.common.api.shared.define.MutexElementType;
import com.deppon.foss.module.base.common.api.shared.dto.MutexElement;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
/**
 * 用来响应“外请车辆（厢式车、挂车、拖头）”的Action类：SUC-104、SUC-608、SUC-44、SUC-103  
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:100847-foss-GaoPeng,date:2012-11-26 下午8:47:51</p>
 * @author 100847-foss-GaoPeng
 * @date 2012-11-26 下午8:47:51
 * @since
 * @version
 */
public class LeasedVehicleAction extends AbstractAction {

    /**
     * Serial Version UID
     */
    private static final long serialVersionUID = 4029629214645825530L;
    
    //"外请车"参数和结果对象
    private LeasedVehicleVo leasedVehicleVo;
    
    //"外请车"服务
    private ILeasedVehicleService leasedVehicleService;
	
	/**
     * 业务锁
     */
 	private IBusinessLockService businessLockService;
	 
	public void setBusinessLockService(IBusinessLockService businessLockService) {
		this.businessLockService = businessLockService;
	}

  /*  private IBindingLeasedVehicleService bindingLeasedVehicleService;
    
    public void setBindingLeasedVehicleService(
			IBindingLeasedVehicleService bindingLeasedVehicleService) {
		this.bindingLeasedVehicleService = bindingLeasedVehicleService;
	}*/

	/**
     * <p>通过车辆信息来检索一个车辆信息</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-11-27 上午11:46:39
     * @return
     * @see
     */
    @JSON
    public String queryLeasedVehicle(){
	LeasedTruckEntity leasedTruck = leasedVehicleVo.getLeasedVehicle();
	try {
	    leasedTruck = leasedVehicleService.queryLeasedVehicleBySelectiveCondition(leasedTruck);
	    getLeasedVehicleVo().setLeasedVehicle(leasedTruck);
	} catch (BusinessException e) {
	    return returnError(e.getMessage());
	}
	return returnSuccess();
    }
    
    /**
     * <p>有条件的分页检索车信息列表</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-11-27 上午11:46:42
     * @return
     * @see
     */
    @JSON
    @SuppressWarnings("unchecked")
    public String queryLeasedVehicleList(){
	LeasedTruckEntity leasedTruck = getLeasedVehicleVo().getLeasedVehicle();
	PaginationDto pagination = null;
	try {
	    pagination = leasedVehicleService.queryLeasedVehicleListDtosBySelectiveCondition(leasedTruck, start, limit);
	    getLeasedVehicleVo().setLeasedVehicleList(pagination.getPaginationDtos());
	    setTotalCount(pagination.getTotalCount());
	} catch (LeasedVehicleException e) {
	    returnError(e.getMessage());
	}
	return returnSuccess();
    }
    
    /**
     * <p>新增一个外请车信息</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-12-1 上午9:47:35
     * @return
     * @see
     */
    @JSON
	public String addLeasedVehicle() {
		LeasedTruckEntity leasedTruck = getLeasedVehicleVo().getLeasedVehicle();
		String createUser = getCurrentUser().getEmpCode();
		// 设置当前用户的所属部门
		leasedTruck.setCreateUserDept(getCurrentUser().getCurrentDeptCode());
		MutexElement mutex =null;
		try {
			//优化加锁—313353,并发优化，对外请车车牌号加锁
			mutex = new MutexElement(leasedTruck.getVehicleNo(), 
					"LEASED_VEHICLE_NO",MutexElementType.
					LEASED_VEHICLE_NO);
			boolean result = businessLockService.lock(mutex,
					NumberConstants.ZERO);
			if (result) {		
				leasedVehicleService.addLeasedVehicle(leasedTruck, createUser,
						false);
				return returnSuccess(LeasedVehicleException.LEASEDVEHICLE_ADD_SUCCESS);
			} else {
				return returnError("改车辆正在被操作，请稍等。");
			}
		} catch (LeasedVehicleException e) {
			return returnError(e.getMessage());
		} catch (BusinessException e) {
		    return returnError(e);
		} finally {
			if(mutex!=null){
				businessLockService.unlock(mutex);				
			}
		}
	}

    /**
     * <p>删除一个外请车信息</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-12-1 上午9:47:40
     * @return
     * @see
     */
	@JSON
	public String deleteLeasedVehicle() {
		List<String> ids = getLeasedVehicleVo().getBatchIds();
		//String modifyUser = getCurrentUser().getEmpCode();
		try {
			leasedVehicleService.deleteLeasedVehicle(ids, getCurrentUser());
			return returnSuccess(LeasedVehicleException.LEASEDVEHICLE_DEL_SUCCESS);
		} catch (LeasedVehicleException e) {
			returnError(e.getMessage());
		} catch (BusinessException e) {
		    return returnError(e);
		} 
		return returnSuccess();
	}
    
    /**
     * <p>修改一个外请车信息</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-12-1 上午9:47:45
     * @return
     * @see
     */
    @JSON
    public String updateLeasedVehicle(){
	LeasedTruckEntity leasedTruck = getLeasedVehicleVo().getLeasedVehicle();
	CurrentInfo modifyUser = getCurrentUser();
	//设置修改人所属部门
	//leasedTruck.setModifyUserDept(getCurrentUser().getCurrentDeptCode());
	MutexElement mutex =null;
	try {
		//优化加锁—313353,并发优化，对外请车车牌号加锁
		mutex = new MutexElement(leasedTruck.getVehicleNo(), 
				"LEASED_VEHICLE_NO",MutexElementType.
				LEASED_VEHICLE_NO);
		boolean result = businessLockService.lock(mutex,
				NumberConstants.ZERO);
		if (result) {		
			leasedVehicleService.updateLeasedVehicle(leasedTruck, modifyUser, true);
			return returnSuccess(LeasedVehicleException.LEASEDVEHICLE_UPD_SUCCESS);
		} else {
			return returnError("改车辆正在被操作，请稍等。");
		}
	} catch (LeasedVehicleException e) {
	    returnError(e.getMessage());
	} catch (BusinessException e) {
	    return returnError(e);
	} finally {
		if(mutex!=null){
			businessLockService.unlock(mutex);				
		}
	}
	return returnSuccess();
    }
    
    /**
     * <p>修改净空</p> 
     * @author 232607 
     * @date 2015-10-12 下午9:24:12
     * @return
     * @see
     */
    @JSON
    public String updateSelfVolume(){
		LeasedTruckEntity leasedTruck = getLeasedVehicleVo().getLeasedVehicle();
		MutexElement mutex =null;
		//313353 空指针异常修复
		if(null == leasedTruck){
			return returnError("车辆信息不能为空！");
		}
		try {
			//优化加锁—313353,并发优化，对外请车车牌号加锁
			mutex = new MutexElement(leasedTruck.getVehicleNo(), 
					"LEASED_VEHICLE_NO",MutexElementType.
					LEASED_VEHICLE_NO);
			boolean result = businessLockService.lock(mutex,
					NumberConstants.ZERO);
			if (result) {		
				leasedVehicleService.updateSelfVolume(leasedTruck);
				return returnSuccess(LeasedVehicleException.LEASEDVEHICLE_UPD_SUCCESS);
			} else {
				return returnError("改车辆正在被操作，请稍等。");
			}
		} catch (BusinessException e) {
			return returnError(e.getMessage());
		} finally {
			if(mutex!=null){
				businessLockService.unlock(mutex);				
			}
		}
    }
    
    /**
     * 外清车服务资料,可查询【外请厢式车】信息里的“白名单状态”为“可用”的车辆信息
     * 查询结果字段有：勾选框、车牌号、所属司机、司机电话、载重（吨）、自重（吨）、净空（方）、车长（米）、所属车队、操作人
     * @author 310854
     * @date 2016-5-14
     */
    @JSON
    @SuppressWarnings("unchecked")
    public String queryLeasedServiceDateList(){
	LeasedTruckEntity leasedTruck = leasedVehicleVo.getLeasedVehicle();
	PaginationDto pagination = null;
	try {
	    pagination = leasedVehicleService.queryLeasedServiceDateList(leasedTruck, start, limit);
//	    BindingLeasedTruckEntity entity = bindingLeasedVehicleService.queryBindingLeasedDate(leasedTruck.getVehicleNo());
	    getLeasedVehicleVo().setLeasedVehicleList(pagination.getPaginationDtos());
	    setTotalCount(pagination.getTotalCount());
	} catch (LeasedVehicleException e) {
	    returnError(e.getMessage());
	}
	return returnSuccess();
    }
    
    /**
     * 绑定车队
     * @author 310854
     * @date 2016-5-16
     */
    @JSON
	public String addLeasedServiceDateTream() {
		List<String> ids = getLeasedVehicleVo().getBatchIds();
		//String modifyUser = getCurrentUser().getEmpCode();
		try {
			leasedVehicleService.addLeasedServiceDateTream(ids, getCurrentUser());
			return returnSuccess("外请车(厢式车)绑定车队成功");
		} catch (LeasedVehicleException e) {
			returnError(e.getMessage());
		}
		return returnSuccess();
	}
    
    /**
     * 释放车队
     * @author 310854
     * @date 2016-5-16
     */
    @JSON
	public String deleteLeasedServiceDateTream() {
		List<String> ids = getLeasedVehicleVo().getBatchIds();
		List<String> bindingOgrCodeS = getLeasedVehicleVo().getBindingOgrCodes();
		try {
			leasedVehicleService.deleteLeasedServiceDateTream(ids, getCurrentUser(), bindingOgrCodeS);
			return returnSuccess("外请车(厢式车)释放车队成功");
		} catch (LeasedVehicleException e) {
			returnError(e.getMessage());
		}
		return returnSuccess();
	}
    /**
     * 通过员工工号判断职位
     * @author 307196
     * @date 2017-3-27
     */
    public String judgeTitleByEmpcode(){
    	String empCode=leasedVehicleVo.getEmpCode();
    	String empNum=leasedVehicleVo.getEmpNum();
    	try {
    		String str = leasedVehicleService.queryTitleByEmpCode(empCode,empNum);
        	leasedVehicleVo.setReturnYorN(str);
		} catch (LeasedVehicleException e) {
			returnError(e.getMessage());
		} catch (BusinessException e) {
		    return returnError(e);
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
    private CurrentInfo getCurrentUser(){
    	CurrentInfo user = FossUserContext.getCurrentInfo();
	return  user;
    }
    
    /**
     * @return  the leasedVehicleVo
     */
    public LeasedVehicleVo getLeasedVehicleVo() {
	if (null == leasedVehicleVo) {
	    leasedVehicleVo = new LeasedVehicleVo();
	}
        return leasedVehicleVo;
    }
    
    /**
     * @param leasedVehicleVo the leasedVehicleVo to set
     */
    public void setLeasedVehicleVo(LeasedVehicleVo leasedVehicleVo) {
        this.leasedVehicleVo = leasedVehicleVo;
    }
    
    /**
     * @param leasedVehicleService the leasedVehicleService to set
     */
    public void setLeasedVehicleService(ILeasedVehicleService leasedVehicleService) {
        this.leasedVehicleService = leasedVehicleService;
    }
}
