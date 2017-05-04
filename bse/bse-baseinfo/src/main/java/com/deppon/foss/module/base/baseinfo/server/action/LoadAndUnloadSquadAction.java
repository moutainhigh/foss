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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/action/LoadAndUnloadSquadAction.java
 * 
 * FILE NAME        	: LoadAndUnloadSquadAction.java
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

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.module.base.baseinfo.api.server.service.ILoadAndUnloadSquadService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IPorterService;
import com.deppon.foss.module.base.baseinfo.api.server.service.esb.ISyncLoadUnloadTeamService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.LoadAndUnloadSquadEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.PorterEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.AirAgencyCompanyException;
import com.deppon.foss.module.base.baseinfo.api.shared.vo.LoadAndUnloadSquadVo;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.util.define.FossConstants;
/**
 * 装卸车小队action
 * @author 073586-FOSS-LIXUEXING
 * @date 2012-11-27 14:10:10
 * @since
 * @version 0.01
 */
public class LoadAndUnloadSquadAction extends AbstractAction {
	/**
	 * 下面是声明的属性
	 */
	private static final long serialVersionUID = -802246567875971335L;
	//装卸车小队service接口
    private ILoadAndUnloadSquadService loadAndUnloadSquadService;
	//理货员service接口
    private IPorterService porterService;
	//装卸车小队 action使用VO
	private LoadAndUnloadSquadVo objectVo = new LoadAndUnloadSquadVo();
	private ISyncLoadUnloadTeamService syncLoadUnloadTeamService;
	
    public void setSyncLoadUnloadTeamService(
			ISyncLoadUnloadTeamService syncLoadUnloadTeamService) {
		this.syncLoadUnloadTeamService = syncLoadUnloadTeamService;
	}
	/**
     * 日志.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(LoadAndUnloadSquadAction.class);
	/**
     * <p>查询装卸车小队</p> 
     * @author 073586-FOSS-LIXUEXING
     * @date 2012-12-03 19:50:10
     * @return String
     */
	public String queryLoadAndUnloadSquadExactByEntity(){
		LoadAndUnloadSquadEntity entityCondition = objectVo.getLoadAndUnloadSquadEntity();
		// 返回的结果显示在表格中：模糊查询 queryLoadAndUnloadSquadByEntity queryLoadAndUnloadSquadByEntityCount
		// 返回的结果显示在表格中：精确查询 queryLoadAndUnloadSquadExactByEntity queryLoadAndUnloadSquadExactByEntityCount
    	objectVo.setLoadAndUnloadSquadEntityList( loadAndUnloadSquadService.queryLoadAndUnloadSquadByEntity(entityCondition,start, limit));
    	totalCount = loadAndUnloadSquadService.queryLoadAndUnloadSquadByEntityCount(entityCondition);
    	return returnSuccess();
	}
	//理货员 本方法 传入装卸车小队 code查询其下的 所有人员
	public String queryPorterBatchByParentOrgCode(){
    	objectVo.setPorterEntityList( porterService.queryPorterBatchByParentOrgCode(objectVo.getCodeStr()));
    	return returnSuccess();
	}
    /**
     * 作废装卸车小队 
     * @author 073586-FOSS-LIXUEXING
     * @date 2012-11-27 下午19:10:10
     * @return String
     * @see
     */
    public String deleteLoadAndUnloadSquad() {
    	try {
        	objectVo.setLoadAndUnloadSquadEntity(loadAndUnloadSquadService.deleteLoadAndUnloadSquad(objectVo.getLoadAndUnloadSquadEntity()));
        	//同步装卸车小队数据到快递
        	List<LoadAndUnloadSquadEntity> loadAndUnloadSquadEntitys = new ArrayList<LoadAndUnloadSquadEntity>();
        	LoadAndUnloadSquadEntity loadAndUnloadSquadEntity = objectVo.getLoadAndUnloadSquadEntity();
        	loadAndUnloadSquadEntitys.add(loadAndUnloadSquadEntity);
        	syncLoadUnloadTeamService.syncLoadUnloadTeamToEcs(loadAndUnloadSquadEntitys, NumberConstants.NUMBER_3);
        	return returnSuccess();
    	} catch (AirAgencyCompanyException e) {
    	    LOGGER.debug(e.getMessage(), e);
    	    return returnError(e);
    	}
    }
    /**
     * 批量作废装卸车小队 
     * @author 073586-FOSS-LIXUEXING
     * @date 2012-11-27 下午19:10:10
     * @return String
     * @see
     */
    public String deleteLoadAndUnloadSquadMore() {
    	try {
        	objectVo.setLoadAndUnloadSquadEntity(loadAndUnloadSquadService.deleteLoadAndUnloadSquadMore(objectVo.getCodeStr(), FossUserContext.getCurrentInfo().getEmpCode()));
        	//同步装卸车小队数据到快递
        	List<LoadAndUnloadSquadEntity> loadAndUnloadSquadEntitys = new ArrayList<LoadAndUnloadSquadEntity>();
        	if (!ArrayUtils.isEmpty(objectVo.getCodeStr())) {
        		String[] codes = objectVo.getCodeStr();
        		for(int i = 0; i < codes.length ; i++){
        			LoadAndUnloadSquadEntity loadAndUnloadSquadEntity = new LoadAndUnloadSquadEntity();
        			loadAndUnloadSquadEntity.setCode(codes[i]);
        			loadAndUnloadSquadEntity.setActive(FossConstants.INACTIVE);
        			loadAndUnloadSquadEntity.setModifyDate(objectVo.getLoadAndUnloadSquadEntity().getModifyDate());
        			loadAndUnloadSquadEntity.setModifyUser(FossUserContext.getCurrentInfo().getEmpCode());
        			loadAndUnloadSquadEntitys.add(loadAndUnloadSquadEntity);
        		}
    		}
        	syncLoadUnloadTeamService.syncLoadUnloadTeamToEcs(loadAndUnloadSquadEntitys, NumberConstants.NUMBER_3);
        	
        	return returnSuccess();
    	} catch (AirAgencyCompanyException e) {
    	    LOGGER.debug(e.getMessage(), e);
    	    return returnError(e);
    	}
    }
	//理货员
    public String deletePorterEntityMore() {
    	try {
        	delPorterEntity(objectVo.getCodeStr(),objectVo.getLoadAndUnloadSquadEntity());
        	return returnSuccess();
    	} catch (AirAgencyCompanyException e) {
    	    LOGGER.debug(e.getMessage(), e);
    	    return returnError(e);
    	}
    }
	//批量新增 理货员
    private PorterEntity delPorterEntity(String[] codeStr,LoadAndUnloadSquadEntity loadAndUnloadSquadEntity){
    	objectVo.setPorterEntity(porterService.deletePorterMore(codeStr,loadAndUnloadSquadEntity, FossUserContext.getCurrentInfo().getEmpCode()));
    	return objectVo.getPorterEntity();
    }
    /**
     * 修改装卸车小队 
     * @author 073586-FOSS-LIXUEXING
     * @date 2012-11-28 13:50:10
     * @return String
     * @see
     */
    public String updateLoadAndUnloadSquad() {
    	LoadAndUnloadSquadEntity loadAndUnloadSquadEntity = new LoadAndUnloadSquadEntity();
    	try {
    		loadAndUnloadSquadEntity = loadAndUnloadSquadService.updateLoadAndUnloadSquad(objectVo.getLoadAndUnloadSquadEntity());
        	objectVo.setLoadAndUnloadSquadEntity(loadAndUnloadSquadEntity);
        	if(loadAndUnloadSquadEntity != null){
        	addPorterEntity(objectVo.getPorterEntityList(),objectVo.getLoadAndUnloadSquadEntity());
        	objectVo.setPorterEntity(delPorterEntity(objectVo.getCodeStr(),objectVo.getLoadAndUnloadSquadEntity()));
        	//同步装卸车小队数据到快递
        	List<LoadAndUnloadSquadEntity> loadAndUnloadSquadEntitys = new ArrayList<LoadAndUnloadSquadEntity>();
        	//新增的理货员信息
        	loadAndUnloadSquadEntity.setPorters(objectVo.getPorterEntityList());
        	//同步删除的理货员信息
        	if (!ArrayUtils.isEmpty(objectVo.getCodeStr())) {
        		for(String code : objectVo.getCodeStr()){
        			PorterEntity porter = new PorterEntity();
        			porter.setParentOrgCode(loadAndUnloadSquadEntity.getCode());
        			porter.setEmpCode(code);
        			porter.setActive(FossConstants.INACTIVE);
        			loadAndUnloadSquadEntity.getPorters().add(porter);
        		}
    		}
        	loadAndUnloadSquadEntitys.add(loadAndUnloadSquadEntity);
        	syncLoadUnloadTeamService.syncLoadUnloadTeamToEcs(loadAndUnloadSquadEntitys, NumberConstants.NUMBER_2);
        	}
          return returnSuccess();
    	} catch (AirAgencyCompanyException e) {
    	    LOGGER.debug(e.getMessage(), e);
    	    return returnError(e);
    	}
    }
    /**
     * 新增装卸车小队 
     * @author 073586-FOSS-LIXUEXING
     * @date 2012-11-28 13:50:10
     * @return String
     * @see
     */
    public String addLoadAndUnloadSquad() {
    	LoadAndUnloadSquadEntity loadAndUnloadSquadEntity = new LoadAndUnloadSquadEntity();
    	try {
    		loadAndUnloadSquadEntity = loadAndUnloadSquadService
					.addLoadAndUnloadSquad(objectVo
							.getLoadAndUnloadSquadEntity());
        	objectVo.setLoadAndUnloadSquadEntity(loadAndUnloadSquadEntity);
        	addPorterEntity(objectVo.getPorterEntityList(),objectVo.getLoadAndUnloadSquadEntity());
        	
        	return returnSuccess();
    	} catch (AirAgencyCompanyException e) {
    		
    	    LOGGER.debug(e.getMessage(), e);
    	    return returnError(e);
    	}finally{
    		//同步装卸车小队数据到快递
        	List<LoadAndUnloadSquadEntity> loadAndUnloadSquadEntitys = new ArrayList<LoadAndUnloadSquadEntity>();
        	if(loadAndUnloadSquadEntity != null){
        		loadAndUnloadSquadEntity.setPorters(objectVo.getPorterEntityList());
            	loadAndUnloadSquadEntitys.add(loadAndUnloadSquadEntity);
            	syncLoadUnloadTeamService.syncLoadUnloadTeamToEcs(loadAndUnloadSquadEntitys, NumberConstants.NUMBER_1);
               
        	}
        	
    	}
    }
	//理货员
    public String addPorterEntity() {
    	try {
        	addPorterEntity(objectVo.getPorterEntityList(),objectVo.getLoadAndUnloadSquadEntity());
        	return returnSuccess();
    	} catch (AirAgencyCompanyException e) {
    	    LOGGER.debug(e.getMessage(), e);
    	    return returnError(e);
    	}
    }
	//批量新增 理货员
    private List<PorterEntity> addPorterEntity(List<PorterEntity> porterList,LoadAndUnloadSquadEntity loadAndUnloadSquadEntity){
    	for(int i=0;porterList != null && i <porterList.size();i++){
    		porterList.get(i).setParentOrgCode(loadAndUnloadSquadEntity.getCode());
        	objectVo.setPorterEntity(porterService.addPorter(porterList.get(i)));
    	}
    	return porterList;
    }
	/**
     * <p>装卸车小队  是否重复</p> 
     * @author 073586-FOSS-LIXUEXING
     * @date 2012-12-03 19:50:10
     * @return String
     */
    public String loadAndUnloadSquadIsExist() {
    	try {
        	objectVo.setLoadAndUnloadSquadEntityList( loadAndUnloadSquadService.queryLoadAndUnloadSquadExactByEntity(objectVo.getLoadAndUnloadSquadEntity(),0, 1));
        	List<LoadAndUnloadSquadEntity> list = objectVo.getLoadAndUnloadSquadEntityList();
        	objectVo.setLoadAndUnloadSquadEntity((list.size()>0)?list.get(0):null);
        	return returnSuccess();
    	} catch (AirAgencyCompanyException e) {
    	    LOGGER.debug(e.getMessage(), e); 
    	    return returnError(e);
    	}
    }
    /*
     * =================================================================
     * 下面是get,set方法：
     */
	public LoadAndUnloadSquadVo getObjectVo() {
		return objectVo;
	}
	public void setObjectVo(LoadAndUnloadSquadVo objectVo) {
		this.objectVo = objectVo;
	}
	public void setLoadAndUnloadSquadService(
			ILoadAndUnloadSquadService loadAndUnloadSquadService) {
		this.loadAndUnloadSquadService = loadAndUnloadSquadService;
	}
	public void setPorterService(IPorterService porterService) {
		this.porterService = porterService;
	}

}
