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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/action/ResourceAction.java
 * 
 * FILE NAME        	: ResourceAction.java
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

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.base.util.Sesn.SesnUtils;
import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.base.util.define.SesnKeyConstants;
import com.deppon.foss.base.util.define.SymbolConstants;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.module.base.baseinfo.api.server.service.IResourceService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IRoleResourceService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.ResourceEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.RoleResourceEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.TreeNodeSimple;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.ResourceException;
import com.deppon.foss.module.base.baseinfo.api.shared.vo.ResourceVo;
import com.deppon.foss.module.base.dict.api.server.service.IConfigurationParamsService;
import com.deppon.foss.module.base.dict.api.shared.define.ConfigurationParamsConstants;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryValueConstants;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.util.define.FossConstants;
import com.opensymphony.xwork2.ActionContext;

/**
 * 权限 action
 * 
 * @author 087584-foss-lijun
 * @date 2012-12-18 上午9:49:09
 */
public class ResourceAction extends AbstractAction {
	
	/**
	 * 序列化对象
	 */
	private static final long serialVersionUID = -1782509318456860274L;

	/**
	 * 父功能编码
	 */
	private String node;
	
	/**
	 * 用于菜单树展开路径设置
	 */
	private String path = "";
	
	/**
	 * 菜单树展开路径集合
	 */
	private Set<String> pathList;
	
	/**
	 * 保存权限（新增或者修改
	 * 
	 * @author 087584-foss-lijun
	 * @date 2012-11-8 下午5:58:41
	 */
	@JSON
	public String saveResource() {
		if (resourceVo == null
				|| resourceVo.getResourceEntityDetail() == null
				|| StringUtils.isEmpty(resourceVo.getResourceEntityDetail()
						.getName())
				|| StringUtils.isEmpty(resourceVo.getResourceEntityDetail()
						.getCode())) {
			// 如果查询的名称为空，则直接返回，通过根结点来展开树
			return returnSuccess();
		}

		ResourceEntity entityView = resourceVo.getResourceEntityDetail();

		ResourceEntity entityQuery = resourceService.queryResourceByCode(entityView.getCode());
		
		entityQuery = this.transEntity(entityView, entityQuery);
		ResourceEntity parentResEntity=resourceService.queryResourceByCode(entityQuery.getParentRes().getCode());
		if(null != parentResEntity && StringUtils.isNotBlank(parentResEntity.getResLevel())){
			int resLevel=Integer.valueOf(parentResEntity.getResLevel())+NumberConstants.NUMBER_1;
			entityQuery.setResLevel(String.valueOf(resLevel));
		}
		entityQuery = this.decorateEntity(entityQuery);

		// 设置操作用户的用户编码
		UserEntity userOfCache = FossUserContext.getCurrentUser();
		String operUserCode = null;
		if (userOfCache != null
				&& userOfCache.getEmployee() != null
				&& StringUtils.isNotBlank(userOfCache.getEmployee()
						.getEmpCode())) {
			operUserCode = userOfCache.getEmployee().getEmpCode();
		}
		entityQuery.setCreateUser(operUserCode);
		entityQuery.setModifyUser(operUserCode);
		
		
		// 如果是新增：
		if (resourceVo.getIsAdd()) {
			// 先保存权限基本信息
			try {
				ResourceEntity entityAdd = resourceService.addResource(entityQuery);
				if (entityAdd == null) {
					return returnError("保存权限失败");
				}
			} catch (BusinessException e) {
				LOGGER.error("保存权限失败", e);
				return returnError("保存权限失败");
			}
		} else {
			// 不是新增就是更新
			try {
				ResourceEntity entityUpdate = resourceService.updateResource(entityQuery);
				if (entityUpdate == null) {
					return returnError("保存权限失败");
				}
			} catch (BusinessException e) {
				LOGGER.error("保存权限失败", e);
				return returnError("保存权限失败");
			}
		}

		return returnSuccess();
	}

	/**
	 * 删除 权限
	 * 
	 * @author 087584-foss-lijun
	 * @date 2012-11-19 上午11:47:23
	 */
	@JSON
	public String deleteResourceRecursion() {
		if (resourceVo == null
				|| resourceVo.getResourceEntityDetail() == null
				|| StringUtils.isEmpty(resourceVo.getResourceEntityDetail()
						.getCode())) {
			return returnSuccess();
		}

		ResourceEntity entityView = resourceVo.getResourceEntityDetail();
		// 设置操作用户的用户编码
		UserEntity userOfCache = FossUserContext.getCurrentUser();
		String operUserCode = null;
		if (userOfCache != null
				&& userOfCache.getEmployee() != null
				&& StringUtils.isNotBlank(userOfCache.getEmployee()
						.getEmpCode())) {
			operUserCode = userOfCache.getEmployee().getEmpCode();
		}
		entityView.setModifyUser(operUserCode);

		try {
			ResourceEntity entityUpdate = resourceService
					.deleteResourceRecursion(entityView);
			if (entityUpdate == null) {
				return returnError("保存权限失败");
			}
		} catch (BusinessException e) {
			LOGGER.error("保存权限失败", e);
			return returnError("保存权限失败");
		}
		return returnSuccess();
	}

	/**
	 * 以下全为查询
	 */

	/**
	 * 根据权限名称查询权限列表
	 * 
	 * 值放在resourceVo.resourceEntityList
	 * 
	 * @author 087584-foss-lijun
	 * @date 2012-11-8 下午5:58:41
	 */
	@JSON
	public String queryResourceFullPathByName() {
		if (resourceVo == null
				|| resourceVo.getResourceEntityDetail() == null
				|| StringUtils.isEmpty(resourceVo.getResourceEntityDetail()
						.getName())) {
			// 如果查询的名称为空，则直接返回，通过根结点来展开树
			return returnSuccess();
		}

		String resourceName = resourceVo.getResourceEntityDetail().getName();
		String belongSystemType = resourceVo.getResourceEntityDetail().getBelongSystemType();
		Set<String> resCodes = FossUserContext.getCurrentUser().getOrgResCodes();
		// 查出符合条件的实体
		ResourceEntity entityCondition = new ResourceEntity();
		entityCondition.setName(resourceName);
		entityCondition.setBelongSystemType(belongSystemType);
		// 由于树型结构一次只能选中一个根结点，所以只处理一个返回结果，这里取第一个：
		List<ResourceEntity> resourceEntityList = resourceService
				.queryResourceByEntity(entityCondition, 0,
						BigDecimal.TEN.intValue());
		pathList = new HashSet<String>();
		// 如果查询的没有结果，直接返回：
		if (CollectionUtils.isEmpty(resourceEntityList)
				|| StringUtils.isEmpty(resourceEntityList.get(0).getCode())) {
			return returnSuccess();
		}

		for(ResourceEntity res : resourceEntityList){
			if(resCodes.contains(res.getCode())){
				node = res.getCode();
				if(res.getBelongSystemType().equals("GUI")){
					this.expendGuiTreePath();
				}else{
					this.expendTreePath();
				}
				pathList.add(new String(path));
				path = "";
			}
		}
		resourceVo.setAllFullPath(pathList);
		return returnSuccess();
	}

	/**
	 * 常用菜单树的展开路径加载 
	 * 
	 * @return
	 * @return String
	 * @since:
	 */
	@JSON
	public String expendTreePath() {
		try {
			ResourceEntity rootRes = resourceService.queryResourceRoot(DictionaryValueConstants.BSE_RESOURCE_BELONG_SYSTEM_TYPE_WEB);
			ResourceEntity parentRes = resourceService
					.queryResourceByCode(node);
			String parentNode = node;
			while (!rootRes.getCode().equals(parentNode)) {
				path = "/" + parentNode + path;
				parentRes = resourceService.queryResourceByCode(parentNode).getParentRes();
				if(parentRes==null){
					path = "";
					break;
				}
				parentNode = parentRes.getCode();
			}
			path = "/" + rootRes.getCode() + path;
			return returnSuccess("保存成功");
		} catch (ResourceException e) {
			return returnError(e);
		}
	}
	/**
	 * 常用菜单树的展开路径加载 (GUI)
	 * 
	 * @return
	 * @return String
	 * @since:
	 */
	@JSON
	public String expendGuiTreePath(){
		try {
			ResourceEntity rootRes = resourceService.queryResourceRoot(DictionaryValueConstants.BSE_RESOURCE_BELONG_SYSTEM_TYPE_GUI);
			ResourceEntity parentRes = resourceService
					.queryResourceByCode(node);
			String parentNode = node;
			while (!rootRes.getCode().equals(parentNode)) {
				path = "/" + parentNode + path;
				parentRes = resourceService.queryResourceByCode(parentNode).getParentRes();
				if(parentRes==null){
					path = "";
					break;
				}
				parentNode = parentRes.getCode();
			}
			path = "/" + rootRes.getCode() + path;
			return returnSuccess("保存成功");
		} catch (ResourceException e) {
			return returnError(e);
		}
	} 
	/**
	 * 根据权限编码查询权限详情
	 * 
	 * 值放在resourceVo.resourceEntityDetail
	 * 
	 * @author 087584-foss-lijun
	 * @date 2012-11-8 下午5:58:41
	 */
	@JSON
	public String queryResourceByCode() {
		if (resourceVo == null
				|| resourceVo.getResourceEntityDetail() == null
				|| StringUtils.isEmpty(resourceVo.getResourceEntityDetail()
						.getCode())) {
			// 如果查询的名称为空，则直接返回，通过根结点来展开树
			return returnSuccess();
		}

		String resourceCode = resourceVo.getResourceEntityDetail().getCode();

		ResourceEntity resourceEntityDetail = resourceService
				.queryResourceByCodeNoCache(resourceCode);
		resourceVo.setResourceEntityDetail(resourceEntityDetail);

		return returnSuccess();
	}

	/**
	 * 去重判断，如果实体已存在，则返回
	 * 
	 * 值放在resourceVo.resourceEntityDetail
	 * 
	 * @author 087584-foss-lijun
	 * @date 2012-11-8 下午5:58:41
	 */
	@JSON
	public String queryResourceExactByEntity() {
		if (resourceVo == null || resourceVo.getResourceEntityDetail() == null) {
			// 如果查询的名称为空，则直接返回，通过根结点来展开树
			return returnSuccess();
		}

		ResourceEntity entityView = resourceVo.getResourceEntityDetail();
		ResourceEntity entityCondition = new ResourceEntity();
		entityCondition.setCode(entityView.getCode());
		entityCondition.setEntryUri(entityView.getEntryUri());

		List<ResourceEntity> resourceEntityList = resourceService
				.queryResourceExactByEntity(entityCondition,
						NumberConstants.ZERO, BigDecimal.ONE.intValue());
		if (CollectionUtils.isEmpty(resourceEntityList)) {
			resourceVo.setResourceEntityDetail(null);
			return returnSuccess();
		}
		ResourceEntity resourceEntityDetail = resourceEntityList
				.get(NumberConstants.ZERO);
		resourceVo.setResourceEntityDetail(resourceEntityDetail);

		return returnSuccess();
	}

	/**
	 * 去重判断，如果实体已存在，则返回
	 * 
	 * 值放在resourceVo.resourceEntityDetail
	 * 
	 * @author 087584-foss-lijun
	 * @date 2012-11-8 下午5:58:41
	 */
	@JSON
	public String queryResourceExactByEntryUri() {
		if (resourceVo == null || resourceVo.getResourceEntityDetail() == null) {
			// 如果查询的名称为空，则直接返回，通过根结点来展开树
			return returnSuccess();
		}

		ResourceEntity entityView = resourceVo.getResourceEntityDetail();
		ResourceEntity entityCondition = new ResourceEntity();
		// entityCondition.setCode(entityView.getCode());
		entityCondition.setEntryUri(entityView.getEntryUri());

		List<ResourceEntity> resourceEntityList = resourceService
				.queryResourceExactByEntity(entityCondition, 0,
						Integer.MAX_VALUE);
		if (CollectionUtils.isEmpty(resourceEntityList)) {
			resourceVo.setResourceEntityDetail(null);
			return returnSuccess();
		}
		ResourceEntity resourceEntityDetail = null;
		for (ResourceEntity queryResult : resourceEntityList) {
			if (!StringUtils.endsWith(queryResult.getCode(),
					entityView.getCode())) {
				resourceEntityDetail = queryResult;
				break;
			}
		}

		resourceVo.setResourceEntityDetail(resourceEntityDetail);

		return returnSuccess();
	}

	/**
	 * 根据权限编码查询权限详情
	 * 
	 * 值放在resourceVo.resourceEntityDetail
	 * 
	 * @author 087584-foss-lijun
	 * @date 2012-11-8 下午5:58:41
	 */
	@JSON
	public String queryRoleResourceByResourceCode() {
		if (resourceVo == null
				|| resourceVo.getResourceEntityDetail() == null
				|| StringUtils.isEmpty(resourceVo.getResourceEntityDetail()
						.getCode())) {
			// 如果查询的名称为空，则直接返回，通过根结点来展开树
			return returnSuccess();
		}

		String resourceCode = resourceVo.getResourceEntityDetail().getCode();

		RoleResourceEntity entityCondition = new RoleResourceEntity();
		entityCondition.setResourceCode(resourceCode);
		List<RoleResourceEntity> roleResourceEntityList = roleResourceService
				.queryRoleResourceExactByEntity(entityCondition, 0,
						Integer.MAX_VALUE);

		resourceVo.setRoleResourceEntityList(roleResourceEntityList);

		return returnSuccess();
	}

	/**
	 * 根据权限名称查询权限列表
	 * 
	 * 值放在resourceVo.resourceEntityList
	 * 
	 * @author 087584-foss-lijun
	 * @date 2012-11-8 下午5:58:41
	 */
	@JSON
	public String queryResourceTreeByName() {
		if (resourceVo == null
				|| resourceVo.getResourceEntityDetail() == null
				|| StringUtils.isEmpty(resourceVo.getResourceEntityDetail()
						.getName())) {
			// 如果查询的名称为空，则直接返回，通过根结点来展开树
			return returnSuccess();
		}

		String resourceName = resourceVo.getResourceEntityDetail().getName();

		// 按名称查出所有的上级
		List<ResourceEntity> resourceEntityList = resourceService
				.queryResourceUpByName(resourceName);

		this.nodes = new ArrayList<TreeNodeSimple<ResourceEntity>>();

		// 将权限对象转换成树结点：
		for (ResourceEntity res : resourceEntityList) {
			TreeNodeSimple<ResourceEntity> treeNode = this
					.changeResToTreeNodeSimple(res);
			nodes.add(treeNode);
		}

		return returnSuccess();
	}

	/**
	 * 查询所有的权限
	 * 
	 * 值放在resourceVo.resourceEntityDetail
	 * 
	 * @author 087584
	 * @date 2012-9-8 上午11:16:53
	 * @return
	 * @see
	 */
	@JSON
	public String queryResourceAll() {
		// 返回的结果显示在表格中：
		List<ResourceEntity> resourceEntityList = resourceService
				.queryResourceExactByEntity(new ResourceEntity(), 0,
						Integer.MAX_VALUE);
		// resourceVo.setResourceEntityList(resourceEntityList);

		this.nodes = new ArrayList<TreeNodeSimple<ResourceEntity>>();

		// 将权限对象转换成树结点：
		for (ResourceEntity res : resourceEntityList) {
			TreeNodeSimple<ResourceEntity> treeNode = this
					.changeResToTreeNodeSimple(res);
			nodes.add(treeNode);
		}
		nodes = this.attachChild(nodes);
		nodes = this.getRootNodes(nodes);

		return returnSuccess();

	}

	/**
	 * 根据权限的上级编码查询权限,用于选择，带checked
	 * 
	 * ParentRes为权限的上级权限编码
	 * 
	 * 值放在resourceVo.resourceEntityDetail
	 * 
	 * @author 087584
	 * @date 2012-9-8 上午11:16:53
	 * @return
	 * @see
	 */
	@JSON
	public String queryResourceByParentResSelect() {
		return this.queryResourceByParentRes(true);
	}

	/**
	 * 根据权限的上级编码查询权限,用于显示， 不带checked
	 * 
	 * ParentRes为权限的上级权限编码
	 * 
	 * 值放在resourceVo.resourceEntityDetail
	 * 
	 * @author 087584
	 * @date 2012-9-8 上午11:16:53
	 * @return
	 * @see
	 */
	@JSON
	public String queryResourceByParentResShow() {
		return this.queryResourceByParentRes(false);
	}

	/**
	 * 精确查询 通过 CODE 查询 全路径
	 * 
	 * // 全路径示例：“.JT00001.GS00002.DQ00003.XQ00003.YYB00008”
	 * 
	 * @author 087584-foss-lijun
	 * @date 2012-11-2 下午5:21:30
	 * @param code权限编码
	 * @return 权限的全路径，父结点在前面
	 * 
	 */
	@JSON
	public String gainFullPathByNameRole() {
		if (resourceVo == null) {
			return returnError("请求数据不能为空");
		}
		if (StringUtils.isBlank(resourceVo.getCurrRoleCode())) {
			return returnError("角色编码不能为空");
		}
		if (resourceVo.getResourceEntityDetail() == null
				|| StringUtils.isBlank(resourceVo.getResourceEntityDetail()
						.getName())) {
			// 如果权限名称为空，则通过根结点向下点击，所以直接返回空
			return returnSuccess();
		}

		String name = resourceVo.getResourceEntityDetail().getName();
		String code = resourceVo.getCurrRoleCode();
		// 根据权限名称（模糊），角色编码（精确）查询出一个权限
		List<ResourceEntity> entitys = resourceService.queryResourceByNameRole(
				name, code, NumberConstants.ZERO, NumberConstants.ONE);
		if (CollectionUtils.isEmpty(entitys)) {
			return returnSuccess();
		}
		ResourceEntity entity = entitys.get(NumberConstants.ZERO);

		// 获得全路径
		String fullPath = resourceService.gainFullPathByCode(entity.getCode());

		resourceVo.setFullPath(fullPath);

		return returnSuccess();
	}

	/**
	 * 根据权限的上级编码查询权限
	 * 
	 * ParentRes为权限的上级权限编码
	 * 
	 * 值放在resourceVo.resourceEntityDetail
	 * 
	 * @author 087584
	 * @date 2012-9-8 上午11:16:53
	 * @return
	 * @see
	 */
	@JSON
	public String queryResourceByParentResContain() {
		if (resourceVo == null) {
			return returnError("请求数据不能为空");
		}
		if (StringUtils.isBlank(resourceVo.getCurrRoleCode())) {
			return returnError("角色编码不能为空");
		}
		if (resourceVo.getResourceEntityDetail() == null
				|| resourceVo.getResourceEntityDetail().getParentRes() == null
				|| StringUtils.isBlank(resourceVo.getResourceEntityDetail()
						.getParentRes().getCode())) {
			return returnError("父权限编码不能为空");
		}

		String parentRes = resourceVo.getResourceEntityDetail().getParentRes()
				.getCode();

		ResourceEntity resourceEntityCondition = new ResourceEntity();
		ResourceEntity resourceEntityParent = new ResourceEntity();
		resourceEntityParent.setCode(parentRes);
		resourceEntityCondition.setParentRes(resourceEntityParent);
		// 根据权限编码查询下级权限：
		List<ResourceEntity> resourceEntityList = resourceService
				.queryResourceExactByEntity(resourceEntityCondition, 0,
						Integer.MAX_VALUE);

		this.nodes = new ArrayList<TreeNodeSimple<ResourceEntity>>();

		Set<String> set = getResourceSet(resourceVo.getCurrRoleCode());
		if (CollectionUtils.isNotEmpty(set)) {
			// 将权限对象转换成树结点：
			for (ResourceEntity res : resourceEntityList) {
				if (set.contains(res.getCode())) {
					TreeNodeSimple<ResourceEntity> treeNode = this
							.changeResToTreeNodeSimple(res);
					nodes.add(treeNode);
				}
			}
		}

		return returnSuccess();
	}

	/**
	 * 精确查询
	 * 
	 * 返回符合记录的条数
	 * 
	 * 根据上级权限，角色查询‘角色所包含的这个权限的下级权限’
	 * 
	 * @author 087584-foss-lijun
	 * @date 2012-11-2 下午3:44:19
	 */
	@JSON
	public String queryResourceCountByRoleResource() {
		if (resourceVo == null) {
			return returnError("请求数据不能为空");
		}
		if (StringUtils.isBlank(resourceVo.getCurrRoleCode())) {
			return returnError("角色编码不能为空");
		}
		if (resourceVo.getResourceEntityDetail() == null
				|| resourceVo.getResourceEntityDetail().getCode() == null) {
			return returnError("权限编码不能为空");
		}

		String resourceCode = resourceVo.getResourceEntityDetail().getCode();

		long resourceNum = resourceService.queryResourceCountByRoleResource(
				resourceCode, resourceVo.getCurrRoleCode());

		resourceVo.setResourceNum(resourceNum);
		return returnSuccess();
	}

	/**
	 * 下面为特殊作废
	 */

	/**
	 * 作废角色权限
	 * 
	 * 返回作废的记录数
	 * 
	 * 根据上级权限，角色查询‘角色所包含的这个权限的下级权限’,然后作废
	 * 
	 * @author 087584-foss-lijun
	 * @date 2012-11-2 下午3:44:19
	 */
	@JSON
	public String deleteResourceByRoleResource() {
		if (resourceVo == null) {
			return returnError("请求数据不能为空");
		}
		if (StringUtils.isBlank(resourceVo.getCurrRoleCode())) {
			return returnError("角色编码不能为空");
		}
		if (resourceVo.getResourceEntityDetail() == null
				|| resourceVo.getResourceEntityDetail().getCode() == null) {
			return returnError("权限编码不能为空");
		}

		String resourceCode = resourceVo.getResourceEntityDetail().getCode();

		long resourceNum = resourceService.deleteResourceByRoleResource(
				resourceCode, resourceVo.getCurrRoleCode());

		resourceVo.setResourceNum(resourceNum);
		return returnSuccess();
	}

	/**
	 * 下面是一些工具方法
	 */

	/**
	 * 根据权限的上级编码查询权限
	 * 
	 * ParentRes为权限的上级权限编码
	 * 
	 * 值放在resourceVo.resourceEntityDetail
	 * 
	 * @author 087584
	 * @date 2012-9-8 上午11:16:53
	 * @param hasChecked
	 *            是否设置checked
	 * @return
	 * @see
	 */
	public String queryResourceByParentRes(boolean hasChecked) {
		String parentRes = null;
		String roleCode = null;
		if (resourceVo != null) {
			if (resourceVo.getResourceEntityDetail() != null
					&& resourceVo.getResourceEntityDetail().getParentRes() != null
					&& !StringUtils
							.isBlank(resourceVo.getResourceEntityDetail()
									.getParentRes().getCode())) {
				parentRes = resourceVo.getResourceEntityDetail().getParentRes()
						.getCode();
			}
			if (!StringUtils.isBlank(resourceVo.getCurrRoleCode())) {
				roleCode = resourceVo.getCurrRoleCode();
			}
		}

		ResourceEntity resourceEntityCondition = new ResourceEntity();
		ResourceEntity resourceEntityParent = new ResourceEntity();
		resourceEntityParent.setCode(parentRes);
		resourceEntityCondition.setParentRes(resourceEntityParent);
		// 返回的结果显示在表格中：
		List<ResourceEntity> resourceEntityList = resourceService
				.queryResourceExactByEntity(resourceEntityCondition, 0,
						Integer.MAX_VALUE);

		this.nodes = new ArrayList<TreeNodeSimple<ResourceEntity>>();

		// 查出角色所包含的所有权限
		Set<String> set = null;
		if (hasChecked && StringUtils.isNotBlank(roleCode)) {
			set = this.getResourceSet(roleCode);
		}

		// 将权限对象转换成树结点：
		for (ResourceEntity res : resourceEntityList) {
			TreeNodeSimple<ResourceEntity> treeNode = new TreeNodeSimple<ResourceEntity>();
			// 设置树结点标识
			treeNode.setId(res.getCode());
			treeNode.setText(res.getName());
			// 设置权限是否叶子结点
			treeNode.setLeaf(StringUtils.equals(FossConstants.YES,
					res.getLeafFlag()));

			if (res.getParentRes() != null) {
				treeNode.setParentId(res.getParentRes().getCode());
			}

			// 如果此角色有这个权限，且需要设置checked则进行设置
			if (hasChecked) {
				if(null != set){
					treeNode.setChecked(set.contains(res.getCode()));
				}else{
					treeNode.setChecked(false);
				}
			}
			nodes.add(treeNode);
		}
		ResourceEntity rootRes = resourceService.queryResourceRoot(DictionaryValueConstants.BSE_RESOURCE_BELONG_SYSTEM_TYPE_WEB);
		if (rootRes.getCode().equals(parentRes)) {
			// 用户常用菜单节点
			if (nodes.size() >= 1) {
				nodes.remove(nodes.size() - 1);
			}
			// 空白节点
			if (nodes.size() > 0) {
				nodes.remove(0);
			}
		}

		return returnSuccess();
	}

	/**
	 * 设置当前操作的角色，便于显示这个角色包含的权限
	 */
	public String setCurrRoleCode() {
		if (resourceVo != null
				&& !StringUtils.isEmpty((resourceVo.getCurrRoleCode()))) {
			Map<String, Object> session = ActionContext.getContext()
					.getSession();
			SesnUtils.setFromSesn(session, SesnKeyConstants.SELECTED_ROLE,
					resourceVo.getCurrRoleCode());
		}
		return returnSuccess();
	}

	public String loadTree() {
		List<ResourceEntity> resourceEntityList = resourceService
				.queryResourceExactByEntity(new ResourceEntity(), 0,
						Integer.MAX_VALUE);

		if (resourceVo == null) {
			resourceVo = new ResourceVo();
		}

		if (nodes == null) {
			nodes = new ArrayList<TreeNodeSimple<ResourceEntity>>();
		}

		// 将权限对象转换成树结点：
		for (ResourceEntity res : resourceEntityList) {
			TreeNodeSimple<ResourceEntity> treeNode = changeResToTreeNodeSimple(res);
			nodes.add(treeNode);
		}
		return returnSuccess();
	}

	/**
	 * 将权限转换成树结点,无checkbox
	 * 
	 * @author 087584-foss-lijun
	 * @date 2012-11-9 上午10:00:51
	 */
	private TreeNodeSimple<ResourceEntity> changeResToTreeNodeSimple(
			ResourceEntity res) {
		TreeNodeSimple<ResourceEntity> treeNode = new TreeNodeSimple<ResourceEntity>();
		// 设置树结点标识
		treeNode.setId(res.getCode());
		treeNode.setText(res.getName());

		// 设置权限是否叶子结点
		treeNode.setLeaf(StringUtils.equals(FossConstants.YES,
				res.getLeafFlag()));

		// 设置父结点：
		if (res.getParentRes() != null) {
			treeNode.setParentId(res.getParentRes().getCode());
		} else {
			treeNode.setParentId(null);
		}
		return treeNode;
	}

	/**
	 * 将权限转换成树结点
	 * 
	 * @author 087584-foss-lijun
	 * @date 2012-11-9 上午10:00:51
	 */
	@SuppressWarnings("unused")
	private TreeNodeSimple<ResourceEntity> changeResToTreeNodeSimpleHasChecked(
			ResourceEntity res) {
		TreeNodeSimple<ResourceEntity> treeNode = new TreeNodeSimple<ResourceEntity>();
		// 设置树结点标识
		treeNode.setId(res.getCode());
		treeNode.setText(res.getName());
		Set<String> set = getResourceSet();

		// 判断角色是否包含此权限
		if (set != null && set.contains(res.getCode())) {
			treeNode.setChecked(true);
		} else {
			treeNode.setChecked(false);
		}

		// 设置权限是否叶子结点
		treeNode.setLeaf(StringUtils.equals(FossConstants.YES,
				res.getLeafFlag()));

		// 设置父结点：
		if (res.getParentRes() != null) {
			treeNode.setParentId(res.getParentRes().getCode());
		} else {
			treeNode.setParentId(null);
		}

		return treeNode;
	}

	/**
	 * 将权限的子结点加进去
	 * 
	 * @author 087584-foss-lijun
	 * @date 2012-11-9 上午10:00:51
	 */
	@SuppressWarnings("rawtypes")
	private List<TreeNodeSimple<ResourceEntity>> attachChild(
			List<TreeNodeSimple<ResourceEntity>> treeNodes) {

		Map<String, TreeNodeSimple<ResourceEntity>> map = new HashMap<String, TreeNodeSimple<ResourceEntity>>();
		Iterator<TreeNodeSimple<ResourceEntity>> it = treeNodes.iterator();
		while (it.hasNext()) {
			TreeNodeSimple<ResourceEntity> node = (TreeNodeSimple<ResourceEntity>) it
					.next();
			map.put(node.getId(), node);
		}

		Iterator<TreeNodeSimple<ResourceEntity>> it2 = treeNodes.iterator();
		while (it2.hasNext()) {
			TreeNodeSimple<ResourceEntity> node = (TreeNodeSimple<ResourceEntity>) it2
					.next();
			if (StringUtils.isEmpty(node.getParentId())) {
				continue;
			}
			TreeNodeSimple<ResourceEntity> parent = map.get(node.getParentId());

			List<TreeNodeSimple> children = parent.getChildren();
			if (children == null) {
				children = new ArrayList<TreeNodeSimple>();
			}
			children.add(node);
		}

		return treeNodes;
	}

	/**
	 * 获得根结点
	 * 
	 * @author 087584-foss-lijun
	 * @date 2012-11-9 上午10:00:51
	 */
	private List<TreeNodeSimple<ResourceEntity>> getRootNodes(
			List<TreeNodeSimple<ResourceEntity>> treeNodes) {
		List<TreeNodeSimple<ResourceEntity>> rootNodes = new ArrayList<TreeNodeSimple<ResourceEntity>>();
		Iterator<TreeNodeSimple<ResourceEntity>> it = treeNodes.iterator();
		while (it.hasNext()) {
			TreeNodeSimple<ResourceEntity> node = (TreeNodeSimple<ResourceEntity>) it
					.next();
			// 如果结点的上级为空，则为根结点
			if (StringUtils.isEmpty(node.getParentId())) {
				rootNodes.add(node);
			}
		}

		return rootNodes;
	}

	/**
	 * 获得权限编码的set
	 * 
	 * @author 087584-foss-lijun
	 * @date 2012-11-9 上午10:00:51
	 */
	private Set<String> getResourceSet() {
		// 获得请求所需要的角色编码：
		Map<String, Object> session = ActionContext.getContext().getSession();
		String roleCode = (String) SesnUtils.getFromSesn(session,
				SesnKeyConstants.SELECTED_ROLE);

		// 如果为空则为根结点：
		if (StringUtils.isEmpty(roleCode)) {
			return null;
		}
		List<RoleResourceEntity> roleResourceEntitys = roleResourceService
				.queryRoleResourceMoreByRoleCode(roleCode
						.split(SymbolConstants.EN_COMMA));

		return resourceToSet(roleResourceEntitys);
	}

	/**
	 * 获得权限编码的set
	 * 
	 * @author 087584-foss-lijun
	 * @date 2012-11-9 上午10:00:51
	 */
	private Set<String> getResourceSet(String roleCode) {
		// 如果为空则为根结点：
		if (StringUtils.isEmpty(roleCode)) {
			return null;
		}
		List<RoleResourceEntity> roleResourceEntitys = roleResourceService
				.queryRoleResourceMoreByRoleCode(roleCode
						.split(SymbolConstants.EN_COMMA));

		return resourceToSet(roleResourceEntitys);
	}

	/**
	 * 将角色权限转换成编码的set
	 * 
	 * @author 087584-foss-lijun
	 * @date 2012-11-9 上午10:00:51
	 */
	private Set<String> resourceToSet(
			List<RoleResourceEntity> roleResourceEntitys) {
		if (CollectionUtils.isEmpty(roleResourceEntitys)) {
			return null;
		}
		Set<String> set = new HashSet<String>();
		Iterator<RoleResourceEntity> it = roleResourceEntitys.iterator();
		while (it.hasNext()) {
			RoleResourceEntity entity = (RoleResourceEntity) it.next();
			if (entity != null
					&& !StringUtils.isEmpty(entity.getResourceCode())) {
				set.add(entity.getResourceCode());
			}
		}
		return set;
	}

	/**
	 * 下面是一些工具方法
	 */

	/**
	 * 将从界面获取的数据与数据库中的源数据整合一下
	 * 
	 * @author 087584-foss-lijun
	 * @date 2012-12-21 上午10:40:15
	 * 
	 * @param entityView
	 *            从界面获取的源数据
	 * @param entityQuery
	 *            从数据库查出来的，要存入数据库的目标数据
	 */
	public ResourceEntity transEntity(ResourceEntity entityView,
			ResourceEntity entityQuery) {
		if (entityQuery == null) {
			entityQuery = new ResourceEntity();
		}

		// 权限编码
		entityQuery.setCode(entityView.getCode());
		// 权限名称
		entityQuery.setName(entityView.getName());
		// 权限入口URI
		entityQuery.setEntryUri(entityView.getEntryUri());
		// 功能层次 暂时不用
		// entityQuery.setResLevel(entityView.getResLevel());
		// 上级权限
		entityQuery.setParentRes(entityView.getParentRes());
		// 显示顺序
		entityQuery.setDisplayOrder(entityView.getDisplayOrder());
		// 是否权限检查
		entityQuery.setChecked(entityView.getChecked());
		// 权限类型
		entityQuery.setResType(entityView.getResType());
		// 是否子结点
		entityQuery.setLeafFlag(entityView.getLeafFlag());
		// 图标的CSS样式
		entityQuery.setIconCls(entityView.getIconCls());
		// 节点的CSS样式
		entityQuery.setCls(entityView.getCls());
		// 权限描述
		entityQuery.setNotes(entityView.getNotes());
		// 所属系统类型
		entityQuery.setBelongSystemType(entityView.getBelongSystemType());

		return entityQuery;
	}

	/**
	 * 将数据进行包装，加上权限对应的样式
	 * 
	 * @author 087584-foss-lijun
	 * @date 2012-12-21 上午10:40:15
	 * 
	 * @param entityView
	 *            从界面获取的源数据
	 * @param entityQuery
	 *            从数据库查出来的，要存入数据库的目标数据
	 */
	public ResourceEntity decorateEntity(ResourceEntity entity) {
		if (entity == null) {
			return entity;
		}
		String cls = entity.getResLevel();
		if (StringUtils.equals(cls, NumberConstants.NUMERAL_S_ZERE)) {
			cls = ConfigurationParamsConstants.RESOURCE_NODE_CLS0;
		} else if (StringUtils.equals(cls, NumberConstants.NUMERAL_S_ONE)) {
			cls = ConfigurationParamsConstants.RESOURCE_NODE_CLS1;
		} else if (StringUtils.equals(cls, NumberConstants.NUMERAL_S_TWO)) {
			cls = ConfigurationParamsConstants.RESOURCE_NODE_CLS2;
		} else if (StringUtils.equals(cls, NumberConstants.NUMERAL_S_THREE)) {
			cls = ConfigurationParamsConstants.RESOURCE_NODE_CLS3;
		} else if (StringUtils.equals(cls, NumberConstants.NUMERAL_S_FOUR)) {
			cls = ConfigurationParamsConstants.RESOURCE_NODE_CLS4;
		} else if (StringUtils.equals(cls, NumberConstants.NUMERAL_S_FIVE)) {
			cls = ConfigurationParamsConstants.RESOURCE_NODE_CLS5;
		}
		if(StringUtils.equals(entity.getResType(),NumberConstants.NUMERAL_S_THREE)){
			cls = ConfigurationParamsConstants.RESOURCE_NODE_CLS4;
		}
		if(StringUtils.equals(entity.getResType(),NumberConstants.NUMERAL_S_ONE) || StringUtils.equals(entity.getResType(),NumberConstants.NUMERAL_S_TWO)){
			entity.setLeafFlag(FossConstants.NO);
		}
		String iconCls = configurationParamsService
				.queryConfValueByCode(ConfigurationParamsConstants.RESOURCE_ICONCLS);
		cls = configurationParamsService.queryConfValueByCode(cls);
		if (cls == null) {
			String tipStr = " 没有对应的样式配置在 系统配置中";
			throw new ResourceException(tipStr, tipStr);
		}

		entity.setIconCls(iconCls);
		entity.setCls(cls);

		if (!StringUtils.equals(entity.getChecked(), FossConstants.YES)) {
			entity.setChecked(FossConstants.NO);
		}
		if (!StringUtils.equals(entity.getLeafFlag(), FossConstants.YES)) {
			entity.setLeafFlag(FossConstants.NO);
		}

		return entity;
	}

	/**
	 * 将角色权限转换成角色编码的set
	 * 
	 * @author 087584-foss-lijun
	 * @date 2012-11-9 上午10:00:51
	 */
	public Set<String> roleToSet(List<RoleResourceEntity> roleResourceEntitys) {
		if (CollectionUtils.isEmpty(roleResourceEntitys)) {
			return null;
		}
		Set<String> set = new LinkedHashSet<String>();
		for (RoleResourceEntity entity : roleResourceEntitys) {
			if (entity != null && !StringUtils.isEmpty(entity.getRoleCode())) {
				set.add(entity.getRoleCode());
			}
		}
		return set;
	}

	/**
	 * 根据权限编码查询权限详情
	 * 
	 * 值放在resourceVo.resourceEntityDetail
	 * 
	 * @author 087584-foss-lijun
	 * @date 2012-11-8 下午5:58:41
	 * @param code
	 *            权限编码
	 */
	@JSON
	public String gainFullPath(String code) {
		// 按编码CODE查出所有的上级
		List<ResourceEntity> resourceEntityList = resourceService
				.queryResourceUpByCode(code);

		// 转换成fullPath:
		// 全路径示例：“.JT00001.GS00002.DQ00003.XQ00003.YYB00008”
		StringBuilder fullPath = new StringBuilder();
		Map<String, ResourceEntity> map = new HashMap<String, ResourceEntity>();
		for (ResourceEntity entity : resourceEntityList) {
			if (entity != null) {
				map.put(entity.getCode(), entity);
			}
		}

		ResourceEntity currResourceEntity = map.get(code);
		for (int i = NumberConstants.ZERO, l = resourceEntityList.size(); i < l
				&& currResourceEntity != null; i++) {
			fullPath.insert(NumberConstants.ZERO, currResourceEntity.getCode())
					.insert(NumberConstants.ZERO, SymbolConstants.EN_PERIOD);
			if (currResourceEntity.getParentRes() != null) {
				currResourceEntity = map.get(currResourceEntity.getParentRes()
						.getCode());
			} else {
				break;
			}
		}

		return fullPath.toString();
	}

	private static final Logger LOGGER = LoggerFactory
			.getLogger(ResourceAction.class);

	/**
	 * 下面是属性的get,set方法：
	 */
	private ResourceVo resourceVo;
	/**
	 * 
	 */
	private List<TreeNodeSimple<ResourceEntity>> nodes;
	/**
	 * 权限控制Service：
	 */
	private IResourceService resourceService;
	/**
	 * 系统配置Service
	 */
	private IConfigurationParamsService configurationParamsService;
	/**
	 * 角色控制Service：
	 */
	private IRoleResourceService roleResourceService;
	/**
	 * @param  resourceService  
	 */
	public void setResourceService(IResourceService resourceService) {
		this.resourceService = resourceService;
	}
	/**
	 * @param  configurationParamsService  
	 */
	public void setConfigurationParamsService(
			IConfigurationParamsService configurationParamsService) {
		this.configurationParamsService = configurationParamsService;
	}
	/**
	 * @param  roleResourceService  
	 */
	public void setRoleResourceService(IRoleResourceService roleResourceService) {
		this.roleResourceService = roleResourceService;
	}

	/**
	 * @return resourceVo
	 */
	public ResourceVo getResourceVo() {
		return resourceVo;
	}

	/**
	 * @param  resourceVo  
	 */
	public void setResourceVo(ResourceVo resourceVo) {
		this.resourceVo = resourceVo;
	}

	/**
	 * @return nodes
	 */
	public List<TreeNodeSimple<ResourceEntity>> getNodes() {
		return nodes;
	}

	/**
	 * @param  nodes  
	 */
	public void setNodes(List<TreeNodeSimple<ResourceEntity>> nodes) {
		this.nodes = nodes;
	}
}
