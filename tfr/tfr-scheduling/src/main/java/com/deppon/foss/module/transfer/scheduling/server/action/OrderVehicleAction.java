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
 *  DESCRIPTION   : 调度、约车记录、排班、月台、车辆管理等
 *  
 *  FILE PATH     :src/main/java/com/deppon/foss/module/transfer/scheduling/server/action/OrderVehicleAction.java
 * 
 *  FILE NAME     :OrderVehicleAction.java
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

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.deppon.foss.base.util.define.BizTypeConstants;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.module.base.baseinfo.api.server.service.ISalesMotorcadeService;
import com.deppon.foss.module.base.baseinfo.api.server.service.complex.IOrgAdministrativeInfoComplexService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SalesMotorcadeEntity;
import com.deppon.foss.module.base.common.api.shared.define.MutexElementType;
import com.deppon.foss.module.base.common.api.shared.dto.MutexElement;
import com.deppon.foss.module.pickup.order.api.shared.exception.DispatchException;
import com.deppon.foss.module.transfer.common.api.shared.exception.TfrBusinessException;
import com.deppon.foss.module.transfer.scheduling.api.server.service.IOrderVehicleService;
import com.deppon.foss.module.transfer.scheduling.api.server.service.IPassOrderApplyService;
import com.deppon.foss.module.transfer.scheduling.api.shared.domain.OrderVehicleEntity;
import com.deppon.foss.module.transfer.scheduling.api.shared.dto.PassOrderApplyDto;
import com.deppon.foss.module.transfer.scheduling.api.shared.dto.SimpleTruckScheduleDto;
import com.deppon.foss.module.transfer.scheduling.api.shared.exception.OrderVehicleStatusErrorException;
import com.deppon.foss.module.transfer.scheduling.api.shared.exception.ParameterException;
import com.deppon.foss.module.transfer.scheduling.api.shared.vo.OrderVehicleVo;

/**
 * 约车申请
 * 
 * @author 104306-foss-wangLong
 * @date 2012-10-15 下午12:49:37
 */
public class OrderVehicleAction extends AbstractAction {

	private static final long serialVersionUID = 13966823467298584L;

	/** 约车申请Service */
	private IOrderVehicleService orderVehicleService;

	/** 约车审核Service */
	private IPassOrderApplyService passOrderApplyService;

	/** 营业部与车队 */
	private ISalesMotorcadeService salesMotorcadeService;

	/** 约车申请view Object */
	private OrderVehicleVo orderVehicleVo = new OrderVehicleVo();

	/**
	 * 业务锁Service
	 */
	//sonar-未使用-352203
//	private IBusinessLockService businessLockService;

	private IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService;

/*	public void setBusinessLockService(IBusinessLockService businessLockService) {
		this.businessLockService = businessLockService;
	}*/

	public void setOrgAdministrativeInfoComplexService(IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService) {
		this.orgAdministrativeInfoComplexService = orgAdministrativeInfoComplexService;
	}

	/**
	 * 查询约车申请
	 * 
	 * @author 104306-foss-wangLong
	 * @date 2012-10-15 下午12:37:18
	 * @return
	 * @see IOrderVehicleService#queryOrderVehicleForPage
	 * @see IOrderVehicleService#queryCount
	 */
	@JSON
	public String queryOrderVehicleApply() {
		List<OrderVehicleEntity> orderVehicleList = orderVehicleService.queryOrderVehicleForPage(orderVehicleVo.getOrderVehicleDto(), this.getStart(), this.getLimit());
		setTotalCount(orderVehicleService.queryCount(orderVehicleVo.getOrderVehicleDto()));
		orderVehicleVo.setOrderVehicleList(orderVehicleList);

		return returnSuccess();
	}

	/**
	 * 根据当前部门查询所属外场或营业部
	 * @author huyue
	 * @date 2013-5-3 上午11:14:00
	 */
	@JSON
	public String findOrderBelongDept() {
		// 尝试找登陆部门所属的顶级车队，如果能找到
		String belongTransforCenter = findTransforCenter(orderVehicleVo.getBelongTransforCenter());
		if (null == belongTransforCenter) {
			throw new ParameterException("此登陆部门未找到对应的顶级车队，无法审核受理！");
		} else {
			orderVehicleVo.setBelongTransforCenter(belongTransforCenter);
			return returnSuccess();
		}
	}

	/**
	 * 查询约车申请明细
	 * 
	 * @author 104306-foss-wangLong
	 * @date 2012-10-15 下午4:31:17
	 * @return
	 * @see IOrderVehicleService#queryOrderVehicle(String)
	 */
	@JSON
	public String queryOrderVehicleApplyDetail() {
		OrderVehicleEntity orderVehicleEntity = orderVehicleVo.getOrderVehicleEntity();
		orderVehicleEntity = orderVehicleService.queryOrderVehicle(orderVehicleEntity.getId());
		orderVehicleVo.setOrderVehicleEntity(orderVehicleEntity);
		if (orderVehicleEntity == null) {
			orderVehicleEntity = new OrderVehicleEntity();
		}
		PassOrderApplyDto passOrderApplyDto = passOrderApplyService.queryPassOrderApplyAndAuditOrderApplyLog(orderVehicleEntity.getOrderNo());
		orderVehicleVo.setPassOrderApplyDto(passOrderApplyDto);
		return returnSuccess();
	}

	/**
	 * 
	 * 撤销约车申请 修改状态
	 * 
	 * @author 104306-foss-wangLong
	 * @date 2012-10-15 下午6:45:10
	 * @throws OrderVehicleStatusErrorException
	 * @return {@link java.lang.String}
	 * @see IOrderVehicleService#doUndoOrderVehicleApply()
	 */
	@JSON
	public String doUndoOrderVehicleApply() {
		try {
			orderVehicleService.doUndoOrderVehicleApply(orderVehicleVo.getOrderVehicleApplyIdList());
		} catch (DispatchException e) {
			return returnError(e);
		}catch (TfrBusinessException e) {
			return returnError(e);
		}
		return returnSuccess();
	}

	/**
	 * 
	 * 确认到达 修改状态
	 * 
	 * @author 104306-foss-wangLong
	 * @date 2012-10-16 下午3:25:51
	 * @throws OrderVehicleStatusErrorException
	 * @return {@link java.lang.String}
	 * @see IOrderVehicleService#doConfirmTo()
	 */
	@JSON
	public String doConfirmTo() {
		try {
			orderVehicleService.doConfirmTo(orderVehicleVo.getOrderVehicleApplyIdList());
		} catch (TfrBusinessException e) {
			return returnError(e);
		}
		return returnSuccess();
	}

	/**
	 * 约车申请 提交按钮 </br> 1.如果id不为空 为修改操作</br> 2.其他为新增操作
	 * 
	 * @author 104306-foss-wangLong
	 * @date 2012-10-22 下午5:09:06
	 * @return {@link java.lang.String}
	 * @see IOrderVehicleService#saveOrderVehicle()
	 */
	@JSON
	public String saveVehicleApply() {
		OrderVehicleEntity orderVehicleEntity = orderVehicleVo.getOrderVehicleEntity();
		try {
			// 保存更新
			orderVehicleEntity = orderVehicleService.saveOrderVehicle(orderVehicleEntity);
			orderVehicleVo.setOrderVehicleEntity(orderVehicleEntity);
			return returnSuccess();
		} catch (TfrBusinessException e) {
			// 记录异常错误
			return returnError(e);
		}
	}

	/**
	 * 约车申请 暂存 </br>
	 * 
	 * @author 104306-foss-wangLong
	 * @date 2012-10-22 下午5:09:06
	 * @return {@link java.lang.String}
	 * @see {@link #saveVehicleApply()}
	 */
	@JSON
	public String doTemporarySave() {
		return saveVehicleApply();
	}

	/**
	 * 查询车队</br>
	 * 
	 * @author 104306-foss-wangLong
	 * @date 2012-12-4 下午7:42:15
	 * @return {@link java.lang.String}
	 */
	@JSON
	public String queryMotorcade() {
		List<SalesMotorcadeEntity> salesMotorcadeList = new ArrayList<SalesMotorcadeEntity>();
		SalesMotorcadeEntity salesMotorcadeEntity = new SalesMotorcadeEntity();
		salesMotorcadeEntity.setSalesdeptCode(orderVehicleVo.getSalesdeptCode());
		try {
			salesMotorcadeList = salesMotorcadeService.querySalesMotorcadeExactByEntity(salesMotorcadeEntity, Integer.MIN_VALUE, Integer.MAX_VALUE);
		} catch (BusinessException e) {
			return returnError(e);
		}
		orderVehicleVo.setSalesMotorcadeList(salesMotorcadeList);
		return returnSuccess();
	}

	/**
	 * 非集中区域的转货约车，以及送货约车、偏线约车，需要处理车辆归还业务
	 * 
	 * @return:
	 * 
	 * @author foss-wuyingjie
	 * @date 2013-3-4 下午5:08:15
	 */
	@JSON
	public String doOrderVehicleGiveBack() {
		List<String> orderyNoList = orderVehicleVo.getOrderVehicleApplyIdList();
		try {
			orderVehicleService.doOrderVehicleGiveBack(orderyNoList);
		} catch (TfrBusinessException e) {

			return returnError(e);
		}
		return returnSuccess();
	}

	/**
	 * 判断此登陆用户是否为所选约车信息中的派送车队所对应的的顶级车队或顶级车队的调度组
	 * 
	 * @return:
	 * 
	 * @author foss-wuyingjie
	 * @date 2013-4-8 下午3:49:57
	 */
	@JSON
	public String checkTopFleet() {
		try {
			orderVehicleService.checkTopFleet(orderVehicleVo.getMotorcadeCode());
		} catch (TfrBusinessException e) {

			return returnError(e);
		}
		return returnSuccess();
	}

	/**
	 * 尝试通过车牌号在短途排班表中获取司机信息
	 * 
	 * @return:
	 * 
	 * @author foss-wuyingjie
	 * @date 2013-4-8 下午5:03:18
	 */
	@JSON
	public String queryDriverInfoInShortPlan() {
		try {
			SimpleTruckScheduleDto dto = orderVehicleService.queryDriverInfoInShortPlan(orderVehicleVo.getVehicleNo());
			orderVehicleVo.setSimpleTruckScheduleDto(dto);
		} catch (TfrBusinessException e) {

			return returnError(e);
		}
		return returnSuccess();
	}

	/**
	 * 获取锁条件
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2013-4-22 上午10:14:03
	 */
	private MutexElement fetchMutexElement(String orderId) {
		// 锁定条件
		MutexElement mutex = new MutexElement(orderId, "约车", MutexElementType.TRUCK_SCHEDULING);
		return mutex;
	}

	public String findTransforCenter(String org){
		List<String> bizTypes = new ArrayList<String>();
		bizTypes.add(BizTypeConstants.ORG_TRANSFER_CENTER);
		bizTypes.add(BizTypeConstants.ORG_SALES_DEPARTMENT);
		OrgAdministrativeInfoEntity parentOrg = orgAdministrativeInfoComplexService.queryOrgAdministrativeInfoIncludeSelfByCode(org, bizTypes);
		if(null == parentOrg || StringUtils.isEmpty(parentOrg.getCode())){
			throw new TfrBusinessException("未找到此部门：" + org + " 所对应的的上级 外场 或 营业部");
		}else{
			return parentOrg.getCode();
		}
	}
	
	/**
	 * 获得orderVehicleVo
	 * 
	 * @return the orderVehicleVo
	 */
	public OrderVehicleVo getOrderVehicleVo() {
		return orderVehicleVo;
	}

	/**
	 * 设置orderVehicleVo
	 * 
	 * @param orderVehicleVo
	 *            the orderVehicleVo to set
	 */
	public void setOrderVehicleVo(OrderVehicleVo orderVehicleVo) {
		this.orderVehicleVo = orderVehicleVo;
	}

	/**
	 * 设置orderVehicleService
	 * 
	 * @param orderVehicleService
	 *            the orderVehicleService to set
	 */
	public void setOrderVehicleService(IOrderVehicleService orderVehicleService) {
		this.orderVehicleService = orderVehicleService;
	}

	/**
	 * 设置passOrderApplyService
	 * 
	 * @param passOrderApplyService
	 *            the passOrderApplyService to set
	 */
	public void setPassOrderApplyService(IPassOrderApplyService passOrderApplyService) {
		this.passOrderApplyService = passOrderApplyService;
	}

	/**
	 * 设置salesMotorcadeService
	 * 
	 * @param salesMotorcadeService
	 *            the salesMotorcadeService to set
	 */
	public void setSalesMotorcadeService(ISalesMotorcadeService salesMotorcadeService) {
		this.salesMotorcadeService = salesMotorcadeService;
	}
}