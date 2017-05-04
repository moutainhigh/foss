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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/server/service/IExhibitionKeywordService.java
 * 
 * FILE NAME        	: IExhibitionKeywordService.java
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
import com.deppon.foss.module.base.baseinfo.api.shared.domain.ExhibitionKeywordEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.PaginationDto;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.ExhibitionKeywordException;
/**
 * 
 * 展馆关键字信息service接口
 * @author: 189284
 * @time:2014-12-26 上午9:36:41
 * @since
 * @version
 */
public interface IExhibitionKeywordService extends IService {
	
	/**
	 * 
	 * <p>新增展馆关键字信息</p> 
     * @author: 189284
	 * @time:2014-12-26 上午9:36:41
	 * @return
	 * @see
	 */
	 int addExhibitionKeyword(ExhibitionKeywordEntity exhibitionKeyword) throws ExhibitionKeywordException;

	/**
	 * 
	 * <p>作废展馆关键字信息</p> 
	 * @author: 189284
	 * @time:2014-12-26 上午9:36:41
	 * @param exhibitionKeyword
	 * @return
	 * @see
	 */
	int deleteExhibitionKeyword(List<String> ids, String modifyUser) throws ExhibitionKeywordException;
	

	/**
	 * 
	* @Title: updateExhibitionKeyword 
	* @Description: 修改 展馆关键字信息
	* @param  exhibitionKeyword
	* @throws ExhibitionKeywordException    
	* @return int    返回类型 
	* @author 189284 ZhangXu
	* @date 2014-12-27 下午2:28:02 
	* @throws
	 */
	int updateExhibitionKeyword(ExhibitionKeywordEntity exhibitionKeyword) throws ExhibitionKeywordException;
	
	/**
	 * 
	* @Title: queryExhibitionKeyword 
	* @Description: 查询 展馆关键字信息 
	* @param  exhibitionKeyword
	* @throws ExhibitionKeywordException    设定文件 
	* @return List<ExhibitionKeywordEntity>    返回类型 
	* @author 189284 ZhangXu
	* @date 2014-12-27 下午2:28:48 
	* @throws
	 */
	List<ExhibitionKeywordEntity> queryExhibitionKeyword(ExhibitionKeywordEntity exhibitionKeyword) throws ExhibitionKeywordException;
	
	/**
	 * 
	* @Title: queryExhibitionKeywordBySelective 
	* @Description: 查询   展馆关键字信息 
	* @param  id 
	* @return ExhibitionKeywordEntity    返回类型 
	* @author 189284 ZhangXu
	* @date 2014-12-27 下午2:35:03 
	* @throws
	 */
	ExhibitionKeywordEntity queryExhibitionKeywordBySelective(String id);
	
	/**
	 * 
	* @Title: queryExhibitionKeywordDtoListBySelectiveCondition 
	* @Description: 分页 查询 展馆关键字信息  
	* @param  sa
	* @param  offset
	* @param  limit
	*  @throws ExhibitionKeywordException    设定文件 
	* @return PaginationDto    返回类型 
	* @author 189284 ZhangXu
	* @date 2014-12-27 下午2:35:38 
	* @throws
	 */
	PaginationDto queryExhibitionKeywordDtoListBySelectiveCondition(ExhibitionKeywordEntity sa, int offset, int limit) throws ExhibitionKeywordException;
	/**
     * 
     * <p>查询出展馆关键字信息  </p> 
     * @author: 189284
	 * @time:2014-12-26 上午9:36:41
     * @param sa
     * @param offset
     * @param limit
     * @return
     * @throws ExhibitionKeywordException
     * @see
     */
	PaginationDto queryExhibitionKeywordListBySelectiveCondition(ExhibitionKeywordEntity sa, int offset, int limit) throws ExhibitionKeywordException ;
	/**
	 * 
	* @Title: queryExhibitionKeywordListByTargetOrgName 
	* @Description: 根据目的站地址查询目的站地址是否含有展馆关键字信息
	*  @param condition
	* @return List<ExhibitionKeywordEntity> 
	* @author 189284 ZhangXu
	* @date 2014-12-29 下午4:40:51 
	* @throws
	 */
	List<ExhibitionKeywordEntity> queryExhibitionKeywordListByTargetOrgName(String condition);
}
