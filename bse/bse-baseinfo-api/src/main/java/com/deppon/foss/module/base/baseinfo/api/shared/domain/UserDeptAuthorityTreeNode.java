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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/shared/domain/UserDeptAuthorityTreeNode.java
 * 
 * FILE NAME        	: UserDeptAuthorityTreeNode.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.baseinfo.api.shared.domain;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.TreeNode;

/**
 * 
 * 生成用户权限树
 * 
 * @author 何波
 * @date 2013-2-23 下午1:07:26
 * @since
 * @version
 */
@SuppressWarnings("rawtypes")
public class UserDeptAuthorityTreeNode  extends TreeNode<OrgAdministrativeInfoEntity, TreeNode>{
	
	/**
	 * 是否选择子组织
	 */
	private boolean subOrg;

	/**
	 * @return subOrg
	 */
	public boolean isSubOrg() {
		return subOrg;
	}

	/**
	 * @param  subOrg  
	 */
	public void setSubOrg(boolean subOrg) {
		this.subOrg = subOrg;
	}
}
