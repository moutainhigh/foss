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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/server/dao/ISpecialAddressDao.java
 * 
 * FILE NAME        	: ISpecialAddressDao.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.baseinfo.api.server.dao;

import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.ExhibitionKeywordEntity;
/**
 * 
 * 展馆关键字信息DAO接口
* @author 189284 ZhangXu
* @date 2014-12-27 上午11:27:09 
 * @since
 * @version
 */
public interface IExhibitionKeywordDao {

	/**
	 * 
	* @Title: addExhibitionKeyword 
	* @Description: 新增展馆关键字信息 
	* @param  exhibitionKeyword
	* @return int    返回类型 
	* @author 189284 ZhangXu
	* @date 2014-12-27 上午11:27:42 
	* @throws
	 */
	 int addExhibitionKeyword(ExhibitionKeywordEntity exhibitionKeyword);

	/**
	* @Title: deleteExhibitionKeyword 
	* @Description: 作废展馆关键字信息
	* @param exhibitionKeyword
	* @return int    返回类型 
	* @author 189284 ZhangXu
	* @date 2014-12-27 上午11:27:42  
	* @throws
	 */
	int deleteExhibitionKeyword(ExhibitionKeywordEntity exhibitionKeyword);
	
	/**
	* @Title: updateExhibitionKeyword 
	* @Description: 修改展馆关键字信息
	* @param exhibitionKeyword
	* @return int    返回类型 
	* @author 189284 ZhangXu
	* @date 2014-12-27 上午11:27:42 
	* @throws
	 */
	int updateExhibitionKeyword(ExhibitionKeywordEntity exhibitionKeyword);
	
	/**
	 *
	* @Title: queryExhibitionKeyword 
	* @Description: 查询 展馆关键字信息（关键字和地址-模糊-匹配） 
	* @param @param exhibitionKeyword
	* @return List<ExhibitionKeywordEntity>    返回类型 
    * @author 189284 ZhangXu
	* @date 2014-12-27 上午11:27:42 
	* @throws
	 */
	List<ExhibitionKeywordEntity> queryExhibitionKeyword(ExhibitionKeywordEntity exhibitionKeyword);
	/**
	 * 
	 * <p>查询 展馆关键字信息（关键字和地址-精确-匹配） </p> 
	 * @author 189284-ZhangXu
	 * @date 2014-12-29 下午5:21:35
	 * @param ExhibitionKeyword
	 * @return
	 * @see
	 */
	List<ExhibitionKeywordEntity> queryExhibitionKeywordByExact(ExhibitionKeywordEntity exhibitionKeyword);
	/**
	 * 
	 * <p>查询 展馆关键字信息（关键字和地址-精确-匹配）id不等于 </p> 
	 * @author 189284-ZhangXu
	 * @date 2014-12-29 下午5:21:35
	 * @param ExhibitionKeyword
	 * @return
	 * @see
	 */
	List<ExhibitionKeywordEntity> queryExhibitionKeywordNotId(ExhibitionKeywordEntity exhibitionKeyword);
	/**
	 * 
	* @Title: queryExhibitionKeywordBySelective 
	* @Description: 查询 展馆关键字信息 根据ID
	* @param id
	* @return ExhibitionKeywordEntity    返回类型 
	* @author 189284 ZhangXu
	* @date 2014-12-27 上午11:27:42  
	* @throws
	 */
	ExhibitionKeywordEntity queryExhibitionKeywordBySelective(String id);
	
	/**
	 * 
	* @Title: queryExhibitionKeywordListBySelectiveCondition 
	* @Description: 分页查询 展馆关键字信息 
	*  @param exhibitionKeyword
	*  @param offset
	*  @param limit
	* @return List<ExhibitionKeywordEntity>    返回类型 
	* @author 189284 ZhangXu
	* @date 2015-1-5 上午10:58:14 
	* @throws
	 */
	List<ExhibitionKeywordEntity> queryExhibitionKeywordListBySelectiveCondition(ExhibitionKeywordEntity exhibitionKeyword, int offset, int limit);
	
	/**
	 * 
	* @Title: queryExhibitionKeywordCountBySelectiveCondition 
	* @Description: 展馆关键字信息  总数
	* @param  exhibitionKeyword
	* @return Long    返回类型 
	* @author 189284 ZhangXu
	* @date 2014-12-27 上午11:27:42  
	* @throws
	 */
	Long queryExhibitionKeywordCountBySelectiveCondition(ExhibitionKeywordEntity exhibitionKeyword);
	/**
	 * 
	* @Title: queryExhibitionKeywordListByTargetOrgName 
	* @Description:根据目的站地址查询目的站地址是否含有展馆关键字信息
	* @param  condition
	* @return List<ExhibitionKeywordEntity>    返回类型 
	* @author 189284 ZhangXu
	* @date 2014-12-29 下午5:24:23 
	* @throws
	 */
	List<ExhibitionKeywordEntity> queryExhibitionKeywordListByTargetOrgName(String condition);
	
}
