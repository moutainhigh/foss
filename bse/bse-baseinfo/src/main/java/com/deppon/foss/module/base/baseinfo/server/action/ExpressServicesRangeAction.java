/*******************************************************************************
 * Copyright 2013 BSE TEAM
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * 
 * you may not use this file except in compliance with the License.
 * 
 * You may obtain a copy of the License at
 * 
 *    http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * 
 * distributed under the License is distributed on an "AS IS" BASIS,
 * 
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * 
 * See the License for the specific language governing permissions and
 * 
 * limitations under the License.
 * 
 * PROJECT NAME	: bse-baseinfo
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/action/ExpressServicesRangeAction.java
 * 
 * FILE NAME        	: ExpressServicesRangeAction.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2014  Deppon All Rights Reserved.
 ******************************************************************************/

package com.deppon.foss.module.base.baseinfo.server.action;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.module.base.baseinfo.api.server.service.IAdministrativeRegionsService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IVehicleAgencyDeptService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.AdministrativeRegionsEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OuterBranchEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.TreeNode;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.AdministrativeRegionsDto;
import com.deppon.foss.module.base.baseinfo.api.shared.vo.AdministrativeRegionsVo;
import com.deppon.foss.module.base.baseinfo.server.service.impl.AdministrativeRegionsService;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryValueConstants;

/**
 * 行政区域 action
 * 
 * @author 135299-foss-WeiXing
 * @date 2014-08-18 上午9:47:55
 */
public class ExpressServicesRangeAction extends AbstractAction {
	/**
	 * 根据父结点编码，查询直接下级的所有子结点（不包括间接下级）
	 * 
	 */
	@SuppressWarnings("rawtypes")
	@JSON
	public String queryByParentDistrictCode() {
		// 校验前台传入的VO是否为空
		if (administrativeRegionsVo == null
		// 校验前台传入的VO的行政区域明细信息是否为空
				|| administrativeRegionsVo.getAdministrativeRegionsDetail() == null
				|| StringUtils.isEmpty(administrativeRegionsVo
						.getAdministrativeRegionsDetail()
						.getParentDistrictCode())) {
			// 如果VO为空，则new一个对象
			if (administrativeRegionsVo == null) {
				administrativeRegionsVo = new AdministrativeRegionsVo();
			}
			// 如上级编码为空，返回根结点
			administrativeRegionsVo
					.setAdministrativeRegionsList(administrativeRegionsService
							.queryRoot());
			return returnSuccess();
		}
		// 获取父节点行政区域编码
		String parentCode = administrativeRegionsVo.getAdministrativeRegionsDetail().getParentDistrictCode();
		
		//根据城市Code查询行政区域信息
		AdministrativeRegionsEntity administrativeRegions = administrativeRegionsService.queryAdministrativeRegionsByCodeNotCache(parentCode);
		//如果节点行政区域编码是县区,则查询快递网点和落地配信息
		if(StringUtils.equals(DictionaryValueConstants.DISTRICT_COUNTY, administrativeRegions.getDegree())){
			nodes=getExpressVehicleCoordinates(parentCode);
			return returnSuccess();
		}
		//如果节点行政区域编码是地级市,则查询出区县信息以及区县所以对应的快递和落地配网点信息
		/*if(StringUtils.equals(DictionaryValueConstants.DISTRICT_CITY, administrativeRegions.getDegree())){
			List<AdministrativeRegionsEntity> administrativeRegionsList = administrativeRegionsService.queryAdministrativeRegionsByParentDistrictCode(parentCode);
			nodes = new ArrayList<TreeNode>();
			for (AdministrativeRegionsEntity pojo : administrativeRegionsList) {
				TreeNode<AdministrativeRegionsEntity, TreeNode> treeNode = new TreeNode<AdministrativeRegionsEntity, TreeNode>();
				treeNode.setId(pojo.getCode());
				treeNode.setText(pojo.getName());
				treeNode.setLeaf(false);
				// 如果上级行政区域编码不为空
				if (pojo.getParentDistrictCode() != null&& !"".equals(pojo.getParentDistrictCode())) {
					treeNode.setParentId(pojo.getParentDistrictCode());
				} else {
					treeNode.setParentId(null);
				}
				//只有“行政区域级别”为市和县/区的才可以有复选框
				if(pojo.getDegree().trim().equals("CITY")||pojo.getDegree().trim().equals("DISTRICT_COUNTY")){
					treeNode.setChecked(false); //设置节点前有复选框
				}
				// 将获取的行政区域信息添加到节点中
				treeNode.setEntity(pojo);
				List<TreeNode> listChildrenTreeNodes=new ArrayList<TreeNode>();
				listChildrenTreeNodes=getExpressVehicleCoordinates(pojo.getCode());
				treeNode.setChildren(listChildrenTreeNodes);
				nodes.add(treeNode);
			}
			return returnSuccess();
		}*/
		
		
		
		
		
		// 根据行政区域编码查询行政区域集合信息
		List<AdministrativeRegionsEntity> administrativeRegionsList = administrativeRegionsService
				.queryAdministrativeRegionsByParentDistrictCode(parentCode);
		nodes = new ArrayList<TreeNode>();
		// 循环获取行政区域信息
		for (AdministrativeRegionsEntity pojo : administrativeRegionsList) {
			TreeNode<AdministrativeRegionsEntity, TreeNode> treeNode = new TreeNode<AdministrativeRegionsEntity, TreeNode>();
			treeNode.setId(pojo.getCode());
			treeNode.setText(pojo.getName());
			// 如果行政区域级别是DISTRICT_COUNTY(DISTRICT_COUNTY)（区县），则为叶子结点：
//			treeNode.setLeaf(StringUtils.equals(
//					DictionaryValueConstants.DISTRICT_COUNTY, pojo.getDegree()));
			treeNode.setLeaf(false);
			// 如果上级行政区域编码不为空
			if (pojo.getParentDistrictCode() != null
					&& !"".equals(pojo.getParentDistrictCode())) {
				treeNode.setParentId(pojo.getParentDistrictCode());
			} else {
				treeNode.setParentId(null);
			}
			//只有“行政区域级别”为市和县/区的才可以有复选框
			if(pojo.getDegree().trim().equals("CITY")||pojo.getDegree().trim().equals("DISTRICT_COUNTY")){
				treeNode.setChecked(false); //设置节点前有复选框
			}
			// 将获取的行政区域信息添加到节点中
			treeNode.setEntity(pojo);
			//如果节点行政区域编码是地级市,则查询出区县信息以及区县所以对应的快递和落地配网点信息
			if(StringUtils.equals(DictionaryValueConstants.DISTRICT_CITY, administrativeRegions.getDegree())){
				treeNode.setChildren(getExpressVehicleCoordinates(pojo.getCode()));
			}
			nodes.add(treeNode);
		}
		return returnSuccess();
	}
	
	
	//根据区县编码查询出落地配网点和快递网点
	public List<TreeNode> getExpressVehicleCoordinates(String parentCode){
		//查询快递试点网点信息
		List<AdministrativeRegionsDto> serverCoordinatesList = administrativeRegionsService.queryServerCoordinatesByCode(parentCode);
		//查询非试点快递网点信息
		List<AdministrativeRegionsDto> serverCoordinatesNotList = administrativeRegionsService.queryServerCoordinatesNotByCode(parentCode);
		//查询落地配网点信息
		List<OuterBranchEntity> vehicleAgencyDeptsList = vehicleAgencyDeptService.queryServerCoordinatesByCountyCode(parentCode);
		
		List<TreeNode> nodes = new ArrayList<TreeNode>();
		//添加快递试点网点信息
		if(serverCoordinatesList!=null &&serverCoordinatesList.size()>0){
			for (AdministrativeRegionsDto dto : serverCoordinatesList) {
				TreeNode<AdministrativeRegionsEntity, TreeNode> treeNode = new TreeNode<AdministrativeRegionsEntity, TreeNode>();
				if(dto.getDepCoordinate()==null){
					continue;
				}
				if(dto.getExpressDeliveryCoordinate()==null){
					continue;
				}
				
				treeNode.setId(dto.getExpressDeliveryCoordinate()+"_"+dto.getDepCoordinate()+"_"+dto.getMapType());
				treeNode.setText(dto.getName());
				treeNode.setLeaf(true);
				treeNode.setParentId(parentCode);
				treeNode.setChecked(false); //设置节点前有复选框
				nodes.add(treeNode);
			}
		}
		//添加非试点快递网点信息
		if(serverCoordinatesNotList!=null &&serverCoordinatesNotList.size()>0){
			for (AdministrativeRegionsDto dto : serverCoordinatesNotList) {
				TreeNode<AdministrativeRegionsEntity, TreeNode> treeNode = new TreeNode<AdministrativeRegionsEntity, TreeNode>();
				if(dto.getDepCoordinate()==null){
					continue;
				}
				if(dto.getExpressDeliveryCoordinate()==null){
					continue;
				}
				
				treeNode.setId(dto.getExpressDeliveryCoordinate()+"_"+dto.getDepCoordinate()+"_"+dto.getMapType());
				treeNode.setText(dto.getName());
				treeNode.setLeaf(true);
				treeNode.setParentId(parentCode);
				treeNode.setChecked(false); //设置节点前有复选框
				nodes.add(treeNode);
			}
		}
		//添加落地配网点信息
		if(vehicleAgencyDeptsList!=null &&vehicleAgencyDeptsList.size()>0){
			for (OuterBranchEntity entity : vehicleAgencyDeptsList) {
				TreeNode<OuterBranchEntity, TreeNode> treeNode = new TreeNode<OuterBranchEntity, TreeNode>();
				if(entity.getDeptCoordinate()==null){
					continue;
				}
				if(entity.getDeliveryCoordinate()==null){
					continue;
				}
				treeNode.setId(entity.getDeliveryCoordinate()+"_"+entity.getDeptCoordinate()+"_"+entity.getMapType());
				treeNode.setText(entity.getAgentDeptName());
				treeNode.setLeaf(true);
				treeNode.setParentId(parentCode);
				treeNode.setChecked(false); //设置节点前有复选框
				nodes.add(treeNode);
			}
		}
		return nodes;
	}
	
	
	/**
	 * 下面是声明的变更
	 */
	private static final long serialVersionUID = -4387687988772020011L;

	// 用于注入行政区域业务服务实现类
	private IAdministrativeRegionsService administrativeRegionsService;
	
	private IVehicleAgencyDeptService vehicleAgencyDeptService;

	private AdministrativeRegionsVo administrativeRegionsVo;
	
	private String params;
	
	@SuppressWarnings({ "rawtypes" })
	private List<TreeNode> nodes;
	// 日志信息
	//private static final Logger LOGGER = LoggerFactory
			//.getLogger(AdministrativeRegionsService.class);

	
	
	
	@SuppressWarnings("rawtypes")
	public List<TreeNode> getNodes() {
		return nodes;
	}

	public void setAdministrativeRegionsService(
			IAdministrativeRegionsService administrativeRegionsService) {
		this.administrativeRegionsService = administrativeRegionsService;
	}

	public AdministrativeRegionsVo getAdministrativeRegionsVo() {
		return administrativeRegionsVo;
	}

	public void setAdministrativeRegionsVo(
			AdministrativeRegionsVo administrativeRegionsVo) {
		this.administrativeRegionsVo = administrativeRegionsVo;
	}

	public void setVehicleAgencyDeptService(
			IVehicleAgencyDeptService vehicleAgencyDeptService) {
		this.vehicleAgencyDeptService = vehicleAgencyDeptService;
	}

	public String getParams() {
		return params;
	}

	public void setParams(String params) {
		this.params = params;
	}

}
