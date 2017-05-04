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
 *  FILE PATH     :src/main/java/com/deppon/foss/module/transfer/scheduling/server/action/ReCreateTransportationPathAction.java
 * 
 *  FILE NAME     :ReCreateTransportationPathAction.java
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
package com.deppon.foss.module.transfer.scheduling.server.action;

import java.util.List;

import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.module.transfer.common.api.shared.exception.TfrBusinessException;
import com.deppon.foss.module.transfer.scheduling.api.server.service.IReCreateTransportationPathService;
import com.deppon.foss.module.transfer.scheduling.api.shared.domain.PathDetailEntity;
import com.deppon.foss.module.transfer.scheduling.api.shared.domain.TransportPathEntity;
import com.deppon.foss.module.transfer.scheduling.api.shared.vo.SchedulingVO;
import com.deppon.foss.module.transfer.stock.api.shared.domain.StockEntity;

/**
 * 重新计算货物走货路径action
 * 
 * @author huyue
 * @date 2012-10-11 下午9:11:32
 */
public class ReCreateTransportationPathAction extends AbstractAction {

	private static final long serialVersionUID = -5182857592065055743L;

	private SchedulingVO schedulingVO = new SchedulingVO();

	private transient IReCreateTransportationPathService reCreateTransportationPathService;

	/**
	 * 
	 *
	 * @return 
	 */
	public SchedulingVO getSchedulingVO() {
		return schedulingVO;
	}

	/**
	 * 
	 *
	 * @param schedulingVO 
	 */
	public void setSchedulingVO(SchedulingVO schedulingVO) {
		this.schedulingVO = schedulingVO;
	}

	/**
	 * 
	 *
	 * @param reCreateTransportationPathService 
	 */
	public void setReCreateTransportationPathService(IReCreateTransportationPathService reCreateTransportationPathService) {
		this.reCreateTransportationPathService = reCreateTransportationPathService;
	}

	/**
	 * 
	 *
	 * @return 
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	/**
	 * 重新计算走货路径LIST查询, 通过VO传递list+count至前端
	 * 
	 * @author huyue
	 * @date 2012-10-11 下午9:12:51
	 */
	public String queryTransportPath() {
		try {
			List<TransportPathEntity> transportPathList = reCreateTransportationPathService.queryTransportPathforPage(schedulingVO.getTransportPathEntity(), this.getLimit(), this.getStart());
			schedulingVO.setTransportPathList(transportPathList);
			Long totalCount = reCreateTransportationPathService.getCount(schedulingVO.getTransportPathEntity());
			this.setTotalCount(totalCount);
			return returnSuccess();
		} catch (TfrBusinessException e) {
			return returnError(e);
		}
	}

	/**
	 * 重新计算货物走货路径
	 * 
	 * @author huyue
	 * @date 2012-11-5 上午10:15:10
	 */
	public String reCreateTransportPath() {
		try {
			reCreateTransportationPathService.reCreateTransportPath(schedulingVO.getTransportPathList());
			return returnSuccess();
		} catch (TfrBusinessException e) {
			return returnError(e);
		}
	}
	
	/**
	 * 根据运单号、流水号查询走货路径明细
	 * 
	 * @author peng.z
	 * @date 2013-05-29 上午10:15:10
	 */
	public String queryPathDetail(){
		try {
			PathDetailEntity pathDetail = schedulingVO.getPathDetailEntity();
			if(pathDetail != null){
				List<PathDetailEntity> pathDetailList = reCreateTransportationPathService.queryPathDetail(pathDetail.getWaybillNo(), pathDetail.getGoodsNo());
				schedulingVO.setPathDetailList(pathDetailList);
			}
			return returnSuccess();
		} catch (TfrBusinessException e) {
			return returnError(e);
		}
	}
	
	/**
	 * 根据运单号查询当前部门库存信息
	 * 
	 * @author peng.z
	 * @date 2013-05-29 上午10:15:10
	 */
	public String queryStock(){
		try {
			TransportPathEntity transportPath = schedulingVO.getTransportPathEntity();
			if(transportPath != null){
				List<StockEntity>  stockEntityList = reCreateTransportationPathService.queryStock(transportPath.getWaybillNo(),transportPath.getCurrentOrgCode());
				schedulingVO.setStockEntityList(stockEntityList);
			}
			return returnSuccess();
		} catch (TfrBusinessException e) {
			return returnError(e);
		}
	}
}
