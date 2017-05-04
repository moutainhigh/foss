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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/action/UserDeptAuthorityAction.java
 * 
 * FILE NAME        	: UserDeptAuthorityAction.java
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
import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IUserDeptAuthorityService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserDeptAuthorityTreeNode;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserDeptDataEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.MessageType;
import com.deppon.foss.module.base.baseinfo.api.shared.vo.OrgAdministrativeInfoVo;
import com.deppon.foss.util.CollectionUtils;
import com.deppon.foss.util.define.FossConstants;

/**
 * 
 * @date Mar 12, 2013 3:26:30 PM
 * @version 1.0
 */
public class UserDeptAuthorityAction extends AbstractAction {

	private static final long serialVersionUID = -4387687988722020011L;

	// 组织vo
	private OrgAdministrativeInfoVo orgAdministrativeInfoVo = new OrgAdministrativeInfoVo();

	// 前台传递的用户数据权限集合
	private List<UserDeptDataEntity> deptDataEntities;
	// 前台传递的用户数据权限集合
	private String empCode;

	// 行政组织service
	private IOrgAdministrativeInfoService orgAdministrativeInfoService;
	// 用户数据权限service
	private IUserDeptAuthorityService userDeptAuthorityService;

	public OrgAdministrativeInfoVo getOrgAdministrativeInfoVo() {
		return orgAdministrativeInfoVo;
	}

	public void setOrgAdministrativeInfoVo(
			OrgAdministrativeInfoVo orgAdministrativeInfoVo) {
		this.orgAdministrativeInfoVo = orgAdministrativeInfoVo;
	}

	public void setOrgAdministrativeInfoService(
			IOrgAdministrativeInfoService orgAdministrativeInfoService) {
		this.orgAdministrativeInfoService = orgAdministrativeInfoService;
	}

	// 树形结构传来的node
	private String node;

	public void setNode(String node) {
		this.node = node;
	}

	private List<UserDeptAuthorityTreeNode> nodes;

	public List<UserDeptAuthorityTreeNode> getNodes() {
		return nodes;
	}

	/**
	 * 通过node，调用loadTree方法展开树 loadDepartmentTree 方法名：loadTree </p>
	 * 
	 * @author 078823-foss-panGuangjun
	 * @时间 2012-12-17
	 * @since JDK1.6
	 */
	@JSON
	public String loadDepartmentTree() {
		try {
			OrgAdministrativeInfoEntity orgAdministrativeInfoEntity = new OrgAdministrativeInfoEntity();
			orgAdministrativeInfoEntity.setUumsParentId(node);
			List<OrgAdministrativeInfoEntity> orgAdministrativeInfoEntityList = orgAdministrativeInfoService
					.queryOrgAdministrativeInfoExactByEntity(
							orgAdministrativeInfoEntity, NumberConstants.ZERO,
							Integer.MAX_VALUE);
			if (CollectionUtils.isNotEmpty(orgAdministrativeInfoEntityList)) {
				loadTree(orgAdministrativeInfoEntityList);
			}
			return returnSuccess();
		} catch (BusinessException e) {
			return returnError(e);
		}
	}

	/**
	 * 
	 * 保存用户数据权限
	 * 
	 * @author 078823-foss-panGuangJun
	 * @date 2012-12-18 下午1:45:45
	 * @return String
	 * @param
	 */
	@JSON
	public String saveUserDepts() {
		int result = userDeptAuthorityService.saveUserDepts(deptDataEntities, empCode);
		String type = 0 >= result ? MessageType.SAVE_USERDEPTS_FAIL
				: MessageType.SAVE_USERDEPTS_SUCCESS;
		return returnSuccess(type);
	}

	@JSON
	public String searchUserDepts() {
		// 查询用户的数据权限部门
		String userCode =  orgAdministrativeInfoVo.getUserCode();
		if(StringUtil.isNotBlank(userCode)){
			List<OrgAdministrativeInfoEntity> administrativeInfoEntities = userDeptAuthorityService
				.searchUserDeptsByUserCode(userCode);
		//何波 2013-0-23查询用户权限数据表信息
		List<UserDeptDataEntity>  userDeptList = userDeptAuthorityService
				.queryUserDeptsByUserCode(userCode);
		if (null != administrativeInfoEntities
				&& 0 < administrativeInfoEntities.size()) {
			if (null == nodes) {
				nodes = new ArrayList<UserDeptAuthorityTreeNode>();
			}
			for (OrgAdministrativeInfoEntity userDept : administrativeInfoEntities) {
				nodes.add(getTreeNode(userDept));
			}
		}
		//将查询出的数据权限部门列表加入到vo
		orgAdministrativeInfoVo.setOrgAdministrativeInfoEntityList(administrativeInfoEntities);
        //何波 2013-0-23设置用户权限数据是否包含子组织	
		if(CollectionUtils.isNotEmpty(userDeptList) && CollectionUtils.isNotEmpty(nodes)){
			for (UserDeptDataEntity userDept : userDeptList) {
				for (UserDeptAuthorityTreeNode node : nodes) {
					OrgAdministrativeInfoEntity org = (OrgAdministrativeInfoEntity) node
							.getEntity();
					//子组织编码相等
					if (userDept.getDept().getCode().equals(org.getCode())) {
						//权限是否选择了子组织
						if (userDept.getSubOrgFlag().equals(FossConstants.ACTIVE)) {
							node.setSubOrg(true);
						} else {
							node.setSubOrg(false);
						}
					}
				}
			}
		}	
		return returnSuccess();
		}
		return returnError("用户编码为空");
	}

	/**
	 * 通过所要展开节点的ID信息，获取该节点的子节点，并进行过滤 loadTree 方法名：loadTree </p>
	 * 
	 * @author 078823-foss-panGuangjun
	 * @时间 2012-12-17
	 * @since JDK1.6
	 */
	private void loadTree(
			List<OrgAdministrativeInfoEntity> orgAdministrativeInfoEntityList) {
		/**
		 * 获取前台传来的全路径集合的字符串，将其通过“,”分割开，获取快速查询部门的父部门ID 并将数据放到HashSet中
		 */
		nodes = new ArrayList<UserDeptAuthorityTreeNode>();
		for (OrgAdministrativeInfoEntity orgAdministrativeInfoEntity : orgAdministrativeInfoEntityList) {
			// boolean isChecked = false;
			// if (null != administrativeInfoEntities
			// && 0 < administrativeInfoEntities.size()) {
			// for (int i = 0; i < administrativeInfoEntities.size(); i++) {
			// if (StringUtil.equals(administrativeInfoEntities.get(i)
			// .getId(), orgAdministrativeInfoEntity.getId())) {
			// isChecked = true;
			// }
			// }
			// }
			UserDeptAuthorityTreeNode treeNode = getTreeNode(orgAdministrativeInfoEntity);
			// if (!isChecked) {
			nodes.add(treeNode);
			// }
		}
	}

	/**
	 * 根据部门实体生成树节点
	 * 
	 * @author 078823-foss-panGuangJun
	 * @date 2012-12-19 下午4:34:06
	 * @return TreeNode
	 * @param
	 */
	private UserDeptAuthorityTreeNode getTreeNode(
			OrgAdministrativeInfoEntity orgAdministrativeInfoEntity) {
		// 查询用户的数据权限部门
//		List<OrgAdministrativeInfoEntity> administrativeInfoEntities = userDeptAuthorityService
//				.searchUserDeptsByUserCode(orgAdministrativeInfoVo
//						.getUserCode());

		UserDeptAuthorityTreeNode treeNode = new UserDeptAuthorityTreeNode();
		treeNode.setId(orgAdministrativeInfoEntity.getUumsId());
		treeNode.setText(orgAdministrativeInfoEntity.getName());
		if(StringUtil.isNotBlank(orgAdministrativeInfoEntity.getDeptDesc()) && orgAdministrativeInfoEntity.getDeptDesc().equals(FossConstants.ACTIVE)){
			treeNode.setSubOrg(true);
		}
		OrgAdministrativeInfoEntity child = new OrgAdministrativeInfoEntity();
		child.setParentOrgUnifiedCode(orgAdministrativeInfoEntity
				.getUnifiedCode());
		String leaf = orgAdministrativeInfoEntity.getIsLeaf();
		//去掉通过查询数据判断是否是叶子节点（何波 25013-5-14）
//		List<OrgAdministrativeInfoEntity> childList = orgAdministrativeInfoService
//				.queryOrgAdministrativeInfoExactByEntity(child,
//						NumberConstants.ZERO, Integer.MAX_VALUE);// 查询当前节点是否有子节点
		if (StringUtil.isNotEmpty(leaf)&&leaf.equals(FossConstants.ACTIVE)) {
			treeNode.setLeaf(true);
		} else {
			treeNode.setLeaf(false);
		}


		if (orgAdministrativeInfoEntity.getParentOrgUnifiedCode() != null) {
			treeNode.setParentId(orgAdministrativeInfoEntity
					.getParentOrgUnifiedCode());
		} else {
			treeNode.setParentId(null);
		}
		// 默认设置节点未被选中
		treeNode.setChecked(false);
		// 如果根据用户编码查询出来的部门中和该部门id相同，则用户拥有改部门的数据权限，将设置节点未选中
//		if (null != administrativeInfoEntities
//				&& 0 < administrativeInfoEntities.size()) {
//			for (int i = 0; i < administrativeInfoEntities.size(); i++) {
//				if (StringUtil.equals(
//						administrativeInfoEntities.get(i).getId(),
//						orgAdministrativeInfoEntity.getId())) {
//					treeNode.setChecked(true);
//				}
//			}
//		}

		treeNode.setEntity(orgAdministrativeInfoEntity);
		treeNode.setChildren(null);
		return treeNode;
	}
	/**
	 * 
	 *<p>通过快速定位查询相应部门的部门序列</P>
	 * @author 130566-foss-ZengJunfan
	 * @date 2013-4-28下午2:03:56
	 *@return
	 */
	@JSON
	public String queryOrgAllFullPathByOrgName(){
		try {
			String deptName =orgAdministrativeInfoVo.getOrgAdministrativeInfoEntity().getName();
			orgAdministrativeInfoVo.getOrgAdministrativeInfoEntity().
				setActive(FossConstants.ACTIVE);//查询出来的都是已经激活的
			String fullPath =""; //查询出来的第一个节点的全路径CODE
			if(StringUtil.isNotBlank(deptName)){
				deptName ="";
				List<OrgAdministrativeInfoEntity> orgAdministrativeInfoEntityList = orgAdministrativeInfoService
						.queryOrgAdministrativeInfoByEntity(
								orgAdministrativeInfoVo
										.getOrgAdministrativeInfoEntity(),
								NumberConstants.ZERO, Integer.MAX_VALUE);// 根据部门名称查询部门
				List<String> fullPathList = new ArrayList<String>(); //路径集合
				for (OrgAdministrativeInfoEntity entity : orgAdministrativeInfoEntityList) {
					// 获取全部路径
					fullPathList.add(entity.getUumsIds());
				}
				if (fullPathList.size() > 0) {
					for (String seqOne : fullPathList) {
						if (StringUtil.isNotBlank(seqOne)
								&& seqOne.startsWith(ComnConst.ORG_ROOT_FULLPATH)) {
							fullPath = seqOne;
							fullPath = fullPath.substring(0,
									fullPath.length() - 1);
							break;
						}
					}
				} else {
					fullPath = null;
				}
			}
			orgAdministrativeInfoVo.setFullPath(fullPath);
			return returnSuccess();
		} catch (BusinessException e) {
			return returnError(e);
		}
	}
	/**
	 * setter
	 */
	public void setUserDeptAuthorityService(
			IUserDeptAuthorityService userDeptAuthorityService) {
		this.userDeptAuthorityService = userDeptAuthorityService;
	}

	/**
	 * setter
	 */
	public void setDeptDataEntities(List<UserDeptDataEntity> deptDataEntities) {
		this.deptDataEntities = deptDataEntities;
	}

	public String getEmpCode() {
		return empCode;
	}

	public void setEmpCode(String empCode) {
		this.empCode = empCode;
	}

}