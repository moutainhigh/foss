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
 *  PROJECT NAME  : tfr-load-api
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/load/api/shared/vo/LoaderWorkloadVo.java
 *  
 *  FILE NAME          :LoaderWorkloadVo.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.transfer.load.api.shared.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.deppon.foss.module.transfer.load.api.shared.domain.LoaderWorkloadEntity;
import com.deppon.foss.module.transfer.load.api.shared.dto.ErrorVolumeDeductionDto;
import com.deppon.foss.module.transfer.load.api.shared.dto.LoaderWorkloadDetailDto;
import com.deppon.foss.module.transfer.load.api.shared.dto.LoaderWorkloadDto;

/**
 * 查询装卸车工作量VO
 * @author ibm-zhangyixin
 * @date 2012-12-25 下午1:54:55
 */
public class LoaderWorkloadVo implements Serializable {
	
	private static final long serialVersionUID = -4414499005129277182L;

	/**装卸车工作量Dto**/
	private LoaderWorkloadDto loaderWorkloadDto = new LoaderWorkloadDto();
	
	/**装卸车工作量统计**/
	List<LoaderWorkloadDto> loaderWorkloadDtos = new ArrayList<LoaderWorkloadDto>();

	/**装卸车工作量Dtos**/
	private List<LoaderWorkloadDto> loaderWorkloadList = new ArrayList<LoaderWorkloadDto>();
	
	/**装卸车工作量明细**/
	private LoaderWorkloadDetailDto loaderWorkloadDetailDto = new LoaderWorkloadDetailDto();

	/**装卸车工作量明细s**/
	private List<LoaderWorkloadDetailDto> loaderWorkloadDetailList = new ArrayList<LoaderWorkloadDetailDto>();
	
	/**装卸车工作量Entity**/
	private LoaderWorkloadEntity loaderWorkloadEntity = new LoaderWorkloadEntity();
	
	/**当前部门顶级外场**/
	private String superOrgCode;
	
	/**差错货量扣除**/
	private ErrorVolumeDeductionDto errorVolumeDeductionDto;
	
	/**差错货量扣除Dtos**/
	private List<ErrorVolumeDeductionDto> errorVolumeDeductionList;
	/**
	 * 获取 装卸车工作量Dto*.
	 *
	 * @return the 装卸车工作量Dto*
	 */
	public LoaderWorkloadDto getLoaderWorkloadDto() {
		return loaderWorkloadDto;
	}
	
	/**
	 * 设置 装卸车工作量Dto*.
	 *
	 * @param loaderWorkloadDto the new 装卸车工作量Dto*
	 */
	public void setLoaderWorkloadDto(LoaderWorkloadDto loaderWorkloadDto) {
		this.loaderWorkloadDto = loaderWorkloadDto;
	}
	
	/**
	 * 获取 装卸车工作量Dtos*.
	 *
	 * @return the 装卸车工作量Dtos*
	 */
	public List<LoaderWorkloadDto> getLoaderWorkloadList() {
		return loaderWorkloadList;
	}
	
	/**
	 * 设置 装卸车工作量Dtos*.
	 *
	 * @param loaderWorkloadList the new 装卸车工作量Dtos*
	 */
	public void setLoaderWorkloadList(List<LoaderWorkloadDto> loaderWorkloadList) {
		this.loaderWorkloadList = loaderWorkloadList;
	}
	
	/**
	 * 获取 装卸车工作量明细*.
	 *
	 * @return the 装卸车工作量明细*
	 */
	public LoaderWorkloadDetailDto getLoaderWorkloadDetailDto() {
		return loaderWorkloadDetailDto;
	}
	
	/**
	 * 设置 装卸车工作量明细*.
	 *
	 * @param loaderWorkloadDetailDto the new 装卸车工作量明细*
	 */
	public void setLoaderWorkloadDetailDto(
			LoaderWorkloadDetailDto loaderWorkloadDetailDto) {
		this.loaderWorkloadDetailDto = loaderWorkloadDetailDto;
	}
	
	/**
	 * 获取 装卸车工作量明细s*.
	 *
	 * @return the 装卸车工作量明细s*
	 */
	public List<LoaderWorkloadDetailDto> getLoaderWorkloadDetailList() {
		return loaderWorkloadDetailList;
	}
	
	/**
	 * 设置 装卸车工作量明细s*.
	 *
	 * @param loaderWorkloadDetailList the new 装卸车工作量明细s*
	 */
	public void setLoaderWorkloadDetailList(
			List<LoaderWorkloadDetailDto> loaderWorkloadDetailList) {
		this.loaderWorkloadDetailList = loaderWorkloadDetailList;
	}
	
	/**
	 * 获取 装卸车工作量Entity*.
	 *
	 * @return the 装卸车工作量Entity*
	 */
	public LoaderWorkloadEntity getLoaderWorkloadEntity() {
		return loaderWorkloadEntity;
	}
	
	/**
	 * 设置 装卸车工作量Entity*.
	 *
	 * @param loaderWorkloadEntity the new 装卸车工作量Entity*
	 */
	public void setLoaderWorkloadEntity(LoaderWorkloadEntity loaderWorkloadEntity) {
		this.loaderWorkloadEntity = loaderWorkloadEntity;
	}

	/**   
	 * superOrgCode   
	 *   
	 * @return  the superOrgCode   
	 */
	
	public String getSuperOrgCode() {
		return superOrgCode;
	}

	/**   
	 * @param superOrgCode the superOrgCode to set
	 * Date:2013-5-31上午10:33:59
	 */
	public void setSuperOrgCode(String superOrgCode) {
		this.superOrgCode = superOrgCode;
	}

	/**   
	 * loaderWorkloadDtos   
	 *   
	 * @return  the loaderWorkloadDtos   
	 */
	
	public List<LoaderWorkloadDto> getLoaderWorkloadDtos() {
		return loaderWorkloadDtos;
	}

	/**   
	 * @param loaderWorkloadDtos the loaderWorkloadDtos to set
	 * Date:2013-7-10下午4:04:27
	 */
	public void setLoaderWorkloadDtos(List<LoaderWorkloadDto> loaderWorkloadDtos) {
		this.loaderWorkloadDtos = loaderWorkloadDtos;
	}

	public ErrorVolumeDeductionDto getErrorVolumeDeductionDto() {
		return errorVolumeDeductionDto;
	}

	public void setErrorVolumeDeductionDto(
			ErrorVolumeDeductionDto errorVolumeDeductionDto) {
		this.errorVolumeDeductionDto = errorVolumeDeductionDto;
	}

	public List<ErrorVolumeDeductionDto> getErrorVolumeDeductionList() {
		return errorVolumeDeductionList;
	}

	public void setErrorVolumeDeductionList(
			List<ErrorVolumeDeductionDto> errorVolumeDeductionList) {
		this.errorVolumeDeductionList = errorVolumeDeductionList;
	}
}