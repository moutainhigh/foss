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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/server/service/ISiteGroupSiteService.java
 * 
 * FILE NAME        	: ISiteGroupSiteService.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.baseinfo.api.server.service;

import java.util.List;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SiteGroupSiteEntity;

/**
 * 站点组站点service接口
 * <p style="display:none">
 * modifyRecord
 * </p>
 * <p style="display:none">
 * version:V1.0,author:Administrator,date:2012-10-11 下午5:41:04
 * </p>
 * 
 * @author dp-xieyantao
 * @date 2012-10-11 下午5:41:04
 * @since
 * @version
 */
public interface ISiteGroupSiteService extends IService {

	/**
	 * 新增站点组站点
	 * 
	 * @author dp-xieyantao
	 * @date 2012-10-11 下午5:41:04
	 * @param entitys
	 *            站点组站点实体集合
	 * @return 成功：list 失败：null
	 * @see
	 */
	List<SiteGroupSiteEntity> addSiteGroupSite(List<SiteGroupSiteEntity> entitys);

	/**
	 * 根据code作废站点组站点信息
	 * 
	 * @author dp-xieyantao
	 * @date 2012-10-11 下午5:41:04
	 * @param codeStr
	 *            code字符串
	 * @param modifyUser
	 * @return 1：成功；-1：失败
	 * @see
	 */
	int deleteSiteGroupSiteByCode(String codeStr, String modifyUser);

	/**
	 * <p>
	 * 验证所属站点组虚拟编码、站点序号是否唯一
	 * </p>
	 * 
	 * @author 094463-foss-xieyantao
	 * @date 2012-12-28 上午11:05:08
	 * @param entity
	 * @return
	 * @see
	 */
	SiteGroupSiteEntity queryGroupSiteBySeqVirtualCode(
			SiteGroupSiteEntity entity);
	
	/**
	 * <p>
	 * 验证所属站点组站点是否重复
	 * </p>
	 * 
	 * @author 132599-foss-shenweihua
	 * @date 2013-06-04 上午11:05:08
	 * @param entity
	 * @return
	 * @see
	 */
	SiteGroupSiteEntity queryGroupSiteBySiteCode(
			SiteGroupSiteEntity entity);

	/**
	 * 修改站点组站点信息 对于有版本控制的编辑，先逻辑删除该记录，再新增一条记录
	 * 
	 * @author dp-xieyantao
	 * @date 2012-10-11 下午5:41:04
	 * @param entity
	 * @return
	 * @see
	 */
	SiteGroupSiteEntity updateSiteGroupSite(SiteGroupSiteEntity entity);

	/**
	 * 根据传入对象查询符合条件所有站点组站点信息
	 * 
	 * @author dp-xieyantao
	 * @date 2012-10-11 下午5:41:04
	 * @param entity
	 * @param limit
	 * @param start
	 * @return
	 * @see
	 */
	List<SiteGroupSiteEntity> querySiteGroupSites(SiteGroupSiteEntity entity,
			int limit, int start);

	/**
	 * <p>
	 * 根据站点组虚拟编码查询所有站点
	 * </p>
	 * 
	 * @author 094463-foss-xieyantao
	 * @date 2012-11-8 下午2:32:13
	 * @param siteGroupCode
	 *            站点组虚拟编码
	 * @return
	 * @see
	 */
	List<SiteGroupSiteEntity> queryAllSitesByCode(String siteGroupCode);

	/**
	 * 统计总记录数
	 * 
	 * @author dp-xieyantao
	 * @date 2012-10-11 下午5:41:04
	 * @param entity
	 * @return
	 * @see
	 */
	Long queryRecordCount(SiteGroupSiteEntity entity);

}
