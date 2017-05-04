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
 * PROJECT NAME	: bse-baseinfo-api
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/server/service/IBaseInfoDownloadService.java
 * 
 * FILE NAME        	: IBaseInfoDownloadService.java
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
package com.deppon.foss.module.base.baseinfo.api.server.service;

import java.util.List;

import com.deppon.foss.base.util.ClientUpdateDataPack;
import com.deppon.foss.base.util.DataBundle;
import com.deppon.foss.framework.service.IService;


/**
 * baseinfo 下载服务
 * @author foss-zhujunyong
 * @date Oct 17, 2012 11:12:19 AM
 * @version 1.0
 */
public interface IBaseInfoDownloadService extends IService{

    /**
     * 
     * <p>银行信息数据下载</p> 
     * @author foss-zhujunyong
     * @date Oct 17, 2012 11:22:14 AM
     * @param clientInfo
     * @return
     * @see
     */
    DataBundle downloadBankData(ClientUpdateDataPack clientInfo);

    /**
     * 
     * <p>行政区域数据下载</p> 
     * @author foss-zhujunyong
     * @date Oct 17, 2012 3:39:06 PM
     * @param clientInfo
     * @return
     * @see
     */
    DataBundle downloadDistrictData(ClientUpdateDataPack clientInfo);

    /**
     * 
     * <p>走货路径数据下载</p> 
     * @author foss-zhujunyong
     * @date Oct 17, 2012 3:39:06 PM
     * @param clientInfo
     * @return
     * @see
     */
    DataBundle downloadFreightRouteData(ClientUpdateDataPack clientInfo);

    /**
     * 
     * <p>走货路径线路数据下载</p> 
     * @author foss-zhujunyong
     * @date Oct 17, 2012 3:39:06 PM
     * @param clientInfo
     * @return
     * @see
     */
    DataBundle downloadFreightRouteLineData(ClientUpdateDataPack clientInfo);

    /**
     * 
     * <p>限保物品数据下载</p> 
     * @author foss-zhujunyong
     * @date Oct 17, 2012 3:39:06 PM
     * @param clientInfo
     * @return
     * @see
     */
    DataBundle downloadInsurGoodsData(ClientUpdateDataPack clientInfo);
    
    /**
     * 
     * <p>线路数据下载</p> 
     * @author foss-zhujunyong
     * @date Oct 17, 2012 3:39:06 PM
     * @param clientInfo
     * @return
     * @see
     */
    DataBundle downloadLineData(ClientUpdateDataPack clientInfo);

    /**
     * 
     * <p>按指定营业部批量下载线路数据</p>
     * 过滤指定营业部的始发线路，全部中转线路和全部到达线路
     *  
     * @author foss-zhujunyong
     * @date Mar 21, 2013 9:29:19 AM
     * @param clientInfoList
     * @return
     * @see
     */
    DataBundle downloadLineData(List<ClientUpdateDataPack> clientInfoList);    
    
    /**
     * 
     * <p>线段数据下载</p> 
     * @author foss-zhujunyong
     * @date Jan 15, 2013 2:39:06 PM
     * @param clientInfo
     * @return
     * @see
     */
    DataBundle downloadLineItemData(ClientUpdateDataPack clientInfo);

    /**
     * 
     * <p>网点组数据下载</p> 
     * @author foss-zhujunyong
     * @date Oct 17, 2012 3:39:06 PM
     * @param clientInfo
     * @return
     * @see
     */
    DataBundle downloadNetGroupData(ClientUpdateDataPack clientInfo);
    
    /**
     * 
     * <p>按所给的营业部列表下载网点组数据</p> 
     * @author foss-zhujunyong
     * @date Mar 20, 2013 4:43:27 PM
     * @param clientInfoList
     * @return
     * @see
     */
    DataBundle downloadNetGroupData(List<ClientUpdateDataPack> clientInfoList);    
    
    /**
     * <p>合作伙伴（代理公司）数据下载</p> 
     * @author 094463-foss-xieyantao
     * @date 2012-12-15 下午4:03:23
     * @param clientInfo
     * @return
     * @see
     */
    DataBundle downloadBusinessPartnerData(ClientUpdateDataPack clientInfo);
    
    /**
     * 
     * <p>组织机构数据下载</p> 
     * @author foss-zhujunyong
     * @date Oct 17, 2012 3:39:06 PM
     * @param clientInfo
     * @return
     * @see
     */
    DataBundle downloadOrganizationData(ClientUpdateDataPack clientInfo);
    
    /**
     * 
     * <p>外部网点数据下载</p> 
     * @author foss-zhujunyong
     * @date Oct 17, 2012 3:39:06 PM
     * @param clientInfo
     * @return
     * @see
     */
    DataBundle downloadOuterBranchData(ClientUpdateDataPack clientInfo);
    
    /**
     * 
     * <p>禁运数据下载</p> 
     * @author foss-zhujunyong
     * @date Oct 17, 2012 3:39:06 PM
     * @param clientInfo
     * @return
     * @see
     */
    DataBundle downloadProhibitGoodsData(ClientUpdateDataPack clientInfo);

    /**
     * 
     * <p>营业部数据下载</p> 
     * @author foss-zhujunyong
     * @date Oct 17, 2012 3:39:06 PM
     * @param clientInfo
     * @return
     * @see
     */
    DataBundle downloadSalesDepartmentData(ClientUpdateDataPack clientInfo);

    /**
     * 
     * <p>外场数据下载</p> 
     * @author foss-zhujunyong
     * @date Oct 17, 2012 3:39:06 PM
     * @param clientInfo
     * @return
     * @see
     */
    DataBundle downloadTransferCenterData(ClientUpdateDataPack clientInfo);
    
    /**
     * 
     * <p>库区数据下载</p> 
     * @author foss-zhujunyong
     * @date Oct 17, 2012 3:39:06 PM
     * @param clientInfo
     * @return
     * @see
     */
    DataBundle downloadGoodsAreaData(ClientUpdateDataPack clientInfo);

    /**
     * 
     * <p>权限 数据下载</p> 
     * @author 087584-foss-lijun
     * @date 2012-11-7 下午8:33:40
     * @param clientInfo
     * @return
     * @see
     */
    DataBundle downloadResourceData(ClientUpdateDataPack clientInfo);

    /**
     * 
     * <p>角色 数据下载</p> 
     * @author 087584-foss-lijun
     * @date 2012-11-7 下午8:50:46
     * @param clientInfo
     * @return
     * @see
     */
    DataBundle downloadRoleData(ClientUpdateDataPack clientInfo);
    
    /**
     * 
     * <p>角色权限 数据下载</p> 
     * @author 087584-foss-lijun
     * @date 2012-11-7 下午8:44:27
     * @param clientInfo
     * @return
     * @see
     */
    DataBundle downloadRoleResourceData(ClientUpdateDataPack clientInfo);
    
    /**
     * 
     * 营业部适用产品下面
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-7 下午8:44:27
     * @param clientInfo
     * @return
     * @see
     */
    DataBundle downloadSalesProductData(ClientUpdateDataPack clientInfo);
        

    /** 
     * 财务组织 数据下载 
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-7 下午8:44:27
     * @param clientInfo
     * @return 
     */
    DataBundle downloadFinancialOrganizationsData(ClientUpdateDataPack clientInfo);

    /**
     * <p>"用户信息"数据下载</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-11-7 下午8:01:54
     * @param clientInfo
     * @return
     * @see
     */
    DataBundle downloadUserData(ClientUpdateDataPack clientInfo);
    
    /**
     * <p>"用户组织角色信息"数据下载</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-11-7 下午8:02:31
     * @param clientInfo
     * @return
     * @see
     */
    DataBundle downloadUseOrgRoleData(ClientUpdateDataPack clientInfo);

    /**
     * 
     * <p>"班次信息"数据下载</p> 
     * @author foss-zhujunyong
     * @date Dec 29, 2012 10:17:38 AM
     * @param clientInfo
     * @return
     * @see
     */
    DataBundle downloadDepartureStandardData(ClientUpdateDataPack clientInfo);

    /**
     * 
     * <p>营业部集中开单组关系表下载</p> 
     * @author foss-zhujunyong
     * @date May 2, 2013 2:27:58 PM
     * @param clientInfo
     * @return
     * @see
     */
    DataBundle downloadSalesBillingGroupData(ClientUpdateDataPack clientInfo);
    
    
    /**
     * 
     * <p>营业部标星号</p> 
     * @author foss-shixiaowei 
     * @date May 17, 2013 3:33:33 PM
     * @param clientInfo
     * @return
     * @see
     */
    DataBundle downloadAsteriskSalesDept(ClientUpdateDataPack clientInfo);
    
    /**
     * 
     * <p>营业部集中开单组关系表下载 根据部门为条件分类下载</p> 
     * @author foss-zhujunyong
     * @date May 2, 2013 2:27:58 PM
     * @param clientInfo
     * @return
     * @see
     */
    DataBundle downloadSalesBillingGroupData(List<ClientUpdateDataPack> clientInfoList);
    
    /**
     * 
     * <p>外场和集中开单组关系表下载 根据versionNo下载</p> 
     * @author foss-zhujunyong
     * @date Jun 3, 2013 2:23:58 PM
     * @param clientInfo
     * @return
     * @see
     */
    DataBundle downloadBillingGroupTransferData(ClientUpdateDataPack clientInfo);   
    
    /**
     * 
     * <p>网点组下载</p> 
     * @author foss-zhujunyong
     * @date Jun 17, 2013 1:46:56 PM
     * @param clientInfoList
     * @return
     * @see
     */
    DataBundle downloadNetGroupMixData(List<ClientUpdateDataPack> clientInfoList, String orgType);    

    /**
     * 
     * <p>网点组分页下载</p> 
     * @author foss-zhujunyong
     * @date Jun 17, 2013 1:46:56 PM
     * @param clientInfoList
     * @param orgType ComnConst.ORG_TYPE_SOURCE表示下载出发网点数据，ComnConst.ORG_TYPE_TARGET表示下载到达网点数据
     * @return
     * @see
     */
    DataBundle downloadNetGroupMixData(ClientUpdateDataPack client, String orgType);

	/**
	 * 分页下载描述信息
	 * @param clientInfo
	 * @return
	 */
	DataBundle downloadSalesDescExpandData(ClientUpdateDataPack clientInfo);


}
