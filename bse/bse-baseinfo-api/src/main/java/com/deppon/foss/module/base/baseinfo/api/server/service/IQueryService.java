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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/server/service/IQueryService.java
 * 
 * FILE NAME        	: IQueryService.java
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

/**
 * 
 * 
*******************************************
* <b style="font-family:微软雅黑"><small>Description:公共选择器接口</small></b>   </br>
* <b style="font-family:微软雅黑"><small>HISTORY</small></b></br>
* <b style="font-family:微软雅黑"><small> ID      DATE    PERSON     REASON</small></b><br>
********************************************
* <div style="font-family:微软雅黑,font-size:70%"> 
 * 1 2012-09-18 钟庭杰 新增 
* </div>  
********************************************
 */
public interface IQueryService<Q,R> extends IService{

	/**
	 * 通过查询对象进行数据的查询
	 * query
	 * @param queryObject 查询对象
	 * @return
	 * @return List<R>
	 * @since:
	 */
	 List<R> query(Q queryObject);
	
	/**
	 * 通过查询对象进行数据的分页查询
	 * query
	 * @param queryObject 查询对象
	 * @param limit 分页条数
	 * @param start 开始条数
	 * @return
	 * @return List<R>
	 * @since:
	 */
	 List<R> query(Q queryObject,int limit,int start);
	
	/**
	 * 统计数据的条数
	 * totalCount
	 * @param queryObject 查询对象
	 * @return
	 * @return Long
	 * @since:
	 */
	 Long totalCount(Q queryObject);
}
