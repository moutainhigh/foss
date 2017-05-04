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
 * PROJECT NAME	: bse-common-api
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/common/api/shared/vo/MonitorVo.java
 * 
 * FILE NAME        	: MonitorVo.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
/*
 * PROJECT NAME: bse-common-api
 * PACKAGE NAME: com.deppon.foss.module.base.common.api.shared.vo
 * FILE    NAME: MonitorVo.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */
package com.deppon.foss.module.base.common.api.shared.vo;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.deppon.foss.module.base.common.api.shared.domain.MonitorIndicatorEntity;

/**
 * 监控VO
 * 
 * @author 096598-foss-zhongyubing
 * @date 2013-2-27 上午11:03:32
 */
public class MonitorVo implements java.io.Serializable {
	private static final long serialVersionUID = 5408182178268252506L;
	
	

	/**
	 * 总记录数
	 */
	private String total;
	/**
	 * 父节点编码
	 */
	private String parentOrgCode;
	/**
	 * 业务类型
	 */
	private String bussinessType;
	/**
	 * 查询分类
	 */
	private String queryType;
	/**
	 * 统计方式
	 */
	private String analysisType;
	/**
	 * 事业部
	 */
	private String division;
	/**
	 * 查询机构编码
	 */
	private String orgCode;
	/**
	 * 日期字符串xxxx-xx-xx
	 */
	private String dateStr;

	/**
	 * 子节点编码
	 */
	private List<String> childCodes;
	/**
	 * 组织机构代码
	 */
	private List<String> orgCodes;
	/**
	 * 指标组
	 */
	private List<String> indicatorGroups;
	/**
	 * 单个指标组
	 */
	private String indicatorGroup;
	/**
	 * 所有可查询指标
	 */
	private List<String> useableIndicators;
	/**
	 * 指标组，组内指标列表
	 */
	private LinkedHashMap<String, List<MonitorIndicatorEntity>> headers;

	/**
	 * 标题数据
	 */
	private List<Map<String, String>> datas;
	/**
	 * 应用数据表头
	 */
	private LinkedHashMap<String, String> appHeaders;
	/**
	 * 监控日期
	 */
	private String monitorDate;

	public String getParentOrgCode() {
		return parentOrgCode;
	}

	public void setParentOrgCode(String parentOrgCode) {
		this.parentOrgCode = parentOrgCode;
	}

	public String getBussinessType() {
		return bussinessType;
	}

	public void setBussinessType(String bussinessType) {
		this.bussinessType = bussinessType;
	}

	public String getQueryType() {
		return queryType;
	}

	public void setQueryType(String queryType) {
		this.queryType = queryType;
	}

	public String getAnalysisType() {
		return analysisType;
	}

	public void setAnalysisType(String analysisType) {
		this.analysisType = analysisType;
	}

	public List<String> getChildCodes() {
		return childCodes;
	}

	public void setChildCodes(List<String> childCodes) {
		this.childCodes = childCodes;
	}

	public String getDivision() {
		return division;
	}

	public void setDivision(String division) {
		this.division = division;
	}

	public List<String> getIndicatorGroups() {
		return indicatorGroups;
	}

	public void setIndicatorGroups(List<String> indicatorGroups) {
		this.indicatorGroups = indicatorGroups;
	}

	public LinkedHashMap<String, List<MonitorIndicatorEntity>> getHeaders() {
		return headers;
	}

	public void setHeaders(LinkedHashMap<String, List<MonitorIndicatorEntity>> headers) {
		this.headers = headers;
	}

	public List<Map<String, String>> getDatas() {
		return datas;
	}

	public void setDatas(List<Map<String, String>> datas) {
		this.datas = datas;
	}

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public List<String> getUseableIndicators() {
		return useableIndicators;
	}

	public void setUseableIndicators(List<String> useableIndicators) {
		this.useableIndicators = useableIndicators;
	}

	public String getDateStr() {
		return dateStr;
	}

	public void setDateStr(String dateStr) {
		this.dateStr = dateStr;
	}

	public List<String> getOrgCodes() {
		return orgCodes;
	}

	public void setOrgCodes(List<String> orgCodes) {
		this.orgCodes = orgCodes;
	}

	public LinkedHashMap<String, String> getAppHeaders() {
		return appHeaders;
	}

	public void setAppHeaders(LinkedHashMap<String, String> appHeaders) {
		this.appHeaders = appHeaders;
	}

	public String getIndicatorGroup() {
		return indicatorGroup;
	}

	public void setIndicatorGroup(String indicatorGroup) {
		this.indicatorGroup = indicatorGroup;
	}

	public String getMonitorDate() {
		return monitorDate;
	}

	public void setMonitorDate(String monitorDate) {
		this.monitorDate = monitorDate;
	}
	
	public String getTotal() {
		return total;
	}

	public void setTotal(String total) {
		this.total = total;
	}

}
