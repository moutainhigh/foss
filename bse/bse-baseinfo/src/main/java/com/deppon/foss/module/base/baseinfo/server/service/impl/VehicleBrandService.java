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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/service/impl/VehicleBrandService.java
 * 
 * FILE NAME        	: VehicleBrandService.java
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
 * 
 * 
 * 
 * 同步车辆品牌接口
        “车辆品牌”作为车辆信息的一个字段，在FOSS系统中将作为车辆查询条件（下拉框）出现，为了避免FOSS系统遍历车辆信息筛选出所有车辆品牌这种大消耗操作的出现，更为了保持LMS系统和FOSS系统数据的一致性，故将车辆品牌作为基础数据从LMS系统同步至FOSS系统；
        FOSS系统	接口调用者	LMS系统
        前置条件：
        1、	FOSS系统正常运行；
        2、	LMS系统能正常调用本接口；
        
        接口场景：
        1、	LMS系统中的车辆品牌发生更改时（新增、修改、删除），调用此接口，传入车辆品牌和更改类型（新增、修改、删除）；
        2、	若调用成功，FOSS系统返回调用成功的标识；
        
        后置动作：
        1、	LMS系统记录接口调用日志；
        2、	FOSS系统记录接口被调用日志；
        
        接口异常处理：
        接口调用发生异常需要抛出，由LMS系统统一处理异常；
        传入参数信息：
        字段	是否必填	是否列表	备注
        车辆品牌信息	是	是	参见【车辆品牌信息】
        
        车辆品牌信息
        字段	是否必填	是否列表	备注
        品牌编码	是	否	
        车辆品牌名称	是	否	
        车辆种类	是	否	整数类型，1、拖头；2、挂车；3、厢式车
        操作类别(新增、修改、删除
        	是	是	整数类型，1、新增；2、修改；3、删除；
        
        返回参数：
        字段	是否必填	是否列表	备注
        成功总数	是	否	
        失败总数	是	否	
        处理明细	是	是	参见【处理明细】
        
        处理明细
        字段	是否必填	是否列表	备注
        品牌编码	是	否	
        成功或失败的标识	是	否	整数类型，0、失败；1、成功
        失败原因	否	否	如果处理失败，此字段为必填
        
        调用时效：立即
        交互模式：请求/回调
        是否需要顺序执行：否
        是否批量处理：是
        是否允许重复调用：否
        调用时段和调用量：00：00~24：00，一天调用1次，一天传送10条车辆品牌信息。
        高峰时段：08:00-12:00，13:30-17:30
        消息大小 ：峰值情况一次10条左右
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.module.base.baseinfo.api.server.dao.IVehicleBrandDao;
import com.deppon.foss.module.base.baseinfo.api.server.service.IVehicleBrandService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.VehicleBrandEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.VehicleBrandException;
import com.deppon.foss.util.define.FossConstants;
/**
 * 用来操作交互“车辆品牌”的数据库对应数据访问Service接口实现类：无SUC,由LMS调用同步
 * 
 * 同步车辆品牌接口
        “车辆品牌”作为车辆信息的一个字段，在FOSS系统中将作为车辆查询条件（下拉框）出现，为了避免FOSS系统遍历车辆信息筛选出所有车辆品牌这种大消耗操作的出现，更为了保持LMS系统和FOSS系统数据的一致性，故将车辆品牌作为基础数据从LMS系统同步至FOSS系统；
        FOSS系统	接口调用者	LMS系统
        前置条件：
        1、	FOSS系统正常运行；
        2、	LMS系统能正常调用本接口；
        
        接口场景：
        1、	LMS系统中的车辆品牌发生更改时（新增、修改、删除），调用此接口，传入车辆品牌和更改类型（新增、修改、删除）；
        2、	若调用成功，FOSS系统返回调用成功的标识；
        
        后置动作：
        1、	LMS系统记录接口调用日志；
        2、	FOSS系统记录接口被调用日志；
        
        接口异常处理：
        接口调用发生异常需要抛出，由LMS系统统一处理异常；
        
 * <p style="display:none">modifyRecord</p>
 * 
 * <p style="display:none">version:V1.0,author:100847-foss-GaoPeng,date:2012-11-6 上午9:36:24</p>
 * @author 100847-foss-GaoPeng
 * 
 * @date 2012-11-6 上午9:36:24
 * 
 * @since
 * 
 * @version
 */
public class VehicleBrandService implements IVehicleBrandService {
    
    /**
     * 日志.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(VehicleBrandService.class);
    
    
    /**
     * "车辆品牌"DAO接口.
     */
    private IVehicleBrandDao vehicleBrandDao;

    /**
     * <p>新增一个“车辆品牌”实体入库（忽略实体中是否存在空值）</p>
     * 同步车辆品牌接口
     * “车辆品牌”作为车辆信息的一个字段，在FOSS系统中将作为车辆查询条件（下拉框）出现，为了避免FOSS系统遍历车辆信息筛选出所有车辆品牌这种大消耗操作的出现，更为了保持LMS系统和FOSS系统数据的一致性，故将车辆品牌作为基础数据从LMS系统同步至FOSS系统；
     * FOSS系统	接口调用者	LMS系统
     * 
     * 前置条件：
     * 1、	FOSS系统正常运行；
     * 
     * 2、	LMS系统能正常调用本接口；
     * 
     * 接口场景：
     * 1、	LMS系统中的车辆品牌发生更改时（新增、修改、删除），调用此接口，传入车辆品牌和更改类型（新增、修改、删除）；
     * 2、	若调用成功，FOSS系统返回调用成功的标识；
     * 
     * 
     * 
     * 后置动作：
     * 1、	LMS系统记录接口调用日志；
     * 2、	FOSS系统记录接口被调用日志；
     * 
     * 接口异常处理：
     * 
     * 接口调用发生异常需要抛出，由LMS系统统一处理异常；.
     *
     * @param vehicleBrand “车辆品牌”实体
     * 
     * @param createUser 创建人
     * 
     * @param ignoreNull true：忽略空值，false：包含空值
     * 
     * @return 1：成功；-1：失败
     * 
     * @throws VehicleBrandException 
     * 
     * @author 100847-foss-GaoPeng
     * 
     * @date 2012-10-16 下午2:33:09
     * 
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.IVehicleBrandService#addVehicleBrand(com.deppon.foss.module.base.baseinfo.api.shared.domain.VehicleBrandEntity)
     */
    @Override
    @Transactional
    public int addVehicleBrand(VehicleBrandEntity vehicleBrand,
	    String createUser, boolean ignoreNull) throws VehicleBrandException {
	//参数判断
	if (null == vehicleBrand) {
	    //异常处理
	    throw new VehicleBrandException("", "车辆品牌信息为空");
	}
	
	//验证“车辆品牌”实体是否已经存在
	VehicleBrandEntity vehicleBrandParameter = new VehicleBrandEntity();
	//设置 品牌编码.
	vehicleBrandParameter.setBrandCode(vehicleBrand.getBrandCode());
	//根据“车辆品牌”记录唯一标识查询出一条“车辆品牌”记录
	VehicleBrandEntity tempVehicleBrand = vehicleBrandDao.queryVehicleBrandBySelective(vehicleBrandParameter);
	//参数判断
	if(tempVehicleBrand != null){
	    //异常处理
	    throw new VehicleBrandException("", "车辆品牌信息已经存在");
	}else{
	    //设置创建用户
	    vehicleBrand.setCreateUser(createUser);
	    //判断
	    if (ignoreNull) {
		//新增一个“车辆品牌”实体入库 （只选择实体中非空值）
		vehicleBrandDao.addVehicleBrandBySelective(vehicleBrand);
	    }else{
		//新增一个“车辆品牌”实体入库（忽略实体中是否存在空值）
		vehicleBrandDao.addVehicleBrand(vehicleBrand);
	    }
	}
	return FossConstants.SUCCESS;
    }

    /**
     * <p>根据“车辆品牌”记录唯一标识作废（逻辑删除）一条“车辆品牌”记录</p>
     * 
     * 同步车辆品牌接口
     * 
     * “车辆品牌”作为车辆信息的一个字段，在FOSS系统中将作为车辆查询条件（下拉框）出现，为了避免FOSS系统遍历车辆信息筛选出所有车辆品牌这种大消耗操作的出现，更为了保持LMS系统和FOSS系统数据的一致性，故将车辆品牌作为基础数据从LMS系统同步至FOSS系统；
     * 
     * FOSS系统	接口调用者	LMS系统
     * 前置条件：
     * 1、	FOSS系统正常运行；
     * 2、	LMS系统能正常调用本接口；
     * 
     * 接口场景：
     * 1、	LMS系统中的车辆品牌发生更改时（新增、修改、删除），调用此接口，传入车辆品牌和更改类型（新增、修改、删除）；
     * 2、	若调用成功，FOSS系统返回调用成功的标识；
     * 
     * 后置动作：
     * 1、	LMS系统记录接口调用日志；
     * 2、	FOSS系统记录接口被调用日志；
     * 
     * 接口异常处理：
     * 接口调用发生异常需要抛出，由LMS系统统一处理异常；.
     *
     * @param ids 记录唯一标识集合
     * 
     * @param modifyUser 修改人
     * 
     * @return 1：成功；-1：失败
     * 
     * @throws VehicleBrandException 
     * 
     * @author 100847-foss-GaoPeng
     * 
     * @date 2012-10-16 下午2:33:12
     * 
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.IVehicleBrandService#deleteVehicleBrand(com.deppon.foss.module.base.baseinfo.api.shared.domain.VehicleBrandEntity)
     */
    @Override
    @Transactional
    public int deleteVehicleBrand(List<String> ids, String modifyUser) throws VehicleBrandException {
	//参数判断
	if(StringUtils.isBlank(modifyUser)){
	    //异常处理
	    throw new VehicleBrandException("", "修改人信息为空");
	}
	//迭代循环
	for (String id : ids) {
	    //参数判断
	    if (StringUtils.isBlank(id)) {
		//异常处理
		throw new VehicleBrandException("", "ID为空");
	    }else{
		//记录日志
		if(LOGGER.isDebugEnabled()){
		    LOGGER.debug(" *******  ******* 调试：作废车辆品牌的ID检测通过 *******  ******* ");
		}
	    }
	    //验证“车辆品牌”实体是否已经存在
	    VehicleBrandEntity vehicleBrandParameter = new VehicleBrandEntity();
	    //设置ＩＤ
	    vehicleBrandParameter.setId(id);
	    //根据“车辆品牌”记录唯一标识查询出一条“车辆品牌”记录
	    VehicleBrandEntity vehicleBrand = vehicleBrandDao.queryVehicleBrandBySelective(vehicleBrandParameter);
	    //参数判断
	    if(vehicleBrand == null){
		//异常信息处理
		throw new VehicleBrandException("", "车辆品牌信息不存在");
	    }else{
		//设置状态
		vehicleBrand.setActive(FossConstants.INACTIVE);
	    }
	    //修改一个“车辆品牌”实体入库 （只选择实体中非空值）
	    vehicleBrandDao.updateVehicleBrandBySelective(vehicleBrand);
	}
	return FossConstants.SUCCESS;
    }

    /**
     * <p>修改一个“车辆品牌”实体入库（忽略实体中是否存在空值）</p>
     * 
     * 同步车辆品牌接口
     * “车辆品牌”作为车辆信息的一个字段，在FOSS系统中将作为车辆查询条件（下拉框）出现，为了避免FOSS系统遍历车辆信息筛选出所有车辆品牌这种大消耗操作的出现，更为了保持LMS系统和FOSS系统数据的一致性，故将车辆品牌作为基础数据从LMS系统同步至FOSS系统；
     * FOSS系统	接口调用者	LMS系统
     * 前置条件：
     * 1、	FOSS系统正常运行；
     * 2、	LMS系统能正常调用本接口；
     * 
     * 接口场景：
     * 1、	LMS系统中的车辆品牌发生更改时（新增、修改、删除），调用此接口，传入车辆品牌和更改类型（新增、修改、删除）；
     * 2、	若调用成功，FOSS系统返回调用成功的标识；
     * 
     * 后置动作：
     * 1、	LMS系统记录接口调用日志；
     * 2、	FOSS系统记录接口被调用日志；
     * 
     * 接口异常处理：
     * 接口调用发生异常需要抛出，由LMS系统统一处理异常；.
     *
     * @param vehicleBrand “车辆品牌”实体
     * 
     * @param modifyUser 修改人
     * 
     * @param ignoreNull true：忽略空值，false：包含空值
     * 
     * @return 1：成功；0：失败
     * 
     * @throws VehicleBrandException 
     * 
     * @author 100847-foss-GaoPeng
     * 
     * @date 2012-10-16 下午2:33:15
     * 
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.IVehicleBrandService#updateVehicleBrand(com.deppon.foss.module.base.baseinfo.api.shared.domain.VehicleBrandEntity)
     */
    @Override
    @Transactional
    public int updateVehicleBrand(VehicleBrandEntity vehicleBrand,String modifyUser, boolean ignoreNull) throws VehicleBrandException {
	//参数判断
	if (null == vehicleBrand) {
	    //异常信息处理
	    throw new VehicleBrandException("", "车辆品牌为空");
	}
	//参数判断
	if (StringUtils.isBlank(vehicleBrand.getId())) {
	  //异常信息处理
	    throw new VehicleBrandException("", "ID为空");
	}
	//参数判断
	if (StringUtils.isBlank(vehicleBrand.getBrandCode())) {
	  //异常信息处理
	    throw new VehicleBrandException("", "车辆品牌编码为空");
	}
	
	//验证“车辆品牌”实体是否已经存在
	VehicleBrandEntity vehicleBrandParameter = new VehicleBrandEntity();
	//设置 品牌编码
	vehicleBrandParameter.setBrandCode(vehicleBrand.getBrandCode());
	//根据“车辆品牌”记录唯一标识查询出一条“车辆品牌”记录
	VehicleBrandEntity tempVehicleBrand = vehicleBrandDao.queryVehicleBrandBySelective(vehicleBrandParameter);
	//参数判断
	if(tempVehicleBrand != null){
	  //异常信息处理
	    throw new VehicleBrandException("", "车辆品牌信息已经存在");
	}else{
	    //设置修改人
	    vehicleBrand.setModifyUser(modifyUser); 
	    if (ignoreNull) {
		//修改一个“车辆品牌”实体入库 （只选择实体中非空值）
		vehicleBrandDao.updateVehicleBrandBySelective(vehicleBrand);
	    }else{
		//修改一个“车辆品牌”实体入库（忽略实体中是否存在空值）
		vehicleBrandDao.updateVehicleBrand(vehicleBrand);
	    }
	}
	return FossConstants.SUCCESS;
    }
    
    /**
     * <p>根据“车辆品牌”编码查询出一条“车辆品牌”记录</p>
     * 
     * 
     * 同步车辆品牌接口
     * “车辆品牌”作为车辆信息的一个字段，在FOSS系统中将作为车辆查询条件（下拉框）出现，为了避免FOSS系统遍历车辆信息筛选出所有车辆品牌这种大消耗操作的出现，更为了保持LMS系统和FOSS系统数据的一致性，故将车辆品牌作为基础数据从LMS系统同步至FOSS系统；
     * FOSS系统	接口调用者	LMS系统
     * 前置条件：
     * 1、	FOSS系统正常运行；
     * 2、	LMS系统能正常调用本接口；
     * 
     * 接口场景：
     * 1、	LMS系统中的车辆品牌发生更改时（新增、修改、删除），调用此接口，传入车辆品牌和更改类型（新增、修改、删除）；
     * 2、	若调用成功，FOSS系统返回调用成功的标识；
     * 
     * 后置动作：
     * 1、	LMS系统记录接口调用日志；
     * 2、	FOSS系统记录接口被调用日志；
     * 
     * 接口异常处理：
     * 接口调用发生异常需要抛出，由LMS系统统一处理异常；.
     *
     * @param brandCode 品牌编码
     * 
     * @return “车辆品牌”实体
     * 
     * @throws VehicleBrandException 
     * 
     * @author 100847-foss-GaoPeng
     * 
     * @date 2012-11-6 上午10:11:17
     * 
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.IVehicleBrandService#queryVehicleBrandByBrandCode(java.lang.String)
     */
    @Override
    @Transactional(readOnly = true)
    public VehicleBrandEntity queryVehicleBrandByBrandCode(String brandCode) throws VehicleBrandException{
	//参数判断
	if (StringUtils.isBlank(brandCode)) {
	    //异常信息处理
	    throw new VehicleBrandException("", "品牌编码为空");
	}
	//创建实体
	VehicleBrandEntity vehicleBrand = new VehicleBrandEntity();
	//设置 品牌编码.
	vehicleBrand.setBrandCode(brandCode);
	//根据“车辆品牌”记录唯一标识查询出一条“车辆品牌”记录
        return vehicleBrandDao.queryVehicleBrandBySelective(vehicleBrand);
    }

    /**
     * <p>根据条件有选择的检索出符合条件的“车辆品牌”实体列表（条件做自动判断，只选择实体中非空值）</p>
     * 
     * 同步车辆品牌接口
     * “车辆品牌”作为车辆信息的一个字段，在FOSS系统中将作为车辆查询条件（下拉框）出现，为了避免FOSS系统遍历车辆信息筛选出所有车辆品牌这种大消耗操作的出现，更为了保持LMS系统和FOSS系统数据的一致性，故将车辆品牌作为基础数据从LMS系统同步至FOSS系统；
     * FOSS系统	接口调用者	LMS系统
     * 前置条件：
     * 1、	FOSS系统正常运行；  
     * 
     * 2、	LMS系统能正常调用本接口；
     * 
     * 接口场景：
     * 1、	LMS系统中的车辆品牌发生更改时（新增、修改、删除），调用此接口，传入车辆品牌和更改类型（新增、修改、删除）；
     * 
     * 2、	若调用成功，FOSS系统返回调用成功的标识；
     * 
     * 后置动作：
     * 1、	LMS系统记录接口调用日志；
     * 
     * 2、	FOSS系统记录接口被调用日志；
     * 
     * 接口异常处理：
     * 接口调用发生异常需要抛出，由LMS系统统一处理异常；.
     *
     * @param vehicleBrand 以“车辆品牌”实体承载的条件参数实体
     * 
     * @param offset 开始记录数
     * 
     * @param limit 限制记录数
     * 
     * @return 符合条件的“车辆品牌”实体列表
     * 
     * @throws VehicleBrandException 
     * 
     * @author 100847-foss-GaoPeng
     * 
     * @date 2012-11-6 上午9:34:59
     * 
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.IVehicleBrandService#queryVehicleBrandBySelectiveCondition(com.deppon.foss.module.base.baseinfo.api.shared.domain.VehicleBrandEntity, int, int)
     */
    @Override
    @Transactional(readOnly = true)
    public List<VehicleBrandEntity> queryVehicleBrandBySelectiveCondition(
	    VehicleBrandEntity vehicleBrand, int offset, int limit) throws VehicleBrandException {
	//参数判断
	if (offset < 0 || limit < 0) {
	    //返回空的集合
	    return new ArrayList<VehicleBrandEntity>();
	}
	if(null == vehicleBrand){
	    //定义一个对象
	    vehicleBrand = new VehicleBrandEntity();
	}
	//定义一个集合
	List<VehicleBrandEntity> vehicleBrandList;
	//根据条件（分页模糊）有选择的检索出符合条件的“车辆品牌”实体列表
	//（条件做自动判断，只选择实体中非空值）
	vehicleBrandList = vehicleBrandDao.queryVehicleBrandBySelectiveCondition(vehicleBrand, offset, limit);
	return vehicleBrandList;
    }

    /**
     * 设置 "车辆品牌"DAO接口.
     *
     * @param vehicleBrandDao the vehicleBrandDao to set
     */
    public void setVehicleBrandDao(IVehicleBrandDao vehicleBrandDao) {
        this.vehicleBrandDao = vehicleBrandDao;
    }
}
