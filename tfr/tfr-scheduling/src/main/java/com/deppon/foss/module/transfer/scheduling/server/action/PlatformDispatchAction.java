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
 *  
 *  PROJECT NAME  : tfr-scheduling
 *  
 *  PACKAGE NAME  : 
 * 
 *  DESCRIPTION   : 调度、发车计划、排班、月台、车辆管理等
 *  
 *  FILE PATH     :src/main/java/com/deppon/foss/module/transfer/scheduling/server/action/PlatformDispatchAction.java
 * 
 *  FILE NAME     :PlatformDispatchAction.java
 *  
 *  AUTHOR        : FOSS中转开发组
 *  
 *  TIME          : 
 *  
 *  HOME PAGE     :  http://www.deppon.com
 *  
 *  COPYRIGHT     : Copyright (c) 2013  Deppon All Rights Reserved.
 * 
 *  VERSION       :0.1
 * 
 *  LAST MODIFY TIME:
 ******************************************************************************/
/*
 * PROJECT NAME: tfr-scheduling
 * PACKAGE NAME: com.deppon.foss.module.transfer.scheduling.server.action
 * FILE    NAME: PlatformDispatchAction.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.transfer.scheduling.server.action;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.PlatformEntity;
import com.deppon.foss.module.transfer.common.api.shared.exception.TfrBusinessException;
import com.deppon.foss.module.transfer.load.api.server.service.IQueryProgressService;
import com.deppon.foss.module.transfer.load.api.shared.dto.QueryProgressResultDto;
import com.deppon.foss.module.transfer.scheduling.api.define.PlatformDispatchConstants;
import com.deppon.foss.module.transfer.scheduling.api.server.service.IPlatformDispatchService;
import com.deppon.foss.module.transfer.scheduling.api.shared.domain.PlatformDistributeEntity;
import com.deppon.foss.module.transfer.scheduling.api.shared.dto.PlatformDistributeDto;
import com.deppon.foss.module.transfer.scheduling.api.shared.dto.TransferDeptInfo;
import com.deppon.foss.module.transfer.scheduling.api.shared.exception.PlatformDispatchException;
import com.deppon.foss.module.transfer.scheduling.api.shared.vo.PlatformDispatchVo;

/**
 * 月台
 * @author 104306-foss-wangLong
 * @date 2012-10-31 下午3:13:16
 */
public class PlatformDispatchAction extends AbstractAction {

	private static final long serialVersionUID = -308116834261081386L;

	/** 月台分配View Object */
	private PlatformDispatchVo platformDispatchVo = new PlatformDispatchVo();
	
	/** 月台分配service */
	private IPlatformDispatchService platformDispatchService;
	
	/** 装卸车进度service */
	private IQueryProgressService queryProgressService;
	
	/**
	 * 查询月台使用情况
	 * @author 104306-foss-wangLong
	 * @date 2012-11-8 上午8:18:17
	 * @return 
	 * @see IPlatformDispatchService#queryPlatformUseInfo
	 */
	@JSON
	public String queryPlatformUseInfo() {
		PlatformDistributeDto platformDistributeDto = platformDispatchVo.getPlatformDistributeDto();
		List<PlatformDistributeDto> list = null;
		try {
			list = platformDispatchService.queryPlatformUseInfo(platformDistributeDto);
		} catch (PlatformDispatchException e) {
			return returnError(e);
		}
		platformDispatchVo.setPlatformDistributeDtoList(list);
		return returnSuccess();
	}
	
	/**
	 * 强制清空月台信息
	 * @author 046130-foss-xuduowei
	 * @date 2013-05-20 下午15:40:22
	 * @return
	 */
	@JSON
	public String forceDeletePlatFrom(){
		int succNum = 
				platformDispatchService.forceDeletePlatFrom(
						platformDispatchVo.getDeptCode(), platformDispatchVo.getPlatFormNo());
		if(succNum > 0){
			return returnSuccess();
		}else{
			return returnError(new TfrBusinessException("请确认此月台是否为手动占用"));
		}
	}
	
	/**
	 * 初始化获取部门信息
	 * @author 046130-foss-xuduowei 
	 * @date 2013-4-25 下午4:18:17
	 * @return
	 */
	@JSON
	public String queryTransferDept(){
		try {
			TransferDeptInfo transferDeptInfo = platformDispatchService.queryTransferDept();
			platformDispatchVo.setTransferDeptInfo(transferDeptInfo);
			return returnSuccess();
		} catch (TfrBusinessException e) {
			return returnError(e);
		}
	}
	
	/**
	 * 查看月台详情
	 * @author 104306-foss-wangLong
	 * @date 2012-11-9 上午10:08:05
	 * @return
	 * @see IPlatformDispatchService#queryPlatformDetail
	 */
	@JSON
	public String queryPlatformDetail() {
		PlatformDistributeDto platformDistributeDto = platformDispatchVo.getPlatformDistributeDto();
		//根据月台虚拟编号查询
		PlatformEntity platformEntity = platformDispatchService.queryPlatformDetail(platformDistributeDto.getPlatformVirtualCode());
		platformDispatchVo.setPlatformEntity(platformEntity);
		return returnSuccess();
	}
	
	/**
	 * 检查月台使用时间是否大于当前时间
	 * @author 104306-foss-wangLong
	 * @date 2012-11-12 上午8:13:56
	 * @return
	 * @see IPlatformDispatchService#doUseTimeIfGreaterThanOrEqualCurrentTime
	 */
	@JSON
	public String doUseTimeIfGreaterThanOrEqualCurrentTime() {
		PlatformDistributeEntity platformDistributeEntity = platformDispatchVo.getPlatformDistributeEntity();
		boolean result = platformDispatchService.doUseTimeIfGreaterThanOrEqualCurrentTime(
															platformDistributeEntity.getId());
		platformDispatchVo.setUserTimeIfGreaterThanOrEqualCurrentTime(result);
		return returnSuccess();
	}
	
	/**
	 * 根据月台分配主键id查询
	 * @author 104306-foss-wangLong
	 * @date 2012-11-12 下午3:20:20
	 * @return
	 * @see IPlatformDispatchService#queryPlatformDistribute
	 */
	@JSON
	public String queryPlatformDistributeInfo() {
		PlatformDistributeEntity platformDistributeEntity = platformDispatchVo.getPlatformDistributeEntity();
		platformDistributeEntity = platformDispatchService.queryPlatformDistribute(platformDistributeEntity.getId());
		platformDispatchVo.setPlatformDistributeEntity(platformDistributeEntity);
		if (platformDistributeEntity != null 
				&& StringUtils.equals(platformDistributeEntity.getType(), PlatformDispatchConstants.PLATFORMDISPATCH_TYPE_ACTUALUSE) 
				&& StringUtil.isNotBlank(platformDistributeEntity.getLoadTaskNo())) {
			//查询装卸车进度   沪B57855
			try {
				QueryProgressResultDto queryProgressResultDto = queryProgressService.queryProgressResult(platformDistributeEntity.getLoadTaskNo());
				platformDispatchVo.setQueryProgressResultDto(queryProgressResultDto);
			} catch (BusinessException e) {
				return returnError(e);
			}
		}
		return returnSuccess();
	}
	
	/**
	 * 清空月台
	 * @author 104306-foss-wangLong
	 * @date 2012-11-12 上午10:32:23
	 * @return
	 * @see IPlatformDispatchService#clearPlatform
	 */
	@JSON
	public String clearPlatform() {
		PlatformDistributeEntity platformDistributeEntity = platformDispatchVo.getPlatformDistributeEntity();
		platformDispatchService.clearPlatform(platformDistributeEntity);
		return returnSuccess();
	}
	
	/**
	 * 修改月台
	 * @author 104306-foss-wangLong
	 * @date 2012-11-12 下午2:52:23
	 * @return
	 * @see IPlatformDispatchService#updatePlatformUseInfo
	 */
	@JSON
	public String updatePlatformUseInfo() {
		PlatformDistributeEntity platformDistributeEntity = platformDispatchVo.getPlatformDistributeEntity();
		try {
			platformDispatchService.updatePlatformUseInfo(platformDistributeEntity);
		} catch (TfrBusinessException e) {
			return returnError(e);
		}
		return returnSuccess(); 
	}
	
	/**
	 * 分配月台(使用月台)
	 * @author 104306-foss-wangLong
	 * @date 2012-11-14 下午4:33:02
	 * @return
	 * @see IPlatformDispatchService#updatePlatformUseInfo
	 */
	public String dispatchPlatform() {
		PlatformDistributeEntity platformDistributeEntity = platformDispatchVo.getPlatformDistributeEntity();
		try {
			platformDispatchService.dispatchPlatform(platformDistributeEntity);
		} catch (TfrBusinessException e) {
			return returnError(e);
		}
		return returnSuccess(); 
	}

	/**
	 * 获得platformDispatchVo
	 * @return the platformDispatchVo
	 */
	public PlatformDispatchVo getPlatformDispatchVo() {
		return platformDispatchVo;
	}

	/**
	 * 设置platformDispatchVo
	 * @param platformDispatchVo the platformDispatchVo to set
	 */	
	public void setPlatformDispatchVo(PlatformDispatchVo platformDispatchVo) {
		this.platformDispatchVo = platformDispatchVo;
	}

	/**
	 * 设置platformDispatchService
	 * @param platformDispatchService the platformDispatchService to set
	 */	
	public void setPlatformDispatchService(IPlatformDispatchService platformDispatchService) {
		this.platformDispatchService = platformDispatchService;
	}

	/**
	 * 设置queryProgressService
	 * @param queryProgressService the queryProgressService to set
	 */	
	public void setQueryProgressService(IQueryProgressService queryProgressService) {
		this.queryProgressService = queryProgressService;
	}
}