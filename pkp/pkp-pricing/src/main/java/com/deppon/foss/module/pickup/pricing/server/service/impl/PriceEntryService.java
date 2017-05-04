/**
 *  initial comments.
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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/pricing/server/service/impl/PriceEntryService.java
 * 
 * FILE NAME        	: PriceEntryService.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.pricing.server.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.deppon.foss.base.util.SqlUtil;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.server.service.IEmployeeService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.EmployeeEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.pickup.pricing.api.server.dao.IPriceEntryDao;
import com.deppon.foss.module.pickup.pricing.api.server.service.IPriceEntryService;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.PriceEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.exception.PriceEntryException;
import com.deppon.foss.module.pickup.pricing.server.cache.PriceEntryCacheDeal;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;
import com.google.inject.Inject;

/**
 * 
 * (计价条目服务)
 * @author DP-Foss-YueHongJie
 * @date 2012-12-7 下午2:33:50
 * @version 1.0
 */
public class PriceEntryService implements IPriceEntryService{
	
    /**
     * 日志信息
     */
    private static final Logger log = Logger.getLogger(PriceEntryService.class);
    /**
     * 计价条目DAO
     */
    @Inject
    IPriceEntryDao priceEntryDao;
    /**
     * 计价条目缓存处理类
     */
    @Inject
    PriceEntryCacheDeal priceEntryCacheDeal;
    
    /**
     * 员工信息
     */
    IEmployeeService employeeService;
    
    public void setEmployeeService(IEmployeeService employeeService){
	this.employeeService = employeeService;
    }
    
    /**
     * 设置 计价条目缓存处理类.
     *
     * @param priceEntryCacheDeal the new 计价条目缓存处理类
     */
    public void setPriceEntryCacheDeal(PriceEntryCacheDeal priceEntryCacheDeal) {
		this.priceEntryCacheDeal = priceEntryCacheDeal;
    }

    /**
     * 设置 计价条目DAO.
     *
     * @param priceEntryDao the new 计价条目DAO
     */
    public void setPriceEntryDao(IPriceEntryDao priceEntryDao) {
        this.priceEntryDao = priceEntryDao;
    }
	/**
     * 
     * <p>根据条目entryCode返回计价条目名称,entryCode设置请参照常量类PricingConstants</p> 
     * @author DP-Foss-YueHongJie
     * @date 2012-12-7 下午2:32:26
     * @param entryCode
     * @return
     * @see
     */
    @Override
    public String queryPriceEntryNameByCode(String entryCode) {
	return priceEntryDao.queryPriceEntryNameByCode(entryCode);
    }
    /**
     * 
     * <p>(根据条目entryCodes批量查询条目信息,entryCode设置请参照常量类PricingConstants)</p> 
     * @author DP-Foss-YueHongJie
     * @date 2012-12-7 下午6:10:06
     * @param entryCodes
     * @return
     * @see
     */
    @Override
    public Map<String, String> queryPriceEntryNameByEntryCodes(
	    List<String> entryCodes) {
	Map<String,String> resultMap = new HashMap<String,String>();
	List<PriceEntity>  priceEntrys =  priceEntryDao.queryPriceEntryNameByEntryCodes(entryCodes);
	if(CollectionUtils.isNotEmpty(priceEntrys)){
	    for (PriceEntity priceEntity : priceEntrys) {
		resultMap.put(priceEntity.getCode(), priceEntity.getName());
	    }
	}
	return resultMap;
    }
    /**
     * 
     * @Description: 从缓存中获取计价条目实体
     * @author FOSSDP-sz
     * @date 2013-2-19 下午3:49:52
     * @param entryCode
     * @param billDate
     * @return
     * @version V1.0
     */
    @Override
    public PriceEntity getPriceEntryByCache(String entryCode, Date billDate) {
    	if(StringUtils.isEmpty(entryCode)) {
    		return null;
    	}
    	if(billDate == null) {
    		billDate = new Date();
    	}
    	PriceEntity priceEntity = null;
    	//客户端不读缓存
    	if(SqlUtil.loadCache){
	    	try {
	    		priceEntity = priceEntryCacheDeal.getPriceEntryByCache(entryCode, billDate);
	    		//修复pop-437开单时，详细计价信息中优惠项目"包装费"字段丢失
	    		if(null!=priceEntity){
	    		   return priceEntity;
	    		}
			} catch (Exception e) {
				log.info("无法获取计价条目缓存",e);
			}
    	}
    	List<PriceEntity> priceEntities = priceEntryDao.getPriceEntryByCode(entryCode, billDate);
		if(CollectionUtils.isNotEmpty(priceEntities)) {
			return priceEntities.get(0);
		} 
		return null;
    }

    /**
     * @Description: 刷新计价条目缓存
     * @author FOSSDP-sz
     * @date 2013-2-22 上午10:18:46
     * @param entryCode
     * @version V1.0
     */
    public void refreshPriceEntryCache(String entryCode) {
    	if(StringUtil.isNotBlank(entryCode)) {
    		try {
        		priceEntryCacheDeal.getPriceEntryCache().invalid(entryCode);
    		} catch (Exception e) {
    			log.info("无法刷新计价条目缓存",e);
    		}
    	}
    }

    @Override
    public List<PriceEntity> findPriceEntityPagingByCondition(
	    PriceEntity entity, int start, int limit)throws PriceEntryException {
	entity.setRefCode(PricingConstants.PriceEntityConstants.PRICING_CODE_QT);
	List<PriceEntity> list = priceEntryDao.findPriceEntityPagingByCondition(entity, start, limit);
	List<PriceEntity> resultList = null;
	if(CollectionUtils.isNotEmpty(list)){
	    resultList = new ArrayList<PriceEntity>();
		for (PriceEntity priceEntity : list) {
			String blongPricingName = priceEntryDao.queryPriceEntryNameByCode(priceEntity.getBlongPricingCode());
			String refName =  priceEntryDao.queryPriceEntryNameByCode(PricingConstants.PriceEntityConstants.PRICING_CODE_QT);
			if(StringUtil.isNotEmpty(priceEntity.getModifyUser())){
				EmployeeEntity emp = employeeService.queryEmployeeByEmpCode(priceEntity.getModifyUser());
			    if(emp == null){
				log.error("根据修改人编码没有找到对应的记录");
				throw new PriceEntryException("根据修改人编码没有找到对应的记录");
			    }
			    priceEntity.setModifyUserName(emp.getEmpName());
			}		    
		    priceEntity.setBlongPricingName(blongPricingName);
		    priceEntity.setRefName(refName);
		    resultList.add(priceEntity);
		}
	}
	return resultList;
    }

    @Override
    public Long countPriceEntityPagingByCondition(PriceEntity entity) {
    	// BUG-56658 没有refCode导致查询结果与count不一致
    	entity.setRefCode(PricingConstants.PriceEntityConstants.PRICING_CODE_QT);
    	return priceEntryDao.countPriceEntityPagingByCondition(entity);
    }

    @Override
    public PriceEntity findPriceEntityById(String id) {
	return priceEntryDao.findPriceEntityById(id);
    }

    @Override
    public void modifyPriceEntity(PriceEntity priceEntity) {
	PriceEntity chekNameBean = new PriceEntity();
	chekNameBean.setName(priceEntity.getName());
	chekNameBean.setId(priceEntity.getId());
	PriceEntity checkNameResult = priceEntryDao.modifyBeforeCheckPriceEntryCode(chekNameBean);
	//校验其他其他费用名称是否已经与当前维护的名称重复
	if(checkNameResult != null){
	   log.error("名称:"+priceEntity.getName()+"已经重复");
	   throw new PriceEntryException("名称："+checkNameResult.getName()+"已经存在, 不能成功修改!");
	}
	PriceEntity dbPriceEntity = priceEntryDao.findPriceEntityById(priceEntity.getId());
	Date currentTime = new Date();
   
	//版本号
	dbPriceEntity.setVersionNo(currentTime.getTime());
	//修改时间
	dbPriceEntity.setModifyDate(currentTime);
	OrgAdministrativeInfoEntity currentDept  = FossUserContext.getCurrentDept();
	UserEntity currentUser = FossUserContext.getCurrentUser();
	//创建机构,修改机构
	dbPriceEntity.setCreateOrgCode(currentDept.getCode());
	dbPriceEntity.setModifyOrgCode(currentDept.getCode());
	//创建人,修改人
	dbPriceEntity.setCreateUser(currentUser.getEmployee().getEmpCode());
	dbPriceEntity.setModifyUser(currentUser.getEmployee().getEmpCode());
	dbPriceEntity.setRemarks(priceEntity.getRemarks());
	dbPriceEntity.setName(priceEntity.getName());
	
	dbPriceEntity.setBlongPricingCode(priceEntity.getBlongPricingCode());
	
	//根据归集类别CODE查询相关ID信息
	List<PriceEntity>  resultList = priceEntryDao.getPriceEntryByCode(priceEntity.getBlongPricingCode(),new Date());
	PriceEntity paramert = new PriceEntity();
	paramert = resultList.get(0);
    
	dbPriceEntity.setBlongPricingId(paramert.getId());
	priceEntryDao.modifyPriceEntity(dbPriceEntity);
    }

    @Override
    public void addPriceEntry(PriceEntity priceEntity)throws PriceEntryException {
	PriceEntity temp = priceEntryDao.queryPriceEntryByCode(priceEntity.getCode());
	PriceEntity chekNameBean = new PriceEntity();
	chekNameBean.setName(priceEntity.getName());
	chekNameBean.setId(priceEntity.getId());
	PriceEntity checkNameResult = priceEntryDao.modifyBeforeCheckPriceEntryCode(chekNameBean);
	if(checkNameResult != null){
	  log.error("名称为:"+priceEntity.getName()+"已经重复");
	  throw new PriceEntryException("名称为:"+priceEntity.getName()+"已经重复");
	}
	if(null == temp){
	    
	    List<PriceEntity>  resultList = priceEntryDao.getPriceEntryByCode(PricingConstants.PriceEntityConstants.PRICING_CODE_QT,new Date());
	    if(CollectionUtils.isEmpty(resultList)){
		throw new PriceEntryException("没有其他费用基础信息,不能在此基础上添加其子元素,请通知管理员检查基础数据");
	    }
	    PriceEntity paramert = new PriceEntity();
	    paramert = resultList.get(0);
	    Date currentTime = new Date();
	    priceEntity.setId(UUIDUtils.getUUID());
	    priceEntity.setActive(FossConstants.ACTIVE);
	    priceEntity.setRefId(paramert.getId());
	    priceEntity.setRefCode(paramert.getCode());
	    
	    //根据归集类别CODE查询相关ID信息
	    List<PriceEntity>  tempRsultList = priceEntryDao.getPriceEntryByCode(priceEntity.getBlongPricingCode(),new Date());
	    paramert = tempRsultList.get(0);
	    
	    priceEntity.setBlongPricingId(paramert.getId());
	    priceEntity.setBeginTime(currentTime);
	    priceEntity.setEndTime(new Date(PricingConstants.ENDTIME));
	    priceEntity.setVersionNo(currentTime.getTime());
	    priceEntity.setCreateDate(currentTime);
	    priceEntity.setModifyDate(currentTime);
	    OrgAdministrativeInfoEntity currentDept  = FossUserContext.getCurrentDept();
	    UserEntity currentUser = FossUserContext.getCurrentUser();
	    //创建机构,修改机构
	    priceEntity.setCreateOrgCode(currentDept.getCode());
	    priceEntity.setModifyOrgCode(currentDept.getCode());
	    //创建人,修改人
	    priceEntity.setCreateUser(currentUser.getEmployee().getEmpCode());
	    priceEntity.setModifyUser(currentUser.getEmployee().getEmpCode());
	    priceEntryDao.addPriceEntry(priceEntity);
	}else{
	    throw new PriceEntryException("该计价条目编码"+temp.getCode()+"已经存在, 不能成功添加!");
	}
    }

    /**
     * 
     * 根据外部条件查询对应的费用记录信息
     * @author 025000-FOSS-helong
     * @date 2013-7-2
     */
	@Override
	public List<PriceEntity> findParentPriceEntitys() {
		return priceEntryDao.findParentPriceEntitys();
	}
}