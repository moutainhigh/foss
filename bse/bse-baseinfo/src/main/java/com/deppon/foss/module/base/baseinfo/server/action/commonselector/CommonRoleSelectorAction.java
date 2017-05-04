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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/action/commonselector/CommonRoleSelectorAction.java
 * 
 * FILE NAME        	: CommonRoleSelectorAction.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
/*
 * PROJECT NAME: bse-baseinfo
 * PACKAGE NAME: com.deppon.foss.module.base.baseinfo.server.action
 * FILE    NAME: CommonRoleSelectorAction.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */
package com.deppon.foss.module.base.baseinfo.server.action.commonselector;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.util.CollectionUtils;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.module.base.baseinfo.api.server.service.IRoleService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IUserOrgRoleService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.RoleEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserOrgRoleEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.vo.UserOrgRoleVo;

/**
 * 公共选择器--角色查询Action.
 *
 * @author panGuangJun
 * @date 2012-11-30 下午7:16:06
 */
public class CommonRoleSelectorAction extends AbstractAction {
	
	/** The role service. */
	private IRoleService roleService;
	
	/** The user org role service. */
	private IUserOrgRoleService userOrgRoleService;
	
	/** serializaId. */
	private static final long serialVersionUID = -1580737226793786901L;
	
	/** The user org role vo. */
	private UserOrgRoleVo userOrgRoleVo;

	/**
	 * @param roleService the roleService to set
	 */
	public void setRoleService(IRoleService roleService) {
	    this.roleService = roleService;
	}

	
	/**
	 * @param userOrgRoleService the userOrgRoleService to set
	 */
	public void setUserOrgRoleService(IUserOrgRoleService userOrgRoleService) {
	    this.userOrgRoleService = userOrgRoleService;
	}

	/**
	 * 根据用户部门编码，查询未分配角色.
	 *
	 * @return the string
	 * @author 087584-foss-lijun
	 * @date 2012-11-21 上午9:06:41
	 */
	@JSON
	public String queryCommonRoleByNoAllot() {
		if (userOrgRoleVo == null
				|| userOrgRoleVo.getUserOrgRoleEntity() == null
				|| StringUtils.isBlank(userOrgRoleVo.getUserOrgRoleEntity()
						.getEmpCode())
				) {
			return returnSuccess();
		}

		try {
			List<RoleEntity> roleList = this.queryRoleList(userOrgRoleVo, 0,
					Integer.MAX_VALUE);
			List<RoleEntity> allRoleList = roleService.queryRoleExactByEntity(
					null, 0, Integer.MAX_VALUE);
			if (CollectionUtils.isEmpty(roleList)) {
				userOrgRoleVo.setRoleList(allRoleList);
				return returnSuccess();
			}

			Map<String, RoleEntity> map = new LinkedHashMap<String, RoleEntity>();
			for (RoleEntity roleEntity : allRoleList) {
				map.put(roleEntity.getCode(), roleEntity);
			}
			for (RoleEntity roleEntity : roleList) {
				map.remove(roleEntity.getCode());
			}
			List<RoleEntity> roleListNoAllot = new ArrayList<RoleEntity>();
			for (RoleEntity roleEntity : map.values()) {
				roleListNoAllot.add(roleEntity);
			}

			if (!CollectionUtils.isEmpty(roleListNoAllot)) {
				userOrgRoleVo.setRoleList(roleListNoAllot);
			}

			return returnSuccess();
		} catch (BusinessException e) {
			return returnError(e);
		}
	}

	/**
	 * 根据用户部门角色中的用户编码，操作部门编码查询角色.
	 *
	 * @param userOrgRoleVo the user org role vo
	 * @param start the start
	 * @param limit the limit
	 * @return the list
	 * @throws BusinessException the business exception
	 * @author 087584-foss-lijun
	 * @date 2012-11-22 下午9:31:50
	 */
	private List<RoleEntity> queryRoleList(UserOrgRoleVo userOrgRoleVo,
			int start, int limit) throws BusinessException {
		// 设置查询条件
		UserOrgRoleEntity userOrgRoleEntity = userOrgRoleVo
				.getUserOrgRoleEntity();
		// 设置查询结果
		List<UserOrgRoleEntity> userOrgRoleList = userOrgRoleService
				.queryUserOrgRoleExactByEntity(userOrgRoleEntity, start, limit);

		if (!CollectionUtils.isEmpty(userOrgRoleList)) {
			// 组装查询条件
			int leng = userOrgRoleList.size();
			String[] codes = new String[leng];
			for (int i = 0; i < leng; i++) {
				codes[i] = userOrgRoleList.get(i).getRoleCode();
			}

			return roleService.queryRoleBatchByCode(codes);
		}
		return null;
	}

	/**
	 * 根据用户部门编码，查询全部角色.
	 *
	 * @return the string
	 * @author 087584-foss-lijun
	 * @date 2012-11-21 上午9:06:41
	 */
	@JSON
	public String queryCommonRoleByUserOrgAll() {
		if (userOrgRoleVo == null
				|| userOrgRoleVo.getUserOrgRoleEntity() == null
				|| StringUtils.isBlank(userOrgRoleVo.getUserOrgRoleEntity()
						.getEmpCode())
				) {
			return returnSuccess();
		}

		try {
			List<RoleEntity> roleList = this.queryRoleList(userOrgRoleVo, 0,
					Integer.MAX_VALUE);
			if (!CollectionUtils.isEmpty(roleList)) {
				userOrgRoleVo.setRoleList(roleList);
			}

			return returnSuccess();
		} catch (BusinessException e) {
			return returnError(e);
		}
	}

	/**
	 * 根据用户部门编码，分页查询角色.
	 *
	 * @return the string
	 * @author 087584-foss-lijun
	 * @date 2012-11-21 上午9:06:41
	 */
	@JSON
	public String queryCommonRoleByUserOrg() {
		if (userOrgRoleVo == null
				|| userOrgRoleVo.getUserOrgRoleEntity() == null
				|| StringUtils.isBlank(userOrgRoleVo.getUserOrgRoleEntity()
						.getEmpCode())
				|| StringUtils.isBlank(userOrgRoleVo.getUserOrgRoleEntity()
						.getOrgCode())) {
			return returnSuccess();
		}

		try {
			List<RoleEntity> roleList = this.queryRoleList(userOrgRoleVo,
					start, limit);
			if (!CollectionUtils.isEmpty(roleList)) {
				userOrgRoleVo.setRoleList(roleList);
			}
			UserOrgRoleEntity userOrgRoleEntity = userOrgRoleVo
					.getUserOrgRoleEntity();
			totalCount = userOrgRoleService
					.queryUserOrgRoleExactByEntityCount(userOrgRoleEntity);

			return returnSuccess();
		} catch (BusinessException e) {
			return returnError(e);
		}
	}

	/**
	 * getter.
	 *
	 * @return the user org role vo
	 */
	public UserOrgRoleVo getUserOrgRoleVo() {
		return userOrgRoleVo;
	}

	/**
	 * setter.
	 *
	 * @param userOrgRoleVo the new user org role vo
	 */
	public void setUserOrgRoleVo(UserOrgRoleVo userOrgRoleVo) {
		this.userOrgRoleVo = userOrgRoleVo;
	}

	

}
