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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/action/commonselector/CommonResourceAction.java
 * 
 * FILE NAME        	: CommonResourceAction.java
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
 * FILE    NAME: CommonRoleAction.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */
package com.deppon.foss.module.base.baseinfo.server.action.commonselector;

import java.util.ArrayList;
import java.util.List;

import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.server.action.IQueryAction;
import com.deppon.foss.module.base.baseinfo.api.server.service.IResourceService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.ResourceEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.TreeNode;

/**
 * 公共组件--权限查询.
 * 
 * @author panGuangJun
 * @date 2012-11-30 下午4:47:42
 */
public class CommonResourceAction extends AbstractAction implements
		IQueryAction {

	/** SerializableId. */
	private static final long serialVersionUID = 8263765905288760678L;

	/** The deal. */
//	private ResourceCacheDeal deal = new ResourceCacheDeal();

	private IResourceService resourceService;

	public void setResourceService(IResourceService resourceService) {
		this.resourceService = resourceService;
	}

	/** The resource list. */
	private List<ResourceEntity> resourceList = new ArrayList<ResourceEntity>();

	/** The nodes. */
	@SuppressWarnings("rawtypes")
	private List<TreeNode> nodes;

	/** The resource entity. */
	private ResourceEntity resourceEntity;

	/**
	 * 查询所有权限并产生树.
	 * 
	 * @return the string
	 * @author panGuangJun
	 * @date 2012-11-30 下午4:48:02
	 * @see com.deppon.foss.module.base.baseinfo.api.server.action.IQueryAction#query()
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public String query() {
		resourceList = resourceService.queryAllResource();
		nodes = new ArrayList<TreeNode>();
		if (null != resourceList && 0 < resourceList.size()) {
			for (ResourceEntity pojo : resourceList) {
				TreeNode<ResourceEntity, TreeNode> treeNode = new TreeNode<ResourceEntity, TreeNode>();
				treeNode.setId(pojo.getCode());
				treeNode.setText(pojo.getName());
				// 默认为叶子节点
				treeNode.setLeaf(true);
				// 默认没有父节点
				treeNode.setParentId(null);
				// 如果该节点是另一个节点的孩子节点，则设置其父节点
				for (ResourceEntity resource : resourceList) {
					if (null != pojo.getParentRes()
							&& StringUtil.equals(pojo.getParentRes().getCode(),
									resource.getCode())) {
						treeNode.setParentId(resource.getCode());
					}
				}
				// 如果该节点是另一个节点的父亲节点，这不是叶子节点
				for (ResourceEntity resource : resourceList) {
					if (null != resource.getParentRes()
							&& pojo.getCode().equals(
									resource.getParentRes().getCode())) {
						treeNode.setLeaf(false);
					}
				}
				treeNode.setEntity(pojo);
				nodes.add(treeNode);
			}
		}
		return returnSuccess();
	}

	/**
	 * Query common resource by name.
	 * 
	 * @return the string
	 */
	@JSON
	public String queryCommonResourceByName() {
		resourceList = resourceService.queryResourceExactByEntity(resourceEntity, start, limit);
		setTotalCount(resourceService.queryResourceExactByEntityCount(resourceEntity));
		return returnSuccess();
	}

	/**
	 * Gets the resource list.
	 * 
	 * @return the resource list
	 */
	public List<ResourceEntity> getResourceList() {
		return resourceList;
	}

	/**
	 * Gets the nodes.
	 * 
	 * @return the nodes
	 */
	@SuppressWarnings("rawtypes")
	public List<TreeNode> getNodes() {
		return nodes;
	}

	/**
	 * getter.
	 * 
	 * @return the resource entity
	 */
	public ResourceEntity getResourceEntity() {
		return resourceEntity;
	}

	/**
	 * setter.
	 * 
	 * @param resourceEntity
	 *            the new resource entity
	 */
	public void setResourceEntity(ResourceEntity resourceEntity) {
		this.resourceEntity = resourceEntity;
	}

}
