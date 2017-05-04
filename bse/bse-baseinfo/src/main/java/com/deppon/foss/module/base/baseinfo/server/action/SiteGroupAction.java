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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/action/SiteGroupAction.java
 * 
 * FILE NAME        	: SiteGroupAction.java
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
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.collections.MapUtils;

import com.deppon.foss.base.util.define.BizTypeConstants;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.context.UserContext;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.module.base.baseinfo.api.server.service.ILineService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.server.service.ISiteGroupService;
import com.deppon.foss.module.base.baseinfo.api.server.service.ISiteGroupSiteService;
import com.deppon.foss.module.base.baseinfo.api.server.service.complex.IOrgAdministrativeInfoComplexService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SiteGroupEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SiteGroupSiteEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.MapDto;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.MessageType;
import com.deppon.foss.module.base.baseinfo.api.shared.vo.SiteGroupVo;
import com.deppon.foss.util.define.FossConstants;

/**
 * 站点组ACTION.
 * 
 * @author 078838-foss-zhangbin
 * @date 2012-11-7
 * @version 1.0
 */
public class SiteGroupAction extends AbstractAction {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 2883644272419312426L;

    /**
     * 前后台传的参数.
     */
    private SiteGroupVo siteGroupVo = new SiteGroupVo();

    /**
     * 线路服务类.
     */
    private ILineService lineService;

    /**
     * 站点service.
     */
    private ISiteGroupSiteService siteGroupSiteService;

    /**
     * 站点组service.
     */
    private ISiteGroupService siteGroupService;

    /**
     * 组织service.
     */
    private IOrgAdministrativeInfoService orgAdministrativeInfoService;
    
    /**
     * 查询组织service.
     */
    private IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService;
    
	/**
     * 设置查询 组织service.
     * 
     * @param orgAdministrativeInfoComplexService
     *            
     */
    public void setOrgAdministrativeInfoComplexService(
			IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService) {
		this.orgAdministrativeInfoComplexService = orgAdministrativeInfoComplexService;
	}

	/**
     * 设置 组织service.
     * 
     * @param orgAdministrativeInfoService
     *            the new 组织service
     */
    public void setOrgAdministrativeInfoService(
	    IOrgAdministrativeInfoService orgAdministrativeInfoService) {
	this.orgAdministrativeInfoService = orgAdministrativeInfoService;
    }

    /**
     * 设置 线路服务类.
     * 
     * @param lineService
     *            the lineService to set
     */
    public void setLineService(ILineService lineService) {
	this.lineService = lineService;
    }

    /**
     * 获取 前后台传的参数.
     * 
     * @return the 前后台传的参数
     */
    public SiteGroupVo getSiteGroupVo() {
	return siteGroupVo;
    }

    /**
     * 设置 前后台传的参数.
     * 
     * @param siteGroupVo
     *            the new 前后台传的参数
     */
    public void setSiteGroupVo(SiteGroupVo siteGroupVo) {
	this.siteGroupVo = siteGroupVo;
    }

    /**
     * 设置 站点组service.
     * 
     * @param siteGroupService
     *            the new 站点组service
     */
    public void setSiteGroupService(ISiteGroupService siteGroupService) {
	this.siteGroupService = siteGroupService;
    }

    /**
     * 设置 站点service.
     * 
     * @param siteGroupSiteService
     *            the new 站点service
     */
    public void setSiteGroupSiteService(
	    ISiteGroupSiteService siteGroupSiteService) {
	this.siteGroupSiteService = siteGroupSiteService;
    }

    /**
     * .
     * <p>
     * 查询所有的站点组<br/>
     * 方法名：searchSiteGroupByCondition
     * </p>
     * 
     * @return
     * @author 078838-foss-zhangbin
     * @时间 2012-11-07
     * @since JDK1.6
     */
    @JSON
    public String searchSiteGroupByCondition() {
	try {
	    //根据传入对象查询符合条件所有站点组信息
	    List<SiteGroupEntity> siteGroupEntityList = siteGroupService
		    .querySiteGroups(siteGroupVo.getSiteGroupEntity(), limit,
			    start);
	    //设置 站点组List.
	    siteGroupVo.setSiteGroupEntityList(siteGroupEntityList);
	    //设置总记录数
	    this.setTotalCount(siteGroupService.queryRecordCount(siteGroupVo
		    .getSiteGroupEntity()));
	    return returnSuccess();
	} catch (BusinessException e) {
	    return returnError(e);
	}
    }
    
    /**
     * .
     * <p>
     * 查询上级外场<br/>
     * 方法名：searchUpOrgAdministrativeByCondition
     * </p>
     * 
     * @return
     * @author 132599-foss-shenweihua
     * @时间 2013-06-02
     * @since JDK1.6
     */
    @SuppressWarnings("null")
	@JSON
    public String searchUpOrgAdministrativeByCondition() {
    	try {
    		List<String> bizTypes = new ArrayList<String>();
    		bizTypes.add(BizTypeConstants.ORG_TRANSFER_CENTER);
    	    //根据传入对象查询符合条件所有站点组信息
    		 OrgAdministrativeInfoEntity entity = orgAdministrativeInfoComplexService.queryOrgAdministrativeInfoIncludeSelfByCode(siteGroupVo.getDeptCode(),bizTypes);
    		if(entity!=null){
    			 siteGroupVo.setDeptCode(entity.getCode());
        		 siteGroupVo.setDeptName(entity.getName());
    		}else{
    			return returnError("该员工账号所对应的的上级外场不存在，故不能新增！");
    		}
    		
    	    return returnSuccess();
    	} catch (BusinessException e) {
    	    return returnError(e);
    	}
        }

    /**
     * .
     * <p>
     * 查询外场<br/>
     * 方法名：searchOrgAdministrativeByCondition
     * </p>
     * 
     * @return
     * @author 078838-foss-zhangbin
     * @时间 2012-11-07
     * @since JDK1.6
     */
    @JSON
    public String searchOrgAdministrativeByCondition() {
	try {
	    //获取 部门.
	    OrgAdministrativeInfoEntity orgAdministrativeInfoEntity = siteGroupVo
		    .getOrgAdministrativeInfoEntity();
	    //设置外场属性
	    orgAdministrativeInfoEntity.setTransferCenter(FossConstants.YES);
	    //精确查询-查询总条数，用于分页 动态的查询条件
	    List<OrgAdministrativeInfoEntity> orgAdministrativeInfoEntityList = orgAdministrativeInfoService
		    .queryOrgAdministrativeInfoExactByEntity(
			    orgAdministrativeInfoEntity, start, limit);
	    //设置 部门LIST.
	    siteGroupVo
		    .setOrgAdministrativeInfoEntityList(orgAdministrativeInfoEntityList);
	    //设置记录总数
	    this.setTotalCount(orgAdministrativeInfoService
		    .queryOrgAdministrativeInfoExactByEntityCount(orgAdministrativeInfoEntity));
	    return returnSuccess();
	} catch (BusinessException e) {
	    return returnError(e);
	}
    }

    /**
     * .
     * <p>
     * 作废站点组<br/>
     * 方法名：deleteSiteGroup
     * </p>
     * 
     * @return
     * @author 078838-foss-zhangbin
     * @时间 2012-11-07
     * @since JDK1.6
     */
    @JSON
    public String deleteSiteGroup() {
	try {
	    // 获取当前登录用户
	    UserEntity user = (UserEntity) UserContext.getCurrentUser();
	    // 当前登录用户empcode
	    String userCode = user.getEmployee().getEmpCode();
	    //根据code作废站点组信息
	    siteGroupService.deleteSiteGroupByCode(siteGroupVo.getCodeStr(),
		    userCode);
	    //返回消息
	    return returnSuccess(MessageType.DELETE_SITEGROUP_SUCCESS);
	} catch (BusinessException e) {
	    return returnError(e);
	}
    }

    /**
     * .
     * <p>
     * 作废站点<br/>
     * 方法名：deleteSiteGroupSite
     * </p>
     * 
     * @return
     * @author 078838-foss-zhangbin
     * @时间 2012-11-07
     * @since JDK1.6
     */
    @JSON
    public String deleteSiteGroupSite() {
	try {
	    // 获取当前登录用户
	    UserEntity user = (UserEntity) UserContext.getCurrentUser();
	    // 当前登录用户empcode
	    String userCode = user.getEmployee().getEmpCode();
	    //根据code作废站点组站点信息
	    siteGroupSiteService.deleteSiteGroupSiteByCode(
		    siteGroupVo.getCodeStr(), userCode);
	    //返回消息
	    return returnSuccess(MessageType.DELETE_SITEGROUPSITE_SUCCESS);
	} catch (BusinessException e) {
	    return returnError(e);
	}
    }

    /**
     * .
     * <p>
     * 批量新增站点<br/>
     * 方法名：addSiteGroupSiteList
     * </p>
     * 
     * @return
     * @author 078838-foss-zhangbin
     * @时间 2012-11-07
     * @since JDK1.6
     */
    @JSON
    public String addSiteGroupSiteList() {
	try {
	    //新增站点组站点
	    List<SiteGroupSiteEntity> siteGroupSiteEntityList = siteGroupSiteService
		    .addSiteGroupSite(siteGroupVo.getSiteGroupSiteEntityList());
	    //设置 站点List.
	    siteGroupVo.setSiteGroupSiteEntityList(siteGroupSiteEntityList);
	    //返回消息
	    return returnSuccess(MessageType.SAVE_SITEGROUPSITE_SUCCESS);
	} catch (BusinessException e) {
	    return returnError(e);
	}
    }

    /**
     * .
     * <p>
     * 新增站点组<br/>
     * 方法名：addSiteGroup
     * </p>
     * 
     * @return
     * @author 078838-foss-zhangbin
     * @时间 2012-11-07
     * @since JDK1.6
     */
    @JSON
    public String addSiteGroup() {
	try {
	    //新增站点组
	    SiteGroupEntity siteGroupEntity = siteGroupService
		    .addSiteGroup(siteGroupVo.getSiteGroupEntity());
	    //设置 站点.
	    siteGroupVo.setSiteGroupEntity(siteGroupEntity);
	    //返回消息
	    return returnSuccess(MessageType.SAVE_SITEGROUP_SUCCESS);
	} catch (BusinessException e) {
	    return returnError(e);
	}
    }

    /**
     * .
     * <p>
     * 修改站点组<br/>
     * 方法名：addSiteGroup
     * </p>
     * 
     * @return
     * @author 078838-foss-zhangbin
     * @时间 2012-11-07
     * @since JDK1.6
     */
    @JSON
    public String updateSiteGroup() {
	try {
	    //修改站点组信息
	    SiteGroupEntity siteGroupEntity = siteGroupService
		    .updateSiteGroup(siteGroupVo.getSiteGroupEntity());
	    //设置 站点.
	    siteGroupVo.setSiteGroupEntity(siteGroupEntity);
	    //返回消息
	    return returnSuccess(MessageType.UPDATE_SITEGROUP_SUCCESS);
	} catch (BusinessException e) {
	    return returnError(e);
	}
    }

    /**
     * .
     * <p>
     * 修改站点<br/>
     * 方法名：updateSiteGroupSite
     * </p>
     * 
     * @return
     * @author 078838-foss-zhangbin
     * @时间 2012-11-07
     * @since JDK1.6
     */
    @JSON
    public String updateSiteGroupSite() {
	try {
	    //修改站点组站点信息 
	    SiteGroupSiteEntity siteGroupSiteEntity = siteGroupSiteService
		    .updateSiteGroupSite(siteGroupVo.getSiteGroupSiteEntity());
	    //设置 站点.
	    siteGroupVo.setSiteGroupSiteEntity(siteGroupSiteEntity);
	    //返回消息
	    return returnSuccess(MessageType.UPDATE_SITEGROUPSITE_SUCCESS);
	} catch (BusinessException e) {
	    return returnError(e);
	}
    }

    /**
     * .
     * <p>
     * 查询站点组相关信息<br/>
     * 方法名：saerchSiteGroupInfo
     * </p>
     * 
     * @return
     * @author 078838-foss-zhangbin
     * @时间 2012-11-07
     * @since JDK1.6
     */
    @JSON
    public String saerchSiteGroupInfo() {
	try {
	    //根据站点组虚拟编码查询所有站点 
	    List<SiteGroupSiteEntity> siteGroupSiteEntityList = siteGroupSiteService
		    .queryAllSitesByCode(siteGroupVo.getSiteGroupVirtualCode());
	    //设置 站点List.
	    siteGroupVo.setSiteGroupSiteEntityList(siteGroupSiteEntityList);
	    //根据站点组虚拟编码查询站点组信息 
	    SiteGroupEntity siteGroupEntity = siteGroupService
		    .querySiteGroupByCode(siteGroupVo.getSiteGroupVirtualCode());
	    //设置 站点.
	    siteGroupVo.setSiteGroupEntity(siteGroupEntity);
	    //返回消息
	    return returnSuccess();
	} catch (BusinessException e) {
	    return returnError(e);
	}
    }

    /**
     * <p>
     * 查询到达传入参数站点的站点列表
     * </p>
     * .
     * 
     * @return
     * @author 094463-foss-xieyantao
     * @date 2013-1-22 下午3:26:31
     * @see
     */
    @JSON
    public String queryArriveGroupSites() {
	try {
	    //查询以指定站点为终点的，所有能出发的所有站点列表
	    Map<String, String> map = lineService.querySourceSiteForSiteGroup(
		    siteGroupVo.getDeptCode(), siteGroupVo.getSiteName());
	    //把Map转换成MapDto列表 
	    List<MapDto> mapDtoList = convertMapToDtoList(map);
	    //设置 站点组选择的站点列表.
	    siteGroupVo.setMapDtoList(mapDtoList);
	    //返回消息
	    return returnSuccess();
	} catch (BusinessException e) {
	    return returnError(e);
	}
    }

    /**
     * <p>
     * 查询从传入参数站点出发的站点列表
     * </p>
     * .
     * 
     * @return
     * @author 094463-foss-xieyantao
     * @date 2013-1-22 下午3:26:31
     * @see
     */
    @JSON
    public String queryLeaveGroupSites() {
	try {
	    //查询以指定站点为起点的，所有能到达的所有站点列表
	    Map<String, String> map = lineService.queryTargetSiteForSiteGroup(
		    siteGroupVo.getDeptCode(), siteGroupVo.getSiteName());
	    //把Map转换成MapDto列表 
	    List<MapDto> mapDtoList = convertMapToDtoList(map);
	    //设置 站点组选择的站点列表.
	    siteGroupVo.setMapDtoList(mapDtoList);
	    //返回消息
	    return returnSuccess();
	} catch (BusinessException e) {
	    return returnError(e);
	}
    }

    /**
     * <p>
     * 把Map转换成MapDto列表
     * </p>
     * .
     * 
     * @param map
     * @return
     * @author foss-zhujunyong
     * @date Jan 21, 2013 5:35:28 PM
     * @see
     */
    private List<MapDto> convertMapToDtoList(Map<String, String> map) {
	//定义一个存放MapDto集合
	List<MapDto> result = new ArrayList<MapDto>();
	if (MapUtils.isEmpty(map)) {
	    return result;
	}
	for (Entry<String, String> entry : map.entrySet()) {
	    MapDto dto = new MapDto();
	    //设置编码
	    dto.setCode(entry.getKey());
	    //设置名称
	    dto.setName(entry.getValue());
	    //把dto存放到集合
	    result.add(dto);
	}
	//返回把Map转换成MapDto列表
	return result;
    }
    
    

}
