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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/action/SpecialAddressAction.java
 * 
 * FILE NAME        	: SpecialAddressAction.java
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
import com.deppon.foss.module.base.baseinfo.api.server.service.ISpecialAddressService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SpecialAddressEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.PaginationDto;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.SpecialAddressException;
import com.deppon.foss.module.base.baseinfo.api.shared.vo.SpecialAddressVo;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
/**
 * 
 * 用来响应"特殊地址"的Action类
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:何波,date:2013-2-20 下午2:20:19</p>
 * @author 何波
 * @date 2013-2-20 下午2:20:19
 * @since
 * @version
 */
public class SpecialAddressAction extends AbstractAction {

    private static final Logger log = Logger.getLogger(SpecialAddressAction.class);

    private static final long serialVersionUID = -7813314294109040394L;

	//特殊地址服务接口
	private ISpecialAddressService specialAddressService;
    //接收前台数据VO
	private SpecialAddressVo specialAddressVo = new SpecialAddressVo();
	

	public void setSpecialAddressService(
			ISpecialAddressService specialAddressService) {
		this.specialAddressService = specialAddressService;
	}
	
	/**
	 * @return  the specialAddressVo
	 */
	public SpecialAddressVo getSpecialAddressVo() {
	    return specialAddressVo;
	}

	/**
	 * @param specialAddressVo the specialAddressVo to set
	 */
	public void setSpecialAddressVo(SpecialAddressVo specialAddressVo) {
	    this.specialAddressVo = specialAddressVo;
	}



	/**
	 * 
	 * <p> 获取当前用户</p>
	 * @author 何波
	 * @date 2013-1-31 下午4:12:45
	 * @return
	 * @see
	 */
	private UserEntity getCurrentUser() {
		UserEntity user = FossUserContext.getCurrentUser();
		return null == user ? new UserEntity() : user;
	}

	/**
	 * 
	 * <p>新增特殊地址</p> 
	 * @author 何波
	 * @date 2013-2-20 下午2:16:19
	 * @return
	 * @see
	 */
	@JSON
	public String addSpecialAddress() {
	
		SpecialAddressEntity specialAddressEntity = specialAddressVo
				.getSpecialAddress();
		specialAddressEntity.setDescription(specialAddressEntity.getDescNote());
		String createUser = getCurrentUser().getEmployee().getEmpCode();
		specialAddressEntity.setCreateUser(createUser);
		try {
			specialAddressService.addSpecialAddress(specialAddressEntity);
			return returnSuccess(SpecialAddressException.SPECIALADDRESS_ADD_SUCCESS);
		} catch (SpecialAddressException e) {
			log.error("eeeeeee", e);
			return returnError(e);
		}

	}

	/**
	 * 
	 * <p>作废特殊地址</p> 
	 * @author 何波
	 * @date 2013-2-20 下午2:16:48
	 * @return
	 * @see
	 */
	@JSON
	public String deleteSpecialAddress() {
		List<String> ids = getSpecialAddressVo().getBatchIds();
		String createUser = getCurrentUser().getEmployee().getEmpCode();
		try {
			specialAddressService.deleteSpecialAddress(ids,createUser);
			return returnSuccess(SpecialAddressException.SPECIALADDRESS_DEL_SUCCESS);
		} catch (SpecialAddressException e) {
			return returnError(e);
		}

	}
    /**
     * 
     * <p>修改特殊地址</p> 
     * @author 何波
     * @date 2013-2-20 下午2:17:13
     * @return
     * @see
     */
	@JSON
	public String updateSpecialAddress() {

		SpecialAddressEntity specialAddressEntity = specialAddressVo.getSpecialAddress();
		specialAddressEntity.setDescription(specialAddressEntity.getDescNote());
		
		String createUser = getCurrentUser().getEmployee().getEmpCode();
		specialAddressEntity.setCreateUser(createUser);
		try {
			specialAddressService.updateSpecialAddress(specialAddressEntity);
			return returnSuccess(SpecialAddressException.SPECIALADDRESS_UPD_SUCCESS);
		} catch (SpecialAddressException e) {
			return	returnError(e);
		}

	}

	/**
	 * 
	 * <p>查询出满足条件的特殊地址</p> 
	 * @author 何波
	 * @date 2013-2-20 下午2:17:40
	 * @return
	 * @see
	 */
	@SuppressWarnings("unchecked")
	@JSON
	public String querySpecialAddress() {
		SpecialAddressEntity specialAddressEntity = getSpecialAddressVo()
				.getSpecialAddress();
		try {
			PaginationDto paginationDto = specialAddressService.querySpecialAddressDtoListBySelectiveCondition(specialAddressEntity, start, limit);
			specialAddressVo.setSpecialAddressList(paginationDto.getPaginationDtos());
			setTotalCount(paginationDto.getTotalCount());
			return returnSuccess();
		} catch (SpecialAddressException e) {
			return returnError(e);
		}

	}

}
