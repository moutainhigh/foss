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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/service/impl/VehicleUnavilableService.java
 * 
 * FILE NAME        	: VehicleUnavilableService.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 * 
 * 
 * 
 * 
 * 同步停车计划接口
        LMS系统负责维护车辆信息，车辆因为各种原因，例如年审、维修等需要停止使用一段时间，故此时LMS系统需要通知FOSS系统该车辆的停车开始时间和停车结束时间及停车原因代码；
        FOSS系统	接口调用者	LMS系统
        前置条件：
        1、FOSS系统正常运行；
        2、LMS系统能正常调用本接口；
        
        接口场景：
        1、	LMS系统中停车计划发生更改时，则调用此接口，传入停车计划信息；
        2、	若调用成功，FOSS系统返回调用成功的标识；
        
        后置动作：
        1、	LMS系统记录接口调用日志；
        2、	FOSS系统记录接口被调用日志；
        
        接口异常处理：
        接口调用发生异常需要抛出，由LMS系统统一处理异常；
        传入参数信息：
        字段	是否必填	是否列表	备注
        停车计划信息	是	是	参见【停车计划信息】
        
        停车原因代码信息
        字段	是否必填	是否列表	备注
        唯一标识符	是	否	
        车牌号	是	否	
        开始时间	是	否	
        结束时间	是	否	
        原因代码	是	否	关联停车原因代码
        
        返回参数：
        字段	是否必填	是否列表	备注
        成功总数	是	否	
        失败总数	是	否	
        处理明细	是	是	参见【处理明细】
        
        处理明细
        字段	是否必填	是否列表	备注
        车牌号	是	否	
        成功或失败的标识	是	否	整数类型，0、失败；1、成功
        失败原因	否	否	如果处理失败，此字段为必填
        
        调用时效：立即
        交互模式：请求/回调
        是否需要顺序执行：否
        是否批量处理：是
        是否允许重复调用：否
        调用时段和调用量：00：00~24：00，一天调用10次，一天传送500条停车计划信息。
        高峰时段：08:00-12:00，13:30-17:30
        消息大小 ：峰值情况一次50条左右
        时间响应要求：N/A
        安全性要求：无

 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
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
package com.deppon.foss.module.base.baseinfo.server.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.module.base.baseinfo.api.server.dao.IVehicleUnavilableDao;
import com.deppon.foss.module.base.baseinfo.api.server.service.IVehicleUnavilableService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.VehicleUnavilableEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.VehicleUnavilableException;
import com.deppon.foss.util.define.FossConstants;
/**
 * 用来操作交互“公司车停车计划”的数据库对应数据访问Service接口实现类：无SUC
 * 
 * 同步停车计划接口
        LMS系统负责维护车辆信息，车辆因为各种原因，例如年审、维修等需要停止使用一段时间，故此时LMS系统需要通知FOSS系统该车辆的停车开始时间和停车结束时间及停车原因代码；
        FOSS系统	接口调用者	LMS系统
        前置条件：
        1、FOSS系统正常运行；
        2、LMS系统能正常调用本接口；
        
        接口场景：
        1、	LMS系统中停车计划发生更改时，则调用此接口，传入停车计划信息；
        2、	若调用成功，FOSS系统返回调用成功的标识；
        
        后置动作：
        1、	LMS系统记录接口调用日志；
        2、	FOSS系统记录接口被调用日志；
 * 
 * <p style="display:none">modifyRecord</p>
 * 
 * <p style="display:none">version:V1.0,author:100847-foss-GaoPeng,date:2012-11-3 上午11:30:56</p>
 * 
 * @author 100847-foss-GaoPeng
 * 
 * @date 2012-11-3 上午11:30:56
 * 
 * @since
 * 
 * @version
 */
public class VehicleUnavilableService implements IVehicleUnavilableService {

    
    /**
     * "公司车停车计划"DAO.
     */
    private IVehicleUnavilableDao vehicleUnavilableDao;

    /**
     * <p>新增一个“公司车停车计划”实体入库（忽略实体中是否存在空值）</p>.
     *
     *同步停车计划接口
                LMS系统负责维护车辆信息，车辆因为各种原因，例如年审、维修等需要停止使用一段时间，故此时LMS系统需要通知FOSS系统该车辆的停车开始时间和停车结束时间及停车原因代码；
                FOSS系统	接口调用者	LMS系统
                前置条件：
                1、FOSS系统正常运行；
                2、LMS系统能正常调用本接口；
                
                接口场景：
                1、	LMS系统中停车计划发生更改时，则调用此接口，传入停车计划信息；
                2、	若调用成功，FOSS系统返回调用成功的标识；
                
                后置动作：
                1、	LMS系统记录接口调用日志；
                2、	FOSS系统记录接口被调用日志；
     * @param vehicleUnavilable “公司车停车计划”实体
     * 
     * @param createUser 创建人
     * 
     * @param ignoreNull true：忽略空值，false：包含空值
     * 
     * @return 1：成功；0：失败
     * 
     * @throws VehicleUnavilableException 
     * 
     * @author 100847-foss-GaoPeng
     * 
     * @date 2012-10-20 上午9:57:46
     * 
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.IVehicleUnavilableService#addVehicleUnavilable(com.deppon.foss.module.base.baseinfo.api.shared.domain.VehicleUnavilableEntity, java.lang.String, boolean)
     */
    @Override
    @Transactional
    public int addVehicleUnavilable(VehicleUnavilableEntity vehicleUnavilable,
	    String createUser, boolean ignoreNull)
	    throws VehicleUnavilableException {
	//公司车停车计划信息验证
	if (null == vehicleUnavilable) {
	    throw new VehicleUnavilableException("", "公司车停车计划信息为空");
	}
	//创建人信息
	if (StringUtils.isBlank(createUser)) {
	    throw new VehicleUnavilableException("", "创建人信息为空");
	}
	//设置创建用户
	vehicleUnavilable.setCreateUser(createUser);
	//逻辑判断
	if (ignoreNull) {
	    //新增一个“公司车停车计划”实体入库 （只选择实体中非空值）
	    vehicleUnavilableDao.addVehicleUnavilableBySelective(vehicleUnavilable);
	}else{
	    //新增一个“公司车停车计划”实体入库（忽略实体中是否存在空值）
	    vehicleUnavilableDao.addVehicleUnavilable(vehicleUnavilable);
	}
	return FossConstants.SUCCESS;
    }

    /**
     * <p>根据“公司车停车计划”记录唯一标识作废（物理删除）一条“公司车停车计划”记录</p>.
     *	同步停车计划接口
        LMS系统负责维护车辆信息，车辆因为各种原因，例如年审、维修等需要停止使用一段时间，故此时LMS系统需要通知FOSS系统该车辆的停车开始时间和停车结束时间及停车原因代码；
        FOSS系统	接口调用者	LMS系统
                        前置条件：
                        1、FOSS系统正常运行；
                        2、LMS系统能正常调用本接口；
                        
                        接口场景：
                        1、	LMS系统中停车计划发生更改时，则调用此接口，传入停车计划信息；
                        2、	若调用成功，FOSS系统返回调用成功的标识；
                        
                        后置动作：
                        1、	LMS系统记录接口调用日志；
                        2、	FOSS系统记录接口被调用日志；
     * @param id 记录唯一标识
     * 
     * @param modifyUser 修改人
     * 
     * @return 1：成功；0：失败
     * 
     * @throws VehicleUnavilableException 
     * 
     * @author 100847-foss-GaoPeng
     * 
     * @date 2012-10-20 上午9:57:17
     * 
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.IVehicleUnavilableService#deleteVehicleUnavilable(java.lang.String, java.lang.String)
     */
    @Override
    @Transactional
    public int deleteVehicleUnavilable(String id, String modifyUser)
	    throws VehicleUnavilableException {
	//参数验证 ID 
	if (StringUtils.isBlank(id)) {
	    throw new VehicleUnavilableException("", "ID 为空");
	}
	//参数验证 公司车停车计划信息
	if (StringUtils.isBlank(modifyUser)) {
	    throw new VehicleUnavilableException("", "公司车停车计划信息为空");
	}
	
	//创建一个实体
	VehicleUnavilableEntity vehicleUnavilable = new VehicleUnavilableEntity();
	//设置ＩＤ
	vehicleUnavilable.setId(id);
	//设置修改人
	vehicleUnavilable.setModifyUser(modifyUser);
	//设置状态
	vehicleUnavilable.setActive(FossConstants.INACTIVE);
	//修改一个“公司车停车计划”实体入库 （只选择实体中非空值）
	vehicleUnavilableDao.updateVehicleUnavilableBySelective(vehicleUnavilable);
	return FossConstants.SUCCESS;
    }

    /**
     * <p>修改一个“公司车停车计划”实体入库（忽略实体中是否存在空值）</p>.
     *		同步停车计划接口
        LMS系统负责维护车辆信息，车辆因为各种原因，例如年审、维修等需要停止使用一段时间，故此时LMS系统需要通知FOSS系统该车辆的停车开始时间和停车结束时间及停车原因代码；
        FOSS系统	接口调用者	LMS系统
                        前置条件：
                        1、FOSS系统正常运行；
                        2、LMS系统能正常调用本接口；
                        
                        接口场景：
                        1、	LMS系统中停车计划发生更改时，则调用此接口，传入停车计划信息；
                        2、	若调用成功，FOSS系统返回调用成功的标识；
                        
                        后置动作：
                        1、	LMS系统记录接口调用日志；
                        2、	FOSS系统记录接口被调用日志；
     * @param vehicleUnavilable “公司车停车计划”实体
     * 
     * @param modifyUser 修改人
     * 
     * @param ignoreNull true：忽略空值，false：包含空值
     * 
     * @return 1：成功；0：失败
     * 
     * @throws VehicleUnavilableException 
     * 
     * @author 100847-foss-GaoPeng
     * 
     * @date 2012-10-20 上午9:58:49
     * 
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.IVehicleUnavilableService#updateVehicleUnavilable(com.deppon.foss.module.base.baseinfo.api.shared.domain.VehicleUnavilableEntity, java.lang.String, boolean)
     */
    @Override
    @Transactional
    public int updateVehicleUnavilable(
	    VehicleUnavilableEntity vehicleUnavilable, String modifyUser,
	    boolean ignoreNull) throws VehicleUnavilableException {
	//公司车停车计划信息
	if (null == vehicleUnavilable) {
	    throw new VehicleUnavilableException("", "公司车停车计划信息为空");
	}
	//ID 为空
	if (StringUtils.isBlank(vehicleUnavilable.getId())) {
	    throw new VehicleUnavilableException("", "ID 为空");
	}
	//修改人信息为空
	if (StringUtils.isBlank(modifyUser)) {
	    throw new VehicleUnavilableException("", "修改人信息为空");
	}
	//设置修改人
	vehicleUnavilable.setModifyUser(modifyUser);
	//判断
	if (ignoreNull) {
	    //修改一个“公司车停车计划”实体入库 （只选择实体中非空值）
	    vehicleUnavilableDao.updateVehicleUnavilableBySelective(vehicleUnavilable);
	}else{
	    //修改一个“公司车停车计划”实体入库（忽略实体中是否存在空值）
	    vehicleUnavilableDao.updateVehicleUnavilable(vehicleUnavilable);
	}
	
	return FossConstants.SUCCESS;
    }

    /**
     * <p>根据“公司车停车计划”记录唯一标识查询出一条“公司车停车计划”记录</p>.
     *	同步停车计划接口
        LMS系统负责维护车辆信息，车辆因为各种原因，例如年审、维修等需要停止使用一段时间，故此时LMS系统需要通知FOSS系统该车辆的停车开始时间和停车结束时间及停车原因代码；
        FOSS系统	接口调用者	LMS系统
                        前置条件：
                        1、FOSS系统正常运行；
                        2、LMS系统能正常调用本接口；
                        
                        接口场景：
                        1、	LMS系统中停车计划发生更改时，则调用此接口，传入停车计划信息；
                        2、	若调用成功，FOSS系统返回调用成功的标识；
                        
                        后置动作：
                        1、	LMS系统记录接口调用日志；
                        2、	FOSS系统记录接口被调用日志；
                        
     * @param id 记录唯一标识
     * 
     * @return “公司车停车计划”实体
     * 
     * @throws VehicleUnavilableException 
     * 
     * @author 100847-foss-GaoPeng
     * 
     * @date 2012-10-20 上午9:58:01
     * 
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.IVehicleUnavilableService#queryVehicleUnavilable(java.lang.String)
     */
    @Override
    @Transactional(readOnly = true)
    public VehicleUnavilableEntity queryVehicleUnavilable(String id)
	    throws VehicleUnavilableException {
	//参数验证
	if (StringUtils.isBlank(id)) {
	    throw new VehicleUnavilableException("", "ID 为空");
	}
	//定义实体
	VehicleUnavilableEntity vehicleUnavilable = null;
	//根据“公司车停车计划”记录唯一标识查询出一条“公司车停车计划”记录
	vehicleUnavilable = vehicleUnavilableDao.queryVehicleUnavilable(id); 
	//根据“公司车停车计划”记录唯一标识查询出一条“公司车停车计划”记录
	return vehicleUnavilable;
    }

    /**
     * <p>根据条件有选择的检索出符合条件的“外请车厢式车”实体列表（条件做自动判断，只选择实体中非空值）</p>.
     *	同步停车计划接口
        LMS系统负责维护车辆信息，车辆因为各种原因，例如年审、维修等需要停止使用一段时间，故此时LMS系统需要通知FOSS系统该车辆的停车开始时间和停车结束时间及停车原因代码；
        FOSS系统	接口调用者	LMS系统
                        前置条件：
                        1、FOSS系统正常运行；
                        2、LMS系统能正常调用本接口；
                        
                        接口场景：
                        1、	LMS系统中停车计划发生更改时，则调用此接口，传入停车计划信息；
                        2、	若调用成功，FOSS系统返回调用成功的标识；
                        
                        后置动作：
                        1、	LMS系统记录接口调用日志；
                        2、	FOSS系统记录接口被调用日志；
     * @param vehicleUnavilable 以“外请车厢式车”实体承载的条件参数实体
     * 
     * @param offset 开始记录数
     * 
     * @param limit 限制记录数
     * 
     * @return 符合条件的“外请车厢式车”实体列表
     * 
     * @throws VehicleUnavilableException 
     * 
     * @author 100847-foss-GaoPeng
     * 
     * @date 2012-10-20 上午9:58:43
     * 
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.IVehicleUnavilableService#queryVehicleUnavilableListBySelectiveCondition(com.deppon.foss.module.base.baseinfo.api.shared.domain.VehicleUnavilableEntity, int, int)
     */
    @Override
    @Transactional(readOnly = true)
    public List<VehicleUnavilableEntity> queryVehicleUnavilableListBySelectiveCondition(
	    VehicleUnavilableEntity vehicleUnavilable, int offset, int limit)
	    throws VehicleUnavilableException {
	//参数验证
	if (offset < 0 || limit < 0) {
	    //返回空的集合
	    return new ArrayList<VehicleUnavilableEntity>();
	}
	//参数验证
	if(null == vehicleUnavilable){
	    //创建实体
	    vehicleUnavilable = new VehicleUnavilableEntity();
	}
	//定义集合
	List<VehicleUnavilableEntity> vehicleUnavilableList = null;
	//根据条件有选择的检索出符合条件的“外请车厢式车”实体列表（条件做自动判断，只选择实体中非空值）
	vehicleUnavilableList = vehicleUnavilableDao.queryVehicleUnavilableListBySelectiveCondition(vehicleUnavilable, offset, limit);
	//返回符合条件的集合
	return vehicleUnavilableList; 
    }
    
    /**
     * 设置 "公司车停车计划"DAO.
     *
     * @param vehicleUnavilableDao the vehicleUnavilableDao to set
     */
    public void setVehicleUnavilableDao(IVehicleUnavilableDao vehicleUnavilableDao) {
        this.vehicleUnavilableDao = vehicleUnavilableDao;
    }
}
