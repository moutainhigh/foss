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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/action/ExhibitionKeywordAction.java
 * 
 * FILE NAME        	: ExhibitionKeywordAction.java
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

import java.util.List;

import org.apache.log4j.Logger;

import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.module.base.baseinfo.api.server.service.IExhibitionKeywordService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.ExhibitionKeywordEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.PaginationDto;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.ExhibitionKeywordException;
import com.deppon.foss.module.base.baseinfo.api.shared.vo.ExhibitionKeywordVo;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
/**
 * 
 *展馆关键字信息Action
 *@Date： 2014-12-25下午5:26:27
 *@author： 189284
 *@since
 *@version
 */
public class ExhibitionKeywordAction extends AbstractAction {

    private static final Logger log = Logger.getLogger(ExhibitionKeywordAction.class);

    private static final long serialVersionUID = -7813314294109040394L;

	//展馆关键字信息接口
	private IExhibitionKeywordService exhibitionKeywordService;
    //接收前台数据VO               
	private ExhibitionKeywordVo exhibitionKeywordVo;
	
	public void setExhibitionKeywordService(
			IExhibitionKeywordService exhibitionKeywordService) {
		this.exhibitionKeywordService = exhibitionKeywordService;
	}
	public ExhibitionKeywordVo getExhibitionKeywordVo() {
		return exhibitionKeywordVo;
	}
	public void setExhibitionKeywordVo(ExhibitionKeywordVo exhibitionKeywordVo) {
		this.exhibitionKeywordVo = exhibitionKeywordVo;
	}
	/**
	 * 
	 * @Description:<p> 获取当前用户</p>
	 * @author: 189284
	 * @time:2014-12-26 上午9:36:41
	 *@see
	 */
	private UserEntity getCurrentUser() {
		UserEntity user = FossUserContext.getCurrentUser();
		return null == user ? new UserEntity() : user;
	}
	/**
	 * 
	 * <p>新增展馆关键字信息</p> 
     * @author: 189284
	 * @time:2014-12-26 上午9:36:41
	 * @return
	 * @see
	 */
	@JSON
	public String addExhibitionKeyword() {
	
		ExhibitionKeywordEntity exhibitionKeywordEntity = exhibitionKeywordVo
				.getExhibitionKeyword();
		try {
			/**
			 * 新增展馆关键字信息
			 */
			exhibitionKeywordService.addExhibitionKeyword(exhibitionKeywordEntity);
			return returnSuccess(ExhibitionKeywordException.EXHIBITIONKEYWORD_ADD_SUCCESS);
		} catch (ExhibitionKeywordException e) {
			log.error("新增展馆关键字信息错误！", e);
			return returnError(e);
		}

	}

	/**
	* @Description: 作废展馆关键字信息
	* @return String
	* @author 189284 ZhangXu
	* @date 2014-12-27 下午1:50:14 
	* @throws
	 */
	@JSON
	public String deleteExhibitionKeyword() {
		List<String> ids = getExhibitionKeywordVo().getBatchIds();
		String createUser = getCurrentUser().getEmployee().getEmpCode();
		try {
			exhibitionKeywordService.deleteExhibitionKeyword(ids,createUser);
			return returnSuccess(ExhibitionKeywordException.EXHIBITIONKEYWORD_DEL_SUCCESS);
		} catch (ExhibitionKeywordException e) {
			return returnError(e);
		}

	}
    /**
     * 
    * @Title: updateExhibitionKeyword 
    * @Description: 修改 展馆关键字信息
    * @param
    * @return String   
    * @author 189284 ZhangXu
    * @date 2014-12-27 下午1:51:18 
    * @throws
     */
	@JSON
	public String updateExhibitionKeyword() {

		ExhibitionKeywordEntity exhibitionKeywordEntity = exhibitionKeywordVo.getExhibitionKeyword();
		//ExhibitionKeywordEntity.setDescription(ExhibitionKeywordEntity.getDescNote());
		try {
			exhibitionKeywordService.updateExhibitionKeyword(exhibitionKeywordEntity);
			return returnSuccess(ExhibitionKeywordException.EXHIBITIONKEYWORD_UPD_SUCCESS);
		} catch (ExhibitionKeywordException e) {
			return	returnError(e);
		}

	}

	/**
	* @Description:查询 展馆关键字信息
	* @return String    分页
	* @author 189284 ZhangXu
	* @date 2014-12-27 下午1:51:41 
	* @throws
	 */
	@SuppressWarnings("unchecked")
	@JSON
	public String queryExhibitionKeyword() {
		/**
		 * 获取 查询实体
		 */
		ExhibitionKeywordEntity exhibitionKeywordEntity = getExhibitionKeywordVo().getExhibitionKeyword();
		try {
			PaginationDto paginationDto = exhibitionKeywordService.queryExhibitionKeywordDtoListBySelectiveCondition(exhibitionKeywordEntity, start, limit);
			exhibitionKeywordVo.setExhibitionKeywordList(paginationDto.getPaginationDtos());
			setTotalCount(paginationDto.getTotalCount());
			return returnSuccess();
		} catch (ExhibitionKeywordException e) {
			return returnError(e);
		}

	}

}
