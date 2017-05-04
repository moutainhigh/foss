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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/action/FinancialOrganizationsAction.java
 * 
 * FILE NAME        	: FinancialOrganizationsAction.java
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

import com.deppon.foss.base.util.ComnConst;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.server.service.IFinancialOrganizationsService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.FinancialOrganizationsEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.TreeNode;
import com.deppon.foss.module.base.baseinfo.api.shared.vo.FinancialOrganizationsInfoVo;
import com.deppon.foss.util.CollectionUtils;
import com.deppon.foss.util.define.FossConstants;

/**
 * 
 * @date Mar 12, 2013 3:25:07 PM
 * @version 1.0
 */
public class FinancialOrganizationsAction extends AbstractAction {

	private static final long serialVersionUID = -4387687988722020011L;
	// 查询财务组织service
	private IFinancialOrganizationsService financialOrganizationsService;

	public void setFinancialOrganizationsService(
			IFinancialOrganizationsService financialOrganizationsService) {
		this.financialOrganizationsService = financialOrganizationsService;
	}

	// 财务组织VO
	private FinancialOrganizationsInfoVo financialOrganizationsInfoVo = new FinancialOrganizationsInfoVo();

	public FinancialOrganizationsInfoVo getFinancialOrganizationsInfoVo() {
		return financialOrganizationsInfoVo;
	}

	public void setFinancialOrganizationsInfoVo(
			FinancialOrganizationsInfoVo financialOrganizationsInfoVo) {
		this.financialOrganizationsInfoVo = financialOrganizationsInfoVo;
	}

	// 树形结构传来的node
	private String node;

	public void setNode(String node) {
		this.node = node;
	}

	@SuppressWarnings("rawtypes")
	private List<TreeNode> nodes;

	@SuppressWarnings("rawtypes")
	public List<TreeNode> getNodes() {
		return nodes;
	}

	/**
	 * 通过node，调用loadTree方法展开树 loadDepartmentTree 方法名：loadTree </p>
	 * 
	 * @author 078838-foss-zhangbin
	 * @时间 2012-11-07
	 * @since JDK1.6
	 */
	@JSON
	public String loadFinancialDepartmentTree() {
		try {
			FinancialOrganizationsEntity financialOrganizationsEntity = new FinancialOrganizationsEntity();
			financialOrganizationsEntity.setParentOrgCode(node);
			List<FinancialOrganizationsEntity> financialOrganizationsEntityList = financialOrganizationsService
					.queryFinancialOrganizationsExactByEntity(
							financialOrganizationsEntity, 0, Integer.MAX_VALUE);
			if (CollectionUtils.isNotEmpty(financialOrganizationsEntityList)) {
				loadTree(financialOrganizationsEntityList);
			}
			return returnSuccess();
		} catch (BusinessException e) {

			return returnError(e);
		}
	}

	/**
	 * 通过所要展开节点的ID信息，获取该节点的子节点，并进行过滤 loadTree 方法名：loadTree </p>
	 * 
	 * @author 078838-foss-zhangbin
	 * @时间 2012-11-07
	 * @since JDK1.6
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void loadTree(
			List<FinancialOrganizationsEntity> financialOrganizationsEntityList) {
		nodes = new ArrayList<TreeNode>();
		for (FinancialOrganizationsEntity financialOrganizationsEntity : financialOrganizationsEntityList) {
			TreeNode treeNode = new TreeNode();
			treeNode.setId(financialOrganizationsEntity.getCode());
			treeNode.setText(financialOrganizationsEntity.getName());
			FinancialOrganizationsEntity child = new FinancialOrganizationsEntity();
			child.setParentOrgCode(financialOrganizationsEntity.getCode());
			child.setActive(FossConstants.ACTIVE);
			List<FinancialOrganizationsEntity> childList = financialOrganizationsService
					.queryFinancialOrganizationsExactByEntity(
							child, 0, Integer.MAX_VALUE);
			if(CollectionUtils.isEmpty(childList)){
				treeNode.setLeaf(true);
			}else{
				treeNode.setLeaf(false);
			}
			
			if (financialOrganizationsEntity.getParentOrgCode() != null) {
				treeNode.setParentId(financialOrganizationsEntity
						.getParentOrgCode());
			} else {
				treeNode.setParentId(null);
			}
			treeNode.setEntity(financialOrganizationsEntity);
			treeNode.setChildren(null);
			nodes.add(treeNode);
		}
	}

	/**
	 * .
	 * <p>
	 * 通过快速定位查询相应部门的部门序列 querySeq<br/>
	 * 方法名：queryAllFullPath
	 * </p>
	 * 
	 * @author 078838-foss-zhangbin
	 * @时间 2012-11-07
	 * @since JDK1.6
	 */
	@JSON
	public String queryFinancialOrganizationsFullPath() {
		try {
			String deptName = financialOrganizationsInfoVo
					.getFinancialOrganizationsEntity().getName();
			financialOrganizationsInfoVo.getFinancialOrganizationsEntity()
					.setActive(FossConstants.ACTIVE);// 查询出来是激活的
			String fullPath = "";// 查询出来的第一个部门的全路径CODE的
			if (StringUtil.isNotBlank(deptName)) {
				deptName = "";
				List<FinancialOrganizationsEntity> financialOrganizationsEntityList = financialOrganizationsService
						.queryFinancialOrganizationsByEntity(
								financialOrganizationsInfoVo
										.getFinancialOrganizationsEntity(), 0,
								Integer.MAX_VALUE);
				// 根据财务组织名称查询部门
				List<String> fullPathList = new ArrayList<String>();
				for (FinancialOrganizationsEntity financialOrganizationsEntity : financialOrganizationsEntityList) {
					fullPathList
							.add(financialOrganizationsEntity.getFullPath());// 得到所有的全路径
				}
				if (fullPathList.size() > 0) {
					for (String seqOne : fullPathList) {
						if (StringUtil.isNotBlank(seqOne)&&seqOne.startsWith(ComnConst.FINANCIAL_ORG_ROOT_FULLPATH)) {//已根节点的全路径开头
							fullPath = seqOne;
							break;
						}
					}
				} else {
					fullPath = null;
				}
			}
			financialOrganizationsInfoVo.setFullPath(fullPath);
			return returnSuccess();
		} catch (BusinessException e) {
			return returnError(e);
		}
	}

}
