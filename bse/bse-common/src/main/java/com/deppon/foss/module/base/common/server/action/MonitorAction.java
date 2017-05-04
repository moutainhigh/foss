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
 * PROJECT NAME	: bse-common
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/common/server/action/MonitorAction.java
 * 
 * FILE NAME        	: MonitorAction.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
/*
 * PROJECT NAME: bse-common
 * PACKAGE NAME: com.deppon.foss.module.base.common.server.action
 * FILE    NAME: MonitorAction.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */
package com.deppon.foss.module.base.common.server.action;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.TreeNode;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserDeptAuthorityTreeNode;
import com.deppon.foss.module.base.common.api.server.service.IMonitorOrgService;
import com.deppon.foss.module.base.common.api.server.service.IMonitorQueryService;
import com.deppon.foss.module.base.common.api.shared.define.BusinessMonitorIndicator;
import com.deppon.foss.module.base.common.api.shared.define.MonitorConstant;
import com.deppon.foss.module.base.common.api.shared.vo.MonitorVo;
import com.deppon.foss.util.CollectionUtils;
import com.deppon.foss.util.DateUtils;
import com.deppon.foss.util.define.FossConstants;

/**
 * 监控Action
 * 
 * @author 096598-foss-zhongyubing
 * @date 2013-2-27 上午10:52:05
 */
public class MonitorAction extends AbstractAction {

	private static final long serialVersionUID = -632086951897058285L;
	/**
	 * 日志管理器
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(MonitorAction.class);
	/**
	 * 监控VO
	 */
	private MonitorVo vo = new MonitorVo();
	/**
	 * 节点列表
	 */
	@SuppressWarnings("rawtypes")
	private List<TreeNode> nodes;

	/**
	 * 导出Excel 文件流
	 */
	private InputStream excelStream;
	/**
	 * 导出Excel 文件名
	 */
	private String fileName;

	/**
	 * 树查询
	 */
	private IMonitorOrgService monitorOrgService;
	/**
	 * 监控查询Service
	 */
	private IMonitorQueryService monitorQueryService;

	public void setMonitorOrgService(IMonitorOrgService monitorOrgService) {
		this.monitorOrgService = monitorOrgService;
	}

	public void setMonitorQueryService(IMonitorQueryService monitorQueryService) {
		this.monitorQueryService = monitorQueryService;
	}

	public MonitorVo getVo() {
		return vo;
	}

	public void setVo(MonitorVo vo) {
		this.vo = vo;
	}

	@SuppressWarnings("rawtypes")
	public List<TreeNode> getNodes() {
		return nodes;
	}

	/**
	 * 查询树结构
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2013-2-27 上午11:00:43
	 */
	@JSON
	public String loadMonitorOrgTree() {
		try {
			// 父节点ID不为空，则不处理
			if (vo != null) {
				if (StringUtils.isBlank(vo.getParentOrgCode())) {
					// 设置为事业部
					vo.setDivision(MonitorConstant.DIVISION_Y);
				}
			}
			// 查询构建数结构
			//第一次vo为空的时候查询所有为事业部的数据
			makeTreeData(monitorOrgService.queryOrgList(vo));
		} catch (BusinessException e) {
			LOGGER.error("loadMonitorOrgTree", e);
			return super.returnError(e.getMessage());
		}
		return super.returnSuccess();
	}

	/**
	 * 查询常规表头
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2013-3-4 上午11:49:21
	 */
	@JSON
	public String queryCommonIndicatorHeader() {
		try {
			if (vo != null) {
				vo.setHeaders(monitorQueryService.queryCommonIndicatorHeader(vo.getIndicatorGroups()));
			}
		} catch (BusinessException e) {
			LOGGER.error("queryCommonIndicatorHeader", e);
			return super.returnError(e.getMessage());
		}
		return super.returnSuccess();
	}

	/**
	 * 查询新业务表头
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2013-3-4 上午11:49:21
	 */
	@JSON
	public String queryNewIndicatorHeader() {
		try {
			if (vo != null) {
				vo.setHeaders(monitorQueryService.queryNewIndicatorHeader(vo.getIndicatorGroups()));
			}
		} catch (BusinessException e) {
			LOGGER.error("queryNewIndicatorHeader", e);
			return super.returnError(e.getMessage());
		}
		return super.returnSuccess();
	}

	/**
	 * 查询新业务表头
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2013-3-4 上午11:49:21
	 */
	@JSON
	public String queryApplicationIndicatorHeader() {
		try {
			if (vo != null) {
				vo.setAppHeaders(monitorQueryService.queryApplicationIndicatorHeader(vo.getIndicatorGroup()));
			}
		} catch (BusinessException e) {
			LOGGER.error("queryNewIndicatorHeader", e);
			return super.returnError(e.getMessage());
		}
		return super.returnSuccess();
	}

	/**
	 * 通过类别查询指标组列表
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2013-3-4 上午11:49:21
	 */
	@JSON
	public String queryIndicatorGroupByCategory() {
		try {
			if (vo != null) {
				// 默认为常规
				String bussinessType = BusinessMonitorIndicator.CATEGORY_COMMON;
				// 机构不为空
				if (StringUtils.isNotBlank(vo.getBussinessType())) {
					bussinessType = vo.getBussinessType();
				}
				// 指标组列表
				vo.setUseableIndicators(monitorQueryService.queryIndicatorGroupByCategory(bussinessType));

			}
		} catch (BusinessException e) {
			LOGGER.error("queryIndicatorGroupByCategory", e);
			return super.returnError(e.getMessage());
		}
		return super.returnSuccess();
	}

	/**
	 * 查询常规业务表体数据
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2013-3-4 上午11:49:21
	 */
	@JSON
	public String queryDailyCommonIndicatorData() {
		try {
			if (vo != null) {
				// 默认DIP
				String orgCode = FossConstants.ROOT_ORG_CODE;
				// 默认时间
				Date date = Calendar.getInstance().getTime();
				// 机构不为空
				if (StringUtils.isNotBlank(vo.getOrgCode())) {
					orgCode = vo.getOrgCode();
				}
				// 时间不为空
				if (StringUtils.isNotBlank(vo.getDateStr())) {
					date = DateUtils.convert(vo.getDateStr());
				}
				// 数据结果
				// 月范围
				if (MonitorConstant.ANALYSISTYPE_MONITORDATE.equals(vo.getAnalysisType())) {
					vo.setDatas(monitorQueryService.queryMonthlyCommonIndicatorData(date, vo.getIndicatorGroups(),
							orgCode));
				} else
				// 日范围
				if (MonitorConstant.ANALYSISTYPE_MONITORTIMERANGE.equals(vo.getAnalysisType())) {
					vo.setDatas(monitorQueryService.queryDailyCommonIndicatorData(date, vo.getIndicatorGroups(),
							orgCode));
				}

			}
		} catch (BusinessException e) {
			LOGGER.error("queryDailyCommonIndicatorData", e);
			return super.returnError(e.getMessage());
		}
		return super.returnSuccess();
	}

	/**
	 * 查询新业务表体数据
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2013-3-4 上午11:49:21
	 */

	@JSON
	public String queryDailyNewIndicatorData() {
		try {
			if (vo != null) {
				// 默认时间
				Date date = Calendar.getInstance().getTime();
				// 时间不为空
				if (StringUtils.isNotBlank(vo.getDateStr())) {
					date = DateUtils.convert(vo.getDateStr());
				}
				if(this.getLimit()+ this.getStart()>vo.getOrgCodes().size()){
					this.limit=vo.getOrgCodes().size();
				}else{
					this.limit=this.getLimit()+ this.getStart();
				}
				
				
				// 当天数据结果
				vo.setDatas(monitorQueryService.queryDailyNewIndicatorData(date, vo.getIndicatorGroups(),
						vo.getOrgCodes().subList(getStart(), getLimit())));
				this.setTotalCount(totalCount);

			}
		} catch (BusinessException e) {
			LOGGER.error("queryDailyNewIndicatorData", e);
			return super.returnError(e.getMessage());
		}
		return super.returnSuccess();
	}

	/**
	 * 查询应用数据表体数据
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2013-3-4 上午11:49:21 total dayTotal monthTotal
	 */
	private String total;
	
	 
	public String getTotal() {
		return total;
	}

	public void setTotal(String total) {
		this.total = total;
	}

	@JSON
	public String queryDailyApplicationIndicatorData() {
		
		try {
			if (vo != null) {
				// 默认时间
				Date defaultDate = Calendar.getInstance().getTime();
				Date startTime = null;
				Date endTime=null;
				Date date=new Date();
				if(total.endsWith("dayTotal")){
					//日
					startTime = org.apache.commons.lang.time.DateUtils.truncate(defaultDate, Calendar.DATE);
					
				}else{
					//月
					startTime = org.apache.commons.lang.time.DateUtils.truncate(defaultDate, Calendar.MONTH);
					endTime = org.apache.commons.lang.time.DateUtils.addMonths(startTime, 1);
					
				}
				
				
//				// 时间不为空
//				if (StringUtils.isNotBlank(vo.getDateStr())) {
//					date = DateUtils.convert(vo.getDateStr());
//				}
//				System.out.println(vo.getStartTime()+vo.getEndTime());
//				DateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
//				Date startTime=format1.parse(vo.getStartTime());
//				Date endTime=format1.parse(vo.getEndTime());
				// 当天数据结果
				vo.setDatas(monitorQueryService.queryDailyApplicationIndicatorData(date, vo.getIndicatorGroup(),
						vo.getOrgCodes(),startTime,endTime));

			}
		} catch (BusinessException e) {
			LOGGER.error("queryDailyApplicationIndicatorData", e);
			return super.returnError(e.getMessage());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return super.returnSuccess();
	}

	/**
	 * 查询应用数据表体数据
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2013-3-4 上午11:49:21
	 */
	public String exportData() {
		try {
			// 默认时间
			Date date = Calendar.getInstance().getTime();
			// 导出文件名			
			fileName = URLEncoder.encode(MonitorConstant.NORMAL_EXCEL_FILE_NAME, "UTF-8");
			//表明名
			String sheetName=MonitorConstant.NORMAL_EXCEL_FILE_NAME;
			//判空
			if(vo !=null){
				//新业务
				if(BusinessMonitorIndicator.CATEGORY_NEW.equals(vo.getBussinessType())){
					//新业务
					fileName = URLEncoder.encode(MonitorConstant.NEW_EXCEL_FILE_NAME, "UTF-8");
					//新业务
					sheetName = MonitorConstant.NEW_EXCEL_FILE_NAME;
				}
				//应用数据
				else if (BusinessMonitorIndicator.CATEGORY_APPLICATION.equals(vo.getBussinessType())){
					//应用数据
					fileName = URLEncoder.encode(MonitorConstant.APP_EXCEL_FILE_NAME, "UTF-8");
					//应用数据
					sheetName = MonitorConstant.APP_EXCEL_FILE_NAME;
				}
				// 时间不为空
				if (StringUtils.isNotBlank(vo.getDateStr())) {
					date = DateUtils.convert(vo.getDateStr());
				}
			}
		
			// 导出文件流
			//313353 sonar
			String total = (vo==null ? StringUtils.EMPTY:vo.getTotal());
			excelStream = monitorQueryService.exportExcelStream(vo, date,sheetName,total);
			return returnSuccess();
		} catch (BusinessException e) {
			LOGGER.error("exportData", e);
			return super.returnError(e.getMessage());
		} catch (UnsupportedEncodingException e) {
			LOGGER.error("exportData", e);
			return super.returnError(e.getMessage());
		}
	}

	/**
	 * 创建节点列表
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2013-2-27 上午11:17:16
	 */
	@SuppressWarnings("rawtypes")
	private void makeTreeData(List<OrgAdministrativeInfoEntity> orgAdministrativeInfoEntityList) {
		/**
		 * 获取前台传来的全路径集合的字符串，将其通过“,”分割开，获取快速查询部门的父部门ID 并将数据放到HashSet中
		 */
		nodes = new ArrayList<TreeNode>();
		if (CollectionUtils.isNotEmpty(orgAdministrativeInfoEntityList)) {
			// 循环解析节点信息
			for (OrgAdministrativeInfoEntity orgAdministrativeInfoEntity : orgAdministrativeInfoEntityList) {
				// 构建节点信息
				TreeNode treeNode = fetchTreeNode(orgAdministrativeInfoEntity);
				// 加入节点列表
				nodes.add(treeNode);
			}
		}
	}

	/**
	 * 根据部门实体生成树节点
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2013-2-27 上午11:17:16
	 * @return TreeNode
	 * @param
	 */
	@SuppressWarnings({ "rawtypes" })
	private TreeNode fetchTreeNode(OrgAdministrativeInfoEntity orgAdministrativeInfoEntity) {
		// 临时节点
		UserDeptAuthorityTreeNode treeNodeTemp = new UserDeptAuthorityTreeNode();
		// 编码
		treeNodeTemp.setId(orgAdministrativeInfoEntity.getCode());
		// 节点名
		treeNodeTemp.setText(orgAdministrativeInfoEntity.getName());
		// 是否叶子节点
		checkLeafNode(orgAdministrativeInfoEntity, treeNodeTemp);
		// 父节点编码
		if (orgAdministrativeInfoEntity.getParentOrgUnifiedCode() != null) {
			treeNodeTemp.setParentId(orgAdministrativeInfoEntity.getParentOrgCode());
		} else {
			treeNodeTemp.setParentId(null);
		}
		// 默认设置节点未被选中
		treeNodeTemp.setChecked(false);
		// 本节点信息
		treeNodeTemp.setEntity(orgAdministrativeInfoEntity);
		// 子节点
		treeNodeTemp.setChildren(null);
		return treeNodeTemp;
	}

	/**
	 * 检查是否为子节点
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2013-2-27 下午7:20:53
	 */
	private void checkLeafNode(OrgAdministrativeInfoEntity orgAdministrativeInfoEntity,
			UserDeptAuthorityTreeNode treeNode) {
		// 查询条件
		MonitorVo tempVo = new MonitorVo();
		// 根据三种情况判断是否为叶子节点
		if (orgAdministrativeInfoEntity != null) {
			// 父节点编码
			tempVo.setParentOrgCode(orgAdministrativeInfoEntity.getCode());
			// 查询当前节点是否有子节点
			List<OrgAdministrativeInfoEntity> childList = monitorOrgService.queryOrgList(tempVo);
			// 无子节点，则为叶子节点
			if (CollectionUtils.isEmpty(childList)) {
				treeNode.setLeaf(true);
			} else {
				treeNode.setLeaf(false);
			}
			// 外场
			if (MonitorConstant.TRANSFER_CENTER_Y.equals(orgAdministrativeInfoEntity.getTransferCenter())) {
				treeNode.setLeaf(true);
			}
			// 车队
			if (MonitorConstant.TRANS_DEPARTMENT_Y.equals(orgAdministrativeInfoEntity.getTransDepartment())) {
				treeNode.setLeaf(true);
			}
			// 营业大区
			if (MonitorConstant.BIG_REGION_Y.equals(orgAdministrativeInfoEntity.getBigRegion())) {
				treeNode.setLeaf(true);
			}
		}

	}

	public InputStream getExcelStream() {
		return excelStream;
	}

	public String getFileName() {
		return fileName;
	}

}
