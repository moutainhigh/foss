/**
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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/service/impl/BargainAppOrgService.java
 * 
 * FILE NAME        	: BargainAppOrgService.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 
 *
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

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.module.base.baseinfo.api.server.dao.IBargainAppOrgDao;
import com.deppon.foss.module.base.baseinfo.api.server.service.IBargainAppOrgService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.BargainAppOrgEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.BargainAppOrgException;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;
import com.deppon.foss.base.util.define.NumberConstants;


/**
 * 合同适用部门Service接口实现
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:094463-foss-xieyantao,date:2012-11-20 下午6:50:31 </p>
 * @author 094463-foss-xieyantao
 * @date 2012-11-20 下午6:50:31
 * @since
 * @version
 */
public class BargainAppOrgService implements IBargainAppOrgService {
    
    /**
     * 日志.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(BargainAppOrgService.class);
    
    /**
     * 合同适用部门DAO接口.
     */
    private IBargainAppOrgDao bargainAppOrgDao;
    
    /**
     * 设置 合同适用部门DAO接口.
     *
     * @param bargainAppOrgDao the new 合同适用部门DAO接口
     */
    public void setBargainAppOrgDao(IBargainAppOrgDao bargainAppOrgDao) {
        this.bargainAppOrgDao = bargainAppOrgDao;
    }
    
    /**
     * 新增合同适用部门.
     *
     * @param entity 合同适用部门实体
     * @return 1：成功；-1：失败
     * @throws BargainAppOrgException 
     * @author 094463-foss-xieyantao
     * @date 2012-11-20 下午6:08:49
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.IBargainAppOrgService#addBargainAppOrg(com.deppon.foss.module.base.baseinfo.api.shared.domain.BargainAppOrgEntity)
     */
    @Transactional
    @Override
    public int addBargainAppOrg(BargainAppOrgEntity entity) throws BargainAppOrgException{
	
	if (null == entity) {

	    return FossConstants.FAILURE;
	}
	
	if(null == entity.getCrmId()){
	    throw new BargainAppOrgException("合同适用部门CRM_ID不允许为空！");
	}
	
	//先验证在数据库是否存在
	boolean isFlag = bargainAppOrgDao.queryBargainAppOrgByCrmId(entity.getCrmId(),null);
	
	if(isFlag){//存在就修改
	    bargainAppOrgDao.updateBargainAppOrg(entity);
	}else {
	    entity.setId(UUIDUtils.getUUID());
	    // 第一次记录新增时，虚拟编码为记录的id
	    entity.setVirtualCode(entity.getId());

	    bargainAppOrgDao.addBargainAppOrg(entity);
	}
	
	return FossConstants.SUCCESS;
    }

    /**
     * 根据code作废合同适用部门.
     *
     * @param crmId 
     * @param modifyUser 
     * @return 1：成功；-1：失败
     * @throws BargainAppOrgException 
     * @author dp-xieyantao
     * @date 2012-11-20 下午6:08:49
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.IBargainAppOrgService#deleteBargainAppOrgByCode(java.lang.String, java.lang.String)
     */
    @Transactional
    @Override
    public int deleteBargainAppOrgByCode(BigDecimal crmId, String modifyUser) throws BargainAppOrgException{
	
	if(null == crmId){
	    throw new BargainAppOrgException("合同适用部门CRMID不允许为空");
	}else {
	    LOGGER.debug("crmId: "+crmId + "modifyUser:"+modifyUser);
	    return bargainAppOrgDao.deleteBargainAppOrgByCode(crmId, modifyUser);
	}
    }

    /**
     * 修改合同适用部门
     * 对于有版本控制的编辑，先逻辑删除该记录，再新增一条记录.
     *
     * @param entity 合同适用部门实体
     * @return 1：成功；-1：失败
     * @throws BargainAppOrgException 
     * @author dp-xieyantao
     * @date 2012-11-20 下午6:08:49
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.IBargainAppOrgService#updateBargainAppOrg(com.deppon.foss.module.base.baseinfo.api.shared.domain.BargainAppOrgEntity)
     */
    @Transactional
    @Override
    public int updateBargainAppOrg(BargainAppOrgEntity entity) throws BargainAppOrgException{
	if(null == entity){
	    return FossConstants.FAILURE;
	}
	if(null == entity.getCrmId()){
	    throw new BargainAppOrgException("客户合同适用部门ID不允许为空！");
	}
	/*//作废记录
	int flag = bargainAppOrgDao.deleteBargainAppOrgByCode(entity.getCrmId(), entity.getModifyUser());
	
	if(flag > 0){//作废成功
	    entity.setActive(FossConstants.ACTIVE);
	    entity.setId(UUIDUtils.getUUID());
	    
	    return bargainAppOrgDao.addBargainAppOrg(entity);
	}*/
	
	return bargainAppOrgDao.updateBargainAppOrg(entity);
//	return FossConstants.FAILURE;
    }
    
    /**
     * <p>根据crmId,最后一次修改时间查询合同适用部门是否存在</p>.
     *
     * @param crmId 
     * @param lastupdatetime 
     * @return 
     * @author 094463-foss-xieyantao
     * @date 2012-11-26 上午10:35:38
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.IBargainAppOrgService#queryBargainAppOrgByCrmId(java.math.BigDecimal, java.util.Date)
     */
    @Override
    public boolean queryBargainAppOrgByCrmId(BigDecimal crmId,
	    Date lastupdatetime) {
	
	return bargainAppOrgDao.queryBargainAppOrgByCrmId(crmId, lastupdatetime);
    }
    
    /**
     * <p>
     * 根据客户合同CRM_ID查询合同适用部门信息
     * </p>
     * 
     * @author 094463-foss-xieyantao
     * @date 2013-6-3 上午11:28:02
     * @param bargainCrmId
     *            客户合同CRM_ID
     * @return
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.IBargainAppOrgService#queryAppOrgByBargainCrmId(java.math.BigDecimal)
     */
    @Override
    public List<BargainAppOrgEntity> queryAppOrgByBargainCrmId(
	    BigDecimal bargainCrmId,List<String> unifiedCodeList) {
	if(null == bargainCrmId){
	    return null;
	}
	if (CollectionUtils.isNotEmpty(unifiedCodeList)) {
		//大于1000条，则for循环，否则直接in
		if(unifiedCodeList.size()>NumberConstants.NUMBER_1000){
			List<BargainAppOrgEntity> allList=new ArrayList<BargainAppOrgEntity>();
			for(String unifiedCode:unifiedCodeList){
				List<BargainAppOrgEntity> list=bargainAppOrgDao.queryAppOrgByBargainCrmIdAndCode(bargainCrmId,unifiedCode);
				allList.addAll(list);
			}
			return allList;
		}else{			
			return bargainAppOrgDao.queryAppOrgByBargainCrmIdAndCodeList(bargainCrmId,unifiedCodeList);
		}		
	}else{
		return bargainAppOrgDao.queryAppOrgByBargainCrmId(bargainCrmId);
	}
	
    }
    @Override
    public List<BargainAppOrgEntity> queryAppOrgByBargainCrmIdAndUnifiedCode(BigDecimal bargainCrmId,String unifiedCode){
    	if(bargainCrmId==null||StringUtils.isEmpty(unifiedCode)){
    		return null;
    	}
    	return bargainAppOrgDao.queryAppOrgByBargainCrmIdAndCode(bargainCrmId,unifiedCode);
    	 
    }
}
