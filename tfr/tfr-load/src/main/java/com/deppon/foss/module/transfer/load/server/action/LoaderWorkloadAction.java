/**
 *  initial comments.
 */
/*******************************************************************************
 * Copyright 2013 TFR TEAM
 *  
 *  Licensed under the DEPPON License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *  
 *     http://www.deppon.com/licenses/LICENSE-2.0
 *  
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *  Contributors:
 *  038300-foss-pengzhen - initial API and implementation
 * 
 *  PROJECT NAME  : tfr-load
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/load/server/action/LoaderWorkloadAction.java
 *  
 *  FILE NAME          :LoaderWorkloadAction.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.transfer.load.server.action;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.module.base.baseinfo.api.server.service.complex.IOrgAdministrativeInfoComplexService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.transfer.common.api.shared.exception.TfrBusinessException;
import com.deppon.foss.module.transfer.load.api.server.service.ILoaderWorkloadService;
import com.deppon.foss.module.transfer.load.api.shared.dto.ErrorVolumeDeductionDto;
import com.deppon.foss.module.transfer.load.api.shared.dto.LoaderWorkloadDto;
import com.deppon.foss.module.transfer.load.api.shared.vo.LoaderWorkloadVo;

/**
 * 查询装卸车工作量Action
 * @author ibm-zhangyixin
 * @date 2012-11-28 下午4:27:08
 */
public class LoaderWorkloadAction extends AbstractAction {
	private static final long serialVersionUID = -6570935217333669116L;
	
	/** 导出Excel 文件流*/
	private InputStream excelStream;
	/** 导出Excel 文件名*/
	private String fileName;
	/**查询装卸车工作量VO**/
	private LoaderWorkloadVo loaderWorkloadVo = new LoaderWorkloadVo();
	/**查询装卸车工作量**/
	private ILoaderWorkloadService loaderWorkloadService;
	/**部门 复杂查询 service**/
	private IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService;
	
	/**
	 * 个人统计查询
	 * @author ibm-zhangyixin
	 * @date 2012-12-17 下午6:42:44
	 */
	@JSON
	public String queryPersonCount() {
		LoaderWorkloadDto loaderWorkloadDto = loaderWorkloadVo.getLoaderWorkloadDto();
		loaderWorkloadDto.setLoaderOrgCodes(getChildDept(loaderWorkloadDto.getLoaderOrgCode()));
		List<LoaderWorkloadDto> loaderWorkloadDtos = loaderWorkloadService.queryPersonCount(loaderWorkloadDto, this.getLimit(), this.getStart());
		Long totCount = loaderWorkloadService.getTotCountPersonCount(loaderWorkloadDto);
		this.setTotalCount(totCount);
		loaderWorkloadVo.setLoaderWorkloadList(loaderWorkloadDtos);
		return returnSuccess();
	}
	/**
	 * 个人统计查询(快递)
	 * @author zhangpeng
	 * @date 2015-12-12下午6:42:44
	 */
	@JSON
	public String queryPersonCountExpress() {
		LoaderWorkloadDto loaderWorkloadDto = loaderWorkloadVo.getLoaderWorkloadDto();
		loaderWorkloadDto.setLoaderOrgCodes(getChildDept(loaderWorkloadDto.getLoaderOrgCode()));
		List<LoaderWorkloadDto> loaderWorkloadDtos = loaderWorkloadService.queryPersonCountExpress(loaderWorkloadDto, this.getLimit(), this.getStart());
		Long totCount = loaderWorkloadService.getTotCountPersonCountExpress(loaderWorkloadDto);
		this.setTotalCount(totCount);
		loaderWorkloadVo.setLoaderWorkloadList(loaderWorkloadDtos);
		return returnSuccess();
	}

	/**
	 * 个人统计查询合计  
	 * @author ibm-zhangyixin
	 * @date 2012-12-17 下午6:42:44
	 */
	@JSON
	public String queryPersonCountSummary() {
		LoaderWorkloadDto loaderWorkloadDto = loaderWorkloadVo.getLoaderWorkloadDto();
		loaderWorkloadDto.setLoaderOrgCodes(getChildDept(loaderWorkloadDto.getLoaderOrgCode()));
		List<LoaderWorkloadDto> loaderWorkloadDtos = loaderWorkloadService.queryPersonCountSummary(loaderWorkloadDto);
		loaderWorkloadVo.setLoaderWorkloadDtos(loaderWorkloadDtos);
		return returnSuccess();
	}
	
	/**
	 * 个人统计查询合计  queryPersonCountSummaryExpress
	 * @author ibm-zhangyixin
	 * @date 2012-12-17 下午6:42:44
	 */
	@JSON
	public String queryPersonCountSummaryExpress() {
		LoaderWorkloadDto loaderWorkloadDto = loaderWorkloadVo.getLoaderWorkloadDto();
		loaderWorkloadDto.setLoaderOrgCodes(getChildDept(loaderWorkloadDto.getLoaderOrgCode()));
		List<LoaderWorkloadDto> loaderWorkloadDtos = loaderWorkloadService.queryPersonCountSummaryExpress(loaderWorkloadDto);
		loaderWorkloadVo.setLoaderWorkloadDtos(loaderWorkloadDtos);
		return returnSuccess();
	}

	/**
	 * 队统计查询
	 * @author ibm-zhangyixin
	 * @date 2012-12-17 下午6:42:59
	 */
	@JSON
	public String queryTeamCount() {
		LoaderWorkloadDto loaderWorkloadDto = loaderWorkloadVo.getLoaderWorkloadDto();
		loaderWorkloadDto.setLoaderOrgCodes(getChildDept(loaderWorkloadDto.getLoaderOrgCode()));
		List<LoaderWorkloadDto> loaderWorkloadDtos = loaderWorkloadService.queryTeamCount(loaderWorkloadDto, this.getLimit(), this.getStart());
		Long totCount = loaderWorkloadService.getTotCountTeamCount(loaderWorkloadDto);
		this.setTotalCount(totCount);
		loaderWorkloadVo.setLoaderWorkloadList(loaderWorkloadDtos);
		return returnSuccess();
	}
	
	/**
	 * 队统计查询
	 * @author zhangpeng
	 * @date 2015-12-10 下午6:42:59
	 */
	@JSON
	public String queryTeamCountExpress() {
		LoaderWorkloadDto loaderWorkloadDto = loaderWorkloadVo.getLoaderWorkloadDto();
		loaderWorkloadDto.setLoaderOrgCodes(getChildDept(loaderWorkloadDto.getLoaderOrgCode()));
		List<LoaderWorkloadDto> loaderWorkloadDtos = loaderWorkloadService.queryTeamCountExpress(loaderWorkloadDto, this.getLimit(), this.getStart());
		Long totCount = loaderWorkloadService.getTotCountTeamCount(loaderWorkloadDto);
		this.setTotalCount(totCount);
		loaderWorkloadVo.setLoaderWorkloadList(loaderWorkloadDtos);
		return returnSuccess();
	}
	
	/**
	 * @Title: querySuperiorOrgByOrgCode 
	 * @Description: 根据当前部门code获取上级外场 
	 * @return    
	 * @return String    返回类型 
	 * querySuperiorOrgByOrgCode
	 * @author: ibm-zhangyixin
	 * @throws 
	 * Date:2013-5-31上午10:34:42
	 */
	@JSON
	public String querySuperiorOrgByOrgCode() {
		try{
			//当前部门Code
			String currentDeptCode = FossUserContext.getCurrentDeptCode();
			//当前部门顶级组织
			OrgAdministrativeInfoEntity administrativeInfo = loaderWorkloadService.querySuperiorOrgByOrgCode(currentDeptCode);
			//当前部门顶级组织code
			String orgCode = administrativeInfo.getCode();
			loaderWorkloadVo.setSuperOrgCode(orgCode);
		}catch(TfrBusinessException e){
			return returnError(e);
		}
		return returnSuccess();
	}

	/**
	 * 队统计查询合计
	 * @author ibm-zhangyixin
	 * @date 2012-12-27 下午4:13:45
	 */
	@JSON
	public String queryTeamCountSummary() {
		LoaderWorkloadDto loaderWorkloadDto = loaderWorkloadVo.getLoaderWorkloadDto();
		loaderWorkloadDto.setLoaderOrgCodes(getChildDept(loaderWorkloadDto.getLoaderOrgCode()));
		loaderWorkloadDto = loaderWorkloadService.queryTeamCountSummary(loaderWorkloadDto);
		loaderWorkloadVo.setLoaderWorkloadDto(loaderWorkloadDto);
		return returnSuccess();
	}
	
	/**
	 * 队统计查询合计(快递)
	 * @author zhangpeng
	 * @date 2015-12-09 下午4:13:45
	 */
	@JSON
	public String queryTeamCountSummaryExpress() {
		LoaderWorkloadDto loaderWorkloadDto = loaderWorkloadVo.getLoaderWorkloadDto();
		loaderWorkloadDto.setLoaderOrgCodes(getChildDept(loaderWorkloadDto.getLoaderOrgCode()));
		loaderWorkloadDto = loaderWorkloadService.queryTeamCountSummaryExpress(loaderWorkloadDto);
		loaderWorkloadVo.setLoaderWorkloadDto(loaderWorkloadDto);
		return returnSuccess();
	}

	/**
	 * 导出个人统计查询结果
	 * @author ibm-zhangyixin
	 * @date 2012-12-27 下午12:31:43
	 */
	public String exportPersonCountExcel() {
		try{
			LoaderWorkloadDto loaderWorkloadDto = loaderWorkloadVo.getLoaderWorkloadDto();
			loaderWorkloadDto.setLoaderOrgCodes(getChildDept(loaderWorkloadDto.getLoaderOrgCode()));
			fileName = loaderWorkloadService.encodeFileName("个人统计");
			excelStream = loaderWorkloadService.exportPersonCountExcel(loaderWorkloadDto);
		}catch(TfrBusinessException e){
			return returnError(e);
		}
		return returnSuccess();
	}

	/**
	 * 导出队统计查询结果
	 * @author ibm-zhangyixin
	 * @date 2012-12-27 下午12:31:43
	 */
	public String exportTeamCountExcel() {
		try{
			LoaderWorkloadDto loaderWorkloadDto = loaderWorkloadVo.getLoaderWorkloadDto();
			loaderWorkloadDto.setLoaderOrgCodes(getChildDept(loaderWorkloadDto.getLoaderOrgCode()));
			fileName = loaderWorkloadService.encodeFileName("队统计");
			excelStream = loaderWorkloadService.exportTeamCountExcel(loaderWorkloadDto);
		}catch(TfrBusinessException e){
			return returnError(e);
		}
		return returnSuccess();
	}
	public String queryErrorVolumeDeductionList(){
		try{
			ErrorVolumeDeductionDto errorVolumeDeductionDto = loaderWorkloadVo.getErrorVolumeDeductionDto();
			//将标杆编码转换为Foss编码
			String orgCode = errorVolumeDeductionDto.getRespDeptCode();
			if(StringUtils.isNotEmpty(orgCode)){
				List<String> unifiedCodes = getChildUnifiedCode(orgCode);
				errorVolumeDeductionDto.setUnifiedCodes(unifiedCodes);
			}
			List<ErrorVolumeDeductionDto> list = loaderWorkloadService.queryErrorVolumeDeductionList(errorVolumeDeductionDto,this.getLimit(), this.getStart());
			loaderWorkloadVo.setErrorVolumeDeductionList(list);
			return returnSuccess();
		}catch(TfrBusinessException e){
			return returnError(e);
		}
	}
	/**
	 * 根据部门code查询出当前部门下(包括当前部门)所有的子部门
	 * @author ibm-zhangyixin
	 * @date 2013-3-13 上午9:28:06
	 */
	private List<String> getChildDept(String orgCode) {
		if(StringUtils.isEmpty(orgCode)) {
			return null;
		}
		//根据部门编码获取所属及下属部门信息 此部门及下属的所有部门。
		List<OrgAdministrativeInfoEntity> orgAdministrativeInfos = orgAdministrativeInfoComplexService.
				queryOrgAdministrativeInfoEntityAllSubByCode(orgCode);
		List<String> orgCodes = new ArrayList<String>(orgAdministrativeInfos.size());
		for(OrgAdministrativeInfoEntity orgAdministrativeInfo : orgAdministrativeInfos) {
			orgCodes.add(orgAdministrativeInfo.getCode());
		}
		//返回部门code
		return orgCodes;
	}
	/**
	 * 根据部门code查询出当前部门下(包括当前部门)所有的子部门标杆编码
	 * @author 257220
	 * @date 2015-10-13 上午9:28:06
	 */
	private List<String> getChildUnifiedCode(String orgCode) {
		if(StringUtils.isEmpty(orgCode)) {
			return null;
		}
		//根据部门编码获取所属及下属部门信息 此部门及下属的所有部门。
		List<OrgAdministrativeInfoEntity> orgAdministrativeInfos = orgAdministrativeInfoComplexService.
				queryOrgAdministrativeInfoEntityAllSubByCode(orgCode);
		List<String> unifiedCodes = new ArrayList<String>(orgAdministrativeInfos.size());
		for(OrgAdministrativeInfoEntity orgAdministrativeInfo : orgAdministrativeInfos) {
			unifiedCodes.add(orgAdministrativeInfo.getUnifiedCode());
		}
		//返回部门code
		return unifiedCodes;
	}
	/**
	 * 设置 查询装卸车工作量*.
	 *
	 * @param loaderWorkloadService the new 查询装卸车工作量*
	 */
	public void setLoaderWorkloadService(
			ILoaderWorkloadService loaderWorkloadService) {
		this.loaderWorkloadService = loaderWorkloadService;
	}

	/**
	 * 获取 查询装卸车工作量VO*.
	 *
	 * @return the 查询装卸车工作量VO*
	 */
	public LoaderWorkloadVo getLoaderWorkloadVo() {
		return loaderWorkloadVo;
	}

	/**
	 * 设置 查询装卸车工作量VO*.
	 *
	 * @param loaderWorkloadVo the new 查询装卸车工作量VO*
	 */
	public void setLoaderWorkloadVo(LoaderWorkloadVo loaderWorkloadVo) {
		this.loaderWorkloadVo = loaderWorkloadVo;
	}

	/**
	 * 获取 导出Excel 文件流.
	 *
	 * @return the 导出Excel 文件流
	 */
	public InputStream getExcelStream() {
		return excelStream;
	}

	/**
	 * 设置 导出Excel 文件流.
	 *
	 * @param excelStream the new 导出Excel 文件流
	 */
	public void setExcelStream(InputStream excelStream) {
		this.excelStream = excelStream;
	}

	/**
	 * 获取 导出Excel 文件名.
	 *
	 * @return the 导出Excel 文件名
	 */
	public String getFileName() {
		return fileName;
	}

	/**
	 * 设置 导出Excel 文件名.
	 *
	 * @param fileName the new 导出Excel 文件名
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	/**
	 * 设置 部门 复杂查询 service*.
	 *
	 * @param orgAdministrativeInfoComplexService the new 部门 复杂查询 service*
	 */
	public void setOrgAdministrativeInfoComplexService(
			IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService) {
		this.orgAdministrativeInfoComplexService = orgAdministrativeInfoComplexService;
	}
}