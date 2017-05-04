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
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/load/server/action/LoaderWorkloadModifyAction.java
 *  
 *  FILE NAME          :LoaderWorkloadModifyAction.java
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

import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.transfer.common.api.shared.exception.TfrBusinessException;
import com.deppon.foss.module.transfer.load.api.server.service.ILoadService;
import com.deppon.foss.module.transfer.load.api.server.service.ILoaderWorkloadModifyService;
import com.deppon.foss.module.transfer.load.api.shared.domain.LoaderWorkloadEntity;
import com.deppon.foss.module.transfer.load.api.shared.dto.LoaderWorkloadDetailDto;
import com.deppon.foss.module.transfer.load.api.shared.dto.LoaderWorkloadDto;
import com.deppon.foss.module.transfer.load.api.shared.vo.LoaderWorkloadVo;

/**
 * 修改装卸车工作量Action
 * @author ibm-zhangyixin
 * @date 2012-12-20 下午5:30:06
 */
public class LoaderWorkloadModifyAction extends AbstractAction {
	private static final long serialVersionUID = 3227837547978033757L;
	/**查询装卸车工作量VO**/
	private LoaderWorkloadVo loaderWorkloadVo = new LoaderWorkloadVo();
	/**修改装卸车工作量service**/
	private ILoaderWorkloadModifyService loaderWorkloadModifyService;
	
	
	/**
	 * 用于获取小部门所属的大部门
	 */
	private ILoadService loadService;
	

	
	/**
	 * 用于获取小部门所属的大部门
	 */
	public void setLoadService(ILoadService loadService) {
		this.loadService = loadService;
	}
	/**
	 * 
	 * 修改装卸车工作量查询方法
	 * @author ibm-zhangyixin
	 * @date 2012-12-25 上午11:27:19
	 */
	@JSON
	public String queryLoaderWorkloadDetail() {
		try {
			//获取查寻工作两的条件实体
			LoaderWorkloadDetailDto loaderWorkloadDetailDto =loaderWorkloadVo.getLoaderWorkloadDetailDto();
			//设置部门
			loaderWorkloadDetailDto.setOrgCode(querySuperiorOrgCode());
			//查询修改工作两数据
			List<LoaderWorkloadDetailDto> loaderWorkloadDetailList = loaderWorkloadModifyService.queryLoaderWorkloadDetail(loaderWorkloadDetailDto, this.getLimit(), this.getStart());
			Long totCount = loaderWorkloadModifyService.getTotCount(loaderWorkloadDetailDto);
			this.setTotalCount(totCount);
			loaderWorkloadVo.setLoaderWorkloadDetailList(loaderWorkloadDetailList);
		} catch (TfrBusinessException e) {
			return returnError(e);
		}
		return returnSuccess();
	}
	
	/**
	 * 
	 * 修改装卸车工作量查询方法(快递)
	 * @author ibm-zhangyixin
	 * @date 2012-12-25 上午11:27:19
	 */
	@JSON
	public String queryLoaderWorkloadDetailExpress() {
		try {
			//获取查寻工作两的条件实体
			LoaderWorkloadDetailDto loaderWorkloadDetailDto =loaderWorkloadVo.getLoaderWorkloadDetailDto();
			//设置部门
			loaderWorkloadDetailDto.setOrgCode(querySuperiorOrgCode());
			//查询修改工作两数据
			List<LoaderWorkloadDetailDto> loaderWorkloadDetailList = loaderWorkloadModifyService.queryLoaderWorkloadDetailExpress(loaderWorkloadDetailDto, this.getLimit(), this.getStart());
			Long totCount = loaderWorkloadModifyService.getTotCount(loaderWorkloadDetailDto);
			this.setTotalCount(totCount);
			loaderWorkloadVo.setLoaderWorkloadDetailList(loaderWorkloadDetailList);
		} catch (TfrBusinessException e) {
			return returnError(e);
		}
		return returnSuccess();
	}
	
	/**
	 * 统计查询的总票数, 总重量, 总件数
	 * @author ibm-zhangyixin
	 * @date 2012-12-25 上午11:26:45
	 */
	@JSON
	public String querySummaryDetail() {
		try {
			//获取查寻工作两的条件实体
			LoaderWorkloadDetailDto queryLoaderWorkloadDetailDto =loaderWorkloadVo.getLoaderWorkloadDetailDto();
			//设置部门
			queryLoaderWorkloadDetailDto.setOrgCode(querySuperiorOrgCode());
			//查询总票数, 总重量, 总件数
			LoaderWorkloadDetailDto loaderWorkloadDetailDto = loaderWorkloadModifyService.querySummaryDetail(queryLoaderWorkloadDetailDto);
			loaderWorkloadVo.setLoaderWorkloadDetailDto(loaderWorkloadDetailDto);
		} catch (TfrBusinessException e) {
			return returnError(e);
		}
		return returnSuccess();
	}
	
	/**
	 * 统计查询的总票数, 总重量, 总件数(快递)
	 * @author ibm-zhangyixin
	 * @date 2015-12-25 上午11:26:45
	 */
	@JSON
	public String querySummaryDetailExpress() {
		try {
			//获取查寻工作两的条件实体
			LoaderWorkloadDetailDto queryLoaderWorkloadDetailDto =loaderWorkloadVo.getLoaderWorkloadDetailDto();
			//设置部门
			queryLoaderWorkloadDetailDto.setOrgCode(querySuperiorOrgCode());
			//查询总票数, 总重量, 总件数
			LoaderWorkloadDetailDto loaderWorkloadDetailDto = loaderWorkloadModifyService.querySummaryDetailExpress(queryLoaderWorkloadDetailDto);
			loaderWorkloadVo.setLoaderWorkloadDetailDto(loaderWorkloadDetailDto);
		} catch (TfrBusinessException e) {
			return returnError(e);
		}
		return returnSuccess();
	}
	
	/**
	 * 根据任务编号查询出当前任务的工作量
	 * @author ibm-zhangyixin
	 * @date 2012-12-25 上午11:26:05
	 */
	@JSON
	public String queryLoaderWorksByTaskNo() {
		try {
			List<LoaderWorkloadDto> loaderWorkloadList = loaderWorkloadModifyService.queryLoaderWorksByTaskNo(loaderWorkloadVo.getLoaderWorkloadDto().getTaskNo());
			loaderWorkloadVo.setLoaderWorkloadList(loaderWorkloadList);
		} catch (TfrBusinessException e) {
			return returnError(e);
		}
		return returnSuccess();
	}

	
	/**
	 * 根据任务编号查询出当前任务的工作量(快递)
	 * @author ibm-zhangyixin
	 * @date 2012-12-25 上午11:26:05
	 */
	@JSON
	public String queryLoaderWorksByTaskNoExpress() {
		try {
			List<LoaderWorkloadDto> loaderWorkloadList = loaderWorkloadModifyService.queryLoaderWorksByTaskNoExpress(loaderWorkloadVo.getLoaderWorkloadDto().getTaskNo());
			loaderWorkloadVo.setLoaderWorkloadList(loaderWorkloadList);
		} catch (TfrBusinessException e) {
			return returnError(e);
		}
		return returnSuccess();
	}
	/**
	 * 保存理货员货量信息
	 * @author ibm-zhangyixin
	 * @date 2012-12-25 下午4:15:30
	 */
	@JSON
	public String saveLoaderWork() {
		try {
			loaderWorkloadModifyService.saveLoaderWork(loaderWorkloadVo.getLoaderWorkloadDto());
		} catch (TfrBusinessException e) {
			return returnError(e);
		}
		return returnSuccess();
	}
	
	/**
	 * 保存理货员货量信息（快递）
	 * @author ibm-zhangyixin
	 * @date 2015-12-08 下午4:15:30
	 */
	@JSON
	public String saveLoaderWorkExpress() {
		try {
			loaderWorkloadModifyService.saveLoaderWorkExpress(loaderWorkloadVo.getLoaderWorkloadDto());
		} catch (TfrBusinessException e) {
			return returnError(e);
		}
		return returnSuccess();
	}
	
	/**
	 * 根据任务ID查询出当前任务的工作量信息
	 * @author ibm-zhangyixin
	 * @date 2012-12-25 上午11:26:05
	 */
	@JSON
	public String queryLoaderWorksById() {
		try {
			LoaderWorkloadEntity loaderWorkload = loaderWorkloadModifyService.queryLoaderWorksById(loaderWorkloadVo.getLoaderWorkloadDto().getId());
			loaderWorkloadVo.setLoaderWorkloadEntity(loaderWorkload);
		} catch (TfrBusinessException e) {
			return returnError(e);
		}
		return returnSuccess();
	}

	
	/**
	 * 根据任务ID查询出当前任务的工作量信息（快递）
	 * @author ibm-zhangyixin
	 * @date 2012-12-25 上午11:26:05
	 */
	@JSON
	public String queryLoaderWorksByIdExpress() {
		try {
			LoaderWorkloadEntity loaderWorkload = loaderWorkloadModifyService.queryLoaderWorksByIdExpress(loaderWorkloadVo.getLoaderWorkloadDto().getId());
			loaderWorkloadVo.setLoaderWorkloadEntity(loaderWorkload);
		} catch (TfrBusinessException e) {
			return returnError(e);
		}
		return returnSuccess();
	}
	/**
	 * 修改理货员货量信息
	 * @author ibm-zhangyixin
	 * @date 2012-12-25 下午4:15:30
	 */
	@JSON
	public String modifyLoaderWork() {
		try {
			loaderWorkloadModifyService.modifyLoaderWork(loaderWorkloadVo.getLoaderWorkloadDto());
		} catch (TfrBusinessException e) {
			return returnError(e);
		}
		return returnSuccess();
	}
	
	/**
	 * 修改理货员货量信息（快递）
	 * @author zhangpeng
	 * @date 2012-12-25 下午4:15:30
	 */
	@JSON
	public String modifyLoaderWorkExpress() {
		try {
			loaderWorkloadModifyService.modifyLoaderWorkExpress(loaderWorkloadVo.getLoaderWorkloadDto());
		} catch (TfrBusinessException e) {
			return returnError(e);
		}
		return returnSuccess();
	}
	
	
	/**
	* 获取当前部门的上级部门code
	* @author 105869-foss-heyongdong
	* @date 2013-10-15 下午2:58:53
	*/
	public String querySuperiorOrgCode() {
		String orgCode = FossUserContext.getCurrentInfo().getCurrentDeptCode();
		OrgAdministrativeInfoEntity superEntity = loadService.querySuperiorOrgByOrgCode(orgCode);
		if(superEntity == null || StringUtils.isBlank(superEntity.getCode())){
			return orgCode;
		}else{
			return superEntity.getCode();
		}
	}
	/**
	 * 设置 修改装卸车工作量service*.
	 *
	 * @param loaderWorkloadModifyService the new 修改装卸车工作量service*
	 */
	public void setLoaderWorkloadModifyService(
			ILoaderWorkloadModifyService loaderWorkloadModifyService) {
		this.loaderWorkloadModifyService = loaderWorkloadModifyService;
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
}