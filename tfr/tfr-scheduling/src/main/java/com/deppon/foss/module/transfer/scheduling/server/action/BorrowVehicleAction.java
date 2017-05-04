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
 *  FILE PATH     :src/main/java/com/deppon/foss/module/transfer/scheduling/server/action/BorrowVehicleAction.java
 * 
 *  FILE NAME     :BorrowVehicleAction.java
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
 * FILE    NAME: BorrowVehicleAction.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.transfer.scheduling.server.action;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.module.base.baseinfo.api.server.service.complex.IOrgAdministrativeInfoComplexService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.transfer.common.api.shared.exception.TfrBusinessException;
import com.deppon.foss.module.transfer.scheduling.api.server.service.IBorrowVehicleService;
import com.deppon.foss.module.transfer.scheduling.api.shared.domain.BorrowVehicleEntity;
import com.deppon.foss.module.transfer.scheduling.api.shared.dto.BorrowVehicleDto;
import com.deppon.foss.module.transfer.scheduling.api.shared.exception.BorrowVehicleException;
import com.deppon.foss.module.transfer.scheduling.api.shared.util.ConstantsNumberSonar;
import com.deppon.foss.module.transfer.scheduling.api.shared.vo.BorrowVehicleVo;

/**
 * 借车申请Action.
 * @author 104306-foss-wangLong
 * @date 2012-12-3 下午12:22:37
 */
public class BorrowVehicleAction extends AbstractAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4234973004446081491L;

	/** The borrow vehicle vo. */
	private BorrowVehicleVo borrowVehicleVo = new BorrowVehicleVo();
	
	/** The borrow vehicle service. */
	private IBorrowVehicleService borrowVehicleService; 
	
	/**
	 * 组织信息service
	 */
	private IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService;
	
	/**
	 * 查询借车申请.
	 * @return {@link java.lang.String}
	 * @author 104306-foss-wangLong
	 * @date 2012-12-3 下午2:54:45
	 * @see IBorrowVehicleService#queryBorrowVehicleForPage(BorrowVehicleDto, int, int)
	 * @see IBorrowVehicleService#queryCount(BorrowVehicleDto)
	 */
	@JSON
	public String queryBorrowVehicleApplyList() {
		BorrowVehicleDto borrowVehicleDto = borrowVehicleVo.getBorrowVehicleDto();
		List<BorrowVehicleDto> borrowVehicleList = borrowVehicleService.queryBorrowVehicleForPage(borrowVehicleDto, start, limit);
		Long count = borrowVehicleService.queryCount(borrowVehicleDto);
		borrowVehicleVo.setBorrowVehicleList(borrowVehicleList);
		setTotalCount(count);
		return returnSuccess();
	}
	
	/**
	 * 查询受理超时时间.
	 * @return {@link java.lang.String}
	 * @author 104306-foss-wangLong
	 * @date 2012-12-7 下午4:31:01
	 */
	@JSON
	public String queryAcceptanceTime() {
		//TODO 调用综合借口读取时间
		borrowVehicleVo.setAcceptanceTime(ConstantsNumberSonar.SONAR_NUMBER_15);
		return returnSuccess();
	}

	/**
	 * 查询借车申请明细.
	 * @return {@link java.lang.String}
	 * @author 104306-foss-wangLong
	 * @date 2012-12-3 下午2:54:45
	 * @see IBorrowVehicleService#queryBorrowVehicleApplyDetail(BorrowVehicleDto)
	 */
	@JSON
	public String queryBorrowVehicleApplyDetail() {
		BorrowVehicleDto borrowVehicleDto = borrowVehicleVo.getBorrowVehicleDto();
		String borrowNo = borrowVehicleDto.getBorrowNo();
		try {
			borrowVehicleDto = borrowVehicleService.queryBorrowVehicleApplyDetail(borrowNo);
		} catch (BorrowVehicleException e) {
			return returnError(e);
		}
		borrowVehicleVo.setBorrowVehicleDto(borrowVehicleDto);
		return returnSuccess();
	}
	
	/**
	 * 借车申请 提交按钮 </br>
	 * 1.如果id不为空  为修改操作</br>
	 * 2.其他为新增操作
	 * @return {@link java.lang.String}
	 * @author 104306-foss-wangLong
	 * @date 2012-12-3 下午2:54:45
	 * @see IBorrowVehicleService#saveBorrowVehicleApply()
	 */
	@JSON
	public String saveBorrowVehicleApply() {
		BorrowVehicleEntity borrowVehicleEntity = borrowVehicleVo.getBorrowVehicleEntity();
		try {
			borrowVehicleService.saveBorrowVehicleApply(borrowVehicleEntity);
		} catch (TfrBusinessException e) {
			return returnError(e);
		}
		return returnSuccess();
	}
	
	/**
	 * 查询借车申请  </br>.
	 * @return {@link java.lang.String}
	 * @author 104306-foss-wangLong
	 * @date 2012-12-3 下午2:54:45
	 * @see IBorrowVehicleService#queryBorrowVehicle(String)
	 */
	@JSON
	public String queryBorrowVehicleApply() {
		BorrowVehicleEntity borrowVehicleEntity = borrowVehicleVo.getBorrowVehicleEntity();
		borrowVehicleEntity =  borrowVehicleService.queryBorrowVehicle(borrowVehicleEntity.getId());
		borrowVehicleVo.setBorrowVehicleEntity(borrowVehicleEntity);
		return returnSuccess();
	}
	
	/**
	 * 撤销借车申请.
	 * @return {@link java.lang.String}
	 * @author 104306-foss-wangLong
	 * @date 2012-12-5 下午2:04:06
	 * @see IBorrowVehicleService#doUndoBorrowVehicleApply(List)
	 */
	@JSON
	public String doUndoBorrowVehicleApply() {
		List<String> borrowNoList = borrowVehicleVo.getBorrowNoList();
		List<String> applyOrgCodeList = borrowVehicleVo.getApplyOrgCodeList();
		try {
			for(int i = 0;i<applyOrgCodeList.size();i++){
				if(!StringUtils.equals(applyOrgCodeList.get(i), FossUserContext.getCurrentDeptCode())){
					return returnError("借车号为："+borrowNoList.get(i)+"的单据与申请部门不一致，不能撤销，请检查后重新选择数据！");
				}
			}
			borrowVehicleService.doUndoBorrowVehicleApply(borrowNoList);
		} catch (TfrBusinessException e) {
			return returnError(e);
		}
		return returnSuccess();
	}
	
	/**
	 * 确认到达.
	 * @return {@link java.lang.String}
	 * @author 104306-foss-wangLong
	 * @date 2012-12-5 下午2:04:06
	 * @see IBorrowVehicleService#doBorrowVehicleConfirmTo(List)
	 */
	@JSON
	public String doBorrowVehicleConfirmTo() {
		List<String> borrowNoList = borrowVehicleVo.getBorrowNoList();
		try {
			borrowVehicleService.doBorrowVehicleConfirmTo(borrowNoList);
		} catch (TfrBusinessException e) {
			return returnError(e);
		}
		return returnSuccess();
	}
	
	/**
	 * 车辆归还.
	 * @return {@link java.lang.String}
	 * @author 104306-foss-wangLong
	 * @date 2012-12-5 下午2:06:18
	 * @see IBorrowVehicleService#doBorrowVehicleGiveBack(List)
	 */
	@JSON
	public String doBorrowVehicleGiveBack() {
		List<String> borrowNoList = borrowVehicleVo.getBorrowNoList();
		try {
			borrowVehicleService.doBorrowVehicleGiveBack(borrowNoList);
		} catch (TfrBusinessException e) {
			return returnError(e);
		}
		return returnSuccess();
	}
	
	/**
	 * 验证是否配置了顶级车队
	 * @author foss-liuxue(for IBM)
	 * @date 2013-4-28 下午3:31:40
	 */
	public String validateApplyBorrow(){
		BorrowVehicleDto borrowVehicleDto = borrowVehicleVo.getBorrowVehicleDto();
		OrgAdministrativeInfoEntity topFleet = orgAdministrativeInfoComplexService.getTopFleetByCode(FossUserContext.getCurrentDeptCode());
		if(null == topFleet){
			return returnError("当前部门没有配置顶级车队，不能进行申请！");
		}
		if(StringUtils.isNotBlank(borrowVehicleDto.getId())){
			if(!StringUtils.equals(borrowVehicleDto.getApplyOrgCode(), FossUserContext.getCurrentDeptCode())){
				return returnError("当前部门与申请部门不一致，不能修改！");
			}
		}
		return returnSuccess(); 
	}
	
	/**
	 * 获取服务器时间
	 * @author 104306-foss-wangLong
	 * @date 2013-1-5 下午12:56:33
	 * @return {@link java.lang.String}
	 */
	@JSON
	public String queryCurrentTime() {
		borrowVehicleVo.setCurrentTime(new Date());
		return returnSuccess(); 
	}

	/**
	 * 设置borrowVehicleService.
	 * @param borrowVehicleService the borrowVehicleService to set
	 */
	public void setBorrowVehicleService(IBorrowVehicleService borrowVehicleService) {
		this.borrowVehicleService = borrowVehicleService;
	}

	/**
	 * 设置borrowVehicleVo.
	 * @param borrowVehicleVo the borrowVehicleVo to set
	 */
	public void setBorrowVehicleVo(BorrowVehicleVo borrowVehicleVo) {
		this.borrowVehicleVo = borrowVehicleVo;
	}

	/**
	 * 设置borrowVehicleVo.
	 * @return the borrow vehicle vo
	 */
	public BorrowVehicleVo getBorrowVehicleVo() {
		return borrowVehicleVo;
	}

	/**
	 * 设置 组织信息service.
	 *
	 * @param orgAdministrativeInfoComplexService the new 组织信息service
	 */
	public void setOrgAdministrativeInfoComplexService(
			IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService) {
		this.orgAdministrativeInfoComplexService = orgAdministrativeInfoComplexService;
	}
}