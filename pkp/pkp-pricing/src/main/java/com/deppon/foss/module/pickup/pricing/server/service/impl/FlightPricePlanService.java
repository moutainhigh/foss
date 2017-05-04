/**
 *  initial comments.
 *  
 *  	空运底价方案信息：
 *  
 *  1、	运价编号-航空公司下发的运价编号
 *  
 *  
 *  2、	配载部门-空运总调信息通过点击右边的“选择”按钮弹出配载部门界面
 *  
 *  
 *  3、	出发机场-根据航班号显示的某个出发城市机场，自动带出不可编辑。
 *  
 *  
 *  4、	航空公司-包含所有与德班业务往来的航空公司,根据录入的航班号默认自动带出不可修改
 *  
 *  
 *  5、	起飞时间 - 根据航班号显示，不可修改
 *  
 *  
 *  6、	到达时间-  根据航班号显示，不可修改
 *  
 *  
 *  7、	生效时间-当前配置信息开始时间
 *  
 *  
 *  8、	截止时间-当前配置信息结束时间
 *  
 *  
 *  9、	最低费用-当重量 * X费率 = X运费价格费率<最低费用时就用此处最低费用来代替目的为了控制成本价格
 *  
 *  
 *  10、	是否激活-选择是否激活可让管理员抉择是否立即激活本次维护的配置信息，默认为“否”
 *  
 *  
 *  11、	航班运费-默认显示按照重量标准费率表格支持增、删、改、导入、导出操作来管理运价费率。
 *  
 *  
 *  12、	备注描述： 备注信息
 *  
 *  
 *  	空运底价方案明细信息：
 *  
 *  
 *  	1.	目的地-到达机场所在城市
 *  
 *  
 *  	2.	货物类别-根据不同的航空公司显示不同货物类别提供选择录入
 *  
 *  
 *  	3.	航班号-航空公司规划的航班号
 *  
 *  
 *  	4.	最低一票-航空公司给出的最低一票
 *  
 *  
 *  	5.	45-3000kg在45-3000公斤固定范围内给予不同费率点设置（其规则一样不一一列举）
 */
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
 * PROJECT NAME	: pkp-pricing
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/pricing/server/service/impl/FlightPricePlanService.java
 * 
 * FILE NAME        	: FlightPricePlanService.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.pricing.server.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.server.service.IAirlinesService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IEmployeeService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.AdministrativeRegionsEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.AirlinesEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.EmployeeEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.pickup.pricing.api.server.dao.IFlightPricePlanDao;
import com.deppon.foss.module.pickup.pricing.api.server.dao.IFlightPricePlanDetailDao;
import com.deppon.foss.module.pickup.pricing.api.server.service.IFlightPricePlanService;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.FlightPricePlanDetailEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.FlightPricePlanEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.GoodsTypeEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.FlightDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.FlightPriceDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.FlightPricePlanDto;
import com.deppon.foss.module.pickup.pricing.api.shared.exception.FlightPricePlanException;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;
import com.google.inject.Inject;

/**
 * 
 * 航空公司运价方案主信息维护
 * @author DP-Foss-YueHongJie
 * @date 2012-10-31 下午3:32:27
 */
public class FlightPricePlanService implements IFlightPricePlanService {
    /**
     * 航空代理方案运价dao
     */
	@Inject
    IFlightPricePlanDao  flightPricePlanDao;
    /**
     * 航空代理方案明细运价dao
     */
	@Inject
    IFlightPricePlanDetailDao  flightPricePlanDetailDao;
	/**
     * 员工SERVICE
     */
	@Inject
    IEmployeeService employeeService;
	/**
     * 航空线路SERVICE
     */
	@Inject
	IAirlinesService airlinesService;
	/**
     * 区域组织SERVICE
     */
	@Inject
	IOrgAdministrativeInfoService orgAdministrativeInfoService;
    /**
     * 设置 航空线路SERVICE.
     *
     * @param airlinesService the new 航空线路SERVICE
     */
    public void setAirlinesService(IAirlinesService airlinesService) {
        this.airlinesService = airlinesService;
    }
    /**
     * 设置 员工SERVICE.
     *
     * @param employeeService the new 员工SERVICE
     */
    public void setEmployeeService(IEmployeeService employeeService) {
        this.employeeService = employeeService;
    }
    /**
     * 设置 航空代理方案运价dao.
     *
     * @param flightPricePlanDao the new 航空代理方案运价dao
     */
    public void setFlightPricePlanDao(IFlightPricePlanDao flightPricePlanDao) throws FlightPricePlanException{
        this.flightPricePlanDao = flightPricePlanDao;
    }
    /**
     * 设置 航空代理方案明细运价dao.
     *
     * @param flightPricePlanDetailDao the new 航空代理方案明细运价dao
     */
    public void setFlightPricePlanDetailDao(
	    	IFlightPricePlanDetailDao flightPricePlanDetailDao) {
	        this.flightPricePlanDetailDao = flightPricePlanDetailDao;
    }
	/**
	 * 设置 区域组织SERVICE.
	 *
	 * @param orgAdministrativeInfoService the new 区域组织SERVICE
	 */
	public void setOrgAdministrativeInfoService(IOrgAdministrativeInfoService orgAdministrativeInfoService) {
		this.orgAdministrativeInfoService = orgAdministrativeInfoService;
	}
    /**
     * 
     * <p>添加航空公司运价方案</p> 
     * 
     * @author DP-Foss-YueHongJie
     * 
     * @date 2012-12-25 下午4:02:46
     * 
     * @param entity
     * 
     * @return
     * 
     * @throws FlightPricePlanException 
     * 
     * @see com.deppon.foss.module.pickup.pricing.api.server.service.IFlightPricePlanService#addFlightPricePlanInfo(com.deppon.foss.module.pickup.pricing.api.shared.domain.FlightPricePlanEntity)
     */
    @Override
    @Transactional
    public FlightPricePlanEntity addFlightPricePlanInfo(FlightPricePlanEntity entity) throws FlightPricePlanException{
		// 判断航空信息是否为null
		if(null == entity){
		    throw new FlightPricePlanException("添加航空公司运价方案信息为空，请检查",null);
		}
		entity.setId(UUIDUtils.getUUID());
		// 判断生效日期是否在当前营业日期之前
		if(entity.getBeginTime().before(new Date())){
		    throw new FlightPricePlanException("生效日期必须大于当前营业日期",null);
		}
		//生效日期必须小于截止日期
		if(entity.getBeginTime().after(entity.getEndTime())){
		    throw new FlightPricePlanException("生效日期必须小于截止日期",null);
		}
		FlightPricePlanEntity fppe = new FlightPricePlanEntity();
		fppe.setAirlinesCode(entity.getAirlinesCode());
		fppe.setLoadOrgCode(entity.getLoadOrgCode());
		//fppe.setActive(FossConstants.ACTIVE);
		long temp = flightPricePlanDao.queryFlightPricePlanByEntityPaggingCount(fppe);
		if (temp > 0) {
			throw new FlightPricePlanException("该航空公司运价已经存在，不能重复添加，请重新选择配载部门或航空公司！",null);
		}
		//设置有效状态
		entity.setActive(FossConstants.INACTIVE);
		//数据库持久化航空信息
		flightPricePlanDao.addFlightPricePlan(entity);
		//查询最新航空运价信息列表返回
		return flightPricePlanDao.getFlightPricePlanEntityById(entity.getId());
    }

    /**
     * 
     * <p>批量删除方案信息</p> 
     * 
     * @author DP-Foss-YueHongJie
     * 
     * @date 2012-12-25 下午4:03:02
     * 
     * @param ids
     * 
     * @throws FlightPricePlanException 
     * 
     * @see com.deppon.foss.module.pickup.pricing.api.server.service.IFlightPricePlanService#deleteFlightPricePlanByIds(java.util.List)
     */
    @Override
    @Transactional
    public void deleteFlightPricePlanByIds(List<String> ids) throws FlightPricePlanException{
	 flightPricePlanDao.deleteFlightPricePlanById(ids);
    }

    /**
     * 
     * <p>修改方案运价信息</p> 
     * 
     * @author DP-Foss-YueHongJie
     * 
     * @date 2012-12-25 下午4:03:20
     * 
     * @param entity
     * 
     * @return
     * 
     * @throws FlightPricePlanException 
     * 
     * @see com.deppon.foss.module.pickup.pricing.api.server.service.IFlightPricePlanService#updateFlightPricePlanByEntity(com.deppon.foss.module.pickup.pricing.api.shared.domain.FlightPricePlanEntity)
     */
    @Override
    @Transactional
    public FlightPricePlanEntity updateFlightPricePlanByEntity(FlightPricePlanEntity entity) throws FlightPricePlanException{
	// 判断航空信息是否为null
	if(null == entity){
	    throw new FlightPricePlanException("修改航空公司运价方案信息为空，请检查",null);
	}
	// 判断生效日期是否在当前营业日期之前
	if(entity.getBeginTime().before(new Date())){
	    throw new FlightPricePlanException("生效日期必须大于当前营业日期",null);
	}
	//生效日期必须小于截止日期
	if(entity.getBeginTime().after(entity.getEndTime())){
	    throw new FlightPricePlanException("生效日期必须小于截止日期",null);
	}
	//数据库持久化航空信息
	flightPricePlanDao.updateFlightPricePlan(entity);
	//查询最新航空运价信息列表返回
	return flightPricePlanDao.getFlightPricePlanEntityById(entity.getId());
    }

    /**
     * 
     * <p>查询方案运价方案信息</p> 
     * 
     * @author DP-Foss-YueHongJie
     * 
     * @date 2012-12-25 下午4:03:38
     * 
     * @param dto
     * 
     * @return
     * 
     * @throws FlightPricePlanException 
     * 
     * @see com.deppon.foss.module.pickup.pricing.api.server.service.IFlightPricePlanService#queryFlightPricePlanByEntity(com.deppon.foss.module.pickup.pricing.api.shared.dto.FlightPricePlanDto)
     */
    @Override
    public List<FlightPricePlanEntity> queryFlightPricePlanByEntity(
	    FlightPricePlanDto dto) throws FlightPricePlanException{
	return flightPricePlanDao.queryFlightPricePlans(dto);
    }

    /**
     * 
     * <p>查询运价方案信息</p> 
     * 
     * @author DP-Foss-YueHongJie
     * 
     * @date 2012-12-25 下午4:03:51
     * 
     * @param airlinesCode
     * 
     * @param loadOrgCode
     * 
     * @param active
     * 
     * @param billDate
     * 
     * @return
     * 
     * @throws FlightPricePlanException 
     * 
     * @see com.deppon.foss.module.pickup.pricing.api.server.service.IFlightPricePlanService#findFlightPricePlanByCondition(java.lang.String, java.lang.String, java.lang.String, java.util.Date)
     */
    @Override
    public FlightPricePlanEntity findFlightPricePlanByCondition(String airlinesCode,
	    String loadOrgCode, String active, Date billDate)throws FlightPricePlanException {
	//航空运价实体bean 设置有效数据、 设置配载部门、设置营业部时间、
	FlightPricePlanDto dto = new FlightPricePlanDto();
	dto.setActive(FossConstants.ACTIVE);
	dto.setLoadOrgCode(loadOrgCode);
	dto.setBillDate(billDate);
	dto.setAirlinesCode(airlinesCode);
	//获取航空运价明细
	List<FlightPricePlanEntity> list = flightPricePlanDao.queryFlightPricePlans(dto);
	FlightPricePlanEntity flightPricePlanEntity = null;
	//避免调用时查询没有数据的情况出现报错
	if(CollectionUtils.isEmpty(list)){
	    flightPricePlanEntity = new FlightPricePlanEntity();
	}else{
	    flightPricePlanEntity =  list.get(0);
	}
	return flightPricePlanEntity;
    }
    /**
     * 
     * <p>批量激活</p> 
     * 
     * @author DP-Foss-YueHongJie
     * 
     * @date 2012-12-25 下午4:04:39
     * 
     * @param ids
     * 
     * @throws FlightPricePlanException 
     * 
     * @see com.deppon.foss.module.pickup.pricing.api.server.service.IFlightPricePlanService#activeFlightPricePlanByIds(java.util.List)
     */
    @Override
    @Transactional
    public void activeFlightPricePlanByIds(List<String> ids)
	    throws FlightPricePlanException {
	if(CollectionUtils.isNotEmpty(ids)){
	    for (String id : ids) {
		FlightPricePlanEntity filightPricePlanEntity = flightPricePlanDao.getFlightPricePlanEntityById(id);
		if(filightPricePlanEntity.getBeginTime().before(new Date(System.currentTimeMillis()))){
		    throw new FlightPricePlanException("当前营业日期必须小于生效日期,才能被激活,请重新修改草稿！",null);
		}
		FlightPricePlanDto dto = new FlightPricePlanDto();
		//有效状态
		dto.setActive(FossConstants.ACTIVE); 
		//航空公司
		dto.setLoadOrgCode(filightPricePlanEntity.getLoadOrgCode());
		//配载部门
		dto.setAirlinesCode(filightPricePlanEntity.getAirlinesCode());
		//生效日期
		dto.setBillDate(filightPricePlanEntity.getBeginTime());
		List<FlightPricePlanEntity> flightPricePlanEntitys = flightPricePlanDao.queryFlightPricePlans(dto);
		if(CollectionUtils.isNotEmpty(flightPricePlanEntitys)){
		    FlightPricePlanEntity dbEntity =  flightPricePlanEntitys.get(0);
		    if(null!=dbEntity){
			dbEntity.setEndTime(filightPricePlanEntity.getBeginTime());
			flightPricePlanDao.updateFlightPricePlan(dbEntity);
		    }
		}
		//校验空的方案不可激活
		FlightPricePlanDetailEntity queryDetailEntity = new FlightPricePlanDetailEntity();
		queryDetailEntity.setFlightPricePlanId(id);
		List<String> detailIds = new ArrayList<String>();
		List<FlightPricePlanDetailEntity> detailList = flightPricePlanDetailDao.queryFlightPricePlanDetails(queryDetailEntity);
		if(CollectionUtils.isEmpty(detailList)){
		    throw new FlightPricePlanException("空的方案不能被激活！",null);
		}
		//收集相关运价明细ID
		for (FlightPricePlanDetailEntity flightPricePlanDetailEntity : detailList) {
		    String detailId = flightPricePlanDetailEntity.getId();
		    detailIds.add(detailId);
		}
		//处理明细立即激活
		flightPricePlanDetailDao.activeFlightPricePlanDetailByIds(detailIds);
	    }
	    //处理方案立即激活
	    flightPricePlanDao.activeFlightPricePlanByIds(ids);
	}
    }

    /**
     * 
     * <p>查询分页</p> 
     * 
     * @author DP-Foss-YueHongJie
     * 
     * @date 2012-12-25 下午4:04:57
     * 
     * @param flightPricePlanEntity
     * 
     * @param start
     * 
     * @param limit
     * 
     * @return
     * 
     * @throws FlightPricePlanException 
     * 
     * @see com.deppon.foss.module.pickup.pricing.api.server.service.IFlightPricePlanService#queryFlightPricePlanByEntity(com.deppon.foss.module.pickup.pricing.api.shared.domain.FlightPricePlanEntity, int, int)
     */
    @Override
    public List<FlightPricePlanEntity> queryFlightPricePlanByEntity(
	    FlightPricePlanEntity flightPricePlanEntity, int start, int limit)
	    throws FlightPricePlanException {
	List<FlightPricePlanEntity>  flightPricePlanEntitys = flightPricePlanDao.queryFlightPricePlanByEntityPagging(flightPricePlanEntity,start,limit);
	return this.boxFlightPricePlanDate(flightPricePlanEntitys);
    }
    /**
     * 
     * <p>查询配载部门，航空公司中文名称</p> 
     * 
     * @author DP-Foss-YueHongJie
     * 
     * @date 2013-1-8 下午3:42:44
     * 
     * @param flightPricePlanEntitys
     * 
     * @return
     * 
     * @see
     */
    private List<FlightPricePlanEntity> boxFlightPricePlanDate(List<FlightPricePlanEntity> flightPricePlanEntitys) {
	//设置相关对象名称信息
	List<FlightPricePlanEntity> list = new ArrayList<FlightPricePlanEntity>();
	for (FlightPricePlanEntity flightPricePlanEntity : flightPricePlanEntitys) {
	    AirlinesEntity airlinesEntity = airlinesService.queryAirlineByCode(flightPricePlanEntity.getAirlinesCode());
	    OrgAdministrativeInfoEntity orgAdministrativeInfoEntity = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(flightPricePlanEntity.getLoadOrgCode());
	    if(StringUtil.isNotEmpty(flightPricePlanEntity.getCreateUser())){
	    	EmployeeEntity employee = employeeService.queryEmployeeByEmpCode(flightPricePlanEntity.getCreateUser());
	    	//设置创建人中文名称
		    if(null != employee){
			flightPricePlanEntity.setCreateUserName(employee.getEmpName());
		    }
	    }
	    EmployeeEntity modifyEmployee = employeeService.queryEmployeeByEmpCode(flightPricePlanEntity.getModifyUser());
	    //设置航空公司中文名称
	    if(null != airlinesEntity){
		flightPricePlanEntity.setAirlinesName(airlinesEntity.getName());
	    }
	    //设置配载部门中文名称
	    if(null!= orgAdministrativeInfoEntity){
		flightPricePlanEntity.setLoadOrgName(orgAdministrativeInfoEntity.getName());
	    }	    
	    //设置修改人中文名称
	    if(null != modifyEmployee){
		flightPricePlanEntity.setModifyUserName(modifyEmployee.getEmpName());
	    }
	    list.add(flightPricePlanEntity);
	}
	return list;
    }
    /**
     * 
     * <p>查询总数</p> 
     * 
     * @author DP-Foss-YueHongJie
     * 
     * @date 2012-12-25 下午4:05:08
     * 
     * @param flightPricePlanEntity
     * 
     * @return
     * 
     * @throws FlightPricePlanException 
     * 
     * @see com.deppon.foss.module.pickup.pricing.api.server.service.IFlightPricePlanService#queryFlightPricePlanByEntityCount(com.deppon.foss.module.pickup.pricing.api.shared.domain.FlightPricePlanEntity)
     */
    @Override
	public Long queryFlightPricePlanByEntityCount(FlightPricePlanEntity flightPricePlanEntity)
			throws FlightPricePlanException {
		return flightPricePlanDao.queryFlightPricePlanByEntityPaggingCount(flightPricePlanEntity);
	}
    /**
     * 
     * <p>查询单个运价方案</p> 
     * 
     * @author DP-Foss-YueHongJie
     * 
     * @date 2012-12-25 下午4:05:34
     * 
     * @param id
     * 
     * @return 
     * 
     * @see com.deppon.foss.module.pickup.pricing.api.server.service.IFlightPricePlanService#getFlightPricePlanEntityById(java.lang.String)
     */
    @Override
    public FlightPricePlanEntity getFlightPricePlanEntityById(String id) {
    	FlightPricePlanEntity flightPricePlanEntity = flightPricePlanDao.getFlightPricePlanEntityById(id);
    	if(null!=flightPricePlanEntity){
    		AirlinesEntity airlinesEntity = airlinesService.queryAirlineByCode(flightPricePlanEntity.getAirlinesCode());
    		if(null!=airlinesEntity){
    			flightPricePlanEntity.setAirlinesName(airlinesEntity.getName());
    		}
    		OrgAdministrativeInfoEntity orgAdministrativeInfoEntity = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(flightPricePlanEntity.getLoadOrgCode());
    		if(null!=orgAdministrativeInfoEntity){
			flightPricePlanEntity.setLoadOrgName(orgAdministrativeInfoEntity.getName());
		}
    	}
    	return flightPricePlanEntity;
    }
    /**
     * 
     * <p>复制方案</p> 
     * 
     * @author DP-Foss-YueHongJie
     * 
     * @date 2012-12-25 下午4:05:52
     * 
     * @param id
     * 
     * @return 
     * 
     * @see com.deppon.foss.module.pickup.pricing.api.server.service.IFlightPricePlanService#copyFlightPricePlanEntity(java.lang.String)
     */
    @Override
    @Transactional
    public FlightPricePlanEntity copyFlightPricePlanEntity(String id) {
	FlightPricePlanEntity flightPricePlanEntity = flightPricePlanDao.getFlightPricePlanEntityById(id);
	String uuId = UUIDUtils.getUUID();
	flightPricePlanEntity.setId(uuId);
	flightPricePlanEntity.setCreateDate(new Date());
	flightPricePlanEntity.setVersionNo(new Date().getTime());
	//复制为草稿状态
	flightPricePlanEntity.setActive(FossConstants.INACTIVE);
	flightPricePlanDao.addFlightPricePlan(flightPricePlanEntity);
	
	FlightPricePlanDetailEntity queryDetailEntity = new FlightPricePlanDetailEntity();
	//根据复制方案id复制其明细信息
	queryDetailEntity.setFlightPricePlanId(id);
	List<FlightPricePlanDetailEntity> detailList = flightPricePlanDetailDao.queryFlightPricePlanDetails(queryDetailEntity);
	for (FlightPricePlanDetailEntity flightPricePlanDetailEntity : detailList) {
	    flightPricePlanDetailEntity.setId(UUIDUtils.getUUID());
	    flightPricePlanDetailEntity.setFlightPricePlanId(uuId);
	  //复制草稿明细
	    flightPricePlanDetailEntity.setActive(FossConstants.INACTIVE);
	    flightPricePlanDetailDao.copyFlightPricePlanDetailEntity(flightPricePlanDetailEntity);
	}
	return flightPricePlanDao.getFlightPricePlanEntityById(uuId);
    }
    /**
     * 
     * <p>立即激活</p> 
     * 
     * @author DP-Foss-YueHongJie
     * 
     * @date 2013-3-1 下午4:27:27
     * 
     * @param flightPricePlanEntity
     * 
     * @throws FlightPricePlanException
     * 
     * @see
     */
    @Transactional
    @Override
	public void immediatelyActiveFlightPrice(FlightPricePlanEntity flightPricePlanEntity)
			throws FlightPricePlanException {

		if (null == flightPricePlanEntity) {
			throw new FlightPricePlanException("方案信息为空！", null);
		}
		//根据当前运价方案id查询是否存在运价明细信息，如果存在。 则流程继续。否则抛出异常
		FlightPricePlanDetailEntity queryDetailEntity = new FlightPricePlanDetailEntity();
		queryDetailEntity.setFlightPricePlanId(flightPricePlanEntity.getId());
		List<String> detailIds = new ArrayList<String>();
		List<FlightPricePlanDetailEntity> detailList = flightPricePlanDetailDao.queryFlightPricePlanDetails(queryDetailEntity);
		if (CollectionUtils.isEmpty(detailList)) {
			throw new FlightPricePlanException("空的方案不能被激活！", null);
		}
		// 找到已经存方案将方案截止日期修正为当前方案
		FlightPricePlanDto dto = new FlightPricePlanDto();
		dto.setActive(FossConstants.ACTIVE);
		dto.setLoadOrgCode(flightPricePlanEntity.getLoadOrgCode());
		dto.setBillDate(new Date(System.currentTimeMillis()));
		dto.setAirlinesCode(flightPricePlanEntity.getAirlinesCode());
		List<FlightPricePlanEntity> flightPricePlanEntitys = flightPricePlanDao.queryFlightPricePlans(dto);
		if (CollectionUtils.isNotEmpty(flightPricePlanEntitys)) {
			FlightPricePlanEntity dbEntity = flightPricePlanEntitys.get(0);
			if (null != dbEntity) {
				dbEntity.setEndTime(flightPricePlanEntity.getBeginTime());
				flightPricePlanDao.updateFlightPricePlan(dbEntity);
			}
		}
		//收集航空运价明细ID
		for (FlightPricePlanDetailEntity flightPricePlanDetailEntity : detailList) {
			String detailId = flightPricePlanDetailEntity.getId();
			detailIds.add(detailId);
		}
		//立即激活所有明细
		flightPricePlanDetailDao.activeFlightPricePlanDetailByIds(detailIds);
		// 立即激活，将状态修改
		flightPricePlanEntity.setActive(FossConstants.ACTIVE);
		//立即激活方案
		flightPricePlanDao.updateFlightPricePlan(flightPricePlanEntity);
	}
    /**
     * 
     * <p>立即中止</p> 
     * 
     * @author DP-Foss-YueHongJie
     * 
     * @date 2013-3-1 下午4:28:16
     * 
     * @param flightPricePlanEntity
     * 
     * @throws FlightPricePlanException
     * 
     * @see
     */
	@Override
	@Transactional
	public void immediatelyStopFlightPrice(FlightPricePlanEntity flightPricePlanEntity) throws FlightPricePlanException {
	    	//立即中止处理截止日期、如果所修改的截止日期比默认数据库中的截止日期还要大。 则提示不可顺延活动截止日期
	    	if (null != flightPricePlanEntity) {
			String id = flightPricePlanEntity.getId();
			FlightPricePlanEntity dbEntity = flightPricePlanDao.getFlightPricePlanEntityById(id);
			if(flightPricePlanEntity.getEndTime().before(new Date())){
	    	    throw new FlightPricePlanException("中止日期必须大于当前营业日期!",null);
	    	}
			if(flightPricePlanEntity.getEndTime().after(dbEntity.getEndTime())){
			    throw new FlightPricePlanException("中止日期不能延长原方案所制定的活动结束日期!",null);
			}
			dbEntity.setEndTime(flightPricePlanEntity.getEndTime());
			flightPricePlanDao.updateFlightPricePlan(dbEntity);
		} else {
			throw new FlightPricePlanException("所选方案信息为空", null);
		}
	}
    @Override
    @Transactional
    public String addFlightPricePlanBatch(
	    Map<String, List<FlightPriceDto>> detailMap,
	    Map<String, AirlinesEntity> airlinesMap,
	    Map<String, OrgAdministrativeInfoEntity> loadOrgsMap,
	    Map<String, AdministrativeRegionsEntity> destCity,
	    Map<String, GoodsTypeEntity> goodsMap,
	    Map<String, FlightDto> flightDtoMap) {
	
		// 检查数据
		if (airlinesMap == null
			|| airlinesMap.size() < 1 || loadOrgsMap == null || loadOrgsMap.size() < 1
			|| destCity == null || destCity.size() < 1 || goodsMap == null 
			|| goodsMap.size() < 1 || flightDtoMap == null || flightDtoMap.size()<1) {
		    return "没有需要导入的数据";
		}
		UserEntity userEntity = FossUserContext.getCurrentUser();
		OrgAdministrativeInfoEntity orgEntity = FossUserContext.getCurrentDept();
	//	Iterator<String> it = detailMap.keySet().iterator();
		Date currentDate = new Date();
		Date endDate = new Date(PricingConstants.ENDTIME);
	//	while(it.hasNext())
	//	{
		
		//系统中重复数据不导入
		StringBuffer sb = new StringBuffer();
		
		Set<Entry<String, List<FlightPriceDto>>> detailSet = detailMap.entrySet();
		for (Entry<String, List<FlightPriceDto>> entry : detailSet) {
		    String key = entry.getKey();
		    String[] elements = key.split(",");
		    String priceNo = elements[0];
		    String loadOrgName = elements[1];
		    String airlinesName =  elements[2];
		    
		    FlightPricePlanEntity flightPricePlanEntity = new FlightPricePlanEntity();
		    //处理航空公司
		    AirlinesEntity airLinesEntity = airlinesMap.get(airlinesName);
		    flightPricePlanEntity.setAirlinesCode(airLinesEntity.getCode());
		    //处理配载部门
		    OrgAdministrativeInfoEntity orgInfoEntity = loadOrgsMap.get(loadOrgName);
		    flightPricePlanEntity.setLoadOrgCode(orgInfoEntity.getCode());
		    //根据航空公司代码和配载部门代码查询数据库中数据是否存在
			long temp = flightPricePlanDao.queryFlightPricePlanByEntityPaggingCount(flightPricePlanEntity);
			if (temp > 0) {
				sb.append("航空公司：" + airLinesEntity.getCode() + "-" + airlinesName + 
						"，配载部门：" + orgInfoEntity.getCode() + "-" + loadOrgName);
			}
		    
		    String batchId = UUIDUtils.getUUID();
		    flightPricePlanEntity.setId(batchId);
		    //处理运价号
		    flightPricePlanEntity.setPriceNo(priceNo);
		    
		    flightPricePlanEntity.setBeginTime(currentDate);
		    flightPricePlanEntity.setEndTime(endDate);
		    flightPricePlanEntity.setActive(FossConstants.INACTIVE);
		    flightPricePlanEntity.setDescription("导入的数据");
		    flightPricePlanEntity.setVersionNo(currentDate.getTime());
		    flightPricePlanEntity.setCreateDate(currentDate);
		    flightPricePlanEntity.setCreateUser(userEntity.getEmpCode());
		    flightPricePlanEntity.setCreateOrgCode(orgEntity.getCode());
		    flightPricePlanEntity.setCurrencyCode(FossConstants.CURRENCY_CODE_RMB);
		    flightPricePlanDao.addFlightPricePlan(flightPricePlanEntity);
		    
		    List<FlightPriceDto> flightPriceDtos = entry.getValue();
		    for (FlightPriceDto flightPriceDto : flightPriceDtos) {
			FlightPricePlanDetailEntity flightPricePlanDetailEntity = new FlightPricePlanDetailEntity();
			flightPricePlanDetailEntity.setId(UUIDUtils.getUUID());
			flightPricePlanDetailEntity.setFlightPricePlanId(batchId);
			flightPricePlanDetailEntity.setActive(FossConstants.INACTIVE);
			flightPricePlanDetailEntity.setCreateDate(currentDate);
			flightPricePlanDetailEntity.setCreateUser(userEntity.getEmpCode());
			flightPricePlanDetailEntity.setCreateOrgCode(orgEntity.getCode());
			flightPricePlanDetailEntity.setCurrencyCode(FossConstants.CURRENCY_CODE_RMB);
			flightPricePlanDetailEntity.setVersionNo(currentDate.getTime());
			//处理目的站
			AdministrativeRegionsEntity regionEntity = destCity.get(flightPriceDto.getDestDistrictName());
			flightPricePlanDetailEntity.setDestDistrictCode(regionEntity.getCode());
			//处理货物类型
			GoodsTypeEntity goodsType = goodsMap.get(flightPriceDto.getGoodsTypeName());
			flightPricePlanDetailEntity.setGoodsTypeCode(goodsType.getCode());
			//处理航班类型
			FlightDto flightDto = flightDtoMap.get(flightPriceDto.getFlightNo());
			flightPricePlanDetailEntity.setFlightNo(flightDto.getFlightNo());
			flightPricePlanDetailEntity.setMinFee(flightPriceDto.getMinFee()* NumberConstants.NUMBER_100);
			
			
			//处理数据库以分记录与金额相关的数值
			double down45kg = flightPriceDto.getDown45Kg().doubleValue() * PricingConstants.YUTOFEN;
			double up45kg = flightPriceDto.getUp45Kg().doubleValue() * PricingConstants.YUTOFEN;
			double up100kg = flightPriceDto.getUp100Kg().doubleValue() * PricingConstants.YUTOFEN;
			double up300kg = flightPriceDto.getUp300Kg().doubleValue() * PricingConstants.YUTOFEN;
			double up500kg = flightPriceDto.getUp500Kg().doubleValue() * PricingConstants.YUTOFEN;
			double up1000kg = flightPriceDto.getUp1000Kg().doubleValue() * PricingConstants.YUTOFEN;
			double up2000kg = flightPriceDto.getUp2000Kg().doubleValue() * PricingConstants.YUTOFEN;
			double up3000kg = flightPriceDto.getUp3000Kg().doubleValue() * PricingConstants.YUTOFEN;
			
			flightPricePlanDetailEntity.setDown45Kg(BigDecimal.valueOf(Double.valueOf(down45kg)));
			flightPricePlanDetailEntity.setUp45Kg(BigDecimal.valueOf(Double.valueOf(up45kg)));
			flightPricePlanDetailEntity.setUp100Kg(BigDecimal.valueOf(Double.valueOf(up100kg)));
			flightPricePlanDetailEntity.setUp300Kg(BigDecimal.valueOf(Double.valueOf(up300kg)));
			flightPricePlanDetailEntity.setUp500Kg(BigDecimal.valueOf(Double.valueOf(up500kg)));
			flightPricePlanDetailEntity.setUp1000Kg(BigDecimal.valueOf(Double.valueOf(up1000kg)));
			flightPricePlanDetailEntity.setUp2000Kg(BigDecimal.valueOf(Double.valueOf(up2000kg)));
			flightPricePlanDetailEntity.setUp3000Kg(BigDecimal.valueOf(Double.valueOf(up3000kg)));
			flightPricePlanDetailDao.addFlightPricePlanDetail(flightPricePlanDetailEntity);
		    }
		}
		if (sb != null) {
			return "以下数据：" + sb.toString() + "  在系统中已存在未导入，其余数据导入成功。";
		} else {
			return null;
		}
    }
}
